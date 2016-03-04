/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;


import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
    private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        image = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("image");
        if(usuarioFacade.getUser(email) == null){
            System.out.println("NUEVO USER");
            usuarioFacade.setNewUser(email, name);
        }else{
            user = usuarioFacade.getUser(email);
        }
        proyectos = (List<Proyecto>) user.getProyectoCollection();  
    }

    public String doSignOut(){
        HttpSession session =  (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        user = new Usuario();
        email="";
        image="";
        return "PageTitle";
    }

    

}
