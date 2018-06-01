/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.interceptor;

import com.hs.modelo.Usuarios;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 *
 * @author Andres Lanzoni
 */
public class PostEliminaUsuarioListener implements PostDeleteEventListener {

    @Override
    public void onPostDelete(PostDeleteEvent postDeleteEvent) {
        Object entidad = postDeleteEvent.getEntity();

        if (entidad instanceof Usuarios) {
            Usuarios usuario = (Usuarios) entidad;

            System.out.println("Se ha eliminado al Usuario " + usuario.getDniUsu() + ", id=\"" + postDeleteEvent.getId() + "\"");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        return false;
    }
}
