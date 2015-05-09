/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import beans.DocumentoBean;
import com.controller.CtrBusqueda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author Nico
 */
@ManagedBean
@Named
public class RespuestaDocumentos {

    @ManagedProperty(value = "#{CtrBusqueda}")
    private CtrBusqueda ctrB;

    public RespuestaDocumentos() {

    }

    public String getResponse() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DataTableDocumentos dO = new DataTableDocumentos();
        dO.setAaData(ctrB.getLista());
        String json = gson.toJson(dO);
        return json;
    }

}
