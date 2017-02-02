/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.vista;

import de.enough.polish.ui.Form;
import de.enough.polish.ui.TextField;
import de.enough.polish.util.Locale;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class VistaLogueoUsuario extends Form{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VistaLogueoUsuario.class);
    private TextField txfUsuario;
    private TextField txfPassword;
    //private StringItem si;
    
    public VistaLogueoUsuario(){
        super(Locale.get("form.logueo.titulo"));
        
    }

    public void adicionarElementos() {
         //#style itemInput
        txfUsuario = new TextField(Locale.get("form.logueo.user"), "", 30, TextField.ANY);
        txfUsuario.setShowInputInfo(false);
        txfUsuario.setCskOpensNativeEditor(false);
        txfUsuario.setNoNewLine(true);
        txfUsuario.setItemWidth(30);
        
        this.append(txfUsuario);
        //#style itemInput
        txfPassword = new TextField(Locale.get("form.logueo.passwd"), "", 30, TextField.ANY | TextField.PASSWORD);
        txfPassword.setShowInputInfo(false);
        txfPassword.setCskOpensNativeEditor(false);
        txfPassword.setNoNewLine(true);       
        this.append(txfPassword);
        
       // si = new StringItem("Holaundo", null, StringItem.BUTTON);
        //si.setLayout(StringItem.LAYOUT_CENTER | StringItem.LAYOUT_TOP | StringItem.LAYOUT_BOTTOM | StringItem.LAYOUT_VCENTER);
        // //#style itemMainBoton
        //this.append(si);
        
    }   

    public TextField getTxfUsuario() {
        return txfUsuario;
    }

    public void setTxfUsuario(TextField txfUsuario) {
        this.txfUsuario = txfUsuario;
    }

    public TextField getTxfPassword() {
        return txfPassword;
    }

    public void setTxfPassword(TextField txfPassword) {
        this.txfPassword = txfPassword;
    }
    
    public boolean camposVacios(){
        boolean resp = false;
        LOGGER.info("inicia campos vacios");
        if(this.getTxfUsuario().getText().trim().equals("")){
            LOGGER.info("usuario igual a vacio");
            resp = true;
        }
        LOGGER.info("continua al sgte");
        if(this.getTxfPassword().getText().trim().equals("")){
            LOGGER.info("password igual a vacio");
            resp = true;
        }
        
        return resp;
    }
    
}
