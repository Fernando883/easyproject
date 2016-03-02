/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author csalas
 */
@ManagedBean
@RequestScoped
public class ProjectBean {
    private String projectName;
    private String projectDescription;
    private Usuario projectDirector;

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
