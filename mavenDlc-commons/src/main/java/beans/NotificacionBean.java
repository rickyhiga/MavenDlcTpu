/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;

/**
 *
 * @author user
 */
public class NotificacionBean {
    DocumentoBean documento;
    long tiempoIndexacionMiliseg;
    int cantPalabras, cantNuevas, cantUpdates;

    public NotificacionBean(DocumentoBean documento, long tiempoIndexacionMiliseg, int cantPalabras, int cantNuevas, int cantUpdates) {
        this.documento=documento;
        this.tiempoIndexacionMiliseg = tiempoIndexacionMiliseg;
        this.cantPalabras = cantPalabras;
        this.cantNuevas = cantNuevas;
        this.cantUpdates = cantUpdates;
    }

    public DocumentoBean getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoBean documento) {
        this.documento = documento;
    }
    



    public long getTiempoIndexacionMiliseg() {
        return tiempoIndexacionMiliseg;
    }

    public void setTiempoIndexacionMiliseg(long tiempoIndexacionMiliseg) {
        this.tiempoIndexacionMiliseg = tiempoIndexacionMiliseg;
    }

    public int getCantUpdates() {
        return cantUpdates;
    }

    public void setCantUpdates(int cantUpdates) {
        this.cantUpdates = cantUpdates;
    }

    

    public int getCantPalabras() {
        return cantPalabras;
    }

    public void setCantPalabras(int cantPalabras) {
        this.cantPalabras = cantPalabras;
    }

    public int getCantNuevas() {
        return cantNuevas;
    }

    public void setCantNuevas(int cantNuevas) {
        this.cantNuevas = cantNuevas;
    }

    @Override
    public String toString() {
        return "Doc: " + documento.getNombre()+" # " + " T. Indexacion:" + tiempoIndexacionMiliseg/1000 + " seg - Cant. Palabras:" + cantPalabras;
    }

   
}
