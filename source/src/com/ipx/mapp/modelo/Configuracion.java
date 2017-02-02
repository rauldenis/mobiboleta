/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo;

import com.ipx.mapp.modelo.dominio.Boleta;
import com.ipx.mapp.modelo.dominio.Operador;
import com.ipx.mapp.modelo.dominio.Usuario;
import de.enough.polish.io.Serializable;

/**
 *
 * @author silisqui
 */
public class Configuracion implements Serializable {

    // private static final Logger LOGGER = LoggerFactory.getLogger(Configuracion.class);
    private boolean cambios;
    private Usuario usuario;
    private Boleta boletaImprimir;
    private Operador operador;
    private String strImei;
    private int numImpreso;

    public Configuracion() {
        this.boletaImprimir = null;
        this.usuario = null;
        this.operador = null;
        this.numImpreso = 0;
    }

    public boolean isCambios() {
        return cambios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.cambios = true;
    }

    public Boleta getBoletaImprimir() {
        return boletaImprimir;
    }

    public void setBoletaImprimir(Boleta boletaImprimir) {
        this.boletaImprimir = boletaImprimir;
        this.cambios = true;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
        this.cambios = true;
    }

    public String getStrImei() {
        return strImei;
    }

    public void setStrImei(String strImei) {
        this.strImei = strImei;
    }

    public int getNumImpreso() {
        return numImpreso;
    }

    public void setNumImpreso(int numImpreso) {
        this.numImpreso = numImpreso;
    }
}
