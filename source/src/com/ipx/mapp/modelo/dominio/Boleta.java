/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.dominio;

import de.enough.polish.io.Serializable;
import de.enough.polish.json.JsonArray;
import de.enough.polish.json.JsonObject;
import de.enough.polish.util.ArrayList;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class Boleta implements Serializable{
    private static final transient Logger LOGGER = LoggerFactory.getLogger(Boleta.class);
    private String imeiBoleta;
    private String placaBoleta;
    private String rutaBoleta;
    private String ciBoleta;
    private String tipoBoleta;    
    private Usuario usuarioBoleta;
    private Operador operadorBoleta;
    private ArrayList infraccionesBoleta;
    private String colorBoleta;
    private String operadorBoleta2;
    private String operador2Boleta2;
    private String conductorBoleta;
    private int montoTotalBoleta;
    private int correlativoBoleta;
    private String fechaBoleta;

    public Boleta() {
    }

    public Boleta(JsonObject jo) {
        if(jo!= null){     
          //  LOGGER.info("");
        this.placaBoleta = String.valueOf(jo.get("placa"));
        //LOGGER.info("metemos placaBoleta");
        this.rutaBoleta = String.valueOf(jo.get("ruta"));
        //LOGGER.info("metemos ruta");
        this.ciBoleta = String.valueOf(jo.get("ci"));
       //LOGGER.info("metemos ci"); 
        this.colorBoleta = String.valueOf(jo.get("color"));
       // LOGGER.info("metemos color");
        this.conductorBoleta = String.valueOf(jo.get("conductor"));
        //LOGGER.info("metemos conductor");
        this.correlativoBoleta = Integer.parseInt(String.valueOf(jo.get("correlativo")));
        //LOGGER.info("metemos correlativo");
        this.fechaBoleta = String.valueOf(jo.get("fecha"));
        //LOGGER.info("metemos fecha");
        this.montoTotalBoleta = Integer.parseInt(String.valueOf(jo.get("monto_total")));
        //LOGGER.info("metemos monto total");
        this.operadorBoleta2 = String.valueOf(jo.get("operador"));
        //LOGGER.info("metemos operador");
        this.operador2Boleta2 = String.valueOf(jo.get("operador2"));
        //LOGGER.info("metemos operador 2");
        this.tipoBoleta = String.valueOf(jo.get("tipo"));
        //LOGGER.info("metemos tipo");
        JsonArray ja = (JsonArray) jo.get("infracciones");
        //LOGGER.info("creamos una lista de infracciones vacia");
        this.infraccionesBoleta = new ArrayList();
        for (int i = 0; i < ja.getCount(); i++) {
          //  LOGGER.info("for iteracion " + i);
            JsonObject job = (JsonObject)ja.get(i);
           // LOGGER.info("obtenemos json del array de json");
            Infraccion inf = new Infraccion();
            //LOGGER.info("creamos nuevo objeto infraccion");
            inf.setCodigoInfraccion(String.valueOf(job.get("inf_codigo")));
            //LOGGER.info("metemos inf_codigo ");
            inf.setMonto(String.valueOf(job.get("monto")));
            //LOGGER.info("metemos monto");
            this.infraccionesBoleta.add(inf);
            //LOGGER.info("termina iteracion " + i);            
        }
        //LOGGER.info("termina bucle for");
        }else{
            //LOGGER.info("jo es nulo");
        }
        
    }

    public Boleta(String placaBoleta, String rutaBoleta, String ciBoleta, String tipoBoleta, Usuario usuarioBoleta, Operador operadorBoleta, ArrayList infraccionesBoleta) {
        this.placaBoleta = placaBoleta;
        this.rutaBoleta = rutaBoleta;
        this.ciBoleta = ciBoleta;
        this.tipoBoleta = tipoBoleta;
        this.usuarioBoleta = usuarioBoleta;
        this.operadorBoleta = operadorBoleta;
        this.infraccionesBoleta = infraccionesBoleta;
    }

    public String getImeiBoleta() {
        return imeiBoleta;
    }

    public void setImeiBoleta(String imeiBoleta) {
        this.imeiBoleta = imeiBoleta;
    }

    public String getPlacaBoleta() {
        return placaBoleta;
    }

    public void setPlacaBoleta(String placaBoleta) {
        this.placaBoleta = placaBoleta;
    }

    public String getRutaBoleta() {
        return rutaBoleta;
    }

    public void setRutaBoleta(String rutaBoleta) {
        this.rutaBoleta = rutaBoleta;
    }

    public String getCiBoleta() {
        return ciBoleta;
    }

    public void setCiBoleta(String ciBoleta) {
        this.ciBoleta = ciBoleta;
    }

    public String getTipoBoleta() {
        return tipoBoleta;
    }

    public void setTipoBoleta(String tipoBoleta) {
        this.tipoBoleta = tipoBoleta;
    }

    public Usuario getUsuarioBoleta() {
        return usuarioBoleta;
    }

    public void setUsuarioBoleta(Usuario usuarioBoleta) {
        this.usuarioBoleta = usuarioBoleta;
    }

    public Operador getOperadorBoleta() {
        return operadorBoleta;
    }

    public void setOperadorBoleta(Operador operadorBoleta) {
        this.operadorBoleta = operadorBoleta;
    }

    public ArrayList getInfraccionesBoleta() {
        return infraccionesBoleta;
    }

    public void setInfraccionesBoleta(ArrayList infraccionesBoleta) {
        this.infraccionesBoleta = infraccionesBoleta;
    }

    public String getColorBoleta() {
        return colorBoleta;
    }

    public void setColorBoleta(String colorBoleta) {
        this.colorBoleta = colorBoleta;
    }

    public String getOperadorBoleta2() {
        return operadorBoleta2;
    }

    public void setOperadorBoleta2(String operadorBoleta2) {
        this.operadorBoleta2 = operadorBoleta2;
    }

    public String getOperador2Boleta2() {
        return operador2Boleta2;
    }

    public void setOperador2Boleta2(String operador2Boleta2) {
        this.operador2Boleta2 = operador2Boleta2;
    }

    public String getConductorBoleta() {
        return conductorBoleta;
    }

    public void setConductorBoleta(String conductorBoleta) {
        this.conductorBoleta = conductorBoleta;
    }

    public int getMontoTotalBoleta() {
        return montoTotalBoleta;
    }

    public void setMontoTotalBoleta(int montoTotalBoleta) {
        this.montoTotalBoleta = montoTotalBoleta;
    }

    public int getCorrelativoBoleta() {
        return correlativoBoleta;
    }

    public void setCorrelativoBoleta(int correlativoBoleta) {
        this.correlativoBoleta = correlativoBoleta;
    }

    public String getFechaBoleta() {
        return fechaBoleta;
    }

    public void setFechaBoleta(String fechaBoleta) {
        this.fechaBoleta = fechaBoleta;
    }
    
    public String toJsonRegistro(){
        String respuesta;
        respuesta = "{\"imei\":\"";
        respuesta += getImeiBoleta();
        respuesta += "\",\"placa\":\"";
        respuesta += getPlacaBoleta();
        respuesta += "\",\"ruta\":\"";
        respuesta += getRutaBoleta();
        respuesta += "\",\"ci\":\"";
        respuesta += getCiBoleta();
        respuesta += "\",\"tipo\":\"";
        respuesta += getTipoBoleta();
        respuesta += "\",\"idusuario\":\"";
        respuesta += getUsuarioBoleta().getIdUsuario();
        respuesta += "\",\"infracciones\":[";
        ArrayList auxlist = getInfraccionesBoleta();
        for (int i = 0; i < auxlist.size(); i++) {
            Infraccion inf = (Infraccion)auxlist.get(i);
            if(i>0){
                respuesta += ",";
            }
            respuesta += "{\"inf_codigo\":\"";
            respuesta += inf.getCodigoInfraccion();
            respuesta += "\",\"agravante\":\"";
            respuesta += inf.getAgravante() + "\"}";
        }
        respuesta += "]}";
        return respuesta;
    }
    
}
