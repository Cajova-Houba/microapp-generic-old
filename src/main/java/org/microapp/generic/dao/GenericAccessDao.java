package org.microapp.generic.dao;

import java.io.Serializable;
import java.util.List;

import org.microapp.generic.model.BaseAccessObject;


/**
 * Use this interface whe creating DAOs for entities which implement BaseAccessObject
 * @author Zdenda
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericAccessDao<T extends BaseAccessObject, PK extends Serializable> extends GenericDao {

	/*
	 * ========================
	 * MICROAPPLICATION METHODS
	 * ======================== 
	 */
	
	
	public List<T> getAllForPerson(long personId);
	
	
	/*
	 * ===============================
	 * END OF MICROAPPLICATION METHODS
	 * =============================== 
	 */
	
}
