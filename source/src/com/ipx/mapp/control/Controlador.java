/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.control;

import com.ipx.mapp.IniciaApp;
import com.ipx.mapp.modelo.Configuracion;
import com.ipx.mapp.modelo.dominio.Boleta;
import com.ipx.mapp.modelo.dominio.Infraccion;
import com.ipx.mapp.modelo.dominio.Operador;
import com.ipx.mapp.modelo.dominio.Usuario;
import com.ipx.mapp.modelo.manejador.ManejadorConsulta;
import com.ipx.mapp.modelo.manejador.ManejadorConsultaImpl;
import com.ipx.mapp.modelo.manejador.ManejadorImprimir;
import com.ipx.mapp.modelo.manejador.ManejadorImprimirImpl;
import com.ipx.mapp.vista.PrincipalListaMenu;
import com.ipx.mapp.vista.VistaBuscaOperador;
import com.ipx.mapp.vista.VistaLogueoUsuario;
import com.ipx.mapp.vista.VistaRegistro;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Display;
import de.enough.polish.ui.Displayable;
import de.enough.polish.ui.Form;
import de.enough.polish.ui.Item;
import de.enough.polish.ui.ItemCommandListener;
import de.enough.polish.ui.ItemStateListener;
import de.enough.polish.ui.SimpleScreenHistory;
import de.enough.polish.ui.UiAccess;
import de.enough.polish.ui.splash2.ApplicationInitializer;
import de.enough.polish.ui.splash2.InitializerSplashScreen;
import de.enough.polish.util.ArrayList;
import de.enough.polish.util.Locale;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class Controlador implements ApplicationInitializer, CommandListener, ItemStateListener, ItemCommandListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controlador.class);
    private final IniciaApp iniciaApp;
    public static Display display;
    private Configuracion configuracion;
    private RmsStorage rmsStorage;
    private Command cmdSalir = new Command(Locale.get("cmd.salir"), Command.EXIT, 10);
    private Command cmdVolver = new Command(Locale.get("cmd.volver"), Command.BACK, 1);
    private Command cmdAceptar = new Command(Locale.get("cmd.aceptar"), Command.OK, 3);
    private Command cmdRegistrar = new Command(Locale.get("cmd.registrar"), Command.OK, 2);
    private Command cmdReimprimir = new Command(Locale.get("cmd.reimprimir"), Command.OK, 3);
    //private Command cmdSi = new Command(Locale.get("cmd.si"), Command.OK, 3);
    //private Command cmdNo = new Command(Locale.get("cmd.no"), Command.OK, 3);
    private VistaLogueoUsuario vistaLogueoUsuario;
    private VistaRegistro vistaRegistro;
    private PrincipalListaMenu principalListaMenu;
    private SimpleScreenHistory simpleScreenHistory;
    private VistaBuscaOperador vistaBuscaOperador;
    private ManejadorConsulta mc;
    private ManejadorImprimir mi;
    //private Alert alimp = new Alert("Impresion", "Volver a imprimir Boleta?", null, AlertType.CONFIRMATION);

    public Controlador(IniciaApp iniciaApp) {
        LOGGER.info("constructor controlador");
        this.iniciaApp = iniciaApp;
        this.display = Display.getDisplay(iniciaApp);
        this.simpleScreenHistory = new SimpleScreenHistory(this.display);
    }

    public void appStart() {
        LOGGER.info("appStart Inicia");
        String splashUrl = "/splash.png";
        Image splashImage = null;
        try {
            splashImage = Image.createImage(splashUrl);
        } catch (Exception ex) {
            LOGGER.warn("No se Encuentra la imagen Splash.png", ex);
        }

        int backGroundColor = 0xffffff;
        InitializerSplashScreen initializerSplashScreen = new InitializerSplashScreen(splashImage, backGroundColor, this);
        //initializerSplashScreen.setMessage("Hola esto es una prueba", 100); 
        this.display.setCurrent(initializerSplashScreen);
    }

    public void appPause() {
        LOGGER.info("AppPause iniciando");
    }

    public void appContinue() {
        LOGGER.info("AppContinue iniciando");
    }

    public void initApp() {
        LOGGER.info("InitApp iniciando");
        long iniciaTiempo = System.currentTimeMillis();
        this.rmsStorage = new RmsStorage();
        this.configuracion = cargarConfiguracion();
        //this.principalListaMenu = crearListaMenuPrincipal();
        this.vistaLogueoUsuario = obtenerFormularioLogueo();
       
        long actualTiempo = System.currentTimeMillis();
        if ((actualTiempo - iniciaTiempo) < 2000) {
            try {
                Thread.sleep(1500 - actualTiempo + iniciaTiempo);
            } catch (InterruptedException ex) {
                LOGGER.warn("Problema al crear un Hilo de javame", ex);
            }
        }
        //Printer p = ManejadorImprimirImpl.printer;
        //String uno = p.getIMEI(Printer.SIM1);
        // String dos = p.getIMEI(Printer.SIM2);
//        alimp.setTimeout(Alert.FOREVER);
//        alimp.addCommand(cmdSi);
//        alimp.addCommand(cmdNo);
//        alimp.setCommandListener(this);
        mc = new ManejadorConsultaImpl(this);
        mi = new ManejadorImprimirImpl(this);
        
        if (this.configuracion.getUsuario() != null) {
            this.vistaLogueoUsuario.getTxfUsuario().setText(configuracion.getUsuario().getUsuario());
        }
        
        display.setCurrent(this.vistaLogueoUsuario);        
    }

    public void commandAction(Command c, Displayable d) {
       // this.vistaEspera.iniciaHilo();
        if (c == this.cmdSalir) {
            if (configuracion.isCambios()) {
                //salvarConfiguracion();
            }
            salvarConfiguracion();
            this.iniciaApp.exitMIDlet();
        } else if (this.cmdVolver == c) {
            if (this.simpleScreenHistory.hasPrevious()) {
                this.simpleScreenHistory.showPrevious();
            } else {
                this.display.setCurrent(this.principalListaMenu);
            }

        }
        if (d == this.vistaLogueoUsuario) {
            if (this.cmdAceptar == c) {
                LOGGER.info("entro por si vista logueo");
                if (!this.vistaLogueoUsuario.camposVacios()) {
                    //Alert al = new Alert("Verificando al Usuario", "...", null, AlertType.INFO);                    
                    //iniciaApp.switchDisplayable(null, this.vistaEspera);
                    Usuario aux = new Usuario();
                    Usuario u = null;
                    aux.setUsuario(this.vistaLogueoUsuario.getTxfUsuario().getString());
                    aux.setPassword(this.vistaLogueoUsuario.getTxfPassword().getString());
                    //aux.setUsuario("ruth.callisaya");
                    //aux.setPassword("9225090");
                    LOGGER.info("userjson " + aux.toJsonStringLoguin());
                    //this.iniciaApp.switchDisplayable(null, this.vistaEspera);
                   
                    u = mc.autentificarUsuario(aux);
                    this.vistaLogueoUsuario.append(aux.toJsonStringLoguin());
                    //u = configuracion.getUsuario();
                    if (u != null) {
                        LOGGER.info("por si");
                        LOGGER.info("Colocamos usuario en configuracion");
                        configuracion.setUsuario(u);
                        this.vistaBuscaOperador = obtenerFormularioBuscaOperador();
                        this.vistaRegistro = obtenerFormularioRegistro();
                        //#style mailAlert
                        Alert ale2 = new Alert("Ingresar", "Bienvenido al Programa!!!", null, AlertType.INFO);
                        ale2.setTimeout(1500);
                        mostrarAlerta(ale2, this.vistaBuscaOperador);
                        //this.display.setCurrent(this.vistaBuscaOperador);
                        habilitarReimpresion();
                    } else {
                        //#style mailAlert
                        Alert ale = new Alert("Ingresar", "No existe el Usuario.", null, AlertType.INFO);
                        mostrarAlerta(ale, this.vistaLogueoUsuario);
                    }
                } else {
                    Image img;
                    try {
                        img = Image.createImage(getClass().getResourceAsStream("/alertsign.PNG"));
                    } catch (IOException ex) {
                        LOGGER.warn("Error al cargar la imagen", ex);
                        img = null;
                    }
                    //#style mailAlert
                    Alert alert = new Alert("INGRESO", "Los Campos no deben estar vacios!!!", img, AlertType.WARNING);
                    mostrarAlerta(alert,this.vistaLogueoUsuario);
                }

            } else {
                LOGGER.info("Por el else en aceptar logueo");
            }

        } else if (d.equals(this.vistaRegistro)) {
            LOGGER.info("Por el si aceptar vista registro");
            if (c.equals(this.cmdRegistrar)) {
                if (!this.vistaRegistro.camposVacios()) {
                    Boleta boleta = new Boleta();
                    boleta.setPlacaBoleta(this.vistaRegistro.getTxfPlaca().getText());
                    boleta.setCiBoleta(this.vistaRegistro.getTxfCi().getText());
                    boleta.setRutaBoleta(this.vistaRegistro.getTxfRuta().getText());
                    boleta.setTipoBoleta(this.vistaRegistro.getTxfTransporte().getText());
                    boleta.setUsuarioBoleta(this.configuracion.getUsuario());
                    ArrayList lista = this.vistaRegistro.getDatos(), listaux = new ArrayList();
                    boleta.setOperadorBoleta(this.configuracion.getOperador());
                    LOGGER.info("Empieza la creacion de la lista de infracciones de la boleta");

                    for (int i = 0; i < lista.size(); i++) {
                        LOGGER.info("for " + i);
                        if (this.vistaRegistro.getFcgInfracciones().isSelected(i)) {
                            LOGGER.info("si esta seleccionado");
                            Infraccion infrac = (Infraccion) lista.get(i);
                            LOGGER.info("Infraccion " + infrac.getCodigoInfraccion());
                            infrac.setAgravante("0");                            
                            listaux.add(infrac);
                            LOGGER.info("infraccion adicionada");
                            
                        }
                    }
                    LOGGER.info("termina la creacion del array list de infracciones");
                    boleta.setInfraccionesBoleta(listaux);
                    LOGGER.info("adicionamos las infracciones");
                    boleta.setImeiBoleta(this.mi.getImei());
                    LOGGER.info("adicionamos el imei del dispositivo");
                    Boleta resp;
                    LOGGER.info("realizamos la consulta");
                    resp = this.mc.registrarInfraccion(boleta);
                    LOGGER.info("boleta " + resp);
                    //resp.setBanderaImpresiones(2);
                    LOGGER.info("colocamos la bandera de boleta a 2");                   
                    if(resp != null){
                        try{
                            resp.setUsuarioBoleta(this.configuracion.getUsuario());
                            resp.setOperadorBoleta(this.configuracion.getOperador());
                            this.configuracion.setBoletaImprimir(resp);
                        LOGGER.info("entrams por el si");
                        this.configuracion.setNumImpreso(2);
                        //resp = this.configuracion.getBoletaImprimir();
                        mostrarMensaje(resp.getInfraccionesBoleta().size() +" ");
                        LOGGER.info("mostramos el mensaje");
                        salvarConfiguracion();
                        mostrarMensaje("ahora vamos a imprimir");
                        
                        this.mi.imprimirBoleta(resp);
                        LOGGER.info("mostramos la alerta");
                        ////#style mailAlert
                        //Alert ale = new Alert("Impresion", "Boleta Registrada exitosamente.\n Se Se Procedera a Imprimir!!!", null, AlertType.INFO);
                        //ale.setTimeout(2000);
                        //mostrarAlerta(ale, this.vistaRegistro);
                        habilitarReimpresion();
                        }catch(Exception ex){
                            LOGGER.info("error en no se donde ",ex);
                        }
                       // this.mi.imprimirBoleta(resp);
                    }else{
                        LOGGER.info("resp == null");
                        LOGGER.info("mostramos la alerta");
                        //#style mailAlert
                        Alert ale = new Alert("Impresion", "Boleta Registrada exitosamente.\n Se Se Procedera a Imprimir!!!", null, AlertType.INFO);
                        ale.setTimeout(2000);
                        mostrarAlerta(ale, this.vistaRegistro);
                        habilitarReimpresion();

                    }
                }
            } else {
                LOGGER.info("no es aceptar por el no vista registro");
            }

        } else if (d.equals(this.vistaBuscaOperador)) {
            if (c.equals(this.cmdAceptar)) {
                String ruta = this.vistaBuscaOperador.getTxfOperador().getText();

                if (!ruta.trim().equals("")) {
                    Operador o = new Operador();
                    o.setRuta(ruta);
                    Operador o2 = mc.buscarOperador(o);

                    if (o2 != null) {
                        LOGGER.info("Por el si aceptar vista Buscar operador");
                        LOGGER.info("metemos los datos de infraciones");                        
                        this.configuracion.setOperador(o2); 
                        this.vistaRegistro.getTxfRuta().setString(o2.getRuta());
                        Usuario u = this.configuracion.getUsuario();
                        this.vistaRegistro.append("usuario : " + u.getPrs_nombres() + " " + u.getPrs_paterno() + " " + u.getPrs_materno());
                        this.vistaRegistro.getTxfCi().setString("1280900");
                        this.vistaRegistro.getTxfPlaca().setString("2616LHH");
                        this.vistaRegistro.getTxfRuta().setString("M");
                        this.vistaRegistro.getTxfTransporte().setString("transporte");
                        display.setCurrent(this.vistaRegistro);
                        habilitarReimpresion();
                        LOGGER.info("termina manejador");
                    }

                }

            } else {
                LOGGER.info("no es aceptar por el no vista Buscar Operador");
            }
        }
        if(c.equals(cmdReimprimir)){
            Boleta boletaReimp = this.configuracion.getBoletaImprimir();
            this.mi.imprimirBoleta(boletaReimp);
        }
                LOGGER.info(c.getLabel() + " - " + c.toString() + " displyable " + d.getTitle() + " - " + d.toString());
    }

    private Configuracion cargarConfiguracion() {
        Configuracion mr;
         try {
            LOGGER.info("por el try cargarconfig");
            LOGGER.info("leendo el usuario guardado");
            mr = (Configuracion) this.rmsStorage.read("configuracion");
            LOGGER.info("Configuracion leida");
            if (mr == null) {
                mr = new Configuracion();
                LOGGER.info("usuario almacenado en config");
            }
        } catch (Exception ex) {
            mr = new Configuracion();
            LOGGER.warn("Error al cargar configuraciones", ex);
        }
        LOGGER.info("devolviendo la configuracion");
        return mr;
    }

    public void salvarConfiguracion() {
        try {
            LOGGER.info("trye salvarconfig");
            if (this.configuracion != null) {
                LOGGER.info("por si usuario no nulo");
                this.rmsStorage.save(this.configuracion, "configuracion");
                LOGGER.info("usuario recuperado");
            }

        } catch (IOException ex) {
            LOGGER.warn("Error al guardar usuario en la configuracion", ex);
            LOGGER.info("termina el metodo mal");
        }
    }
    
    public boolean impresionHecha(){
        int numero = this.configuracion.getNumImpreso();
        numero--;
        this.configuracion.setNumImpreso(numero);
        if(numero == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * private PrincipalListaMenu crearListaMenuPrincipal() { PrincipalListaMenu
     * listaMenu = new PrincipalListaMenu(); listaMenu.setCommandListener(this);
     * listaMenu.addCommand(this.cmdSalir);
     * listaMenu.addCommand(this.cmdVolver);
     * listaMenu.adicionarEntrada("Registrar Infraccion"); return listaMenu; }*
     */
    private VistaLogueoUsuario obtenerFormularioLogueo() {
        VistaLogueoUsuario vlu = new VistaLogueoUsuario();
        vlu.adicionarElementos();
        vlu.addCommand(this.cmdAceptar);
        vlu.addCommand(this.cmdSalir);
        vlu.setCommandListener(this);
        vlu.setItemStateListener(this);
        vlu.getTxfUsuario().setString("ruth.callisaya");
        vlu.getTxfPassword().setString("9225090");
        return vlu;
    }
    
    private VistaBuscaOperador obtenerFormularioBuscaOperador(){
        VistaBuscaOperador vbo = new VistaBuscaOperador(display);        
                        vbo.iniciaCoponentes();                       
                        vbo.addCommand(this.cmdSalir);
                        vbo.addCommand(this.cmdAceptar);
                        vbo.addCommand(cmdReimprimir);
                        vbo.setCommandListener(this);
                        vbo.getTxfOperador().setString("M");
                        return vbo;
    }
    
    private VistaRegistro obtenerFormularioRegistro(){
        VistaRegistro vr = new VistaRegistro(display);
                        LOGGER.info("metemos los datos de infraciones");
                        Usuario u = this.configuracion.getUsuario();
                        vr.setDatos(u.getInfracciones());
                        LOGGER.info("colocamos el itemcommandlistener");
                        vr.setClPrincipal(this);
                        LOGGER.info("le asignamo el command listener");
                        vr.setCommandListener(this);
                        
                        //configuracion.setUsuario(u);
                        LOGGER.info("llamamos al metodo inicializacampos");
                        vr.inicializaCampos();
                        LOGGER.info("visualizamos la pantalla");
                        vr.addCommand(this.cmdSalir);
                        vr.addCommand(this.cmdRegistrar);
                        vr.addCommand(cmdReimprimir);
                        
                        return vr;
    }

    public void itemStateChanged(Item item) {
        LOGGER.info("Item State Changed " + item.getLabel() + " - " + item.getLabelItem().getLabel() + " - " + item.toString());
    }

    public void commandAction(Command c, Item item) {
        LOGGER.info("comando " + c.getLabel() + " item " + item.getLabel());
    }

    public void mostrarMensaje(String mens) {
        //this.vistaRegistro.append(mens);
        ((Form)this.display.getCurrent()).append(mens);
    }

    public void mostrarAlerta(Alert alert, Displayable displayable) {
        LOGGER.info("terminamos hilo");
        if(displayable != null){
            LOGGER.info("hay una vista anteriro por el si");
            iniciaApp.switchDisplayable(alert, displayable);
        }else{
            Displayable dis = this.display.getCurrent();
            LOGGER.info("hay una vista anterior Por el no");
            iniciaApp.switchDisplayable(alert, dis);
        }        
        
        LOGGER.info("Termina metodo de mostrar alerta");
    }

    public void mostrarAlerta(String strAlerta) {
        mostrarAlerta("Iformacion", strAlerta);
    }
    
    public void mostrarAlerta(String titulo, String strAlerta){        
        mostrarAlerta(titulo, strAlerta, null);
    }
    
    public void mostrarAlerta(String titulo, String strAlerta, Displayable displayable){
        //#style mailAlert
        Alert alerta = new Alert(titulo, strAlerta, null, AlertType.INFO);
        if(displayable == null){
            Displayable dis = display.getCurrent();
            display.setCurrent(alerta,dis);
        }else{
            this.iniciaApp.switchDisplayable(alerta, displayable);
        }
    }
    
    public void habilitarReimpresion(){
        if(this.configuracion.getNumImpreso() > 0){
            UiAccess.setAccessible( (Form)(display.getCurrent()), cmdReimprimir, true);
        }else{
            UiAccess.setAccessible( (Form)(display.getCurrent()), cmdReimprimir, false);
        }        
    }
    
}