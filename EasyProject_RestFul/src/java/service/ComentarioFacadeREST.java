/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import EasyProject.ejb.ComentarioFacade;
import EasyProject.ejb.TareaFacade;
import EasyProject.ejb.UsuarioFacade;
import EasyProject.entities.Comentario;
import EasyProject.entities.Proyecto;
import EasyProject.entities.Tarea;
import EasyProject.entities.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author inftel11
 */
@Stateless
@Path("entity.comentario")
public class ComentarioFacadeREST {

    @EJB
    private TareaFacade tareaFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ComentarioFacade comentarioFacade;

    public ComentarioFacadeREST() {
        //super(Comentario.class);
    }

    @POST
    @Consumes({"application/json"})
    public void create(Comentario comentario) throws ParseException {

       //Gson gson=new Gson();
        //JSONObject j=new JSONObject(comentario);
        //String fecha=(String) j.get("fecha");
       /* SimpleDateFormat formatoDelTexto = new SimpleDateFormat(“dd//”);
        
         Date date=texto.parse(fecha);*/
        Date date = Calendar.getInstance().getTime();
        comentario.setFecha(date);

      //Comentario comment=gson.fromJson(comentario,Comentario.class);
        //comment.setFecha(date);
        comentarioFacade.create(comentario);
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
    public String find(@PathParam("id") Long id) throws ParseException {
        Comentario com = comentarioFacade.find(id);
        Gson parser = new Gson();
        String str = parser.toJson(com);
        JSONObject json = new JSONObject(str);
       
       /* SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fech=simpleDateFormat.parse(fecha.toString());
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern();
       
        System.out.println("La fecha es:"+fech);*/
        json.put("fecha", com.getFecha());
        System.out.println("Salida find :" + json.toString());
        return json.toString();
    }

    @GET
    @Produces({"application/json"})
    public List<Comentario> findAll() {
        return comentarioFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Comentario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return comentarioFacade.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(comentarioFacade.count());
    }

    /*protected Usuario reseteaUsuario (Usuario user) {                    
     user.setProyectoCollection(null);
     user.setTareaCollection(null);
     user.setComentarioCollection(null);
     return user;
     }*/
    @GET
    @Path("findComentsTask/{idTarea}")
    @Produces({"application/json"})
    public /*String*/ List<Comentario> findComentsTask(@PathParam("idTarea") Long idTarea) {
        List<Comentario> comments = new ArrayList<>();
        comments.addAll(tareaFacade.find(idTarea).getComentarioCollection());
        for (Comentario comment : comments) {
            Tarea task = comment.getIdTarea();
            task.setFicheroCollection(null);
            comment.setFecha(comment.getFecha());

            /*task.setUsuarioCollection(null);
             //task.setIdProyecto(null);
             task.setComentarioCollection(null);
             Proyecto proy = task.getIdProyecto();
             proy.setTareaCollection(null);
             proy.setUsuarioCollection(null);
             Usuario user = proy.getDirector();
             proy.setDirector(this.reseteaUsuario(user));
            
             user = comment.getIdUsuario();
             comment.setIdUsuario(this.reseteaUsuario(user));            
             user = task.getIdUsuario();
             task.setIdUsuario(this.reseteaUsuario(user));*/
        }
        /*Gson parser = new Gson();
         Type listOfTestObject = new TypeToken<List<Comentario>>(){}.getType();
         String str = parser.toJson(comments, listOfTestObject);
         //JSONObject json = new JSONObject(str);
        
         JSONArray jsonarray = new JSONArray(str);
         for (int i = 0; i < jsonarray.length(); i++) {
         JSONObject jsonobject = jsonarray.getJSONObject(i);
         jsonobject.put("fecha", comments.get(i).getFecha().getTime());
         }*/
        //comments = null;

        //System.out.println("Salida findComentsTask :" + jsonarray.toString());
        return comments/*jsonarray.toString()*/;
    }

}
