/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import beans.PosteoBean;
import beans.VocabularioBean;
import business.Documento;
import business.Posteo;
import business.Vocabulario;
import daos.DocumentoDao;
import daos.PosteoDao;
import daos.VocabularioDao;
import entity.DocumentoEntity;
import entity.PosteoEntity;
import entity.VocabularioEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.JOptionPane;

/**
 *
 * @author Nico
 */
@Stateless
public class IndexadorFacade implements IndexadorFacadeRemote {

    @Inject
    private DocumentoDao docDao;
    @Inject
    private VocabularioDao vocDao;
    @Inject
    private PosteoDao posDao;

    @Override
    public int indexar() {
        DocumentoBean docB = obtenerDoc("Aca va el documento");
        if (docB == null) {
            docB = new DocumentoBean();
        } else {
            return 1;
        }
        VocabularioBean vocB = obtenerPalabra("aca va la palabra");
        if (vocB == null) {
            vocB = new VocabularioBean();
        }
        if (vocB.getId() != 0) {

        }
        return 0;

    }

    private DocumentoBean obtenerDoc(String url) {
        return null;
    }

    private VocabularioBean obtenerPalabra(String palabra) {
        return null;
    }

    private TempStore readFile(File archivo) {
        // Pattern pattern = Pattern.compile("[ñÑA-Za-záÁéÉíÍóÓúÚ][ñÑa-zA-ZáÁéÉíÍóÓúÚ]+");
        Pattern pattern = Pattern.compile("([A-Za-z])\\w+");
        File f;
        Scanner sc;
        TempStore t = new TempStore(1000);
        try {

            f = archivo;
            sc = new Scanner(f);
            while (sc.hasNext()) {
                Matcher matcher = pattern.matcher(sc.nextLine());
                while (matcher.find()) {
                    String st = matcher.group();
                    boolean numero = false;
                    for (int i = 0; i < st.length(); i++) {
                        //System.out.println(st.charAt(i));
                        if (st.charAt(i) == '_' || st.charAt(i) == '0' || st.charAt(i) == '1' || st.charAt(i) == '2' || st.charAt(i) == '3' || st.charAt(i) == '4' || st.charAt(i) == '5' || st.charAt(i) == '6' || st.charAt(i) == '7' || st.charAt(i) == '8' || st.charAt(i) == '9') {
                            numero = true;
                            break;
                        }

                    }
                    if (!numero) {
                        t.addCount(st.toLowerCase());
                    }

                }
            }
            System.out.println("Palabras en archivo " + f.getName() + ": " + t.getCantClaves());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo leer el archivo");
        }
        return t;
    }

    public String saveCount(List<File> archivos) {
        StringBuilder st = new StringBuilder("Los archivos siguientes archivos han sido coorrectamente procesados: \n");
        int idArch = 0;
//        this.a.openConnection();

        for (File archivo : archivos) {
//            if (!cancel) {

//                this.padre.setResArc(i);
//                this.padre.setProgreso(0);
            TempStore t = this.readFile(archivo);
            DocumentoBean docB = this.saveCountArch(archivo);
            
            this.saveVocabularioPosteo(docB, t);
            st.append("-" + archivo.getAbsolutePath() + "\n");
//            }

        }
//        this.a.closeConnection();
        return st.toString();
    }

    private DocumentoBean saveCountArch(File archivo) {
        //ARCHIVO
//        int idAr = 0, idRes = 0;
        //bar

        String urlFile = "";
        urlFile = archivo.getAbsolutePath();
//        idRes = a.getId(this.tablaArch, this.pkArch, "nombre=('" + nombreFile + "')");
        DocumentoBean docBean = docDao.buscarPorUrl(urlFile);
        if (docBean == null) {
            docBean = new DocumentoBean(archivo.getName(), urlFile);
            DocumentoEntity docE = new Documento(docBean).getEntidad();
            docDao.create(docE);
        }
        //debo manejar correctamente la excepcion
//        if(idRes == -1){
//            
//        }
//        if (idRes != -1) {
//            //Existe
//            String msg = "Ese archivo " + urlFile + " ya fue cargado";
//            JOptionPane.showMessageDialog(null, msg, "Error!", JOptionPane.INFORMATION_MESSAGE, null);
//
//        } else {
//            a.insertPoA(this.tablaArch, urlFile);
//            idRes = a.getMaxId(this.tablaArch, this.pkArch);
//            if (idRes != -1) {
//                idAr = idRes;
//
//            } else {
//                System.out.println("No se insertó el Archivo" + urlFile);
//                System.exit(0);
//            }
//        }

//        this.padre.setArchivo(archivo.getName());
        return docBean;
    }

    private int saveVocabularioPosteo(DocumentoBean docB, TempStore t) {
        Iterator<String> it = t.getIterator();
        
        int repeticiones, cantPalabras = 0;
        
        String clave;
       
        while (it.hasNext()) {
            cantPalabras++;
            clave = it.next();
            repeticiones = t.getCount(clave);
            VocabularioBean vocB = vocDao.buscarPorTermino(clave);
            if (vocB == null) {
                vocB = new VocabularioBean(1, repeticiones, clave);
                VocabularioEntity docE = new Vocabulario(vocB).getEntidad();
                vocDao.create(docE);
            }else{
                vocB.setCant_doc(vocB.getCant_doc()+1);
                if(repeticiones>vocB.getMax_tf()){
                    vocB.setMax_tf(repeticiones);
                }
                VocabularioEntity vocE=new Vocabulario(vocB).getEntidad();
                vocDao.update(vocE);
            }
            PosteoBean posteoB=new PosteoBean(repeticiones, vocB, docB);
            PosteoEntity posE=new Posteo(posteoB).getEntidad();
            posDao.create(posE);
            //BUSCAR SI EXISTE
//  idOld = a.getId(tablaP, this.pkP, "nombre=('" + clave + "')");
            //SI NO EXISTE INSERTAR, SI EXISTE ACTUALIZAR
//            if (idOld == -1) {
//                a.insertPoA(this.tablaP, clave);
//                idPa++;
//                idP = idPa;
//            } else {
//                idP = idOld;
//            }
//            a.insertPxA(idP, idAr, cant);
//            porcentaje = i * 100 / size;
//            padre.setProgreso(porcentaje);
        }
        return cantPalabras;
    }

//    private int tempIdWord() {
//        int i = a.getMaxId(this.tablaP, this.pkP);
//        if (i == -1) {
//            return 0;
//        }
//        return i;
//    }
    public class TempStore {

        private HashMap<String, Integer> hm;
        private int cant = 0;

        public TempStore() {
            hm = new HashMap();
        }

        public TempStore(int size) {
            hm = new HashMap(size);
        }

        public TempStore(int size, float loadFactor) {
            hm = new HashMap(size, loadFactor);
        }

        public void addCount(String clave) {
            if (!hm.containsKey(clave)) {
                hm.put(clave, 1);
                cant++;
                return;
            }
            int old = this.getCount(clave);
            //DESCOMENTAR LA SENTENCIA SEGUN JAVA QUE VERSION DE JAVA TENGAS
            //Para JAVA 1.8
            hm.replace(clave, old + 1);
            //Para JAVA 1.7
            // hm.remove(clave);
            //hm.put(clave, old+1);
        }

        public int getCount(String clave) {
            return hm.get(clave);
        }

        public int getCantClaves() {
            return cant;
        }

        public String toString() {
            Iterator<String> it = hm.keySet().iterator();
            StringBuilder st = new StringBuilder("HashMap A\n");
            while (it.hasNext()) {
                String clave = it.next();
                st.append("[");
                st.append(clave);
                st.append("]=");
                st.append(this.getCount(clave));
                st.append("\n");
            }
            return st.toString();
        }

        public Iterator<String> getIterator() {
            Iterator<String> it = hm.keySet().iterator();
            return it;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
