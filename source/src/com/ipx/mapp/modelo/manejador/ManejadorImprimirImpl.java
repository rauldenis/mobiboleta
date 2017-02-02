/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.mapp.modelo.manejador;

import com.ipx.mapp.control.Controlador;
import com.ipx.mapp.modelo.dominio.Boleta;
import com.ipx.mapp.modelo.dominio.Infraccion;
import com.nbbse.printer.Printer;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.Form;
import de.enough.polish.util.ArrayList;
import de.enough.polish.util.Locale;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.AlertType;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 *
 * @author silisqui
 */
public class ManejadorImprimirImpl implements ManejadorImprimir {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManejadorImprimirImpl.class);
    private static final int TIPO_IMAGEN = 1;
    int size;
    int BMPDataOffset;
    int BMPHeaderSize;
    int height;
    int width;
    int DataSize;
    int[] RGBQUAD = null;
    byte[] image;
    //public static Printer printer;
    byte[] image_bytes;
    public static Printer printer = Printer.getInstance();
    private final Controlador controlador;

    public ManejadorImprimirImpl(Controlador controlador) {
        this.controlador = controlador;
    }

    public void imprimirBoleta(Boleta boleta) {
        //printer.powerOff();
        /*Thread h = new Thread(new Runnable() {

            public void run() {*/
                LOGGER.info("inicia imprimir");
        controlador.mostrarMensaje("ini imp");
        LOGGER.info("imprimimos la imagen");
        controlador.mostrarMensaje("ini bandera");
        int rep = 2;
        controlador.mostrarMensaje("ini for");
        
        for (int i = 1; i <= rep; i++) {
            try{
            controlador.mostrarMensaje("imp img");
            imprimir(Locale.get("impresion.logoimagen"), 1, 0);
                controlador.mostrarMensaje("Logo imagen");
            //imprimir(Locale.get("impresion.letraimagen"),1,0);
            //imprimir("    GOBIERNO AUTONOMO",2,2);
            //imprimir("    MUNICIPAL DE LA PAZ", 2, 2);

                imprimir(Locale.get("impresion.imagenlinea"), 1, 0);
                controlador.mostrarMensaje("imagen linea");

                imprimir("       MEMORANDUM DE INFRACCION", 2, 1);
                controlador.mostrarMensaje("memorandum");
                imprimir("Nro. " + boleta.getCorrelativoBoleta() + " ", 2, 2);
                controlador.mostrarMensaje("mro correlativo");
                imprimir(boleta.getOperador2Boleta2(), 2, 1);
                controlador.mostrarMensaje("imprimir operador");
                imprimir("Fecha:" + boleta.getFechaBoleta(), 2, 1);
                controlador.mostrarMensaje("FEcha boleta");
                imprimir(Locale.get("impresion.imagenlinea"), 1, 0);
                controlador.mostrarMensaje("Linea ");
                imprimir(boleta.getPlacaBoleta(), 2, 2);
                controlador.mostrarMensaje("Placa boleta");
                String todo = boleta.getTipoBoleta() + " " + boleta.getColorBoleta();
                imprimir(todo, 2, 1);
                controlador.mostrarMensaje("tipo y color boleta");
                imprimir(Locale.get("impresion.imagenlinea"), 1, 0);
                controlador.mostrarMensaje("linea");
                imprimir("OPERADOR: " + boleta.getOperadorBoleta2(), 2, 1);
                controlador.mostrarMensaje("operador boleta");
                imprimir("RUTA: " + boleta.getRutaBoleta(), 2, 1);
                controlador.mostrarMensaje("ruta boleta");
                imprimir(Locale.get("impresion.imagenlinea"), 1, 0);
                controlador.mostrarMensaje("imagen");
                imprimir("CONDUCTOR: " + boleta.getConductorBoleta(), 2, 1);
                controlador.mostrarMensaje("Conductor");
                imprimir("CI: " + boleta.getCiBoleta(), 2, 1);
                controlador.mostrarMensaje("Ci del conductor");
                imprimir(Locale.get("impresion.imagenlinea"), 1, 0);
                controlador.mostrarMensaje("imagen linea");
                imprimir("          INFRACCIONES", 2, 1);
controlador.mostrarMensaje("empieza las infracciiones");

            ArrayList laux = boleta.getInfraccionesBoleta();
            for (int j = 0; j < laux.size(); j++) {

                    Infraccion infAux = (Infraccion) laux.get(j);
                    String auximp = "  " + infAux.getCodigoInfraccion();
                    auximp += " " + infAux.getMonto();
                    imprimir(auximp, 2, 1);

            }
controlador.mostrarMensaje("termina las infracciones");
                imprimir("  TOTAL: " + boleta.getMontoTotalBoleta(), 2, 1);
                controlador.mostrarMensaje("monto total boleta");
            String nomUser = boleta.getUsuarioBoleta().getNombreCompleto();
                imprimir("GMT: " + nomUser, 2, 1);
controlador.mostrarMensaje("usuario");
            controlador.mostrarMensaje("termina");
            //imprimir(boleta,2,1);
            printer.printEndLine();
            //if (ban1) {
            //boleta.setBanderaImpresiones(rep - i);
            if(this.controlador.impresionHecha()){
                break;
            }
            
            //} else {
            //    break reanudar;
            //}
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                LOGGER.warn("Error al dormir el proceso", ex);
            }
        }catch(IOException ex){
            this.controlador.salvarConfiguracion();
            this.controlador.mostrarMensaje(ex.getMessage() + " --- " + ex.toString());
            break;
        } 
        }
           /* }
        });
        h.start();
        */
        this.controlador.habilitarReimpresion();
    }

    private void imprimir(Object imprime, int tipoimp, int tamanioFuente) throws IOException{
        controlador.mostrarMensaje(" estado de la imp");
        controlador.mostrarMensaje(" es : " + printer.getPrinterStatus());
        switch (printer.getPrinterStatus()) {
            case Printer.PRINTER_INITING:
                LOGGER.info("Estado de iniciando en la impresora");
                controlador.mostrarMensaje("Estado de la impresora initing");
                try {
                    controlador.mostrarMensaje("Esperamos 300 mls en initing");
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    controlador.mostrarMensaje("error al esperar 300 mls en initing");
                    LOGGER.warn("Error al dormir el proceso", ex);
                }
                imprimir(imprime, tipoimp, tamanioFuente);
                break;
            case Printer.PRINTER_PRINTING:
                controlador.mostrarMensaje("estado de la impresora printing");
                LOGGER.info("Estado de imprimiendo en la impresora");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    LOGGER.warn("Error al dormir el proceso", ex);
                }
                imprimir(imprime, tipoimp, tamanioFuente);
                break;
            case Printer.PRINTER_READY:
                controlador.mostrarMensaje("estado de la impresora ready");
                LOGGER.info("Estado de listo en la impresora");
                if (printer.voltageCheck()) {
                    if (hayPapel()) {
                        if (tipoimp == TIPO_IMAGEN) {
                            imprimirImagen(String.valueOf(imprime));
                        } else {
                            printer.printText(String.valueOf(imprime), tamanioFuente);
                        }
                    }
                    else{
                        this.controlador.habilitarReimpresion();
                        throw new IOException();
                    }
                } else {
                    //ban2 = false;
                    //ban1 = false;
                    this.controlador.salvarConfiguracion();
                    //#style mailAlert
                    Alert a1 = new Alert("Dispositivo", Locale.get("impresion.mensaje.sinvoltage"), null, AlertType.INFO);
                    this.controlador.mostrarAlerta(a1,null);                    
                    printer.powerOff();
                }
                break;
            case Printer.PRINTER_ERROR:
                //ban2 = false;
                //ban1 = false;
                
                //#style mailAlert
                Alert a = new Alert("Impresion", "Error en el Dispositivo de impresion.", null, AlertType.INFO);
                this.controlador.mostrarAlerta(a,null);
                
                //controlador.mostrarMensaje("estado de la impresora error");
                LOGGER.info("Estado de error en la impresora");
                throw new IOException("");
               // break;
        }

    }

    public void imprimirImagen(String dirImagen) {
        controlador.mostrarMensaje("imprime imagen metodo impimg");

        LOGGER.info("aleer imagen");
        controlador.mostrarMensaje("blee img");
        byte[] imgb = readImage(dirImagen);
        LOGGER.info("terminamos de leer la imagen");
        LOGGER.info("imagen leida " + imgb);
        controlador.mostrarMensaje("imprime");
        printer.printBitmap(imgb);

    }

    public boolean hayPapel() {
        Alert alerta;
        try {
            switch (printer.getPaperStatus()) {
                case Printer.PRINTER_EXIST_PAPER:
                    controlador.mostrarMensaje("Existe papel");
                    return true;
                case Printer.PRINTER_NO_PAPER:
                    //ban1 = false;
                    this.controlador.habilitarReimpresion();
                    //#style mailAlert
                    alerta = new Alert("Impresion", "Coloque nuevo papel. \n Imprima Nuevamente", null, AlertType.INFO);
                    
                    this.controlador.mostrarAlerta(alerta,null);
                    
                    //this.controlador.mostrarMensaje("no hay papel");
                    //throw new IOException();
                    return false;
                case Printer.PRINTER_PAPER_ERROR:
                    //ban2 = false;
                    //ban1 = false;
                    this.controlador.habilitarReimpresion();
                    //#style mailAlert
                    alerta = new Alert("Impresion", "Verifique el papel. \n Imprima Nuevamente", null, AlertType.INFO);
                    this.controlador.mostrarAlerta(alerta,null);
                    //controlador.mostrarMensaje("error de papel");
                   // throw new IOException();
                    return false;
                default:
                    return false;
            }
        } catch (Exception ex) {
            return false;
        }
        //return true;

    }

    //convert to int
    private int ChangeInt(byte[] bi, int start) {
        return ((bi[start] & 0xff) << 24) | ((bi[start - 1] & 0xff) << 16)
                | ((bi[start - 2] & 0xff) << 8) | bi[start - 3] & 0xff;
    }

    /**
     * a method used to read image from BMP format and return a byte array
     *
     * @param imagePath image path
     * @param typeNum (IMAGE_NORMAL for normal/IMAGE_STRING_ADD for adding
     * string)
     * @return byte[] of image
     */
    private byte[] readImage(String imagePath) {
        try {
            InputStream is = getClass().getResourceAsStream(imagePath);
            DataInputStream dis = null;
            dis = new DataInputStream(is);

            int bflen = 14;
            byte bf[] = new byte[bflen];
            dis.read(bf, 0, bflen); // read 14 bytes BMP file header

            BMPDataOffset = ChangeInt(bf, 13); // BMP data shifting info

            int bilen = 40;
            byte bi[] = new byte[bilen];
            dis.read(bi, 0, bilen);//read 40 bytes BMP info header

            // get width and height
            width = ChangeInt(bi, 7); // width of image

            height = ChangeInt(bi, 11); // height of image
            if (width > 384) {
                throw new Exception("width is beyond the range (<=384)");
            }
            /*
             * bytes array of image "4" for the width(2 bit) and height(2 bit)
             * to API
             */
            image_bytes = new byte[4 + width * height / 8];

            // bit depth
            int nbitcount = ((bi[15] & 0xff) << 8) | bi[14] & 0xff;

            // ****begin
            image = new byte[width * height];
            int plate = 0;
            switch (nbitcount) {
                case 1:
                    return null;
                case 8:
                    plate = (BMPDataOffset - 54) / 4;
                    DataSize = (size - BMPDataOffset);
                    if (plate == 0) {
                        // for (int i = height - 1; i >= 0; i--) {
                        // for (int j = 0; j < width; j++) {
                        // image[i * width + j] = RGBQUAD[(dis.readByte() & 0xff)];
                        // }
                        // }
                    } else {
                        RGBQUAD = new int[plate];
                        for (int i = 0; i < plate; i++) {
                            RGBQUAD[i] = ((dis.readByte() & 0xff)
                                    | (dis.readByte() & 0xff) << 8
                                    | (dis.readByte() & 0xff) << 16 | (dis
                                    .readByte() & 0xff) << 24);
                        }

                        int dataArrayLen = width * height;
                        byte[] imageData = new byte[dataArrayLen];
                        dis.read(imageData, 0, dataArrayLen);
                        int nArray = 0;
                        for (int i = height - 1; i >= 0; i--) {
                            for (int j = 0; j < width; j++) {
                                /*
                                 * because of black and white BMP, "==0" is easy to
                                 * judge
                                 */
                                if (RGBQUAD[imageData[nArray++] & 0xff] == 0) {
                                    image[i * width + j] = 1; // 1 for print black
                                    // dot
                                } else {
                                    image[i * width + j] = 0; // 0 for print white
                                    // dot
                                }
                            }
                        }
                        /*
                         * image_bytes[0] width low (value = width / 8)( 0 < value
                         * <255) image_bytes[1] width high image_bytes[2] height low
                         * (value = height ) ( 0 < value <255) image_bytes[3] height
                         * high
                         */
                        image_bytes[0] = (byte) (width / 8);
                        image_bytes[1] = 0;
                        if (height > 255) {
                            image_bytes[2] = (byte) (height - 255);// (byte) height;
                            // // high
                            image_bytes[3] = 1; // high
                        } else {
                            image_bytes[2] = (byte) (height);// (byte) height; //
                            // high
                            image_bytes[3] = 0; // high
                        }
                        /*
                         * 8 bit for one byte to print
                         */
                        for (int n = 0; n < width * height / 8; n++) {
                            image_bytes[4 + n] = (byte) ((byte) (image[8 * n + 0] & 0x1) << 7
                                    | (byte) (image[8 * n + 1] & 0x1) << 6
                                    | (byte) (image[8 * n + 2] & 0x1) << 5
                                    | (byte) (image[8 * n + 3] & 0x1) << 4
                                    | (byte) (image[8 * n + 4] & 0x1) << 3
                                    | (byte) (image[8 * n + 5] & 0x1) << 2
                                    | (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
                        }
                        return image_bytes;
                    }
                    break;
                case 24:
                    int dataArrayLen = width * height * 3;
                    byte[] imageData = new byte[dataArrayLen];
                    dis.read(imageData, 0, dataArrayLen);
                    int nArray = 0;

                    for (int i = height - 1; i >= 0; i--) {
                        for (int j = 0; j < width; j++) {
                            int b = imageData[nArray++];
                            int g = imageData[nArray++];
                            int r = imageData[nArray++];
                            if ((r + g + b) / 3 >= 0 && (r + g + b) / 3 < 127) {
                                image[i * width + j] = 1;
                            } else {
                                image[i * width + j] = 0;
                            }
                        }
                    }
                    /*
                     * image_bytes[0] width low (value = width / 8)( 0 < value <255)
                     * image_bytes[1] width high image_bytes[2] height low (value =
                     * height ) ( 0 < value <255) image_bytes[3] height high
                     */
                    image_bytes[0] = (byte) (width / 8);
                    image_bytes[1] = 0;
                    if (height > 255) {
                        image_bytes[2] = (byte) (height - 255);// (byte) height;
                        image_bytes[3] = 1; // high
                    } else {
                        image_bytes[2] = (byte) (height);// (byte) height; // low
                        image_bytes[3] = 0; // high
                    }
                    for (int n = 0; n < width * height / 8; n++) {
                        image_bytes[4 + n] = (byte) ((byte) (image[8 * n] & 0x1) << 7
                                | (byte) (image[8 * n + 1] & 0x1) << 6
                                | (byte) (image[8 * n + 2] & 0x1) << 5
                                | (byte) (image[8 * n + 3] & 0x1) << 4
                                | (byte) (image[8 * n + 4] & 0x1) << 3
                                | (byte) (image[8 * n + 5] & 0x1) << 2
                                | (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
                    }
                    return image_bytes;
            }

        } catch (Exception ex) {
        }
        return null;
    }

    public void establecerMensaje(String titulo, String mensaje) {
        Alert alerta = new Alert(titulo, mensaje, null, AlertType.INFO);
        controlador.mostrarAlerta(alerta,null);
    }

    public void mosrarmensa(String mensa) {
        this.controlador.mostrarMensaje(mensa);
    }

    public String getImei() {
        LOGGER.info("devuelve el imei");
        LOGGER.info("obtenemos el imei");
        String imei = printer.getIMEI(Printer.SIM1);
        LOGGER.info("imei obtenido " + imei);
        LOGGER.info("devolviendo");
        return imei;
    }

}
