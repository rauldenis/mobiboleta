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
public class Usuario implements Serializable {
    
    private transient static final Logger LOGGER = LoggerFactory.getLogger(Usuario.class);

    private transient String idUsuario;
    private String usuario;
    private transient String password;
    private String prs_nombres;
    private String prs_paterno;
    private String prs_materno;
    private String calle;
    private String zona;
    private ArrayList infracciones;

    public Usuario() {
    }

    public Usuario(JsonObject jsonObject) {
        LOGGER.info("constructor usuario jsonObject");
        if (jsonObject != null) {
            
            LOGGER.info("jsonObject no nul " + jsonObject);
            this.usuario = String.valueOf(jsonObject.get("usuario"));
            LOGGER.info("usuario");
            this.calle = String.valueOf(jsonObject.get("calle"));
            LOGGER.info("calle");
            this.zona = String.valueOf(jsonObject.get("zona"));
            LOGGER.info("zona");
            this.idUsuario = String.valueOf(jsonObject.get("idusuario"));
            LOGGER.info("isuaurio");
            this.prs_nombres = String.valueOf(jsonObject.get("prs_nombres"));
            LOGGER.info("nombres");
            this.prs_paterno = String.valueOf(jsonObject.get("prs_paterno"));
            LOGGER.info("paterno");
            this.prs_materno = String.valueOf(jsonObject.get("prs_materno"));
            LOGGER.info("materno");
            JsonArray jsonArray = (JsonArray) jsonObject.get("infracciones");
            LOGGER.info("infracciones");

            int tamanio = jsonArray.getCount();
            LOGGER.info("obtenemos tamanio del array");
            this.infracciones = new ArrayList();
            LOGGER.info("creamos una lista vacia de infracciones");
            for (int i = 0; i < tamanio; i++) {
                LOGGER.info("for nro " + i);
                JsonObject joAux = (JsonObject) jsonArray.get(i);
                LOGGER.info("obtenemos el jsn");
                Infraccion infraccion = new Infraccion();
                LOGGER.info("creamos objeto infraccion");
                infraccion.setCodigoInfraccion(String.valueOf(joAux.get("inf_codigo")));
                LOGGER.info("obtenemos codinfraccion");
                infracciones.add(infraccion);
                LOGGER.info("adicionamos la infraccion a la lista");
                LOGGER.info("fin del for");
            }
            LOGGER.info("fin de obencion de datos");
        }
        LOGGER.info("fin del constructor");
    }

    public Usuario(String usuario, String password, String calle, String zona, ArrayList infracciones, int identificador, String prs_nombres, String prs_paterno, String prs_materno) {
        this.usuario = usuario;
        this.password = password;
        this.prs_nombres = prs_nombres;
        this.prs_paterno = prs_paterno;
        this.prs_materno = prs_materno;
        this.calle = calle;
        this.zona = zona;
        this.infracciones = infracciones;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrs_nombres() {
        return prs_nombres;
    }

    public void setPrs_nombres(String prs_nombres) {
        this.prs_nombres = prs_nombres;
    }

    public String getPrs_paterno() {
        return prs_paterno;
    }

    public void setPrs_paterno(String prs_paterno) {
        this.prs_paterno = prs_paterno;
    }

    public String getPrs_materno() {
        return prs_materno;
    }

    public void setPrs_materno(String prs_materno) {
        this.prs_materno = prs_materno;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public ArrayList getInfracciones() {
        return infracciones;
    }

    public void setInfracciones(ArrayList infracciones) {
        this.infracciones = infracciones;
    }

    public String toJsonStringLoguin() {
        String resp;
        resp = "{";
        resp += "\"usuario\":" + "\"";
        resp += getUsuario();
        resp += "\",\"password\":\"";
        resp += getPassword();
        resp += "\"}";
        return resp;
    }

    public String getNombreCompleto(){
        String resp;
        resp = getPrs_nombres();
        resp += " " + getPrs_paterno();
        resp += " " + getPrs_materno();
        return resp;
    }
    
}
