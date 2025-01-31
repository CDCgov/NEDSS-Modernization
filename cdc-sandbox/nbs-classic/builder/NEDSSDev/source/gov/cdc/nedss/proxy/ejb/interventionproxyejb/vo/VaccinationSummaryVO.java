/**
 * Title: VaccinationSummaryVO helper class.
 * Description: A helper class for Vaccination Summary VO
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo;

import  gov.cdc.nedss.util.*;

import java.sql.Timestamp;
import java.util.Map;

import gov.cdc.nedss.systemservice.util.*;

public class VaccinationSummaryVO extends AbstractVO implements RootDTInterface
{
   public String arTypeCode = "1180";

   private String actionLink;
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

   /**
   * from Material.nm
   */
   private String vaccineNameCode;

   /**
    * from SRT Code Set, based on material.nm
    */
   private String vaccineAdministered;

   /**
    * from Intervention.interventionUid
    * (Not for display purposes)
    */
   private Long interventionUid;

   /**
    * from par2.type_cd
    * (Not for display purposes)
    */
   private String par2TypeCd;

   /**
    * from Intervention.localId
    */
   private String localId;

    /**
    * from Intervention.activityFromTime
    */
   private Timestamp activityFromTime;

    /**
    * from par.record_status_cd,
    */
   private String recordStatusCd;

   /**
    * from par.type_cd
    * (Not for display purposes)
    */
   private String parTypeCd;

   /**
    * from mt.material_uid
    * (Not for display purposes)
    */
   private Long materialUid;

   /**
    * from ar.target_act_uid PHC_uid
    * (Not for display purposes)
    */
   private Long phcUid;

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
   
   private String checkBoxId;
   
   private Timestamp createDate;
   
   private Map<Object,Object> associationsMap;
   
   private String providerFirstName = "";
   
   private String degree="" ;
   
   private Timestamp effectiveFromTime;
   
   private String electronicInd;
   
   public String getDegree() {
	return degree;
}

public void setDegree(String degree) {
	this.degree = degree;
}

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


   public Map<Object, Object> getAssociationsMap() {
	return associationsMap;
}

public void setAssociationsMap(Map<Object, Object> associationsMap) {
	this.associationsMap = associationsMap;
}

public Timestamp getCreateDate() {
	return createDate;
}

public void setCreateDate(Timestamp createDate) {
	this.createDate = createDate;
}

public VaccinationSummaryVO()
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
   public String getVaccineNameCode()
   {
      return vaccineNameCode;
   }

   /**
    * Sets the value of the vaccineNameCode property.
    * @param aVaccineNameCode the new value of the vaccineNameCode property
    */
   public void setVaccineNameCode(String aVaccineNameCode)
   {
      vaccineNameCode = aVaccineNameCode;
   }


   /**
    * Access method for the vaccineAdministered property.
    * @return   the current value of the vaccineAdministered property
    */
   public String getVaccineAdministered()
   {
      return vaccineAdministered;
   }

   /**
    * Sets the value of the vaccineAdministered property.
    * @param aVaccineAdministered the new value of the vaccineAdministered property
    */
   public void setVaccineAdministered(String aVaccineAdministered)
   {
      vaccineAdministered = aVaccineAdministered;
   }

   /**
    * Access method for the interventionUid property.
    * @return   the current value of the interventionUid property
    */
   public Long getInterventionUid()
   {
      return interventionUid;
   }

   /**
    * Sets the value of the interventionUid property.
    * @param aInterventionUid the new value of the interventionUid property
    */
   public void setInterventionUid(Long aInterventionUid)
   {
      interventionUid = aInterventionUid;
   }

   /**
    * Access method for the par2TypeCd property.
    * @return   the current value of the par2TypeCd property
    */
   public String getPar2TypeCd()
   {
      return par2TypeCd;
   }

   /**
    * Sets the value of the par2TypeCd property.
    * @param aPar2TypeCd the new value of the par2TypeCd property
    */
   public void setPar2TypeCd(String aPar2TypeCd)
   {
      par2TypeCd = aPar2TypeCd;
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
    * Access method for the parTypeCd property.
    * @return   the current value of the parTypeCd property
    */
   public String getParTypeCd()
   {
      return parTypeCd;
   }

   /**
    * Sets the value of the parTypeCd property.
    * @param aParTypeCd the new value of the parTypeCd property
    */
   public void setParTypeCd(String aParTypeCd)
   {
      par2TypeCd = aParTypeCd;
   }

   /**
    * Access method for the materialUid property.
    * @return   the current value of the materialUid property
    */
   public Long getMaterialUid()
   {
      return materialUid;
   }

   /**
    * Sets the value of the materialUid property.
    * @param aMaterialUid the new value of the materialUid property
    */
   public void setMaterialUid(Long aMaterialUid)
   {
      materialUid = aMaterialUid;
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
   /**
 * @return java.lang.Long
 */
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

/**
 * @return java.lang.String
 */
public String getJurisdictionCd()
{
 return null;
}

/**
 * @param aJurisdictionCd
 */
public void setJurisdictionCd(String aJurisdictionCd)
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
public String getStatusCd()
{
 return null;
}

/**
 * @param aStatusCd
 */
public void setStatusCd(String aStatusCd)
{

}

/**
 * @return java.sql.Timestamp
 */
public Timestamp getStatusTime()
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
 * @return java.lang.String
 */
public String getSuperclass()
{
 return null;
}

/**
 * @return java.lang.Long
 */
public Long getUid()
{
 return null;
}
 public void setUid(Long aUid)
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
 */
public Integer getVersionCtrlNbr()
{
   return null;
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

public Timestamp getEffectiveFromTime() {
	return effectiveFromTime;
}

public void setEffectiveFromTime(Timestamp effectiveFromTime) {
	this.effectiveFromTime = effectiveFromTime;
}

public String getElectronicInd() {
	return electronicInd;
}

public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
}


}
