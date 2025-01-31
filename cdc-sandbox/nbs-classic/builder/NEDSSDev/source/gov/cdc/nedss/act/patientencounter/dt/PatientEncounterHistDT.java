package gov.cdc.nedss.act.patientencounter.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
 * Title:PatientEncounterHistDT
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class PatientEncounterHistDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;
    private Long patientEncounterUid;
    private Integer versionCtrlNbr;
    private String activityDurationAmt;
    private String activityDurationUnitCd;
    private Timestamp activityFromTime;
    private Timestamp activityToTime;
    private String acuityLevelCd;
    private String acuityLevelDescTxt;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String admissionSourceCd;
    private String admissionSourceDescTxt;
    private String birthEncounterInd;
    private String cd;
    private String cdDescTxt;
    private String confidentialityCd;
    private String confidentialityDescTxt;
    private String effectiveDurationAmt;
    private String effectiveDurationUnitCd;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String priorityCd;
    private String priorityDescTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String referralSourceCd;
    private String referralSourceDescTxt;
    private Integer repeatNbr;
    private String statusCd;
    private Timestamp statusTime;
    private String txt;
    private String userAffiliationTxt;
    private Long programJurisdictionOid;
    private String sharedInd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     *
     * @return Long patientEncounterUid
     */
    public Long getPatientEncounterUid() {

        return patientEncounterUid;
    }

    /**
     *
     * @param aPatientEncounterUid
     */
    public void setPatientEncounterUid(Long aPatientEncounterUid) {
        patientEncounterUid = aPatientEncounterUid;
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
     * @return String acuityLevelCd
     */
    public String getAcuityLevelCd() {

        return acuityLevelCd;
    }

    /**
     *
     * @param aAcuityLevelCd
     */
    public void setAcuityLevelCd(String aAcuityLevelCd) {
        acuityLevelCd = aAcuityLevelCd;
        setItDirty(true);
    }

    /**
     *
     * @return String acuityLevelDescTxt
     */
    public String getAcuityLevelDescTxt() {

        return acuityLevelDescTxt;
    }

    /**
     *
     * @param aAcuityLevelDescTxt
     */
    public void setAcuityLevelDescTxt(String aAcuityLevelDescTxt) {
        acuityLevelDescTxt = aAcuityLevelDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String  addReasonCd
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
     * @return String admissionSourceCd
     */
    public String getAdmissionSourceCd() {

        return admissionSourceCd;
    }

    /**
     *
     * @param aAdmissionSourceCd
     */
    public void setAdmissionSourceCd(String aAdmissionSourceCd) {
        admissionSourceCd = aAdmissionSourceCd;
        setItDirty(true);
    }

    /**
     *
     * @return String admissionSourceDescTxt
     */
    public String getAdmissionSourceDescTxt() {

        return admissionSourceDescTxt;
    }

    /**
     *
     * @param aAdmissionSourceDescTxt
     */
    public void setAdmissionSourceDescTxt(String aAdmissionSourceDescTxt) {
        admissionSourceDescTxt = aAdmissionSourceDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String birthEncounterInd
     */
    public String getBirthEncounterInd() {

        return birthEncounterInd;
    }

    /**
     *
     * @param aBirthEncounterInd
     */
    public void setBirthEncounterInd(String aBirthEncounterInd) {
        birthEncounterInd = aBirthEncounterInd;
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
     * @return String lastChgReasonCd
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
     * @return String priorityCd
     */
    public String getPriorityCd() {

        return priorityCd;
    }

    /**
     *
     * @param aPriorityCd
     */
    public void setPriorityCd(String aPriorityCd) {
        priorityCd = aPriorityCd;
        setItDirty(true);
    }

    /**
     *
     * @return String priorityDescTxt
     */
    public String getPriorityDescTxt() {

        return priorityDescTxt;
    }

    /**
     *
     * @param aPriorityDescTxt
     */
    public void setPriorityDescTxt(String aPriorityDescTxt) {
        priorityDescTxt = aPriorityDescTxt;
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
     * @return String referralSourceCd
     */
    public String getReferralSourceCd() {

        return referralSourceCd;
    }

    /**
     *
     * @param aReferralSourceCd
     */
    public void setReferralSourceCd(String aReferralSourceCd) {
        referralSourceCd = aReferralSourceCd;
        setItDirty(true);
    }

    /**
     *
     * @return String referralSourceDescTxt
     */
    public String getReferralSourceDescTxt() {

        return referralSourceDescTxt;
    }

    /**
     *
     * @param aReferralSourceDescTxt
     */
    public void setReferralSourceDescTxt(String aReferralSourceDescTxt) {
        referralSourceDescTxt = aReferralSourceDescTxt;
        setItDirty(true);
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
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((PatientEncounterHistDT)objectname1).getClass();

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
}