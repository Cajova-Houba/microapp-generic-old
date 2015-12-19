package org.microapp.generic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Use this class for entities which are bound to a member - entities whichc should have some sort of "member_id" field.
 * @author Zdenda
 *
 */
@MappedSuperclass
public abstract class BaseAccessObject extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="person_id")
	protected long personId;
	
	
	
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

}
