/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.PosteoBean;
import business.Posteo;
import entity.PosteoEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 *
 * @author user
 */
public class PosteoDao {
    @PersistenceContext(name = "MavenDlc-ejbPU")
    private EntityManager entityManager;

    public int insertarUno(PosteoBean posteoBean) {
//        this.entityManager.createNamedQuery("PosteoEntity")
        PosteoEntity posE=new Posteo(posteoBean).getEntidad();
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(posE);
            entityManager.flush();
            entityManager.getTransaction().commit();

        }
        catch (EclipseLinkException ex)
        {
            entityManager.getTransaction().rollback();
            System.out.println(ex.toString());
        }

        return posE.getId();
    }
}
