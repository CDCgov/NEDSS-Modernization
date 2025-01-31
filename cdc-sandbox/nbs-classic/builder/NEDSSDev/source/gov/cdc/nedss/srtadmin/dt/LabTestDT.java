package gov.cdc.nedss.srtadmin.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class LabTestDT extends AbstractVO implements RootDTInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String labTestCd;
	private String labTestCdTmp;
	private String laboratoryId;
	private String laboratoryDescTxt;
	private String labTestDescTxt;
	private String testTypeCd;
	private String testTypeDescTxt;
	private Long nbsUid;
	private java.util.Date effectiveFromTime;
	private java.util.Date effectiveToTime;
	private String defaultProgAreaCd;
	private String defaultConditionCd;
	private String conditionDescTxt;
	private String drugTestInd;
	private String organismResultTestInd;
	private Integer indentLevelNbr;
	private String paDerivationExcludeCd;
	private String viewLink;
	private String editLink;
	
	
	public String getLabTestCd() {
		return labTestCd;
	}
	public void setLabTestCd(String labTestCd) {
		this.labTestCd = labTestCd;
	}
	public String getLaboratoryId() {
		return laboratoryId;
	}
	public void setLaboratoryId(String laboratoryId) {
		this.laboratoryId = laboratoryId;
	}
	public String getLabTestDescTxt() {
		return labTestDescTxt;
	}
	public void setLabTestDescTxt(String labTestDescTxt) {
		this.labTestDescTxt = labTestDescTxt;
	}
	public String getTestTypeCd() {
		return testTypeCd;
	}
	public void setTestTypeCd(String testTypeCd) {
		this.testTypeCd = testTypeCd;
	}
	public Long getNbsUid() {
		return nbsUid;
	}
	public void setNbsUid(Long nbsUid) {
		this.nbsUid = nbsUid;
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
	public String getDefaultProgAreaCd() {
		return defaultProgAreaCd;
	}
	public void setDefaultProgAreaCd(String defaultProgAreaCd) {
		this.defaultProgAreaCd = defaultProgAreaCd;
	}
	public String getDefaultConditionCd() {
		return defaultConditionCd;
	}
	public void setDefaultConditionCd(String defaultConditionCd) {
		this.defaultConditionCd = defaultConditionCd;
	}
	public String getDrugTestInd() {
		return drugTestInd;
	}
	public void setDrugTestInd(String drugTestInd) {
		this.drugTestInd = drugTestInd;
	}
	public String getOrganismResultTestInd() {
		return organismResultTestInd;
	}
	public void setOrganismResultTestInd(String organismResultTestInd) {
		this.organismResultTestInd = organismResultTestInd;
	}

	public String getEditLink() {
		return editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
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
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}
	public void setAddUserId(Long addUserId) {
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
	public String getViewLink() {
		return viewLink;
	}
	public void setViewLink(String viewLink) {
		this.viewLink = viewLink;
	}
	public String getTestTypeDescTxt() {
		return testTypeDescTxt;
	}
	public void setTestTypeDescTxt(String testTypeDescTxt) {
		this.testTypeDescTxt = testTypeDescTxt;
	}
	public String getConditionDescTxt() {
		return conditionDescTxt;
	}
	public void setConditionDescTxt(String conditionDescTxt) {
		this.conditionDescTxt = conditionDescTxt;
	}
	public Integer getIndentLevelNbr() {
		return indentLevelNbr;
	}
	public void setIndentLevelNbr(Integer indentLevelNbr) {
		this.indentLevelNbr = indentLevelNbr;
	}
	public String getLaboratoryDescTxt() {
		return laboratoryDescTxt;
	}
	public void setLaboratoryDescTxt(String laboratoryDescTxt) {
		this.laboratoryDescTxt = laboratoryDescTxt;
	}
	public String getLabTestCdTmp() {
		return labTestCdTmp;
	}
	public void setLabTestCdTmp(String labTestCdTmp) {
		this.labTestCdTmp = labTestCdTmp;
	}
	public String getPaDerivationExcludeCd() {
		return paDerivationExcludeCd;
	}
	public void setPaDerivationExcludeCd(String paDerivationExcludeCd) {
		this.paDerivationExcludeCd = paDerivationExcludeCd;
	}


}
