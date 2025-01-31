package gov.cdc.nedss.alert.vo;

import java.util.Collection;

import gov.cdc.nedss.alert.dt.AlertLogDT;
import gov.cdc.nedss.util.AbstractVO;

public class AlertLogVO  extends AbstractVO{

	/**
	 *  @author Pradeep Sharma
	 */
	private static final long serialVersionUID = 1L;
	private Collection<Object> alertLogDetailDTColl;
	private AlertLogDT alertLogDT;

	public Collection<Object> getAlertLogDetailDTColl() {
		return alertLogDetailDTColl;
	}

	public void setAlertLogDetailDTColl(Collection<Object> alertLogDetailDTColl) {
		this.alertLogDetailDTColl = alertLogDetailDTColl;
	}

	public AlertLogDT getAlertLogDT() {
		return alertLogDT;
	}

	public void setAlertLogDT(AlertLogDT alertLogDT) {
		this.alertLogDT = alertLogDT;
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
