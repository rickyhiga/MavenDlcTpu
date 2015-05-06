/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.File;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface IndexadorFacadeRemote {

   public String saveCount(final List<File> archivos);
   
   public void leerArchivoDefault();
}
