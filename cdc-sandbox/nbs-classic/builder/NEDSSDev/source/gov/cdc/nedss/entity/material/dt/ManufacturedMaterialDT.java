/**
* Name:		    ManufacturedMaterialDT
* Description:	ManufacturedMaterial data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/


package gov.cdc.nedss.entity.material.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class ManufacturedMaterialDT extends AbstractVO
{

	private static final long serialVersionUID = 1L;
	
    private Long materialUid;

    private Integer manufacturedMaterialSeq;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;

    private Timestamp expirationTime;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String lotNm;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private String userAffiliationTxt;

    private Timestamp stabilityFromTime;

    private Timestamp stabilityToTime;

    private String stabilityDurationCd;

    private String stabilityDurationUnitCd;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;

    /**
     * gets MaterialUid
     * @return Long materialUid
     */
    public Long getMaterialUid()
    {
        return materialUid;
    }

    /**
     * sets MaterialUid
     * @param Long aMaterialUid
     */
    public void setMaterialUid(Long aMaterialUid)
    {
        materialUid = aMaterialUid;
        setItDirty(true);
    }

    /**
     * gets ManufacturedMaterialSeq
     * @return Integer manufacturedMaterialSeq
     */
    public Integer getManufacturedMaterialSeq()
    {
        return manufacturedMaterialSeq;
    }

    /**
     * sets ManufacturedMaterialSeq
     * @param Integer aManufacturedMaterialSeq
     */
    public void setManufacturedMaterialSeq(Integer aManufacturedMaterialSeq)
    {
        manufacturedMaterialSeq = aManufacturedMaterialSeq;
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
     * sets AddReasonCd
     * @param String aAddReasonCd
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
     * takes String and converts to Timestamp
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
     * gets ExpirationTime
     * @return Timestamp expirationTime
     */
    public Timestamp getExpirationTime()
    {
        return expirationTime;
    }

    /**
     * sets ExpirationTime
     * @param Timestamp aExpirationTime
     */
    public void setExpirationTime(Timestamp aExpirationTime)
    {
        expirationTime = aExpirationTime;
        setItDirty(true);
    }

    /**
     * sets ExpirationTime_s
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setExpirationTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setExpirationTime(StringUtils.stringToStrutsTimestamp(strTime));
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
     * @param String lastChgReasonCd
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
     * gets LotNm
     * @return String lotNm
     */
    public String getLotNm()
    {
        return lotNm;
    }

    /**
     * sets LotNm
     * @param String aLotNm
     */
    public void setLotNm(String aLotNm)
    {
        lotNm = aLotNm;
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
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
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
     * @return String aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets StabilityFromTime
     * @return Timestamp stabilityFromTime
     */
    public Timestamp getStabilityFromTime()
    {
        return stabilityFromTime;
    }

    /**
     * sets StabilityFromTime
     * @param Timestamp aStabilityFromTime
     */
    public void setStabilityFromTime(Timestamp aStabilityFromTime)
    {
        stabilityFromTime = aStabilityFromTime;
        setItDirty(true);
    }

    /**
     * sets StabilityFromTime_s
     * @param String strTime
     */
   public void setStabilityFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStabilityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets StabilityToTime
    * @return Timestamp stabilityToTime
    */
    public Timestamp getStabilityToTime()
    {
        return stabilityToTime;
    }

    /**
     * sets StabilityToTime
     * @param Timestamp aStabilityToTime
     */
    public void setStabilityToTime(Timestamp aStabilityToTime)
    {
        stabilityToTime = aStabilityToTime;
        setItDirty(true);
    }

    /**
     * sets StabilityToTime_s
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setStabilityToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStabilityToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * gets StabilityDurationCd
    * @return String stabilityDurationCd
    */
    public String getStabilityDurationCd()
    {
        return stabilityDurationCd;
    }

    /**
     * sets StabilityDurationCd
     * @param String aStabilityDurationCd
     */
    public void setStabilityDurationCd(String aStabilityDurationCd)
    {
        stabilityDurationCd = aStabilityDurationCd;
        setItDirty(true);
    }

    /**
     * gets StabilityDurationUnitCd
     * @return String stabilityDurationUnitCd
     */
    public String getStabilityDurationUnitCd()
    {
        return stabilityDurationUnitCd;
    }

    /**
     * sets StabilityDurationUnitCd
     * @param String aStabilityDurationUnitCd
     */
    public void setStabilityDurationUnitCd(String aStabilityDurationUnitCd)
    {
        stabilityDurationUnitCd = aStabilityDurationUnitCd;
        setItDirty(true);
    }

    /**
     * gets ProgAreaCd
     * String progAreaCd
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
    * @param Long aProgramJurisdictionOid
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
    voClass =  (( ManufacturedMaterialDT) objectname1).getClass();
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
}
