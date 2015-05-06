package commons;

import exceptions.TechnicalException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.eclipse.persistence.exceptions.EclipseLinkException;

/**
 * Clase base para los Daos que utilicen JPA
 * 
 * @author Felipe
 *
 * @param <E> Tipo de la entidad asociada
 * @param <K> Tipo de la clave primaria de la entidad asociada
 */
public abstract class DaoEclipseLink<E extends DalEntity, K> implements Dao<E, K>
{

    @PersistenceContext(unitName = "MavenDlc-ejbPU")
    protected EntityManager entityManager; // = DesktopEntityManagerProvider.getEntityManager();

    private final Class<E> entityClass;

    public DaoEclipseLink()
    {
        entityClass = ReflectionUtil.getGenericTypeClass(getClass());
        
    }

    protected Class<E> getEntityClass()
    {
        return entityClass;
    }

    @Override
    public E create(E pData)
    {
        try
        {
//            entityManager.getTransaction().begin();
            entityManager.persist(pData);
            entityManager.flush();
//            entityManager.getTransaction().commit();

        }
        catch (EclipseLinkException ex)
        {
//            entityManager.getTransaction().rollback();
            throw new TechnicalException(ex);
        }

        return pData;
    }

    @Override
    public void update(E pData)
    {
        try
        {
         //   entityManager.getTransaction().begin();
            E managed = entityManager.merge(pData);
            entityManager.persist(managed);
            entityManager.flush();
           // entityManager.getTransaction().commit();
        }
        catch (EclipseLinkException ex)
        {
         //   entityManager.getTransaction().rollback();
            throw new TechnicalException(ex);
        }
    }

    @Override
    public void delete(K pKey)
    {
        try
        {
        //    entityManager.getTransaction().begin();
            entityManager.remove(retrieve(pKey));
            entityManager.flush();
         //   entityManager.getTransaction().commit();
        }
        catch (EclipseLinkException ex)
        {
           // entityManager.getTransaction().rollback();
            throw new TechnicalException(ex);
        }
    }

    @Override
    public E retrieve(K pKey)
    {
        return entityManager.find(getEntityClass(), pKey);
    }

    @Override
    public List<E> findAll()
    {
        try
        {
            String className = getEntityClass().getSimpleName();
            Query query = entityManager.createQuery("SELECT e FROM " + className + " e");

            return query.getResultList();
        }
        catch (EclipseLinkException ex)
        {
            throw new TechnicalException(ex);
        }

    }

    public List<E> findByFilter(String filter)
    {
        try
        {
            String className = getEntityClass().getSimpleName();
            Query query = entityManager.createNamedQuery(className + ".findByFilter")
                .setParameter(":filter", filter);

            return query.getResultList();
        }
        catch (EclipseLinkException ex)
        {
            throw new TechnicalException(ex);
        }

    }

}
