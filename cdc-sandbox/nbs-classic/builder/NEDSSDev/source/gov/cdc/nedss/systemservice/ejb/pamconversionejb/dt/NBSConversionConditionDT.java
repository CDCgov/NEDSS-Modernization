package gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class NBSConversionConditionDT  extends AbstractVO implements RootDTInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nbsConversionConditionUid;
	private String conditionCd;
	private Long conditionCdGroupId;
	private Long nbsConversionPageMgmtUid;
	private String statusCd;
	private Timestamp addTime;
	private Timestamp lastChangeTime;
	
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public Timestamp getAddTime() {
		return addTime;
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
		return statusCd;
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
		this.addTime = addTime;
		
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
		this.statusCd = statusCd;
	}
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}
	public Long getNbsConversionConditionUid() {
		return nbsConversionConditionUid;
	}
	public void setNbsConversionConditionUid(Long nbsConversionConditionUid) {
		this.nbsConversionConditionUid = nbsConversionConditionUid;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public Long getConditionCdGroupId() {
		return conditionCdGroupId;
	}
	public void setConditionCdGroupId(Long conditionCdGroupId) {
		this.conditionCdGroupId = conditionCdGroupId;
	}
	public Long getNbsConversionPageMgmtUid() {
		return nbsConversionPageMgmtUid;
	}
	public void setNbsConversionPageMgmtUid(Long nbsConversionPageMgmtUid) {
		this.nbsConversionPageMgmtUid = nbsConversionPageMgmtUid;
	}
	public Timestamp getLastChangeTime() {
		return lastChangeTime;
	}
	public void setLastChangeTime(Timestamp lastChangeTime) {
		this.lastChangeTime = lastChangeTime;
	}
	
}
