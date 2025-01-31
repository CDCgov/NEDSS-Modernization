/**
 *
 * <p>Title: ObservationDT.java</p>
 * <p>Description:This is a class which sets/gets(retrieves)
 *                all the fields to the observation database table </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author NEDSS team
 * @version 1.0
 */

package gov.cdc.nedss.act.observation.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

public class ObservationDT extends AbstractVO implements RootDTInterface
{

    private static final long serialVersionUID = 1L;
    private Long observationUid;

    private String activityDurationAmt;

    private String activityDurationUnitCd;

    private Timestamp activityFromTime;

    private Timestamp activityToTime;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;

    private String addUserName; // BB - civil00012298 - add Name field to
                                // display now instead of Id

    private String altCd;

    private String altCdDescTxt;

    private String altCdSystemCd;

    private String altCdSystemDescTxt;

    private String cd;

    private String cdDerivedInd;

    private String cdDescTxt;

    private String cdSystemCd;

    private String cdSystemDescTxt;

    private String confidentialityCd;

    private String confidentialityDescTxt;

    private String ctrlCdDisplayForm;

    private String ctrlCdUserDefined1;

    private String ctrlCdUserDefined2;

    private String ctrlCdUserDefined3;

    private String ctrlCdUserDefined4;

    private Integer derivationExp;

    private String effectiveDurationAmt;

    private String effectiveDurationUnitCd;

    private Timestamp effectiveFromTime;

    private Timestamp effectiveToTime;

    private String electronicInd;

    private String groupLevelCd;

    private String jurisdictionCd;

    private String labConditionCd;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String lastChgUserName; // BB - civil00012298 - add Name field to
                                    // display now instead of Id

    private String localId;

    private String methodCd;

    private String methodDescTxt;

    private String obsDomainCd;

    private String obsDomainCdSt1;

    private String pnuCd;

    private String priorityCd;

    private String priorityDescTxt;

    private String progAreaCd;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private Integer repeatNbr;

    private Timestamp rptToStateTime;

    private String statusCd;

    private Timestamp statusTime;

    private Long subjectPersonUid;

    private String targetSiteCd;

    private String targetSiteDescTxt;

    private String txt;

    private String userAffiliationTxt;

    private String valueCd;

    private String ynuCd;

    private Long programJurisdictionOid;

    private String sharedInd;

    private Integer versionCtrlNbr;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    private String cdVersion;

    private String searchResultOT;

    private String searchResultRT;

    private String cdSystemCdOT;

    private String cdSystemCdRT;

    private String hiddenCd;

    private String codedResultCd;
    private String organismCd;
    private String susceptabilityVal;
    private String resultedMethodCd;
    private String drugNameCd;
    private String interpretiveFlagCd;
    
    private String processingDecisionCd;
    private String processingDecisionTxt;

	// Task: #2567, #2566
    private String pregnantIndCd; 
    private Integer pregnantWeek; 

    /**
     * @return the processingDecisionCd
     */
    public String getProcessingDecisionCd()
    {
        return processingDecisionCd;
    }

    /**
     * @param processingDecisionCd
     *            the processingDecisionCd to set
     */
    public void setProcessingDecisionCd(String processingDecisionCd)
    {
        this.processingDecisionCd = processingDecisionCd;
    }

    /**
     * gets ObservationUid
     * 
     * @return : Long value
     */
    public Long getObservationUid()
    {
        return observationUid;
    }

    /**
     * sets ObservationUid
     * 
     * @param aObservationUid
     *            : Long value
     */
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

    /**
     * gets ActivityDurationAmt
     * 
     * @return : String value
     */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }

    /**
     * sets ActivityDurationAmt
     * 
     * @param aActivityDurationAmt
     *            : String value
     */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

    /**
     * gets ActivityDurationUnitCd
     * 
     * @return : String value
     */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }

    /**
     * sets ActivityDurationUnitCd
     * 
     * @param aActivityDurationUnitCd
     *            : String value
     */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets ActivityFromTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }

    /**
     * sets ActivityFromTime
     * 
     * @param aActivityFromTime
     *            :Timestamp value
     */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

    /**
     * sets ActivityFromTime(convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setActivityFromTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets ActivityToTime
     * 
     * @return :Timestamp value
     */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }

    /**
     * sets ActivityToTime
     * 
     * @param aActivityToTime
     *            :Timestamp value
     */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

    /**
     * sets ActivityToTime(convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setActivityToTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets AddReasonCd
     * 
     * @return : String value
     */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * sets ActivityToTime(convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setRptToStateTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setRptToStateTimeToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * sets ActivityToTime
     * 
     * @param aActivityToTime
     *            :Timestamp value
     */
    public void setRptToStateTimeToTime(Timestamp aRptToStateTimeToTime)
    {
        rptToStateTime = aRptToStateTimeToTime;
        setItDirty(true);
    }

    /**
     * sets AddReasonCd
     * 
     * @param aAddReasonCd
     *            : String value
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets AddTime
     * 
     * @return :Timestamp value
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * sets AddTime
     * 
     * @param aAddTime
     *            : Timestamp value
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets AddTime(convenient method for struts)
     * 
     * @param strTime
     *            :String value
     */
    public void setAddTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets AddUserId
     * 
     * @return : Long value
     */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * sets AddUserId
     * 
     * @param aAddUserId
     *            : Long value
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    // BB - civil00012298 - add Name field to display now instead of Id
    /**
     * gets AddUserName
     * 
     * @return : String value
     */
    public String getAddUserName()
    {
        return addUserName;
    }

    /**
     * sets AddUserName
     * 
     * @param aAddUserName
     *            : String value
     */
    public void setAddUserName(String aAddUserName)
    {
        addUserName = aAddUserName;
        setItDirty(true);
    }

    /**
     * gets AltCd
     * 
     * @return String
     */
    public String getAltCd()
    {
        return this.altCd;
    }

    /**
     * sets AltCd
     * 
     * @param aAltCd
     */
    public void setAltCd(String aAltCd)
    {
        this.altCd = aAltCd;
        setItDirty(true);
    }

    /**
     * gets AltCdDescTxt
     * 
     * @return String
     */
    public String getAltCdDescTxt()
    {
        return this.altCdDescTxt;
    }

    /**
     * sets AltCdDescTxt
     * 
     * @param aAltCdDescTxt
     */
    public void setAltCdDescTxt(String aAltCdDescTxt)
    {
        this.altCdDescTxt = aAltCdDescTxt;
        setItDirty(true);
    }

    /**
     * gets AltCdSystemCd
     * 
     * @return String
     */
    public String getAltCdSystemCd()
    {
        return this.altCdSystemCd;
    }

    /**
     * sets AltCdSystemCd
     * 
     * @param aAltCdSystemCd
     */
    public void setAltCdSystemCd(String aAltCdSystemCd)
    {
        this.altCdSystemCd = aAltCdSystemCd;
        setItDirty(true);
    }

    /**
     * gets AltCdSystemDescTxt
     * 
     * @return String
     */
    public String getAltCdSystemDescTxt()
    {
        return this.altCdSystemDescTxt;
    }

    /**
     * set AltCdSystemCdDescTxt
     * 
     * @param aAltCdSystemDescTxt
     */
    public void setAltCdSystemDescTxt(String aAltCdSystemDescTxt)
    {
        this.altCdSystemDescTxt = aAltCdSystemDescTxt;
        setItDirty(true);
    }

    /**
     * gets Cd
     * 
     * @return : String value
     */
    public String getCd()
    {
        return cd;
    }

    /**
     * sets Cd
     * 
     * @param aCd
     *            : String value
     */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * gets CdDerivedInd
     * 
     * @return String
     */
    public String getCdDerivedInd()
    {
        return this.cdDerivedInd;
    }

    /**
     * sets CdDerivedInd
     * 
     * @param aCdDerivedInd
     */
    public void setCdDerivedInd(String aCdDerivedInd)
    {
        this.cdDerivedInd = aCdDerivedInd;
        setItDirty(true);
    }

    /**
     * gets CdDescTxt
     * 
     * @return : String value
     */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
     * sets CdDescTxt
     * 
     * @param aCdDescTxt
     *            : String value
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * gets CdSystemCd
     * 
     * @return : String value
     */
    public String getCdSystemCd()
    {
        return cdSystemCd;
    }

    /**
     * sets CdSystemCd
     * 
     * @param aCdSystemCd
     *            : String value
     */
    public void setCdSystemCd(String aCdSystemCd)
    {
        cdSystemCd = aCdSystemCd;
        setItDirty(true);
    }

    /**
     * gets CdSystemDescTxt
     * 
     * @return : String value
     */
    public String getCdSystemDescTxt()
    {
        return cdSystemDescTxt;
    }

    /**
     * sets CdSystemDescTxt
     * 
     * @param aCdSystemDescTxt
     *            : String value
     */
    public void setCdSystemDescTxt(String aCdSystemDescTxt)
    {
        cdSystemDescTxt = aCdSystemDescTxt;
        setItDirty(true);
    }

    /**
     * gets ConfidentialityCd
     * 
     * @return : String value
     */
    public String getConfidentialityCd()
    {
        return confidentialityCd;
    }

    /**
     * sets ConfidentialityCd
     * 
     * @param aConfidentialityCd
     *            : String value
     */
    public void setConfidentialityCd(String aConfidentialityCd)
    {
        confidentialityCd = aConfidentialityCd;
        setItDirty(true);
    }

    /**
     * gets ConfidentialityDescTxt
     * 
     * @return : String value
     */
    public String getConfidentialityDescTxt()
    {
        return confidentialityDescTxt;
    }

    /**
     * sets ConfidentialityDescTxt
     * 
     * @param aConfidentialityDescTxt
     *            : String value
     */
    public void setConfidentialityDescTxt(String aConfidentialityDescTxt)
    {
        confidentialityDescTxt = aConfidentialityDescTxt;
        setItDirty(true);
    }

    /**
     * gets CtrlCdDisplayForm
     * 
     * @return : String value
     */
    public String getCtrlCdDisplayForm()
    {
        return ctrlCdDisplayForm;
    }

    /**
     * sets CtrlCdDisplayForm
     * 
     * @param aCtrlCdDisplayForm
     *            : String value
     */
    public void setCtrlCdDisplayForm(String aCtrlCdDisplayForm)
    {
        ctrlCdDisplayForm = aCtrlCdDisplayForm;
        setItDirty(true);
    }

    /**
     * gets CtrlCdUserDefined1
     * 
     * @return : String value
     */
    public String getCtrlCdUserDefined1()
    {
        return ctrlCdUserDefined1;
    }

    /**
     * sets CtrlCdUserDefined1
     * 
     * @param aCtrlCdUserDefined1
     *            : String value
     */
    public void setCtrlCdUserDefined1(String aCtrlCdUserDefined1)
    {
        ctrlCdUserDefined1 = aCtrlCdUserDefined1;
        setItDirty(true);
    }

    /**
     * gets CtrlCdUserDefined2
     * 
     * @return : String value
     */
    public String getCtrlCdUserDefined2()
    {
        return ctrlCdUserDefined2;
    }

    /**
     * sets CtrlCdUserDefined2
     * 
     * @param aCtrlCdUserDefined2
     *            : String value
     */
    public void setCtrlCdUserDefined2(String aCtrlCdUserDefined2)
    {
        ctrlCdUserDefined2 = aCtrlCdUserDefined2;
        setItDirty(true);
    }

    /**
     * gets CtrlCdUserDefined3
     * 
     * @return : String value
     */
    public String getCtrlCdUserDefined3()
    {
        return ctrlCdUserDefined3;
    }

    /**
     * sets CtrlCdUserDefined3
     * 
     * @param aCtrlCdUserDefined3
     *            : String value
     */
    public void setCtrlCdUserDefined3(String aCtrlCdUserDefined3)
    {
        ctrlCdUserDefined3 = aCtrlCdUserDefined3;
        setItDirty(true);
    }

    /**
     * gets CtrlCdUserDefined4
     * 
     * @return : String value
     */
    public String getCtrlCdUserDefined4()
    {
        return ctrlCdUserDefined4;
    }

    /**
     * sets CtrlCdUserDefined4
     * 
     * @param aCtrlCdUserDefined4
     *            : String value
     */
    public void setCtrlCdUserDefined4(String aCtrlCdUserDefined4)
    {
        ctrlCdUserDefined4 = aCtrlCdUserDefined4;
        setItDirty(true);
    }

    /**
     * gets DerivationExp
     * 
     * @return : Integer value
     */
    public Integer getDerivationExp()
    {
        return derivationExp;
    }

    /**
     * sets DerivationExp
     * 
     * @param aDerivationExp
     *            : Integer value
     */
    public void setDerivationExp(Integer aDerivationExp)
    {
        derivationExp = aDerivationExp;
        setItDirty(true);
    }

    /**
     * gets EffectiveDurationAmt
     * 
     * @return : String value
     */
    public String getEffectiveDurationAmt()
    {
        return effectiveDurationAmt;
    }

    /**
     * sets EffectiveDurationAmt
     * 
     * @param aEffectiveDurationAmt
     *            : String value
     */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

    /**
     * gets EffectiveDurationUnitCd
     * 
     * @return : String value
     */
    public String getEffectiveDurationUnitCd()
    {
        return effectiveDurationUnitCd;
    }

    /**
     * sets EffectiveDurationUnitCd
     * 
     * @param aEffectiveDurationUnitCd
     *            : String value
     */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets EffectiveFromTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
     * sets EffectiveFromTime
     * 
     * @param aEffectiveFromTime
     *            : Timestamp value
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
     * sets EffectiveFromTime( convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setEffectiveFromTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets EffectiveToTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
     * sets EffectiveToTime
     * 
     * @param aEffectiveToTime
     *            : Timestamp value
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
     * sets EffectiveToTime(convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setEffectiveToTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets ElectronicInd
     * 
     * @return : String value
     */
    public String getElectronicInd()
    {
        return electronicInd;
    }

    /**
     * sets ElectronicInd
     * 
     * @param aElectronicInd
     *            : String value
     */
    public void setElectronicInd(String aElectronicInd)
    {
        electronicInd = aElectronicInd;
        setItDirty(true);
    }

    /**
     * gets GroupLevelCd
     * 
     * @return : String value
     */
    public String getGroupLevelCd()
    {
        return groupLevelCd;
    }

    /**
     * sets GroupLevelCd
     * 
     * @param aGroupLevelCd
     *            : String value
     */
    public void setGroupLevelCd(String aGroupLevelCd)
    {
        groupLevelCd = aGroupLevelCd;
        setItDirty(true);
    }

    /**
     * gets JurisdictionCd
     * 
     * @return : String value
     */
    public String getJurisdictionCd()
    {
        return jurisdictionCd;
    }

    /**
     * sets JurisdictionCd
     * 
     * @param aJurisdictionCd
     *            : String value
     */
    public void setJurisdictionCd(String aJurisdictionCd)
    {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * gets LabConditionCd
     * 
     * @return : String value
     */
    public String getLabConditionCd()
    {
        return labConditionCd;
    }

    /**
     * sets LabConditionCd
     * 
     * @param aLabConditionCd
     *            : String value
     */
    public void setLabConditionCd(String aLabConditionCd)
    {
        labConditionCd = aLabConditionCd;
        setItDirty(true);
    }

    /**
     * gets LastChgReasonCd
     * 
     * @return : String value
     */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * sets LastChgReasonCd
     * 
     * @param aLastChgReasonCd
     *            : String value
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * gets LastChgTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * sets LastChgTime
     * 
     * @param aLastChgTime
     *            : Timestamp value
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets LastChgTime( convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setLastChgTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets LastChgUserId
     * 
     * @return : Long value
     */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * sets LastChgUserId
     * 
     * @param aLastChgUserId
     *            : Long value
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    // BB - civil00012298 - add Name field to display now instead of Id
    /**
     * gets LastChgUserName
     * 
     * @return : String value
     */
    public String getLastChgUserName()
    {
        return lastChgUserName;
    }

    /**
     * sets LastChgUserName
     * 
     * @param aLastChgUserName
     *            : String value
     */
    public void setLastChgUserName(String aLastChgUserName)
    {
        lastChgUserName = aLastChgUserName;
        setItDirty(true);
    }

    /**
     * gets LocalId
     * 
     * @return : String value
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * sets LocalId
     * 
     * @param aLocalId
     *            : String value
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * gets MethodCd
     * 
     * @return : String value
     */
    public String getMethodCd()
    {
        return methodCd;
    }

    /**
     * sets MethodCd
     * 
     * @param aMethodCd
     *            : String value
     */
    public void setMethodCd(String aMethodCd)
    {
        methodCd = aMethodCd;
        setItDirty(true);
    }

    /**
     * gets MethodDescTxt
     * 
     * @return : String value
     */
    public String getMethodDescTxt()
    {
        return methodDescTxt;
    }

    /**
     * sets MethodDescTxt
     * 
     * @param aMethodDescTxt
     *            : String value
     */
    public void setMethodDescTxt(String aMethodDescTxt)
    {
        methodDescTxt = aMethodDescTxt;
        setItDirty(true);
    }

    /**
     * gets ObsDomainCd
     * 
     * @return : String value
     */
    public String getObsDomainCd()
    {
        return obsDomainCd;
    }

    /**
     * sets ObsDomainCd
     * 
     * @param aObsDomainCd
     *            : String value
     */
    public void setObsDomainCd(String aObsDomainCd)
    {
        obsDomainCd = aObsDomainCd;
        setItDirty(true);
    }

    /**
     * gets ObsDomainCdSt1
     * 
     * @return : String value
     */
    public String getObsDomainCdSt1()
    {
        return obsDomainCdSt1;
    }

    /**
     * sets ObsDomainCdSt1
     * 
     * @param aObsDomainCdSt1
     *            : String value
     */
    public void setObsDomainCdSt1(String aObsDomainCdSt1)
    {
        obsDomainCdSt1 = aObsDomainCdSt1;
        setItDirty(true);
    }

    /**
     * gets PnuCd
     * 
     * @return : String value
     */
    public String getPnuCd()
    {
        return pnuCd;
    }

    /**
     * sets PnuCd
     * 
     * @param aPnuCd
     *            : String value
     */
    public void setPnuCd(String aPnuCd)
    {
        pnuCd = aPnuCd;
        setItDirty(true);
    }

    /**
     * gets PriorityCd
     * 
     * @return : String value
     */
    public String getPriorityCd()
    {
        return priorityCd;
    }

    /**
     * sets PriorityCd
     * 
     * @param aPriorityCd
     *            : String value
     */
    public void setPriorityCd(String aPriorityCd)
    {
        priorityCd = aPriorityCd;
        setItDirty(true);
    }

    /**
     * gets PriorityDescTxt
     * 
     * @return : String value
     */
    public String getPriorityDescTxt()
    {
        return priorityDescTxt;
    }

    /**
     * sets PriorityDescTxt
     * 
     * @param aPriorityDescTxt
     *            : String value
     */
    public void setPriorityDescTxt(String aPriorityDescTxt)
    {
        priorityDescTxt = aPriorityDescTxt;
        setItDirty(true);
    }

    /**
     * gets ProgAreaCd
     * 
     * @return : String value
     */
    public String getProgAreaCd()
    {
        return progAreaCd;
    }

    /**
     * sets ProgAreaCd
     * 
     * @param aProgAreaCd
     *            (String value)
     */
    public void setProgAreaCd(String aProgAreaCd)
    {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * gets RecordStatusCd
     * 
     * @return : String value
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * sets RecordStatusCd
     * 
     * @param aRecordStatusCd
     *            : String value
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * gets RecordStatusTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * sets RecordStatusTime
     * 
     * @param aRecordStatusTime
     *            : Timestamp value
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * sets RecordStatusTime(convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setRecordStatusTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets RepeatNbr
     * 
     * @return : Integer value
     */
    public Integer getRepeatNbr()
    {
        return repeatNbr;
    }

    /**
     * gets RptToStateTime
     * 
     * @return Timestamp
     */
    public Timestamp getRptToStateTime()
    {
        return this.rptToStateTime;
    }

    /**
     * sets RptToStateTime
     * 
     * @param aRptToStateTime
     */
    public void setRptToStateTime(Timestamp aRptToStateTime)
    {
        this.rptToStateTime = aRptToStateTime;
        setItDirty(true);
    }

    /**
     * sets RepeatNbr
     * 
     * @param aRepeatNbr
     *            : Integer value
     */
    public void setRepeatNbr(Integer aRepeatNbr)
    {
        repeatNbr = aRepeatNbr;
        setItDirty(true);
    }

    /**
     * gets StatusCd
     * 
     * @return : String value
     */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * sets StatusCd
     * 
     * @param aStatusCd
     *            : String value
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * gets StatusTime
     * 
     * @return : Timestamp value
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * sets StatusTime
     * 
     * @param aStatusTime
     *            : Timestamp value
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * sets StatusTime( convenient method for struts)
     * 
     * @param strTime
     *            : String value
     */
    public void setStatusTime_s(String strTime)
    {
        if (strTime == null)
            return;
        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets SubjectPersonUid
     * 
     * @return : Long value
     */
    public Long getSubjectPersonUid()
    {
        return subjectPersonUid;
    }

    /**
     * sets SubjectPersonUid
     * 
     * @param aSubjectPersonUid
     *            : Long value
     */
    public void setSubjectPersonUid(Long aSubjectPersonUid)
    {
        subjectPersonUid = aSubjectPersonUid;
        setItDirty(true);
    }

    /**
     * gets TargetSiteCd
     * 
     * @return : String value
     */
    public String getTargetSiteCd()
    {
        return targetSiteCd;
    }

    /**
     * sets TargetSiteCd
     * 
     * @param aTargetSiteCd
     *            : String value
     */
    public void setTargetSiteCd(String aTargetSiteCd)
    {
        targetSiteCd = aTargetSiteCd;
        setItDirty(true);
    }

    /**
     * gets TargetSiteDescTxt
     * 
     * @return : String value
     */
    public String getTargetSiteDescTxt()
    {
        return targetSiteDescTxt;
    }

    /**
     * sets TargetSiteDescTxt
     * 
     * @param aTargetSiteDescTxt
     *            : String value
     */
    public void setTargetSiteDescTxt(String aTargetSiteDescTxt)
    {
        targetSiteDescTxt = aTargetSiteDescTxt;
        setItDirty(true);
    }

    /**
     * gets Txt
     * 
     * @return : String value
     */
    public String getTxt()
    {
        return txt;
    }

    /**
     * sets Txt
     * 
     * @param aTxt
     *            : String value
     */
    public void setTxt(String aTxt)
    {
        txt = aTxt;
        setItDirty(true);
    }

    /**
     * gets UserAffiliationTxt
     * 
     * @return : String value
     */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * sets UserAffiliationTxt
     * 
     * @param aUserAffiliationTxt
     *            : String value
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets ValueCd
     * 
     * @return : String value
     */
    public String getValueCd()
    {
        return valueCd;
    }

    /**
     * sets ValueCd
     * 
     * @param aValueCd
     *            : String value
     */
    public void setValueCd(String aValueCd)
    {
        valueCd = aValueCd;
        setItDirty(true);
    }

    /**
     * gets YnuCd
     * 
     * @return : String value
     */
    public String getYnuCd()
    {
        return ynuCd;
    }

    /**
     * sets YnuCd
     * 
     * @param aYnuCd
     *            : String value
     */
    public void setYnuCd(String aYnuCd)
    {
        ynuCd = aYnuCd;
        setItDirty(true);
    }

    /**
     * gets ProgramJurisdictionOid
     * 
     * @return : Long value
     */
    public Long getProgramJurisdictionOid()
    {
        return programJurisdictionOid;
    }

    /**
     * sets ProgramJurisdictionOid
     * 
     * @param aProgramJurisdictionOid
     *            : Long value
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * gets SharedInd
     * 
     * @return : String value
     */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
     * sets SharedInd
     * 
     * @param aSharedInd
     *            : String value
     */
    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * gets VersionCtrlNbr
     * 
     * @return : Integer value
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * sets VersionCtrlNbr
     * 
     * @param aVersionCtrlNbr
     *            : Integer value
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * gets if objectname1 and objectname2 are equal or not
     * 
     * @param objectname1
     *            : first object to be compared
     * @param objectname2
     *            : second object to be compared
     * @param voClass
     *            : (( ObservationDT) objectname1).getClass()
     * @return : boolean value
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass = ((ObservationDT) objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     * sets itDirty
     * 
     * @param itDirty
     *            : boolean value
     */
    public void setItDirty(boolean itDirty)
    {
        this.itDirty = itDirty;
    }

    /**
     * gets isDirty
     * 
     * @return : boolean value
     */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
     * sets itNew
     * 
     * @param itNew
     *            :boolean value
     */
    public void setItNew(boolean itNew)
    {
        this.itNew = itNew;
    }

    /**
     * gets itNew
     * 
     * @return : boolean value
     */
    public boolean isItNew()
    {
        return itNew;
    }

    /**
     * gets itdelete
     * 
     * @return : boolean value
     */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
     * sets itDelete
     * 
     * @param itDelete
     *            :boolean value
     */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
     * returns superclass
     * 
     * @return : String value
     */
    public String getSuperclass()
    {
        return NEDSSConstants.CLASSTYPE_ACT;
    }

    /**
     * gets Observation UID
     * 
     * @return : Long value
     */
    public Long getUid()
    {
        return observationUid;
    }

    /**
     * gets cdVersion
     * 
     * @return : String value
     */
    public String getCdVersion()
    {
        return cdVersion;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setCdVersion(String aCdVersion)
    {
        cdVersion = aCdVersion;
        setItDirty(true);
    }

    /**
     * gets cdVersion
     * 
     * @return : String value
     */
    public String getSearchResultOT()
    {
        return searchResultOT;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setSearchResultOT(String aSearchResultOT)
    {
        searchResultOT = aSearchResultOT;
        setItDirty(true);
    }

    /**
     * gets cdVersion
     * 
     * @return : String value
     */
    public String getSearchResultRT()
    {
        return searchResultRT;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setSearchResultRT(String aSearchResultRT)
    {
        searchResultRT = aSearchResultRT;
        setItDirty(true);
    }

    /**
     * gets cdVersion
     * 
     * @return : String value
     */
    public String getCdSystemCdOT()
    {
        return cdSystemCdOT;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setCdSystemCdOT(String aCdSystemCdOT)
    {
        cdSystemCdOT = aCdSystemCdOT;
        setItDirty(true);
    }

    /**
     * gets cdVersion
     * 
     * @return : String value
     */
    public String getCdSystemCdRT()
    {
        return cdSystemCdRT;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setCdSystemCdRT(String aCdSystemCdRT)
    {
        cdSystemCdRT = aCdSystemCdRT;
        setItDirty(true);
    }

    public String getHiddenCd()
    {
        return hiddenCd;
    }

    /**
     * sets cdVersion
     * 
     * @param aCdVersion
     *            : String value
     */
    public void setHiddenCd(String aHiddenCd)
    {
        hiddenCd = aHiddenCd;
        setItDirty(true);
    }

    /**
     * public String getCodedResultCd() { return codedResultCd; }
     * 
     * public void setCodedResultCd(String aCodedResultCd) { codedResultCd =
     * aCodedResultCd; setItDirty(true); }
     * 
     * public String getOrganismCd() { return organismCd; }
     * 
     * public void setOrganismCd(String aOrganismCd) { organismCd = aOrganismCd;
     * setItDirty(true); }
     * 
     * 
     * 
     * 
     * 
     * public String getSusceptabilityVal() { return susceptabilityVal; }
     * 
     * public void setSusceptabilityVal(String aSusceptabilityVal) {
     * susceptabilityVal = aSusceptabilityVal; setItDirty(true); }
     * 
     * public String getResultedMethodCd() { return resultedMethodCd; }
     * 
     * public void setResultedMethodCd(String aResultedMethodCd) {
     * resultedMethodCd = aResultedMethodCd; setItDirty(true); }
     * 
     * public String getDrugNameCd() { return drugNameCd; }
     * 
     * public void setDrugNameCd(String aDrugNameCd) { drugNameCd = aDrugNameCd;
     * setItDirty(true); }
     * 
     * public String getInterpretiveFlagCd() { return interpretiveFlagCd; }
     * 
     * public void setInterpretiveFlagCd(String aInterpretiveFlagCd) {
     * interpretiveFlagCd = aInterpretiveFlagCd; setItDirty(true); }
     */

    public String getPregnantIndCd()
    {
        return pregnantIndCd;
    }

    public void setPregnantIndCd(String pregnantIndCd)
    {
        this.pregnantIndCd = pregnantIndCd;
    }

    public Integer getPregnantWeek()
    {
        return pregnantWeek;
    }

    public void setPregnantWeek(Integer pregnantWeek)
    {
        this.pregnantWeek = pregnantWeek;
    }

    public String getProcessingDecisionTxt() {
		return processingDecisionTxt;
	}

	public void setProcessingDecisionTxt(String processingDecisionTxt) {
		this.processingDecisionTxt = processingDecisionTxt;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = ObservationDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
}
