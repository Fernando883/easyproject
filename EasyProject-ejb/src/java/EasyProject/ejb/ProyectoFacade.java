/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.ejb;

import EasyProject.entities.Proyecto;
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
public class ProyectoFacade extends AbstractFacade<Proyecto> {
    @PersistenceContext(unitName = "EasyProject-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }
    
    public Proyecto getProject(String nameProject){
        List<Proyecto> project = em.createNamedQuery("Proyecto.findByNombreP").setParameter("nombreP", nameProject).getResultList();  
        if(project.isEmpty()){
            return null;
        }
        return project.get(0);
    }
    

}