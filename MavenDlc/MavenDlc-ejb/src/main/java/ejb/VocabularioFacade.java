/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.VocabularioBean;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class VocabularioFacade implements VocabularioFacadeRemote {

    @Override
    public int insertarUno(VocabularioBean vocabularioBean) {
        /* si no existe
            creo uno nuevo con el max tf del tf actual
           si existe
            update de cant doc y max tf
        */
        return 0;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
