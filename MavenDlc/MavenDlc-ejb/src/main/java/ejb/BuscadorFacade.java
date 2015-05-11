/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import beans.PosteoBean;
import beans.VocabularioBean;
import daos.DocumentoDao;
import daos.PosteoDao;
import daos.VocabularioDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Aldi Vaca
 */
@Stateless
public class BuscadorFacade implements BuscadorFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    PosteoDao posDao;
    @Inject
    DocumentoDao docDao;
    @Inject
    VocabularioDao vocDao;
    @EJB
    VocabularioVolatilRemote vocRAM;

    int cantDocs = 0;

    @Override
    public ArrayList<DocumentoBean> busqueda(String consulta) {
        cantDocs = docDao.cantidadDocumentos();
        String var;
        ArrayList<String> busqueda = null; //mi búsqueda en terminos parseados
        ArrayList<VocabularioBean> busquedaBeans = null; //mi búsqueda en VocuabularioBean
        ArrayList<PosteoBean> posteos = null; //mi lista de posteos en general
        List<PosteoBean> auxPosteos = null; //mi lista de posteos en general
        ArrayList<DocumentoBean> resultado = null; //lista de DocumentosBean ordenados
        HashMap<String, VocabularioBean> vocabulario = vocRAM.getVocabulario();//obtengo el map de vocabularioBean

        long tiempoInicio = System.currentTimeMillis();

        //System.out.println("Busq RAM: " + vocabulario);
        //parseo la consulta
        //mejorar el delimitador comitas y eso
        if (consulta.length() > 0) {
            Scanner in = new Scanner(consulta).useDelimiter(" ");
            while (in.hasNext()) {
                var = in.next();
                if (var.length() > 1) {
                    var = var.toUpperCase();
                    if (busqueda == null) {
                        busqueda = new ArrayList<>();
                        busqueda.add(var);
                    } else {
                        if (!busqueda.contains(var)) {
                            busqueda.add(var);
                        }
                    }
                }
            }

            //obtengo los vocabularioBean de busqueda
            if (busqueda != null) {
                for (int i = 0; i < busqueda.size(); i++) {
                    VocabularioBean vb = (VocabularioBean) vocabulario.get(busqueda.get((i)));
                    if (vb != null && busquedaBeans == null) {
                        busquedaBeans = new ArrayList<>();
                    }
                    if (vb != null) {
                        busquedaBeans.add(vb);
                    }
                }

                if (busquedaBeans != null) {
                    //ordeno los terminos para empezar con los posteos mas cortos y relevantes
                    ordenarTerminos(busquedaBeans);

                    //busco los posteos
                    for (VocabularioBean busquedaBean : busquedaBeans) {
                        int id = busquedaBean.getId(); //obtengo el id para hacer la consulta
                        auxPosteos = posDao.obtenerPosteosPorIdVocabularioOrderByTf(id);//agrego todos los posteos de la consulta
                        if (auxPosteos != null && posteos == null) {
                            posteos = new ArrayList<>();
                        }
                        if (auxPosteos != null) {
                            for (PosteoBean pb : auxPosteos) {
                                posteos.add(pb);
                            }
                        }
                    }
                    //obtengo los posteos
                    if (posteos != null) {
                        resultado = tratamientoPosteos(posteos);
                    }
                }
            }
        }

        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.out.println("******El tiempo de busquedaentre " + posteos.size() + " posteos es de " + totalTiempo + " milisegundos*****");

        return resultado;
    }

    private ArrayList<DocumentoBean> tratamientoPosteos(ArrayList<PosteoBean> posteos) {
        HashMap<Integer, DocumentoBean> documentos = new HashMap<>();
        ArrayList<DocumentoBean> docByRank = null;

        for (PosteoBean posteo : posteos) {
            BigDecimal tf = BigDecimal.valueOf(posteo.getCant_apariciones_tf());
//            System.out.println("TF: " + tf);
            BigDecimal idf = BigDecimal.valueOf(Math.log10((double) cantDocs / (double) posteo.getVocBean().getCant_doc()));
//            System.out.println("Idf: " + idf + " es el log de " + cantDocs + " sobre " + posteo.getVocBean().getCant_doc());
            BigDecimal rank = idf.multiply(tf);
//            System.out.println("Rank: " + rank);
            DocumentoBean doc = posteo.getDocBean();
            doc.setPuntosRank(rank);
//            System.out.println("Documento Name " + doc.getNombre() + " Rank: " + rank);
            if (documentos.containsKey(doc.getId())) {
                rank.add(documentos.get(doc.getId()).getPuntosRank());
                doc.setPuntosRank(rank);
            } else {
                documentos.put(doc.getId(), doc);
            }
        }

        if (documentos.size() > 0) {
            docByRank = new ArrayList<>(documentos.values());
            ordenarDocs(docByRank);
        }

        return docByRank;
    }

    private void ordenarTerminos(ArrayList<VocabularioBean> original) {
        Collections.sort(original, new Comparator<VocabularioBean>() {
            @Override
            public int compare(VocabularioBean v1, VocabularioBean v2) {

                if (v1.getCant_doc() < v2.getCant_doc()) {
                    return -1;
                }
                if (v1.getCant_doc() == v2.getCant_doc()) {
                    return 0;
                }

                return 1;
            }
        });
    }

    private void ordenarDocs(ArrayList<DocumentoBean> original) {
        Collections.sort(original, new Comparator<DocumentoBean>() {
            @Override
            public int compare(DocumentoBean d1, DocumentoBean d2) {

                return d1.getPuntosRank().compareTo(d2.getPuntosRank());
            }
        });
    }

}
