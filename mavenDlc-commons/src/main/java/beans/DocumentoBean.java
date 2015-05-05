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
public class DocumentoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id=0;
    private String nombre;
    private String url;
    //private float moduloDoc;
    private double puntosRank=0;

    public DocumentoBean() {
    }

    public DocumentoBean(int id, String nombre, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        //this.moduloDoc = moduloDoc;
    }

    public DocumentoBean(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
       // this.moduloDoc = moduloDoc;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPuntosRank() {
        return puntosRank;
    }

    public void setPuntosRank(double puntosRank) {
        this.puntosRank = puntosRank;
    }

//    public float getModuloDoc() {
//        return moduloDoc;
//    }
//
//    public void setModuloDoc(float moduloDoc) {
//        this.moduloDoc = moduloDoc;
//    }

    @Override
    public String toString() {
        return "DocumentoBean{" + "id=" + id + ", nombre=" + nombre + ", url=" + url ;
    }
}
