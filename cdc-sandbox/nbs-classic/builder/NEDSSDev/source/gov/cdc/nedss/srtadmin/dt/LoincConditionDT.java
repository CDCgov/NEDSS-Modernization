package gov.cdc.nedss.srtadmin.dt;

import java.sql.Timestamp;
import java.util.Date;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class LoincConditionDT extends AbstractVO implements RootDTInterface{
	private static final long serialVersionUID = 1L;
	private String loincCd;
	private String conditionCd;
	private String diseaseNm;
	private String reportedValue;
	private String reportedNumericValue;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;
		
	
	
	public String getLoincCd() {
		return loincCd;
	}

	public void setLoincCd(String loincCd) {
		this.loincCd = loincCd;
	}

	public String getConditionCd() {
		return conditionCd;
	}

	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}

	public String getDiseaseNm() {
		return diseaseNm;
	}

	public void setDiseaseNm(String diseaseNm) {
		this.diseaseNm = diseaseNm;
	}

	public String getReportedValue() {
		return reportedValue;
	}

	public void setReportedValue(String reportedValue) {
		this.reportedValue = reportedValue;
	}

	public String getReportedNumericValue() {
		return reportedNumericValue;
	}

	public void setReportedNumericValue(String reportedNumericValue) {
		this.reportedNumericValue = reportedNumericValue;
	}

	public Date  getEffectiveFromTime() {
		return effectiveFromTime;
	}

	public void setEffectiveFromTime(Date effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}

	public Date getEffectiveToTime() {
		return effectiveToTime;
	}

	public void setEffectiveToTime(Date effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
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

}
