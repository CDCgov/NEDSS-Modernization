/**
* Name:		    EntityIdDT
* Description:	entity id data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/


package gov.cdc.nedss.entity.entityid.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class EntityIdHistDT extends AbstractVO
{
    private Long entityUid;
    private Integer entityIdSeq;
    private Integer versionCtrlNbr;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp asOfDate;
    private String assigningAuthorityCd;
    private String assigningAuthorityDescTxt;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String rootExtensionTxt;
    private String statusCd;
    private Timestamp statusTime;
    private String typeCd;
    private String typeDescTxt;
    private String userAffiliationTxt;
    private Timestamp validFromTime;
    private Timestamp validToTime;
     private String progAreaCd = null;
     private String jurisdictionCd = null;
     private Long programJurisdictionOid = null;
     private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    private String assigningAuthorityIdType;

    /**
     * gets EntityUid
     * @return Long entityUid
     */
    public Long getEntityUid()
    {
        return entityUid;
    }

    /**
     * sets EntityUid
     * @param Long aEntityUid
     */
    public void setEntityUid(Long aEntityUid)
    {
        entityUid = aEntityUid;
        setItDirty(true);
    }

    /**
     * gets EntityIdSeq
     * @return Integer entityIdSeq
     */
    public Integer getEntityIdSeq()
    {
        return entityIdSeq;
    }

    /**
     * sets IntityIdSeq
     * @param Integer aEntityIdSeq
     */
    public void setEntityIdSeq(Integer aEntityIdSeq)
    {
        entityIdSeq = aEntityIdSeq;
        setItDirty(true);
    }

    /**
     * gets VersionCtrlNbr
     * @return Integer versionCtrlNbr
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * sets VersionCtrlNbr
     * @param Integer aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * gets AddReasonCd
     * @return String addReasonCd
     */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

    /**
     * sets AssReasonCd
     * @return String aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets AddTime
     * @return Timestamp addTime
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }

    /**
     * sets AddTime
     * @param Timestamp aAddTime
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets AddTime_s
     * takes String adn converts to Timestamp
     * @param String strTime
     */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets AddUserId
    * @return Long addUserId
    */
    public Long getAddUserId()
    {
        return addUserId;
    }

    /**
     * sets AddUserId
     * @param Long aAddUserId
     */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

	/**
	 * gets asOfDate
	 * @return Timestamp
	 */
	public Timestamp getAsOfDate(){
		return this.asOfDate;
	}


	/**
	 * sets asOfDate
	 * @param aAsOfDate
	 */
	public void setAsOfDate(Timestamp aAsOfDate){
		this.asOfDate = aAsOfDate;
		setItDirty(true);
	}

    /**
     * getAssigningAuthorityCd
     * @return String assigningAuthorityCd
     */
    public String getAssigningAuthorityCd()
    {
        return assigningAuthorityCd;
    }

    /**
     * sets AssigningAuthorityCd
     * @param String aAssigningAuthorityCd
     */
    public void setAssigningAuthorityCd(String aAssigningAuthorityCd)
    {
        assigningAuthorityCd = aAssigningAuthorityCd;
        setItDirty(true);
    }

    /**
     * gets AssigningAuthorityDescTxt
     * @return String assigningAuthorityDescTxt
     */
    public String getAssigningAuthorityDescTxt()
    {
        return assigningAuthorityDescTxt;
    }

    /**
     * sets AssigningAuthorityDescTxt
     * @param String aAssigningAuthorityDescTxt
     */
    public void setAssigningAuthorityDescTxt(String aAssigningAuthorityDescTxt)
    {
        assigningAuthorityDescTxt = aAssigningAuthorityDescTxt;
        setItDirty(true);
    }

    /**
     * gets DurationAmt
     * @return String durationAmt
     */
    public String getDurationAmt()
    {
        return durationAmt;
    }

    /**
     * sets DurationAmt
     * @param String durationAmt
     */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

    /**
     * gets DurationUnitCd
     * @return String durationUnitCd
     */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }

    /**
     * sets DurationulnitCd
     * @param String aDurationUnitCd
     */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets EffectiveFromTime
     * @return Timestamp effectiveFromTime
     */
    public Timestamp getEffectiveFromTime()
    {
        return effectiveFromTime;
    }

    /**
     * sets EffectiveFromTime
     * @param Timestamp aEffectiveFromTime
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
     * sets EffectiveFromTime_s
     * takes a String and converts to Timestamp
     * @param String strTime
     */
   public void setEffectiveFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets EffectiveToTime
    * @return Timestamp effectiveToTime
    */
    public Timestamp getEffectiveToTime()
    {
        return effectiveToTime;
    }

    /**
     * sets EffectiveToTime
     * @param Timestamp aEffectiveToTime
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
     * sets EffectiveToTime_s
     * takes a String and converts to Timestamp
     * @param String strTime
     */
   public void setEffectiveToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets LastChgReasonCd
    * @return String lastChgReasonCd
    */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

    /**
     * sets LastChgReasonCd
     * @param String aLastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * gets LastChgTime
     * @return Timestamp lastChgTime
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

    /**
     * sets LastChgTime
     * @param Timestamp lastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets LastChgTime_s
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * gets LastChgUserId
     * @return Long lastChgUserId
     */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * sets LastChgUserId
     * @param Long aLastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * gets RecordStatusCd
     * @return String recordStatusCd
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

    /**
     * sets RecordStatusCd
     * @param String aRecordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * gets RecordStatusTime
     * @return Timestamp recordStatusTime
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * sets RecordStatusTime
     * @param Timestamp aRecordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * sets RecordStatusTime_s
     * takes String and converts to Timestampe
     * @param String strTime
     */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets RootExtensionTxt
    * @return rootExtensionTxt
    */
    public String getRootExtensionTxt()
    {
        return rootExtensionTxt;
    }

    /**
     * sets RootExtensionTxt
     * @return String aRootExtensionTxt
     */
    public void setRootExtensionTxt(String aRootExtensionTxt)
    {
        rootExtensionTxt = aRootExtensionTxt;
        setItDirty(true);
    }

    /**
     * gets StatusCd
     * @return statusCd
     */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * sets StatusCd
     * @return String aStatusCd
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * gets StatusTime
     * @return Timestamp statusTime
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * sets StatusTime
     * @param Timestamp aStatusTime
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * setStatusTime_s
     * takes a String and converts it to a Timestamp
     * @param String strTime
     */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets TypeCd
    * @return String typeCd
    */
    public String getTypeCd()
    {
        return typeCd;
    }

    /**
     * sets TypeCd
     * @param String aTypeCd
     */
    public void setTypeCd(String aTypeCd)
    {
        typeCd = aTypeCd;
        setItDirty(true);
    }

    /**
     * gets TypeDescTxt
     * @return String typeDescTxt
     */
    public String getTypeDescTxt()
    {
        return typeDescTxt;
    }

    /**
     * sets TypeDescTxt
     * @param String aTypeDescTxt
     */
    public void setTypeDescTxt(String aTypeDescTxt)
    {
        typeDescTxt = aTypeDescTxt;
        setItDirty(true);
    }

    /**
     * gets UserAffiliationTxt
     * @return String userAffiliationTxt
     */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * sets UserAffiliationTxt
     * @param String aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets ValidFromTime
     * @return Timestamp validFromTime
     */
    public Timestamp getValidFromTime()
    {
        return validFromTime;
    }

    /**
     * sets ValidFromTime
     * @param Timestamp aValidFromTime
     */
    public void setValidFromTime(Timestamp aValidFromTime)
    {
        validFromTime = aValidFromTime;
        setItDirty(true);
    }

    /**
     * sets ValidFromTime_s
     * takes a String and converts it to a Timestamp
     * @param String strTime
     */
   public void setValidFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setValidFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets ValidToTime
    * @return Timestamp validToTime
    */
    public Timestamp getValidToTime()
    {
        return validToTime;
    }

    /**
     * sets ValidToTime
     * @param Timestamp aValidToTime
     */
    public void setValidToTime(Timestamp aValidToTime)
    {
        validToTime = aValidToTime;
        setItDirty(true);
    }

    /**
     * sets ValidToTime_s
     * takes a String and converts it to a Timestamp
     * @param String strTime
     */
   public void setValidToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setValidToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets ProgAreaCd
    * @return String progAreaCd
    */
    public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * sets ProgAreaCd
    * @param String aProgAreaCd
    */
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
    * gets JurisdictionCd
    * @return String jurisdictionCd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * sets JurisdictionCd
    * @param String aJurisdictionCd
    */
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
    * gets ProgramJurisdictionOid
    * @return Long programJurisdictionOid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * sets ProgramJurisdictionOid
    * @param String aProgramJurisdictionOid
    */
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }

   /**
    * gets SharedInd
    * @return sharedInd
    */
   public String getSharedInd()
   {
      return sharedInd;
   }

   /**
    * sets SharedInd
    * @param String aSharedInd
    */
      public void setSharedInd(String aSharedInd)
   {
      sharedInd = aSharedInd;
      setItDirty(true);
   }

    /**
     * isEqual
     * @param java.lang.Object objectname1
     * @param java.lang.Object objectname2
     * @param Class<?> voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( EntityIdHistDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }

    /**
     * sets ItDirty
     * @param boolean itDirty
     */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }

   /**
    * isItDirty
    * @return boolean itDirty
    */
   public boolean isItDirty() {
     return itDirty;
   }

   /**
    * sets ItNew
    * @param boolean itNew
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }

   /**
    * isItNew
    * @return boolean itNew
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * isItDelete
    * @return boolean itDelete
    */
   public boolean isItDelete() {
     return itDelete;
   }

   /**
    * sets ItDelete
    * @param boolean itDelete
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

public String getAssigningAuthorityIdType() {
	return assigningAuthorityIdType;
}

public void setAssigningAuthorityIdType(String assigningAuthorityIdType) {
	this.assigningAuthorityIdType = assigningAuthorityIdType;
}
    
    
}
