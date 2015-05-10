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

    public CtrBusqueda() {
    }

    public String buscar() {
        System.out.println("**LOG NICO: el texto: " + this.getTxtBusqueda());
        lista = buscadorBean.busqueda(this.getTxtBusqueda());
        System.out.println("**LOG NICO: la lista trajo:" + lista.size());
        return "principal";

    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public ArrayList<DocumentoBean> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DocumentoBean> lista) {
        this.lista = lista;
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
    }

}
