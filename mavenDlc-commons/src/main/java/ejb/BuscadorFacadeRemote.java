/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Aldi Vaca
 */
@Remote
public interface BuscadorFacadeRemote {

    ArrayList<DocumentoBean> busqueda(String consulta);

}
