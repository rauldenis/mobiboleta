/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.manejador;

import com.ipx.mapp.control.Controlador;
import com.ipx.mapp.modelo.Configuracion;
import com.ipx.mapp.modelo.dominio.Boleta;
import com.ipx.mapp.modelo.dominio.Infraccion;
import com.ipx.mapp.modelo.dominio.Operador;
import com.ipx.mapp.modelo.dominio.Usuario;
import com.nbbse.printer.Printer;
import de.enough.polish.json.JsonObject;
import de.enough.polish.ui.Alert;
import de.enough.polish.util.ArrayList;
import javax.microedition.lcdui.AlertType;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class ManejadorConsultaImpl implements ManejadorConsulta{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ManejadorConsultaImpl.class);
    private Conexion conexion;
    private Controlador controlador;

    public ManejadorConsultaImpl(Controlador controlador) {
       this.conexion = new Conexion();
       this.conexion.setManejador(this);
       this.controlador = controlador;
    }

    public Conexion getConexion() {
        return conexion;
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }
        
    public Usuario autentificarUsuario(Usuario usuario) {
        Usuario uresp = null;
         try{
        LOGGER.info("Empieza auntetificar usuario de manejdaor");
            conexion.setUrlLogueo();            
            conexion.setCadenajson(usuario.toJsonStringLoguin());
            LOGGER.info("datos necesarios");
            LOGGER.info("realizamos la consulta");
           // LOGGER.info("Respuesta de ,etodo conectar" + conexion.conectar());  
            conexion.conectar();
            LOGGER.info("terminanos");
            //JsonParser
            //JsonObject jsonObject = conexion.realizarConsulta();
            this.controlador.mostrarMensaje(conexion.getResp());
            LOGGER.info("respuesta en conexion " + conexion.getResp());
            //uresp = new Usuario();            
            JsonObject jo = conexion.getRespJsonObject();
            
            if(jo != null){
                LOGGER.info("jo no es nulo");
                uresp = new Usuario(jo);
                this.controlador.mostrarMensaje("usuario obtenido");
                LOGGER.info("usuario obtenido");
                LOGGER.info("valor de jsonobject " + jo.serializeToString());
                LOGGER.info("valor de jsonobject prs_nombres" + jo.get("prs_nombres"));
                LOGGER.info("Usuario Autentificado" + uresp);
                LOGGER.info("usuario obtenido" + uresp.getUsuario() + " " +uresp.getPrs_nombres() + " " + uresp.getPrs_paterno());
            }else{
                establecerMensaje("Autentificar", "No existe el usuario " + usuario.getUsuario());
            }
            LOGGER.info("TErmina manejdaor");
        } catch (Exception ex) {
            uresp = null;
            LOGGER.warn("Error al Autentificar al usuario", ex);
            ////#style mailAlert
             //Alert alerta = new Alert("Entrar al Sistema", "Error en el servidor al auntentificar usuario " + usuario.getUsuario(), null, AlertType.INFO);
            this.controlador.mostrarMensaje("por error datos obtenidos " + this.conexion.getResp() );
        }
        return uresp;
    }

    public Boleta registrarInfraccion(Boleta boleta) {
        Boleta bolResp;
        try{
        LOGGER.info("Empieza metodo registrar infraccion");
        
       // boleta.setImeiBoleta(this.);
        LOGGER.info("url a registrar");
        conexion.setUrlRegistrar();
        LOGGER.info("colocamos el texto json a la conexio");
        conexion.setCadenajson(boleta.toJsonRegistro());
        LOGGER.info("a realizar la consulta");
        conexion.conectar();
        LOGGER.info("Termino la consulta");
        LOGGER.info("REspuesta del servidor : " + conexion.getCadenajson());
        JsonObject jo = conexion.getRespJsonObject();
        bolResp = new Boleta(jo);
        LOGGER.info("Valor de jsonobject " + jo.serializeToString());
        LOGGER.info("Termino la consulta");
        
        }catch(Exception ex){
            bolResp = null;
            ////#style mailAlert
            //Alert alerta = new Alert("Registrar", "Error en el servidor al registrar los datos", null, AlertType.INFO);
            //this.controlador.mostrarAlerta(alerta,null);
        }
        
        return bolResp;
    }

    public Operador buscarOperador(Operador operador) {
        Operador oResp;
        try{
        LOGGER.info("Empiesa buscar operador");
        LOGGER.info("url buscaroperador");
        conexion.setUrlBuscaOperador();
        LOGGER.info("colocamos el texto json en la conexion");
        conexion.setCadenajson(operador.toJsonBuscarOperador());
        LOGGER.info("realizamos la consulta");
        conexion.conectar();
        LOGGER.info("termino de realizar la consulta al servidor y recogemos la informacion devuelta");
        LOGGER.info("respuesta del servidor " + conexion.getCadenajson());
        JsonObject jo = conexion.getRespJsonObject();
        oResp = new Operador(jo);
        LOGGER.info("VAlor del objetojson " + jo.serializeToString());
        LOGGER.info("termina buscaroperador");
        }catch(Exception ex){
            oResp = null;
           // //#style mailAlert
           // Alert alerta = new Alert("Buscar Operador", "Error en el servidor al obtener datos del operador", null, AlertType.INFO);
           // this.controlador.mostrarAlerta(alerta,null);
        }
        return oResp;
    }    
    
    public void establecerMensaje(String titulo,String mensaje){
        //#style mailAlert
        Alert alerta = new Alert(titulo, mensaje, null, AlertType.INFO);
        //controlador.mostrarAlerta(alerta);
    }

    public void mosrarmensa(String mensa) {
       this.controlador.mostrarMensaje(mensa);
    }
}
