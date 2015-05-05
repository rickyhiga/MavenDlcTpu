/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.PosteoBean;
import daos.DocumentoDao;
import daos.PosteoDao;
import daos.VocabularioDao;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class PosteoFacade implements PosteoFacadeRemote {
    @EJB
    DocumentoFacadeRemote docFac;
    @EJB
    VocabularioFacadeRemote vocFac;
    @Inject
    private PosteoDao posDao;
    
    @Override
    public int indexarUnPosteo(PosteoBean posteo) {
        
        int idDoc=docFac.insertarUno(posteo.getDocBean());
        posteo.getDocBean().setId(idDoc);
        //si tf del posteo es > maxtf de vocBean -> setear maxtf vocBean
        if(posteo.getCant_apariciones_tf()>posteo.getVocBean().getMax_tf()){
            posteo.getVocBean().setMax_tf(posteo.getCant_apariciones_tf());
        }
        int idVoc=vocFac.insertarUno(posteo.getVocBean());
        posteo.getVocBean().setId(idVoc);
        this.insertarUno(posteo);
        return posteo.getId();
    }
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void indexadorHashMapPosteo(HashMap<Integer, PosteoBean> hmPosteo) {
    }

    @Override
    public int insertarUno(PosteoBean posteoBean) {
       // int id=posDao.insertarUno(posteoBean);
        return 0;
    }

    
   
}
