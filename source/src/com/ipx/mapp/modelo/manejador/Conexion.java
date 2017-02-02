/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.manejador;

import de.enough.polish.json.JsonObject;
import de.enough.polish.json.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class Conexion implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Conexion.class);
    private Thread t;
    private String resp;
    private String url;
    private String cadenajson;
    private Manejador manejador;
    private boolean estadoConsulta;

    public Conexion() {
        //this.mc = mc;
    }
    
    public void setManejador(Manejador manejador){
        this.manejador = manejador;
    }

    private void resetUrl() {
        this.url = "http://200.105.139.183:9090/movilidad/model/servicios/";
        //this.url = "http://192.168.50.128/";
    }

    public void setUrlLogueo() {
        resetUrl();
        this.url += "autenticacion.php";
        //this.url += "autentificar.php";
    }

    public void setUrlRegistrar() {
        resetUrl();
        this.url += "registrar.php";
    }
    
    public void setUrlBuscaOperador() {
        resetUrl();
        this.url += "buscarOperador.php";
        //this.url += "operador.php";
    }

    public void setCadenajson(String cadenajson) {
        logger.info("cadena json " + cadenajson);
        this.cadenajson = cadenajson;
    }

    public String getCadenajson() {
        return this.cadenajson;
    }

    public void conectar() {
        t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);

        t.start();
        try {
            t.join();
            //return getResp();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void run() {
        estadoConsulta = true;
        HttpConnection hc = null;
        try {
            logger.info("try");
            hc = (HttpConnection) Connector.open(url);
            logger.info("open");
            hc.setRequestMethod(HttpConnection.POST);
            logger.info("post");
            hc.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Confirguration/CLDC-1.0");
            logger.info("user agent");
            hc.setRequestProperty("Accept_Language", "en-US");
            logger.info("acept languaje");
            //Content-Type is must to pass parameters in POST Request
            hc.setRequestProperty("Content-Type", "application/json");
            logger.info("conent tyep");
            //hc.setRequestProperty("Connection", "close");
            OutputStream os = hc.openOutputStream();
            logger.info("os ");
            logger.info("procesarEnvio");
            os.write(getCadenajson().getBytes());
            logger.info("terminaEnviodatos");
            logger.info("http " + hc);
            logger.info("http gethost" + hc.getHost());
            //logger.info("http response message" + hc.getResponseMessage());
            //logger.info("http getExpiration" + hc.getExpiration());
            //logger.info("http lastmodified" + hc.getLastModified());
            logger.info("http REsponsecode" + hc.getResponseCode());
            InputStream in = hc.openInputStream();
            logger.info("in");
            logger.info("procesa respuesta");
            procesaRespuestaDatos(hc, in);
            logger.info("termina respuesta");
            //mc.setRespuestaJson(getResp());
            try {
                logger.info("cerramos os");
                os.close();
                logger.info("os cerrado");
            } catch (IOException ex) {
                logger.info("problemas al cerrar os", ex);
            }
            try {
                logger.info("cerramos in");
                in.close();
                logger.info("in cerrado");
            } catch (IOException ex) {
                logger.info("problemas al cerrar in ", ex);
            }

        } catch (IOException ex) {            
            manejador.establecerMensaje("Conexion", "No se Puede Conectar con el SErvicio");
            logger.warn("Error en el sistema", ex);            
        } finally {
            try {
                hc.close();
            } catch (IOException ex) {
                logger.info("problemas al cerrar http");
            }
        }

    }

    /**
     * public void procesarEnvioDatos(HttpConnection hc, OutputStream os) throws
     * IOException{ if(hc.getResponseCode() == HttpConnection.HTTP_OK){
     * logger.info("ok"); logger.info("escribiendo");
     * os.write(cadenajson.getBytes()); logger.info("escribido");
     * logger.info("cerrar"); //os.close(); logger.info("cerrado"); } }
    **
     */
    public synchronized void procesaRespuestaDatos(HttpConnection hc, InputStream is) throws IOException {
        if (hc.getResponseCode() == HttpConnection.HTTP_OK) {
            logger.info("ok");
            /**int tam = (int) hc.getLength();
            logger.info("tamanio " + tam);
            if (tam != -1) {
                logger.info("si tam");
                byte[] datos = new byte[tam];
                logger.info("datos[]");
                is.read(datos, 0, datos.length);
                logger.info("leemos datos " + datos);
                this.resp = new String(datos);
                logger.info("resp = " + resp);
            } else {**/
                logger.info("else");
                StringBuffer sb = new StringBuffer();
                logger.info("string buffer");
                int ch;
                logger.info("ch");
                logger.info("empezamos a leer");
                while ((ch = is.read()) != -1) {
                    sb.append((char) ch);
                }
                logger.info("termino de leer");
                String miresp = new String(sb);
                setResp(miresp);
                logger.info("resp = " + resp);
                logger.info("cerrando");
                // is.close();
                logger.info("cerrado");
           // }

        }        
        else{
            this.resp = null;
        }
        
    }
    
    public synchronized void setResp(String resp) {
        logger.info("setresp Ha entrado el hilo " + Thread.currentThread().getName());
        logger.info("setresp estadoC " + estadoConsulta + resp);
        this.resp = resp;
        estadoConsulta = false;
        notifyAll();
    }

    public synchronized String getResp() {
        logger.info("getresp Ha entrado el hilo " + Thread.currentThread().getName());
        logger.info("getresp estadoC " + estadoConsulta);
        while(estadoConsulta){
            try {
                logger.info("Esperando el hilo " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException ex) {
                logger.info("error al esperar hilo ", ex);
            }
        }
        return this.resp;
    }

    public synchronized JsonObject getRespJsonObject() {
        JsonParser jp = new JsonParser();
        JsonObject jores;
        logger.info("resp jsonobject Ha entrado el hilo " + Thread.currentThread().getName());
        logger.info("resp jsonobject estadoC " + estadoConsulta);
        while(estadoConsulta){
            try {
                logger.info("Esperando el hilo " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException ex) {
                logger.info("error al esperar hilo ", ex);
            }
        }
        try {
            if(getResp() != null){
                this.manejador.mosrarmensa(this.resp);
                this.manejador.mosrarmensa("resp no es nulo");
                logger.info("getRep no es nulo");
                jores = (JsonObject) jp.parseJson(getResp());
                logger.info("objeto parseado " + jores.serializeToString());
            }
            else{
                this.manejador.mosrarmensa("resp es nulo");
                logger.info("getResp por el no");
                jores = null;
            }
            logger.info("jores " + jores);
        } catch (IOException ex) {
            this.manejador.mosrarmensa("exception " + ex.getMessage());
            jores = null;
            logger.info("excepcion en parsejson ", ex);
        } 
        this.resp = null;
        logger.info("REtornando jores ");        
        return jores;
    }
}
