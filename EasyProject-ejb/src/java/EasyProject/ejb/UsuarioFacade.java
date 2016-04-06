/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.ejb;

import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author macbookpro
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {
    @PersistenceContext(unitName = "EasyProject-ejbPU")
    private EntityManager em;
    
    @EJB
    private Mail mail;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario getUser(String email){
        List<Usuario> userSelected = em.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getResultList();
        if(userSelected.isEmpty()){
            return null;
        }
        return userSelected.get(0);
    }
    
    public Usuario getUser(Long user){
        Usuario userSelected = em.find(Usuario.class, user);
        em.refresh(userSelected);
        return userSelected;
    }
    
    
    public void sendEmail(String user,String email,String desteny, String subject, String message){
        
        String message1 = "El usuario " + user + ", con email " + email + ", le ha mandado el siguiente mensaje: \n\n" + message;
        
        mail = new Mail(subject, message1,desteny);
        mail.sendMail();
        }
    public void sendEmailCreate(String desteny, String subject, String message)
    {
        mail=new Mail(subject,message,desteny);
        mail.sendMail();
    }
    
    public List<String> getUsersEmail () {
        
        Query q = em.createQuery("SELECT distinct u.email FROM Usuario u");
        List<String> listUsersEmail = q.getResultList();
        
        return listUsersEmail;
        
    }

    public void setNewUser(String email, String nombre) {
        try{
            Usuario newUser = new Usuario();
            newUser.setEmail(email);
            newUser.setNombreU(nombre);
            em.persist(newUser);
        }catch (Exception ex) {
            System.out.println("Error" + ex);
        }
        
    }
    
}
