/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.conexion;

import com.hs.modelo.HibernateUtil;
import com.hs.modelo.Usuarios;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Andres Lanzoni
 */
public class UsuariosDAOImp implements UsuariosDAO{
    Session sesion = HibernateUtil.getSessionFactory().openSession();
    
    @Override
    public void insertarUsuario(Usuarios usuario) throws Exception{
        sesion.beginTransaction();
        sesion.save(usuario);
        sesion.getTransaction().commit();
    }

    @Override
    public void actualizarUsuario(Usuarios usuario) throws Exception{
        sesion.beginTransaction();
        Usuarios us = (Usuarios) sesion.get(Usuarios.class, usuario.getIdUsu());
        if (us != null){
            us.setNombreUsu(usuario.getNombreUsu());
            //seguir
        }
        else{
            us = new Usuarios(usuario.getIdUsu(), usuario.getNombreUsu(), usuario.getApellidoUsu(), usuario.getDniUsu(), usuario.getPassUsu(), usuario.getMailUsu(), usuario.getDireccionUsu(), usuario.getEntreCalle1Usu(), usuario.getEntreCalle2Usu(), usuario.getNumeroUsu(), usuario.getPisoUsu(), usuario.getDepartamentoUsu(), usuario.getLocalidadUsu(), usuario.getCodposUsu(), usuario.getUltimoAccesoUsu());
        }
        sesion.saveOrUpdate(us);
        sesion.getTransaction().commit();
    }

    @Override
    public void eliminarUsuario(Usuarios usuario) throws Exception{
        sesion.beginTransaction();
        Usuarios us = (Usuarios) sesion.get(Usuarios.class, usuario.getIdUsu());
        if (us != null){
            sesion.delete(us);
        }
        sesion.getTransaction().commit();
    }

    @Override
    public List<Usuarios> listarUsuarios(String filtro) throws Exception{
        Query q = sesion.createQuery("select us from Usuarios us where us.nombreUsu = :filtro");
        q.setParameter("filtro", filtro);
        return (List <Usuarios> )q.list();
    }

    @Override
    public Usuarios getUsuarioDNI(Long dni) throws Exception{
        Query q = sesion.createQuery("select us from Usuarios us where us.dniUsu = :filtro");
        q.setParameter("filtro", dni);
        List <Usuarios> search = q.list();
        if(search.isEmpty() == false)
            return search.get(0);
        else
            return null;
    }
    
}
