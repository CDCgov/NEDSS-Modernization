package gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class DSMAlgorithmDT extends AbstractVO implements RootDTInterface {
	
	/**
	 * DSMAlgorithmDT uses Decision Support algorithm functionality
	 */
	private static final long serialVersionUID = 4546705321489806575L;
	private Long dsmAlgorithmUid;
	private String algorithmNm;
	private String eventType;
	private String conditionList;
	private String resultedTestList;
	private String frequency;
	private String applyTo;
	private String sendingSystemList;
	private String reportingSystemList;
	private String eventAction;
	private String algorithmPayload;
	private String adminComment;
	private Timestamp statusTime;
	private String statusCd;
	private Long lastChgUserId;
	private Timestamp lastChgTime;


	public Long getDsmAlgorithmUid() {
		return dsmAlgorithmUid;
	}

	public void setDsmAlgorithmUid(Long dsmAlgorithmUid) {
		this.dsmAlgorithmUid = dsmAlgorithmUid;
	}

	public String getAlgorithmNm() {
		return algorithmNm;
	}

	public void setAlgorithmNm(String algorithmNm) {
		this.algorithmNm = algorithmNm;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getConditionList() {
		return conditionList;
	}

	public void setConditionList(String conditionList) {
		this.conditionList = conditionList;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	public String getSendingSystemList() {
		return sendingSystemList;
	}

	public void setSendingSystemList(String sendingSystemList) {
		this.sendingSystemList = sendingSystemList;
	}

	public String getReportingSystemList() {
		return reportingSystemList;
	}

	public void setReportingSystemList(String reportingSystemList) {
		this.reportingSystemList = reportingSystemList;
	}

	public String getEventAction() {
		return eventAction;
	}

	public void setEventAction(String eventAction) {
		this.eventAction = eventAction;
	}

	public String getAlgorithmPayload() {
		return algorithmPayload;
	}

	public void setAlgorithmPayload(String algorithmPayload) {
		this.algorithmPayload = algorithmPayload;
	}

	public String getAdminComment() {
		return adminComment;
	}

	public void setAdminComment(String adminComment) {
		this.adminComment = adminComment;
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	
	public Timestamp getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Timestamp aStatusTime) {
		this.statusTime = aStatusTime;
	}
	
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
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

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
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

	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	public void setAddUserId(Long aAddUserId) {
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

	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}


	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	public String getResultedTestList() {
		return resultedTestList;
	}

	public void setResultedTestList(String resultedTestList) {
		this.resultedTestList = resultedTestList;
	}



}
