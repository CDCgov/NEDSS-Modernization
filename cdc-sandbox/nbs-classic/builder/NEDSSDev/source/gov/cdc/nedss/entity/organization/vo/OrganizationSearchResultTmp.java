package gov.cdc.nedss.entity.organization.vo;


import java.sql.*;
import java.util.*;

import gov.cdc.nedss.systemservice.util.*;

/**
 * <p>Title: OrganizationSearchResultTmp</p>
 * <p>Description: Class to hold temp. result of Organization search</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author not attributable
 * @version 1.0
 */

public class OrganizationSearchResultTmp implements RootDTInterface{

  private Long organizationUid;
  private String localId;
  private String name;
  private String recordStatusCd;
  private String nameUseCd;
  private String nameDesc;
  private Long locatorUid;
  private String classCd;
  private String locatorTypeCdDesc;
  private String locatorUseCd;
  private String locatorCd;
  private String locatorUseCdDesc;
  private String streetAddr1;
  private String streetAddr2;
  private String city;
  private String state;
  private String zip;
  private String telephoneNbr;
  private String extensionTxt;
  private String eiRootExtensionTxt;
  private String eiTypeDesc;
  private String eiTypeDescTxt;
  private String eiTypeCd;
  private String eiAssigningAuthorityCd;
  private String eiAssigningAuthorityDescTxt;
  private String cntyCd;
  private Integer versionCtrlNbr;
  public void setVersionCtrlNbr(Integer versionCtrlNbr) {
	this.versionCtrlNbr = versionCtrlNbr;
}

public OrganizationSearchResultTmp() { 

  }

  public Long getOrganizationUid(){
    return organizationUid;
  }
  public void setOrganizationUid(Long newOrganizationUid){
    organizationUid = newOrganizationUid;
  }
  public String getLocalId(){
    return localId;
  }
  public void setLocalId(String newLocalId){
    localId = newLocalId;
  }
  public String getName(){
    //System.out.println("this is from getLastName " + lastName);
    return name;
  }
  public void setName(String newName){
      //  System.out.println("this is from setLastName " + newLastName);
    name = newName;
  }
  public String getRecordStatusCd(){
    return recordStatusCd;
  }
  public void setRecordStatusCd(String newRecordStatusCd){
    recordStatusCd = newRecordStatusCd;
  }
  public String getNameUseCd(){
    return nameUseCd;
  }
  public void setNameUseCd(String newNameUseCd){
    nameUseCd = newNameUseCd;
  }
  public String getNameDesc(){
    return nameDesc;
  }
  public void setNameDesc(String newNameDesc){
    nameDesc = newNameDesc;
  }
  public Long getLocatorUid(){
    return locatorUid;
  }
  public void setLocatorUid(Long newLocatorUid){
    locatorUid = newLocatorUid;
  }
  public String getClassCd(){
    return classCd;
  }
  public void setClassCd(String newClassCd){
    classCd = newClassCd;
  }
  public String getLocatorTypeCdDesc(){
    return locatorTypeCdDesc;
  }
  public void setLocatorTypeCdDesc(String newLocatorTypeCdDesc){
    locatorTypeCdDesc = newLocatorTypeCdDesc;
  }
  public String getLocatorUseCd(){
    return locatorUseCd;
  }
  public void setLocatorUseCd(String newLocatorUseCd){
    locatorUseCd = newLocatorUseCd;
  }
  public String getLocatorCd(){
    return locatorCd;
  }
  public void setLocatorCd(String newLocatorCd){
    locatorCd = newLocatorCd;
  }
  public String getLocatorUseCdDesc(){
    return locatorUseCdDesc;
  }
  public void setLocatorUseCdDesc(String newLocatorUseCdDesc){
    locatorUseCdDesc = newLocatorUseCdDesc;
  }
  public String getStreetAddr1(){
    return streetAddr1;
  }
  public void setStreetAddr1(String newStreetAddr1){
    streetAddr1 = newStreetAddr1;
  }
  public String getStreetAddr2(){
    return streetAddr2;
  }
  public void setStreetAddr2(String newStreetAddr2){
    streetAddr2 = newStreetAddr2;
  }

  public String getCity(){
    return city;
  }
  public void setCity(String newCity){
    city = newCity;
  }
  public String getZip(){
    return zip;
  }
  public void setZip(String newZip){
    zip = newZip;
  }
  public String getState(){
    return state;
  }
  public void setState(String newState){
    state = newState;
  }
  public String getTelephoneNbr(){
    return telephoneNbr;
  }
  public void setTelephoneNbr(String newTelephoneNbr){
    telephoneNbr = newTelephoneNbr;
  }
  public String getExtensionTxt(){
    return extensionTxt;
  }
  public void setExtensionTxt(String newExtensionTxt){
    extensionTxt = newExtensionTxt;
  }

  public String getEiRootExtensioTxt(){
    return eiRootExtensionTxt;
  }
  public void setEiRootExtensionTxt(String newEiRootExtensionTxt){
    eiRootExtensionTxt = newEiRootExtensionTxt;
  }
  public String getEiTypeDesc(){
    return eiTypeDesc;
  }
  public void setEiTypeDesc(String newEiTypeDesc){
    eiTypeDesc = newEiTypeDesc;
  }

  public String getEiTypeDescTxt(){
    return eiTypeDescTxt;
  }
  public void setEiTypeDescTxt(String newEiTypeDescTxt){
    eiTypeDescTxt = newEiTypeDescTxt;
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
      return versionCtrlNbr;
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
  public String getCntyCd() {
    return cntyCd;
  }
  public void setCntyCd(String cntyCd) {
    this.cntyCd = cntyCd;
  }

public String getEiTypeCd() {
	return eiTypeCd;
}

public void setEiTypeCd(String eiTypeCd) {
	this.eiTypeCd = eiTypeCd;
}

public String getEiAssigningAuthorityCd() {
	return eiAssigningAuthorityCd;
}

public void setEiAssigningAuthorityCd(String eiAssigningAuthorityCd) {
	this.eiAssigningAuthorityCd = eiAssigningAuthorityCd;
}

public String getEiAssigningAuthorityDescTxt() {
	return eiAssigningAuthorityDescTxt;
}

public void setEiAssigningAuthorityDescTxt(String eiAssigningAuthorityDescTxt) {
	this.eiAssigningAuthorityDescTxt = eiAssigningAuthorityDescTxt;
}

public String getEiRootExtensionTxt() {
	return eiRootExtensionTxt;
}


}