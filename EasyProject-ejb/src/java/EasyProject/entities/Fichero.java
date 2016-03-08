/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EasyProject.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "FICHERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fichero.findAll", query = "SELECT f FROM Fichero f"),
    @NamedQuery(name = "Fichero.findByIdFichero", query = "SELECT f FROM Fichero f WHERE f.idFichero = :idFichero"),
    @NamedQuery(name = "Fichero.findByRuta", query = "SELECT f FROM Fichero f WHERE f.ruta = :ruta")})
public class Fichero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "idGenerator_fichero", allocationSize = 1, sequenceName = "fich_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator_fichero")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_FICHERO")
    private Long idFichero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "RUTA")
    private String ruta;
    @JoinColumn(name = "ID_TAREA", referencedColumnName = "ID_TAREA")
    @ManyToOne(optional = false)
    private Tarea idTarea;

    public Fichero() {
    }

    public Fichero(Long idFichero) {
        this.idFichero = idFichero;
    }

    public Fichero(Long idFichero, String ruta) {
        this.idFichero = idFichero;
        this.ruta = ruta;
    }

    public Long getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(Long idFichero) {
        this.idFichero = idFichero;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Tarea getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Tarea idTarea) {
        this.idTarea = idTarea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFichero != null ? idFichero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fichero)) {
            return false;
        }
        Fichero other = (Fichero) object;
        if ((this.idFichero == null && other.idFichero != null) || (this.idFichero != null && !this.idFichero.equals(other.idFichero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EasyProject.entities.Fichero[ idFichero=" + idFichero + " ]";
    }
    
}
