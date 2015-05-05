/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.VocabularioBean;
import business.Vocabulario;
import commons.DaoEclipseLink;
import entity.DocumentoEntity;
//import entity.DocumentoEntity;
import entity.VocabularioEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
public class VocabularioDao extends DaoEclipseLink<VocabularioEntity, Integer>{
//    @PersistenceContext(name = "MavenDlc-ejbPU")
//    private EntityManager entityManager;
    public VocabularioBean buscarPorTermino(final String termino) {
        VocabularioBean vocB = null;
        Query query = this.entityManager.createNamedQuery("VocabularioEntity.findByTermino").setParameter(":termino", termino);
        List<VocabularioEntity> lista = (List<VocabularioEntity>) query.getResultList();
        if (lista.size()>=0) {
            VocabularioEntity docE = lista.get(0);
            vocB=new Vocabulario(docE).getBean();
        }
        return vocB;
    }
      public VocabularioBean buscarPorId(final int id) {
        VocabularioBean vocB = null;
        Query query = this.entityManager.createNamedQuery("VocabularioEntity.findById").setParameter(":id", id);
        List<VocabularioEntity> lista = (List<VocabularioEntity>) query.getResultList();
        if (lista.size()>=0) {
            VocabularioEntity docE = lista.get(0);
            vocB=new Vocabulario(docE).getBean();
        }
        return vocB;
    }
}
