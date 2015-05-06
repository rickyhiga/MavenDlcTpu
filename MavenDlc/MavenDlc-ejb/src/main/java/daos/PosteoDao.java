/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.PosteoBean;
import business.Posteo;
import commons.DaoEclipseLink;
import entity.DocumentoEntity;
import entity.PosteoEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author user
 */
public class PosteoDao extends DaoEclipseLink<PosteoEntity, Integer> {
    public List<PosteoBean> obtenerPosteosPorId(int id){
        List<PosteoBean> posteos= new ArrayList<>();
        Query query=this.entityManager.createNamedQuery("PosteoEntity.findById").setParameter("id", id);
        List<PosteoEntity> posE=query.getResultList();
        for (PosteoEntity posEntity : posE) {
            PosteoBean posB=new Posteo(posEntity).getBean();
            posteos.add(posB);
        }
        return posteos;
    }
}
