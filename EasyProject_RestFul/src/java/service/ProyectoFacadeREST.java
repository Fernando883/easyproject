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
    public void create(Proyecto entity) {
        proyectoFacade.create(entity);
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
    public Proyecto find(@PathParam("id") Long id) {
        return proyectoFacade.find(id);
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
    public List<Proyecto> findProjectByIdUser(@PathParam("idUsuario") Long idUsuario) {
        Usuario u = usuarioFacade.find(idUsuario);
        List<Proyecto> p = (List<Proyecto>) u.getProyectoCollection();
        for (Proyecto p1 : p) {
            p1.setChat(null);
            p1.setTareaCollection(null);
            p1.setUsuarioCollection(null);
        }
        return p;
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
    
    

    
}
