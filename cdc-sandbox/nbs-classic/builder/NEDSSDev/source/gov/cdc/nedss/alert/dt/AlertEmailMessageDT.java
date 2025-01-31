package gov.cdc.nedss.alert.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class AlertEmailMessageDT extends AbstractVO implements RootDTInterface{

	/**
	 * Title: AlertEmailMessage class. 
	 * Description: Provides data transfer Object to store and retrieve alert message information in Alert_Email_Message table
	 * Copyright: Copyright (c) 2008 Company: CSC
	 * @author Pradeep Sharma
	 * @version
	 */
	
	private static final long serialVersionUID = 1L;
	private Long AlertEmailMessageUid; 
	private String TypeCd;
	private String Type;
	private String SeverityCd;
	private String Severity;
	private String alertMsgTxt; //not saved in alert_email_message table, stored in alert.dt
	private String SimulatedAlert;
	private String JurisdictionCd; 
	private String JurisdictionDescription;
	private String AssociatedConditionCd;
	private String AssociatedConditionDesc;
	private Timestamp EventAddTime;
	private Timestamp AlertAddTime ;
	private Timestamp  emailSentTime;
	private String EventLocalId;  
	private String TransmissionStatus;
	private Long AlertUid ;
	private String progAreaCd;
	private String proAreaDescription;
	
	public String getProAreaDescription() {
		return proAreaDescription;
	}

	public void setProAreaDescription(String proAreaDescription) {
		this.proAreaDescription = proAreaDescription;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAddUserId() {
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

	public Long getAlertEmailMessageUid() {
		return AlertEmailMessageUid;
	}

	public void setAlertEmailMessageUid(Long alertEmailMessageUid) {
		AlertEmailMessageUid = alertEmailMessageUid;
	}

	public String getTypeCd() {
		return TypeCd;
	}

	public void setTypeCd(String typeCd) {
		TypeCd = typeCd;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getSeverityCd() {
		return SeverityCd;
	}

	public void setSeverityCd(String severityCd) {
		SeverityCd = severityCd;
	}

	public String getSeverity() {
		return Severity;
	}

	public void setSeverity(String severity) {
		Severity = severity;
	}

	public String getSimulatedAlert() {
		return SimulatedAlert;
	}

	public void setSimulatedAlert(String simulatedAlert) {
		SimulatedAlert = simulatedAlert;
	}

	public String getAssociatedConditionCd() {
		return AssociatedConditionCd;
	}

	public void setAssociatedConditionCd(String associatedConditionCd) {
		AssociatedConditionCd = associatedConditionCd;
	}

	public String getAssociatedConditionDesc() {
		return AssociatedConditionDesc;
	}

	public void setAssociatedConditionDesc(String associatedConditionDesc) {
		AssociatedConditionDesc = associatedConditionDesc;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = AlertEmailMessageDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Timestamp getEventAddTime() {
		return EventAddTime;
	}

	public void setEventAddTime(Timestamp eventAddTime) {
		EventAddTime = eventAddTime;
	}

	public Timestamp getAlertAddTime() {
		return AlertAddTime;
	}

	public void setAlertAddTime(Timestamp alertAddTime) {
		AlertAddTime = alertAddTime;
	}

	public String getEventLocalId() {
		return EventLocalId;
	}

	public void setEventLocalId(String eventLocalId) {
		EventLocalId = eventLocalId;
	}

	public String getTransmissionStatus() {
		return TransmissionStatus;
	}

	public void setTransmissionStatus(String transmissionStatus) {
		TransmissionStatus = transmissionStatus;
	}

	public Long getAlertUid() {
		return AlertUid;
	}

	public void setAlertUid(Long alertUid) {
		AlertUid = alertUid;
	}

	public String getJurisdictionCd() {
		return JurisdictionCd;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		JurisdictionCd = jurisdictionCd;
	}
	
	public String stringValue() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + ":");
			Field[] f = AlertEmailMessageDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "  :::: ");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public String getJurisdictionDescription() {
		return JurisdictionDescription;
	}

	public void setJurisdictionDescription(String jurisdictionDescription) {
		JurisdictionDescription = jurisdictionDescription;
	}

	public String getProgAreaCd() {
		return progAreaCd;
	}

	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}

	public Timestamp getEmailSentTime() {
		return emailSentTime;
	}

	public void setEmailSentTime(Timestamp emailSentTime) {
		this.emailSentTime = emailSentTime;
	}
	public String getAlertMsgTxt() {
		return alertMsgTxt;
	}

	public void setAlertMsgTxt(String alertMsgTxt) {
		this.alertMsgTxt = alertMsgTxt;
	}	
}
