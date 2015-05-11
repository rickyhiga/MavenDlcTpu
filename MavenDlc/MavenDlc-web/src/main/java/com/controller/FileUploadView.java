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
import beans.DocumentoBean;
import ejb.BuscadorFacadeRemote;
import ejb.IndexadorFacadeRemote;
import ejb.VocabularioVolatilRemote;
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

@ManagedBean
public class FileUploadView {

    private String path = "C:\\IDE\\";

//    @EJB
//    private VocabularioVolatilRemote vocRAM;
    @EJB
    private BuscadorFacadeRemote buscadorBean;
    @EJB
    private IndexadorFacadeRemote idx;

//    private static void printFilesInFolder(Drive service, String folderId)
//            throws IOException {
//        Children.List request = service.children().list(folderId);
//
//        do {
//            try {
//                ChildList children = request.execute();
//
//                for (ChildReference child : children.getItems()) {
//                    System.out.println("File Id: " + child.getId());
//                }
//                request.setPageToken(children.getNextPageToken());
//            } catch (IOException e) {
//                System.out.println("An error occurred: " + e);
//                request.setPageToken(null);
//            }
//        } while (request.getPageToken() != null
//                && request.getPageToken().length() > 0);
//    }
    public void handleFileUpload(FileUploadEvent event) {
        ArrayList<File> lista = new ArrayList<>();
//        try {
//            File copyFile = copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
//            lista.add(copyFile);
//            FacesMessage message = new FacesMessage("Ok", event.getFile().getFileName() + " subido correctamente.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        File dir = new File("C:\\IDE");
        File[] directoryListing = dir.listFiles();
        int count = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
//                count++;
//                if(count>5){
//                    break;
//                }
                
                lista.add(child);
            }
            idx.saveCount(lista);
        } else {

            System.out.println("NO EXISTE LA CARPETA");
        }
//        File f = new File("C:\\IDE\\prueba.txt");
//        lista.add(f);
//        f = new File("C:\\IDE\\hola.txt");
//        lista.add(f);
//        f = new File("C:\\IDE\\quetal.txt");
//        lista.add(f);

//        idx.leerArchivoDefault();
        ArrayList<DocumentoBean> busqueda = buscadorBean.busqueda("que Una Guardas");
        System.out.println("BUSQUEDA");
        for (DocumentoBean b : busqueda) {
            System.out.println("NOMBRE: " + b.getNombre() + " por puntos: " + b.getPuntosRank());
            FacesMessage message = new FacesMessage("Ok", "NOMBRE: " + b.getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
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
