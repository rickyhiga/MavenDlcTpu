/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author user
 */
public class PosteoBean {
    int id, documento_id,vocabulario_id, cant_apariciones_tf;
    VocabularioBean vocBean;
    DocumentoBean docBean;
    public PosteoBean(int documento_id, int vocabulario_id, int cant_apariciones_tf) {
        this.documento_id = documento_id;
        this.vocabulario_id = vocabulario_id;
        this.cant_apariciones_tf = cant_apariciones_tf;
    }

    public PosteoBean(int id, int documento_id, int vocabulario_id, int cant_apariciones_tf) {
        this.id = id;
        this.documento_id = documento_id;
        this.vocabulario_id = vocabulario_id;
        this.cant_apariciones_tf = cant_apariciones_tf;
    }

    public PosteoBean(int id, VocabularioBean vocBean, DocumentoBean docBean, int cant_apariciones_tf) {
        this.id = id;
        this.vocBean = vocBean;
        this.vocabulario_id=vocBean.getId();
        this.docBean = docBean;
        this.documento_id=docBean.getId();
        this.cant_apariciones_tf=cant_apariciones_tf;
    }

    public VocabularioBean getVocBean() {
        return vocBean;
    }

    public void setVocBean(VocabularioBean vocBean) {
        this.vocBean = vocBean;
    }

    public DocumentoBean getDocBean() {
        return docBean;
    }

    public void setDocBean(DocumentoBean docBean) {
        this.docBean = docBean;
    }
    

    public PosteoBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(int documento_id) {
        this.documento_id = documento_id;
    }

    public int getVocabulario_id() {
        return vocabulario_id;
    }

    public void setVocabulario_id(int vocabulario_id) {
        this.vocabulario_id = vocabulario_id;
    }

    public int getCant_apariciones_tf() {
        return cant_apariciones_tf;
    }

    public void setCant_apariciones_tf(int cant_apariciones_tf) {
        this.cant_apariciones_tf = cant_apariciones_tf;
    }

    @Override
    public String toString() {
        return "PosteoBean{" + "id=" + id + ", documento_id=" + documento_id + ", vocabulario_id=" + vocabulario_id + ", cant_apariciones_tf=" + cant_apariciones_tf + '}';
    }
    
    
}
