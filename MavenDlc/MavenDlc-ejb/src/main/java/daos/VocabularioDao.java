/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
public class VocabularioDao {
    @PersistenceContext(name = "MavenDlc-ejbPU")
    private EntityManager entityManager;
}
