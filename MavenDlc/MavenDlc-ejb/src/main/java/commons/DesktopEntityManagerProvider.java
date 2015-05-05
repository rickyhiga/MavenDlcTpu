/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commons;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Felipe
 */
public class DesktopEntityManagerProvider
{
    private static DesktopEntityManagerProvider emProv;
    
    private EntityManager em;
    
    private DesktopEntityManagerProvider()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MavenDlc-ejbPU");
        em = emf.createEntityManager();
    }
    
    public static EntityManager getEntityManager()
    {
        if (emProv == null)
            emProv = new DesktopEntityManagerProvider();
        
        return emProv.em;
    }
    
}
