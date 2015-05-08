/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import beans.DocumentoBean;
import com.api.RespuestaDocumentos;
import ejb.BuscadorFacadeRemote;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author Nico
 */
@ManagedBean
@Named
public class CtrBusqueda {
    
    @EJB
    private BuscadorFacadeRemote buscadorBean;
    
    private String txtBusqueda;

    public CtrBusqueda() {
    }
    
    
    public String buscar()
    {
         ArrayList<DocumentoBean> busqueda = buscadorBean.busqueda("abajo");
         //RespuestaDocumentos rD = new RespuestaDocumentos(busqueda);
         return "listResultados";
         
    }

    public String getTxtBusqueda() {
        return txtBusqueda;
    }

    public void setTxtBusqueda(String txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }
    
    
    
}
