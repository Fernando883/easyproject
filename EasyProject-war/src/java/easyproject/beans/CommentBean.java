/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.ComentarioFacade;
import EasyProject.entities.Comentario;
import java.util.Calendar;
import java.util.Date;
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
public class CommentBean {
    @EJB
    private ComentarioFacade comentarioFacade;

    
    private String message;
    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    /**
     * Creates a new instance of CommentBean
     */
    public CommentBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = Message;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    

    public String doSaveComment() {

        Comentario comment = new Comentario();
        comment.setTexto(message);
        Date date = Calendar.getInstance().getTime();
        comment.setFecha(date);
        comment.setIdTarea(userBean.getTaskSelected());
        comment.setIdUsuario(userBean.getUser());
        
        comentarioFacade.create(comment);
        userBean.getTaskSelected().getComentarioCollection().add(comment);
        
        message="";
        return "";
    }

}
