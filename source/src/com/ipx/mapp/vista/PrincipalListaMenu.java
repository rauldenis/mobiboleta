/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.mapp.vista;

import de.enough.polish.ui.List;
import de.enough.polish.util.Locale;
import java.io.IOException;
import javax.microedition.lcdui.Image;


/**
 *
 * @author silisqui
 */
public class PrincipalListaMenu extends List{

    public PrincipalListaMenu() {
        //#style screenMainMenu
        super(Locale.get("principal.titulo"), List.IMPLICIT);
    }
    
    public void adicionarEntrada(String nombre){
        try {
            //#style itemMainMenuEntry
            append(nombre, Image.createImage("/Others_32x32.png"));
        } catch (IOException ex) {
            //#style itemMainMenuEntry
            append(nombre, null);
        }
    }

}
