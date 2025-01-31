/**
 * Title: NonPersonLivingSubjectHistDT helper class.
 * Description: A helper class for non-person living subject history data table
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.nonpersonlivingsubject.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

public class NonPersonLivingSubjectHistDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
    private Long nonPersonUid;
    private Integer versionCtrlNbr;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String birthSexCd;
    private Integer birthOrderNbr;
    private Timestamp birthTime;
    private String breedCd;
    private String breedDescTxt;
    private String cd;
    private String cdDescTxt;
    private String deceasedIndCd;
    private Timestamp deceasedTime;
    private String description;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String multipleBirthInd;
    private String nm;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private String taxonomicClassificationCd;
    private String taxonomicClassificationDesc;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

   /**
    * Access method for the nonPersonUid property.
    * @return   the current value of the nonPersonUid property
    */
    public Long getNonPersonUid()
    {
        return nonPersonUid;
    }

   /**
    * Sets the value of both nonPersonUid and itDirty properties
    * @param aNonPersonUid the new value of the nonPersonUid property
    */
    public void setNonPersonUid(Long aNonPersonUid)
    {
        nonPersonUid = aNonPersonUid;
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
    * Access method for the birthSexCd property.
    * @return   the current value of the birthSexCd property
    */
    public String getBirthSexCd()
    {
        return birthSexCd;
    }

   /**
    * Sets the value of both birthSexCd and itDirty properties
    * @param aBirthSexCd the new value of the birthSexCd property
    */
    public void setBirthSexCd(String aBirthSexCd)
    {
        birthSexCd = aBirthSexCd;
        setItDirty(true);
    }

   /**
    * Access method for the birthOrderNbr property.
    * @return   the current value of the birthOrderNbr property
    */
    public Integer getBirthOrderNbr()
    {
        return birthOrderNbr;
    }

   /**
    * Sets the value of both birthOrderNbr and itDirty properties
    * @param aBirthOrderNbr the new value of the birthOrderNbr property
    */
    public void setBirthOrderNbr(Integer aBirthOrderNbr)
    {
        birthOrderNbr = aBirthOrderNbr;
        setItDirty(true);
    }

   /**
    * Access method for the birthTime property.
    * @return   the current value of the birthTime property
    */
    public Timestamp getBirthTime()
    {
        return birthTime;
    }

   /**
    * Sets the value of both birthTime and itDirty properties
    * @param aBirthTime the new value of the birthTime property
    */
    public void setBirthTime(Timestamp aBirthTime)
    {
        birthTime = aBirthTime;
        setItDirty(true);
    }

   /**
    * Sets the value of both birthTime and itDirty properties
    * @param strTime the new value of the birthTime property
    */
    public void setBirthTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setBirthTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

   /**
    * Access method for the breedCd property.
    * @return   the current value of the breedCd property
    */
    public String getBreedCd()
    {
        return breedCd;
    }

   /**
    * Sets the value of both breedCd and itDirty properties
    * @param aBreedCd the new value of the breedCd property
    */
    public void setBreedCd(String aBreedCd)
    {
        breedCd = aBreedCd;
        setItDirty(true);
    }

   /**
    * Access method for the breedDescTxt property.
    * @return   the current value of the breedDescTxt property
    */
    public String getBreedDescTxt()
    {
        return breedDescTxt;
    }

   /**
    * Sets the value of both breedDescTxt and itDirty properties
    * @param aBreedDescTxt the new value of the breedDescTxt property
    */
    public void setBreedDescTxt(String aBreedDescTxt)
    {
        breedDescTxt = aBreedDescTxt;
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
    * Access method for the deceasedIndCd property.
    * @return   the current value of the deceasedIndCd property
    */
    public String getDeceasedIndCd()
    {
        return deceasedIndCd;
    }

   /**
    * Sets the value of both deceasedIndCd and itDirty properties
    * @param aDeceasedIndCd the new value of the deceasedIndCd property
    */
    public void setDeceasedIndCd(String aDeceasedIndCd)
    {
        deceasedIndCd = aDeceasedIndCd;
        setItDirty(true);
    }

   /**
    * Access method for the deceasedTime property.
    * @return   the current value of the deceasedTime property
    */
    public Timestamp getDeceasedTime()
    {
        return deceasedTime;
    }

   /**
    * Sets the value of both deceasedTime and itDirty properties
    * @param aDeceasedTime the new value of the deceasedTime property
    */
    public void setDeceasedTime(Timestamp aDeceasedTime)
    {
        deceasedTime = aDeceasedTime;
        setItDirty(true);
    }

   /**
    * Sets the value of both deceasedTime and itDirty properties
    * @param strTime the new value of the deceasedTime property
    */
    public void setDeceasedTime_s(String strTime)
    {
      if (strTime == null) return;
      this.setDeceasedTime(StringUtils.stringToStrutsTimestamp(strTime));
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
    * Access method for the multipleBirthInd property.
    * @return   the current value of the multipleBirthInd property
    */
    public String getMultipleBirthInd()
    {
        return multipleBirthInd;
    }

   /**
    * Sets the value of both multipleBirthInd and itDirty properties
    * @param aMultipleBirthInd the new value of the multipleBirthInd property
    */
    public void setMultipleBirthInd(String aMultipleBirthInd)
    {
        multipleBirthInd = aMultipleBirthInd;
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
    * Access method for the taxonomicClassificationCd property.
    * @return   the current value of the taxonomicClassificationCd property
    */
    public String getTaxonomicClassificationCd()
    {
        return taxonomicClassificationCd;
    }

   /**
    * Sets the value of both taxonomicClassificationCd and itDirty properties
    * @param aTaxonomicClassificationCd the new value of the taxonomicClassificationCd property
    */
    public void setTaxonomicClassificationCd(String aTaxonomicClassificationCd)
    {
        taxonomicClassificationCd = aTaxonomicClassificationCd;
        setItDirty(true);
    }

   /**
    * Access method for the taxonomicClassificationDesc property.
    * @return   the current value of the taxonomicClassificationDesc property
    */
    public String getTaxonomicClassificationDesc()
    {
        return taxonomicClassificationDesc;
    }

   /**
    * Sets the value of both taxonomicClassificationDesc and itDirty properties
    * @param aTaxonomicClassificationDesc the new value of the taxonomicClassificationDesc property
    */
    public void setTaxonomicClassificationDesc(String aTaxonomicClassificationDesc)
    {
        taxonomicClassificationDesc = aTaxonomicClassificationDesc;
        setItDirty(true);
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
      voClass =  (( NonPersonLivingSubjectHistDT) objectname1).getClass();
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
