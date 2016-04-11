/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "TAREA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarea.findAll", query = "SELECT t FROM Tarea t"),
    @NamedQuery(name = "Tarea.findByIdTarea", query = "SELECT t FROM Tarea t WHERE t.idTarea = :idTarea"),
    @NamedQuery(name = "Tarea.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tarea.findByTiempo", query = "SELECT t FROM Tarea t WHERE t.tiempo = :tiempo"),
    @NamedQuery(name = "Tarea.findByEstado", query = "SELECT t FROM Tarea t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tarea.findByDescripcion", query = "SELECT t FROM Tarea t WHERE t.descripcion = :descripcion")})
public class Tarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "idGenerator_tarea", allocationSize = 1, sequenceName = "task_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator_tarea")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TAREA")
    private Long idTarea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIEMPO")
    private BigInteger tiempo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinTable(name = "TAREA_USUARIO", joinColumns = {
        @JoinColumn(name = "ID_TAREA", referencedColumnName = "ID_TAREA")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")})
    @ManyToMany (fetch = FetchType.LAZY)
    private Collection<Usuario> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTarea", fetch = FetchType.LAZY)
    private Collection<Comentario> comentarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTarea", fetch = FetchType.LAZY)
    private Collection<Fichero> ficheroCollection;
    @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECT")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proyecto idProyecto;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Tarea() {
    }

    public Tarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public Tarea(Long idTarea, String nombre, BigInteger tiempo, String estado, String descripcion) {
        this.idTarea = idTarea;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigInteger tiempo) {
        this.tiempo = tiempo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    @XmlTransient
    public Collection<Fichero> getFicheroCollection() {
        return ficheroCollection;
    }

    public void setFicheroCollection(Collection<Fichero> ficheroCollection) {
        this.ficheroCollection = ficheroCollection;
    }

    public Proyecto getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Proyecto idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarea != null ? idTarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.idTarea == null && other.idTarea != null) || (this.idTarea != null && !this.idTarea.equals(other.idTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EasyProject.entities.Tarea[ idTarea=" + idTarea + " ]";
    }
    
    public Tarea getClone () {
        Tarea task = new Tarea();
        task.descripcion = this.descripcion;
        task.estado = this.estado;
        task.nombre = this.nombre;
        task.tiempo = this.tiempo;
        List<Usuario> coleccion = (List<Usuario>)this.getUsuarioCollection();
        if (coleccion != null) {
            for (int i=0; i<coleccion.size();i++) {
                Usuario user = coleccion.get(i);
                Usuario nuevo = user.getClone();
                coleccion.set(i, nuevo);
            }
        }
        task.setUsuarioCollection(coleccion);
       
        //Collection listaUsuario = this.
        
        //if (this)
        return task;
    }
    
}
