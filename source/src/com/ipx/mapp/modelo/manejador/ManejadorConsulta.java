/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.manejador;

import com.ipx.mapp.modelo.dominio.Boleta;
import com.ipx.mapp.modelo.dominio.Operador;
import com.ipx.mapp.modelo.dominio.Usuario;

/**
 *
 * @author silisqui
 */
public interface ManejadorConsulta extends Manejador{
    
    public Usuario autentificarUsuario(Usuario usuario);
    public Boleta registrarInfraccion(Boleta boleta);
    public Operador buscarOperador(Operador operador);
    
}
