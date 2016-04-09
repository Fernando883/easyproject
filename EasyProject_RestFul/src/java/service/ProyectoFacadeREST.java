/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.ProyectoFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Usuario;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.json.JSONObject;

/**
 *
 * @author inftel11
 */
@Stateless
@Path("entity.proyecto")
public class ProyectoFacadeREST {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    
    @EJB
    private ProyectoFacade proyectoFacade;

    
    public ProyectoFacadeREST() {
        //super(Proyecto.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(String json) {
        Proyecto proy = new Proyecto();
        Gson gson = new Gson();
        List<Usuario> usuarioCollection = new ArrayList<>();
        
        proy = gson.fromJson(json, Proyecto.class);
        /*
        Usuario director = (Usuario) j.get("director");
        proy.setDirector(director);
        proy.setDescripcion(j.getString("descripcion"));
        proy.setNombreP(j.getString("nombreP"));*/
        JSONObject j = new JSONObject(json);
        String listEmails = (String) j.get("listEmails");
        List<String> items = Arrays.asList(listEmails.split("\\s*,\\s*"));
        for (String item : items) {
            Usuario u = new Usuario ();
            u.setEmail(usuarioFacade.getUser(item).getEmail());
            u.setIdUsuario(usuarioFacade.getUser(item).getIdUsuario());
            u.setNombreU(usuarioFacade.getUser(item).getNombreU());
            if (u!= null) {
                usuarioCollection.add(u);
            }
            
        }
        proy.setUsuarioCollection(usuarioCollection);
        proyectoFacade.create(proy);
    }

    @PUT
    @Path("{id}")
    @Consumes({ "application/json"})
    public void edit(@PathParam("id") Long id, Proyecto entity) {
        proyectoFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        proyectoFacade.remove(proyectoFacade.find(id));
    }

     @GET
    @Path("{id}")
    @Produces({"application/json"})
    public String find(@PathParam("id") Long id) {
        Proyecto p = proyectoFacade.find(id);
        Gson conversor = new Gson();
        Usuario director = p.getDirector();
        director.setComentarioCollection(null);
        director.setProyectoCollection(null);
        director.setTareaCollection(null);
        
        if (p.getUsuarioCollection() != null) {
            for (Usuario u:p.getUsuarioCollection()) {
                u.setProyectoCollection(null);
                u.setComentarioCollection(null);
                u.setTareaCollection(null);
            }
        }

        p.setTareaCollection(null);
        return conversor.toJson(p);
    }

    @GET
    @Produces({"application/json"})
    public List<Proyecto> findAll() {
        return proyectoFacade.findAll();
    }
    
    @GET
    @Path("findProjectByName/{nombreP}")
    @Produces({"application/json"})
    public Proyecto findProjectByName(@PathParam("nombreP") String name) {
        return proyectoFacade.getProject(name);
    }
    
    @GET
    @Path("findProjectByIdUser/{idUsuario}")
    @Produces({"application/json"})
    public String findProjectByIdUser(@PathParam("idUsuario") Long idUsuario) {
        Usuario u = usuarioFacade.find(idUsuario);
        List<Proyecto> projectList = new ArrayList<>(u.getProyectoCollection());
  
        List<ProyectoREST> projectRESTList = new ArrayList<>();
        for (Proyecto p: projectList) {
            ProyectoREST pr = new ProyectoREST();
            pr.idProyect = p.getIdProyect();
            pr.descripcion = p.getDescripcion();
            pr.nombreP = p.getNombreP();
            projectRESTList.add(pr);
            
        }
        Gson gson = new Gson();
        
        return gson.toJson(projectRESTList);
    }
    

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Proyecto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return proyectoFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(proyectoFacade.count());
    }
    
    @GET
    @Path("countUser")
    @Produces("text/plain")
    public String countRESTUser(@PathParam("id") Long id) {
        return String.valueOf(proyectoFacade);
    }
    
     @GET
    @Path("getUsersEmailNonProject/{id}")
    @Produces("application/json")
    public String getUsersEmailNonProject(@PathParam("id") Long id) {
        List<String> usersEmail = usuarioFacade.getUsersEmail();
        Proyecto project = proyectoFacade.find(id);
        Collection<Usuario> usuarioCollection = project.getUsuarioCollection();
        
        for (Usuario user: usuarioCollection){
            if (usersEmail.contains(user.getEmail())) {
                usersEmail.remove(user.getEmail());
            }
        }
        
        Gson trad = new Gson();
        return trad.toJson(usersEmail);
        //return Arrays.asList(usersEmail);
        
    }
    
         @GET
    @Path("getUsersEmailProject/{id}")
    @Produces("application/json")
    public String getUsersEmailProject(@PathParam("id") Long id) {
        
        List<String> usersEmail = new ArrayList<>();
        Proyecto project = proyectoFacade.find(id);
        
        for (Usuario user: project.getUsuarioCollection()){
            usersEmail.add(user.getEmail());
        }
        
        Gson trad = new Gson();
        return trad.toJson(usersEmail);
        //return Arrays.asList(usersEmail);
        
    }
    
    @GET
    @Path("getUsersProject/{id}")
    @Produces("application/json")
    public String getUsersProject(@PathParam("id") Long id) {
        
        Proyecto project = proyectoFacade.find(id);
        Collection<Usuario> usuarioCollection = project.getUsuarioCollection();
        
        Gson trad = new Gson();
        return trad.toJson(usuarioCollection);
    } //return Arrays.asList(usersEmail);
    
    
    class ProyectoREST {
        public Long idProyect;
        public String descripcion;
        public String nombreP;
    }

    
}
