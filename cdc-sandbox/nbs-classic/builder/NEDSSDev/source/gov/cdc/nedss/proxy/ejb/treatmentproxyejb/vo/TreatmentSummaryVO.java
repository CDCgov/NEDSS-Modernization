/**
 * Title:       TreatmentSummaryVO helper class.
 * Description: A helper class for Treatment Summary VO
 * Copyright:   Copyright (c) 2001
 * Company:     Computer Sciences Corporation
 * @author      NEDSS Development Team
 * @version     1.1
 */

package gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;
import java.util.Collection;

import java.sql.*;
import java.util.*;


import gov.cdc.nedss.systemservice.util.*;

public class TreatmentSummaryVO extends AbstractVO implements RootDTInterface
{
   public String arTypeCd;

   /**
   * index
   * (Not for display purposes)
   */
   private String index;

   /**
   * from per.person_uid
   * (Not for display purposes)
   */
   private Long personUid;

   /**
    * from set in InvestigationProxyEJB with the isAssociated field
    */
   private String yesNoFlag;

   private Long nbsDocumentUid;
   public Long getNbsDocumentUid() {
	return nbsDocumentUid;
}

public void setNbsDocumentUid(Long nbsDocumentUid) {
	this.nbsDocumentUid = nbsDocumentUid;
}

/**
   * from treatment_administered
   */
   private String treatmentNameCode;

   /**
   * from treatment_administered
   */
   private String customTreatmentNameCode;


   /**
    * from SRT Code Set, based on material.nm
    */
   private String treatmentAdministered;

   /**
    * from Treatment.treatmentUid
    * (Not for display purposes)
    */
   private Long treatmentUid;
   private Long uid;


   /**
    * from Treatment.localId
    */
   private String localId;

    /**
    * from Treatment.activityFromTime
    */
   private Timestamp activityFromTime;

 /**
    * from Treatment.activityToTime
    */
   private Timestamp activityToTime;

    /**
    * from par.record_status_cd,
    */
   private String recordStatusCd;


   /**
    * from ar.target_act_uid PHC_uid
    * (Not for display purposes)
    */
   private Long phcUid;

   /**
       * from ar.target_act_uid parentUid
       * (Not for display purposes)
       */
      private Long parentUid;



   /**
    * MorbReportSummaryVO
    */
   private Collection<Object>  morbReportSummaryVOColl;

   /**
    * Populated by front-end to indicate if the isAssociated attribute may have been
    * changed by the user.
    * (not for display purposes)
    */
    private boolean isTouched;

   /**
    * Set by back-end and front-end to indicate if an ActRelationship entry exists or
    * should be created to support associating the Vaccination with the Investigation.
    * (not for display purposes)
    */
   private boolean isAssociated;

   private Character isRadioBtnAssociated;   // same as isAssociated but we need Character to be able to sort.

   private String actionLink;
   
   private String checkBoxId;
   
   private Timestamp createDate;
   
   private Map<Object,Object> associationMap;
   
   private String providerFirstName = "";
   public String getProviderFirstName() {
	return providerFirstName;
}

public void setProviderFirstName(String providerFirstName) {
	this.providerFirstName = providerFirstName;
}

public String getProviderLastName() {
	return providerLastName;
}

public void setProviderLastName(String providerLastName) {
	this.providerLastName = providerLastName;
}

public String getProviderSuffix() {
	return providerSuffix;
}

public void setProviderSuffix(String providerSuffix) {
	this.providerSuffix = providerSuffix;
}

public String getProviderPrefix() {
	return providerPrefix;
}

public void setProviderPrefix(String providerPrefix) {
	this.providerPrefix = providerPrefix;
}

private String providerLastName = "";
   private String providerSuffix = "";
   private String providerPrefix = "";
   private String degree="" ;
   
   public String getDegree() {
	return degree;
}

public void setDegree(String degree) {
	this.degree = degree;
}

public Timestamp getCreateDate() {
	return createDate;
}

public void setCreateDate(Timestamp createDate) {
	this.createDate = createDate;
}

public Map<Object, Object> getAssociationMap() {
	return associationMap;
}

public void setAssociationMap(Map<Object, Object> associationMap) {
	this.associationMap = associationMap;
}

public TreatmentSummaryVO()
   {
   }

    /**
    * Access method for the index property.
    * @return the current value of the index  property
    */
   public String getIndex()
   {
      return index;
   }

   /**
    * Sets the value of the index property.
    * @param aIndex the new value of the index property
    */
   public void setIndex(String aIndex)
   {
      index = aIndex;
   }

   /**
    * Access method for the personUid property.
    * @return   the current value of the personUid property
    */
   public Long getPersonUid()
   {
      return personUid;
   }

   /**
    * Sets the value of the personUid property.
    * @param aPersonUid the new value of the personUid property
    */
   public void setPersonUid(Long aPersonUid)
   {
      personUid = aPersonUid;
   }

    /**
    * Access method for the yesNoFlag property.
    * @return   the current value of the yesNoFlag property
    */
   public String getYesNoFlag()
   {
      return yesNoFlag;
   }

   /**
    * Sets the value of the yesNoFlag property.
    * @param aYesNoFlag the new value of the yesNoFlag property
    */
   public void setYesNoFlag(String aYesNoFlag)
   {
      yesNoFlag = aYesNoFlag;
   }

   /**
    * Access method for the vaccineNameCode property.
    * @return   the current value of the vaccineNameCode property
    */
   public String getTreatmentNameCode()
   {
      return treatmentNameCode;
   }

   /**
    * Sets the value of the vaccineNameCode property.
    * @param aVaccineNameCode the new value of the vaccineNameCode property
    */
   public void setTreatmentNameCode(String aTreatmentNameCode)
   {
      treatmentNameCode = aTreatmentNameCode;
   }

   /**
    * Access method for the vaccineNameCode property.
    * @return   the current value of the vaccineNameCode property
    */
   public String getCustomTreatmentNameCode()
   {
      return customTreatmentNameCode;
   }

   /**
    * Sets the value of the vaccineNameCode property.
    * @param aVaccineNameCode the new value of the vaccineNameCode property
    */
   public void setCustomTreatmentNameCode(String aCustomTreatmentNameCode)
   {
      customTreatmentNameCode = aCustomTreatmentNameCode;
   }

   /**
    * Access method for the vaccineAdministered property.
    * @return   the current value of the vaccineAdministered property
    */
   public String getTreatmentAdministered()
   {
      return treatmentAdministered;
   }

   /**
    * Sets the value of the vaccineAdministered property.
    * @param aVaccineAdministered the new value of the vaccineAdministered property
    */
   public void setTreatmentAdministered(String aTreatmentAdministered)
   {
      treatmentAdministered = aTreatmentAdministered;
   }

   /**
    * Access method for the treatmentUid property.
    * @return   the current value of the treatmentUid property
    */
   public Long getTreatmentUid()
   {
      return treatmentUid;
   }

   /**
    * Sets the value of the treatmentUid property.
    * @param aTreatmentUid the new value of the treatmentUid property
    */
   public void setTreatmentUid(Long aTreatmentUid)
   {
      treatmentUid = aTreatmentUid;
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
    * Sets the value of the localId property.
    * @param aLocalId the new value of the localId property
    */
   public void setLocalId(String aLocalId)
   {
      localId = aLocalId;
   }

    /**
    * Access method for the activityFromTime property.
    * @return   the current value of the activityFromTime property
    */
   public Timestamp getActivityFromTime()
   {
      return activityFromTime;
   }

   /**
    * Sets the value of the activityFromTime property.
    * @param aActivityFromTime the new value of the activityFromTime property
    */
   public void setActivityFromTime(Timestamp aActivityFromTime)
   {
      activityFromTime = aActivityFromTime;
   }

	  /**
    * Access method for the activityFromTime property.
    * @return   the current value of the activityFromTime property
    */
   public Timestamp getActivityToTime()
   {
      return activityToTime;
   }

   /**
    * Sets the value of the activityFromTime property.
    * @param aActivityFromTime the new value of the activityFromTime property
    */
   public void setActivityToTime(Timestamp aActivityToTime)
   {
      activityToTime = aActivityToTime;
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
    * Sets the value of the recordStatusCd property.
    * @param aRecordStatusCd the new value of the recordStatusCd property
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
   }

   /**
    * Access method for the arTypeCd property.
    * @return   the current value of the arTypeCd property
    */
   public String getArTypeCd()
   {
      return arTypeCd;
   }

   /**
    * Sets the value of the arTypeCd property.
    * @param aArTypeCd the new value of the arTypeCd property
    */
   public void setArTypeCd(String aArTypeCd)
   {
      arTypeCd = aArTypeCd;
   }


    /**
    * Access method for the phcUid property.
    * @return   the current value of the phcUid property
    */
   public Long getPhcUid()
   {
      return phcUid;
   }

   /**
    * Sets the value of the phcUid property.
    * @param aPhcUid the new value of the phcUid property
    */
   public void setPhcUid(Long aPhcUid)
   {
      phcUid = aPhcUid;
   }

   /**
    * Access method for the parentUid property.
    * @return   the current value of the parentUid property
    */
   public Long getParentUid()
   {
      return parentUid;
   }

   /**
    * Sets the value of the parentUid property.
    * @param aPhcUid the new value of the parentUid property
    */
   public void setParentUid(Long aParentUid)
   {
      parentUid = aParentUid;
   }


   /**
    * Access method for the morbReportSummaryVOColl
    * @return   the current value of the morbReportSummaryVOColl
    */
   public Collection<Object>  getMorbReportSummaryVOColl()
   {
      return morbReportSummaryVOColl;
   }

   /**
    * Sets the value of the phcUid property.
    * @param aPhcUid the new value of the phcUid property
    */
   public void setMorbReportSummaryVOColl(Collection<Object> aMorbReportSummaryVOColl)
   {
      morbReportSummaryVOColl = aMorbReportSummaryVOColl;
   }

   /**
    * Access method for the isTouched property.
    * @return   the current value of the isTouched property
    */
   public boolean getIsTouched()
   {
      return isTouched;
   }

   /**
    * Sets the value of the isTouched property.
    * @param aIsTouched the new value of the isTouched property
    */
   public void setIsTouched(boolean aIsTouched)
   {
      isTouched = aIsTouched;
   }

   /**
    * Access method for the isAssociated property.
    * @return   the current value of the isAssociated property
    */
   public boolean getIsAssociated()
   {
      return isAssociated;
   }

   /**
    * Sets the value of the isAssociated property.
    * @param aIsAssociated the new value of the isAssociated property
    */
   public void setIsAssociated(boolean aIsAssociated)
   {
      isAssociated = aIsAssociated;
   }

   /**
    * Logically same as set/getIsAssocaited but need Character to be able to sort VO arrays.
    * @param flag the new value in order to set isRadioBtnAssociated value
    */
   public void setIsRadioBtnAssociated(boolean flag) {
           if( flag == true )
              isRadioBtnAssociated = new Character('F');
           else
              isRadioBtnAssociated = new Character('T');
   }

   /**
    * Access method for the isRadioBtnAssociated property.
    * @return   the current value of the isRadioBtnAssociated property
    */
   public Character getIsRadioBtnAssociated() {
           return isRadioBtnAssociated;
   }

   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   public void setItDirty(boolean itDirty)
   {
   }

   public boolean isItDirty()
   {
    return true;
   }

   public void setItNew(boolean itNew)
   {
   }

   public boolean isItNew()
   {
    return true;
   }

   public void setItDelete(boolean itDelete)
   {
   }

   public boolean isItDelete()
   {
    return true;
   }



	   public String getStatusCd()
  {
    return null;
  }

  /**
   * Sets the value of the statusCd property.
   *
   * @param aStatusCd the new value of the statusCd property
   */
  public void setStatusCd(String aStatusCd)
  {
  }

     /**
      * Access method for the jurisdictionCd property.
      *
      * @return   the current value of the jurisdictionCd property
      */
     public String getJurisdictionCd()
     {
       return null;
     }


     /**
      * Sets the value of the jurisdictionCd property.
      *
      * @param aJurisdictionCd the new value of the jurisdictionCd property
      */
     public void setJurisdictionCd(String aJurisdictionCd)
     {
     }

     /**
      */
     public Integer getVersionCtrlNbr()
     {
        return null;
     }
     /**
    * @return java.lang.String
    */
   public String getSharedInd()
   {
    return null;
   }

   /**
    * @param aSharedInd
    */
   public void setSharedInd(String aSharedInd)
   {

   }
   /**
    * @return java.lang.Long
    */
   public Long getProgramJurisdictionOid()
   {
    return null;
   }

   /**
    * @param aProgramJurisdictionOid
    */
   public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
   {

   }
   /**
* A setter for add time
*/
public void setAddTime(java.sql.Timestamp aAddTime)
{

}

/**
* A getter for add time
*/
public Timestamp getAddTime()
{
 return null;
}
  /**
 * @return java.lang.Long
 */
public Long getUid()
{
 return uid;
}
 public void setUid(Long aUid)
{
  uid = aUid;
}
 /**
  * @return java.lang.String
  */
 public String getSuperclass()
 {
  return null;
 }
 /**
  * @param aStatusTime
  */
 public void setStatusTime(Timestamp aStatusTime)
 {

 }
 /**
  * @param aStatusTime
  */
 public Timestamp getStatusTime()
 {
   return null;
 }
 /**
  * @return java.sql.Timestamp
  */
 public Timestamp getRecordStatusTime()
 {
  return null;
 }

 /**
  * @param aRecordStatusTime
  */
 public void setRecordStatusTime(Timestamp aRecordStatusTime)
 {

 }

 /**
  * @return java.lang.String
  */
 public String getLastChgReasonCd()
 {
  return null;
 }

 /**
  * @param aLastChgReasonCd
  */
 public void setLastChgReasonCd(String aLastChgReasonCd)
 {

 }
 /**
  * @return java.lang.String
  */
 /**
  * @return java.lang.Long
  */
 public Long getAddUserId()
 {
  return null;
 }

 /**
  * @param aAddUserId
  */
 public void setAddUserId(Long aAddUserId)
 {

 }

 /**
* Access method for the lastChgTime property.
*
* @return   the current value of the lastChgTime property
*/
public Timestamp getLastChgTime()
{
return null;
}

/**
* Sets the value of the lastChgTime property.
*
* @param aLastChgTime the new value of the lastChgTime property
*/
public void setLastChgTime(Timestamp aLastChgTime)
{

}
 /**
  * @return java.lang.String
  */
 public String getProgAreaCd()
 {
    return null;
 }

 /**
  * @param aProgAreaCd
  */
 public void setProgAreaCd(String aProgAreaCd)
 {
 }


 public Long getLastChgUserId()
 {
  return null;
 }

 /**
  * @param aLastChgUserId
  */
 public void setLastChgUserId(Long aLastChgUserId)
 {

 }

public String getActionLink() {
	return actionLink;
}

public void setActionLink(String actionLink) {
	this.actionLink = actionLink;
}
public String getCheckBoxId() {
	if(isAssociated) {
		checkBoxId = "checked=\"true\"";	
	}
	return checkBoxId;
}

public void setCheckBoxId(String checkBoxId) {
	this.checkBoxId = checkBoxId;
}
}
