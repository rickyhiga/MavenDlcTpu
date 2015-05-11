/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nico
 */
@Named(value = "mostrarDocBean")
@SessionScoped
public class MostrarDocBean implements Serializable {

    /**
     * Creates a new instance of MostrarDocBean
     */
    public MostrarDocBean() {
    }
    
    public void rendertext() throws IOException {
    FacesContext fc = FacesContext.getCurrentInstance();
    ExternalContext ec = fc.getExternalContext();
    
    Map<String, String> params = ec.getRequestParameterMap();
    String url = params.get("url"); // Returns request parameter with name "foo".
        System.out.println("***EL PARAM: "+url);
    
    
                    BufferedReader br = null;
                        String sCurrentLine;
                        StringBuilder sB = new StringBuilder();
 
			br = new BufferedReader(new FileReader(url));
 
			while ((sCurrentLine = br.readLine()) != null) {
				sB.append(sCurrentLine);
                                sB.append("\n");
			}
    
    ec.setResponseContentType("text/plain");
    ec.setResponseCharacterEncoding("UTF-8");
    ec.getResponseOutputWriter().write(sB.toString());
    // ...

    fc.responseComplete(); // Important! Prevents JSF from proceeding to render HTML.
}
    
}
