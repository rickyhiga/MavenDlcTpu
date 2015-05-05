/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import beans.DocumentoBean;
import entity.DocumentoEntity;



/**
 *
 * @author Nico
 */
public class Documento{
    DocumentoEntity entidad;
    public Documento(DocumentoEntity entidad) {
        this.entidad = entidad;
    }

    public Documento() {
        this.entidad = new DocumentoEntity();
    }

    public Documento(DocumentoBean documento) {
        this();
        if (documento.getId() != 0) {
            this.entidad.setId(documento.getId());
        }
        this.entidad.setNombre(documento.getNombre());
        this.entidad.setUrl(documento.getUrl());
        this.entidad.setModuloDoc(documento.getModuloDoc());

    }

    public int getId() {
        return this.entidad.getId();
    }

    public String getNombre() {
        return this.entidad.getNombre();
    }

    public void setNombre(String nombre) {
        this.entidad.setNombre(nombre);
    }

    public String getUrl() {
        return this.entidad.getUrl();
    }

    public void setUrl(String url) {
        this.entidad.setUrl(url);
    }

    public float getModuloDoc() {
        return this.entidad.getModuloDoc();
    }

    public void setModuloDoc(float moduloDoc) {
        this.entidad.setModuloDoc(moduloDoc);
    }


    public DocumentoBean getBean() {
        DocumentoBean resp=new DocumentoBean();
        resp.setId(getId());
        resp.setNombre(getNombre());
        resp.setUrl(getUrl());
        resp.setModuloDoc(getModuloDoc());
        return resp;
    }

    public DocumentoEntity getEntidad() {
        return entidad;
    }

    public void setEntidad(DocumentoEntity entidad) {
        this.entidad = entidad;
    }
    

}
