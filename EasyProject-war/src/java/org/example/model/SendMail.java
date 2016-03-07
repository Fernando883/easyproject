/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.model;


import EasyProject.ejb.UsuarioFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author victo
 */
public class SendMail extends Thread {

    UsuarioFacade usuarioFacade = lookupUsuarioFacadeBean();

    String email, nombre, message;

    public SendMail(String email, String nombre, String message) {
        this.email = email;
        this.nombre = nombre;
        this.message = message;
        
    }

    @Override
    public void run() {
        
            usuarioFacade.sendEmailCreate(email, nombre, message);
            
        
            
    }

    private UsuarioFacade lookupUsuarioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UsuarioFacade) c.lookup("java:global/EasyProject/EasyProject-ejb/UsuarioFacade!EasyProject.ejb.UsuarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
