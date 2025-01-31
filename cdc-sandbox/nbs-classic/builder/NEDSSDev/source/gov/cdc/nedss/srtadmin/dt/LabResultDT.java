package gov.cdc.nedss.srtadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class LabResultDT extends AbstractVO implements RootDTInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String labResultCd;
	private String labResultCdTmp;
	private String laboratoryId;
	private String labResultDescTxt;
	private String effectiveFromTime;
	private String effectiveToTime;
	private Long nbsUid;
	private String defaultProgAreaCd;
	private String organismNameInd;
	private String defaultConditionCd;
	private String conditionDescTxt;
	private String paDerivationExcludeCd;
	private String viewLink;
	private String editLink;
	private String selectLink;
	
	
	public String getLabResultCd() {
		return labResultCd;
	}
	public void setLabResultCd(String labResultCd) {
		this.labResultCd = labResultCd;
	}
	public String getLaboratoryId() {
		return laboratoryId;
	}
	public void setLaboratoryId(String laboratoryId) {
		this.laboratoryId = laboratoryId;
	}
	public String getLabResultDescTxt() {
		return labResultDescTxt;
	}
	public void setLabResultDescTxt(String labResultDescTxt) {
		this.labResultDescTxt = labResultDescTxt;
	}
	public String getEffectiveFromTime() {
		return effectiveFromTime;
	}
	public void setEffectiveFromTime(String effectiveFromTime) {
		this.effectiveFromTime = effectiveFromTime;
	}
	public String getEffectiveToTime() {
		return effectiveToTime;
	}
	public void setEffectiveToTime(String effectiveToTime) {
		this.effectiveToTime = effectiveToTime;
	}
	public Long getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
	}
	public String getDefaultProgAreaCd() {
		return defaultProgAreaCd;
	}
	public void setDefaultProgAreaCd(String defaultProgAreaCd) {
		this.defaultProgAreaCd = defaultProgAreaCd;
	}
	public String getOrganismNameInd() {
		return organismNameInd;
	}
	public void setOrganismNameInd(String organismNameInd) {
		this.organismNameInd = organismNameInd;
	}
	public String getDefaultConditionCd() {
		return defaultConditionCd;
	}
	public void setDefaultConditionCd(String defaultConditionCd) {
		this.defaultConditionCd = defaultConditionCd;
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
	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getLabResultCdTmp() {
		return labResultCdTmp;
	}
	public void setLabResultCdTmp(String labResultCdTmp) {
		this.labResultCdTmp = labResultCdTmp;
	}
	public String getConditionDescTxt() {
		return conditionDescTxt;
	}
	public void setConditionDescTxt(String conditionDescTxt) {
		this.conditionDescTxt = conditionDescTxt;
	}
	public String getSelectLink() {
		return selectLink;
	}
	public void setSelectLink(String selectLink) {
		this.selectLink = selectLink;
	}
	public String getPaDerivationExcludeCd() {
		return paDerivationExcludeCd;
	}
	public void setPaDerivationExcludeCd(String paDerivationExcludeCd) {
		this.paDerivationExcludeCd = paDerivationExcludeCd;
	}

}
