/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import beans.PosteoBean;

import entity.DocumentoEntity;
import entity.PosteoEntity;
import entity.VocabularioEntity;

/**
 *
 * @author user
 */
public class Posteo {

    PosteoEntity entidad;
    DocumentoEntity docE;
    VocabularioEntity vocE;
    

    public Posteo() {
        entidad = new PosteoEntity();
    }

    public Posteo(PosteoEntity entidad) {
        this.entidad = entidad;
    }

    public Posteo(PosteoBean posteo) {
        this();
        if (posteo.getId() != 0) {
            this.entidad.setId(posteo.getId());
        }
        entidad.setCantAparicionesTf(posteo.getCant_apariciones_tf());
        entidad.setDocumentoId(posteo.getDocumento_id());
        entidad.setVocabularioId(posteo.getVocabulario_id());
    }

    public int getId() {
        return entidad.getId();
    }

    public void setId(int id) {
        this.entidad.setId(id);
    }

    public DocumentoEntity getDocE() {
        return docE;
    }

    public void setDocE(DocumentoEntity docE) {
        this.docE = docE;
    }

    public VocabularioEntity getVocE() {
        return vocE;
    }

//    public int getDocumento_id() {
//        return entidad.getDocumentoId();
//    }
//
//    public void setDocumento_id(int documento_id) {
//        this.entidad.setDocumentoId(documento_id);
//    }
//
//    public int getVocabulario_id() {
//        return this.entidad.getVocabularioId();
//    }
//
//    public void setVocabulario_id(int vocabulario_id) {
//        this.entidad.setVocabularioId(vocabulario_id);
//    }
    public void setVocE(VocabularioEntity docV) {
        this.vocE = docV;
    }

    public int getCant_apariciones_tf() {
        return this.entidad.getCantAparicionesTf();
    }

    public void setCant_apariciones_tf(int cant_apariciones_tf) {
        this.entidad.setCantAparicionesTf(cant_apariciones_tf);
    }

    public PosteoEntity getEntidad() {
        return entidad;
    }

    public PosteoBean getBean() {
        PosteoBean resp = new PosteoBean(getId(), getCant_apariciones_tf(), null, null);
        return resp;
    }
}
