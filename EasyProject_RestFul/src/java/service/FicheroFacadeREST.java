/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.FicheroFacade;
import EasyProject.entities.Fichero;
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
@Path("entity.fichero")
public class FicheroFacadeREST {
    
    @EJB
    private FicheroFacade ficheroFacade;
   

    public FicheroFacadeREST() {
     //   super(Fichero.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(Fichero entity) {
        ficheroFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Fichero entity) {
        ficheroFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        ficheroFacade.remove(ficheroFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Fichero find(@PathParam("id") Long id) {
        return ficheroFacade.find(id);
    }

    @GET
    @Produces({"application/json"})
    public List<Fichero> findAll() {
        return ficheroFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Fichero> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return ficheroFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(ficheroFacade.count());
    }

    
}
