package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


public class CoinfectionSummaryVO implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	private Long publicHealthCaseUid;
	private String localId;
	private String coinfectionId;
	private String investigatorLastNm;
	private String intestigatorFirstNm;
	private String conditionCd;
	private String jurisdictionCd;
	private Long programJurisdictionOid;
	private String progAreaCd;
	private String investigationStatus;
	private String caseClassCd;
	private Timestamp createDate;
	private Timestamp updateDate;
	private Timestamp investigationStartDate;
	private Long patientRevisionUid;
	private String epiLinkId;
	private String fieldRecordNumber;
	private String patIntvStatusCd; //Patient Interview Status Cd
	private boolean associated;
	private String checkBoxId;
	private String disabled;
	
	private String processingDecisionCode;
	
	
	
	
	
	public String getProcessingDecisionCode() {
		return processingDecisionCode;
	}
	public void setProcessingDecisionCode(String processingDecisionCode) {
		this.processingDecisionCode = processingDecisionCode;
	}
	public Long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}
	public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getCoinfectionId() {
		return coinfectionId;
	}
	public void setCoinfectionId(String coinfectionId) {
		this.coinfectionId = coinfectionId;
	}
	public String getInvestigatorLastNm() {
		return investigatorLastNm;
	}
	public void setInvestigatorLastNm(String investigatorLastNm) {
		this.investigatorLastNm = investigatorLastNm;
	}
	public String getIntestigatorFirstNm() {
		return intestigatorFirstNm;
	}
	public void setIntestigatorFirstNm(String intestigatorFirstNm) {
		this.intestigatorFirstNm = intestigatorFirstNm;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public String getJurisdictionCd() {
		return jurisdictionCd;
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	public Long getProgramJurisdictionOid() {
		return programJurisdictionOid;
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
	}
	public String getInvestigationStatus() {
		return investigationStatus;
	}
	public void setInvestigationStatus(String investigationStatus) {
		this.investigationStatus = investigationStatus;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getCaseClassCd() {
		return caseClassCd;
	}
	public void setCaseClassCd(String caseClassCd) {
		this.caseClassCd = caseClassCd;
	}
	public Timestamp getInvestigationStartDate() {
		return investigationStartDate;
	}
	public void setInvestigationStartDate(Timestamp investigationStartDate) {
		this.investigationStartDate = investigationStartDate;
	}

	public Long getPatientRevisionUid() {
		return patientRevisionUid;
	}
	public void setPatientRevisionUid(Long patientRevisionUid) {
		this.patientRevisionUid = patientRevisionUid;
	}
	public String getInvestigator() {
		if (intestigatorFirstNm == null)
			intestigatorFirstNm = "";
		if (investigatorLastNm == null)
			investigatorLastNm = "";
		return intestigatorFirstNm + " " + investigatorLastNm;
	}

	public String getCondition() {
		return CachedDropDowns.getConditionDesc(conditionCd);
	}

	public String getCaseStatus() {
		if (caseClassCd == null)
			return "";
		return CachedDropDowns.getCodeDescTxtForCd(caseClassCd,
				NEDSSConstants.CASE_CLASS_CODE_SET_NM);
	}

	public String getStatus() {
		if (investigationStatus == null)
			return "";
		return CachedDropDowns.getCodeDescTxtForCd(investigationStatus,
				NEDSSConstants.PHC_IN_STS);
	}

	public String getJurisdiction() {
		return CachedDropDowns.getJurisdictionDesc(jurisdictionCd);
	}
	public String getEpiLinkId() {
		return epiLinkId;
	}
	public void setEpiLinkId(String epiLinkId) {
		this.epiLinkId = epiLinkId;
	}
	public String getFieldRecordNumber() {
		return fieldRecordNumber;
	}
	public void setFieldRecordNumber(String fieldRecordNumber) {
		this.fieldRecordNumber = fieldRecordNumber;
	}
	public String getPatIntvStatusCd() {
		return patIntvStatusCd;
	}
	public void setPatIntvStatusCd(String patIntvStatusCd) {
		this.patIntvStatusCd = patIntvStatusCd;
	}
	//This is set in the action class depending on the associated item type.
	//i.e. treatment, interview, contact, etc.
	public boolean isAssociated() {
		return associated;
	}
	public void setAssociated(boolean isAssociated) {
		this.associated = isAssociated;
	}	
	public String getCheckBoxId() {
		if(associated) {
			checkBoxId = "checked=\"true\"";	
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	/**
	 * Used to disable chekbox.
	 * @return
	 */
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
