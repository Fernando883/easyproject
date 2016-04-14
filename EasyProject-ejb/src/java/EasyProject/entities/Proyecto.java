/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
@Table(name = "PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByIdProyect", query = "SELECT p FROM Proyecto p WHERE p.idProyect = :idProyect"),
    @NamedQuery(name = "Proyecto.findByNombreP", query = "SELECT p FROM Proyecto p WHERE p.nombreP = :nombreP"),
    @NamedQuery(name = "Proyecto.findByDescripcion", query = "SELECT p FROM Proyecto p WHERE p.descripcion = :descripcion")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "idGenerator_proyecto", allocationSize = 1, sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator_proyecto")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROYECT")
    private Long idProyect;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE_P")
    private String nombreP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 499)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Lob
    @Column(name = "CHAT")
    private String chat;
    @JoinTable(name = "PROYECTO_USUARIO", joinColumns = {
        @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID_PROYECT")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")})
    @ManyToMany
    private Collection<Usuario> usuarioCollection;
    @JoinColumn(name = "DIRECTOR", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario director;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProyecto")
    private Collection<Tarea> tareaCollection;

    public Proyecto() {
    }

    public Proyecto(Long idProyect) {
        this.idProyect = idProyect;
    }

    public Proyecto(Long idProyect, String nombreP, String descripcion) {
        this.idProyect = idProyect;
        this.nombreP = nombreP;
        this.descripcion = descripcion;
    }

    public Long getIdProyect() {
        return idProyect;
    }

    public void setIdProyect(Long idProyect) {
        this.idProyect = idProyect;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    public Usuario getDirector() {
        return director;
    }

    public void setDirector(Usuario director) {
        this.director = director;
    }

    @XmlTransient
    public Collection<Tarea> getTareaCollection() {
        return tareaCollection;
    }

    public void setTareaCollection(Collection<Tarea> tareaCollection) {
        this.tareaCollection = tareaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProyect != null ? idProyect.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.idProyect == null && other.idProyect != null) || (this.idProyect != null && !this.idProyect.equals(other.idProyect))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EasyProject.entities.Proyecto[ idProyect=" + idProyect + " ]";
    }

    public Proyecto getClone() {
        Proyecto project = new Proyecto();
        project.descripcion = this.descripcion;
        project.idProyect = this.idProyect;
        List<Usuario> coleccion = (List<Usuario>) this.getUsuarioCollection();
        if (coleccion != null) {
            for (int i = 0; i < coleccion.size(); i++) {
                Usuario user = coleccion.get(i);
                Usuario nuevo = user.getClone();
                coleccion.set(i, nuevo);
            }
        }
        Usuario userClone = this.getDirector().getClone();
        project.setDirector(userClone);
        project.setUsuarioCollection(coleccion);
        return project;
    }

}
