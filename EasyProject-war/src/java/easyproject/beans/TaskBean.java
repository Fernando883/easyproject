/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.TareaFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import EasyProject.entities.Usuario;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
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
    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    private String nameTask;
    private BigInteger tiempo;
    private Collection<Tarea> collectionTask;
    

    private BigInteger duration;
    protected boolean taskAdded;

    protected List<String> listUsersName;
    protected List<String> tempUsers;
    protected String search;


    /**
     * Creates a new instance of TaskBean
     */
    public TaskBean() {
    }

    @PostConstruct
    public void init() {
        taskAdded = false;
        listUsersName = new ArrayList<>();
        //System.out.println(userBean.getProjectSelected().getNombreP());
        /*List<Usuario> users = (List<Usuario>) userBean.getProjectSelected().getUsuarioCollection();
        for (Usuario user : users) {
            listUsersName.add(user.getEmail());
        }*/

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
    
    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    
    public boolean isTaskAdded() {
        return taskAdded;
    }

    public void setTaskAdded(boolean taskAdded) {
        this.taskAdded = taskAdded;
    }

    public List<String> getListUsersName() {
        return listUsersName;
    }

    public void setListUsersName(List<String> listUsersName) {
        this.listUsersName = listUsersName;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    public List<String> getTempUsers() {
        return tempUsers;
    }

    public void setTempUsers(List<String> tempUsers) {
        this.tempUsers = tempUsers;
    }

    public List<String> completeName(String query) {
        List<String> results = new ArrayList<>();

        for (String nombre : this.listUsersName) {
            if (nombre.startsWith(query)) {
                results.add(nombre);

            }
        }
        return results;
    }

    public String doAddTempList() {

        if (!tempUsers.contains(search)) {
            tempUsers.add(search);
        }

        search = "";
        return null;
    }

    public String doAddTask() {
        Tarea task = new Tarea();

        task.setNombre(nameTask);

        BigInteger min = new BigInteger("60");
        BigInteger durationMinutes = duration.multiply(min);
        task.setTiempo(durationMinutes);

        task.setIdProyecto(null);
        task.setIdUsuario(userBean.getUser());
        //tareaFacade.create(task);

        nameTask = "";
        duration = new BigInteger("");
        taskAdded = true;

        return "";
    }

}
