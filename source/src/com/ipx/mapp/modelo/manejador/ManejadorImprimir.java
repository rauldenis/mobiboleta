/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.manejador;

import com.ipx.mapp.modelo.dominio.Boleta;
import java.io.IOException;

/**
 *
 * @author silisqui
 */
public interface ManejadorImprimir extends Manejador{
    
    public void imprimirBoleta(final Boleta boleta);
    public void imprimirImagen(String dirImagen);
    public boolean hayPapel();
    public String getImei();
    //public void imprimir() throws IOException;
    //public int ChangeInt(byte[] bi, int start);
    //public byte[] readImage(String imagePath);
    
}
