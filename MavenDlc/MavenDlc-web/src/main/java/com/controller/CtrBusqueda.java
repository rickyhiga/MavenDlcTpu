/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import beans.DocumentoBean;
import com.api.DataTableDocumentos;
import com.api.RespuestaDocumentos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ejb.BuscadorFacadeRemote;
import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Nico
 */
@ManagedBean
@SessionScoped
public class CtrBusqueda {

    @EJB
    private BuscadorFacadeRemote buscadorBean;

    private String txtBusqueda;
    private ArrayList<DocumentoBean> lista;
    private DocumentoBean relevante1;
    private DocumentoBean relevante2;
    private DocumentoBean relevante3;
    private DocumentoBean relevante4;
    private DocumentoBean relevante5;
    private String resumenBusqueda = "";

    public CtrBusqueda() {
    }

    public String buscar() {
        System.out.println("**LOG: el texto: " + this.getTxtBusqueda());
        long tiempoInicio = System.currentTimeMillis();

        lista = buscadorBean.busqueda(this.getTxtBusqueda());
        if (lista != null) {
            if (lista.size() > 0) {
                relevante1 = lista.get(0);
            }
            if (lista.size() > 1) {
                relevante2 = lista.get(1);
            }
            if (lista.size() > 2) {
                relevante3 = lista.get(2);
            }
            if (lista.size() > 3) {
                relevante4 = lista.get(3);
            }
            if (lista.size() > 4) {
                relevante5 = lista.get(4);
            }
            System.out.println("**LOG: la lista trajo:" + lista.size());
        } else {
            lista = new ArrayList<>();
        }

        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        resumenBusqueda = lista.size() + " resultados de búsqueda en " + ((float) totalTiempo /(float) 1000) + " segundos";

        return "principal";

    }

    public String getTxtBusqueda() {
        System.out.println("***getbusqueda "+txtBusqueda);
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        System.out.println("***SETEEEO BUSQUEDA "+txtBusqueda);
        this.txtBusqueda = txtBusqueda;
    }

    public ArrayList<DocumentoBean> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DocumentoBean> lista) {
        this.lista = lista;
    }

    public DocumentoBean getRelevante1() {
        return relevante1;
    }

    public void setRelevante1(DocumentoBean relevante1) {
        this.relevante1 = relevante1;
    }

    public DocumentoBean getRelevante2() {
        return relevante2;
    }

    public void setRelevante2(DocumentoBean relevante2) {
        this.relevante2 = relevante2;
    }

    public DocumentoBean getRelevante3() {
        return relevante3;
    }

    public void setRelevante3(DocumentoBean relevante3) {
        this.relevante3 = relevante3;
    }

    public DocumentoBean getRelevante4() {
        return relevante4;
    }

    public void setRelevante4(DocumentoBean relevante4) {
        this.relevante4 = relevante4;
    }

    public DocumentoBean getRelevante5() {
        return relevante5;
    }

    public void setRelevante5(DocumentoBean relevante5) {
        this.relevante5 = relevante5;
    }

    public String getResumenBusqueda() {
        return resumenBusqueda;
    }

    public void setResumenBusqueda(String resumenBusqueda) {
        this.resumenBusqueda = resumenBusqueda;
    }

    public void renderJson() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DataTableDocumentos dO = new DataTableDocumentos();

        ArrayList<DocumentoBean> list;
        list = new ArrayList<>();

        if (null != this.lista) {
            list = this.lista;
        }

        int tam = list.size();
        dO.setiTotalRecords(tam);
        dO.setiTotalDisplayRecords(tam);

        dO.setAaData(list);
        String json = gson.toJson(dO);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/json");
        externalContext.setResponseCharacterEncoding("UTF-8");
        externalContext.getResponseOutputWriter().write(json);
        facesContext.responseComplete();
        this.lista = null;
        this.txtBusqueda = "";
        this.resumenBusqueda = "";
    }
     public String test(){
         return "hola";
     }

}
