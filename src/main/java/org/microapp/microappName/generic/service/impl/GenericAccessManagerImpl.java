package org.microapp.microappName.generic.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.microapp.microappName.generic.dao.GenericAccessDao;
import org.microapp.microappName.generic.model.BaseAccessObject;
import org.microapp.microappName.generic.service.GenericAccessManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basically the same class as GenericManagerImpl, but it also implements the getAllForPerson() method.
 * @author Zdenda
 *
 * @param <T>
 * @param <PK>
 */
public class GenericAccessManagerImpl<T extends BaseAccessObject, PK extends Serializable> implements GenericAccessManager<T, PK> {
	/**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected GenericAccessDao<T, PK> dao;


    public GenericAccessManagerImpl() {
    }

    public GenericAccessManagerImpl(GenericAccessDao<T, PK> genericDao) {
        this.dao = genericDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        return dao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional (
    		value = "transactionManagerGeneric",
    		propagation = Propagation.REQUIRES_NEW
    		)
    public T save(T object) {
        return dao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        dao.remove(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        dao.remove(id);
    }

    /**
     * {@inheritDoc}
     */
	public List<T> getAllForPerson(PK personId) {
		// TODO Auto-generated method stub
		return this.dao.getAllForPerson(personId);
	}
}