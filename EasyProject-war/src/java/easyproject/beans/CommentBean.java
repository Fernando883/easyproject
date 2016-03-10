/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyproject.beans;

import EasyProject.ejb.ComentarioFacade;
import EasyProject.ejb.FicheroFacade;
import EasyProject.entities.Comentario;
import EasyProject.entities.Fichero;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author victo
 */
@ManagedBean
@RequestScoped
public class CommentBean {
    
    //EJB inyections
    @EJB
    private FicheroFacade ficheroFacade;
    @EJB
    private ComentarioFacade comentarioFacade;

    //Session bean
    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;
    
    //Form vars
    protected Part file;
    private String message;
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
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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
    
    public String doUpdateFile() throws IOException{
        file.write(getFilename(file));
        Comentario comment = new Comentario();
        message = "Ha subido el fichero: " + getFilename(file);
        comment.setTexto(message);
        Date date = Calendar.getInstance().getTime();
        comment.setFecha(date);
        comment.setIdTarea(userBean.getTaskSelected());
        comment.setIdUsuario(userBean.getUser());
        comentarioFacade.create(comment);
        
        Fichero fileUpload = new Fichero();
        fileUpload.setRuta("/Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/generated/jsp/EasyProject/EasyProject-war_war/" + getFilename(file));
        fileUpload.setIdTarea(userBean.getTaskSelected());
        ficheroFacade.create(fileUpload);
        userBean.getTaskSelected().getComentarioCollection().add(comment);
        
        
        File dowloadFile = new File("/Applications/NetBeans/glassfish-4.1/glassfish/domains/domain1/generated/jsp/EasyProject/EasyProject-war_war/"+ getFilename(file));
	File newFile = new File("/Users/inftel11/NetBeansProjects/carlos/easyproject/EasyProject-war/web/uploaded/"+ getFilename(file));
	Path sourcePath = dowloadFile.toPath();
	Path newtPath = newFile.toPath();
	Files.copy(sourcePath, newtPath, REPLACE_EXISTING); 
        
        
        message="";
        return "";
    }
    
    public String downloadFile(String text) throws IOException {
        String file = text.substring(22);
	
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("http://localhost:8080/EasyProject-war/faces/uploaded/"+file);
	
        return "";
    }
    
    public static String getFilename(Part part){
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if(cd.trim().startsWith("filename")){
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

}
