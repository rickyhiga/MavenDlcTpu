/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface DocumentoFacadeRemote {

   // DocumentoBean crearDocumento(final DocumentoBean documento);

    List<DocumentoBean> listarDocumentos(final String filtro);

    List<DocumentoBean> listarDocumentos();

    String listarDocsString();

    int insertarUno(final DocumentoBean docB);

    int insertarUnoDefault();
    
}
