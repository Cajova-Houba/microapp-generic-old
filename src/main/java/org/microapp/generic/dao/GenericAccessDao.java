package org.microapp.generic.dao;

import java.io.Serializable;
import java.util.List;


/**
 * Use this dao for accessing microapplication from another module
 * @author Zdenda
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericAccessDao<T, PK extends Serializable> extends GenericDao {

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
