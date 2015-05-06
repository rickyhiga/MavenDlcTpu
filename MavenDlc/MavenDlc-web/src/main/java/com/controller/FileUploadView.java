package com.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nico
 */
import ejb.IndexadorFacadeRemote;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
 private String path=".\\archivos_server\\";
    
 @EJB
 private IndexadorFacadeRemote idx;
 
 public void handleFileUpload(FileUploadEvent event) {
        ArrayList<File> lista = new ArrayList<>();
        try {
            File copyFile = copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            lista.add(copyFile);
            FacesMessage message = new FacesMessage("Ok", event.getFile().getFileName() + " subido correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        idx.saveCount(lista);
        
    }
    
    
    public File copyFile(String fileName, InputStream in) {
           try {
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(path + fileName));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
                out.close();
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
           
           return new File(path + fileName);
    }
    
    
    
}