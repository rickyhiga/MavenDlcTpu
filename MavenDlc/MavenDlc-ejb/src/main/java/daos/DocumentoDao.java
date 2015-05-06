/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.DocumentoBean;
import business.Documento;
import commons.DaoEclipseLink;
import entity.DocumentoEntity;
import java.util.List;
import javax.persistence.Query;

import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author Nico
 */
public class DocumentoDao extends DaoEclipseLink<DocumentoEntity, Integer> {
//    @PersistenceContext(name = "MavenDlc-ejbPU")
//    private EntityManager entityManager;

    public DocumentoBean buscarPorUrl(final String url) {
        DocumentoBean docB = null;
        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findByUrl").setParameter("url", url);
        List<DocumentoEntity> lista = (List<DocumentoEntity>) query.getResultList();
        if (lista.size()>0) {
            DocumentoEntity docE = lista.get(0);
            docB=new Documento(docE).getBean();
        }
        return docB;
    }
    public DocumentoBean buscarPorId(final int id) {
        DocumentoBean docB = null;
        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findById").setParameter("id", id);
        List<DocumentoEntity> lista = (List<DocumentoEntity>) query.getResultList();
        if (lista.size()>=0) {
            DocumentoEntity docE = lista.get(0);
            docB=new Documento(docE).getBean();
        }
        return docB;
    }
    
    public int cantidadDocumentos(){
        Query query=this.entityManager.createNamedQuery("DocumentoEntity.cantTotal");
        return (int) query.getSingleResult();
    }


}
