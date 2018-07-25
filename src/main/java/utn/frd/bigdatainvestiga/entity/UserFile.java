/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name = "files")
public class UserFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_subido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSubido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_investigacion")
    private long idInvestigacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario")
    private long idUsuario;

    public UserFile() {
    }

    public UserFile(Long id) {
        this.id = id;
    }

    public UserFile(Long id, String nombreArchivo, Date fechaSubido, long idInvestigacion, int estado, long idUsuario) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.fechaSubido = fechaSubido;
        this.idInvestigacion = idInvestigacion;
        this.estado = estado;
        this.idUsuario = idUsuario;
    }

    public UserFile(long idUsuario, long idInvestigacion, String fileName) {
        this.nombreArchivo = fileName;
        this.idInvestigacion = idInvestigacion;
        this.idUsuario = idUsuario;
        this.fechaSubido = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaSubido() {
        return fechaSubido;
    }

    public void setFechaSubido(Date fechaSubido) {
        this.fechaSubido = fechaSubido;
    }

    public long getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(long idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFile)) {
            return false;
        }
        UserFile other = (UserFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utn.frd.bigdatainvestiga.File[ id=" + id + " ]";
    }
    
}
