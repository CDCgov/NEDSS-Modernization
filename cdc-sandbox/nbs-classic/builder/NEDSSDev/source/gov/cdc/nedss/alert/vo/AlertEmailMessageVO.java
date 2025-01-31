package gov.cdc.nedss.alert.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.cdc.nedss.alert.dt.AlertEmailMessageDT;
import gov.cdc.nedss.util.AbstractVO;
/**
 *AlertVO  
 * 
 * @author Pradeep Sharma
 * 
 */
public class AlertEmailMessageVO extends AbstractVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AlertEmailMessageDT alertEmailMessageDT;
	private Collection<Object> userEmailDTCollection=new ArrayList<Object> ();
	
	
	public AlertEmailMessageDT getAlertEmailMessageDT() {
		return alertEmailMessageDT;
	}

	public void setAlertEmailMessageDT(AlertEmailMessageDT alertEmailMessageDT) {
		this.alertEmailMessageDT = alertEmailMessageDT;
	}

	public Collection<Object> getUserEmailDTCollection() {
		return userEmailDTCollection;
	}

	public void setUserEmailDTCollection(Collection<Object> userEmailDTCollection) {
		this.userEmailDTCollection  = userEmailDTCollection;
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
