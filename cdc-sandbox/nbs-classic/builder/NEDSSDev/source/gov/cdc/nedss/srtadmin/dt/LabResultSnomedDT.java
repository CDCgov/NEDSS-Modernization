package gov.cdc.nedss.srtadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class LabResultSnomedDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;
	private String labResultCd;
	private String laboratoryID;
	private String snomedCd;
	private String snomedDescTx;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;
	private String statusCd;
	private String selectLink;
	//private java.util.Date statusTime;	
	
	
	public String getSelectLink() {
		return selectLink;
	}
	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
	}
	public String getLabResultCd() {
		return labResultCd;
	}
	public void setLabResultCd(String labResultCd) {
		this.labResultCd = labResultCd;
	}
	public String getLaboratoryID() {
		return laboratoryID;
	}
	public void setLaboratoryID(String laboratoryID) {
		this.laboratoryID = laboratoryID;
	}
	public String getSnomedCd() {
		return snomedCd;
	}
	public void setSnomedCd(String snomedCd) {
		this.snomedCd = snomedCd;
	}
	public java.util.Date getEffectiveFromTime() {
		return effectiveFromTime;
	}
	public void setEffectiveFromTime(java.util.Date effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public java.util.Date getEffectiveToTime() {
		return effectiveToTime;
	}
	public void setEffectiveToTime(java.util.Date effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
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
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getSnomedDescTx() {
		return snomedDescTx;
	}
	public void setSnomedDescTx(String snomedDescTx) {
		this.snomedDescTx = snomedDescTx;
	}
	
	
	
	
}
