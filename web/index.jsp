<%-- 
    Document   : index
    Created on : 28/05/2018, 18:14:34
    Author     : Andres Lanzoni
--%>

<%@page import="java.util.Date"%>
<%@page import="com.hs.modelo.Usuarios"%>
<%@page import="com.hs.conexion.UsuariosDAOImp"%>
<%@page import="com.hs.conexion.UsuariosDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de Usuarios</h1>
        <%
            UsuariosDAO us = new UsuariosDAOImp();
            
            us.insertarUsuario(new Usuarios(0, "Lorenzo", "Lanzoni", new Long("50458662"), "lolo", "llanzoni@gmail.com", "Vilelas", "calle 1 ", "calle 2", 2170, 0, "", "Rio Cuarto", 5800, new java.sql.Date(2018 - 1900,05,28)));
            
            for(Usuarios usuario : us.listarProductos("Andres")){
                %>
                <%="<hr />Nombre Usuario: " + usuario.getNombreUsu() + "<br />" %>
                <%="Apellido: " +usuario.getApellidoUsu() + "<br />" %>
                <%="Direccion: " +usuario.getDireccionUsu() + "<br />" %>
                <%="Fecha: " +usuario.getUltimoAccesoUsu()+ "<br />" %>
                <%="DNI: " +usuario.getDniUsu()+ "<br />" %>
                <%
            }
            
        %>
    </body>
</html>
