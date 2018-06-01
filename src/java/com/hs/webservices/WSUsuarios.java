/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices;

import com.hs.conexion.UsuariosDAO;
import com.hs.conexion.UsuariosDAOImp;
import com.hs.logger.MyFilter;
import com.hs.logger.MyFormatter;
import com.hs.logger.MyHandler;
import com.hs.modelo.Usuarios;
import com.hs.util.Configuracion;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Andres Lanzoni
 */
@WebService(serviceName = "WSUsuarios")
public class WSUsuarios {

    static Logger logger = Logger.getLogger(WSUsuarios.class.getName());

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        logger.setLevel(Level.FINE);
        logger.addHandler(new ConsoleHandler());
        //adding custom handler
        logger.addHandler(new MyHandler());
        try {
            //FileHandler file name with max size and number of log files limit
            Handler fileHandler = new FileHandler(Configuracion.getLog() + "Log.log", 2000, 5);
            fileHandler.setFormatter(new MyFormatter());
            //setting custom filter for FileHandler
            fileHandler.setFilter(new MyFilter());
            logger.addHandler(fileHandler);

            logger.log(Level.SEVERE, "Operacion Hola");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertarUsuario")
    public String insertarUsuario(@WebParam(name = "parametros") String param) {
        //TODO write your implementation code here:

        System.out.println("param: " + param);
        String salida = "";
        JSONObject obj = new JSONObject();

        JSONParser parser = new JSONParser();
        JSONObject parametros = new JSONObject();

        try {
            parametros = (JSONObject) parser.parse(param);
        } catch (ParseException pe) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            pe.printStackTrace();
            return obj.toString();
        }

        UsuariosDAO us = new UsuariosDAOImp();

        Usuarios usuario = new Usuarios();

        System.out.println("JSON : -->  /n" + parametros);

        Long dni = new Long("0");

        String dniS = (String) parametros.get("dni_usu");
        if (dniS != null) {
            try {
                dni = new Long(dniS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - No se parsea a Long el DNI");
                return obj.toString();
            }
        } else {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            return obj.toString();
        }
        usuario.setDniUsu(dni);

        String passS = (String) parametros.get("pass_usu");
        if (passS == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza Pass en JSON(pass_usu)");
            return obj.toString();
        }
        usuario.setPassUsu(passS);

        if ((String) parametros.get("nombre_usu") != null) {
            usuario.setNombreUsu((String) parametros.get("nombre_usu"));
        }

        if ((String) parametros.get("apellido_usu") != null) {
            usuario.setApellidoUsu((String) parametros.get("apellido_usu"));
        }

        if ((String) parametros.get("mail_usu") != null) {
            usuario.setMailUsu((String) parametros.get("mail_usu"));
        }

        if ((String) parametros.get("direccion_usu") != null) {
            usuario.setDireccionUsu((String) parametros.get("direccion_usu"));
        }

        if ((String) parametros.get("entre_calle_1_usu") != null) {
            usuario.setEntreCalle1Usu((String) parametros.get("entre_calle_1_usu"));
        }

        if ((String) parametros.get("entre_calle_2_usu") != null) {
            usuario.setEntreCalle2Usu((String) parametros.get("entre_calle_2_usu"));
        }

        String nroS = (String) parametros.get("numero_usu");
        if (nroS != null) {
            int nro = 0;
            try {
                nro = new Integer(nroS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - Al parsear altura de calle a Integer (numero_usu)");
                return obj.toString();
            }
            usuario.setNumeroUsu(nro);
        }

        String pisoS = (String) parametros.get("piso_usu");
        if (pisoS != null) {
            int piso = 0;
            try {
                piso = new Integer(pisoS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - Al parsear el Piso a Integer (piso_usu)");
                return obj.toString();
            }
            usuario.setPisoUsu(piso);
        }

        if ((String) parametros.get("departamento_usu") != null) {
            usuario.setDepartamentoUsu((String) parametros.get("departamento_usu"));
        }

        if ((String) parametros.get("localidad_usu") != null) {
            usuario.setLocalidadUsu((String) parametros.get("localidad_usu"));
        }

        String cpS = (String) parametros.get("codpos_usu");
        if (cpS != null) {
            int cp;
            try {
                cp = new Integer(cpS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - Al parsear el Codigo Postal a Integer (codpos_usu)");
                return obj.toString();
            }
            usuario.setCodposUsu(cp);
        }

        try {
            us.insertarUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Al Insertar Usuario en DB");
            return obj.toString();
        }
        obj.put("salida", 1);
        obj.put("msg", "Proceso Exitoso");

        return obj.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "validaUsuario")
    public String validaUsuario(@WebParam(name = "parametros") String param) {
        String salida = "";
        System.out.println("parametro de Entrada: " + param);

        JSONParser parser = new JSONParser();
        JSONObject parametros = new JSONObject();
        JSONObject obj = new JSONObject();

        try {
            parametros = (JSONObject) parser.parse(param);
        } catch (ParseException pe) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            pe.printStackTrace();
            return obj.toString();
        }

        String dniS = (String) parametros.get("dni_usu");
        String pass = (String) parametros.get("pass_usu");

        if (dniS == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            return obj.toString();
        }

        if (pass == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza Pass en JSON(pass_usu)");
            return obj.toString();
        }
        Long dniUsu;
        try {
            dniUsu = new Long(dniS);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se parsea a Long el DNI");
            return obj.toString();

        }

        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();

        try {
            usuario = us.getUsuarioDNI(dniUsu);
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            return obj.toString();
        }

        if (usuario != null) {
            if (usuario.getPassUsu().equals(pass)) {
                obj.put("salida", new Integer(1));
            } else {
                obj.put("salida", new Integer(2));
            }

            obj.put("nombre_usu", usuario.getNombreUsu());
            obj.put("apellido_usu", usuario.getApellidoUsu());
            obj.put("dni_usu", usuario.getDniUsu());
            obj.put("pass_usu", usuario.getPassUsu());
            obj.put("mail_usu", usuario.getMailUsu());
            obj.put("direccion_usu", usuario.getDireccionUsu());
            obj.put("entre_calle_1_usu", usuario.getEntreCalle1Usu());
            obj.put("entre_calle_2_usu", usuario.getEntreCalle2Usu());
            obj.put("numero_usu", usuario.getNumeroUsu());
            obj.put("piso_usu", usuario.getPisoUsu());
            obj.put("departamento_usu", usuario.getDepartamentoUsu());
            obj.put("localidad_usu", usuario.getLocalidadUsu());
            obj.put("codpos_usu", usuario.getCodposUsu());

            salida = obj.toString();

            System.out.println("JSON: " + obj);
            System.out.println("JSON String: " + salida);

        } else {
            obj.put("salida", new Integer(3));
            salida = obj.toString();
        }
        return salida;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "eliminarUsuario")
    public String eliminarUsuario(@WebParam(name = "parameter") String param) {
        System.out.println("parametro de Entrada: " + param);

        JSONParser parser = new JSONParser();
        JSONObject parametros = new JSONObject();
        JSONObject obj = new JSONObject();

        try {
            parametros = (JSONObject) parser.parse(param);
        } catch (ParseException pe) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            pe.printStackTrace();
            return obj.toString();
        }

        String dniS = (String) parametros.get("dni_usu");
        if (dniS == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            return obj.toString();
        }
        
        Long dniUsu;
        try {
            dniUsu = new Long(dniS);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se parsea a Long el DNI");
            return obj.toString();
        }

        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();

        try {
            usuario = us.getUsuarioDNI(dniUsu);
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            return obj.toString();
        }

        if (usuario != null) {
            try {
                us.eliminarUsuario(usuario);
                obj.put("salida", 1);
                obj.put("msg", "ERROR - Al Eliminar el Usuario");                
            } catch (Exception e) {
                e.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - Al Eliminar el Usuario");
                return obj.toString();
            }
            obj.put("salida", 1);
            obj.put("msg", "Proceso Exitoso");
        }
        
        return obj.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "modificarUsuario")
    public String modificarUsuario(@WebParam(name = "parameter") String parameter) {
        System.out.println("param: " + parameter);
        String salida = "";
        JSONObject obj = new JSONObject();

        JSONParser parser = new JSONParser();
        JSONObject parametros = new JSONObject();

        try {
            parametros = (JSONObject) parser.parse(parameter);
        } catch (ParseException pe) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            pe.printStackTrace();
            return obj.toString();
        }

        UsuariosDAO us = new UsuariosDAOImp();

        Usuarios usuario = new Usuarios();

        System.out.println("JSON : -->  /n" + parametros);

        Long dniUsu = new Long("0");

        String dniS = (String) parametros.get("dni_usu");
        if (dniS != null) {
            try {
                dniUsu = new Long(dniS);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - No se parsea a Long el DNI");
                return obj.toString();
            }
        } else {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            return obj.toString();
        }
        
        try {
            usuario = us.getUsuarioDNI(dniUsu);
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            return obj.toString();
        }

        if (usuario == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Usuario a modificar no Localizado");
            return obj.toString();
        }

        

        obj.put("nombre_usu", usuario.getNombreUsu());
        obj.put("apellido_usu", usuario.getApellidoUsu());
        obj.put("dni_usu", usuario.getDniUsu());
        obj.put("pass_usu", usuario.getPassUsu());
        obj.put("mail_usu", usuario.getMailUsu());
        obj.put("direccion_usu", usuario.getDireccionUsu());
        obj.put("entre_calle_1_usu", usuario.getEntreCalle1Usu());
        obj.put("entre_calle_2_usu", usuario.getEntreCalle2Usu());
        obj.put("numero_usu", usuario.getNumeroUsu());
        obj.put("piso_usu", usuario.getPisoUsu());
        obj.put("departamento_usu", usuario.getDepartamentoUsu());
        obj.put("localidad_usu", usuario.getLocalidadUsu());
        obj.put("codpos_usu", usuario.getCodposUsu());

            salida = obj.toString();

            System.out.println("JSON: " + obj);
            System.out.println("JSON String: " + salida);


        return salida;        
        

    }

    
    
}
