package gov.cdc.nedss.entity.observation.vo;


import java.sql.*;
import gov.cdc.nedss.systemservice.util.*;
/**c
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ObservationSearchResultTmp implements RootDTInterface{

	// Need for the new observation search  
	private String laboratoryId;
	private String labTestCd;
	private String labTestDescription;
	private String labConditionCd;
	private String labOrganismIndicator;
	private Integer nbsUid;

	public Integer getNbsUid(){
		return nbsUid;
	}

	public void setNbsUid(Integer nbsUid){
		this.nbsUid = nbsUid;
	}
	
	
	public String getLaboratoryId(){
		return laboratoryId;
	}

	public void setLaboratoryId(String laboratoryId){
		this.laboratoryId = laboratoryId;
	}

	public String getLabTestCd(){
		return labTestCd;
	}

	public void setLabTestCd(String labTestCd){
		this.labTestCd = labTestCd;
	}

	public String getLabTestDescription(){
		return labTestDescription;
	}

	public void setLabTestDescription(String labTestDescription){
		this.labTestDescription = labTestDescription;
	}

	public String getLabConditionCd(){
		return labConditionCd;
	}

	public void setLabConditionCd(String labConditionCd){
		this.labConditionCd = labConditionCd;
	}

	public String getLabOrganismIndicator(){
		return this.labOrganismIndicator;
	}

	public void setLabOrganismIndicator(String labOrganismIndicator){
		this.labOrganismIndicator = labOrganismIndicator;
	}



  private String localId;
  private String recordStatusCd;

   public String getLocalId(){
	return localId;
  }


  public void setLocalId(String localId){
	this.localId = localId;
  }

   public String getRecordStatusCd(){
    return recordStatusCd;
  }
  public void setRecordStatusCd(String newRecordStatusCd){
    recordStatusCd = newRecordStatusCd;
  }
  
 /* private Long personUid;
  private String lastName;
  private String firstName;
  private String nameUseCd;
  private String dob;
  private String currentSex;


  


  public ObservationSearchResultTmp() {
  }
  
  public Long getPersonUid(){
    return personUid;
  }
  public void setPersonUid(Long newPersonUid){
    personUid = newPersonUid;
  }

  public String getLastName(){
    return lastName;
  }
  public void setLastName(String newLastName){
    lastName = newLastName;
  }
  public String getFirstName(){
    return firstName;
  }
  public void setFirstName(String newFirstName){
    firstName = newFirstName;
  }
  public String getNameUseCd(){
    return nameUseCd;
  }
  public void setNameUseCd(String newNameUseCd){
    nameUseCd = newNameUseCd;
  }
  public String getDob(){
    return dob;
  }
  public void setDob(String newDob){
    dob = newDob;
  }
  public String getCurrentSex(){
    return currentSex;
  }
  public void setCurrentSex(String newCurrentSex){
    currentSex = newCurrentSex;
  }

*/
 

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItDirty()
   {
    return false;
   }

   /**
    * @param itNew
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItNew()
   {
    return false;
   }

   /**
    * @param itDelete
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    */
   public boolean isItDelete()
   {
    return false;
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

}