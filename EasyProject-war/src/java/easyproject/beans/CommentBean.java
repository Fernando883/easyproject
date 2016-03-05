/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author victo
 */
@ManagedBean
@RequestScoped
public class CommentBean {
    
    private String Message;

    /**
     * Creates a new instance of CommentBean
     */
    public CommentBean() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    
    public String doSaveComment(){
        return "";
    }
  
}
