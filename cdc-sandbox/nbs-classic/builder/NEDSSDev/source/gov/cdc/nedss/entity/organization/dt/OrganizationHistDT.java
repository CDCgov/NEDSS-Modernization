/**
 * Title: OrganizationHistDT helper class.
 * Description: A helper class for organization history data table
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.RootDTInterface;

public class OrganizationHistDT extends AbstractVO implements RootDTInterface
{
    private Long organizationUid;
    private Integer versionCtrlNbr;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cd;
    private String cdDescTxt;
    private String description;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String standardIndustryClassCd;
    private String standardIndustryDescTxt;
    private String statusCd;
    private Timestamp statusTime;
    private Timestamp toTime;
    private String userAffiliationTxt;
    private String displayNm;
    private String streetAddr1;
    private String streetAddr2;
    private String cityCd;
    private String cityDescTxt;
    private String stateCd;
    private String cntyCd;
    private String cntryCd;
    private String zipCd;
    private String phoneNbr;
    private String phoneCntryCd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;


   /**
    * Access method for the organizationUid property.
    * @return   the current value of the organizationUid property
    */
    public Long getOrganizationUid()
    {
        return organizationUid;
    }

   /**
    * Sets the value of both organizationUid and itDirty properties
    * @param aOrganizationUid the new value of the organizationUid property
    */
    public void setOrganizationUid(Long aOrganizationUid)
    {
        organizationUid = aOrganizationUid;
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
    * Access method for the durationAmt property.
    * @return   the current value of the durationAmt property
    */
    public String getDurationAmt()
    {
        return durationAmt;
    }

   /**
    * Sets the value of both durationAmt and itDirty properties
    * @param aDurationAmt the new value of the durationAmt property
    */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

   /**
    * Access method for the durationUnitCd property.
    * @return   the current value of the durationUnitCd property
    */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }

   /**
    * Sets the value of both durationUnitCd and itDirty properties
    * @param aDurationUnitCd the new value of the durationUnitCd property
    */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

   /**
    * Access method for the fromTime property.
    * @return   the current value of the fromTime property
    */
    public Timestamp getFromTime()
    {
        return fromTime;
    }

   /**
    * Sets the value of both fromTime and itDirty properties
    * @param aFromTime the new value of the fromTime property
    */
    public void setFromTime(Timestamp aFromTime)
    {
        fromTime = aFromTime;
        setItDirty(true);
    }

   /**
    * Sets the value of both fromTime and itDirty properties
    * @param strTime the new value of the fromTime property
    */
    public void setFromTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
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
    * Access method for the standardIndustryClassCd property.
    * @return   the current value of the standardIndustryClassCd property
    */
    public String getStandardIndustryClassCd()
    {
        return standardIndustryClassCd;
    }

   /**
    * Sets the value of both standardIndustryClassCd and itDirty properties
    * @param aStandardIndustryClassCd the new value of the standardIndustryClassCd property
    */
    public void setStandardIndustryClassCd(String aStandardIndustryClassCd)
    {
        standardIndustryClassCd = aStandardIndustryClassCd;
        setItDirty(true);
    }

   /**
    * Access method for the standardIndustryDescTxt property.
    * @return   the current value of the standardIndustryDescTxt property
    */
    public String getStandardIndustryDescTxt()
    {
        return standardIndustryDescTxt;
    }

   /**
    * Sets the value of both standardIndustryDescTxt and itDirty properties
    * @param aStandardIndustryDescTxt the new value of the standardIndustryDescTxt property
    */
    public void setStandardIndustryDescTxt(String aStandardIndustryDescTxt)
    {
        standardIndustryDescTxt = aStandardIndustryDescTxt;
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
    * Access method for the toTime property.
    * @return   the current value of the toTime property
    */
    public Timestamp getToTime()
    {
        return toTime;
    }

   /**
    * Sets the value of both toTime and itDirty properties
    * @param aToTime the new value of the toTime property
    */
    public void setToTime(Timestamp aToTime)
    {
        toTime = aToTime;
        setItDirty(true);
    }

   /**
    * Sets the value of both toTime and itDirty properties
    * @param strTime the new value of the toTime property
    */
    public void setToTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
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
    * Access method for the displayNm property.
    * @return   the current value of the displayNm property
    */
    public String getDisplayNm()
    {
        return displayNm;
    }

   /**
    * Sets the value of both displayNm and itDirty properties
    * @param aDisplayNm the new value of the displayNm property
    */
    public void setDisplayNm(String aDisplayNm)
    {
        displayNm = aDisplayNm;
        setItDirty(true);
    }

   /**
    * Access method for the streetAddr1 property.
    * @return   the current value of the streetAddr1 property
    */
    public String getStreetAddr1()
    {
        return streetAddr1;
    }

   /**
    * Sets the value of both streetAddr1 and itDirty properties
    * @param aStreetAddr1 the new value of the streetAddr1 property
    */
    public void setStreetAddr1(String aStreetAddr1)
    {
        streetAddr1 = aStreetAddr1;
        setItDirty(true);
    }

   /**
    * Access method for the streetAddr2 property.
    * @return   the current value of the streetAddr2 property
    */
    public String getStreetAddr2()
    {
        return streetAddr2;
    }

   /**
    * Sets the value of both streetAddr2 and itDirty properties
    * @param aStreetAddr2 the new value of the streetAddr2 property
    */
    public void setStreetAddr2(String aStreetAddr2)
    {
        streetAddr2 = aStreetAddr2;
        setItDirty(true);
    }

   /**
    * Access method for the cityCd property.
    * @return   the current value of the cityCd property
    */
    public String getCityCd()
    {
        return cityCd;
    }

   /**
    * Sets the value of both cityCd and itDirty properties
    * @param aCityCd the new value of the cityCd property
    */
    public void setCityCd(String aCityCd)
    {
        cityCd = aCityCd;
        setItDirty(true);
    }

   /**
    * Access method for the cityDescTxt property.
    * @return   the current value of the cityDescTxt property
    */
    public String getCityDescTxt()
    {
        return cityDescTxt;
    }

   /**
    * Sets the value of both cityDescTxt and itDirty properties
    * @param aCityDescTxt the new value of the cityDescTxt property
    */
    public void setCityDescTxt(String aCityDescTxt)
    {
        cityDescTxt = aCityDescTxt;
        setItDirty(true);
    }

   /**
    * Access method for the stateCd property.
    * @return   the current value of the stateCd property
    */
    public String getStateCd()
    {
        return stateCd;
    }

   /**
    * Sets the value of both stateCd and itDirty properties
    * @param aStateCd the new value of the stateCd property
    */
    public void setStateCd(String aStateCd)
    {
        stateCd = aStateCd;
        setItDirty(true);
    }

   /**
    * Access method for the cntyCd property.
    * @return   the current value of the cntyCd property
    */
    public String getCntyCd()
    {
        return cntyCd;
    }

   /**
    * Sets the value of both cntyCd and itDirty properties
    * @param aCntyCd the new value of the cntyCd property
    */
    public void setCntyCd(String aCntyCd)
    {
        cntyCd = aCntyCd;
        setItDirty(true);
    }

   /**
    * Access method for the cntryCd property.
    * @return   the current value of the cntryCd property
    */
    public String getCntryCd()
    {
        return cntryCd;
    }

   /**
    * Sets the value of both cntryCd and itDirty properties
    * @param aCntryCd the new value of the cntryCd property
    */
    public void setCntryCd(String aCntryCd)
    {
        cntryCd = aCntryCd;
        setItDirty(true);
    }

   /**
    * Access method for the zipCd property.
    * @return   the current value of the zipCd property
    */
    public String getZipCd()
    {
        return zipCd;
    }

   /**
    * Sets the value of both zipCd and itDirty properties
    * @param aZipCd the new value of the zipCd property
    */
    public void setZipCd(String aZipCd)
    {
        zipCd = aZipCd;
        setItDirty(true);
    }

   /**
    * Access method for the phoneNbr property.
    * @return   the current value of the phoneNbr property
    */
    public String getPhoneNbr()
    {
        return phoneNbr;
    }

   /**
    * Sets the value of both phoneNbr and itDirty properties
    * @param aPhoneNbr the new value of the phoneNbr property
    */
    public void setPhoneNbr(String aPhoneNbr)
    {
        phoneNbr = aPhoneNbr;
        setItDirty(true);
    }

   /**
    * Access method for the phoneCntryCd property.
    * @return   the current value of the phoneCntryCd property
    */
    public String getPhoneCntryCd()
    {
        return phoneCntryCd;
    }

   /**
    * Sets the value of both phoneCntryCd and itDirty properties
    * @param aPhoneCntryCd the new value of the phoneCntryCd property
    */
    public void setPhoneCntryCd(String aPhoneCntryCd)
    {
        phoneCntryCd = aPhoneCntryCd;
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
      voClass =  (( OrganizationHistDT) objectname1).getClass();
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

   /**
    * Implement base to return class type - currently CLASSTYPE_ACT or
    * CLASSTYPE_ENTITY
    *
    * @return String
    * @roseuid 3C73FD5C0343
    */
    public String getSuperclass(){
     return NEDSSConstants.CLASSTYPE_ENTITY;
    }

   /**
    * A getter for uid
    * @return Long
    * @roseuid 3C7407A80249
    */
    public Long getUid(){
      return organizationUid;
    }

}
