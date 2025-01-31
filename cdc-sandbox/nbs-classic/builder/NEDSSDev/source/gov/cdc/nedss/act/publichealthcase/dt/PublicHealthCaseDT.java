package gov.cdc.nedss.act.publichealthcase.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Title:PublicHealthCaseDT Description: Copyright: Copyright (c) 2001
 * Release 5.2/2017 Added variable isReentrant for merge investigation
 * Company:CSC
 * 
 * @author: nedss project team
 * @version 1.0
 *  * @updatedByAuthor Pradeep Sharma
 * @company: SAIC
 * @version 4.5
 *  * @updatedByAuthor Pradeep Sharma
 * @company: CSRA
 * @version 5.2
 */

public class PublicHealthCaseDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	private boolean caseStatusDirty = false;

	private boolean isPamCase;
	private boolean isPageCase;
	private boolean isStdHivProgramAreaCode;
	
	private String caseTypeCd;
	private Long publicHealthCaseUid;
	private String activityDurationAmt;
	private String activityDurationUnitCd;
	private Timestamp activityFromTime;
	private Timestamp activityToTime;
	private String addReasonCd;
	private Timestamp addTime;
	private Long addUserId;
	private String caseClassCd;
	private String cd;
	private String cdDescTxt;
	private String cdSystemCd;
	private String cdSystemDescTxt;
	private String confidentialityCd;
	private String confidentialityDescTxt;
	private String detectionMethodCd;
	private String detectionMethodDescTxt;
	private Timestamp diagnosisTime;
	private String diseaseImportedCd;
	private String diseaseImportedDescTxt;
	private String effectiveDurationAmt;
	private String effectiveDurationUnitCd;
	private Timestamp effectiveFromTime;
	private Timestamp effectiveToTime;
	private Integer groupCaseCnt;
	private String investigationStatusCd;
	private Timestamp investigatorAssignedTime;
	private String jurisdictionCd;
	private String lastChgReasonCd;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String localId;
	private String mmwrWeek;
	private String mmwrYear;
	private String outbreakInd;
	private Timestamp outbreakFromTime;
	private Timestamp outbreakToTime;
	private String outbreakName;
	private String outcomeCd;
	private String patAgeAtOnset;
	private String patAgeAtOnsetUnitCd;
	private Long patientGroupId;
	private String progAreaCd;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	private Integer repeatNbr;
	private String rptCntyCd;
	private Timestamp rptFormCmpltTime;
	private String rptSourceCd;
	private String rptSourceCdDescTxt;
	private Timestamp rptToCountyTime;
	private Timestamp rptToStateTime;
	private String statusCd;
	private Timestamp statusTime;
	private String transmissionModeCd;
	private String transmissionModeDescTxt;
	private String txt;
	private String userAffiliationTxt;
	private Long programJurisdictionOid;
	private String sharedInd;
	private Integer versionCtrlNbr;
	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;
	private String addUserName;
	private String lastChgUserName;
	private Long currentInvestigatorUid;
	private Long currentPatientUid;
	//private boolean isReentrant;

	
	// Added for Extending PHC table for common fields - ODS changes activity
	// changes
	private String hospitalizedIndCd;
	private Timestamp hospitalizedAdmTime;
	private Timestamp hospitalizedDischargeTime;
	private BigDecimal hospitalizedDurationAmt;
	private String pregnantIndCd;
  // private String dieFromIllnessIndCD;
	private String dayCareIndCd;
	private String foodHandlerIndCd;
	private String importedCountryCd;
	private String importedStateCd;
	private String importedCityDescTxt;
	private String importedCountyCd;
	private Timestamp deceasedTime;
	private Timestamp rptSentTime;
	private String countIntervalCd;
	private boolean isSummaryCase;
	private String priorityCd;
	private Timestamp infectiousFromDate;
	private Timestamp infectiousToDate;
	private String contactInvStatus;
	private String contactInvTxt;	
	private String referralBasisCd;
	private String currProcessStateCd;	
	private String invPriorityCd;
	private String coinfectionId;
	private Timestamp associatedSpecimenCollDate;
    private String confirmationMethodCd;

    private Timestamp confirmationMethodTime;
	
    private boolean isStdOpenedFromClosed;
    
	public String getConfirmationMethodCd() {
		return confirmationMethodCd;
	}

	public void setConfirmationMethodCd(String confirmationMethodCd) {
		this.confirmationMethodCd = confirmationMethodCd;
	}

	public Timestamp getConfirmationMethodTime() {
		return confirmationMethodTime;
	}

	public void setConfirmationMethodTime(Timestamp confirmationMethodTime) {
		this.confirmationMethodTime = confirmationMethodTime;
	}

	public Timestamp getAssociatedSpecimenCollDate() {
		return associatedSpecimenCollDate;
	}

	public void setAssociatedSpecimenCollDate(Timestamp associatedSpecimenCollDate) {
		this.associatedSpecimenCollDate = associatedSpecimenCollDate;
	}

	public Long getCurrentInvestigatorUid() {
		return currentInvestigatorUid;
	}

	public void setCurrentInvestigatorUid(Long currentInvestigatorUid) {
		this.currentInvestigatorUid = currentInvestigatorUid;
	}
	public boolean isPageCase() {
		return isPageCase;
	}

	public void setPageCase(boolean isPageCase) {
		this.isPageCase = isPageCase;
	}
	public String getPriorityCd() {
		return priorityCd;
	}

	public void setPriorityCd(String priorityCd) {
		this.priorityCd = priorityCd;
	}

	public Timestamp getInfectiousFromDate() {
		return infectiousFromDate;
	}
	
	public String getInfectiousFromDate_s() {

		if (getInfectiousFromDate() == null)
			return null;
		return StringUtils.formatDate(getInfectiousFromDate());
	}

	public void setInfectiousFromDate(Timestamp infectiousFromDate) {
		this.infectiousFromDate = infectiousFromDate;
	}
	public void setInfectiousFromDate_s(String strinfectiousFromDate) {

		if (strinfectiousFromDate == null)

			return;

		this.setInfectiousFromDate(StringUtils.stringToStrutsTimestamp(strinfectiousFromDate));
	}
	
	public Timestamp getInfectiousToDate() {
		return infectiousToDate;
	}
	
	public String getInfectiousToDate_s() {

		if (getInfectiousToDate() == null)
			return null;
		return StringUtils.formatDate(getInfectiousToDate());
	}

	public void setInfectiousToDate(Timestamp infectiousToDate) {
		this.infectiousToDate = infectiousToDate;
	}
	
	public void setInfectiousToDate_s(String strinfectiousToDate) {

		if (strinfectiousToDate == null)

			return;

		this.setInfectiousToDate(StringUtils.stringToStrutsTimestamp(strinfectiousToDate));
	}

	public String getContactInvStatus() {
		return contactInvStatus;
	}

	public void setContactInvStatus(String contactInvStatus) {
		this.contactInvStatus = contactInvStatus;
	}

	public String getContactInvTxt() {
		return contactInvTxt;
	}

	public void setContactInvTxt(String contactInvTxt) {
		this.contactInvTxt = contactInvTxt;
	}

	public boolean isSummaryCase() {
		return isSummaryCase;
	}

	public void setSummaryCase(boolean isSummaryCase) {
		this.isSummaryCase = isSummaryCase;
	}

	public Timestamp getRptSentTime() {
		return rptSentTime;
	}

	public void setRptSentTime(Timestamp rptSentTime) {
		this.rptSentTime = rptSentTime;
	}

	public void setCaseStatusDirty(boolean dirty) {
		this.caseStatusDirty = dirty;
	}

	public boolean isCaseStatusDirty() {
		return this.caseStatusDirty;
	}

	/**
	 * 
	 * @return String caseTypeCd
	 */
	public String getCaseTypeCd() {
		return caseTypeCd;
	}

	/**
	 * 
	 * @param caseTypeCd
	 */
	public void setCaseTypeCd(String caseTypeCd) {
		this.caseTypeCd = caseTypeCd;
	}

	/**
	 * 
	 * @return Long publicHealthCaseUid
	 */
	public Long getPublicHealthCaseUid() {

		return publicHealthCaseUid;
	}

	/**
	 * 
	 * @param aPublicHealthCaseUid
	 */
	public void setPublicHealthCaseUid(Long aPublicHealthCaseUid) {
		publicHealthCaseUid = aPublicHealthCaseUid;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String activityDurationAmt
	 */
	public String getActivityDurationAmt() {
		return activityDurationAmt;
	}

	/**
	 * 
	 * @param aActivityDurationAmt
	 */
	public void setActivityDurationAmt(String aActivityDurationAmt) {
		activityDurationAmt = aActivityDurationAmt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String activityDurationUnitCd
	 */
	public String getActivityDurationUnitCd() {
		return activityDurationUnitCd;
	}

	/**
	 * 
	 * @param aActivityDurationUnitCd
	 */
	public void setActivityDurationUnitCd(String aActivityDurationUnitCd) {
		activityDurationUnitCd = aActivityDurationUnitCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp activityFromTime
	 */
	public Timestamp getActivityFromTime() {

		return activityFromTime;
	}

	/**
	 * 
	 * @param aActivityFromTime
	 */
	public void setActivityFromTime(Timestamp aActivityFromTime) {
		activityFromTime = aActivityFromTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setActivityFromTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	public String getActivityFromTime_s() {

		if (getActivityFromTime() == null)

			return null;
		return StringUtils.formatDate(getActivityFromTime());
	}

	/**
	 * 
	 * @return Timestamp activityToTime
	 */
	public Timestamp getActivityToTime() {

		return activityToTime;
	}

	/**
	 * 
	 * @param aActivityToTime
	 */
	public void setActivityToTime(Timestamp aActivityToTime) {
		activityToTime = aActivityToTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setActivityToTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return String addReasonCd
	 */
	public String getAddReasonCd() {

		return addReasonCd;
	}

	/**
	 * 
	 * @param aAddReasonCd
	 */
	public void setAddReasonCd(String aAddReasonCd) {
		addReasonCd = aAddReasonCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp addTime
	 */
	public Timestamp getAddTime() {

		return addTime;
	}

	/**
	 * 
	 * @param aAddTime
	 */
	public void setAddTime(Timestamp aAddTime) {
		addTime = aAddTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setAddTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Long addUserId
	 */
	public Long getAddUserId() {

		return addUserId;
	}

	/**
	 * 
	 * @param aAddUserId
	 */
	public void setAddUserId(Long aAddUserId) {
		addUserId = aAddUserId;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String caseClassCd
	 */
	public String getCaseClassCd() {
		return caseClassCd;
	}

	/**
	 * 
	 * @param aCaseClassCd
	 */
	public void setCaseClassCd(String aCaseClassCd) {
		caseClassCd = aCaseClassCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * 
	 * @param aCd
	 */
	public void setCd(String aCd) {
		cd = aCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String cdDescTxt
	 */
	public String getCdDescTxt() {
		return cdDescTxt;
	}

	/**
	 * 
	 * @param aCdDescTxt
	 */
	public void setCdDescTxt(String aCdDescTxt) {
		cdDescTxt = aCdDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String cdSystemCd
	 */
	public String getCdSystemCd() {

		return cdSystemCd;
	}

	/**
	 * 
	 * @param aCdSystemCd
	 */
	public void setCdSystemCd(String aCdSystemCd) {
		cdSystemCd = aCdSystemCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String cdSystemDescTxt
	 */
	public String getCdSystemDescTxt() {

		return cdSystemDescTxt;
	}

	/**
	 * 
	 * @param aCdSystemDescTxt
	 */
	public void setCdSystemDescTxt(String aCdSystemDescTxt) {
		cdSystemDescTxt = aCdSystemDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String confidentialityCd
	 */
	public String getConfidentialityCd() {

		return confidentialityCd;
	}

	/**
	 * 
	 * @param aConfidentialityCd
	 */
	public void setConfidentialityCd(String aConfidentialityCd) {
		confidentialityCd = aConfidentialityCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String confidentialityDescTxt
	 */
	public String getConfidentialityDescTxt() {

		return confidentialityDescTxt;
	}

	/**
	 * 
	 * @param aConfidentialityDescTxt
	 */
	public void setConfidentialityDescTxt(String aConfidentialityDescTxt) {
		confidentialityDescTxt = aConfidentialityDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String detectionMethodCd
	 */
	public String getDetectionMethodCd() {

		return detectionMethodCd;
	}

	/**
	 * 
	 * @param aDetectionMethodCd
	 */
	public void setDetectionMethodCd(String aDetectionMethodCd) {
		detectionMethodCd = aDetectionMethodCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String detectionMethodDescTxt
	 */
	public String getDetectionMethodDescTxt() {

		return detectionMethodDescTxt;
	}

	/**
	 * 
	 * @param aDetectionMethodDescTxt
	 */
	public void setDetectionMethodDescTxt(String aDetectionMethodDescTxt) {
		detectionMethodDescTxt = aDetectionMethodDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp diagnosisTime
	 */
	public Timestamp getDiagnosisTime() {

		return diagnosisTime;
	}

	/**
	 * 
	 * @return Timestamp diagnosisTime
	 */
	public String getDiagnosisTime_s() {

		if (getDiagnosisTime() == null)
			return null;
		return StringUtils.formatDate(getDiagnosisTime());
	}

	/**
	 * 
	 * @param aDiagnosisTime
	 */
	public void setDiagnosisTime(Timestamp aDiagnosisTime) {
		diagnosisTime = aDiagnosisTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setDiagnosisTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setDiagnosisTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return String diseaseImportedCd
	 */
	public String getDiseaseImportedCd() {

		return diseaseImportedCd;
	}

	/**
	 * 
	 * @param aDiseaseImportedCd
	 */
	public void setDiseaseImportedCd(String aDiseaseImportedCd) {
		diseaseImportedCd = aDiseaseImportedCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String diseaseImportedDescTxt
	 */
	public String getDiseaseImportedDescTxt() {

		return diseaseImportedDescTxt;
	}

	/**
	 * 
	 * @param aDiseaseImportedDescTxt
	 */
	public void setDiseaseImportedDescTxt(String aDiseaseImportedDescTxt) {
		diseaseImportedDescTxt = aDiseaseImportedDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String effectiveDurationAmt
	 */
	public String getEffectiveDurationAmt() {

		return effectiveDurationAmt;
	}

	/**
	 * 
	 * @param aEffectiveDurationAmt
	 */
	public void setEffectiveDurationAmt(String aEffectiveDurationAmt) {
		effectiveDurationAmt = aEffectiveDurationAmt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String effectiveDurationUnitCd
	 */
	public String getEffectiveDurationUnitCd() {

		return effectiveDurationUnitCd;
	}

	/**
	 * 
	 * @param aEffectiveDurationUnitCd
	 */
	public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd) {
		effectiveDurationUnitCd = aEffectiveDurationUnitCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp effectiveFromTime
	 */
	public Timestamp getEffectiveFromTime() {

		return effectiveFromTime;
	}

	/**
	 * 
	 * @return Timestamp effectiveFromTime
	 */
	public String getEffectiveFromTime_s() {

		if (getEffectiveFromTime() == null)
			return null;
		return StringUtils.formatDate(getEffectiveFromTime());
	}

	/**
	 * 
	 * @param aEffectiveFromTime
	 */
	public void setEffectiveFromTime(Timestamp aEffectiveFromTime) {
		effectiveFromTime = aEffectiveFromTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setEffectiveFromTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Timestamp effectiveToTime
	 */
	public Timestamp getEffectiveToTime() {

		return effectiveToTime;
	}

	/**
	 * 
	 * @param aEffectiveToTime
	 */
	public void setEffectiveToTime(Timestamp aEffectiveToTime) {
		effectiveToTime = aEffectiveToTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setEffectiveToTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Integer groupCaseCnt
	 */
	public Integer getGroupCaseCnt() {

		return groupCaseCnt;
	}

	/**
	 * 
	 * @param aGroupCaseCnt
	 */
	public void setGroupCaseCnt(Integer aGroupCaseCnt) {
		groupCaseCnt = aGroupCaseCnt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String investigationStatusCd
	 */
	public String getInvestigationStatusCd() {

		return investigationStatusCd;
	}

	/**
	 * 
	 * @param aInvestigationStatusCd
	 */
	public void setInvestigationStatusCd(String aInvestigationStatusCd) {
		investigationStatusCd = aInvestigationStatusCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String jurisdictionCd
	 */
	public String getJurisdictionCd() {

		return jurisdictionCd;
	}

	/**
	 * 
	 * @param aJurisdictionCd
	 */
	public void setJurisdictionCd(String aJurisdictionCd) {
		jurisdictionCd = aJurisdictionCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Sting lastChgReasonCd
	 */
	public String getLastChgReasonCd() {

		return lastChgReasonCd;
	}

	/**
	 * 
	 * @param aLastChgReasonCd
	 */
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		lastChgReasonCd = aLastChgReasonCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp lastChgTime
	 */
	public Timestamp getLastChgTime() {

		return lastChgTime;
	}

	/**
	 * 
	 * @param aLastChgTime
	 */
	public void setLastChgTime(Timestamp aLastChgTime) {
		lastChgTime = aLastChgTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setLastChgTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Long lastChgUserId
	 */
	public Long getLastChgUserId() {

		return lastChgUserId;
	}

	/**
	 * 
	 * @param aLastChgUserId
	 */
	public void setLastChgUserId(Long aLastChgUserId) {
		lastChgUserId = aLastChgUserId;
		setItDirty(true);
	}

	/**
	 * 
	 * @return
	 */
	public String getLocalId() {

		return localId;
	}

	/**
	 * 
	 * @param aLocalId
	 */
	public void setLocalId(String aLocalId) {
		localId = aLocalId;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String mmwrWeek
	 */
	public String getMmwrWeek() {

		return mmwrWeek;
	}

	/**
	 * 
	 * @param aMmwrWeek
	 */
	public void setMmwrWeek(String aMmwrWeek) {
		mmwrWeek = aMmwrWeek;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String mmwrYear
	 */
	public String getMmwrYear() {

		return mmwrYear;
	}

	/**
	 * 
	 * @param aMmwrYear
	 */
	public void setMmwrYear(String aMmwrYear) {
		mmwrYear = aMmwrYear;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String outbreakInd
	 */
	public String getOutbreakInd() {

		return outbreakInd;
	}

	/**
	 * 
	 * @param aOutbreakInd
	 */
	public void setOutbreakInd(String aOutbreakInd) {
		outbreakInd = aOutbreakInd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp outbreakFromTime
	 */
	public Timestamp getOutbreakFromTime() {

		return outbreakFromTime;
	}

	/**
	 * 
	 * @param aOutbreakFromTime
	 */
	public void setOutbreakFromTime(Timestamp aOutbreakFromTime) {
		outbreakFromTime = aOutbreakFromTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setOutbreakFromTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setOutbreakFromTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Timestamp outbreakToTime
	 */
	public Timestamp getOutbreakToTime() {

		return outbreakToTime;
	}

	/**
	 * 
	 * @param aOutbreakToTime
	 */
	public void setOutbreakToTime(Timestamp aOutbreakToTime) {
		outbreakToTime = aOutbreakToTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setOutbreakToTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setOutbreakToTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return String outbreakName
	 */
	public String getOutbreakName() {

		return outbreakName;
	}

	/**
	 * 
	 * @param aOutbreakName
	 */
	public void setOutbreakName(String aOutbreakName) {
		outbreakName = aOutbreakName;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String outcomeCd
	 */
	public String getOutcomeCd() {

		return outcomeCd;
	}

	/**
	 * 
	 * @param aOutcomeCd
	 */
	public void setOutcomeCd(String aOutcomeCd) {
		outcomeCd = aOutcomeCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String patAgeAtOnset
	 */
	public String getPatAgeAtOnset() {

		return patAgeAtOnset;
	}

	/**
	 * 
	 * @param aPatAgeAtOnset
	 */
	public void setPatAgeAtOnset(String aPatAgeAtOnset) {
		patAgeAtOnset = aPatAgeAtOnset;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String patAgeAtOnsetUnitCd
	 */
	public String getPatAgeAtOnsetUnitCd() {

		return patAgeAtOnsetUnitCd;
	}

	/**
	 * 
	 * @param aPatAgeAtOnsetUnitCd
	 */
	public void setPatAgeAtOnsetUnitCd(String aPatAgeAtOnsetUnitCd) {
		patAgeAtOnsetUnitCd = aPatAgeAtOnsetUnitCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Long patientGroupId
	 */
	public Long getPatientGroupId() {

		return patientGroupId;
	}

	/**
	 * 
	 * @param aPatientGroupId
	 */
	public void setPatientGroupId(Long aPatientGroupId) {
		patientGroupId = aPatientGroupId;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String progAreaCd
	 */
	public String getProgAreaCd() {

		return progAreaCd;
	}

	/**
	 * 
	 * @param aProgAreaCd
	 */
	public void setProgAreaCd(String aProgAreaCd) {
		progAreaCd = aProgAreaCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String recordStatusCd
	 */
	public String getRecordStatusCd() {

		return recordStatusCd;
	}

	/**
	 * 
	 * @param aRecordStatusCd
	 */
	public void setRecordStatusCd(String aRecordStatusCd) {
		recordStatusCd = aRecordStatusCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {

		return recordStatusTime;
	}

	/**
	 * 
	 * @param aRecordStatusTime
	 */
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		recordStatusTime = aRecordStatusTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setRecordStatusTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Integer repeatNbr
	 */
	public Integer getRepeatNbr() {

		return repeatNbr;
	}

	/**
	 * 
	 * @param aRepeatNbr
	 */
	public void setRepeatNbr(Integer aRepeatNbr) {
		repeatNbr = aRepeatNbr;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String
	 */
	public String getRptCntyCd() {

		return rptCntyCd;
	}

	/**
	 * 
	 * @param aRptCntyCd
	 */
	public void setRptCntyCd(String aRptCntyCd) {
		rptCntyCd = aRptCntyCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp rptFormCmpltTime
	 */
	public Timestamp getRptFormCmpltTime() {

		return rptFormCmpltTime;
	}

	/**
	 * 
	 * @param aRptFormCmpltTime
	 */
	public void setRptFormCmpltTime(Timestamp aRptFormCmpltTime) {
		rptFormCmpltTime = aRptFormCmpltTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setRptFormCmpltTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setRptFormCmpltTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @param strTime
	 */
	public String getRptFormCmpltTime_s() {
		if (getRptFormCmpltTime() == null)
			return null;
		return StringUtils.formatDate(getRptFormCmpltTime());
	}

	/**
	 * 
	 * @return String rptSourceCd
	 */
	public String getRptSourceCd() {

		return rptSourceCd;
	}

	/**
	 * 
	 * @param aRptSourceCd
	 */
	public void setRptSourceCd(String aRptSourceCd) {
		rptSourceCd = aRptSourceCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String rptSourceCdDescTxt
	 */
	public String getRptSourceCdDescTxt() {

		return rptSourceCdDescTxt;
	}

	/**
	 * 
	 * @param aRptSourceCdDescTxt
	 */
	public void setRptSourceCdDescTxt(String aRptSourceCdDescTxt) {
		rptSourceCdDescTxt = aRptSourceCdDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp rptToCountyTime
	 */
	public Timestamp getRptToCountyTime() {

		return rptToCountyTime;
	}

	/**
	 * 
	 * @return Timestamp rptToCountyTime
	 */
	public String getRptToCountyTime_s() {

		if (getRptToCountyTime() == null)
			return null;
		return StringUtils.formatDate(getRptToCountyTime());
	}

	/**
	 * 
	 * @param aRptToCountyTime
	 */
	public void setRptToCountyTime(Timestamp aRptToCountyTime) {
		rptToCountyTime = aRptToCountyTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setRptToCountyTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setRptToCountyTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return Timestamp rptToStateTime
	 */
	public Timestamp getRptToStateTime() {

		return rptToStateTime;
	}

	/**
	 * 
	 * @return Timestamp rptToStateTime
	 */
	public String getRptToStateTime_s() {

		if (getRptToStateTime() == null)
			return null;
		return StringUtils.formatDate(getRptToStateTime());
	}

	/**
	 * 
	 * @param aRptToStateTime
	 */
	public void setRptToStateTime(Timestamp aRptToStateTime) {
		rptToStateTime = aRptToStateTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setRptToStateTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setRptToStateTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return String statusCd
	 */
	public String getStatusCd() {

		return statusCd;
	}

	/**
	 * 
	 * @param aStatusCd
	 */
	public void setStatusCd(String aStatusCd) {
		statusCd = aStatusCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Timestamp statusTime
	 */
	public Timestamp getStatusTime() {

		return statusTime;
	}

	/**
	 * 
	 * @param aStatusTime
	 */
	public void setStatusTime(Timestamp aStatusTime) {
		statusTime = aStatusTime;
		setItDirty(true);
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setStatusTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	/**
	 * 
	 * @return String transmissionModeCd
	 */
	public String getTransmissionModeCd() {

		return transmissionModeCd;
	}

	/**
	 * 
	 * @param aTransmissionModeCd
	 */
	public void setTransmissionModeCd(String aTransmissionModeCd) {
		transmissionModeCd = aTransmissionModeCd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String transmissionModeDescTxt
	 */
	public String getTransmissionModeDescTxt() {

		return transmissionModeDescTxt;
	}

	/**
	 * 
	 * @param aTransmissionModeDescTxt
	 */
	public void setTransmissionModeDescTxt(String aTransmissionModeDescTxt) {
		transmissionModeDescTxt = aTransmissionModeDescTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String txt
	 */
	public String getTxt() {

		return txt;
	}

	/**
	 * 
	 * @param aTxt
	 */
	public void setTxt(String aTxt) {
		txt = aTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String userAffiliationTxt
	 */
	public String getUserAffiliationTxt() {

		return userAffiliationTxt;
	}

	/**
	 * 
	 * @param aUserAffiliationTxt
	 */
	public void setUserAffiliationTxt(String aUserAffiliationTxt) {
		userAffiliationTxt = aUserAffiliationTxt;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Long programJurisdictionOid
	 */
	public Long getProgramJurisdictionOid() {

		return programJurisdictionOid;
	}

	/**
	 * 
	 * @param aProgramJurisdictionOid
	 */
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		programJurisdictionOid = aProgramJurisdictionOid;
		setItDirty(true);
	}

	/**
	 * 
	 * @return String sharedInd
	 */
	public String getSharedInd() {

		return sharedInd;
	}

	/**
	 * 
	 * @param aSharedInd
	 */
	public void setSharedInd(String aSharedInd) {
		sharedInd = aSharedInd;
		setItDirty(true);
	}

	/**
	 * 
	 * @return Integer versionCtrlNbr
	 */
	public Integer getVersionCtrlNbr() {

		return versionCtrlNbr;
	}

	/**
	 * 
	 * @param aVersionCtrlNbr
	 */
	public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
		versionCtrlNbr = aVersionCtrlNbr;
		setItDirty(true);
	}

	/**
	 * 
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(java.lang.Object objectname1,
			java.lang.Object objectname2, Class<?> voClass) {
		voClass = ((PublicHealthCaseDT) objectname1).getClass();

		NedssUtils compareObjs = new NedssUtils();

		return (compareObjs.equals(objectname1, objectname2, voClass));
	}

	/**
	 * 
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isItDirty() {

		return itDirty;
	}

	/**
	 * 
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isItNew() {

		return itNew;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isItDelete() {

		return itDelete;
	}

	/**
	 * 
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/**
	 * 
	 * @return String
	 */
	public String getSuperclass() {

		return NEDSSConstants.CLASSTYPE_ACT;
	}

	/**
	 * 
	 * @return Long publicHealthCaseUid
	 */
	public Long getUid() {

		return publicHealthCaseUid;
	}

	public String getAddUserName() {
		return addUserName;
	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getLastChgUserName() {
		return lastChgUserName;
	}

	public void setLastChgUserName(String lastChgUserName) {
		this.lastChgUserName = lastChgUserName;
	}

	public boolean isPamCase() {
		return isPamCase;
	}

	public void setPamCase(boolean isPamCase) {
		this.isPamCase = isPamCase;
	}

	public Timestamp getInvestigatorAssignedTime() {
		return investigatorAssignedTime;
	}

	public void setInvestigatorAssignedTime(Timestamp aInvestigatorAssignedTime) {
		investigatorAssignedTime = aInvestigatorAssignedTime;
		setItDirty(true);
	}

	/**
	 * @return Timestamp getInvestigatorAssignedTime
	 */
	public String getInvestigatorAssignedTime_s() {

		if (getInvestigatorAssignedTime() == null)
			return null;
		return StringUtils.formatDate(getInvestigatorAssignedTime());
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setInvestigatorAssignedTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setInvestigatorAssignedTime(StringUtils
				.stringToStrutsTimestamp(strTime));
	}

	public String getHospitalizedIndCd() {
		return hospitalizedIndCd;
	}

	public void setHospitalizedIndCd(String hospitalizedIndCd) {
		this.hospitalizedIndCd = hospitalizedIndCd;
	}

	public String getPregnantIndCd() {
		return pregnantIndCd;
	}

	public void setPregnantIndCd(String pregnantIndCd) {
		this.pregnantIndCd = pregnantIndCd;
	}

	/*public String getDieFromIllnessIndCD() {
		return dieFromIllnessIndCD;
	}

	public void setDieFromIllnessIndCD(String dieFromIllnessIndCD) {
		this.dieFromIllnessIndCD = dieFromIllnessIndCD;
	}*/

	public String getDayCareIndCd() {
		return dayCareIndCd;
	}

	public void setDayCareIndCd(String dayCareIndCd) {
		this.dayCareIndCd = dayCareIndCd;
	}

	public String getFoodHandlerIndCd() {
		return foodHandlerIndCd;
	}

	public void setFoodHandlerIndCd(String foodHandlerIndCd) {
		this.foodHandlerIndCd = foodHandlerIndCd;
	}

	public String getImportedCountryCd() {
		return importedCountryCd;
	}

	public void setImportedCountryCd(String importedCountryCd) {
		this.importedCountryCd = importedCountryCd;
	}

	public String getImportedStateCd() {
		return importedStateCd;
	}

	public void setImportedStateCd(String importedStateCd) {
		this.importedStateCd = importedStateCd;
	}

	public String getImportedCountyCd() {
		return importedCountyCd;
	}

	public void setImportedCountyCd(String importedCountyCd) {
		this.importedCountyCd = importedCountyCd;
	}

	public Timestamp getHospitalizedAdminTime() {
		return hospitalizedAdmTime;
	}

	public void setHospitalizedAdminTime(Timestamp hospitalizedAdminTime) {
		this.hospitalizedAdmTime = hospitalizedAdminTime;
	}
	
	public String getHospitalizedAdminTime_s() {

		if (getHospitalizedAdminTime() == null)
			return null;
		return StringUtils.formatDate(getHospitalizedAdminTime());
	}

	public void setHospitalizedAdminTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setHospitalizedAdminTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	public Timestamp getHospitalizedDischargeTime() {
		return hospitalizedDischargeTime;
	}

	public void setHospitalizedDischargeTime(Timestamp hospitalizedDischargeTime) {
		this.hospitalizedDischargeTime = hospitalizedDischargeTime;
	}
	
	public String getHospitalizedDischargeTime_s() {

		if (getHospitalizedDischargeTime() == null)
			return null;
		return StringUtils.formatDate(getHospitalizedDischargeTime());
	}

	public void setHospitalizedDischargeTime_s(String strTime) {

		if (strTime == null)

			return;

		this.setHospitalizedDischargeTime(StringUtils.stringToStrutsTimestamp(strTime));
	}

	public BigDecimal getHospitalizedDurationAmt() {
		return hospitalizedDurationAmt;
	}

	public void setHospitalizedDurationAmt(BigDecimal hospitalizedDurationAmt) {
		this.hospitalizedDurationAmt = hospitalizedDurationAmt;
	}
	
	public void setHospitalizedDurationAmt_s(String sHospitalizedDurationAmt)
    {
        if(sHospitalizedDurationAmt == null || sHospitalizedDurationAmt.trim().equals(""))
        this.setHospitalizedDurationAmt( null);
        else
        this.setHospitalizedDurationAmt( new BigDecimal(sHospitalizedDurationAmt));
    }

	public Timestamp getDeceasedTime() {
		return deceasedTime;
	}

	public void setDeceasedTime(Timestamp deceasedTime) {
		this.deceasedTime = deceasedTime;
	}
	
	public String getDeceasedTime_s() {

		if (getDeceasedTime() == null)
			return null;
		return StringUtils.formatDate(getDeceasedTime());
	}

	public void setDeceasedTime_s(String deceasedTime) {

		if (deceasedTime == null)

			return;

		this.setDeceasedTime(StringUtils.stringToStrutsTimestamp(deceasedTime));
	}

	public String getImportedCityDescTxt() {
		return importedCityDescTxt;
	}

	public void setImportedCityDescTxt(String importedCityDescTxt) {
		this.importedCityDescTxt = importedCityDescTxt;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = PublicHealthCaseDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public String getCountIntervalCd() {
		return countIntervalCd;
	}

	public void setCountIntervalCd(String countIntervalCd) {
		this.countIntervalCd = countIntervalCd;
	}

	public String getReferralBasisCd() {
		return referralBasisCd;
	}

	public void setReferralBasisCd(String referralBasisCd) {
		this.referralBasisCd = referralBasisCd;
	}

	public String getCurrProcessStateCd() {
		return currProcessStateCd;
	}

	public void setCurrProcessStateCd(String currProcessStateCd) {
		this.currProcessStateCd = currProcessStateCd;
	}

	public String getInvPriorityCd() {
		return invPriorityCd;
	}

	public void setInvPriorityCd(String invPriorityCd) {
		this.invPriorityCd = invPriorityCd;
	}
	


	public Long getCurrentPatientUid() {
		return currentPatientUid;
	}

	public void setCurrentPatientUid(Long currentPatientUid) {
		this.currentPatientUid = currentPatientUid;
	}

	public boolean isStdHivProgramAreaCode() {
		return isStdHivProgramAreaCode;
	}

	public void setStdHivProgramAreaCode(boolean isStdHivProgramAreaCode) {
		
		this.isStdHivProgramAreaCode = isStdHivProgramAreaCode;
	}

	public String getCoinfectionId() {
		return coinfectionId;
	}

	public void setCoinfectionId(String coinfectionId) {
		this.coinfectionId = coinfectionId;
	}
	
	public boolean isStdOpenedFromClosed() {
		return isStdOpenedFromClosed;
	}

	public void setStdOpenedFromClosed(boolean isStdOpenedFromClosed) {
		this.isStdOpenedFromClosed = isStdOpenedFromClosed;
	}
	
	/**@since release 5.2
	 * This is a very special scenario that is applicable to merge investigations only!!!!!!
	 * @return true/false
	 */
	/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
	public boolean isReentrant() {
		return isReentrant;
	}


	public void setReentrant(boolean isReentrant) {
		this.isReentrant = isReentrant;
	}

*/

}