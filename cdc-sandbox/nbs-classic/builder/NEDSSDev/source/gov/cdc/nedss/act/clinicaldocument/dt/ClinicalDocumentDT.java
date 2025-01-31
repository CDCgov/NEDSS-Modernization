

package gov.cdc.nedss.act.clinicaldocument.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;


public class ClinicalDocumentDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;
    private Long clinicalDocumentUid;

    private String activityDurationAmt;

    private String activityDurationUnitCd;

    private Timestamp activityFromTime;

    private Timestamp activityToTime;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;

    private String cd;

    private String cdDescTxt;

    private String confidentialityCd;

    private String confidentialityDescTxt;

    private Timestamp copyFromTime;

    private Timestamp copyToTime;

    private String effectiveDurationAmt;

    private String effectiveDurationUnitCd;

    private Timestamp effectiveFromTime;

    private Timestamp effectiveToTime;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String localId;

    private String practiceSettingCd;

    private String practiceSettingDescTxt;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private String statusCd;

    private Timestamp statusTime;

    private String txt;

    private String userAffiliationTxt;

    private Integer versionNbr;

    private Long programJurisdictionOid;

    private String sharedInd;

    private Integer versionCtrlNbr;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;


    /**
     * getter method for ClinicalDocumentUid
     * @return Long value
     */
    public Long getClinicalDocumentUid()
    {
        return clinicalDocumentUid;
    }

    /**
     * setter method for clinicalDocumentUid
     * @param aClinicalDocumentUid Long value
     */
    public void setClinicalDocumentUid(Long aClinicalDocumentUid)
    {
        clinicalDocumentUid = aClinicalDocumentUid;
        setItDirty(true);
    }

    /**
     * getter method for ActivityDurationAmt
     * @return String value
     */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }

    /**
     * setter method for ActivityDurationAmt
     * @param aActivityDurationAmt String
     */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

    /**
     * getter method for ActivityDurationUnitCd
     * @return String value
     */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }

    /**
     * setter method for ActivityDurationUnitCd
     * @param aActivityDurationUnitCd String
     */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * getter method for ActivityFromTime
     * @return Timestamp value
     */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }

    /**
     * setter method for ActivityFromTime
     * @param aActivityFromTime Timestamp
     */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for ActivityFromTime
     * @param strTime String
     */
    public void setActivityFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for ActivityFromTime
     * @return Timestamp value
     */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }

    /**
     * setter method for ActivityToTime
     * @param aActivityToTime Timestamp
     */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for ActivityToTime
     * @param strTime String
     */
    public void setActivityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for AddReasonCd
     * @return String value
     */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * setter method for AddReasonCd
     * @param aAddReasonCd String
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * getter method for AddTime
     * @return Timestamp value
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * setter method for AddTime
     * @param aStatusTime Timestamp
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for AddTime
     * @param strTime String
     */
    public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for AddUserId
     * @return Long value
     */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * setter method for AddUserId
     * @param aAddUserId Timestamp
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * getter method for Cd
     * @return String value
     */
    public String getCd()
    {
        return cd;
    }

    /**
     * setter method for Cd
     * @param aCd String
     */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * getter method for CdDescTxt
     * @return String value
     */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
     * setter method for CdDescTxt
     * @param aCdDescTxt String
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for ConfidentialityCd
     * @return String value
     */
    public String getConfidentialityCd()
    {
        return confidentialityCd;
    }

    /**
     * setter method for ConfidentialityCd
     * @param aConfidentialityCd String
     */
    public void setConfidentialityCd(String aConfidentialityCd)
    {
        confidentialityCd = aConfidentialityCd;
        setItDirty(true);
    }

    /**
     * getter method for ConfidentialityDescTxt
     * @return String value
     */
    public String getConfidentialityDescTxt()
    {
        return confidentialityDescTxt;
    }

    /**
     * setter method for ConfidentialityDescTxt
     * @param aConfidentialityDescTxt String
     */
    public void setConfidentialityDescTxt(String aConfidentialityDescTxt)
    {
        confidentialityDescTxt = aConfidentialityDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for CopyFromTime
     * @return Timestamp value
     */
    public Timestamp getCopyFromTime()
    {
        return copyFromTime;
    }

    /**
     * setter method for CopyFromTime
     * @param aCopyFromTime Timestamp
     */
    public void setCopyFromTime(Timestamp aCopyFromTime)
    {
        copyFromTime = aCopyFromTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for CopyFromTime
     * @param strTime String
     */
    public void setCopyFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setCopyFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for CopyToTime
     * @return Timestamp value
     */
    public Timestamp getCopyToTime()
    {
        return copyToTime;
    }

    /**
     * setter method for CopyToTime
     * @param aCopyToTime Timestamp
     */
    public void setCopyToTime(Timestamp aCopyToTime)
    {
        copyToTime = aCopyToTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for CopyToTime
     * @param strTime String
     */
    public void setCopyToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setCopyToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for EffectiveDurationAmt
     * @return String value
     */
    public String getEffectiveDurationAmt()
    {
        return effectiveDurationAmt;
    }

    /**
     * setter method for EffectiveDurationAmt
     * @param aEffectiveDurationAmt String
     */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

    /**
     * getter method for EffectiveDurationUnitCd
     * @return String value
     */
    public String getEffectiveDurationUnitCd()
    {
        return effectiveDurationUnitCd;
    }

    /**
     * setter method for EffectiveDurationUnitCd
     * @param aEffectiveDurationUnitCd String
     */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

    /**
     * getter method for EffectiveFromTime
     * @return Timestamp value
     */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
     * setter method for EffectiveFromTime
     * @param aEffectiveFromTime Timestamp
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

   /**
    * convenient struts setter method for EffectiveFromTime
     * @param strTime String
     */
    public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for EffectiveToTime
     * @return Timestamp value
     */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
     * setter method for EffectiveToTime
     * @param aEffectiveToTime Timestamp
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for EffectiveToTime
     * @param strTime String
     */
    public void setEffectiveToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for LastChgReasonCd
     * @return String value
     */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * setter method for LastChgReasonCd
     * @param aLastChgReasonCd String
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * getter method for LastChgTime
     * @return Timestamp value
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * setter method for LastChgTime
     * @param aLastChgTime Timestamp
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

   /**
     * convenient struts setter method for LastChgTime
     * @param strTime String
     */
    public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getter method for LastChgUserId
     * @return Long value
     */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * setter method for LastChgUserId
     * @param aLastChgUserId Timestamp
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * getter method for LocalId
     * @return String value
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * setter method for LocalId
     * @param aLocalId String
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * getter method for PracticeSettingCd
     * @return String value
     */
    public String getPracticeSettingCd()
    {
        return practiceSettingCd;
    }

    /**
     * setter method for PracticeSettingCd
     * @param aPracticeSettingCd String
     */
    public void setPracticeSettingCd(String aPracticeSettingCd)
    {
        practiceSettingCd = aPracticeSettingCd;
        setItDirty(true);
    }

    /**
     * getter method for PracticeSettingDescTxt
     * @return String value
     */
    public String getPracticeSettingDescTxt()
    {
        return practiceSettingDescTxt;
    }

    /**
     * setter method for PracticeSettingDescTxt
     * @param aPracticeSettingDescTxt String
     */
    public void setPracticeSettingDescTxt(String aPracticeSettingDescTxt)
    {
        practiceSettingDescTxt = aPracticeSettingDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for RecordStatusCd
     * @return String value
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * setter method for RecordStatusCd
     * @param aRecordStatusCd String
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * getter method for RecordStatusTime
     * @return Timestamp value
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * setter method for RecordStatusTime
     * @param aRecordStatusTime Timestamp
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

   /**
     * conevnient struts setter method for RecordStatusTime
     * @param strTime String
     */
    public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
     * getter method for StatusCd
     * @return String value
     */
     public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * setter method for StatusCd
     * @param aStatusCd String
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * getter method for StatusTime
     * @return Timestamp value
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * setter method for StatusTime
     * @param aStatusTime Timestamp
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }


    /**
     * convenient struts setter method for StatusTime
     * @param strTime String
     */
    public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for Txt
    * @return String
    */
   public String getTxt()
    {
        return txt;
    }

    /**
     * setter method for Txt
     * @param aTxt String
     */
    public void setTxt(String aTxt)
    {
        txt = aTxt;
        setItDirty(true);
    }

    /**
     * getter method for UserAffiliationTxt
     * @return String
     */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * setter method for UserAffiliationTxt
     * @param aUserAffiliationTxt String
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     *getter method for versionNbr
     * @return Integer
     */
    public Integer getVersionNbr()
    {
        return versionNbr;
    }

    /**
     * setter method for VersionNbr
     * @param aVersionNbr Integer
     */
    public void setVersionNbr(Integer aVersionNbr)
    {
        versionNbr = aVersionNbr;
        setItDirty(true);
    }

    /**
     * getter method for ProgramJurisdictionOid
     * @return Long value
     */
    public Long getProgramJurisdictionOid()
    {
        return programJurisdictionOid;
    }

    /**
     * setter method for ProgramJurisdictionOid
     * @param aProgramJurisdictionOid Long
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * getter method for SharedInd
     * @return String
     */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
     * setter method for SharedInd
     * @param aSharedInd String
     */
    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * getter method for VersionCtrlNbr
     * @return Integer
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * setter method for VersionCtrlNbr
     * @param aVersionCtrlNbr integer
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * getter method ProgAreaCd
     * @return String
     */
    public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * setter method for ProgAreaCd
    * @param aProgAreaCd String
    */
   public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
    * getter method for JurisdictionCd
    * @return String
    */
   public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * setter method for JurisdictionCd
    * @param aJurisdictionCd String
    */
   public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }


   /**
     * boolean method for isEqual
     * @param objectname1   Object
     * @param objectname2   Object
     * @param voClass  Class(ClinicalDocumentDT)
     * @return boolean
     */    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ClinicalDocumentDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


     /**
      * setter method for itDirty
      * @param itDirty boolean
      */
     public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   /**
    * getter method for itDirty
    * @return boolean
    */
   public boolean isItDirty() {
     return itDirty;
   }


   /**
    * setter method for itNew
    * @param itNew boolean
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


   /**
    * getter method for itNew
    * @return boolean
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * getter method for itDelete
    * @return boolean
    */
   public boolean isItDelete() {
     return itDelete;
   }


   /**
    * setter method for itDelete
    * @param itDelete boolean
    */
   public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

   /**
    * gets the super class
    * @return String value of Super class
    */
   public String getSuperclass()
  {
    return NEDSSConstants.CLASSTYPE_ACT;
  }

  /**
   * getter method for Uid(ClinicalDocumentUid)
   * @return Long
   */
  public Long getUid()
  {
    return clinicalDocumentUid;
  }


}
