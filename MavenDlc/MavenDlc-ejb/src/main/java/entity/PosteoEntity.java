/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "posteo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PosteoEntity.findAll", query = "SELECT p FROM PosteoEntity p"),
    @NamedQuery(name = "PosteoEntity.findById", query = "SELECT p FROM PosteoEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PosteoEntity.findByVocabularioId", query = "SELECT p FROM PosteoEntity p WHERE p.vocabularioId = :id"),
    @NamedQuery(name = "PosteoEntity.findByCantAparicionesTf", query = "SELECT p FROM PosteoEntity p WHERE p.cantAparicionesTf = :cantAparicionesTf")})
public class PosteoEntity implements Serializable, commons.DalEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_apariciones_tf")
    private int cantAparicionesTf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vocabulario_id")
    private int vocabularioId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "documento_id")
    private int documentoId;

    public PosteoEntity() {
    }

    public PosteoEntity(Integer id) {
        this.id = id;
    }

    public PosteoEntity(Integer id, int cantAparicionesTf, int vocabularioId, int documentoId) {
        this.id = id;
        this.cantAparicionesTf = cantAparicionesTf;
        this.vocabularioId = vocabularioId;
        this.documentoId = documentoId;
    }

    public PosteoEntity(Integer id, int cantAparicionesTf) {
        this.id = id;
        this.cantAparicionesTf = cantAparicionesTf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantAparicionesTf() {
        return cantAparicionesTf;
    }

    public void setCantAparicionesTf(int cantAparicionesTf) {
        this.cantAparicionesTf = cantAparicionesTf;
    }

    public int getVocabularioId() {
        return vocabularioId;
    }

    public void setVocabularioId(int vocabularioId) {
        this.vocabularioId = vocabularioId;
    }

    public int getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(int documentoId) {
        this.documentoId = documentoId;
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
        if (!(object instanceof PosteoEntity)) {
            return false;
        }
        PosteoEntity other = (PosteoEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PosteoEntity{" + "id=" + id + ", cantAparicionesTf=" + cantAparicionesTf + ", vocabularioId=" + vocabularioId + ", documentoId=" + documentoId + '}';
    }

}
