/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.vista;

import de.enough.polish.ui.Display;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.TextField;

/**
 *
 * @author silisqui
 */
public class VistaBuscaOperador extends Form{
    
    private Display display;
    
    private TextField txfOperador;

    public TextField getTxfOperador() {
        return txfOperador;
    }

    public void setTxfOperador(TextField txfOperador) {
        this.txfOperador = txfOperador;
    }

    public VistaBuscaOperador(Display display) {
        super("Buscar Operador");
        this.display = display;
    }
    
    public void iniciaCoponentes(){
        //#style itemInput
        this.txfOperador = new TextField("RUTA: ", "", 30, TextField.ANY);
        this.append(this.txfOperador);        
    }
    
}
