package gov.cdc.nedss.act.publichealthcase.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Title: PublicHealthCaseHistDT
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PublicHealthCaseHistDT
    extends AbstractVO    implements RootDTInterface {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String caseTypeCd;
    private Long publicHealthCaseUid;
    private Integer versionCtrlNbr;
    private String activityDurationAmt;
    private String activityDurationUnitCd;
    private Timestamp activityFromTime;
    private Timestamp activityToTime;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cd;
    private String cdDescTxt;
    private String cdSystemCd;
    private String cdSystemDescTxt;
    private String caseClassCd;
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
    private String jurisdictionCd;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String mmwrYear;
    private String mmwrWeek;
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
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
 // Added for Extending PHC table for common fields - ODS changes activity
	// changes
    private Timestamp investigatorAssignedTime;
	private String hospitalizedIndCD;
	private Timestamp hospitalizedAdminTime;
	private Timestamp hospitalizedDischargeTime;
	private BigDecimal hospitalizedDurationAmt;
	private String pregnantIndCD;
	private String dieFromIllnessIndCD;
	private String dayCareIndCD;
	private String foodHandlerIndCD;
	private String importedCountryCD;
	private String importedStateCD;
	private String importedCityCD;
	private String importedCountyCD;
	private Timestamp infectiousFromDate;
	private Timestamp infectiousToDate;
	private String contactInvStatus;
	private String contactInvTxt;
	private String referralBasisCd;
	private String currProcessStateCd;	
	private String priorityCd;

	public Timestamp getInfectiousFromDate() {
		return infectiousFromDate;
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
     * @return String lastReasonChgCd
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
     * @return String localId
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
     * @return String outComeCd
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
     * @return Long patientGoupId
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
     * @return String rptCntyCd
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
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((PublicHealthCaseHistDT)objectname1).getClass();

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

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getInvestigatorAssignedTime() {
		return investigatorAssignedTime;
	}

	public void setInvestigatorAssignedTime(Timestamp investigatorAssignedTime) {
		this.investigatorAssignedTime = investigatorAssignedTime;
	}

	public String getHospitalizedIndCD() {
		return hospitalizedIndCD;
	}

	public void setHospitalizedIndCD(String hospitalizedIndCD) {
		this.hospitalizedIndCD = hospitalizedIndCD;
	}

	public Timestamp getHospitalizedAdminTime() {
		return hospitalizedAdminTime;
	}

	public void setHospitalizedAdminTime(Timestamp hospitalizedAdminTime) {
		this.hospitalizedAdminTime = hospitalizedAdminTime;
	}

	public Timestamp getHospitalizedDischargeTime() {
		return hospitalizedDischargeTime;
	}

	public void setHospitalizedDischargeTime(Timestamp hospitalizedDischargeTime) {
		this.hospitalizedDischargeTime = hospitalizedDischargeTime;
	}

	public BigDecimal getHospitalizedDurationAmt() {
		return hospitalizedDurationAmt;
	}

	public void setHospitalizedDurationAmt(BigDecimal hospitalizedDurationAmt) {
		this.hospitalizedDurationAmt = hospitalizedDurationAmt;
	}

	public String getPregnantIndCD() {
		return pregnantIndCD;
	}

	public void setPregnantIndCD(String pregnantIndCD) {
		this.pregnantIndCD = pregnantIndCD;
	}

	public String getDieFromIllnessIndCD() {
		return dieFromIllnessIndCD;
	}

	public void setDieFromIllnessIndCD(String dieFromIllnessIndCD) {
		this.dieFromIllnessIndCD = dieFromIllnessIndCD;
	}

	public String getDayCareIndCD() {
		return dayCareIndCD;
	}

	public void setDayCareIndCD(String dayCareIndCD) {
		this.dayCareIndCD = dayCareIndCD;
	}

	public String getFoodHandlerIndCD() {
		return foodHandlerIndCD;
	}

	public void setFoodHandlerIndCD(String foodHandlerIndCD) {
		this.foodHandlerIndCD = foodHandlerIndCD;
	}

	public String getImportedCountryCD() {
		return importedCountryCD;
	}

	public void setImportedCountryCD(String importedCountryCD) {
		this.importedCountryCD = importedCountryCD;
	}

	public String getImportedStateCD() {
		return importedStateCD;
	}

	public void setImportedStateCD(String importedStateCD) {
		this.importedStateCD = importedStateCD;
	}

	public String getImportedCityCD() {
		return importedCityCD;
	}

	public void setImportedCityCD(String importedCityCD) {
		this.importedCityCD = importedCityCD;
	}

	public String getImportedCountyCD() {
		return importedCountyCD;
	}

	public void setImportedCountyCD(String importedCountyCD) {
		this.importedCountyCD = importedCountyCD;
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

	public String getPriorityCd() {
		return priorityCd;
	}

	public void setPriorityCd(String priorityCd) {
		this.priorityCd = priorityCd;
	}
	
}