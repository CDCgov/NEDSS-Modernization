/**
 * Title: MaterialHistDT helper class.
 * Description: A helper class for material history data table object
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.material.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class MaterialHistDT extends AbstractVO
{
    private Long materialUid;
    private Integer versionCtrlNbr;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cd;
    private String cdDescTxt;
    private String description;
    private String effectiveDurationAmt;
    private String effectiveDurationUnitCd;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String formCd;
    private String formDescTxt;
    private String handlingCd;
    private String handlingDescTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String nm;
    private String qty;
    private String qtyUnitCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String riskCd;
    private String riskDescTxt;
    private String statusCd;
    private Timestamp statusTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
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
    * Access method for the cd property.
    * @return   the current value of the cd property
    */
    public String getCd()
    {
      return cd;
    }

   /**
    * Sets the value of both cd and itDirty properties
    * @param aCd the new value of the cd property
    */
    public void setCd(String aCd)
    {
      cd = aCd;
      setItDirty(true);
    }

   /**
    * Access method for the cdDescTxt property.
    * @return   the current value of the cdDescTxt property
    */
    public String getCdDescTxt()
    {
      return cdDescTxt;
    }

   /**
    * Sets the value of both cdDescTxt and itDirty properties
    * @param aCdDescTxt the new value of the cdDescTxt property
    */
    public void setCdDescTxt(String aCdDescTxt)
    {
      cdDescTxt = aCdDescTxt;
      setItDirty(true);
    }

   /**
    * Access method for the description property.
    * @return   the current value of the description property
    */
    public String getDescription()
    {
      return description;
    }

   /**
    * Sets the value of both description and itDirty properties
    * @param aDescription the new value of the description property
    */
    public void setDescription(String aDescription)
    {
      description = aDescription;
      setItDirty(true);
    }

   /**
    * Access method for the effectiveDurationAmt property.
    * @return   the current value of the effectiveDurationAmt property
    */
    public String getEffectiveDurationAmt()
    {
      return effectiveDurationAmt;
    }

   /**
    * Sets the value of both effectiveDurationAmt and itDirty properties
    * @param aEffectiveDurationAmt the new value of the effectiveDurationAmt property
    */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt)
    {
      effectiveDurationAmt = aEffectiveDurationAmt;
      setItDirty(true);
    }

   /**
    * Access method for the effectiveDurationUnitCd property.
    * @return   the current value of the effectiveDurationUnitCd property
    */
    public String getEffectiveDurationUnitCd()
    {
      return effectiveDurationUnitCd;
    }

   /**
    * Sets the value of both effectiveDurationUnitCd and itDirty properties
    * @param aEffectiveDurationUnitCd the new value of the effectiveDurationUnitCd property
    */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd)
    {
      effectiveDurationUnitCd = aEffectiveDurationUnitCd;
      setItDirty(true);
    }

   /**
    * Access method for the effectiveFromTime property.
    * @return   the current value of the effectiveFromTime property
    */
    public Timestamp getEffectiveFromTime()
    {
      return effectiveFromTime;
    }

   /**
    * Sets the value of both effectiveFromTime and itDirty properties
    * @param aEffectiveFromTime the new value of the effectiveFromTime property
    */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime)
    {
      effectiveFromTime = aEffectiveFromTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both effectiveFromTime and itDirty properties
    * @param strTime the new value of the effectiveFromTime property
    */
    public void setEffectiveFromTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the effectiveToTime property.
    * @return   the current value of the effectiveToTime property
    */
    public Timestamp getEffectiveToTime()
    {
      return effectiveToTime;
    }

   /**
    * Sets the value of both effectiveToTime and itDirty properties
    * @param aEffectiveToTime the new value of the effectiveToTime property
    */
    public void setEffectiveToTime(Timestamp aEffectiveToTime)
    {
      effectiveToTime = aEffectiveToTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both effectiveToTime and itDirty properties
    * @param strTime the new value of the effectiveToTime property
    */
    public void setEffectiveToTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the formCd property.
    * @return   the current value of the formCd property
    */
    public String getFormCd()
    {
      return formCd;
    }

   /**
    * Sets the value of both formCd and itDirty properties
    * @param aFormCd the new value of the formCd property
    */
    public void setFormCd(String aFormCd)
    {
      formCd = aFormCd;
      setItDirty(true);
    }

   /**
    * Access method for the formDescTxt property.
    * @return   the current value of the formDescTxt property
    */
    public String getFormDescTxt()
    {
      return formDescTxt;
    }

   /**
    * Sets the value of both formDescTxt and itDirty properties
    * @param aFormDescTxt the new value of the formDescTxt property
    */
    public void setFormDescTxt(String aFormDescTxt)
    {
      formDescTxt = aFormDescTxt;
      setItDirty(true);
    }

   /**
    * Access method for the handlingCd property.
    * @return   the current value of the handlingCd property
    */
    public String getHandlingCd()
    {
      return handlingCd;
    }

   /**
    * Sets the value of both handlingCd and itDirty properties
    * @param aHandlingCd the new value of the handlingCd property
    */
    public void setHandlingCd(String aHandlingCd)
    {
      handlingCd = aHandlingCd;
      setItDirty(true);
    }

   /**
    * Access method for the handlingDescTxt property.
    * @return   the current value of the handlingDescTxt property
    */
    public String getHandlingDescTxt()
    {
    return handlingDescTxt;
    }

   /**
    * Sets the value of both handlingDescTxt and itDirty properties
    * @param aHandlingDescTxt the new value of the handlingDescTxt property
    */
    public void setHandlingDescTxt(String aHandlingDescTxt)
    {
      handlingDescTxt = aHandlingDescTxt;
      setItDirty(true);
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
    * Access method for the localId property.
    * @return   the current value of the localId property
    */
    public String getLocalId()
    {
      return localId;
    }

   /**
    * Sets the value of both localId and itDirty properties
    * @param aLocalId the new value of the localId property
    */
    public void setLocalId(String aLocalId)
    {
      localId = aLocalId;
      setItDirty(true);
    }

   /**
    * Access method for the nm property.
    * @return   the current value of the nm property
    */
    public String getNm()
    {
      return nm;
    }

   /**
    * Sets the value of both nm and itDirty properties
    * @param aNm the new value of the nm property
    */
    public void setNm(String aNm)
    {
      nm = aNm;
      setItDirty(true);
    }

   /**
    * Access method for the qty property.
    * @return   the current value of the qty property
    */
    public String getQty()
    {
      return qty;
    }

   /**
    * Sets the value of both qty and itDirty properties
    * @param aQty the new value of the qty property
    */
    public void setQty(String aQty)
    {
      qty = aQty;
      setItDirty(true);
    }

   /**
    * Access method for the qtyUnitCd property.
    * @return   the current value of the qtyUnitCd property
    */
    public String getQtyUnitCd()
    {
      return qtyUnitCd;
    }

   /**
    * Sets the value of both qtyUnitCd and itDirty properties
    * @param aQtyUnitCd the new value of the qtyUnitCd property
    */
    public void setQtyUnitCd(String aQtyUnitCd)
    {
      qtyUnitCd = aQtyUnitCd;
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
    * Access method for the riskCd property.
    * @return   the current value of the riskCd property
    */
    public String getRiskCd()
    {
      return riskCd;
    }

   /**
    * Sets the value of both riskCd and itDirty properties
    * @param aRiskCd the new value of the riskCd property
    */
    public void setRiskCd(String aRiskCd)
    {
      riskCd = aRiskCd;
      setItDirty(true);
    }

   /**
    * Access method for the riskDescTxt property.
    * @return   the current value of the riskDescTxt property
    */
    public String getRiskDescTxt()
    {
      return riskDescTxt;
    }

   /**
    * Sets the value of both riskDescTxt and itDirty properties
    * @param aRiskDescTxt the new value of the riskDescTxt property
    */
    public void setRiskDescTxt(String aRiskDescTxt)
    {
      riskDescTxt = aRiskDescTxt;
      setItDirty(true);
    }

   /**
    * Access method for the statusCd property.
    * @return   the current value of the statusCd property
    */
    public String getStatusCd()
    {
      return statusCd;
    }

   /**
    * Sets the value of both statusCd and itDirty properties
    * @param aStatusCd the new value of the statusCd property
    */
    public void setStatusCd(String aStatusCd)
    {
      statusCd = aStatusCd;
      setItDirty(true);
    }

   /**
    * Access method for the statusTime property.
    * @return   the current value of the statusTime property
    */
    public Timestamp getStatusTime()
    {
      return statusTime;
    }

   /**
    * Sets the value of both statusTime and itDirty properties
    * @param aStatusTime the new value of the statusTime property
    */
    public void setStatusTime(Timestamp aStatusTime)
    {
      statusTime = aStatusTime;
      setItDirty(true);
    }

   /**
    * Sets the value of both statusTime and itDirty properties
    * @param strTime the new value of the statusTime property
    */
    public void setStatusTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
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
    public Long getProgramJurisdictionOid ()
    {
      return programJurisdictionOid;
    }

   /**
    * Sets the value of both programJurisdictionOid and itDirty properties
    * @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
    public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
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
      voClass =  (( MaterialHistDT) objectname1).getClass();
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
