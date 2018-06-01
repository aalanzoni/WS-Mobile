/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.conexion;

import com.hs.modelo.Usuarios;
import java.util.List;

/**
 *
 * @author Andres Lanzoni
 */
public interface UsuariosDAO {
    
    public void insertarUsuario (Usuarios usuario)throws Exception;
    public void actualizarUsuario (Usuarios usuario)throws Exception;
    public void eliminarUsuario (Usuarios usuario)throws Exception;
    public List<Usuarios> listarUsuarios (String filtro) throws Exception;
    public Usuarios getUsuarioDNI (Long dni) throws Exception;
}
