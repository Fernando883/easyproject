/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.ProyectoFacade;
import EasyProject.ejb.TareaFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import EasyProject.entities.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.example.model.SendMail;

/**
 *
 * @author csalas
 */
@ManagedBean
@ViewScoped
public class ProjectBean {

    @EJB
    private TareaFacade tareaFacade;
    @EJB
    private ProyectoFacade proyectoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;

    private String projectName;
    private String projectDescription;
    //private Usuario projectDirector;

    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    protected List<String> listUsersName;
    protected List<String> listUsersNameEdit;
    protected List<String> tempUsers;
    protected String search;
    protected boolean projectAdded;
    protected boolean projectEdited;
    protected boolean editProject = false;

    protected ArrayList<Usuario> userRemove;
    private Map<Usuario, Boolean> checked = new HashMap<>();
    
    private String message = "Ha intentado eliminar un usuario asignado a una tarea";
    private boolean show = false;

    /**
     * Creates a new instance of addProjectBean
     */
    public ProjectBean() {
    }
    private List<Proyecto> proyectos;

    @PostConstruct
    public void init() {
        search = "";

        userRemove = new ArrayList<>();
        projectAdded = false;
        projectEdited = false;

        cargarProjects();

    }

    private void cargarProjects() {
        Usuario userSelected;
        userSelected = usuarioFacade.getUser(userBean.getUser().getIdUsuario());

        proyectos = (List<Proyecto>) userSelected.getProyectoCollection();

        List<String> nameProject = new ArrayList<>();

        for (Proyecto project : proyectos) {
            nameProject.add(project.getNombreP());
        }

        Collections.sort(nameProject);
        proyectos = new ArrayList<>();

        for (String nameProject1 : nameProject) {
            proyectos.add(usuarioFacade.getProject(nameProject1));
        }

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /*public Usuario getProjectDirector() {
     return projectDirector;
     }

     public void setProjectDirector(Usuario projectDirector) {
     this.projectDirector = projectDirector;
     }*/
    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
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

    public boolean isProjectAdded() {
        return projectAdded;
    }

    public void setProjectAdded(boolean proyectoInsertado) {
        this.projectAdded = proyectoInsertado;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public ArrayList<Usuario> getUserRemove() {
        return userRemove;
    }

    public void setUserRemove(ArrayList<Usuario> userRemove) {
        this.userRemove = userRemove;
    }

    public List<String> getListUsersNameEdit() {
        return listUsersNameEdit;
    }

    public void setListUsersNameEdit(List<String> listUsersNameEdit) {
        this.listUsersNameEdit = listUsersNameEdit;
    }

    public Map<Usuario, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Usuario, Boolean> checked) {
        this.checked = checked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
    

    public String doPrepareEdit() {

        listUsersName = usuarioFacade.getUsersEmail();
        listUsersName.remove(userBean.getUser().getEmail());

        for (Usuario user : userBean.getProjectSelected().getUsuarioCollection()) {
            listUsersName.remove(user.getEmail());
        }

        System.out.println("Longi: " + userBean.getProjectSelected().getUsuarioCollection().size());
        tempUsers = new ArrayList<>();

        return "";
    }

    public String doPrepareCreate() {

        listUsersName = usuarioFacade.getUsersEmail();
        listUsersName.remove(userBean.getUser().getEmail());
        tempUsers = new ArrayList<>();
        projectAdded = false;

        return "";
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

    public String doGoProject(Proyecto project) {
        userBean.setProjectSelected(project);
        return "ViewProjectPage";
    }

    public String doAddTempList() {

        if (!tempUsers.contains(search)) {
            tempUsers.add(search);
        }

        search = "";
        return null;
    }

    public String doCleanProject() {

        projectName = "";

        projectDescription = "";
        tempUsers = new ArrayList<>();

        return "";
    }

    public String doEditProject() {

        //Lo que había antes más los nuevos
        List<Usuario> memberProject = (List<Usuario>) userBean.projectSelected.getUsuarioCollection();

        for (String userString : tempUsers) {
            Usuario tmp = usuarioFacade.getUser(userString);
            if (tmp != null) {
                memberProject.add(tmp);
            }
        }

        userBean.getProjectSelected().setUsuarioCollection(memberProject);

        if (projectName.length() != 0) {
            userBean.getProjectSelected().setNombreP(projectName);

        }

        if (projectDescription.length() != 0) {
            userBean.getProjectSelected().setNombreP(projectName);

        }

        Iterator it = checked.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
            if (pair.getValue().equals(true)) {
                Usuario user = (Usuario) pair.getKey();
                for (Tarea task : userBean.projectSelected.getTareaCollection()) {
                    if (!user.getTareaCollection().contains(task)) {
                        userBean.projectSelected.getUsuarioCollection().remove(pair.getKey());
                    } else {
                        show = true;
                    }
                }

            }

        }

        proyectoFacade.edit(userBean.getProjectSelected());

        projectName = "";
        projectDescription = "";
        tempUsers = new ArrayList<>();
        projectEdited = true;

        return "";
    }

    public void doAddProject() {

        String email;
        String message = "";

        List<Usuario> memberProject = new ArrayList<>();

        for (String userString : tempUsers) {
            Usuario tmp = usuarioFacade.getUser(userString);
            if (tmp != null) {
                memberProject.add(tmp);
            }
        }

        memberProject.add(userBean.getUser());
        Proyecto project = new Proyecto();
        project.setNombreP(projectName);
        project.setDescripcion(projectDescription);
        project.setDirector(userBean.getUser());
        project.setUsuarioCollection(memberProject);

        proyectoFacade.create(project);
        proyectos.add(project);

        projectName = "";

        projectDescription = "";
        tempUsers = new ArrayList<>();
        projectAdded = true;

        message = "has sido añadido al proyecto" + project.getNombreP() + "por el usuario:" + userBean.getName();

        List<Usuario> usuario = (List<Usuario>) project.getUsuarioCollection();
        for (Usuario usuario1 : usuario) {

            email = usuario1.getEmail();
            new SendMail(email, project.getNombreP(), message).start();

            //mail.toString();
        }

    }

    public String doGoToNewProject() {
        return "NewProjectPage";
    }

}
