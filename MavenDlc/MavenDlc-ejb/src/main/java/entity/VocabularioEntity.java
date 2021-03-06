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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "vocabulario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VocabularioEntity.findAll", query = "SELECT v FROM VocabularioEntity v"),
    @NamedQuery(name = "VocabularioEntity.findById", query = "SELECT v FROM VocabularioEntity v WHERE v.id = :id"),
    @NamedQuery(name = "VocabularioEntity.findByTermino", query = "SELECT v FROM VocabularioEntity v WHERE v.termino = :termino"),
    @NamedQuery(name = "VocabularioEntity.findByMaxTf", query = "SELECT v FROM VocabularioEntity v WHERE v.maxTf = :maxTf"),
    @NamedQuery(name = "VocabularioEntity.findByCantDoc", query = "SELECT v FROM VocabularioEntity v WHERE v.cantDoc = :cantDoc")})
public class VocabularioEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "termino")
    private String termino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_tf")
    private int maxTf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cant_doc")
    private int cantDoc;

    public VocabularioEntity() {
    }

    public VocabularioEntity(Integer id) {
        this.id = id;
    }

    public VocabularioEntity(Integer id, String termino, int maxTf, int cantDoc) {
        this.id = id;
        this.termino = termino;
        this.maxTf = maxTf;
        this.cantDoc = cantDoc;
    }

    public VocabularioEntity( String termino, int maxTf, int cantDoc) {
        this.termino = termino;
        this.maxTf = maxTf;
        this.cantDoc = cantDoc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public int getMaxTf() {
        return maxTf;
    }

    public void setMaxTf(int maxTf) {
        this.maxTf = maxTf;
    }

    public int getCantDoc() {
        return cantDoc;
    }

    public void setCantDoc(int cantDoc) {
        this.cantDoc = cantDoc;
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
        if (!(object instanceof VocabularioEntity)) {
            return false;
        }
        VocabularioEntity other = (VocabularioEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VocabularioEntity[ id=" + id + " ]";
    }

}
