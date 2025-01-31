

package gov.cdc.nedss.act.intervention.dt;

import java.sql.Timestamp;

import  gov.cdc.nedss.util.*;
//import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.systemservice.util.*;

public class InterventionDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;


    private Long interventionUid;

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

    private String progAreaCd;

    private String priorityCd;

    private String priorityDescTxt;

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

    private String userAffiliationTxt;

    private Long programJurisdictionOid;

    private String sharedInd;

    private Integer versionCtrlNbr;

    private String materialCd;
    
    private Integer ageAtVacc;
    
    private String ageAtVaccUnitCd;
    
    private String vaccMfgrCd;
    
    private String materialLotNm;
    
    private Timestamp materialExpirationTime;
    
    private Integer vaccDoseNbr;
    
    private String vaccInfoSourceCd;
    
    private String electronicInd;
    
    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;


    /**
     * gets InterventionUid
     * @return : Long value
     */
    public Long getInterventionUid()
    {
        return interventionUid;
    }

    /**
     * sets InterventionUid
     * @param aInterventionUid : Long value
     */
    public void setInterventionUid(Long aInterventionUid)
    {
        interventionUid = aInterventionUid;
        setItDirty(true);
    }

    /**
     * gets ActivityDurationAmt
     * @return : String value
     */
    public String getActivityDurationAmt()
    {
        return activityDurationAmt;
    }

    /**
     * sets ActivityDurationAmt
     * @param aActivityDurationAmt : String value
     */
    public void setActivityDurationAmt(String aActivityDurationAmt)
    {
        activityDurationAmt = aActivityDurationAmt;
        setItDirty(true);
    }

    /**
     * gets ActivityDurationUnitCd
     * @return : String value
     */
    public String getActivityDurationUnitCd()
    {
        return activityDurationUnitCd;
    }

    /**
     * sets ActivityDurationUnitCd
     * @param aActivityDurationUnitCd : String value
     */
    public void setActivityDurationUnitCd(String aActivityDurationUnitCd)
    {
        activityDurationUnitCd = aActivityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets ActivityFromTime
     * @return : Timestamp value
     */
    public Timestamp getActivityFromTime()
    {
        return activityFromTime;
    }

    /**
     * sets ActivityFromTime
     * @param aActivityFromTime : Timestamp value
     */
    public void setActivityFromTime(Timestamp aActivityFromTime)
    {
        activityFromTime = aActivityFromTime;
        setItDirty(true);
    }

    /**
     * sets ActivityFromTime(convenient method for struts)
     * @param strTime : String value
     */
    public void setActivityFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets ActivityToTime
    * @return : Timestamp value
    */
    public Timestamp getActivityToTime()
    {
        return activityToTime;
    }

    /**
     * sets ActivityToTime
     * @param aActivityToTime : Timestamp value
     */
    public void setActivityToTime(Timestamp aActivityToTime)
    {
        activityToTime = aActivityToTime;
        setItDirty(true);
    }

    /**
     * sets ActivityToTime(convenient method for struts)
     * @param strTime : String value
     */
   public void setActivityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setActivityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets AddReasonCd
    * @return : String value
    */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * sets AddReasonCd
     * @param aAddReasonCd : String value
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets AddTime
     * @return : Timestamp value
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * sets AddTime
     * @param aAddTime : Timestamp value
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets AddTime(convenient method for struts)
     * @param strTime : String value
     */
    public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets AddUserId
    * @return : Long value
    */
   public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * sets AddUserId
     * @param aAddUserId : Long value
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets Cd
     * @return : String value
     */
    public String getCd()
    {
        return cd;
    }

    /**
     * set Cd
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
     * sets CdDescTxt
     * @param aCdDescTxt : String value
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * gets CdSystemCd
     * @return : String value
     */
    public String getCdSystemCd()
    {
        return cdSystemCd;
    }

    /**
     * sets CdSystemCd
     * @param aCdSystemCd : String value
     */
    public void setCdSystemCd(String aCdSystemCd)
    {
        cdSystemCd = aCdSystemCd;
        setItDirty(true);
    }

    /**
     * gets CdSystemDescTxt
     * @return : String value
     */
    public String getCdSystemDescTxt()
    {
        return cdSystemDescTxt;
    }

    /**
     * sets CdSystemDescTxt
     * @param aCdSystemDescTxt : String value
     */
    public void setCdSystemDescTxt(String aCdSystemDescTxt)
    {
        cdSystemDescTxt = aCdSystemDescTxt;
        setItDirty(true);
    }

    /**
     * gets ClassCd
     * @return : String value
     */
    public String getClassCd()
    {
        return classCd;
    }

    /**
     * sets ClassCd
     * @param aClassCd : String value
     */
    public void setClassCd(String aClassCd)
    {
        classCd = aClassCd;
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
     * sets ConfidentialityCd
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
     * sets EffectiveDurationUnitCd
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
     * sets EffectiveFromTime(convenient method for struts)
     * @param strTime : String value
     */
    public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets EffectiveToTime
    * @return : Timestamp value
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
     * sets EffectiveToTime( convenient method for struts).
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
     * gets LastChgTime
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
     * gets PriorityCd
     * @return : String value
     */
    public String getPriorityCd()
    {
        return priorityCd;
    }

    /**
     * sets PriorityCd
     * @param aPriorityCd : String value
     */
    public void setPriorityCd(String aPriorityCd)
    {
        priorityCd = aPriorityCd;
        setItDirty(true);
    }

    /**
     * gets PriorityDescTxt
     * @return : String value
     */
    public String getPriorityDescTxt()
    {
        return priorityDescTxt;
    }

    /**
     * sets PriorityDescTxt
     * @param aPriorityDescTxt : String value
     */
    public void setPriorityDescTxt(String aPriorityDescTxt)
    {
        priorityDescTxt = aPriorityDescTxt;
        setItDirty(true);
    }

    /**
     * gets QtyAmt
     * @return : String value
     */
    public String getQtyAmt()
    {
        return qtyAmt;
    }

    /**
     * sets QtyAmt
     * @param aQtyAmt : String value
     */
    public void setQtyAmt(String aQtyAmt)
    {
        qtyAmt = aQtyAmt;
        setItDirty(true);
    }

    /**
     * gets QtyUnitCd
     * @return : String value
     */
    public String getQtyUnitCd()
    {
        return qtyUnitCd;
    }

    /**
     * sets QtyUnitCd
     * @param aQtyUnitCd : String value
     */
    public void setQtyUnitCd(String aQtyUnitCd)
    {
        qtyUnitCd = aQtyUnitCd;
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
     * gets RecordStatusTime
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
   * sets RecordStatusTime( convenient method for struts)
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
     * sets StatusTime( convenient method for struts).
     * @param strTime : Timestamp
     */
    public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets TargetSiteCd
    * @return : String value
    */
   public String getTargetSiteCd()
    {
        return targetSiteCd;
    }

    /**
     * sets TargetSiteCd
     * @param aTargetSiteCd : String value
     */
    public void setTargetSiteCd(String aTargetSiteCd)
    {
        targetSiteCd = aTargetSiteCd;
        setItDirty(true);
    }

    /**
     * gets TargetSiteDescTxt
     * @return : String value
     */
    public String getTargetSiteDescTxt()
    {
        return targetSiteDescTxt;
    }

    /**
     * sets TargetSiteDescTxt
     * @param aTargetSiteDescTxt : String value
     */
    public void setTargetSiteDescTxt(String aTargetSiteDescTxt)
    {
        targetSiteDescTxt = aTargetSiteDescTxt;
        setItDirty(true);
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
     * sets UserAffiliationTxt
     * @param aUserAffiliationTxt : String value
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets ProgramJurisdictionOid
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
     * gets SharedInd
     * @return : String value
     */
    public String getSharedInd()
    {
        return sharedInd;
    }

    /**
     * sets SharedInd
     * @param aSharedInd : String value
     */
    public void setSharedInd(String aSharedInd)
    {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * gets VersionCtrlNbr
     * @return : Integer value
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * sets VersionCtrlNbr
     * @param aVersionCtrlNbr : Integer value
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }


    /**
     * compare to find if two objects are equal
     * @param objectname1 : first object
     * @param objectname2 : second object
     * @param voClass : ((InterventionDT) objectname1).getClass
     * @return boolean value
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
      voClass =  (( InterventionDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
    }

   /**
    *set itDirty
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
    * sets ItNew
    * @param itNew : boolean value
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }

   /**
    * gets isItNew
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
    * sets itDelete
    * @param itDelete : boolean value
    */
  public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

   /**
    * gets Super class
    * @return : String value
    */

   public String getSuperclass()
  {
    return NEDSSConstants.CLASSTYPE_ACT;
  }

  /**
   * gets Intervention UID
   * @return : Long value
   */
  public Long getUid()
  {
    return interventionUid;
  }

	public String getMaterialCd() {
		return materialCd;
	}
	
	public void setMaterialCd(String materialCd) {
		this.materialCd = materialCd;
	}
	
	public Integer getAgeAtVacc() {
		return ageAtVacc;
	}
	
	public void setAgeAtVacc(Integer ageAtVacc) {
		this.ageAtVacc = ageAtVacc;
	}
	
	public String getAgeAtVaccUnitCd() {
		return ageAtVaccUnitCd;
	}
	
	public void setAgeAtVaccUnitCd(String ageAtVaccUnitCd) {
		this.ageAtVaccUnitCd = ageAtVaccUnitCd;
	}
	
	public String getVaccMfgrCd() {
		return vaccMfgrCd;
	}
	
	public void setVaccMfgrCd(String vaccMfgrCd) {
		this.vaccMfgrCd = vaccMfgrCd;
	}
	
	public String getMaterialLotNm() {
		return materialLotNm;
	}
	
	public void setMaterialLotNm(String materialLotNm) {
		this.materialLotNm = materialLotNm;
	}
	
	public Timestamp getMaterialExpirationTime() {
		return materialExpirationTime;
	}
	
	public void setMaterialExpirationTime(Timestamp materialExpirationTime) {
		this.materialExpirationTime = materialExpirationTime;
	}
	
	public Integer getVaccDoseNbr() {
		return vaccDoseNbr;
	}
	
	public void setVaccDoseNbr(Integer vaccDoseNbr) {
		this.vaccDoseNbr = vaccDoseNbr;
	}
	
	public String getVaccInfoSourceCd() {
		return vaccInfoSourceCd;
	}
	
	public void setVaccInfoSourceCd(String vaccInfoSourceCd) {
		this.vaccInfoSourceCd = vaccInfoSourceCd;
	}

	public String getElectronicInd() {
		return electronicInd;
	}

	public void setElectronicInd(String electronicInd) {
		this.electronicInd = electronicInd;
	}

}

