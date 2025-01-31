package gov.cdc.nedss.alert.vo;

import java.util.Collection;


import gov.cdc.nedss.alert.dt.AlertDT;
import gov.cdc.nedss.util.AbstractVO;

public class AlertVO  extends AbstractVO {
	
	/**
	 *AlertVO  
	 * 
	 * @author Pradeep Sharma
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<Object> alertUserDTCollection;
	private AlertDT alertDT;
	private Collection<Object> emailAlertDTCollection;

	public Collection<Object> getEmailAlertDTCollection() {
		return emailAlertDTCollection;
	}
	public void setEmailAlertDTCollection(Collection<Object> emailAlertDTCollection) {
		this.emailAlertDTCollection  = emailAlertDTCollection;
	}
	public Collection<Object> getAlertUserDTCollection() {
		return alertUserDTCollection;
	}
	public void setAlertUserDTCollection(Collection<Object> alertUserDTCollection) {
		this.alertUserDTCollection  = alertUserDTCollection;
	}
	
	public AlertDT getAlertDT() {
		return alertDT;
	}
	public void setAlertDT(AlertDT alertDT) {
		this.alertDT = alertDT;
	}
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
	

}
