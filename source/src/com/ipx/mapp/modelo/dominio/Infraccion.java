/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.dominio;

import de.enough.polish.io.Serializable;

/**
 *
 * @author silisqui
 */
public class Infraccion implements Serializable{
        
    private String codigoInfraccion;
    private String agravante = "0";
    private String monto;

    public Infraccion() {
    }
    
    public Infraccion(String codigoInfraccion) {
        this.codigoInfraccion = codigoInfraccion;
    }

    public String getCodigoInfraccion() {
        return codigoInfraccion;
    }

    public void setCodigoInfraccion(String codigoInfraccion) {
        this.codigoInfraccion = codigoInfraccion;
    }

    public String getAgravante() {
        return agravante;
    }

    public void setAgravante(String agravante) {
        this.agravante = agravante;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
    
}
