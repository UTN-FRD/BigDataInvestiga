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
import javax.validation.constraints.Size;

/**
 *
 * @author Sergio
 */
@Entity
@Table(name = "steps")
public class UserStep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(min = 1, max = 1000)
    @Column(name = "url")
    private String url;
    @Column(name = "fecha_creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreado;
    @Column(name = "id_investigacion")
    private long idInvestigacion;
    @Column(name = "id_usuario")
    private long idUsuario;

    public UserStep() {
    }

    public UserStep(Long userId, Long idInvestigacion, String url2Save) {
        this();
        this.fechaCreado = new Date();
        this.idUsuario = userId;
        this.idInvestigacion = idInvestigacion;
        this.url = url2Save;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public long getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(long idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
