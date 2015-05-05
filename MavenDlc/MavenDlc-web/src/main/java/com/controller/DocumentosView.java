/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;



import beans.DocumentoBean;
import ejb.DocumentoFacadeRemote;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Nico
 */
@ManagedBean
@RequestScoped
public class DocumentosView implements Serializable{
    
    @EJB
    private DocumentoFacadeRemote doc;
    
    private List<DocumentoBean> docs;
    private String msg = "Hola este mensaje, viene de la view";
    
    @PostConstruct
    public void init()
    {
        docs = doc.listarDocumentos();
        System.out.println("***INICIANDO");
    }

    public List<DocumentoBean> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentoBean> docs) {
        this.docs = docs;
    }

    public DocumentosView()
    {
        
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public void newDocumento()
    {
        doc.insertarUnoDefault();
        doc.insertarUno(new DocumentoBean("documento desde la view", "que url"));
    }
    
    
    
    
    
    
    
    
}
