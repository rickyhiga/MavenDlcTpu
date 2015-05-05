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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "PosteoEntity.findByCantAparicionesTf", query = "SELECT p FROM PosteoEntity p WHERE p.cantAparicionesTf = :cantAparicionesTf")})
public class PosteoEntity implements Serializable {
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
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DocumentoEntity documentoId;
    @JoinColumn(name = "vocabulario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VocabularioEntity vocabularioId;

    public PosteoEntity() {
    }

    public PosteoEntity(Integer id) {
        this.id = id;
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

    public DocumentoEntity getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(DocumentoEntity documentoId) {
        this.documentoId = documentoId;
    }

    public VocabularioEntity getVocabularioId() {
        return vocabularioId;
    }

    public void setVocabularioId(VocabularioEntity vocabularioId) {
        this.vocabularioId = vocabularioId;
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
        return "entity.PosteoEntity[ id=" + id + " ]";
    }
    
}
