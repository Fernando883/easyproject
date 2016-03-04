/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.TareaFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    private Collection<Tarea> collectionTask;
    
    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    public BigInteger getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigInteger tiempo) {
        this.tiempo = tiempo;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Collection<Tarea> getCollectionTask() {
        collectionTask = new ArrayList<Tarea>();
        if (userBean.getProjectSelected() != null)
        {
            Collection<Tarea> taskUserSelected = userBean.getUser().getTareaCollection();
            for (Tarea task : taskUserSelected) {
                System.out.println(task.getNombre());
                if(task.getIdProyecto().getIdProyect() == userBean.getProjectSelected().getIdProyect()){
                    collectionTask.add(task);
                } 
            } 
        }
        return collectionTask;
    }

    public void setCollectionTask(Collection<Tarea> collectionTask) {
        this.collectionTask = collectionTask;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
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
