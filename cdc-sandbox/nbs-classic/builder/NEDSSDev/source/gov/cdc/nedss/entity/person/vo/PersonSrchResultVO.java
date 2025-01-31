/**
 * Title: PersonSrchResultVO helper class.
 * Description: A helper class for person search result value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

 package gov.cdc.nedss.entity.person.vo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;

public class PersonSrchResultVO implements Serializable {

  //static final long serialVersionUID  = 3540564138932448904L;

  public PersonSrchResultVO() {
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }


  public void setRaceCdColl(java.util.Collection<Object>  raceCdColl){
    this.raceCdColl = raceCdColl;
  }

  public Collection<Object>  getRaceCdColl(){
     return raceCdColl;
  }



  /**
    * Set method for the thePersonNameColl property.
    * Sets all the associated names for a person
    * @param personNameColl the new value of the thePersonNameColl property
    * @void Sets the current value of the thePersonNameColl property
    */
  public void setPersonNameColl(java.util.Collection<Object>  personNameColl) {
    this.personNameColl = personNameColl;
  }

  /**
    * Access method for the thePersonNameColl property.
    * returns all the associated names for a person
    * @return the current value of the thePersonNameColl property
    */
  public java.util.Collection<Object>  getPersonNameColl() {
    return personNameColl;
  }

  /**
    * Set method for the thePersonLocatorColl property.
    * Sets all the locators associated with a person
    * @param personLocatorsColl the new value of the personLocatorsColl property
    * @return void
    */
  public void setPersonLocatorsColl(java.util.Collection<Object>  personLocatorsColl) {
    this.personLocatorsColl = personLocatorsColl;
  }

  /**
    * Access method for the thePersonLocatorColl property.
    * returns all the locators associated with a person
    * @return the current value of the thePersonLocatorColl property
    */
  public java.util.Collection<Object>  getPersonLocatorsColl() {
    return personLocatorsColl;
  }

  /**
    * Sets the value of the personDOB property.
    * @param personDOB the new value of the personDOB property
    * @return   void
    */
  public void setPersonDOB(String personDOB) {
    this.personDOB = personDOB;
  }

  /**
   * Access method for the personDOB property.
   * @return   the current value of the personDOB property
   */
  public String getPersonDOB() {
    return personDOB;
  }

  /**
  * Sets the value of the personDOB property.
  * @param personDOB the new value of the personDOB property
  * @return   void
  */
public void setEthnicGroupInd(String aEthnicGroupInd) {
   //System.out.println("\n\n PersonSearchResultVO  setEthnicGroupInd  " + aEthnicGroupInd);
  this.ethnicGroupInd = aEthnicGroupInd;
}

/**
 * Access method for the personDOB property.
 * @return   the current value of the personDOB property
 */
public String getEthnicGroupInd() {
   //System.out.println("\n\n PersonSearchResultVO getEthnicGroupInd  " + ethnicGroupInd);
  return ethnicGroupInd;
}

  /**
   * Sets the value of the currentSex property.
   * @param currentSex the new value of the currentSex property
   */
  public void setCurrentSex(String currentSex) {
    this.currentSex = currentSex;
  }

  /**
   * Access method for the currentSex property.
   * @return   the current value of the currentSex property
   */
  public String getCurrentSex() {
    return currentSex;
  }

  /**
   * Sets the value of the personUID property.
   * @param personUID the new value of the personUID property
   */
  public void setPersonUID(Long personUID) {
    this.personUID = personUID;
  }

  /**
   * Access method for the personUID property.
   * @return   the current value of the personUID property
   */
  public Long getPersonUID() {
    return personUID;
  }

  /**
   * Access method for the person's legal name
   * For sorting resultset by person name this method is required. It will return person
   * name for name type=Legal". returns null if that name type does not exist
   * @return   the legal last name of the person
   */
  public String getPersonName(){
    if (personNameColl != null){
     Iterator<Object>  nameIter = personNameColl.iterator();
      while (nameIter.hasNext()){
        PersonNameDT name = (PersonNameDT)nameIter.next();
        if (name.getNmUseCd() != null && name.getNmUseCd().equals(NEDSSConstants.LEGAL)){
          return name.getLastNm();
        }
      }
    }
    return null;
  }

  /**
   * Access method for the person's current home address.
   * For sorting resultset by person address this method is required. It will return person
   * address for address type=Current and address use=Home". returns null if that type and use does not exist
   * @return   the current home address of the person
   */
  public String getaPersonAddress(){
    if(personLocatorsColl != null){
     Iterator<Object>  addressIter = personLocatorsColl.iterator();
      while (addressIter.hasNext()){
        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
        if (elp != null && elp.getCd() != null && elp.getUseCd() != null && elp.getClassCd() != null){
          if (elp.getCd().equals(NEDSSConstants.CURRENTADDRESS) && elp.getUseCd().equals(NEDSSConstants.HOME) && elp.getClassCd().equals(NEDSSConstants.POSTAL) ){
            if (elp.getThePostalLocatorDT() != null)
              return elp.getThePostalLocatorDT().getStreetAddr1();
          }
        }
      }
    }
    return null;
  }

  /**
   * Access method for the person's current home phone number.
   * For sorting resultset by person telephone number this method is required. It will return person
   * telephone number for telephone number type=Phone and use=Home". returns null if that type and use does not exist
   * @return   the current home phone number of the person
   */
  public String getPersonPhone(){
    if (personLocatorsColl != null){
     Iterator<Object>  addressIter = personLocatorsColl.iterator();
      while (addressIter.hasNext()){
        EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)addressIter.next();
        if (elp != null && elp.getCd() != null && elp.getUseCd() != null && elp.getClassCd() != null){
          if (elp.getCd().equals(NEDSSConstants.CURRENTADDRESS) && elp.getUseCd().equals(NEDSSConstants.HOME) && elp.getClassCd().equals(NEDSSConstants.TELE) ){
            if (elp.getTheTeleLocatorDT() != null)
              return elp.getTheTeleLocatorDT().getPhoneNbrTxt();
          }
        }
      }
    }
    return null;
  }

  /**
   * Sets the value of the personLocalID property.
   * @param personLocalID the new value of the personLocalID property
   */
  public void setPersonLocalID(String personLocalID) {
    this.personLocalID = personLocalID;
  }

  /**
   * Access method for the personLocalID property.
   * @return   the current value of the personLocalID property
   */
  public String getPersonLocalID() {
    return personLocalID;
  }

  /**
   * Sets the value of the personIdColl property.
   * @param personIdColl the new value of the personIdColl property
   */
   public void setPersonIdColl(java.util.Collection<Object>  personIdColl) {
    this.personIdColl = personIdColl;
  }

  /**
   * Access method for the personIdColl property.
   * @return   the current value of the personIdColl property
   */
  public java.util.Collection<Object>  getPersonIdColl() {
    return personIdColl;
  }

  /**
   * Sets the value of the recordStatusCd property.
   * @param recordStatusCd the new value of the recordStatusCd property
   */
  public void setRecordStatusCd(String recordStatusCd) {
    this.recordStatusCd = recordStatusCd;
  }

  /**
   * Access method for the recordStatusCd property.
   * @return   the current value of the recordStatusCd property
   */
  public String getRecordStatusCd() {
    return recordStatusCd;
  }

  /**
    * Gets the person's legal first name
    * @return String
    */
   public String getFirstName(){
    if (personNameColl != null){
     Iterator<Object>  nameIter = personNameColl.iterator();
      while (nameIter.hasNext()){
        PersonNameDT name = (PersonNameDT)nameIter.next();
        if (name.getNmUseCd() != null && name.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME_TXT)){
          return name.getFirstNm();
        }
      }
    }
    return null;
  }


  /**
    * Gets the person's legal last name
    * @return String
    */
   public String getLastName(){
    if (personNameColl != null){
     Iterator<Object>  nameIter = personNameColl.iterator();
      while (nameIter.hasNext()){
        PersonNameDT name = (PersonNameDT)nameIter.next();
        if (name.getNmUseCd() != null && name.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME_TXT)){
          return name.getLastNm();
        }
      }
    }
    return null;
  }

  public String getAsOfDateAdmin()
{
  return asOfDateAdm;
}
public void setAsOfDateAdmin(String aAsOfDateAdmin)
{
  asOfDateAdm = aAsOfDateAdmin;
}
public String getAgeReported()
{
  return ageReported;
}
public void setAgeReported(String newAgeReported)
{
  ageReported = newAgeReported;
}

public String getAgeUnit()
{
  return ageUnit;
}
public void setAgeUnit(String newAgeUnit){
ageUnit = newAgeUnit;
}

public String getSex()
{
  return sex;
}
public void setSex(String newSex){
sex = newSex;
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
 // this method a string of DateOfDeath which is helps for front end display
  public String getDateOfDeath() {

      Date date = null;
      SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
      if (this.deceasedTime != null)
        date = new Date(this.deceasedTime.getTime());

      if (date == null)
        return "";
      else
        return formatter.format(date);

  }

  /**
   *
   * @param aDeceasedTime
   */
  public void setDeceasedTime(Timestamp aDeceasedTime) {
      this.deceasedTime = aDeceasedTime;
  }

  public Long getPersonParentUid(){
  return personParentUid;
  }
  public void setPersonParentUid(Long newPersonParentUid){
  personParentUid = newPersonParentUid;
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
   
   public java.util.Collection<Object> getConditionCdColl() {
		return conditionCdColl;
	}

	public void setConditionCdColl(java.util.Collection<Object> conditionCdColl) {
		this.conditionCdColl = conditionCdColl;
	}


  private java.util.Collection<Object>  personNameColl;
  private java.util.Collection<Object>  personLocatorsColl;
  private String personDOB;
  private String ethnicGroupInd;
  private Long personUID;
  private Long personParentUid;
  private String personLocalID;
  private java.util.Collection<Object>  personIdColl;
  private String currentSex;
  private String recordStatusCd;
  private java.util.Collection<Object>  raceCdColl;
  private String sex;
  private Timestamp deceasedTime;
  private String asOfDateAdm;
  private String ageReported;
  private String ssn;
  private String maritalStatusCd;
  private String ageUnit;
  private Integer versionCtrlNbr;
  private java.util.Collection<Object>  conditionCdColl;
  private String personFullName;
  private String personFullNameNoLink;
  private String personFirstName;
  private String personLastName;
  private String displayRevision;
  private String view;
  private String viewFile;
  private String link;
  private String profile;
  private String personAddressProfile;
  private String personPhoneprofile;
  private String personIds;
  private Timestamp startDate;
  private String startDate_s;
  private String investigator;
  private String investigatorLastName;
  private String investigatorFirstName;
  private String Jurisdiction;
  private String JurisdictionCd;
  private String cd;
  private String condition;
  private ArrayList<String> conditions;
  private String caseStatusCd;
  private String conditionLink;
  private String caseStatus;
  private String notification;
  private String notificationCd;
  private String publicHealthCaseUid;
  private String documentType;
  private String documentTypeNoLnk;
  private String electronicInd;
  private String testsString;
  private String testsStringNoLnk;
  private String testsStringPrint;
  private Collection<Object> invSummaryVOs;
  private String dateReceived;
  private String reportingFacilityProvider;
  private String reportingFacilityProviderPrint;
  private String description;
  private ArrayList<String> descriptions;
  private String descriptionPrint;
  private Collection<Object> theResultedTestSummaryVOCollection;
  private String type;
  private String associatedWith;
  private String localId;
  private Long observationUid;
  private Long MPRUid;
  private String totalCount;
  private String profileNoLink;
  private String personAddressProfileNoLink;
  private String personPhoneprofileNoLink;
  private String personIdsNoLink;
  private String viewFileWithoutLink;


  //Provider for Lab report
  
  private String providerLastName;
  private String providerFirstName;
  private String providerPrefix;
  private String providerSuffix;
  private String degree;
  private String providerUid;
  private Timestamp birthTime;
  
  public Timestamp getBirthTime() {
	return birthTime;
}

public void setBirthTime(Timestamp birthTime) {
	this.birthTime = birthTime;
}


private String reportingFacility;
  
  
  private String programAreaCode;
  
  private boolean nonStdHivProgramAreaCode;
  
  public boolean getNonStdHivProgramAreaCode() {
	return nonStdHivProgramAreaCode;
  }

  public void setNonStdHivProgramAreaCode(boolean nonStdHivProgramAreaCode) {
	this.nonStdHivProgramAreaCode = nonStdHivProgramAreaCode;
  }

  
  public String getProgramAreaCode() {
	return programAreaCode;
   }

  public void setProgramAreaCode(String programAreaCode) {
	this.programAreaCode = programAreaCode;
  }


private ArrayList<Object> investigators = new ArrayList<Object> ();
  Collection<Object> summaryVOColl;
	public Collection<Object> getSummaryVOColl() {
		
		// TODO Auto-generated method stub
		return summaryVOColl;
	}
	public void setSummaryVOColl(Collection<Object> summaryVOColl){
		this.summaryVOColl = summaryVOColl;
	}
	
	public ArrayList<Object> getInvestigators(){
		return investigators;
	}
	
	public String getPersonFullName() {
		return personFullName;
	}
	
	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}

	public String getDisplayRevision() {
		return displayRevision;
	}

	public void setDisplayRevision(String displayRevision) {
		this.displayRevision = displayRevision;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getViewFile() {
		return viewFile;
	}

	public void setViewFile(String viewFile) {
		this.viewFile = viewFile;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getPersonAddressProfile() {		  
		return personAddressProfile;
	}
	  
	public void setPersonAddressProfile(String personAddressProfile) {
		this.personAddressProfile = personAddressProfile;
	}

	public String getPersonPhoneprofile() {		
		return personPhoneprofile;
	}

	public void setPersonPhoneprofile(String personPhoneprofile) {
		this.personPhoneprofile = personPhoneprofile;
	}

	public String getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String personIds) {
		this.personIds = personIds;
	}
	
	/**
	    * Gets the person's legal first name
	    * @return String
	    */
	   public String getFirstNameforFilter(){
		  String fName=""; 
	    if (personNameColl != null){
	     Iterator<Object>  nameIter = personNameColl.iterator();
	      while (nameIter.hasNext()){
	        PersonNameDT name = (PersonNameDT)nameIter.next();
	        //if (name.getNmUseCd() != null && name.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME_TXT)){
	        	if(fName.equals("") && name.getFirstNm() != null)
	        		fName =  name.getFirstNm();	
	        	else if(name.getFirstNm() != null)	
	        	fName = fName +"|" + name.getFirstNm();
	       // }
	      }
	    }
	    return fName;
	  }


	  /**
	    * Gets the person's legal last name
	    * @return String
	    */
	   public String getLastNameforFilter(){
		   String LName=""; 
	    if (personNameColl != null){
	     Iterator<Object>  nameIter = personNameColl.iterator();
	      while (nameIter.hasNext()){
	        PersonNameDT name = (PersonNameDT)nameIter.next();
	       // if (name.getNmUseCd() != null && name.getNmUseCd().equals(NEDSSConstants.LEGAL_NAME_TXT)){
	        	if(LName.equals("") && name.getLastNm() != null)
	        		LName = name.getLastNm();	
	        	else if(name.getLastNm() != null)	
	        	LName = LName + "|"+ name.getLastNm();
	       // }
	      }
	    }
	    return LName;
	  }

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getStartDate_s() {
		return startDate_s;
	}

	public void setStartDate_s(String startDate) {
		this.startDate_s = startDate;
	}

	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}

	public String getJurisdiction() {
		return Jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		Jurisdiction = jurisdiction;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getInvestigatorLastName() {
		return investigatorLastName;
	}

	public void setInvestigatorLastName(String investigatorLastName) {
		this.investigatorLastName = investigatorLastName;
	}

	public String getInvestigatorFirstName() {
		return investigatorFirstName;
	}

	public void setInvestigatorFirstName(String investigatorFirstName) {
		this.investigatorFirstName = investigatorFirstName;
	}

	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	public String getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}


	public String getConditionLink() {
		return conditionLink;
	}

	public void setConditionLink(String conditionLink) {
		this.conditionLink = conditionLink;
	}

	public String getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}

	public void setPublicHealthCaseUid(String publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}
	public String getPersonFullNameNoLink() {
		return personFullNameNoLink;
	}

	public void setPersonFullNameNoLink(String personFullNameNoLink) {
		this.personFullNameNoLink = personFullNameNoLink;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getReportingFacilityProvider() {
		return reportingFacilityProvider;
	}

	public void setReportingFacilityProvider(String reportingFacilityProvider) {
		this.reportingFacilityProvider = reportingFacilityProvider;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssociatedWith() {
		return associatedWith;
	}

	public void setAssociatedWith(String associatedWith) {
		this.associatedWith = associatedWith;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public Timestamp getDeceasedTime() {
		return deceasedTime;
	}public String getJurisdictionCd() {
		return JurisdictionCd;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		JurisdictionCd = jurisdictionCd;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCaseStatusCd() {
		return caseStatusCd;
	}

	public void setCaseStatusCd(String caseStatusCd) {
		this.caseStatusCd = caseStatusCd;
	}

	public String getNotificationCd() {
		return notificationCd;
	}

	public void setNotificationCd(String notificationCd) {
		this.notificationCd = notificationCd;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderPrefix() {
		return providerPrefix;
	}

	public void setProviderPrefix(String providerPrefix) {
		this.providerPrefix = providerPrefix;
	}

	public String getProviderSuffix() {
		return providerSuffix;
	}

	public void setProviderSuffix(String providerSuffix) {
		this.providerSuffix = providerSuffix;
	}

	public String getProviderUid() {
		return providerUid;
	}

	public void setProviderUid(String providerUid) {
		this.providerUid = providerUid;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getReportingFacility() {
		return reportingFacility;
	}

	public void setReportingFacility(String reportingFacility) {
		this.reportingFacility = reportingFacility;
	}

	public String getReportingFacilityProviderPrint() {
		return reportingFacilityProviderPrint;
	}

	public void setReportingFacilityProviderPrint(
			String reportingFacilityProviderPrint) {
		this.reportingFacilityProviderPrint = reportingFacilityProviderPrint;
	}

	public Long getObservationUid() {
		return observationUid;
	}

	public void setObservationUid(Long observationUid) {
		this.observationUid = observationUid;
	}

	public Long getMPRUid() {
		return MPRUid;
	}

	public void setMPRUid(Long mPRUid) {
		MPRUid = mPRUid;
	}

	public String getDocumentTypeNoLnk() {
		return documentTypeNoLnk;
	}

	public void setDocumentTypeNoLnk(String documentTypeNoLnk) {
		this.documentTypeNoLnk = documentTypeNoLnk;
	}

	public String getElectronicInd() {
		return electronicInd;
	}

	public void setElectronicInd(String electronicInd) {
		this.electronicInd = electronicInd;
	}

	public String getTestsString() {
		return testsString;
	}

	public void setTestsString(String testsString) {
		this.testsString = testsString;
	}

	public String getTestsStringNoLnk() {
		return testsStringNoLnk;
	}

	public void setTestsStringNoLnk(String testsStringNoLnk) {
		this.testsStringNoLnk = testsStringNoLnk;
	}

	public String getTestsStringPrint() {
		return testsStringPrint;
	}

	public void setTestsStringPrint(String testsStringPrint) {
		this.testsStringPrint = testsStringPrint;
	}

	public ArrayList<String> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<String> conditions) {
		this.conditions = conditions;
	}

	public Collection<Object> getInvSummaryVOs() {
		return invSummaryVOs;
	}

	public void setInvSummaryVOs(Collection<Object> invSummaryVOs) {
		this.invSummaryVOs = invSummaryVOs;
	}

	public String getDescriptionPrint() {
		return descriptionPrint;
	}

	public void setDescriptionPrint(String descriptionPrint) {
		this.descriptionPrint = descriptionPrint;
	}
	
	  public Collection<Object> getTheResultedTestSummaryVOCollection() {
		    return theResultedTestSummaryVOCollection;
		  }

		  public void setTheResultedSummaryTestVOCollection(Collection<Object> aTheResultedTestSummaryVOCollection) {
		    theResultedTestSummaryVOCollection  = aTheResultedTestSummaryVOCollection;
		  }

		public ArrayList<String> getDescriptions() {
			return descriptions;
		}

		public void setDescriptions(ArrayList<String> descriptions) {
			this.descriptions = descriptions;
		}

		public String getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(String totalCount) {
			this.totalCount = totalCount;
		}
		  
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getProfileNoLink() {
			return profileNoLink;
		}

		public void setProfileNoLink(String profileNoLink) {
			this.profileNoLink = profileNoLink;
		}

		public String getPersonAddressProfileNoLink() {
			return personAddressProfileNoLink;
		}

		public String getPersonPhoneprofileNoLink() {
			return personPhoneprofileNoLink;
		}

		public void setPersonAddressProfileNoLink(String personAddressProfileNoLink) {
			this.personAddressProfileNoLink = personAddressProfileNoLink;
		}

		public void setPersonPhoneprofileNoLink(String personPhoneprofileNoLink) {
			this.personPhoneprofileNoLink = personPhoneprofileNoLink;
		}

		public String getPersonIdsNoLink() {
			return personIdsNoLink;
		}

		public void setPersonIdsNoLink(String personIdsNoLink) {
			this.personIdsNoLink = personIdsNoLink;
		}
		public String getViewFileWithoutLink() {
			return viewFileWithoutLink;
		}

		public void setViewFileWithoutLink(String viewFileWithoutLink) {
			this.viewFileWithoutLink = viewFileWithoutLink;
		}
}