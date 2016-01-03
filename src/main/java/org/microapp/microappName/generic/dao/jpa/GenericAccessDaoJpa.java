package org.microapp.microappName.generic.dao.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.util.Version;
import org.hibernate.search.SearchException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.microapp.microappName.generic.dao.GenericAccessDao;
import org.microapp.microappName.generic.dao.GenericDao;
import org.microapp.microappName.generic.model.BaseAccessObject;
import org.springframework.transaction.annotation.Transactional;

public class GenericAccessDaoJpa<T extends BaseAccessObject, PK extends Serializable> implements
		GenericAccessDao<T, PK> {

	/**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    public static final String PERSISTENCE_UNIT_NAME = "ApplicationEntityManagerGeneric";

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext(unitName=PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    private Class<T> persistentClass;
    private Analyzer defaultAnalyzer;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     * @param persistentClass the class type you'd like to persist
     */
    public GenericAccessDaoJpa(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     * @param persistentClass the class type you'd like to persist
     * @param entityManager the configured EntityManager for JPA implementation.
     */
    public GenericAccessDaoJpa(final Class<T> persistentClass, EntityManager entityManager) {
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_36);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return this.entityManager.createQuery(
                "select obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);

        if (entity == null) {
            String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = this.entityManager.find(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return this.entityManager.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object)); 
    } 

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        this.entityManager.remove(this.get(id));
    }

    public List<T> search(String searchTerm) throws SearchException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        org.apache.lucene.search.Query qry;
        try {
            qry = HibernateSearchJpaTools.generateQuery(searchTerm, this.persistentClass, entityManager, defaultAnalyzer);
        } catch (ParseException ex) {
            throw new SearchException(ex);
        }
        org.hibernate.search.jpa.FullTextQuery hibQuery = fullTextEntityManager.createFullTextQuery(qry, this.persistentClass);
        // filter search results by owner.id value:
        // hibQuery.enableFullTextFilter("owned").setParameter("ownerId", owner.getId().toString());
        return hibQuery.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    public void reindex() {
        HibernateSearchJpaTools.reindex(persistentClass, getEntityManager());
    }


    /**
     * {@inheritDoc}
     */
    public void reindexAll(boolean async) {
        HibernateSearchJpaTools.reindexAll(async, getEntityManager());
    }

	public List<T> getAllForPerson(PK personId) {
		// TODO Auto-generated method stub
		String query = "SELECT obj FROM "+this.persistentClass.getName()+ " obj WHERE "+BaseAccessObject.ACCESS_ID_COLUMN_NAME+"=?";
		Query q = this.entityManager.createQuery(query);
		q.setParameter(1, personId);
		
		return q.getResultList();
	}


}
