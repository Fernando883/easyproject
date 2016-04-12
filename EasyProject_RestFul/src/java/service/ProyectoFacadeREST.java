/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.ProyectoFacade;
import EasyProject.ejb.TareaFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
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
    private TareaFacade tareaFacade;
    
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
    @Path("editProject/{id}")
    @Consumes({ "application/json"})
    public void edit(@PathParam("id") Long id, String json) {
       
       Gson gson = new Gson();
       
       Proyecto proy = proyectoFacade.find(id);
       Proyecto jsonProj = gson.fromJson(json, Proyecto.class);
       
       //set nombre
       if (!jsonProj.getNombreP().equals("")) {
           proy.setNombreP(jsonProj.getNombreP());
       } 
       
       //set description
       if (!jsonProj.getDescripcion().equals("")) {
           proy.setDescripcion(jsonProj.getDescripcion());
       }
       
        List<Usuario> addUsuarioCollection = new ArrayList<>();  
        JSONObject j = new JSONObject(json);
        String listAddEmails = (String) j.get("listAddEmails");
        
        // Lista de nuevos usuarios
        if (!listAddEmails.equals("")) {
            
            List<String> items = Arrays.asList(listAddEmails.split("\\s*,\\s*"));
            
            for (String item : items) {
//                Usuario u = new Usuario ();
                Usuario userAnterior = usuarioFacade.getUser(item);
                /*
                u.setEmail(userAnterior.getEmail());
                u.setIdUsuario(userAnterior.getIdUsuario());
                u.setNombreU(userAnterior.getNombreU());
                */
                //addUsuarioCollection.add(u);
                
                Collection<Proyecto> listaPro = userAnterior.getProyectoCollection();
                listaPro.add(proy);
                userAnterior.setProyectoCollection(listaPro);
                addUsuarioCollection.add(userAnterior);                
            }      
        }                
        // Borrado de usuarios
        List<Usuario> removeUsuarioCollection = new ArrayList<>();  
        String listRemoveEmails = (String) j.get("listRemoveEmails");
        if (!listRemoveEmails.equals("")) {
            
            List<String> items2 = Arrays.asList(listRemoveEmails.split("\\s*,\\s*"));
            
            for (String item : items2) {
                //Usuario u = new Usuario();
                Usuario userAnterior = usuarioFacade.getUser(item);
                /*
                u.setEmail(userAnterior.getEmail());
                u.setIdUsuario(userAnterior.getIdUsuario());
                u.setNombreU(userAnterior.getNombreU());
                 */
                boolean exist = false;
                
                for (Tarea task:proy.getTareaCollection()) {
                    if (task.getUsuarioCollection().contains(userAnterior)) {
                        exist = true;
                    }
                }
                if (!exist) {
                    userAnterior.getProyectoCollection().remove(proy);
                    removeUsuarioCollection.add(userAnterior);
                }

            }      
        }  
        
        //recogemos en una lista todos los usuarios de un proyecto
        List<Usuario> usersProject = new ArrayList<>();
        usersProject.addAll(proy.getUsuarioCollection());
        usersProject.addAll(addUsuarioCollection); //añadimos los usuarios nuevos
        usersProject.removeAll(removeUsuarioCollection); //eliminanos los usuarios 
        
        proy.setUsuarioCollection(null);
        proy.setUsuarioCollection(usersProject);
        
        proyectoFacade.edit(proy);
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Proyecto p  = proyectoFacade.find(id);
        
        for (Tarea task: p.getTareaCollection()) {
            task.setComentarioCollection(null);
            for (Usuario user: task.getUsuarioCollection()) {
                user.setProyectoCollection(null);
                user.setComentarioCollection(null);
                user.setTareaCollection(null);
            }               
        }
        p.setTareaCollection(null);

        for (Usuario user: p.getUsuarioCollection()) {
                user.setProyectoCollection(null);
                user.setComentarioCollection(null);
                user.setTareaCollection(null);
            }
        p.setUsuarioCollection(null);
        
        p.setDirector(null);

        proyectoFacade.remove(p);
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
    @Path("findInfoProject/{id}")
    @Produces({"application/json"})
    public String findInfoProject(@PathParam("id") Long id) {
        
        Proyecto p = new Proyecto ();
        Proyecto find = proyectoFacade.find(id);
        
        //le ponemos a p nombre y director
        p.setNombreP(find.getNombreP());
        p.setDescripcion(find.getDescripcion());
       
        //eliminamos lo que no nos interesa de director y se lo añadimos a p
        Usuario director = find.getDirector();
        director.setComentarioCollection(null);
        director.setProyectoCollection(null);
        director.setTareaCollection(null);
        p.setDirector(director);
        
        //Eliminanos lo que no interesa de usuariocollection y se lo ponemos a p
        if (find.getUsuarioCollection() != null) {
            for (Usuario u:find.getUsuarioCollection()) {
                u.setProyectoCollection(null);
                u.setComentarioCollection(null);
                u.setTareaCollection(null);
            }
        }
        p.setUsuarioCollection(find.getUsuarioCollection());
        
        //Lo pasamos todo a Json
        Gson conversor = new Gson(); 
        JSONObject json = new JSONObject(conversor.toJson(p));
        
        
        //Añadimos el parámetro adicional del número de tareas
        int numTasks = find.getTareaCollection().size();
        json.put("numTasks", numTasks);
        
        return json.toString();
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
            pr.numUsers = p.getUsuarioCollection().size();
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
        List<Usuario> usuarioCollection = (List<Usuario>) project.getUsuarioCollection();
          for (int i=0; i<usuarioCollection.size(); i++) {
            Usuario u = usuarioCollection.get(i);
            Usuario clon = u.getClone();
            usuarioCollection.set(i, clon);
        }
        
        Gson trad = new Gson();
        return trad.toJson(usuarioCollection);
    } //return Arrays.asList(usersEmail);
    
    @GET
    @Path("getProjectChat/{id}")
    @Produces("text/plain")
    public String getProjectChat(@PathParam("id") Long id) {
        Proyecto project = proyectoFacade.find(id);
        if (project.getChat() == null)
            return "null";
        
        return project.getChat();
    }
    
    
    class ProyectoREST {
        public Long idProyect;
        public String descripcion;
        public String nombreP;
        public int numUsers;
    }

    
}
