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
    int id, cant_apariciones_tf;
    VocabularioBean vocBean;
    DocumentoBean docBean;
    

    

    public PosteoBean(int cant_apariciones_tf, VocabularioBean vocBean, DocumentoBean docBean) {
        this.cant_apariciones_tf = cant_apariciones_tf;
        this.vocBean = vocBean;
        this.docBean = docBean;
    }

    public PosteoBean(int id, int cant_apariciones_tf, VocabularioBean vocBean, DocumentoBean docBean) {
        this.id = id;
        this.cant_apariciones_tf = cant_apariciones_tf;
        this.vocBean = vocBean;
        this.docBean = docBean;
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
        return docBean.getId();
    }

    public void setDocumento_id(int documento_id) {
        this.docBean.setId(documento_id);
    }

    public int getVocabulario_id() {
        return vocBean.getId();
    }

    public void setVocabulario_id(int vocabulario_id) {
        this.vocBean.setId(vocabulario_id);
    }

    public int getCant_apariciones_tf() {
        return cant_apariciones_tf;
    }

    public void setCant_apariciones_tf(int cant_apariciones_tf) {
        this.cant_apariciones_tf = cant_apariciones_tf;
    }

    @Override
    public String toString() {
        return "PosteoBean{" + "id=" + id + ", documento_id=" + getDocumento_id() + ", vocabulario_id=" + getVocabulario_id() + ", cant_apariciones_tf=" + cant_apariciones_tf + '}';
    }
    
    
}
