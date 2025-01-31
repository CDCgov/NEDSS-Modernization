/**
 *
 * <p>Title: InterventionHistDT.java</p>
 * <p>Description:This is a class which sets/gets(retrieves)
 *                all the fields to the intervention histroy database table </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Computer Sciences Corporation</p>
 * @author NEDSS team
 * @version 1.0
 */

package gov.cdc.nedss.act.intervention.dt;


import java.sql.Timestamp;

import  gov.cdc.nedss.util.*;

public class InterventionHistDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;

    private Long interventionUid;

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

    private String classCd;

    private String confidentialityCd;

    private String confidentialityDescTxt;

    private String effectiveDurationAmt;

    private String effectiveDurationUnitCd;

    private Timestamp effectiveFromTime;

    private Timestamp effectiveToTime;

    private String jurisdictionCd;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String localId;

    private String methodCd;

    private String methodDescTxt;

    private String priorityCd;

    private String priorityDescTxt;

    private String progAreaCd;

    private String qtyAmt;

    private String qtyUnitCd;

    private String reasonCd;

    private String reasonDescTxt;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private Integer repeatNbr;

    private String statusCd;

    private Timestamp statusTime;

    private String targetSiteCd;

    private String targetSiteDescTxt;

    private String txt;

    private Long userAffiliationTxt;

    private Long programJurisdictionOid;

    private String sharedInd;

    private String electronicInd;
    
    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    /**
     * getter method for intervention Uid
     * @return Long
     */
    public Long getInterventionUid()
    {
        return interventionUid;
    }
    /**
     * setter method for Intervention Uid
     * @param aInterventionUid   Long
     */
    public void setInterventionUid(Long aInterventionUid)
    {
        interventionUid = aInterventionUid;
        setItDirty(true);
    }

    /**
     * getter method for versionControlNumber
     * @return  Integer
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * setter method for versionControlNumber
     * @param aVersionCtrlNbr   Integer
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * getter method for ActivityDurationAmount
     * @return String
     */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }
    /**
     * setter method for ActivityDurationAmount
     * @param aActivityDurationAmt   String
     */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

    /**
     * getter method for ActivityDurationAmountUnitCd
     * @return String
     */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }
    /**
     * setter method for ActivityDurationAmountUnitCd
     * @param aActivityDurationUnitCd   String
     */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * getter method for ActivityFromTime
     * @return Timestamp
     */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }
    /**
     * setter method for ActivityFromTime
     * @param aActivityFromTime   Timestamp
     */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

   /**
    * convenient setter method for struts(ActivitFromTime)
    * @param strTime   String
    */
   public void setActivityFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for ActivityToTime
    * @return Timestamp
    */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }
    /**
     * setter method fro ActivityToTime
     * @param aActivityToTime   Timestamp
     */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

  /**
   * convenient setter method for struts(ActivityFromTime)
   * @param strTime   String
   */
   public void setActivityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for AddReasonCd
    * @return String
    */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }
    /**
     * setter method for AddReasonCd
     * @param aAddReasonCd   String
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * getter method for AddTime
     * @return Timestamp
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * setter method for AddTime
     * @param aAddTime  Timestamp
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * convenient strut setter method for addTime
     * @param strTime   String
     */
    public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for AddUserId
    * @return Long
    */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * setter method for AddUserId
     * @param aAddUserId   Long
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * getter method for Cd
     * @return String
     */
    public String getCd()
    {
        return cd;
    }
    /**
     *setter method for Cd
     * @param aCd   String
     */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * getter method for CdDescTxt
     * @return String
     */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
     * setter method for CdDescTxt
     * @param aCdDescTxt   String
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for CdSystemCd
     * @return String
     */
    public String getCdSystemCd()
    {
        return cdSystemCd;
    }

    /**
     * setter method for CdSystemCd
     * @param aCdSystemCd   String
     */
    public void setCdSystemCd(String aCdSystemCd)
    {
        cdSystemCd = aCdSystemCd;
        setItDirty(true);
    }

    /**
     * getter method for CdSystemDescTxt
     * @return String
     */
    public String getCdSystemDescTxt()
    {
        return cdSystemDescTxt;
    }

    /**
     * setter method for CdSystemDescTxt
     * @param aCdSystemDescTxt   String
     */
    public void setCdSystemDescTxt(String aCdSystemDescTxt)
    {
        cdSystemDescTxt = aCdSystemDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for ClassCd
     * @return   String
     */
    public String getClassCd()
    {
        return classCd;
    }

    /**
     * setter method for ClassCd
     * @param aClassCd   String
     */
    public void setClassCd(String aClassCd)
    {
        classCd = aClassCd;
        setItDirty(true);
    }

    /**
     * getter method for ConfidentialityCd
     * @return String
     */
    public String getConfidentialityCd()
    {
        return confidentialityCd;
    }

    /**
     * setter method for ConfidentialityCd
     * @param aConfidentialityCd   String
     */
    public void setConfidentialityCd(String aConfidentialityCd)
    {
        confidentialityCd = aConfidentialityCd;
        setItDirty(true);
    }

    /**
     * getter method for ConfidentialityDescTxt
     * @return String
     */
    public String getConfidentialityDescTxt()
    {
        return confidentialityDescTxt;
    }

    /**
     * setter method for ConfidentialityDescTxt
     * @param aConfidentialityDescTxt   String
     */
    public void setConfidentialityDescTxt(String aConfidentialityDescTxt)
    {
        confidentialityDescTxt = aConfidentialityDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for EffectiveDurationAmt
     * @return String
     */
    public String getEffectiveDurationAmt()
    {
        return effectiveDurationAmt;
    }

    /**
     * setter method for EffectiveDurationAmt
     * @param aEffectiveDurationAmt   String
     */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

    /**
     * getter method for EffectiveDurationUnitCd
     * @return String
     */
    public String getEffectiveDurationUnitCd()
    {
        return effectiveDurationUnitCd;
    }

    /**
     * setter method for EffectiveDurationUnitCd
     * @param aEffectiveDurationUnitCd   String
     */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

    /**
     * getter method for EffectiveFromTime
     * @return Timestamp
     */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
     * setter method for EffectiveFromTime
     * @param aEffectiveFromTime   Timestamp
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
     * convenient strut method for EffectiveFromTime
     * @param strTime   String
     */
   public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for EffectiveToTime
    * @return Timestamp
    */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
     * setter method for EffectiveToTime
     * @param aEffectiveToTime   Timestamp
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
     * convenient struts setter method for EffectiveToTime
     * @param strTime   String
     */
    public void setEffectiveToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }


   /**
    * getter method for JurisdictionCd
    * @return String
    */
   public String getJurisdictionCd()
    {
        return jurisdictionCd;
    }

    /**
     * setter method for JurisdictionCd
     * @param aJurisdictionCd   String
     */
    public void setJurisdictionCd(String aJurisdictionCd)
    {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * getter method for LastChgReasonCd
     * @return String
     */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * setter method for LastChgReasonCd
     * @param aLastChgReasonCd   String
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * getter method for LastChgTime
     * @return Timestamp
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * setter method for LastChgTime
     * @param aLastChgTime   Timestamp
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * convenient struts setter method for LastChgTime
     * @param strTime   String
     */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }


   /**
    * getter method for LastChgUserId
    * @return Long
    */
   public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * setter method for LastChgUserId
     * @param aLastChgUserId   Long
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * getter method for LocalId
     * @return String
     */
    public String getLocalId()
    {
        return localId;
    }

    /**
     * setter method for LocalId
     * @param aLocalId   Long
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * getter method for MethodCd
     * @return String
     */
    public String getMethodCd()
    {
        return methodCd;
    }

    /**
     * setter method for MethodCd
     * @param aMethodCd   String
     */
    public void setMethodCd(String aMethodCd)
    {
        methodCd = aMethodCd;
        setItDirty(true);
    }

    /**
     * getter method for MethodDescTxt
     * @return String
     */
    public String getMethodDescTxt()
    {
        return methodDescTxt;
    }

    /**
     * setter method for MethodDescTxt
     * @param aMethodDescTxt   String
     */
    public void setMethodDescTxt(String aMethodDescTxt)
    {
        methodDescTxt = aMethodDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for PriorityCd
     * @return String
     */
    public String getPriorityCd()
    {
        return priorityCd;
    }

    /**
     * setter method for PriorityCd
     * @param aPriorityCd   String
     */
    public void setPriorityCd(String aPriorityCd)
    {
        priorityCd = aPriorityCd;
        setItDirty(true);
    }


    /**
     * getter method for PriorityDescTxt
     * @return String
     */
    public String getPriorityDescTxt()
    {
        return priorityDescTxt;
    }

    /**
     * setter method for PriorityDescTxt
     * @param aPriorityDescTxt   String
     */
    public void setPriorityDescTxt(String aPriorityDescTxt)
    {
        priorityDescTxt = aPriorityDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for ProgAreaCd
     * @return   String
     */
    public String getProgAreaCd()
    {
        return progAreaCd;
    }

    /**
     * setter method fro PriorityDescTxt
     * @param aProgAreaCd   String
     */
    public void setProgAreaCd(String aProgAreaCd)
    {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * getter method for QtyAmt
     * @return   String
     */
    public String getQtyAmt()
    {
        return qtyAmt;
    }

    /**
     * setter method for QtyAmt
     * @param aQtyAmt   String
     */
    public void setQtyAmt(String aQtyAmt)
    {
        qtyAmt = aQtyAmt;
        setItDirty(true);
    }

    /**
     * getter method for QtyUnitCd
     * @return   String
     */
    public String getQtyUnitCd()
    {
        return qtyUnitCd;
    }

    /**
     * setter method for QtyUnitCd
     * @param aQtyUnitCd   String
     */
    public void setQtyUnitCd(String aQtyUnitCd)
    {
        qtyUnitCd = aQtyUnitCd;
        setItDirty(true);
    }

    /**
     * getter method for ReasonCd
     * @return   String
     */
    public String getReasonCd()
    {
        return reasonCd;
    }

    /**
     * setter method for ReasonCd
     * @param aReasonCd   String
     */
    public void setReasonCd(String aReasonCd)
    {
        reasonCd = aReasonCd;
        setItDirty(true);
    }


    /**
     * getter method for ReasonDescTxt
     * @return   String
     */
    public String getReasonDescTxt()
    {
        return reasonDescTxt;
    }

    /**
     * setter method for ReasonDescTxt
     * @param aReasonDescTxt   String
     */
    public void setReasonDescTxt(String aReasonDescTxt)
    {
        reasonDescTxt = aReasonDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for RecordStatusCd
     * @return   String
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * setter method for RecordStatusCd
     * @param aRecordStatusCd   String
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * getter method for RecordStatusTime
     * @return   Timestamp
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * setter method for RecordStatusTime
     * @param aRecordStatusTime   Timestamp
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * convenient struts setter method for RecordStatusTime
     * @param strTime   String
     */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for RepeatNbr
    * @return   Integer
    */
   public Integer getRepeatNbr()
    {
        return repeatNbr;
    }

    /**
     * setter method for RepeatNbr
     * @param aRepeatNbr   Integer
     */
    public void setRepeatNbr(Integer aRepeatNbr)
    {
        repeatNbr = aRepeatNbr;
        setItDirty(true);
    }

    /**
     * getter method for StatusCd
     * @return   String
     */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * setter method for StatusCd
     * @param aStatusCd   String
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * getter method for StatusTime
     * @return   Timestamp
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * setter method for StatusTime
     * @param aStatusTime   Timestamp
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * convenient struts setter method for StatusTime
     * @param strTime   String
     */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getter method for TargetSiteCd
    * @return   String
    */
   public String getTargetSiteCd()
   {
       return targetSiteCd;
   }

    /**
     * setter method for TargetSiteCd
     * @param aTargetSiteCd   String
     */
    public void setTargetSiteCd(String aTargetSiteCd)
    {
        targetSiteCd = aTargetSiteCd;
        setItDirty(true);
    }

    /**
     * getter method for TargetSiteDescTxt
     * @return   String
     */
    public String getTargetSiteDescTxt()
    {
        return targetSiteDescTxt;
    }

    /**
     * setter method for TargetSiteDescTxt
     * @param aTargetSiteDescTxt   String
     */
    public void setTargetSiteDescTxt(String aTargetSiteDescTxt)
    {
        targetSiteDescTxt = aTargetSiteDescTxt;
        setItDirty(true);
    }

    /**
     * getter method for Txt
     * @return   String
     */
    public String getTxt()
    {
        return txt;
    }
    /**
     * setter method for Txt
     * @param aTxt   String
     */
    public void setTxt(String aTxt)
    {
        txt = aTxt;
        setItDirty(true);
    }

    /**
     * getter method for UserAffiliationTxt
     * @return   Long
     */
    public Long getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }
    /**
     * setter method for UserAffiliationTxt
     * @param aUserAffiliationTxt   Long
     */
    public void setUserAffiliationTxt(Long aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }
    /**
     * getter method for ProgramJurisdictionOid
     * @return   String
     */
    public Long getProgramJurisdictionOid()
    {
        return programJurisdictionOid;
    }
    /**
     * setter method for ProgramJurisdictionOid
     * @param aProgramJurisdictionOid   String
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
    {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * getter method for SharedInd
     * @return   String
     */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
     * setter method for SharedInd
     * @param aSharedInd   String
     */
    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    public String getElectronicInd() {
		return electronicInd;
	}
    
	public void setElectronicInd(String electronicInd) {
		this.electronicInd = electronicInd;
	}
	
	/**
     * boolean method for isEqual
     * @param objectname1   Object
     * @param objectname2   Object
     * @param voClass  Class
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( InterventionHistDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }

  /**
   * setter method for itDirty
   * @param itDirty   boolean
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
   * @param itNew   boolean
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
     * setter method for boolean itDelete
     * @param itDelete   boolean
     */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


}
