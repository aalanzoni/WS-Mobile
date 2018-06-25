/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices;

import com.hs.webservices.model.RequestUsuario;
import com.google.gson.Gson;
import com.hs.conexion.UsuariosDAO;
import com.hs.conexion.UsuariosDAOImp;
import com.hs.modelo.Usuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Windows-7
 */
public class ValidarUsuario extends HttpServlet {
     
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request JSON
     * @param response servlet response
     * @return 
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected int processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter(); 
        JSONObject respuesta = new JSONObject();
        JSONObject user = new JSONObject(); 
        WSLogger logger = new WSLogger();         
        
        //Objeto gson que sirve para "parsear" los resultados.
        final Gson gson = new Gson();
        //Convertimos desde JSON a Java creando una lista de StringMap
        RequestUsuario validateUser = gson.fromJson(reader, RequestUsuario.class);  
             
        response.setContentType("application/json");        
        response.setStatus(HttpServletResponse.SC_OK); 
        
        if (validateUser.getDni_usu() == null) {
            respuesta.put("salida", 9);
            respuesta.put("usuario", user);  
            respuesta.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            out.println(respuesta);
            return 9;
        }

        if (validateUser.getPass_usu() == null) {
            respuesta.put("salida", 9);
            respuesta.put("usuario", user);  
            respuesta.put("msg", "ERROR - No se localiza Pass en JSON(pass_usu)");
            out.println(respuesta);
            return 9;            
        }                      
              
        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();

        try {
            usuario = us.getUsuarioDNI(validateUser.getDni_usu());
        } catch (Exception e) {
            logger.WriteLog(ValidarUsuario.class.getName(), "processRequest", e, Level.SEVERE);
            respuesta.put("salida", 9);
            respuesta.put("usuario", user); 
            respuesta.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            out.println(respuesta);
            return 9;            
        }

        if (usuario != null) {
            if (usuario.getPassUsu().equals(validateUser.getPass_usu())) {
                respuesta.put("salida", new Integer(1));
            } else {
                respuesta.put("salida", new Integer(2));
            }

            user.put("nombre_usu", usuario.getNombreUsu());
            user.put("apellido_usu", usuario.getApellidoUsu());
            user.put("dni_usu", usuario.getDniUsu());
            user.put("pass_usu", usuario.getPassUsu());
            user.put("mail_usu", usuario.getMailUsu());
            user.put("direccion_usu", usuario.getDireccionUsu());
            user.put("entre_calle_1_usu", usuario.getEntreCalle1Usu());
            user.put("entre_calle_2_usu", usuario.getEntreCalle2Usu());
            user.put("numero_usu", usuario.getNumeroUsu());
            user.put("piso_usu", usuario.getPisoUsu());
            user.put("departamento_usu", usuario.getDepartamentoUsu());
            user.put("localidad_usu", usuario.getLocalidadUsu());
            user.put("codpos_usu", usuario.getCodposUsu());

            respuesta.put("usuario", user); 
            respuesta.put("msg", "OK");   
            
            out.println(respuesta);           
            return 9;
        } else {
            respuesta.put("salida", new Integer(3));
            respuesta.put("usuario", user); 
            respuesta.put("msg", "OK"); 
            
            out.println(respuesta);
            return 9;
        }          
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);        
        //response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); 
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

