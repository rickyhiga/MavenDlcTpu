/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import beans.NotificacionBean;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import static javax.ejb.TransactionManagementType.BEAN;
import javax.inject.Inject;
import javax.swing.text.Document;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Nico
 */
@Stateless
//@TransactionManagement(BEAN)
public class IndexadorFacade implements IndexadorFacadeRemote {

//    @Resource
//    private UserTransaction tra;
    @Inject
    private DocumentoDao docDao;
    @Inject
    private VocabularioDao vocDao;
    @Inject
    private PosteoDao posDao;

    @EJB
    VocabularioVolatilRemote vocRAM;

    HashMap<String, VocabularioBean> vocabulario;
    HashMap<String, Integer> temp;

    @Override
    public String saveCount(List<File> archivos) {
        //Por casa archivo
        StringBuilder st = new StringBuilder("Los siguientes archivos han sido coorrectamente procesados: \n");
        int cant = 0;
        long tiempoInicio = System.currentTimeMillis();
        vocabulario = vocRAM.getVocabulario();

        for (File archivo : archivos) {
            //Creo HashMap de las palabras del archivo guardando la frecuencia
            cant++;
            System.out.println("ARCHIVO " + cant + " de " + archivos.size());
            DocumentoBean docB = this.saveCountArch(archivo);
            if (docB != null) {
                System.out.println("-----NUEVO DOCUMENTO " + archivo.getName() + "-----");
                this.readFile(archivo);
                this.saveVocabularioPosteo(docB);
                st.append("-").append(archivo.getAbsolutePath()).append("\n");
                System.out.println("--------FIN DOCUMENTO " + archivo.getName() + "-------");
                
            } else {
                System.out.println("xxxxxxxxxxxx DOCUMENTO " + archivo.getName() + " YA PROCESADO");
            }
            long parcialT = System.currentTimeMillis() - tiempoInicio;
            System.out.println("Tiempo de procesamiento: " + parcialT / 1000 + " seg");

        }
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.out.println("*********************El tiempo de procesamiento de " + archivos.size() + "arcvhivos es :" + totalTiempo / 1000 + " seg");

        return st.toString();
//        return "hola";

    }

    private List<File> obtenerArchivosCarpeta(String url) {
        List<File> archivos = new ArrayList<>();
        File dir = new File(url);
        File[] directoryListing = dir.listFiles();
        int count = 0;
        if (directoryListing != null) {
            for (File child : directoryListing) {
//                count++;
//                if(count>1){
//                    break;
//                }
                archivos.add(child);
            }

            //  indE.fire(idx);
        } else {

            System.out.println("NO EXISTE LA CARPETA");
        }
        return archivos;
    }

    @Override
    public List<NotificacionBean> indexarArchivosDeCarpeta() {
        List<NotificacionBean> archivosIndexados = new ArrayList<>();
        int cant = 0;
        long tiempoInicio = System.currentTimeMillis();
        vocabulario = vocRAM.getVocabulario();
        
        List<File> archivos = this.obtenerArchivosCarpeta("C:\\IDE");
        for (File archivo : archivos) {
            //Creo HashMap de las palabras del archivo guardando la frecuencia
            cant++;
            System.out.println("ARCHIVO " + cant + " de " + archivos.size());
            DocumentoBean docB = this.saveCountArch(archivo);
            if (docB != null) {
                System.out.println("-----NUEVO DOCUMENTO " + archivo.getName() + "-----");
                long inicioIndex = System.currentTimeMillis();
                this.readFile(archivo);
                int[] valores=this.saveVocabularioPosteo(docB);
                long totalIndex = System.currentTimeMillis() - inicioIndex;
                NotificacionBean not = new NotificacionBean(docB, totalIndex, valores[0], valores[1], valores[2]);
                archivosIndexados.add(not);
                System.out.println("--------FIN DOCUMENTO " + archivo.getName() + "-------");
            } else {
                System.out.println("xxxxxxxxxxxx DOCUMENTO " + archivo.getName() + " YA PROCESADO");
            }
            long parcialT = System.currentTimeMillis() - tiempoInicio;
            System.out.println("Tiempo de procesamiento: " + parcialT / 1000 + " seg");
            
        }
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.out.println("*********************El tiempo de procesamiento de " + archivos.size() + "arcvhivos es :" + totalTiempo / 1000 + " seg");
        return archivosIndexados;
    }

    @Override
    public String leerArchivoDefault() {
        return "HOLA LEI UN ARCHIVO";
    }

    private void readFile(File f) {
        // Pattern pattern = Pattern.compile("[ñÑA-Za-záÁéÉíÍóÓúÚ][ñÑa-zA-ZáÁéÉíÍóÓúÚ]+");
        Pattern pattern = Pattern.compile("([A-Za-z])\\w+");

        temp = new HashMap<>(10000);
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
                        if (st.charAt(i) == '_' || st.charAt(i) == '0' || st.charAt(i) == '1' || st.charAt(i) == '2' || st.charAt(i) == '3' || st.charAt(i) == '4' || st.charAt(i) == '5' || st.charAt(i) == '6' || st.charAt(i) == '7' || st.charAt(i) == '8' || st.charAt(i) == '9') {
                            numero = true;
                            break;
                        }

                    }
                    if (!numero) {
                        String clave = st.toUpperCase();
                        if (!temp.containsKey(clave)) {
                            temp.put(clave, 1);
                            //   System.out.println("Nueva palabra " + clave);
                        } else {
                            int old = temp.get(clave);
                            //DESCOMENTAR LA SENTENCIA SEGUN JAVA QUE VERSION DE JAVA TENGAS
                            //Para JAVA 1.8
                            //hm.replace(clave, old + 1);
                            //Para JAVA 1.7
                            temp.remove(clave);
                            temp.put(clave, old + 1);
                            //  System.out.println("No clave");
                        }

                    }

                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se pudo leer el archivo");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private DocumentoBean saveCountArch(File archivo) {
        String urlFile = "";
        urlFile = archivo.getAbsolutePath();
        urlFile = urlFile.replace("\\", "/");
        docDao.openConnection();
        DocumentoBean docBean = docDao.buscarPorUrlSinAbrirCerrarConexion(urlFile);
        DocumentoEntity docE;
        if (docBean == null) {
            // docBean = new DocumentoBean(archivo.getName(), urlFile);
            docE = new DocumentoEntity(archivo.getName(), urlFile);
            // docE = new Documento(docBean).getEntidad();
            try {
                docDao.getCon().setAutoCommit(false);
            } catch (SQLException ex) {
                Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
            docDao.create(docE);
            docBean = new Documento(docE).getBean();
            return docBean;
        } else {
            return null;
        }
    }

    private int[] saveVocabularioPosteo(DocumentoBean docB) {

        long tiempoInicio = System.currentTimeMillis();
        int repeticiones, cantPalabras = 0, cantNew = 0;
        String termino;
        Iterator it = temp.entrySet().iterator();
        Map.Entry e;
        vocDao.setCon(docDao.getCon());
        posDao.setCon(vocDao.getCon());
        HashMap<String, VocabularioBean> modificados = new HashMap<>(5000);
        VocabularioBean vocB;
        VocabularioEntity vocE;

        PosteoEntity posE;

        while (it.hasNext()) {
            cantPalabras++;
            e = (Map.Entry) it.next();
            termino = String.valueOf(e.getKey());
            repeticiones = (int) e.getValue();
            vocB = null;
            vocB = vocabulario.get(termino);
            if (vocB == null) {
                //0 porque el indexador se encarga de ponerle 1
                vocE = new VocabularioEntity(termino, repeticiones, 0);
                cantNew++;
                vocDao.create(vocE);
                vocB = new Vocabulario(vocE).getBean();
                vocabulario.put(termino, vocB);
            } else {

                vocB.aparecioEnDoc();
                if (repeticiones > vocB.getMax_tf()) {
                    vocB.setMax_tf(repeticiones);
                    modificados.put(termino, vocB);

                }
                vocabulario.replace(termino, vocB);

            }
            posE = new PosteoEntity(repeticiones, vocB.getId(), docB.getId());
            posDao.create(posE);
        }
        try {
            posDao.getCon().setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(IndexadorFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        posDao.closeConnection();
        it = modificados.entrySet().iterator();
        while (it.hasNext()) {
            e = (Map.Entry) it.next();
            vocB = (VocabularioBean) e.getValue();
            vocDao.update(new Vocabulario(vocB).getEntidad());
        }
        vocRAM.setVocabulario(vocabulario);

        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.out.println("*********************Cant Palabras: " + cantPalabras + "-Nuevas: " + cantNew + "-Updates: " + modificados.size() + "-TIEMPO: " + totalTiempo / 1000 + " seg");
        int[] valores={cantPalabras, cantNew, modificados.size()};
        return valores;
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
