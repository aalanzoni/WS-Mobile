/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.interceptor;

import com.hs.modelo.Usuarios;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 *
 * @author Andres Lanzoni
 */
public class ActualizaUsuarioListener implements PostUpdateEventListener {

    @Override
    public void onPostUpdate(PostUpdateEvent pue) {
        Object entidad = pue.getEntity();
        if (entidad instanceof Usuarios) {
            Usuarios usuario = (Usuarios) entidad;

            System.out.println("Se va a actualizar al usuario " + usuario.getDniUsu() + ", id=\"" + pue.getId() + "\"");
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        return false;
    }

       
}
