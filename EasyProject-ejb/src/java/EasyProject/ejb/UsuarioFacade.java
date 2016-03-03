/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.ejb;

import EasyProject.entities.Usuario;
import java.util.List;
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
    
    public Usuario getUser(String email){
        Usuario userSelected = (Usuario) em.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getSingleResult();
        return userSelected;
    }
    
    
    public List<String> getUsersEmail () {
        
        Query q = em.createQuery("SELECT distinct u.email FROM Usuario u");
        List<String> listUsersEmail = q.getResultList();
        
        return listUsersEmail;
        
    }
    
}
