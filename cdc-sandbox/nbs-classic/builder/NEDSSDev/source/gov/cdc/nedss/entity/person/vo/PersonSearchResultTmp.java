package gov.cdc.nedss.entity.person.vo;


import java.sql.*;
import java.util.*;

import gov.cdc.nedss.systemservice.util.*;
/**c
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class PersonSearchResultTmp implements RootDTInterface{

  private String dataType;
  private Long personParentUid;
  private Long personUid;
  private String localId;
  private String electronicInd;
  private Long observationUid;
  private String lastName;
  private String firstName;
  private String recordStatusCd;
  private String nameUseCd;
  private String nameDesc;
  private String nmDegree;
  private String dob;
  private String currentSex;
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
  private String cntryCd;
  private String zip;
  private String telephoneNbr;
  private String extensionTxt;
  private String emailAddress;
  private String eiRootExtensionTxt;
  private String eiTypeDesc;
  private String eiTypeCd;
  private String eiAssigningAuthorityCd;
  private String eiAssigningAuthorityDescTxt;
  private String nmSuffix;
  private String cntyCd;
  private String ethnicGroupInd;
  private String nmMiddle;
  private String raceCd;
  private String asOfDateAdmin;
  private String ageReported;
  private String ageUnit;
  private Timestamp deceasedTime;
  private Integer versionCtrlNbr;
  private Long publicHealthCaseUid;
  private String startDate;
  private String jurisdictionCd;
  private String progAreaCd;
  private String pregnancyIndCd;
  private String conditionCodeText;
  private String caseClassCd;
  private String investigationStatusCd;
  private String outbreakName;
  private String currProcessStateCd;
  private String ssn;
  private String maritalStatusCd;
  private String condition;
  private String investigatorFirstName;
  private String patientLastName;
  private String patientFirstName;
  private Long MPRUid;
  private String jurisdictionDescTxt;

  private String statusCd;
  private String notifRecordStatusCd;
  private Timestamp DateReceived;
  private Timestamp birthTime;
  private String personLocalID;
  
  private String investigatorLastName;
  private String investigatorLocalId;
  private String investigationStatusDescTxt;
  private Timestamp activityFromTime;
  private String cd;
  private String caseClassCodeTxt;
  
  private String programArea;


  public String getPersonLocalID() {
	return personLocalID;
  }
  public void setPersonLocalID(String personLocalID) {
	this.personLocalID = personLocalID;
  }

  public Timestamp getBirthTime() {
	return birthTime;
  }
  public void setBirthTime(Timestamp birthTime) {
	this.birthTime = birthTime;
  }


  public String getProgramArea() {
	return programArea;
}
public void setProgramArea(String programArea) {
	this.programArea = programArea;
}
public PersonSearchResultTmp() {
  }
  public String getDataType()
  {
	  return dataType;
  }

  public void setDataType(String aDataType)
  {
	  dataType = aDataType;
  }

 public String getAgeUnit()
{
  return ageUnit;
}
public void setAgeUnit(String aAgeUnit){
  ageUnit = aAgeUnit;
}


  public String getAsOfDateAdmin()
{
  return asOfDateAdmin;
}
public void setAsOfDateAdmin(String aAsOfDateAdmin){
  asOfDateAdmin = aAsOfDateAdmin;
}
public String getAgeReported()
{
  return ageReported;
}
public void setAgeReported(String newAgeReported)
{
  ageReported = newAgeReported;
}


public String getSsn()
{
  return ssn;
}
public void setSsn(String newSsn){
ssn = newSsn;
}

public String getMaritalStatusCd()
{

 return maritalStatusCd;
}

public void setMaritalStatusCd(String newMaritalStatusCd) {
 maritalStatusCd = newMaritalStatusCd;
}

  public String getCntyCd()
  {
        //System.out.println("this is from getcntyCd " + cntyCd);
    return cntyCd;
  }
  public void setCntyCd(String aCntyCd){
    //System.out.println("\n\n this is from setCntyCd " + aCntyCd);
    cntyCd = aCntyCd;
}
  public String getEthnicGroupInd()
  {
      //  System.out.println("this is from getEthnicGroupInd " + ethnicGroupInd);
    return ethnicGroupInd;
  }
  public void setEthnicGroupInd(String newEthnicGroupInd){
    //System.out.println("this is from setEthnicGroupInd " + newEthnicGroupInd);
    ethnicGroupInd = newEthnicGroupInd;
}

public String getNmSuffix()
{
      //System.out.println("this is from getNmSuffix " + nmSuffix);
  return nmSuffix;
}
public void setNmSuffix(String newNmSuffix){
  //System.out.println("this is from setNmSuffix " + newNmSuffix);
nmSuffix = newNmSuffix;
}

public String getRaceCd()
{

    return raceCd;
}
public void setRaceCd(String newRaceCd){
//System.out.println("\n\n this is from setNmMiddle " + newNmMiddle);
raceCd = newRaceCd;
}




 public String getNmMiddle() {

  return nmMiddle;
 }

 public void setNmMiddle(String newNmMiddle) {
  //System.out.println("\n\n this is from setNmMiddle " + newNmMiddle);
  nmMiddle = newNmMiddle;
 }

  public Long getPersonParentUid(){
    return personParentUid;
  }
  public void setPersonParentUid(Long newPersonParentUid){
    personParentUid = newPersonParentUid;
  }
  public Long getPersonUid(){
    return personUid;
  }
  public void setPersonUid(Long newPersonUid){
    personUid = newPersonUid;
  }
  public String getLocalId(){
    return localId;
  }
  public void setLocalId(String newLocalId){
    localId = newLocalId;
  }
  public String getLastName(){
    //System.out.println("this is from getLastName " + lastName);
    return lastName;
  }
  public void setLastName(String newLastName){
      //  System.out.println("this is from setLastName " + newLastName);
    lastName = newLastName;
  }
  public String getFirstName(){

    //System.out.println("this is from getFirstName " + firstName);
    return firstName;
  }
  public void setFirstName(String newFirstName){
    firstName = newFirstName;
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
  public String getCntryCd(){
	return cntryCd;
  }
  public void setCntryCd(String newCntryCd){
	cntryCd = newCntryCd;
  }
  public String getTelephoneNbr(){
    return telephoneNbr;
  }
  public void setTelephoneNbr(String newTelephoneNbr){
    telephoneNbr = newTelephoneNbr;
  }
  public String getNmDegree(){
   return nmDegree;
 }
 public void setNmDegree(String newNmDegree){
   nmDegree = newNmDegree;
 }

  public String getExtensionTxt(){
    return extensionTxt;
  }
  public void setExtensionTxt(String newExtensionTxt){
    extensionTxt = newExtensionTxt;
  }

  public String getEmailAddress(){
    return emailAddress;
  }
  public void setEmailAddress(String newEmailAddress){
    emailAddress = newEmailAddress;
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
  public String getStreetAddr2() {
       return streetAddr2;
  }
  public void setStreetAddr2(String streetAddr2) {
       this.streetAddr2 = streetAddr2;
  }

  public Timestamp getDeceasedTime() {

      return deceasedTime;
  }

  /**
   *
   * @param aDeceasedTime
   */
  public void setDeceasedTime(Timestamp aDeceasedTime) {
      this.deceasedTime = aDeceasedTime;
  }

  public Integer getVersionCtrlNbr() {

      return versionCtrlNbr;
  }

  /**
   *
   * @param aVersionCtrlNbr
   */
  public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
      versionCtrlNbr = aVersionCtrlNbr;
  }
  /**
   * Getter for raceDescTxt
   * @return  raceDescTxt String
   */

  public String getRaceDescTxt() {
    return raceDescTxt;
  }
  /**
   * Setter for raceDescTxt
   * @param raceDescTxt String
   */
  public void setRaceDescTxt(String raceDescTxt) {
    this.raceDescTxt = raceDescTxt;
  }

   /**
    * An inner class
    */
   public static Comparator PatientSearchComparator = new Comparator()
   {
    public int compare(Object personSearchResultTmp1, Object personSearchResultTmp2)
    {
      if (!(personSearchResultTmp1 instanceof PersonSearchResultTmp)
          || !(personSearchResultTmp2 instanceof PersonSearchResultTmp))
      {
        throw new ClassCastException("Error: a PersonSearchResultTmp object expected.");
      }

      String lastName1 = ((PersonSearchResultTmp)personSearchResultTmp1).getLastName();
      lastName1 = lastName1 != null ? lastName1:"";
      lastName1=lastName1.toUpperCase();

      String firstName1 = ((PersonSearchResultTmp)personSearchResultTmp1).getFirstName();
      firstName1 = firstName1 !=null? firstName1:"";
      firstName1=firstName1.toUpperCase();

      String birthTimeCalc1 = ((PersonSearchResultTmp)personSearchResultTmp1).getDob();
      birthTimeCalc1 = birthTimeCalc1 !=null? birthTimeCalc1:"";
      birthTimeCalc1=birthTimeCalc1.toUpperCase();

      String lastName2 = ((PersonSearchResultTmp) personSearchResultTmp2).getLastName();
      lastName2 = lastName2 !=null? lastName2:"";
      lastName2=lastName2.toUpperCase();

      String firstName2 = ((PersonSearchResultTmp) personSearchResultTmp2).getFirstName();
      firstName2 = firstName2!=null? firstName2:"";
      firstName2=firstName2.toUpperCase();

      String birthTimeCalc2 = ((PersonSearchResultTmp)personSearchResultTmp2).getDob();
      birthTimeCalc2 = birthTimeCalc2!=null? birthTimeCalc2:"";
      birthTimeCalc2=birthTimeCalc2.toUpperCase();

      if (!(lastName1.equals(lastName2)))
        return lastName1.compareTo(lastName2);
      else
      {
        if (! (firstName1.equals(firstName2)))
          return firstName1.compareTo(firstName2);
        else
          return birthTimeCalc2.compareTo(birthTimeCalc1);
      }
    }
  };
  private String raceDescTxt;

public String getCondition() {
	return condition;
}
public void setCondition(String condition) {
	this.condition = condition;
}
public Long getPublicHealthCaseUid() {
	return publicHealthCaseUid;
}
public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
	this.publicHealthCaseUid = publicHealthCaseUid;
}

public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getConditionCodeText() {
	return conditionCodeText;
}
public void setConditionCodeText(String conditionCodeText) {
	this.conditionCodeText = conditionCodeText;
}
public String getCaseClassCd() {
	return caseClassCd;
}
public void setCaseClassCd(String caseClassCd) {
	this.caseClassCd = caseClassCd;
}
  
public String getProgAreaCd() {
	return progAreaCd;
}
public void setProgAreaCd(String progAreaCd) {
	this.progAreaCd = progAreaCd;
}
public String getJurisdictionCd() {
	return jurisdictionCd;
}
public void setJurisdictionCd(String jurisdictionCd) {
	this.jurisdictionCd = jurisdictionCd;
}
public String getPregnancyIndCd() {
	return pregnancyIndCd;
}
public void setPregnancyIndCd(String pregnancyIndCd) {
	this.pregnancyIndCd = pregnancyIndCd;
}
public String getInvestigationStatusCd() {
	return investigationStatusCd;
}
public void setInvestigationStatusCd(String investigationStatusCd) {
	this.investigationStatusCd = investigationStatusCd;
}
public String getOutbreakName() {
	return outbreakName;
}
public void setOutbreakName(String outbreakName) {
	this.outbreakName = outbreakName;
}
public String getCurrProcessStateCd() {
	return currProcessStateCd;
}
public void setCurrProcessStateCd(String currProcessStateCd) {
	this.currProcessStateCd = currProcessStateCd;
}
public String getPatientLastName() {
	return patientLastName;
}
public void setPatientLastName(String patientLastName) {
	this.patientLastName = patientLastName;
}
public String getPatientFirstName() {
	return patientFirstName;
}
public void setPatientFirstName(String patientFirstName) {
	this.patientFirstName = patientFirstName;
}
public Long getMPRUid() {
	return MPRUid;
}
public void setMPRUid(Long mPRUid) {
	MPRUid = mPRUid;
}
public String getJurisdictionDescTxt() {
	return jurisdictionDescTxt;
}
public void setJurisdictionDescTxt(String jurisdictionDescTxt) {
	this.jurisdictionDescTxt = jurisdictionDescTxt;
}
public String getNotifRecordStatusCd() {
	return notifRecordStatusCd;
}
public void setNotifRecordStatusCd(String notifRecordStatusCd) {
	this.notifRecordStatusCd = notifRecordStatusCd;
}
public String getStatusCd() {
	return statusCd;
}
public void setStatusCd(String statusCd) {
	this.statusCd = statusCd;
}
public String getInvestigatorFirstName() {
	return investigatorFirstName;
}
public void setInvestigatorFirstName(String investigatorFirstName) {
	this.investigatorFirstName = investigatorFirstName;
}
public String getInvestigatorLastName() {
	return investigatorLastName;
}
public void setInvestigatorLastName(String investigatorLastName) {
	this.investigatorLastName = investigatorLastName;
}
public String getInvestigatorLocalId() {
	return investigatorLocalId;
}
public void setInvestigatorLocalId(String investigatorLocalId) {
	this.investigatorLocalId = investigatorLocalId;
}
public String getInvestigationStatusDescTxt() {
	return investigationStatusDescTxt;
}
public void setInvestigationStatusDescTxt(String investigationStatusDescTxt) {
	this.investigationStatusDescTxt = investigationStatusDescTxt;
}
public Timestamp getActivityFromTime() {
	return activityFromTime;
}
public void setActivityFromTime(Timestamp activityFromTime) {
	this.activityFromTime = activityFromTime;
}
public String getCd() {
	return cd;
}
public void setCd(String cd) {
	this.cd = cd;
}
public String getCaseClassCodeTxt() {
	return caseClassCodeTxt;
}
public void setCaseClassCodeTxt(String caseClassCodeTxt) {
	this.caseClassCodeTxt = caseClassCodeTxt;
}
public Long getObservationUid() {
	return observationUid;
}
public void setObservationUid(Long observationUid) {
	this.observationUid = observationUid;
}
public Timestamp getDateReceived() {
	return DateReceived;
}
public void setDateReceived(Timestamp dateReceived) {
	DateReceived = dateReceived;
}
public String getElectronicInd() {
	return electronicInd;
}
public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
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