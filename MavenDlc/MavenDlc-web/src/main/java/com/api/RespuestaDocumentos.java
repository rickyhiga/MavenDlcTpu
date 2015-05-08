/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api;

import beans.DocumentoBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author Nico
 */
@ManagedBean
@Named
public class RespuestaDocumentos {
    private List<DocumentoBean> list;
    
    
    public RespuestaDocumentos(List<DocumentoBean> l)
    {
        list = l;
        
    }

    public String getResponse() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DataTableDocumentos dO = new DataTableDocumentos();
        dO.setAaData(list);
        String json = gson.toJson(dO);
        return json;
    }

 
    
    
    
    
    
    
}
