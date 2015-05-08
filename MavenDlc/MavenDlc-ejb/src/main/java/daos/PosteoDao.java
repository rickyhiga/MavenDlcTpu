/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.DocumentoBean;
import beans.PosteoBean;
import beans.VocabularioBean;
import business.Posteo;
import commons.DaoEclipseLink;
import entity.DocumentoEntity;
import entity.PosteoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author user
 */
public class PosteoDao extends DaoEclipseLink<PosteoEntity, Integer> {
    
    @Inject
    VocabularioDao vocDao;
    @Inject
    DocumentoDao docDao;
    
    public List<PosteoBean> obtenerPosteosPorIdVocabulario(int id) {
        List<PosteoBean> posteos = new ArrayList<>();
        Query query = this.entityManager.createNamedQuery("PosteoEntity.findByVocabularioId").setParameter("id", id);
        List<PosteoEntity> posE = query.getResultList();
        for (PosteoEntity posEntity : posE) {
            PosteoBean posB = new Posteo(posEntity).getBean();
            DocumentoBean docB = docDao.buscarPorId(posEntity.getDocumentoId());
            VocabularioBean vocB = vocDao.buscarPorId(posEntity.getVocabularioId());
            posB.setDocBean(docB);
            posB.setVocBean(vocB);
            
            posteos.add(posB);
        }
        return posteos;
    }
    
    public List<PosteoBean> obtenerPosteosPorIdVocabularioOrderByTf(int id) {
        List<PosteoBean> posteos = new ArrayList<>();
        Query query = this.entityManager.createNamedQuery("PosteoEntity.findByVocabularioIdOrderByTf").setParameter("id", id);
        List<PosteoEntity> posE = query.setMaxResults(50).getResultList();
        for (PosteoEntity posEntity : posE) {
            PosteoBean posB = new Posteo(posEntity).getBean();
            DocumentoBean docB = docDao.buscarPorId(posEntity.getDocumentoId());
            VocabularioBean vocB = vocDao.buscarPorId(posEntity.getVocabularioId());
            posB.setDocBean(docB);
            posB.setVocBean(vocB);
            
            posteos.add(posB);
        }
        return posteos;
    }
}
