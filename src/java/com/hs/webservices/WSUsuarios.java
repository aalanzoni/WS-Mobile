/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices;

import com.hs.conexion.UsuariosDAO;
import com.hs.conexion.UsuariosDAOImp;
import com.hs.modelo.Usuarios;
import com.hs.util.Configuracion;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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

     Logger logger = Logger.getLogger("MyLog");
     Configuracion config = new Configuracion();
     

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        
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
            logger.throwing("WSUsuarios", "hello", e);
        }
        
        //logger.warn("cabeza de foco","lalala",txt);
        //log.debug("Temperature set to {}. Old temperature was {}.", "hello!", txt);
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
            logger.throwing("WSUsuarios", "insertarUsuario", pe);
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            //pe.printStackTrace();
            return obj.toString();
        }

        UsuariosDAO us = new UsuariosDAOImp();

        Usuarios usuario = new Usuarios();

        //System.out.println("JSON : -->  /n" + parametros);

        Long dni = new Long("0");

        String dniS = (String) parametros.get("dni_usu");
        if (dniS != null) {
            try {
                dni = new Long(dniS);
            } catch (NumberFormatException nfe) {
                logger.throwing("WSUsuarios", "insertarUsuario", nfe);
                //nfe.printStackTrace();
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
                logger.throwing("WSUsuarios", "insertarUsuario", nfe);
                //nfe.printStackTrace();
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
                logger.throwing("WSUsuarios", "insertarUsuario", nfe);
                //nfe.printStackTrace();
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
                logger.throwing("WSUsuarios", "insertarUsuario", nfe);
                //nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - Al parsear el Codigo Postal a Integer (codpos_usu)");
                return obj.toString();
            }
            usuario.setCodposUsu(cp);
        }

        try {
            us.insertarUsuario(usuario);
        } catch (Exception e) {
            logger.throwing("WSUsuarios", "insertarUsuario", e);
            //e.printStackTrace();
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
            logger.throwing("WSUsuarios", "validaUsuario", pe);
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            //pe.printStackTrace();
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
            logger.throwing("WSUsuarios", "validaUsuario", nfe);
            //nfe.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se parsea a Long el DNI");
            return obj.toString();

        }

        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();

        try {
            usuario = us.getUsuarioDNI(dniUsu);
        } catch (Exception e) {
            logger.throwing("WSUsuarios", "validaUsuario", e);
            //e.printStackTrace();
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
            logger.throwing("WSUsuarios", "eliminarUsuario", pe);
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            //pe.printStackTrace();
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
            logger.throwing("WSUsuarios", "eliminarUsuario", nfe);
            //nfe.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No se parsea a Long el DNI");
            return obj.toString();
        }

        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();

        try {
            usuario = us.getUsuarioDNI(dniUsu);
        } catch (Exception e) {
            logger.throwing("WSUsuarios", "eliminarUsuario", e);
            //e.printStackTrace();
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
                logger.throwing("WSUsuarios", "eliminarUsuario", e);
                //e.printStackTrace();
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
            logger.throwing("WSUsuarios", "modificarUsuario", pe);
            obj.put("salida", 9);
            obj.put("msg", "ERROR - No parsea a JSON los parametros de entrada");
            //pe.printStackTrace();
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
                logger.throwing("WSUsuarios", "modificarUsuario", nfe);
                //nfe.printStackTrace();
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
            logger.throwing("WSUsuarios", "modificarUsuario", e);
            //e.printStackTrace();
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            return obj.toString();
        }

        if (usuario == null) {
            obj.put("salida", 9);
            obj.put("msg", "ERROR - Usuario a modificar no Localizado");
            return obj.toString();
        }

        if((String)obj.get("nombre_usu")!= null)
            usuario.setNombreUsu((String)obj.get("nombre_usu"));

        if((String)obj.get("apellido_usu")!= null)
            usuario.setNombreUsu((String)obj.get("apellido_usu"));
        
        if((String)obj.get("pass_uss")!= null)
            usuario.setPassUsu((String)obj.get("pass_uss"));
        
        if((String)obj.get("mail_usu")!= null)
            usuario.setMailUsu((String)obj.get("mail_usu"));
        
        if((String)obj.get("direccion_usu")!= null)
            usuario.setDireccionUsu((String)obj.get("direccion_usu"));

        if((String)obj.get("entre_calle_1_usu")!= null)
            usuario.setEntreCalle1Usu((String)obj.get("entre_calle_1_usu"));

        if((String)obj.get("entre_calle_2_usu")!= null)
            usuario.setEntreCalle2Usu((String)obj.get("entre_calle_2_usu"));

        String nroS = (String)obj.get("numero_usu");
        if(nroS != null){
            try{
                Integer nro = new Integer(nroS);
                usuario.setNumeroUsu(nro);
            }
            catch(NumberFormatException nfe){
                logger.throwing("WSUsuarios", "modificarUsuario", nfe);
                //nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - No se parsea a Integer el Nro");
            }
        }
        
        String pisoS = (String)obj.get("piso_usu");
        if(pisoS != null){
            try{
                Integer piso = new Integer(pisoS);
                usuario.setPisoUsu(piso);
            }
            catch(NumberFormatException nfe){
                logger.throwing("WSUsuarios", "modificarUsuario", nfe);
                //nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - No se parsea a Integer el Piso");
            }
        }
        
        if((String)obj.get("departamento_usu")!= null)
            usuario.setDepartamentoUsu((String)obj.get("departamento_usu"));
        
        if((String)obj.get("localidad_usu")!= null)
            usuario.setLocalidadUsu((String)obj.get("localidad_usu"));
        
        String cpS = (String)obj.get("codpos_usu");
        if(cpS != null){
            try{
                Integer cp = new Integer(cpS);
                usuario.setCodposUsu(cp);
            }
            catch(NumberFormatException nfe){
                logger.throwing("WSUsuarios", "modificarUsuario", nfe);
                //nfe.printStackTrace();
                obj.put("salida", 9);
                obj.put("msg", "ERROR - No se parsea a Integer el Codigo Postal");
            }
        }        
        
        salida = obj.toString();

        System.out.println("JSON: " + obj);
        System.out.println("JSON String: " + salida);

        return salida;
    }

    
    
}
