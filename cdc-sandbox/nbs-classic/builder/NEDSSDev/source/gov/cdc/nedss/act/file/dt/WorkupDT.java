package gov.cdc.nedss.act.file.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
 * Title: WorkupDT
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class WorkupDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;

    private Long workupUid;
    private String activityDurationAmt;
    private String activityDurationUnitCd;
    private Timestamp activityFromTime;
    private Timestamp activityToTime;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp assignTime;
    private Long assignWorkerId;
    private String cd;
    private String cdDescTxt;
    private String confidentialityCd;
    private String confidentialityDescTxt;
    private String diagnosisCd;
    private String diagnosisDescTxt;
    private String dispositionCd;
    private String dispositionDescTxt;
    private Timestamp dispositionTime;
    private Long dispositionWorkerId;
    private String effectiveDurationAmt;
    private String effectiveDurationUnitCd;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String exposureFrequency;
    private Timestamp exposureFromTime;
    private Timestamp exposureToTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private Integer repeatNbr;
    private String statusCd;
    private Timestamp statusTime;
    private String txt;
    private String userAffiliationTxt;
    private Long programJurisdictionOid;
    private String sharedInd;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     *
     * @return Long workupUid
     */
    public Long getWorkupUid() {

        return workupUid;
    }

    /**
     *
     * @param aWorkupUid
     */
    public void setWorkupUid(Long aWorkupUid) {
        workupUid = aWorkupUid;
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
     * @return Timestamp assignTime
     */
    public Timestamp getAssignTime() {

        return assignTime;
    }

    /**
     *
     * @param aAssignTime
     */
    public void setAssignTime(Timestamp aAssignTime) {
        assignTime = aAssignTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setAssignTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAssignTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return Long  assignWorkerId
     */
    public Long getAssignWorkerId() {

        return assignWorkerId;
    }

    /**
     *
     * @param aAssignWorkerId
     */
    public void setAssignWorkerId(Long aAssignWorkerId) {
        assignWorkerId = aAssignWorkerId;
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
     * @return String diagnosisCd
     */
    public String getDiagnosisCd() {

        return diagnosisCd;
    }

    /**
     *
     * @param aDiagnosisCd
     */
    public void setDiagnosisCd(String aDiagnosisCd) {
        diagnosisCd = aDiagnosisCd;
        setItDirty(true);
    }

    /**
     *
     * @return String diagnosisDescTxt
     */
    public String getDiagnosisDescTxt() {

        return diagnosisDescTxt;
    }

    /**
     *
     * @param aDiagnosisDescTxt
     */
    public void setDiagnosisDescTxt(String aDiagnosisDescTxt) {
        diagnosisDescTxt = aDiagnosisDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return Sting dispositionCd
     */
    public String getDispositionCd() {

        return dispositionCd;
    }

    /**
     *
     * @param aDispositionCd
     */
    public void setDispositionCd(String aDispositionCd) {
        dispositionCd = aDispositionCd;
        setItDirty(true);
    }

    /**
     *
     * @return String dispositionDescTxt
     */
    public String getDispositionDescTxt() {

        return dispositionDescTxt;
    }

    /**
     *
     * @param aDispositionDescTxt
     */
    public void setDispositionDescTxt(String aDispositionDescTxt) {
        dispositionDescTxt = aDispositionDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp dispositionTime
     */
    public Timestamp getDispositionTime() {

        return dispositionTime;
    }

    /**
     *
     * @param aDispositionTime
     */
    public void setDispositionTime(Timestamp aDispositionTime) {
        dispositionTime = aDispositionTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setDispositionTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setDispositionTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return Long dispositionWorkerId
     */
    public Long getDispositionWorkerId() {

        return dispositionWorkerId;
    }

    /**
     *
     * @param aDispositionWorkerId
     */
    public void setDispositionWorkerId(Long aDispositionWorkerId) {
        dispositionWorkerId = aDispositionWorkerId;
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
     * @return String exposureFrequency
     */
    public String getExposureFrequency() {

        return exposureFrequency;
    }

    /**
     *
     * @param aExposureFrequency
     */
    public void setExposureFrequency(String aExposureFrequency) {
        exposureFrequency = aExposureFrequency;
        setItDirty(true);
    }

    /**
     *
     * @return Timestamp exposureFromTime
     */
    public Timestamp getExposureFromTime() {

        return exposureFromTime;
    }

    /**
     *
     * @param aExposureFromTime
     */
    public void setExposureFromTime(Timestamp aExposureFromTime) {
        exposureFromTime = aExposureFromTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setExposureFromTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setExposureFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return Timestamp exposureToTime
     */
    public Timestamp getExposureToTime() {

        return exposureToTime;
    }

    /**
     *
     * @param aExposureToTime
     */
    public void setExposureToTime(Timestamp aExposureToTime) {
        exposureToTime = aExposureToTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setExposureToTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setExposureToTime(StringUtils.stringToStrutsTimestamp(strTime));
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
        voClass = ((WorkupDT)objectname1).getClass();

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