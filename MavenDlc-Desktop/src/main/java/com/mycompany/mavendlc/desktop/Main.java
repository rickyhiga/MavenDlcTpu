package com.mycompany.mavendlc.desktop;

import ejb.DocumentoFacadeRemote;
import javax.ejb.EJB;

/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    @EJB
    private static DocumentoFacadeRemote doc;
    
    public static void main(String[] args) {
       
        System.out.println(doc.listarDocsString());
//        DocumentoBean docBean=new DocumentoBean("Test2", "unaurl2", (float) 25.555);
//        System.out.println(doc.insertarUno(docBean));
        System.out.println(doc.insertarUnoDefault());
        System.out.println(doc.listarDocsString());
    }
}
