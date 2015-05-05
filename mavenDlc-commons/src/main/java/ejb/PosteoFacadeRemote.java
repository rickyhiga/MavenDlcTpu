/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.PosteoBean;
import java.util.HashMap;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface PosteoFacadeRemote {

    int indexarUnPosteo(PosteoBean posteo);

    void indexadorHashMapPosteo(HashMap<Integer, PosteoBean> hmPosteo);

    int insertarUno(PosteoBean posteoBean);

    


    
}
