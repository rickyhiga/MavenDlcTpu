/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class VocabularioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    int id, cant_doc = 0, max_tf = 0;
    String termino;

    public VocabularioBean(int id, int cant_doc, int max_tf, String termino) {
        this.id = id;
        this.cant_doc = cant_doc;
        this.max_tf = max_tf;
        this.termino = termino;
    }

    public VocabularioBean(String termino) {
        this.termino = termino;
    }

    public VocabularioBean(int cant_doc, int max_tf, String termino) {
        this.id = 0;
        this.cant_doc = cant_doc;
        this.max_tf = max_tf;
        this.termino = termino;
    }

    public VocabularioBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCant_doc() {
        return cant_doc;
    }

    public void setCant_doc(int cant_doc) {
        this.cant_doc = cant_doc;
    }

    public int getMax_tf() {
        return max_tf;
    }

    public void setMax_tf(int max_tf) {
        this.max_tf = max_tf;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }
//
    public void aparecioEnDoc(int repeticiones) {
        this.cant_doc++;
        if (repeticiones > this.getMax_tf()) {
            this.setMax_tf(repeticiones);
        }
    }

    @Override
    public String toString() {
        return "VocabularioBean{" + "id=" + id + ", cant_doc=" + cant_doc + ", max_tf=" + max_tf + ", termino=" + termino + '}';
    }

}
