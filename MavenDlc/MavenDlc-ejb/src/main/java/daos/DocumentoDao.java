/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.DocumentoBean;
import business.Documento;
import commons.DaoEclipseLink;
import db.DBAccessMySql;
import entity.DocumentoEntity;
import exceptions.TechnicalException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author Nico
 */
public class DocumentoDao extends DBAccessMySql {

    @PersistenceContext(name = "MavenDlc-ejbPU")
    private EntityManager entityManager;
    private String[] columnas = {"nombre", "url"};
    private String tabla = "documento";

    public DocumentoBean buscarPorUrl(final String url) {
        DocumentoBean docB = null;
        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findByUrl").setParameter("url", url);
        List<DocumentoEntity> lista = (List<DocumentoEntity>) query.getResultList();
        if (lista.size() > 0) {
            DocumentoEntity docE = lista.get(0);
            docB = new Documento(docE).getBean();
        }
        return docB;
    }
    public DocumentoBean buscarPorUrlSinAbrirCerrarConexion(String url) {
        DocumentoBean docB = null;
        
        int i = -1;
        String nombreTabla = " documento d ";
        String[] columns = {"d.id", "d.nombre", "d.url"};
        String condicion = " d.url = '" + url +"'";

        ResultSet rs = super.seleccionSimpleLimitOneSinAbrirCerrarConexion(nombreTabla, columnas, condicion);

        try {
            if (rs.next()) {
                docB=new DocumentoBean(rs.getInt(columns[0]), rs.getString(columns[1]), rs.getString(columns[2]));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        
//        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findByUrl").setParameter("url", url);
//        List<DocumentoEntity> lista = (List<DocumentoEntity>) query.getResultList();
//        if (lista.size() > 0) {
//            DocumentoEntity docE = lista.get(0);
//            docB = new Documento(docE).getBean();
//        }
        return docB;
    }
    

    public DocumentoBean buscarPorId(final int id) {
         
        DocumentoBean docB = null;
        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findById").setParameter("id", id);
       
        List<DocumentoEntity> lista = (List<DocumentoEntity>) query.getResultList();
        if (lista.size() > 0) {
            DocumentoEntity docE = lista.get(0);
            docB = new Documento(docE).getBean();
        }
        return docB;
    }

    public int cantidadDocumentos() {
        int result = 0;
        Query query = this.entityManager.createNamedQuery("DocumentoEntity.findAll");
        List<DocumentoEntity> list = query.getResultList();
        for (DocumentoEntity doc : list) {
            result++;
        }
        return result;
    }

    public DocumentoEntity create(DocumentoEntity docE) {
    //    super.openConnection();

        String[] values = {docE.getNombre(), docE.getUrl()};

        int id = this.insertarSinAbrirCerrarConexion(tabla, columnas, values);
        docE.setId(id);
        return docE;
    }

    public List<DocumentoEntity> findAll() {
        try {
            Query query = entityManager.createQuery("DocumentoEntity.findAll");

            return query.getResultList();
        } catch (EclipseLinkException ex) {
            throw new TechnicalException(ex);
        }

    }

}
