/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.VocabularioBean;
import java.util.HashMap;
import javax.ejb.Remote;

/**
 *
 * @author Aldi Vaca
 */
@Remote
public interface VocabularioVolatilRemote {

    public void crearVocabulario();

    public HashMap<String, VocabularioBean> getVocabulario();

    public void setVocabulario(HashMap<String, VocabularioBean> update);
}
