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
import commons.DetectorEncoding;
import daos.DocumentoDao;
import daos.PosteoDao;
import daos.VocabularioDao;
import entity.DocumentoEntity;
import entity.PosteoEntity;
import entity.VocabularioEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    public String saveCount(List<File> archivos) {
        StringBuilder st = new StringBuilder("Los siguientes archivos han sido coorrectamente procesados: \n");
        //Por casa archivo
        for (File archivo : archivos) {
            //Creo HashMap de las palabras del archivo guardando la frecuencia
            DocumentoBean docB = this.saveCountArch(archivo);
            HashMap<String, Integer> t = this.readFile(archivo);
            this.saveVocabularioPosteo(docB, t);
            st.append("-" + archivo.getAbsolutePath() + "\n");

        }

        return st.toString();
    }

    @Override
    public void leerArchivoDefault() {
        File archivo = new File("D:\\archivos_server\\18166-8.txt");
        DocumentoBean docB = this.saveCountArch(archivo);
        System.out.println("*****GUARDADO EL DOCUMENTO: " + docB);
        HashMap<String, Integer> hm = this.readFile(archivo);
        this.saveVocabularioPosteo(docB, hm);

    }

    private HashMap<String, Integer> readFile(File archivo) {
        // Pattern pattern = Pattern.compile("[ñÑA-Za-záÁéÉíÍóÓúÚ][ñÑa-zA-ZáÁéÉíÍóÓúÚ]+");
        Pattern pattern = Pattern.compile("([A-Za-z])\\w+");
        File f = new File("D:\\archivos_server\\18166-8.txt");

        HashMap<String, Integer> hm = new HashMap<>(1000);
        try {
            String charset = DetectorEncoding.getFileEncoding(f);
            FileInputStream fI = new FileInputStream(f);
            InputStreamReader iS = new InputStreamReader(fI, charset);
            Scanner sc = new Scanner(iS);
        
            
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
                        String clave = st.toLowerCase();
                        if (!hm.containsKey(clave)) {
                            hm.put(clave, 1);
                        } else {
                            int old = hm.get(clave);
                            //DESCOMENTAR LA SENTENCIA SEGUN JAVA QUE VERSION DE JAVA TENGAS
                            //Para JAVA 1.8
                            //hm.replace(clave, old + 1);
                            //Para JAVA 1.7
                            hm.remove(clave);
                            hm.put(clave, old + 1);
                        }

                    }

                }
            }
            System.out.println("Palabras en archivo " + f.getName() + ": " + hm.size());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo leer el archivo");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hm;
    }

    private DocumentoBean saveCountArch(File archivo) {
        String urlFile = "";
        urlFile = archivo.getAbsolutePath();
        DocumentoBean docBean = docDao.buscarPorUrl(urlFile);
        if (docBean == null) {
            docBean = new DocumentoBean(archivo.getName(), urlFile);
//            docBean = new DocumentoBean("unarchivo", "unaurl");
            DocumentoEntity docE = new Documento(docBean).getEntidad();

            docDao.create(docE);
            docBean.setId(docE.getId());
        }

        return docBean;
    }

    private int saveVocabularioPosteo(DocumentoBean docB, HashMap<String, Integer> hm) {

        int repeticiones, cantPalabras = 0;
        String termino;
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            cantPalabras++;
            Map.Entry e = (Map.Entry) it.next();
            termino = String.valueOf(e.getKey());
            repeticiones = (int) e.getValue();
            VocabularioBean vocB = vocDao.buscarPorTermino(termino);
            if (vocB == null) {
                vocB = new VocabularioBean(1, repeticiones, termino);
                VocabularioEntity vocE = new Vocabulario(vocB).getEntidad();
                vocDao.create(vocE);
                vocB.setId(vocE.getId());
            } else {
                vocB.setCant_doc(vocB.getCant_doc() + 1);
                if (repeticiones > vocB.getMax_tf()) {
                    vocB.setMax_tf(repeticiones);
                }
                VocabularioEntity vocE = new Vocabulario(vocB).getEntidad();
                vocDao.update(vocE);
            }
            PosteoBean posteoB = new PosteoBean(repeticiones, vocB, docB);
            PosteoEntity posE = new Posteo(posteoB).getEntidad();
            posDao.create(posE);
            posteoB.setId(posE.getId());

        }
        System.out.println("Cantidad de palabras ingresadas: " + cantPalabras);
        return cantPalabras;
    }

    public class TempStore {

        private HashMap<String, Integer> hm;

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
                return;
            }
            int old = this.getCount(clave);
            //DESCOMENTAR LA SENTENCIA SEGUN JAVA QUE VERSION DE JAVA TENGAS
            //Para JAVA 1.8
            //hm.replace(clave, old + 1);
            //Para JAVA 1.7
            hm.remove(clave);
            hm.put(clave, old + 1);
        }

        public int getCount(String clave) {
            return hm.get(clave);
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
