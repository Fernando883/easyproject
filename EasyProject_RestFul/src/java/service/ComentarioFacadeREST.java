/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import EasyProject.ejb.ComentarioFacade;
import EasyProject.entities.Comentario;
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
@Path("entity.comentario")
public class ComentarioFacadeREST{
    
    @EJB
    private ComentarioFacade comentarioFacade;
    

    public ComentarioFacadeREST() {
        //super(Comentario.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(Comentario entity) {
        comentarioFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Comentario entity) {
        comentarioFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        comentarioFacade.remove(comentarioFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Comentario find(@PathParam("id") Long id) {
        return comentarioFacade.find(id);
    }

    @GET
    @Produces({ "application/json"})
    public List<Comentario> findAll() {
        return comentarioFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<Comentario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return comentarioFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(comentarioFacade.count());
    }
    
}
