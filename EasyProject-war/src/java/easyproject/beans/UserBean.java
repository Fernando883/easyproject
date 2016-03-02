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
    private Usuario u;

    public Usuario getU() {
        return u;
    }

    public void setU(Usuario u) {
        this.u = u;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 
    /**
     * Creates a new instance of UserBean
     * @return 
     */
public String doCheckUser()
{
   
    if( (usuarioFacade.find(u)!=null))
    {   
        return "User";
    }
    else
    {
        u=new Usuario();
        u.setToken("");
        u.setEmail(email);
        usuarioFacade.create(u);
        
    }
    return "User";
}
    

}
