/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.vista;

import com.ipx.mapp.modelo.dominio.Infraccion;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.FilteredChoiceGroup;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.ItemCommandListener;
import de.enough.polish.ui.TextField;
import de.enough.polish.util.ArrayList;
import de.enough.polish.util.Locale;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class VistaRegistro extends Form{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VistaRegistro.class);
    
    private Display display;
    private TextField txfPlaca;
    private TextField txfCi;
    private TextField txfRuta;
    private TextField txfTransporte;
    private FilteredChoiceGroup fcgInfracciones;
    private ItemCommandListener clPrincipal;
    private Command cmdchoice = new Command(Locale.get("cmd.aceptar"), Command.OK, 2);
    
    private ArrayList infracciones;
    

    public VistaRegistro(Display display) {        
        super("Resgistro de Infraccion");
        this.display = display;
    }

    public TextField getTxfPlaca() {
        return txfPlaca;
    }

    public void setTxfPlaca(TextField txfPlaca) {
        this.txfPlaca = txfPlaca;
    }

    public TextField getTxfCi() {
        return txfCi;
    }

    public void setTxfCi(TextField txfCi) {
        this.txfCi = txfCi;
    }

    public TextField getTxfRuta() {
        return txfRuta;
    }

    public void setTxfRuta(TextField txfRuta) {
        this.txfRuta = txfRuta;
    }

    public TextField getTxfTransporte() {
        return txfTransporte;
    }

    public void setTxfTransporte(TextField txfTransporte) {
        this.txfTransporte = txfTransporte;
    }

    public FilteredChoiceGroup getFcgInfracciones() {
        return fcgInfracciones;
    }

    public void setFcgInfracciones(FilteredChoiceGroup fcgInfracciones) {
        this.fcgInfracciones = fcgInfracciones;
    }

    public void setClPrincipal(ItemCommandListener clPrincipal) {
        this.clPrincipal = clPrincipal;
    }
    
    public void setDatos(ArrayList infracciones){
        this.infracciones = infracciones;
    }
    
    public ArrayList getDatos(){
        return this.infracciones;
    }
    
    public void inicializaCampos(){
        //#style itemInput
        this.txfPlaca = new TextField("PLACA: ", "", 30, TextField.ANY);
        this.append(this.txfPlaca);
        //#style itemInput
        this.txfCi = new TextField("CI: ", "", 30, TextField.NUMERIC);
        this.append(this.txfCi);
        //#style itemInput
        this.txfRuta = new TextField("RUTA: ", "", 30, TextField.ANY);
        this.append(this.txfRuta);
        //#style itemInput
        this.txfTransporte = new TextField("TIPO: ", "", 30, TextField.ANY);
        this.append(this.txfTransporte);
        //#style from
        this.fcgInfracciones = new FilteredChoiceGroup("INFRACCIONES: ", "Seleccione ... ", FilteredChoiceGroup.MULTIPLE);
        this.fcgInfracciones.addCommand(cmdchoice);
        this.fcgInfracciones.setItemCommandListener(this.clPrincipal);
        for (int i = 0; i < this.infracciones.size(); i++) {
            Infraccion infraccion = (Infraccion)this.infracciones.get(i);
            String nvalor = infraccion.getCodigoInfraccion();
            //#style seleccion
            this.fcgInfracciones.append(nvalor, null);            
        }        
        this.append(fcgInfracciones);
    }
    
    public boolean camposVacios(){
        String splaca = getTxfPlaca().getString().trim();
        String sci = getTxfCi().getString().trim();
        String sruta = getTxfRuta().getText().trim();
        String stransporte = getTxfTransporte().getText().trim();
        if(splaca.equals("")){
            return true;
        }else if(sci.equals("")){
            return true;
        }else if(sruta.equals("")){
            return true;
        }else if(stransporte.equals("")){
            return true;
        }
        else{
            boolean resp = true;
            int tam = this.infracciones.size();
            for (int i = 0; i < tam; i++) {
                if(this.fcgInfracciones.isSelected(i)){
                    resp = false;
                    break;
                }
                
            }
            return resp;
            
        }

    }
}