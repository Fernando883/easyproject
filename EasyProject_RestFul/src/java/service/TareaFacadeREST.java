/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.TareaFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import EasyProject.entities.Usuario;
import java.util.ArrayList;
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
    public void create(Tarea entity) {
        tareaFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Tarea entity) {
        tareaFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
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
    public List<Tarea> findTasksinProjectByIdUser(@PathParam("idUsuario") Long idUsuario, @PathParam("idProyect") Long idProyect) {
        Usuario u = usuarioFacade.find(idUsuario);
        List<Tarea> taskList = new ArrayList<>();
       
        taskList.addAll(u.getTareaCollection());
        /*for (Proyecto p : u.getProyectoCollection()) {
            if (p.getIdProyect().equals(idProyect)) {
                taskList.addAll(p.getTareaCollection());
            }
        }*/
        
        for (Tarea task : taskList) {
            if (task.getIdProyecto().getIdProyect() != idProyect) {
                taskList.remove(task);
            } else {
                task.setComentarioCollection(null);
                task.setFicheroCollection(null);
                task.setDescripcion(null);
            }

        }
        return taskList;
    }
    

}
