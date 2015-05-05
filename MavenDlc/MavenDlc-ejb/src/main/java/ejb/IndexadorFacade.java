/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import beans.VocabularioBean;
import javax.ejb.Stateless;

/**
 *
 * @author Nico
 */
@Stateless
public class IndexadorFacade implements IndexadorFacadeRemote {

    @Override
    public int indexar() {
        DocumentoBean docB=obtenerDoc("Aca va el documento");
        if(docB==null){
            docB=new DocumentoBean();
        }else{
            return 1;
        }
        VocabularioBean vocB=obtenerPalabra("aca va la palabra");
        if(vocB==null){
            vocB=new VocabularioBean();          
        }
        if(vocB.getId() !=0){
            
        }
        return 0;
        
    }
    private DocumentoBean obtenerDoc(String url){
        return null;
    }
    private VocabularioBean obtenerPalabra(String palabra){
        return null;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
