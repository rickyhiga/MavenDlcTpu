/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import beans.VocabularioBean;
import entity.VocabularioEntity;

/**
 *
 * @author user
 */
public class Vocabulario {
    VocabularioEntity entidad;

    public Vocabulario(VocabularioEntity entidad) {
        this.entidad = entidad;
    }

    public Vocabulario() {
        entidad= new VocabularioEntity();
    }
    
    public Vocabulario(VocabularioBean vocabulario){
        this();
        if(vocabulario.getId()!=0){
            this.entidad.setId(vocabulario.getId());
        }
        this.entidad.setTermino(vocabulario.getTermino());
        this.entidad.setCantDoc(vocabulario.getCant_doc());
        this.entidad.setMaxTf(vocabulario.getMax_tf());
        
    }
    public int getId(){
       return this.entidad.getId();
    }
    public void setId(int id){
        this.entidad.setId(id);
    }
     public int getCant_doc() {
        return this.entidad.getCantDoc();
    }

    public VocabularioEntity getEntidad() {
        return entidad;
    }

    public void setCant_doc(int cant_doc) {
        this.entidad.setCantDoc(cant_doc); 
    }

    public int getMax_tf() {
        return this.entidad.getMaxTf();
    }

    public void setMax_tf(int max_tf) {
       this.entidad.setMaxTf(max_tf);
    }

    public String getTermino() {
        return this.entidad.getTermino();
    }

    public void setTermino(String termino) {
        this.entidad.setTermino(termino);
    }
    public VocabularioBean getBean(){
        VocabularioBean resp=new VocabularioBean(getId(), getCant_doc(), getMax_tf(), getTermino());
        return resp;
    }
}