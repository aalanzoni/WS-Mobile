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
public class InsertarUsuario extends HttpServlet {
     
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
        }else{
            usuario.setDniUsu(user.getDni_usu());            
        }

        if (user.getPass_usu() == null) {
            respuesta.put("salida", 9);  
            respuesta.put("msg", "ERROR - No se localiza Pass en JSON(pass_usu)");
            out.println(respuesta);
            return 9;            
        }else{
             usuario.setPassUsu(user.getPass_usu());             
        }                      
        
        if (user.getNombre_usu() != null) {
            usuario.setNombreUsu(user.getNombre_usu());
        }
        
        if (user.getApellido_usu() != null) {
            usuario.setApellidoUsu(user.getApellido_usu());
        }        
        
        if (user.getMail_usu() != null) {
            usuario.setMailUsu(user.getMail_usu());
        }        
        
        if (user.getDireccion_usu() != null) {
            usuario.setDireccionUsu(user.getDireccion_usu());
        }

        if (user.getEntre_calle_1_usu() != null) {
            usuario.setEntreCalle1Usu(user.getEntre_calle_1_usu());
        }

        if (user.getEntre_calle_2_usu() != null) {
            usuario.setEntreCalle2Usu(user.getEntre_calle_2_usu());
        } 
        
        if (user.getNumero_usu() != null) {
            usuario.setNumeroUsu(user.getNumero_usu());
        }   
        
        if (user.getPiso_usu() != null) {
            usuario.setPisoUsu(user.getPiso_usu());
        }           
        
        if (user.getDepartamento_usu() != null) {
            usuario.setDepartamentoUsu(user.getDepartamento_usu());
        }

        if (user.getLocalidad_usu() != null) {
            usuario.setLocalidadUsu(user.getLocalidad_usu());
        }
        
        if (user.getCodpos_usu() != null) {
            usuario.setCodposUsu(user.getCodpos_usu());
        }        

        try {
            us.insertarUsuario(usuario);
        } catch (Exception e) {
            logger.WriteLog(InsertarUsuario.class.getName(), "processRequest", e, Level.SEVERE);
            respuesta.put("salida", 9);
            respuesta.put("msg", "ERROR - Al Insertar Usuario en DB");
            out.println(respuesta);
            return 9;
        }
        respuesta.put("salida", 1);
        respuesta.put("msg", "Proceso Exitoso");
        out.println(respuesta);
        return 1;        
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


