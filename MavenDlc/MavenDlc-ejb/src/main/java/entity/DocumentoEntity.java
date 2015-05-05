/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentoEntity.findAll", query = "SELECT d FROM DocumentoEntity d"),
    @NamedQuery(name = "DocumentoEntity.findById", query = "SELECT d FROM DocumentoEntity d WHERE d.id = :id"),
    @NamedQuery(name = "DocumentoEntity.findByNombre", query = "SELECT d FROM DocumentoEntity d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DocumentoEntity.findByUrl", query = "SELECT d FROM DocumentoEntity d WHERE d.url = :url"),
    @NamedQuery(name = "DocumentoEntity.findByModuloDoc", query = "SELECT d FROM DocumentoEntity d WHERE d.moduloDoc = :moduloDoc")})
public class DocumentoEntity implements Serializable, commons.DalEntity {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentoId")
    private List<PosteoEntity> posteoEntityList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modulo_doc")
    private float moduloDoc;

    public DocumentoEntity() {
    }

    public DocumentoEntity(Integer id) {
        this.id = id;
    }

    public DocumentoEntity(Integer id, String nombre, String url, float moduloDoc) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.moduloDoc = moduloDoc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getModuloDoc() {
        return moduloDoc;
    }

    public void setModuloDoc(float moduloDoc) {
        this.moduloDoc = moduloDoc;
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
        if (!(object instanceof DocumentoEntity)) {
            return false;
        }
        DocumentoEntity other = (DocumentoEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentoEntity[ id=" + id + " ]";
    }

    @XmlTransient
    public List<PosteoEntity> getPosteoEntityList() {
        return posteoEntityList;
    }

    public void setPosteoEntityList(List<PosteoEntity> posteoEntityList) {
        this.posteoEntityList = posteoEntityList;
    }
    
}
