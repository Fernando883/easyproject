/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Usuario;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author inftel11
 */
@Stateless
@Path("entity.usuario")
public class UsuarioFacadeREST{
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    

    public UsuarioFacadeREST() {
        //super(Usuario.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(Usuario entity) {
        //SetNewUser 
        usuarioFacade.create(entity);
    }
    
    @POST
    @Path("sendEmail")
    @Consumes({"application/json"})
    public void sendEmail(@PathParam("user") String user, @PathParam("email") String email,@PathParam("destiny") String destiny, @PathParam("subject") String subject, @PathParam("message") String message) {
        usuarioFacade.sendEmail(user,email, destiny, subject, message);
    }
    
    @POST
    @Path("sendEmailCreate")
    @Consumes({"application/json"})
    public void sendEmailCreate(@PathParam("destiny") String destiny, @PathParam("subject") String subject, @PathParam("message") String message) {
        usuarioFacade.sendEmailCreate(destiny, subject, message);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Usuario entity) {
        usuarioFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        usuarioFacade.remove(usuarioFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Usuario find(@PathParam("id") Long id) {
        // GetUser
        
        return usuarioFacade.find(id);
    }
    
    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public Usuario findByEmail(@PathParam("email") String email) {
        // GetUserByEmail
        return usuarioFacade.getUser(email);
    }

    @GET
    @Path("findAll")
    @Produces({"application/json"})
    public List<Usuario> findAll() {
        return usuarioFacade.findAll();
    }
    
    /*@GET
    @Path("getUsersEmail")
    @Produces({MediaType.APPLICATION_JSON})
    public GenericEntity<List<String>> getUsersEmail() {
        List<String> usersEmail = usuarioFacade.getUsersEmail();
        return new GenericEntity<List<String>>(usersEmail){};
    }*/
    
    @GET
    @Path("getUsersEmail")
    @Produces("application/json")
    public String getUsersEmail() {
        List<String> usersEmail = usuarioFacade.getUsersEmail();
        Gson trad = new Gson();
        
        return trad.toJson(usersEmail);
        //return Arrays.asList(usersEmail);
        
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return usuarioFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(usuarioFacade.count());
    }
    
    
    
}
