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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author victo
 */
@ManagedBean
@SessionScoped
public class UserBean {

    @EJB
    private UsuarioFacade usuarioFacade;

   
    private String email;
    private Usuario user;
    private String name;
    private List<Proyecto> proyectos;
    
    @PostConstruct
    public void init(){
//        user = usuarioFacade.getUser(2L);
//        System.out.println(user.getEmail());
//        proyectos = (List<Proyecto>) user.getProyectoCollection();
//        name = user.getNombreU();
//        
//        for (Proyecto proyecto : proyectos) {
//            System.out.println(proyecto.getNombreP());
//        }
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

 
    /**
     * Creates a new instance of UserBean
     * @return 
     */
    public String doCheckUser(){
   
        if( (usuarioFacade.find(user)!=null)){   
            return "User";
        }
        else{
            user=new Usuario();
            user.setToken("");
            user.setEmail(email);
            usuarioFacade.create(user);
        }
        return "User";
    }
    
    public void doLogin(){
        name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("name");
        email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("email");
        if(usuarioFacade.getUser(email) == null){
            System.out.println("NUEVO USUARIO");
            Usuario NewUser = new Usuario();
            NewUser.setEmail(email);
            NewUser.setNombreU(name);
            usuarioFacade.create(NewUser);
        }else{
            user = usuarioFacade.getUser(email);
        }
        proyectos = (List<Proyecto>) user.getProyectoCollection(); 
    }

    public String doSignOut(){
        user = new Usuario();
        email="";
        return "PageTitle";
    }

    

}
