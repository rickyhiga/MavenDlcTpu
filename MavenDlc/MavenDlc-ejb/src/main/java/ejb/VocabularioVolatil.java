/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.VocabularioBean;
import daos.VocabularioDao;
import java.util.HashMap;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.inject.Inject;

/**
 *
 * @author Aldi Vaca
 */
@Singleton
public class VocabularioVolatil implements VocabularioVolatilRemote {

    @Inject
    private VocabularioDao vocDao;

    private static HashMap<String, VocabularioBean> vocabulario = null;

    @Override
    public void crearVocabulario() {
        if (vocabulario == null) {
            vocabulario = vocDao.listarTodosMap();
            System.out.println("CREO VOCABULARIO DESDE DB");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public HashMap<String, VocabularioBean> getVocabulario() {
        crearVocabulario();
        System.out.println("LEVANTO VOCABULARIO");
        return vocabulario;
    }

    @Override
    public void setVocabulario(HashMap<String, VocabularioBean> update) {
        vocabulario = update;
        System.out.println("ACTUALIZO VOCABULARIO");
    }
}
