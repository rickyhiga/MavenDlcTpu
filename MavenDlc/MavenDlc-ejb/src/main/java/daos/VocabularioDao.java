/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.VocabularioBean;
import business.Vocabulario;
import commons.DaoEclipseLink;
import db.DBAccessMySql;
import entity.DocumentoEntity;
//import entity.DocumentoEntity;
import entity.VocabularioEntity;
import exceptions.TechnicalException;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author user
 */
public class VocabularioDao extends DBAccessMySql {

    @PersistenceContext(name = "MavenDlc-ejbPU")
    private EntityManager entityManager;
    String tabla = "vocabulario";
    String[] columnas = {"termino", "cant_doc", "max_tf"};

    public VocabularioBean buscarPorTermino(final String termino) {
        VocabularioBean vocB = null;
        Query query = this.entityManager.createNamedQuery("VocabularioEntity.findByTermino").setParameter("termino", termino);
        List<VocabularioEntity> lista = (List<VocabularioEntity>) query.getResultList();
        if (lista.size() > 0) {
            VocabularioEntity docE = lista.get(0);
            vocB = new Vocabulario(docE).getBean();
        }
        return vocB;
    }

    public VocabularioBean buscarPorId(final int id) {
        VocabularioBean vocB = null;
        Query query = this.entityManager.createNamedQuery("VocabularioEntity.findById").setParameter("id", id);
        List<VocabularioEntity> lista = (List<VocabularioEntity>) query.getResultList();
        if (lista.size() > 0) {
            VocabularioEntity docE = lista.get(0);
            vocB = new Vocabulario(docE).getBean();
        }
        return vocB;
    }

    public HashMap<String, VocabularioBean> listarTodosMap() {
        VocabularioBean vocB = null;
        HashMap<String, VocabularioBean> mapa = new HashMap<>();
        Query query = this.entityManager.createNamedQuery("VocabularioEntity.findAll");
        List<VocabularioEntity> lista = (List<VocabularioEntity>) query.getResultList();

        for (VocabularioEntity vocEnt : lista) {
            vocB = new Vocabulario(vocEnt).getBean();
            mapa.put(vocB.getTermino(), vocB);
        }
        return mapa;
    }

    public VocabularioEntity create(VocabularioEntity vocE) {

        String[] values = {vocE.getTermino(), "" + vocE.getCantDoc(), "" + vocE.getMaxTf()};

        int id = this.insertarSinAbrirCerrarConexion(tabla, columnas, values);
        vocE.setId(id);
        return vocE;
//        StringBuilder st = new StringBuilder("");
//        st.append("INSERT INTO vocabulario(termino, cant_doc, max_tf) VALUES(");
//        st.append(vocE.getTermino());
//        st.append(", ");
//        st.append(vocE.getCantDoc());
//        st.append(", ");
//        st.append(vocE.getMaxTf());
//        st.append(");");
//        super.setQuery(st.toString());
//        super.executeSingleQuery();

    }

    public void update(VocabularioEntity vocE) {
        String[] columns = { "max_tf"};
        String[] values = {  "" + vocE.getMaxTf()};

        super.actualizarSinAbrirCerrarConexion(tabla, columns, values, "id="+vocE.getId());
    }

}
