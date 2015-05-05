/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import beans.DocumentoBean;
import business.Documento;
import daos.DocumentoDao;
import entity.DocumentoEntity;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author user
 */
@Stateless
public class DocumentoFacade implements DocumentoFacadeRemote {

    @Inject
    private DocumentoDao docDao;



    @Override
    public List<DocumentoBean> listarDocumentos(String filtro) {
        List<DocumentoEntity> entidades = docDao.findAll();
        LinkedList<DocumentoBean> beans = new LinkedList<>();
        entitiesToBeans(entidades, beans);

        return beans;
    }

    @Override
    public List<DocumentoBean> listarDocumentos() {
        List<DocumentoEntity> entidades = docDao.findAll();
        LinkedList<DocumentoBean> beans = new LinkedList<>();
        entitiesToBeans(entidades, beans);
        return beans;
    }

   
   

    private void entitiesToBeans(List<DocumentoEntity> entidades, LinkedList<DocumentoBean> beans) {
        for (DocumentoEntity entidad : entidades) {
            beans.add(new Documento(entidad).getBean());
        }
    }

    public String listarDocsString() {
        StringBuilder st = new StringBuilder();
        List<DocumentoEntity> entidades = docDao.findAll();
        for (DocumentoEntity e : entidades) {
            st.append(e.toString()).append("\n");
           
        }
        return st.toString();
    }

    @Override
    public int insertarUno(final DocumentoBean docb) {
//        DocumentoEntity docE=new Documento(docb).getEntidad();
//        docDao.create(docE);
        
        return docDao.insertarUno(docb);
       // return docDao.insertarUno(documentoBean);
    }

  
    @Override
    public int insertarUnoDefault() {
        //Testeo clases profe
        DocumentoBean docBean=new DocumentoBean("Testing", "unaurl", (float) 25.555);
        DocumentoEntity docE=new Documento(docBean).getEntidad();
        docDao.create(docE);
        return docE.getId();
        //Termina testeo
        
       // return docDao.insertarUnoDefault();
        
    }
}
