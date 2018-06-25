/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices;

import com.google.gson.Gson;
import com.hs.conexion.UsuariosDAO;
import com.hs.conexion.UsuariosDAOImp;
import com.hs.modelo.Usuarios;
import com.hs.webservices.model.RequestUsuario;
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
public class EliminarUsuario extends HttpServlet {
     
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
        WSLogger logger = new WSLogger();         
        
        //Objeto gson que sirve para "parsear" los resultados.
        final Gson gson = new Gson();
        //Convertimos desde JSON a Java creando una lista de StringMap
        RequestUsuario user = gson.fromJson(reader, RequestUsuario.class);  
             
        response.setContentType("application/json");        
        response.setStatus(HttpServletResponse.SC_OK); 
        
        UsuariosDAO us = new UsuariosDAOImp();
        Usuarios usuario = new Usuarios();        
        
        if (user.getDni_usu()== null) {
            respuesta.put("salida", 9);  
            respuesta.put("msg", "ERROR - No se localiza DNI en JSON(dni_usu)");
            out.println(respuesta);
            return 9;          
        }

        try {
            usuario = us.getUsuarioDNI(user.getDni_usu());
        } catch (Exception e) {
            logger.WriteLog(EliminarUsuario.class.getName(), "processRequest", e, Level.SEVERE);
            respuesta.put("salida", 9);
            respuesta.put("msg", "ERROR - Al buscar en DB el usuario por DNI");
            out.println(respuesta);
            return 9;
        }        
        
        if (usuario != null) {
            try {
                us.eliminarUsuario(usuario);                
            } catch (Exception e) {
                logger.WriteLog(EliminarUsuario.class.getName(), "processRequest", e, Level.SEVERE);
                respuesta.put("salida", 9);
                respuesta.put("msg", "ERROR - Al Insertar Usuario en DB");
                out.println(respuesta);
                return 9;
            }  
            respuesta.put("salida", 1);
            respuesta.put("msg", "Proceso Exitoso");
            out.println(respuesta);
            return 1; 
        }else{
            respuesta.put("salida", 9);
            respuesta.put("msg", "ERROR - No se localiza Usuario en DB");
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

