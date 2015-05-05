/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class PosteoEntityFacade extends AbstractFacade<PosteoEntity> {
    @PersistenceContext(unitName = "com.mycompany_MavenDlc-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PosteoEntityFacade() {
        super(PosteoEntity.class);
    }
    
}
