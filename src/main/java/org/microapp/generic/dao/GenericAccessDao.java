package org.microapp.generic.dao;

import java.io.Serializable;
import java.util.List;

import org.microapp.generic.model.BaseAccessObject;


/**
 * Use this interface whe creating DAOs for entities which implement BaseAccessObject.
 * 
 * This interface also has same methods as GenericDao interface. Just extending GenericDao interface
 * caused problems with overriding.
 * @author Zdenda
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericAccessDao<T extends BaseAccessObject, PK extends Serializable> {

	/*
	 * ========================
	 * MICROAPPLICATION METHODS
	 * ======================== 
	 */
	
	
	public List<T> getAllForPerson(PK personId);
	
	
	/*
	 * ===============================
	 * END OF MICROAPPLICATION METHODS
	 * =============================== 
	 */
	
	
	/* GENERIC DAO METHODS*/
	
	/**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll();
   
    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     *
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     *
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    
    
    /**
     * Generic method to save an object - handles both update and insert.
     *
     * @param object the object to save
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    /**
     * Generic method to delete an object
     * @param object the object to remove
     */
    void remove(T object);
	
}
