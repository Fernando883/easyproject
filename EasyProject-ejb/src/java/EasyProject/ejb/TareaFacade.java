/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.ejb;

import EasyProject.entities.Tarea;
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
public class TareaFacade extends AbstractFacade<Tarea> {
    @PersistenceContext(unitName = "EasyProject-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaFacade() {
        super(Tarea.class);
    }
    
    public boolean existUser () {
        Query q = em.createQuery("SELECT distinct u.email FROM Usuario u");
        return false;
        
    }
    
    public List<Tarea> findTareasUsuarioDeProyecto (Usuario u, Long idproyecto) {
        Query q;
        
        q = em.createQuery("select t from Tarea t " +
                "where t.idProyecto.idProyect = :idproy and :user member of t.usuarioCollection ");
        q.setParameter("idproy", idproyecto);
        q.setParameter("user", u);
        return q.getResultList();        
    }
    
}
