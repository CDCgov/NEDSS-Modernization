/**
 * Title: ManufacturedMaterialHistDT helper class.
 * Description: A helper class for manufactured material history data table object
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.material.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class ManufacturedMaterialHistDT extends AbstractVO
{
    private Long materialUid;
    private Integer manufacturedMaterialSeq;
    private Integer versionCtrlNbr;
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
    private String programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;


   /**
    * Access method for the materialUid property.
    * @return   the current value of the materialUid property
    */
    public Long getMaterialUid()
    {
      return materialUid;
    }

   /**
    * Sets the value of both materialUid and itDirty properties
    * @param aMaterialUid the new value of the materialUid property
    */
    public void setMaterialUid(Long aMaterialUid)
    {
      materialUid = aMaterialUid;
      setItDirty(true);
    }

   /**
    * Access method for the manufacturedMaterialSeq property.
    * @return   the current value of the manufacturedMaterialSeq property
    */
    public Integer getManufacturedMaterialSeq()
    {
      return manufacturedMaterialSeq;
    }

   /**
    * Sets the value of both manufacturedMaterialSeq and itDirty properties
    * @param aManufacturedMaterialSeq the new value of the manufacturedMaterialSeq property
    */
    public void setManufacturedMaterialSeq(Integer aManufacturedMaterialSeq)
    {
      manufacturedMaterialSeq = aManufacturedMaterialSeq;
      setItDirty(true);
    }

   /**
    * Access method for the versionCtrlNbr property.
    * @return   the current value of the versionCtrlNbr property
    */
    public Integer getVersionCtrlNbr()
    {
      return versionCtrlNbr;
    }

   /**
    * Sets the value of both versionCtrlNbr and itDirty properties
    * @param aVersionCtrlNbr the new value of the versionCtrlNbr property
    */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
      versionCtrlNbr = aVersionCtrlNbr;
      setItDirty(true);
    }

   /**
    * Access method for the addReasonCd property.
    * @return   the current value of the addReasonCd property
    */
    public String getAddReasonCd()
    {
      return addReasonCd;
    }

   /**
    * Sets the value of both addReasonCd and itDirty properties
    * @param aAddReasonCd the new value of the addReasonCd property
    */
    public void setAddReasonCd(String aAddReasonCd)
    {
      addReasonCd = aAddReasonCd;
      setItDirty(true);
    }

   /**
    * Access method for the addTime property.
    * @return   the current value of the addTime property
    */
    public Timestamp getAddTime()
    {
      return addTime;
    }

   /**
    * Sets the value of both addTime and itDirty properties
    * @param aAddTime the new value of the addTime property
    */
    public void setAddTime(Timestamp aAddTime)
    {
      addTime = aAddTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both addTime and itDirty properties
    * @param strTime the new value of the addTime property
    */
    public void setAddTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the addUserId property.
    * @return   the current value of the addUserId property
    */
    public Long getAddUserId()
    {
      return addUserId;
    }

   /**
    * Sets the value of both addUserId and itDirty properties
    * @param aAddUserId the new value of the addUserId property
    */
    public void setAddUserId(Long aAddUserId)
    {
      addUserId = aAddUserId;
      setItDirty(true);
    }

   /**
    * Access method for the expirationTime property.
    * @return   the current value of the expirationTime property
    */
    public Timestamp getExpirationTime()
    {
      return expirationTime;
    }

   /**
    * Sets the value of both expirationTime and itDirty properties
    * @param aExpirationTime the new value of the expirationTime property
    */
    public void setExpirationTime(Timestamp aExpirationTime)
    {
      expirationTime = aExpirationTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both expirationTime and itDirty properties
    * @param strTime the new value of the expirationTime property
    */
    public void setExpirationTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setExpirationTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the lastChgReasonCd property.
    * @return   the current value of the lastChgReasonCd property
    */
    public String getLastChgReasonCd()
    {
      return lastChgReasonCd;
    }

   /**
    * Sets the value of both lastChgReasonCd and itDirty properties
    * @param aLastChgReasonCd the new value of the lastChgReasonCd property
    */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
      lastChgReasonCd = aLastChgReasonCd;
      setItDirty(true);
    }

   /**
    * Access method for the lastChgTime property.
    * @return   the current value of the lastChgTime property
    */
    public Timestamp getLastChgTime()
    {
      return lastChgTime;
    }

   /**
    * Sets the value of both lastChgTime and itDirty properties
    * @param aLastChgTime the new value of the lastChgTime property
    */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
      lastChgTime = aLastChgTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both lastChgTime and itDirty properties
    * @param strTime the new value of the lastChgTime property
    */
    public void setLastChgTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the lastChgUserId property.
    * @return   the current value of the lastChgUserId property
    */
    public Long getLastChgUserId()
    {
      return lastChgUserId;
    }

   /**
    * Sets the value of both lastChgUserId and itDirty properties
    * @param aLastChgUserId the new value of the lastChgUserId property
    */
    public void setLastChgUserId(Long aLastChgUserId)
    {
      lastChgUserId = aLastChgUserId;
      setItDirty(true);
    }

   /**
    * Access method for the lotNm property.
    * @return   the current value of the lotNm property
    */
    public String getLotNm()
    {
      return lotNm;
    }

   /**
    * Sets the value of both lotNm and itDirty properties
    * @param aLotNm the new value of the lotNm property
    */
    public void setLotNm(String aLotNm)
    {
      lotNm = aLotNm;
      setItDirty(true);
    }

   /**
    * Access method for the recordStatusCd property.
    * @return   the current value of the recordStatusCd property
    */
    public String getRecordStatusCd()
    {
      return recordStatusCd;
    }

   /**
    * Sets the value of both recordStatusCd and itDirty properties
    * @param aRecordStatusCd the new value of the recordStatusCd property
    */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
      recordStatusCd = aRecordStatusCd;
      setItDirty(true);
    }

   /**
    * Access method for the recordStatusTime property.
    * @return   the current value of the recordStatusTime property
    */
    public Timestamp getRecordStatusTime()
    {
      return recordStatusTime;
    }

   /**
    * Sets the value of both recordStatusTime and itDirty properties
    * @param aRecordStatusTime the new value of the recordStatusTime property
    */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
      recordStatusTime = aRecordStatusTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both recordStatusTime and itDirty properties
    * @param strTime the new value of the recordStatusTime property
    */
    public void setRecordStatusTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the userAffiliationTxt property.
    * @return   the current value of the userAffiliationTxt property
    */
    public String getUserAffiliationTxt()
    {
      return userAffiliationTxt;
    }

   /**
    * Sets the value of both userAffiliationTxt and itDirty properties
    * @param aUserAffiliationTxt the new value of the userAffiliationTxt property
    */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
      userAffiliationTxt = aUserAffiliationTxt;
      setItDirty(true);
    }

   /**
    * Access method for the stabilityFromTime property.
    * @return   the current value of the stabilityFromTime property
    */
    public Timestamp getStabilityFromTime()
    {
      return stabilityFromTime;
    }

   /**
    * Sets the value of both stabilityFromTime and itDirty properties
    * @param aStabilityFromTime the new value of the stabilityFromTime property
    */
    public void setStabilityFromTime(Timestamp aStabilityFromTime)
    {
      stabilityFromTime = aStabilityFromTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both stabilityFromTime and itDirty properties
    * @param strTime the new value of the stabilityFromTime property
    */
    public void setStabilityFromTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setStabilityFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the stabilityToTime property.
    * @return   the current value of the stabilityToTime property
    */
    public Timestamp getStabilityToTime()
    {
      return stabilityToTime;
    }

   /**
    * Sets the value of both stabilityToTime and itDirty properties
    * @param aStabilityToTime the new value of the stabilityToTime property
    */
    public void setStabilityToTime(Timestamp aStabilityToTime)
    {
      stabilityToTime = aStabilityToTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both stabilityToTime and itDirty properties
    * @param strTime the new value of the stabilityToTime property
    */
    public void setStabilityToTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setStabilityToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the stabilityDurationCd property.
    * @return   the current value of the stabilityDurationCd property
    */
    public String getStabilityDurationCd()
    {
      return stabilityDurationCd;
    }

   /**
    * Sets the value of both stabilityDurationCd and itDirty properties
    * @param aStabilityDurationCd the new value of the stabilityDurationCd property
    */
    public void setStabilityDurationCd(String aStabilityDurationCd)
    {
      stabilityDurationCd = aStabilityDurationCd;
      setItDirty(true);
    }

   /**
    * Access method for the stabilityDurationUnitCd property.
    * @return   the current value of the stabilityDurationUnitCd property
    */
    public String getStabilityDurationUnitCd()
    {
      return stabilityDurationUnitCd;
    }

   /**
    * Sets the value of both stabilityDurationUnitCd and itDirty properties
    * @param aStabilityDurationUnitCd the new value of the stabilityDurationUnitCd property
    */
    public void setStabilityDurationUnitCd(String aStabilityDurationUnitCd)
    {
      stabilityDurationUnitCd = aStabilityDurationUnitCd;
      setItDirty(true);
    }

   /**
    * Access method for the progAreaCd property.
    * @return   the current value of the progAreaCd property
    */
    public String getProgAreaCd()
    {
      return progAreaCd;
    }

   /**
    * Sets the value of both progAreaCd and itDirty properties
    * @param aProgAreaCd the new value of the progAreaCd property
    */
    public void setProgAreaCd(String aProgAreaCd)
    {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
    }

   /**
    * Access method for the jurisdictionCd property.
    * @return   the current value of the jurisdictionCd property
    */
    public String getJurisdictionCd ()
    {
      return jurisdictionCd ;
    }

   /**
    * Sets the value of both jurisdictionCd and itDirty properties
    * @param aJurisdictionCd the new value of the jurisdictionCd property
    */
    public void setJurisdictionCd (String aJurisdictionCd )
    {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
    }

   /**
    * Access method for the programJurisdictionOid property.
    * @return   the current value of the programJurisdictionOid property
    */
    public String getProgramJurisdictionOid ()
    {
      return programJurisdictionOid;
    }

   /**
    * Sets the value of both programJurisdictionOid and itDirty properties
    * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
    public void setProgramJurisdictionOid (String aProgramJurisdictionOid )
    {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
    }

   /**
    * Access method for the sharedInd property.
    * @return   the current value of the sharedInd property
    */
    public String getSharedInd()
    {
      return sharedInd;
    }

   /**
    * Sets the value of both sharedInd and itDirty properties
    * @param aSharedInd the new value of the sharedInd property
    */
    public void setSharedInd(String aSharedInd)
    {
      sharedInd = aSharedInd;
      setItDirty(true);
    }

   /**
    * This method compares two objects and returns the results
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return   the result of the comparison
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
      voClass =  (( ManufacturedMaterialHistDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
    }

   /**
    * Sets the value of the itDirty property
    * @param itDirty the new value of the itDirty property
    */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

   /**
    * Access method for the itDirty property.
    * @return   the current value of the itDirty property
    */
    public boolean isItDirty() {
      return itDirty;
    }

   /**
    * Sets the value of the itNew property
    * @param itNew the new value of the itNew property
    */
    public void setItNew(boolean itNew) {
      this.itNew = itNew;
    }

   /**
    * Access method for the isItNew property.
    * @return   the current value of the isItNew property
    */
    public boolean isItNew() {
      return itNew;
    }

   /**
    * Access method for the itDelete property.
    * @return   the current value of the itDelete property
    */
    public boolean isItDelete() {
      return itDelete;
    }

   /**
    * Sets the value of the itDelete property
    * @param itDelete the new value of the itDelete property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
    }


}
