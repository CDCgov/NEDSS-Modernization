package gov.cdc.nedss.alert.vo;

import java.util.Collection;

import gov.cdc.nedss.alert.dt.UserEmailDT;

import gov.cdc.nedss.util.AbstractVO;

public class UserEmailVO  extends AbstractVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<Object> userEmailDTCollection;

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Object> getUserEmailDTCollection() {
		return userEmailDTCollection;
	}

	public void setUserEmailDTCollection(Collection<Object> userEmailDTCollection) {
		this.userEmailDTCollection  = userEmailDTCollection;
	} 
	
}