/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import beans.PosteoBean;
import beans.VocabularioBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import javax.ejb.Stateless;

/**
 *
 * @author Aldi Vaca
 */
@Stateless
public class BuscadorFacade implements BuscadorFacadeRemote {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    int cantDocs = 0;

    @Override
    public ArrayList<DocumentoBean> busqueda(String consulta) {
        String var;
        ArrayList<String> busqueda = new ArrayList<>(); //mi búsqueda en terminos parseados
        ArrayList<VocabularioBean> busquedaBeans = new ArrayList<>(); //mi búsqueda en VocuabularioBean
        ArrayList<PosteoBean> posteos = new ArrayList<>(); //mi lista de posteos en general
        ArrayList<Integer> idDocumentos = new ArrayList<>(); //mi lista de id de documentos ordenados por relevancia
        ArrayList<DocumentoBean> resultado = new ArrayList<>(); //lista de DocumentosBean ordenados
        HashMap<Integer, VocabularioBean> vocabulario = new HashMap<>();//obtengo el map de vocabularioBean

        //parseo la consulta
        Scanner in = new Scanner(consulta).useDelimiter(" ");
        while (in.hasNext()) {
            var = in.next();
            if (var.length() > 1) {
                var = var.toLowerCase();
                if (!busqueda.contains(var)) {
                    busqueda.add(var);
                }
            }
        }

        //obtengo los vocabularioBean de busqueda
        for (int i = 0; i < busqueda.size(); i++) {
            Iterator it = vocabulario.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                VocabularioBean vb = (VocabularioBean) e.getValue();
                if (vb.getTermino().equals(busqueda.get(i))) {
                    busquedaBeans.add(vb);
                }
            }
        }
        //ordeno los terminos para empezar con los posteos mas cortos y relevantes
        ordenarTerminos(busquedaBeans);

        //busco los posteos
        for (int i = 0; i < busquedaBeans.size(); i++) {
            int id = busquedaBeans.get(i).getId();//obtengo el id para hacer la consulta
            posteos.addAll(posteos);//agrego todos los posteos de la consulta
        }
        //obtengo los posteos

        idDocumentos = tratamientoPosteos(posteos);
        for (int i = 0; i < idDocumentos.size(); i++) {
            int idDoc = idDocumentos.get(i);
            DocumentoBean db = new DocumentoBean(); //busco el documentoBean con el id=idDoc y lo guardo aca
            resultado.add(db);
        }

        return resultado;
    }

    private ArrayList<Integer> tratamientoPosteos(ArrayList<PosteoBean> posteos) {
        ArrayList<Integer> documentosId = new ArrayList<>();

        HashMap<Integer, Double> docs = new HashMap<>();

        double[] valores = new double[posteos.size()];

        for (int i = 0; i < posteos.size(); i++) {
            valores[i] = posteos.get(i).getCant_apariciones_tf() * Math.log(cantDocs / posteos.get(i).getVocBean().getCant_doc());
        }

        for (int i = 0; i < posteos.size(); i++) {
            int documento = posteos.get(i).getDocumento_id();
            if (docs.containsKey(documento)) {
                double aux = docs.get(documento).doubleValue();
                docs.put(documento, (aux + valores[i]));
            } else {
                docs.put(documento, valores[i]);
            }
        }

        Entry<Integer, Double> maxEntry = null;

        while (docs.size() > 0) {
            for (Entry<Integer, Double> entry : docs.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                    maxEntry = entry;
                }
            }
            docs.remove(maxEntry.getKey());
            documentosId.add(maxEntry.getKey());
        }

        return documentosId;
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

}
