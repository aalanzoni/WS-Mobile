package com.hs.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuracion {
    //VARIABLES DE LA CLASE
    private static String ip = "";
    private static String imagenes = "";
    private static String baseBackup="";
    private static String reportes = "";
    private static String log = "";

    public Configuracion() {
        super();
        //Configuracion.setConfCliente("\\Configuracion.ini");
        Configuracion.setConfCliente("C:\\Users\\usuario\\Documents\\GitHub\\WS-Mobile\\Configuracion.ini");
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current relative path is: " + s);
    }
    
    
    /**
     * @return String
     */
    public static String getIp() {
            return ip;
    }
    /**
     * @return String
     */
    public static String getImagenes() {
            return imagenes;
    }

    public static String getBaseBackup() {
            return baseBackup;
    }

    public static String getReportes() {
            return reportes;
    }

    public static String getLog() {
        return log;
    }

        
        
    /**
     * setea los parametros tomados del archivo de configuracion
     * del cliente
     * @serial
     * @param nameFile
     */
    public static void setConfCliente (String nameFile ){
        if ((nameFile == null)||(nameFile.trim().length() == 0)){
            nameFile = "./Configuracion.ini";
        }
        IniFile ini = new IniFile(nameFile);
        ip = ini.getParameters("ip");        
        imagenes = ini.getParameters("imagenes");
        baseBackup = ini.getParameters("baseBackup");
        reportes = ini.getParameters("reportes");
        log = ini.getParameters("log");
    }
}
