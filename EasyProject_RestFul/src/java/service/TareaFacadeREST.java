/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.TareaFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Comentario;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import EasyProject.entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
@Path("entity.tarea")
public class TareaFacadeREST {

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private TareaFacade tareaFacade;
    

    public TareaFacadeREST() {
        // super(Tarea.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(String json) {

        Tarea task = new Tarea();
        Gson gson = new Gson();
        List<Usuario> usuarioCollection = new ArrayList<>();

        task = gson.fromJson(json, Tarea.class);

        JSONObject j = new JSONObject(json);
        String listEmails = (String) j.get("listEmails");
        List<String> items = Arrays.asList(listEmails.split("\\s*,\\s*"));
        for (String item : items) {
            Usuario u = new Usuario();
            u.setEmail(usuarioFacade.getUser(item).getEmail());
            u.setIdUsuario(usuarioFacade.getUser(item).getIdUsuario());
            u.setNombreU(usuarioFacade.getUser(item).getNombreU());
            if (u != null) {
                usuarioCollection.add(u);
            }
        }
        task.setUsuarioCollection(usuarioCollection);
        tareaFacade.create(task);
    }

    @PUT
    @Path("editTask/{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, String json) {

        Tarea task = tareaFacade.find(id);
        Tarea task2 = new Tarea();
        Gson gson = new Gson();
        List<Usuario> usuarioCollection = new ArrayList<>();

        task2 = gson.fromJson(json, Tarea.class);

        if (!task2.getTiempo().equals("")) {
            task.setTiempo(task2.getTiempo());
        }

        if (!task2.getEstado().equals("")) {
            task.setEstado(task2.getEstado());
        }

        JSONObject j = new JSONObject(json);
        String listEmails = (String) j.get("listAddEmails");
        List<String> items = Arrays.asList(listEmails.split("\\s*,\\s*"));
        
        if (!listEmails.equals("")) {
            for (String item : items) {
                Usuario oldUser = usuarioFacade.getUser(item);
                  Collection<Tarea> listaTarea = oldUser.getTareaCollection();
                  listaTarea.add(task);
                  oldUser.setTareaCollection(listaTarea);
                  usuarioCollection.add(oldUser);
            }
        }
        
        List<Usuario> removeUsuarioCollection = new ArrayList<>();  
        String listRemoveEmails = (String) j.get("listRemoveEmails");
        if (!listRemoveEmails.equals("")) {
            
            List<String> items2 = Arrays.asList(listRemoveEmails.split("\\s*,\\s*"));
            
            for (String item : items2) {
                
                  Usuario oldUser = usuarioFacade.getUser(item);
                  oldUser.getTareaCollection().remove(task);
                  removeUsuarioCollection.add(oldUser);

            }      
        } 
        
        List<Usuario> usuarioCollection2 = new ArrayList<>();
        usuarioCollection2.addAll(task.getUsuarioCollection());
        usuarioCollection2.addAll(usuarioCollection);
        usuarioCollection2.removeAll(removeUsuarioCollection);
        
        task.setUsuarioCollection(null);
        task.setUsuarioCollection(usuarioCollection2);
        tareaFacade.edit(task);
    }

    @GET
    @Path("removeTask/{id}")
    public void remove(@PathParam("id") Long id) {
        tareaFacade.remove(tareaFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Tarea find(@PathParam("id") Long id) {
        return tareaFacade.find(id);
    }


    @GET
    @Produces({"application/json"})
    public List<Tarea> findAll() {
        return tareaFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tarea> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return tareaFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(tareaFacade.count());
    }
    
    @GET
    @Path("findTasksinProjectByIdUser/{idUsuario}/{idProyect}")
    @Produces({"application/json"})
    public String findTasksinProjectByIdUser(@PathParam("idUsuario") Long idUsuario, @PathParam("idProyect") Long idProyect) {
        
        Usuario u = usuarioFacade.find(idUsuario);
        List<Tarea> listaTareas = this.tareaFacade.findTareasUsuarioDeProyecto(u, idProyect);
        for (int i=0; i<listaTareas.size(); i++) {
            Tarea task = listaTareas.get(i);
            Tarea clon = task.getClone();
            listaTareas.set(i, clon);
        }
        
        Gson gson = new Gson();
        String salida = gson.toJson(listaTareas);
        return salida;
    }

    @GET
    @Path("getUsersEmailByTask/{idTask}")
    @Produces({"application/json"})
    public String getUsersEmailByTask(@PathParam("idTask") Long idTask) {
        
        Tarea task = tareaFacade.find(idTask);
        List<Usuario> usuarioCollection = (List<Usuario>) task.getUsuarioCollection();
    
        for (int i=0; i<usuarioCollection.size(); i++) {
            Usuario u = usuarioCollection.get(i);
            Usuario clon = u.getClone();
            usuarioCollection.set(i, clon);
        }
        
        Gson trad = new Gson();
        return trad.toJson(usuarioCollection);
    }
    
     @GET
    @Path("getUsersEmailByNonTask/{idTask}")
    @Produces({"application/json"})
    public String getUsersEmailByNonTask(@PathParam("idTask") Long idTask) {
        
        Tarea task = tareaFacade.find(idTask);
        Proyecto p = task.getIdProyecto();
        List<Usuario> userProject = (List<Usuario>) p.getUsuarioCollection();
        List<Usuario> usuarioCollection = (List<Usuario>) task.getUsuarioCollection();
        List<String> emailsUsers = new ArrayList<>();
        
        for (int  i = 0; i<userProject.size(); i++) {
            if (!usuarioCollection.contains(userProject.get(i))) {
                emailsUsers.add(userProject.get(i).getEmail());
            }     
        }
              
        Gson trad = new Gson();
        return trad.toJson(emailsUsers);
    }
}
