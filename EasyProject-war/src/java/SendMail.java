/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EasyProject.ejb.ProyectoFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Usuario;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Fernando
 */
public class SendMail implements Runnable {
    UsuarioFacade usuarioFacade = lookupUsuarioFacadeBean();

  
    Thread t_email;
    String email, nombre_P, message;

    public SendMail( String email, String nombre_P, String message) {
        this.email = email;
        this.nombre_P = nombre_P;
        this.message = message;
        t_email.start();
    }

    @Override
    public void run() {

        usuarioFacade.sendEmailCreate(email, nombre_P, message);

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
