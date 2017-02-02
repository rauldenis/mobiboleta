/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.dominio;

import de.enough.polish.io.Serializable;
import de.enough.polish.json.JsonObject;

/**
 *
 * @author silisqui
 */
public class Operador implements Serializable{
    private int idRuta;
    private String ruta;
    private int operadorId;
    private String operador;

    public Operador() {
    }
    
    public Operador(JsonObject jo){
        this.idRuta = Integer.parseInt(String.valueOf(jo.get("id_ruta")));
        this.ruta = String.valueOf(jo.get("ruta"));
        this.operadorId = Integer.parseInt(String.valueOf(jo.get("operador_id")));
        this.operador = String.valueOf(jo.get("operador"));
    }

    public Operador(int idRuta, String ruta, int operadorId, String operador) {
        this.idRuta = idRuta;
        this.ruta = ruta;
        this.operadorId = operadorId;
        this.operador = operador;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getOperadorId() {
        return operadorId;
    }

    public void setOperadorId(int operadorId) {
        this.operadorId = operadorId;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
    
    public String toJsonBuscarOperador(){
        String resp;
        resp = "{\"ruta\":";
        resp += "\"" + getRuta();
        resp += "\"}";
        return resp;
    }
    
}
