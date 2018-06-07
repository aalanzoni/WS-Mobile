/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices;

import com.hs.util.Configuracion;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Jonathan Taberna
 */
public class WSLogger {
    Logger logger = Logger.getLogger("MyLog");
    Configuracion config = new Configuracion();
     
    public WSLogger(){
        FileHandler fh;
        try{
            System.out.println("ruta del Log:"+config.getLog());
            fh = new FileHandler(config.getLog(), true); 
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
            logger.info("Primera prueba");
            logger.info("¿Todo bien?");
        }
        catch(Exception e){
            //e.printStackTrace();
            logger.throwing("WSLogger", "WSLogger", e);
        }
    }
    
    /**
     * Log operation
     */
    public void WriteLog(String clase, String metodo, Throwable error, Level nivel){
        logger.log(nivel, "Clase: " + clase + ", Metodo: " + metodo, error);
    }
    
}
