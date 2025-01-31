
/**
* Name:		    EntityGroupDT
* Description:	entity data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.entity.entitygroup.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

public class EntityGroupDT extends AbstractVO implements RootDTInterface
{
	private static final long serialVersionUID = 1L;
    private Long entityGroupUid;

    private String addReasonCd;

    private Timestamp addTime;

    private Long addUserId;

    private String cd;

    private String cdDescTxt;

    private String description;

    private String durationAmt;

    private String durationUnitCd;

    private Timestamp fromTime;

    private Integer groupCnt;

    private String lastChgReasonCd;

    private Timestamp lastChgTime;

    private Long lastChgUserId;

    private String localId;

    private String nm;

    private String recordStatusCd;

    private Timestamp recordStatusTime;

    private String statusCd;

    private Timestamp statusTime;

    private Timestamp toTime;

    private String userAffiliationTxt;

    private Integer versionCtrlNbr;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;


    /**
     * getEntityGroupUid
     * @return Long entityGroupId
     */
    public Long getEntityGroupUid()
    {
        return entityGroupUid;
    }
    public void setEntityGroupUid(Long aEntityGroupUid)
    {
        entityGroupUid = aEntityGroupUid;
        setItDirty(true);
    }

    /**
     *  getAddReasonCd
     *  @return String addReasonCd
     */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }
    /**
     *  setAddReasonCd - sets private variable addReasonCd
     *  @param String aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     *  getAddTime
     *  @return Timestamp addTime
     */
    public Timestamp getAddTime()
    {
        return addTime;
    }
    /**
     *  setAddTime - sets private variable addTime
     *  @param Timestamp aAddTime
     */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     *  setAddTime - sets private variable addTime
     *  converts String to Timestamp
     *  @param String strTime
     */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getAddUserId
    * @return Long addUsreId
    */
    public Long getAddUserId()
    {
        return addUserId;
    }
   /**
    * setAddUserId - sets private variable addUsreId
    * @param Long aAddUserId
    */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }

   /**
    * getCd
    * @return String cd
    */
    public String getCd()
    {
        return cd;
    }
   /**
    * setCd - sets private variable cd
    * @param String aCd
    */
    public void setCd(String aCd)
    {
        cd = aCd;
        setItDirty(true);
    }

   /**
    * getCdDescTxt
    * @return String cdDescTxt
    */
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }

    /**
     * setCdDescTxt - sets private variable cdDescTxt
     * @param String aCdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt)
    {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * getDescription
     * @return String description
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * setDescription - sets private variable description
     * @param String aDescription
     */
    public void setDescription(String aDescription)
    {
        description = aDescription;
        setItDirty(true);
    }

    /**
     * getDurationAmt
     * @return String durationAmt
     */
    public String getDurationAmt()
    {
        return durationAmt;
    }
    /**
     * setDurationAmt - sets private variable durationAmt
     * @param String aDurationAmt
     */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

    /**
     * getDurationCd
     * @return String durationCd
     */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }
    /**
     * setDurationunitCd - set private variable durationUnitCd
     * @param String aDurationUnitCd
     */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

    /**
     * getFromTime
     * @return Timestamp fromTime
     */
    public Timestamp getFromTime()
    {
        return fromTime;
    }
    /**
     * setFromTime - sets fromTime
     * @param Timestamp aFromTime
     */
    public void setFromTime(Timestamp aFromTime)
    {
        fromTime = aFromTime;
        setItDirty(true);
    }

    /**
     * setFromTime - sets fromTime
     * takes String as argument and converts to Timestamp
     * @param String strTime
     */
   public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getGroupCnt
    * @return Integer groupCnt
    */
    public Integer getGroupCnt()
    {
        return groupCnt;
    }
   /**
    * setGroupCnt - sets groupCnt
    * @param Integer aGroupCnt
    */
    public void setGroupCnt(Integer aGroupCnt)
    {
        groupCnt = aGroupCnt;
        setItDirty(true);
    }

   /**
    * getLastChgReasonCd
    * @return String lastChgReasonCd
    */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }
   /**
    * setLastChgReasonCd - sets lastChgReasonCd
    * @param Integer String aLastChgReasonCd
    */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * getLastChgTime
     * @return Timestamp lastChagTime
     */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }
    /**
     * setLastChgTime - sets lastChagTime
     * @param Timestamp aLastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * getLastChgTime
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
     * getLastChgUserId
     * @return Long lastChgUserId
     */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

    /**
     * setLastChgUserId - sets lastChgUserId
     * @param Long aLastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * getLocalId
     * @return String localId
     */
    public String getLocalId()
    {
        return localId;
    }
    /**
     * setLocalId - sets localId
     * @param String aLocalId
     */
    public void setLocalId(String aLocalId)
    {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * getNm
     * @return String nm
     */
    public String getNm()
    {
        return nm;
    }

    /**
     * setNm - sets nm
     * @param String aNm
     */
    public void setNm(String aNm)
    {
        nm = aNm;
        setItDirty(true);
    }

    /**
     * getRecordStatusCd
     * @return String recordStatusCd
     */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }
    /**
     * setRecordStatusCd - sets recordStatusCd
     * @param String aRecordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * getRecordStatusTime
     * @return Timestamp recordStatusTime
     */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

    /**
     * setRecordStatusTime
     * @param Timestamp aRecordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * setRecordStatusTime
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getStatusCd
    * @return String statusCd
    */
    public String getStatusCd()
    {
        return statusCd;
    }

    /**
     * setStatusCd
     * @param String aStatusCd
     */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * getStatusTime
     * @return Timestamp statusTime
     */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

    /**
     * setStatusTime
     * @param Timestamp aStatusTime
     */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * setStatusTime
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getToTime
    * @return Timestamp toTime
    */
    public Timestamp getToTime()
    {
        return toTime;
    }

    /**
     * setToTime
     * @param Timestamp aToTime
     */
    public void setToTime(Timestamp aToTime)
    {
        toTime = aToTime;
        setItDirty(true);
    }

    /**
     * setToTime
     * takes String and converts to Timestamp
     * @param String strTime
     */
   public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getUserAffiliationTxt
    * @return String userAffiliationTxt
    */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

    /**
     * setUserAffiliationxt
     * @param String aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * getVersionCtrlNbr
     * @return Integer versionCtrlNbr
     */
    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * setVersionCtrlNbr
     * @param Integer aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * getProgAreaCd
     * @return String progAreaCd
     */
       public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * setProgAreaCd
    * @param String aProgAreaCd
    */
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
    * getJurisdictionCd
    * @return String jurisdictionCd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * setJurisdictionCd
    * @param String aJurisdictionCd
    */
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
    * getProgramJurisdictionOid
    * @return Long programJurisdictionOid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * setProgramJurisdictionOid
    * @param Long aProgramJurisdictionOid
    */
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }

   /**
    * getSharedInd
    * @return String sharedInd
    */
       public String getSharedInd()
   {
      return sharedInd;
   }

   /**
    * setSharedInd
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
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
    {
        voClass =  (( EntityGroupDT) objectname1).getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1,objectname2,voClass));
     }

    /**
     * setItDirty
     * @param boolean itDirty
     */
   public void setItDirty(boolean itDirty)
   {
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
    * setItNew
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
    * setItDelete
    * @param bolean itDelete
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }

   /**
    * getUid
    * @return Long entityGroupUid
    */
   public Long getUid()
   {
    return entityGroupUid;
  }

  /**
   * getSuperclass
   * @return String NEDSSConstantUtil.CLASSTYPE_ENTITY
   */
  public String getSuperclass(){
    return NEDSSConstants.CLASSTYPE_ENTITY;
  }

}
