/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.ejb;

import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Proyecto getProject(String nameProject){
        System.out.println(nameProject);
        List<Proyecto> project = em.createNamedQuery("Proyecto.findByNombreP").setParameter("nombreP", nameProject).getResultList();  
        if(project.isEmpty()){
            return null;
        }
        return project.get(0);
    }
    
    public Usuario getUser(String email){
        List<Usuario> userSelected = em.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getResultList();
        if(userSelected.isEmpty()){
            return null;
        }
        return userSelected.get(0);
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
