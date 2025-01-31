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

public class LoincSearchResultTmp implements RootDTInterface{

	public LoincSearchResultTmp() {

	}

	// Need for the new observation search
	private String loincCd;
	private String loincComponentName;
	private String loincMethod;
	private String loincSystem;
	private String loincProperty;

        private String relatedClassCd;

        public String getRelatedClassCd() {
          return relatedClassCd;
        }

        public void setRelatedClassCd(String relatedClassCd) {
          this.relatedClassCd = relatedClassCd;
        }


	public String getLoincCd(){
		return loincCd;
	}

	public void setLoincCd(String loincCd){
		this.loincCd = loincCd;
	}

	public String getLoincComponentName(){
		return loincComponentName;
	}

	public void setLoincComponentName(String loincComponentName){
		this.loincComponentName = loincComponentName;
	}

	public String getLoincMethod(){
		return loincMethod;
	}

	public void setLoincMethod(String loincMethod){
		this.loincMethod = loincMethod;
	}

	public String getLoincSystem(){
		return loincSystem;
	}

	public void setLoincSystem(String loincSystem){
		this.loincSystem = loincSystem;
	}

	public String getLoincProperty(){
		return loincProperty;
	}

	public void setLoincProperty(String loincProperty){
		this.loincProperty = loincProperty;
	}

	/*
		these all had to be implemented for teh RootDTInterface.
	*/
	private String localId;
	private String	recordStatusCd;
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