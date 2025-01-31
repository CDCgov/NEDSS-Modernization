package gov.cdc.nedss.alert.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class AlertLogDetailDT extends AbstractVO implements RootDTInterface {

	/**
	 * AlertLogDetailDT is the data transfer object for ALERT_LOG_DETAIL table
	 * @author Pradeep Sharma
	 * @version 
	 */
	
	private Long alertLogDetailUid;
	private Long alertLogUid;
	private String alertActivityDetailLog;
	
	private static final long serialVersionUID = 1L;

	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
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

	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub

	}

	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub

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

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub

	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub

	}

	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub

	}

	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub

	}

	public void setLocalId(String localId) {
		// TODO Auto-generated method stub

	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub

	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub

	}

	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub

	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub

	}

	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub

	}

	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub

	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub

	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Long getAlertLogDetailUid() {
		return alertLogDetailUid;
	}

	public void setAlertLogDetailUid(Long alertLogDetailUid) {
		this.alertLogDetailUid = alertLogDetailUid;
	}

	public Long getAlertLogUid() {
		return alertLogUid;
	}

	public void setAlertLogUid(Long alertLogUid) {
		this.alertLogUid = alertLogUid;
	}

	public String getAlertActivityDetailLog() {
		return alertActivityDetailLog;
	}

	public void setAlertActivityDetailLog(String alertActivityDetailLog) {
		this.alertActivityDetailLog = alertActivityDetailLog;
	}
	public String stringValue() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + ":");
			Field[] f = AlertLogDetailDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "  :::: ");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	
}
