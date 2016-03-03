/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author csalas
 */
@ManagedBean
@RequestScoped
public class ProjectBean {
    @EJB
    private UsuarioFacade usuarioFacade;
    private String projectName;
    private String projectDescription;
    private Usuario projectDirector;
    protected List<String> listUsersName;
    protected String search;
    
    
    @PostConstruct
    public void init () {
        search="";
        listUsersName = usuarioFacade.getUsersEmail();
        
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

    public Usuario getProjectDirector() {
        return projectDirector;
    }

    public void setProjectDirector(Usuario projectDirector) {
        this.projectDirector = projectDirector;
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
    
    
    public List<String> completeName (String query) {
        System.out.println("eiii");
        List<String> results = new ArrayList<>();
        
        for (String nombre: this.listUsersName) {
            if (nombre.startsWith(query)) {
                System.out.println("Prueba: " + nombre);
                    results.add(nombre);
                
            }
        }
        return results;
    }
    
    

    /**
     * Creates a new instance of addProjectBean
     */
    public ProjectBean() {
    }
    
    public String doAddProject() {
        Proyecto project;
        
        return "addProject";
    }
    
    public String doListProject() {
        return "listProject";
    }
    
    
}
