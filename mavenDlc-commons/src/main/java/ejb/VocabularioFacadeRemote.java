/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.VocabularioBean;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface VocabularioFacadeRemote {

    int insertarUno(VocabularioBean vocabularioBean);
    
}
