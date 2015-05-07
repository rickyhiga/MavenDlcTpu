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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
    int cantDocs = 0;

    @Override
    public ArrayList<DocumentoBean> busqueda(String consulta) {
        cantDocs = docDao.cantidadDocumentos();
        String var;
        ArrayList<String> busqueda = new ArrayList<>(); //mi búsqueda en terminos parseados
        ArrayList<VocabularioBean> busquedaBeans = new ArrayList<>(); //mi búsqueda en VocuabularioBean
        ArrayList<PosteoBean> posteos = new ArrayList<>(); //mi lista de posteos en general
        List<PosteoBean> auxPosteos = new ArrayList<>(); //mi lista de posteos en general
        ArrayList<DocumentoBean> resultado = new ArrayList<>(); //lista de DocumentosBean ordenados
        HashMap<String, VocabularioBean> vocabulario = vocDao.listarTodosMap();//obtengo el map de vocabularioBean

        //parseo la consulta
        //mejorar el delimitador comitas y eso
        Scanner in = new Scanner(consulta).useDelimiter(" ");
        while (in.hasNext()) {
            var = in.next();
            if (var.length() > 1) {
                var = var.toUpperCase();
                if (!busqueda.contains(var)) {
                    busqueda.add(var);
                }
            }
        }

        //obtengo los vocabularioBean de busqueda
        for (int i = 0; i < busqueda.size(); i++) {
            VocabularioBean vb = (VocabularioBean) vocabulario.get(busqueda.get((i)));
            if (vb != null) {
                busquedaBeans.add(vb);
            }
        }

        //ordeno los terminos para empezar con los posteos mas cortos y relevantes
        ordenarTerminos(busquedaBeans);

        //busco los posteos
        for (int i = 0; i < busquedaBeans.size(); i++) {
            int id = busquedaBeans.get(i).getId();//obtengo el id para hacer la consulta
            auxPosteos = posDao.obtenerPosteosPorIdVocabulario(id);//agrego todos los posteos de la consulta
            for (PosteoBean pb : auxPosteos) {
                posteos.add(pb);
            }
        }
        //obtengo los posteos

        System.out.println("Posteos beans: " + posteos.size());
        resultado = tratamientoPosteos(posteos);

        return resultado;
    }

    private ArrayList<DocumentoBean> tratamientoPosteos(ArrayList<PosteoBean> posteos) {
        HashMap<Integer, DocumentoBean> documentos = new HashMap<>();

        for (int i = 0; i < posteos.size(); i++) {
            double rank = posteos.get(i).getCant_apariciones_tf() * Math.log(cantDocs / posteos.get(i).getVocBean().getCant_doc());
            DocumentoBean doc = posteos.get(i).getDocBean();
            doc.setPuntosRank(rank);
            if (documentos.containsKey(doc.getId())) {
                rank += documentos.get(doc.getId()).getPuntosRank();
                doc.setPuntosRank(rank);
            } else {
                documentos.put(doc.getId(), doc);
            }
        }

        ArrayList<DocumentoBean> docByRank = new ArrayList<DocumentoBean>(documentos.values());
        ordenarDocs(docByRank);

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

                if (d1.getPuntosRank() > d2.getPuntosRank()) {
                    return -1;
                }
                if (d1.getPuntosRank() == d2.getPuntosRank()) {
                    return 0;
                }

                return 1;
            }
        });
    }

}
