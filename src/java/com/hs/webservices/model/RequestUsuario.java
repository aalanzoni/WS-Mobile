/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hs.webservices.model;

/**
 *
 * @author usuario
 */
public class RequestUsuario {
     private final String nombre_usu;       
     private final String apellido_usu;     
     private final Long dni_usu;               
     private final String pass_usu;         
     private final String mail_usu;         
     private final String direccion_usu;    
     private final String entre_calle_1_usu;
     private final String entre_calle_2_usu;
     private final Integer numero_usu;       
     private final Integer piso_usu;         
     private final String departamento_usu; 
     private final String localidad_usu;    
     private final Integer codpos_usu;

    public RequestUsuario(String nombre_usu, String apellido_usu, Long dni_usu, String pass_usu, String mail_usu, String direccion_usu, String entre_calle_1_usu, String entre_calle_2_usu, Integer numero_usu, Integer piso_usu, String departamento_usu, String localidad_usu, Integer codpos_usu) {
        this.nombre_usu = nombre_usu;
        this.apellido_usu = apellido_usu;
        this.dni_usu = dni_usu;
        this.pass_usu = pass_usu;
        this.mail_usu = mail_usu;
        this.direccion_usu = direccion_usu;
        this.entre_calle_1_usu = entre_calle_1_usu;
        this.entre_calle_2_usu = entre_calle_2_usu;
        this.numero_usu = numero_usu;
        this.piso_usu = piso_usu;
        this.departamento_usu = departamento_usu;
        this.localidad_usu = localidad_usu;
        this.codpos_usu = codpos_usu;
    }

    public String getNombre_usu() {
        return nombre_usu;
    }

    public String getApellido_usu() {
        return apellido_usu;
    }

    public Long getDni_usu() {
        return dni_usu;
    }

    public String getPass_usu() {
        return pass_usu;
    }

    public String getMail_usu() {
        return mail_usu;
    }

    public String getDireccion_usu() {
        return direccion_usu;
    }

    public String getEntre_calle_1_usu() {
        return entre_calle_1_usu;
    }

    public String getEntre_calle_2_usu() {
        return entre_calle_2_usu;
    }

    public Integer getNumero_usu() {
        return numero_usu;
    }

    public Integer getPiso_usu() {
        return piso_usu;
    }

    public String getDepartamento_usu() {
        return departamento_usu;
    }

    public String getLocalidad_usu() {
        return localidad_usu;
    }

    public Integer getCodpos_usu() {
        return codpos_usu;
    }
     
     
}
