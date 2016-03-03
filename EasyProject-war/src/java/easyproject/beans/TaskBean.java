/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.TareaFacade;
import EasyProject.entities.Tarea;
import java.math.BigInteger;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author victo
 */
@ManagedBean
@RequestScoped
public class TaskBean {

    @EJB
    private TareaFacade tareaFacade;

    private String nameTask;
    private BigInteger tiempo;

    public BigInteger getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigInteger tiempo) {
        this.tiempo = tiempo;
    }
    private Collection<Tarea> collectionTask;

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

   

    public Collection<Tarea> getCollectionTask() {
        return collectionTask;
    }

    public void setCollectionTask(Collection<Tarea> collectionTask) {
        this.collectionTask = collectionTask;
    }

    /**
     * Creates a new instance of TaskBean
     */
    public String doAddTask() {
          Tarea tarea=new Tarea();
          tarea.setNombre(nameTask);
          tarea.setTiempo(tiempo);
          tareaFacade.create(tarea);
          collectionTask.add(tarea);
        
        return "task";
    }

  

}
