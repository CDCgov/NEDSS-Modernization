

package gov.cdc.nedss.act.notification.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

public class NotificationDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;

    private Long notificationUid;

    private String activityDurationAmt;

    private String activityDurationUnitCd;

    private Timestamp activityFromTime;

    private Timestamp activityToTime;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;

    private String caseClassCd;

    private String caseConditionCd;

    private String cd;

    private String cdDescTxt;

    private String confidentialityCd;

    private String confidentialityDescTxt;

    private String confirmationMethodCd;

    private String effectiveDurationAmt;

    private String effectiveDurationUnitCd;

    private Timestamp effectiveFromTime;

    private Timestamp effectiveToTime;

    private String jurisdictionCd;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String localId;

    private String messageTxt;

    private String methodCd;

    private String methodDescTxt;

    private String mmwrWeek;

    private String mmwrYear;

    private String nedssVersionNbr;

    private String progAreaCd;

    private String reasonCd;

    private String reasonDescTxt;

    private String recordCount;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private Integer repeatNbr;

    private Timestamp rptSentTime;

    private String rptSourceCd;

    private String rptSourceTypeCd;

    private String statusCd;

    private Timestamp statusTime;

    private String txt;

    private String userAffiliationTxt;

    private Long programJurisdictionOid;

    private String sharedInd;

    private Integer versionCtrlNbr;

    private String autoResendInd;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;
    
    private Long  exportReceivingFacilityUid;
    
    private String receiving_system_nm;

    private Long nbsInterfaceUid;
    
    private String nndInd;
    
    private String labReportEnableInd;
    
    private String vaccineEnableInd;

    public String getVaccineEnableInd() {
		return vaccineEnableInd;
	}

	public void setVaccineEnableInd(String vaccineEnableInd) {
		this.vaccineEnableInd = vaccineEnableInd;
	}

	public String getLabReportEnableInd() {
		return labReportEnableInd;
	}

	public void setLabReportEnableInd(String labReportEnableInd) {
		this.labReportEnableInd = labReportEnableInd;
	}

	public String getNndInd() {
		return nndInd;
	}

	public void setNndInd(String nndInd) {
		this.nndInd = nndInd;
	}

	/**
     * gets the Notification UID.
     * @return : Long value of Notification
     */
    public Long getNotificationUid()
    {
        return notificationUid;
    }

    /**
     * sets the Notification UID.
     * @param aNotificationUid : Long value
     */

    public void setNotificationUid(Long aNotificationUid)
    {
        notificationUid = aNotificationUid;
        setItDirty(true);
    }

    /**
     * gets the activityDurationAmt
     * @return : String value of activityDurationAmt
     */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }

    /**
     * sets the activityDurationAmt
     * @param aActivityDurationAmt : String value of activityDurationAmt
     */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

    /**
     * gets the activityDurationUnitCd
     * @return : String value of activityDurationUnitCd
     */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }

    /**
     * sets the aActivityDurationUnitCd
     * @param aActivityDurationUnitCd : String value of aActivityDurationUnitCd
     */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets the activityFromTime
     * @return : TimeStamp value of activityFromTime
     */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }

    /**
     * sets the activityFromTime
     * @param aActivityFromTime : TimeStamp activityFromTime
     */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

    /**
     * sets the activityFromTime( convenient method for struts)
     * @param strTime : String value
     */
    public void setActivityFromTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets activityToTime.
     * @return : Timestamp value of activityToTime
     */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }

    /**
     * sets the activityToTime
     * @param aActivityToTime : Timestamp value of activityToTime
     */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

    /**
     * sets the strTime(convenient method for struts).
     * @param strTime : String value
     */
   public void setActivityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets the addReasonCd
    * @return : String value of addReasonCd
    */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * sets AddReasonCd
     * @param aAddReasonCd : String value of aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets AddTime
     * @return : Timestamp value of AddTime
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * sets the AddTime
     * @param aAddTime : Timestamp value of AddTime
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets the AddTime(convenient method for struts)
     * @param strTime : String value of strTime
     */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets the AddUserId
    * @return : Long value of AddUserId
    */
   public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * sets the AddUserId
     * @param aAddUserId : Long value of addUserId
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets the CaseClassCd
     * @return : String value of CaseClassCd
     */
    public String getCaseClassCd()
    {
        return caseClassCd;
    }

    /**
     * sets the CaseClassCd
     * @param aCaseClassCd : String value
     */
    public void setCaseClassCd(String aCaseClassCd)
    {
        caseClassCd = aCaseClassCd;
        setItDirty(true);
    }

    /**
     * gets CaseConditionCd
     * @return : String CaseConditionCd
     */
    public String getCaseConditionCd()
    {
        return caseConditionCd;
    }

    /**
     * sets CaseConditionCd
     * @param aCaseConditionCd : String value
     */
    public void setCaseConditionCd(String aCaseConditionCd)
    {
        caseConditionCd = aCaseConditionCd;
        setItDirty(true);
    }

    /**
     * gets CD
     * @return : String value
     */
    public String getCd()
    {
        return cd;
    }

    /**
     * sets CD
     * @param aCd : String value
     */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * gets CdDescTxt
     * @return : String value
     */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
     * sets the CdDescTxt
     * @param aCdDescTxt : String value
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * gets ConfidentialityCd
     * @return : String value
     */
    public String getConfidentialityCd()
    {
        return confidentialityCd;
    }

    /**
     * sets confidentialityCd
     * @param aConfidentialityCd : String value
     */
    public void setConfidentialityCd(String aConfidentialityCd)
    {
        confidentialityCd = aConfidentialityCd;
        setItDirty(true);
    }

    /**
     * gets ConfidentialityDescTxt
     * @return : String value
     */
    public String getConfidentialityDescTxt()
    {
        return confidentialityDescTxt;
    }

    /**
     * sets ConfidentialityDescTxt
     * @param aConfidentialityDescTxt : String value
     */
    public void setConfidentialityDescTxt(String aConfidentialityDescTxt)
    {
        confidentialityDescTxt = aConfidentialityDescTxt;
        setItDirty(true);
    }

    /**
     * gets ConfirmationMethodCd
     * @return : String value
     */
    public String getConfirmationMethodCd()
    {
        return confirmationMethodCd;
    }

    /**
     * sets ConfirmationMethodCd
     * @param aConfirmationMethodCd : String value
     */
    public void setConfirmationMethodCd(String aConfirmationMethodCd)
    {
        confirmationMethodCd = aConfirmationMethodCd;
        setItDirty(true);
    }

    /**
     * gets EffectiveDurationAmt
     * @return : String value
     */
    public String getEffectiveDurationAmt()
    {
        return effectiveDurationAmt;
    }

    /**
     * sets EffectiveDurationAmt
     * @param aEffectiveDurationAmt : String value
     */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

    /**
     * gets EffectiveDurationUnitCd
     * @return : String value
     */
    public String getEffectiveDurationUnitCd()
    {
        return effectiveDurationUnitCd;
    }

    /**
     * sets effectiveDurationUnitCd
     * @param aEffectiveDurationUnitCd : String value
     */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets EffectiveFromTime
     * @return : Timestamp value
     */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
     * sets EffectiveFromTime
     * @param aEffectiveFromTime : Timestamp value
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
     * gets EffectiveFromTime(convenient method for struts)
     * @param strTime : String value
     */
   public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets EffectiveToTime
    * @return : TimeStamp value
    */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
     * sets EffectiveToTime
     * @param aEffectiveToTime : Timestamp value
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
     * sets EffectiveToTime
     * @param strTime : String value
     */
   public void setEffectiveToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets JurisdictionCd
    * @return : String value
    */
    public String getJurisdictionCd()
    {
        return jurisdictionCd;
    }

    /**
     * sets JurisdictionCd
     * @param aJurisdictionCd : String value
     */
    public void setJurisdictionCd(String aJurisdictionCd)
    {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * gets LastChgReasonCd
     * @return : String value
     */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * sets LastChgReasonCd
     * @param aLastChgReasonCd : String value
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * getsb LastChgTime
     * @return : Timestamp value
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * sets LastChgTime
     * @param aLastChgTime : Timestamp value
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets LastChgTime(convenient method for struts)
     * @param strTime : String value
     */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets LastChgUserId
    * @return : Long value
    */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * sets LastChgUserId
     * @param aLastChgUserId : Long value
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * gets LocalId
     * @return : String value
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * sets LocalId
     * @param aLocalId : String value
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * gets MessageTxt
     * @return : String value
     */
    public String getMessageTxt()
    {
        return messageTxt;
    }

    /**
     * sets MessageTxt : String value
     * @param aMessageTxt : String value
     */
    public void setMessageTxt(String aMessageTxt)
    {
        messageTxt = aMessageTxt;
        setItDirty(true);
    }

    /**
     * gets MethodCd
     * @return : String value
     */
    public String getMethodCd()
    {
        return methodCd;
    }

    /**
     * sets MethodCd
     * @param aMethodCd : String value
     */
    public void setMethodCd(String aMethodCd)
    {
        methodCd = aMethodCd;
        setItDirty(true);
    }

    /**
     * gets MethodDescTxt
     * @return : String value
     */
    public String getMethodDescTxt()
    {
        return methodDescTxt;
    }

    /**
     * sets MethodDescTxt
     * @param aMethodDescTxt : String value
     */
     public void setMethodDescTxt(String aMethodDescTxt)
    {
        methodDescTxt = aMethodDescTxt;
        setItDirty(true);
    }

    /**
     * gets MmwrWeek
     * @return : String value
     */
    public String getMmwrWeek()
    {
        return mmwrWeek;
    }

    /**
     * sets MmwrWeek
     * @param aMmwrWeek : String value
     */
     public void setMmwrWeek(String aMmwrWeek)
    {
        mmwrWeek = aMmwrWeek;
        setItDirty(true);
    }

    /**
     * gets MmwrYear
     * @return : String value
     */
    public String getMmwrYear()
    {
        return mmwrYear;
    }

    /**
     * sets MmwrYear
     * @param aMmwrYear : String value
     */
    public void setMmwrYear(String aMmwrYear)
    {
        mmwrYear = aMmwrYear;
        setItDirty(true);
    }

    /**
     * gets NedssVersionNbr
     * @return : String value
     */
    public String getNedssVersionNbr()
    {
        return nedssVersionNbr;
    }

    /**
     * sets NedssVersionNbr
     * @param aNedssVersionNbr : String value
     */
     public void setNedssVersionNbr(String aNedssVersionNbr)
    {
        nedssVersionNbr = aNedssVersionNbr;
        setItDirty(true);
    }

    /**
     * gets ProgAreaCd
     * @return : String value
     */
    public String getProgAreaCd()
    {
        return progAreaCd;
    }

    /**
     * sets ProgAreaCd
     * @param aProgAreaCd : String value
     */
    public void setProgAreaCd(String aProgAreaCd)
    {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * gets ReasonCd
     * @return : String value
     */
    public String getReasonCd()
    {
        return reasonCd;
    }

    /**
     * sets ReasonCd
     * @param aReasonCd : String value
     */
    public void setReasonCd(String aReasonCd)
    {
        reasonCd = aReasonCd;
        setItDirty(true);
    }

    /**
     * gets ReasonDescTxt
     * @return : String value
     */
    public String getReasonDescTxt()
    {
        return reasonDescTxt;
    }

    /**
     * sets ReasonDescTxt
     * @param aReasonDescTxt : String value
     */
    public void setReasonDescTxt(String aReasonDescTxt)
    {
        reasonDescTxt = aReasonDescTxt;
        setItDirty(true);
    }

    /**
     * gets RecordCount
     * @return : String value
     */
    public String getRecordCount()
    {
        return recordCount;
    }

    /**
     * sets RecordCount
     * @param aRecordCount : String value
     */
    public void setRecordCount(String aRecordCount)
    {
        recordCount = aRecordCount;
        setItDirty(true);
    }

    /**
     * gets RecordStatusCd
     * @return : String value
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * sets RecordStatusCd
     * @param aRecordStatusCd : String value
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * get RecordStatusTime
     * @return : Timestamp value
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * sets RecordStatusTime
     * @param aRecordStatusTime : Timestamp value
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * sets RecordStatusTime(convenient method for struts).
     * @param strTime : String value
     */
    public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets RepeatNbr
    * @return : Integer value
    */
   public Integer getRepeatNbr()
    {
        return repeatNbr;
    }

    /**
     * sets RepeatNbr
     * @param aRepeatNbr : Integer value
     */
    public void setRepeatNbr(Integer aRepeatNbr)
    {
        repeatNbr = aRepeatNbr;
        setItDirty(true);
    }

    /**
     * gets RptSentTime
     * @return : Timestamp value
     */
    public Timestamp getRptSentTime()
    {
        return rptSentTime;
    }

    /**
     * sets RptSentTime
     * @param aRptSentTime : Timestamp value
     */
    public void setRptSentTime(Timestamp aRptSentTime)
    {
        rptSentTime = aRptSentTime;
        setItDirty(true);
    }

    /**
     * sets RptSentTime( convenient method for struts)
     * @param strTime : String value
     */
    public void setRptSentTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRptSentTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets RptSourceCd
    * @return : String value
    */
   public String getRptSourceCd()
    {
        return rptSourceCd;
    }

    /**
     * sets RptSourceCd
     * @param aRptSourceCd : String value
     */
    public void setRptSourceCd(String aRptSourceCd)
    {
        rptSourceCd = aRptSourceCd;
        setItDirty(true);
    }

    /**
     * gets RptSourceTypeCd
     * @return : String value
     */
    public String getRptSourceTypeCd()
    {
        return rptSourceTypeCd;
    }

    /**
     * sets RptSourceTypeCd
     * @param aRptSourceTypeCd : String value
     */

    public void setRptSourceTypeCd(String aRptSourceTypeCd)
    {
        rptSourceTypeCd = aRptSourceTypeCd;
        setItDirty(true);
    }

    /**
     * gets StatusCd
     * @return : String value
     */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * sets StatusCd
     * @param aStatusCd : String value
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * gets StatusTime
     * @return : Timestamp value
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * sets StatusTime
     * @param aStatusTime : Timestamp value
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * sets StatusTime( convenient method for struts)
     * @param strTime : String value
     */

   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets Txt
    * @return : String value
    */
    public String getTxt()
    {
        return txt;
    }

    /**
     * sets Txt
     * @param aTxt : String value
     */
    public void setTxt(String aTxt)
    {
        txt = aTxt;
        setItDirty(true);
    }

    /**
     * gets UserAffiliationTxt
     * @return : String value
     */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * sets the UserAffiliationTxt
     * @param aUserAffiliationTxt : String value
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets the ProgramJurisdictionOid
     * @return : Long value
     */
    public Long getProgramJurisdictionOid()
    {
        return programJurisdictionOid;
    }

    /**
     * sets ProgramJurisdictionOid
     * @param aProgramJurisdictionOid : Long value
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * gets the SharedInd
     * @return : String value
     */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
     * sets the SharedInd
     * @param aSharedInd : String value
     */

    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * gets the VersionCtrlNbr
     * @return : Integer value
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * sets the VersionCtrlNbr
     * @param aVersionCtrlNbr : Integer value
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }


    /**
     * gets the AutoResendInd
     * @return autoResendInd : String Value
     */
    public String getAutoResendInd()
    {
      return this.autoResendInd;

    }


    /**
     * sets AutoResendInd
     * @param autoResendInd : String Value
     */
    public void setAutoResendInd(String autoResendInd)
    {
      this.autoResendInd = autoResendInd;
      setItDirty(true);

    }




    /**
     * compares two object for equality
     * @param objectname1 : first object to be comapared
     * @param objectname2 : second object to be compared
     * @param voClass :NotificationDT's class
     * @return : boolean value
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( NotificationDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }

   /**
    * sets itDirty
    * @param itDirty : boolean value
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }

   /**
    * gets itDirty
    * @return : boolean value
    */
   public boolean isItDirty() {
     return itDirty;
   }


   /**
    * sets itNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }

   /**
    * gets itNew
    * @return : boolean value
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * gets itDelete
    * @return : boolean value
    */
   public boolean isItDelete() {
     return itDelete;
   }

   /**
    * sets the setItDelete
    * @param itDelete : boolean value
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

   /**
    * gets Superclass
    * @return : String value
    */
   public String getSuperclass()
  {
    return NEDSSConstants.CLASSTYPE_ACT;
  }

  /**
   * gets notification UID
   * @return : Long value
   */
  public Long getUid()
  {
    return notificationUid;
  }





public String getReceiving_system_nm() {
	return receiving_system_nm;
}

public void setReceiving_system_nm(String receiving_system_nm) {
	this.receiving_system_nm = receiving_system_nm;
}

public Long getExportReceivingFacilityUid() {
	return exportReceivingFacilityUid;
}

public void setExportReceivingFacilityUid(Long exportReceivingFacilityUid) {
	this.exportReceivingFacilityUid = exportReceivingFacilityUid;
}

public Long getNbsInterfaceUid() {
	return nbsInterfaceUid;
}

public void setNbsInterfaceUid(Long nbsInterfaceUid){
	this.nbsInterfaceUid = nbsInterfaceUid;
}


}

