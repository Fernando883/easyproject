/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;


import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Usuario;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

    public String doSignOut(){
        user = new Usuario();
        email="";
        return "PageTitle";
    }

    

}
