/**
 * Title: PersonSearchVO helper class.
 * Description: A helper class for person search value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.person.vo;


import gov.cdc.nedss.util.NEDSSConstants;

import java.io.Serializable;


public class PersonSearchVO implements Serializable{
	
	public static String INVESTIGATION_LOCAL_ID = "P10001";
	public static String ABC_STATE_CASE_ID = "P10000";
	public static String STATE_CASE_ID = "P10004";
	public static String VACCINATION_LOCAL_ID = "P10006";
	public static String LAB_REPORT_LOCAL_ID = "P10002";
	public static String MORBIDITY_REPORT_LOCAL_ID = "P10003";
	public static String TREATMENT_LOCAL_ID = "P10005";
	//public static String RVCT_STATE_CASE_NUMBER = "P10007";
	public static String CITY_COUNTY_CASE_ID = "P10008";
	public static String Accession_Number_LOCAL_ID = "P10009";//changes made for event search based on accession number
	public static String Doc_Number_LOCAL_ID = "P10010";//changes made for event search based on doc number.
	public static String NOTIFICATION_ID = "P10013";
	
	public static String DATE_REPORT ="DR";
	public static String DATE_INVESTIGATION_CLOSED_DATE ="ICLD";
	public static String DATE_INVESTIGATION_CREATE_DATE ="ICD";
	public static String DATE_INVESTIGATION_LAST_UPDATE_DATE ="LUD";
	public static String DATE_INVESTIGATION_START_DATE ="ISD";
	public static String DATE_NOTIFICATION_CREATE_DATE ="NCD";

	public static String Date_of_Report = "DR";
	public static String Date_Received_by_Public_Health = "DPH";
	public static String Date_Specimen_Collection = "SCD";
	public static String Investigation_Closed_Date= "ICLD";
	public static String Investigation_Create_Date = "ICD";
	public static String Lab_Report_Create_Date = "LCD";
	public static String Last_Update_Date = "LUD";
	public static String Morbidity_Report_Create_Date = "MCD";
	public static String Notification_Create_Date = "NCD";
	
	public static String LAB_REPORT = "LR";
	public static String MORBIDITY_REPORT = "MR";
	public static String CASE_REPORT = "CR";
	public static String INVESTIGATION_REPORT = "I";
	
	public static String ORDERING_FACILITY = "OE";
	public static String ORDERING_PROVIDER = "OP";
	public static String REPORTING_FACILITY = "RE";
	public static String REPORTING_PROVIDER = "RP";
	public static String SENDING_FACILITY = "SF";
	
	private Long personUID;

	private String localID;

	private String typeCd;

	private String rootExtensionTxt;

	private String lastName;

	private String firstName;

	private String streetAddr1;

	private String cityDescTxt;

	private String zipCd;

	private String phoneNbrTxt;
	
	private String emailAddress;

	private String birthTime;

	private String beforeBirthTime;

	private String afterBirthTime;

	private String birthTimeDay;

	private String birthTimeMonth;

	private String birthTimeYear;

	private String ageReported;

	private String ageReportedUnitCd;

	private String currentSex;

	private String raceCd;

	private String ethnicGroupCd;

	private String lastNameOperator;

	private String firstNameOperator;

	private String motherLastOperator;

	private String motherFirstOperator;

	private String streetAddr1Operator;

	private String cityDescTxtOperator;

	private String zipCdOperator;

	private String phoneNbrTxtOperator;

	private String birthTimeOperator;

	private String ageReportedOperator;

	private boolean active = false;

	private boolean inActive = false;

	private String nameUseCd;

	private boolean cancelled = false;

	private String locatorUseDesc;

	private String nameUseDesc;

	private String statusCodeActive;

	private String statusCodeCancelled;

	private String statusCodeInActive;

	private String statusCodeNew;

	private String statusCodeSuperCeded;

	private boolean superceded = false;

	private boolean itNew = false;

	private String rootExtensionTxtOperator;

	private String state;

	private String raceDescTxt;

	private String ethnicDescTxt;

	private String recordStatusActive = NEDSSConstants.RECORD_STATUS_ACTIVE;

	private String recordStatusInActive = NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE;

	private String recordStatusSuperceded = NEDSSConstants.RECORD_STATUS_SUPERCEDED;

	private String ethnicGroupInd;

	private String role;

	private String dataAccessWhereClause;

	private boolean logicalDeleted = false;

	private String entityId;

	
	private String patientIDOperator;

	private boolean isEpilink = false;
	
	
	private String motherLastName;
	
	private String motherFirstName;
	
	private String motherMaidenName;
	
	private String multipleBirthIndicator;
	
	private Integer birthOrder;
	
	private String middleName;
	
	private String processedState;
	private String unProcessedState;
	
	/**
	 * @return the programAreaLabSelected
	 */
	public String[] getProgramAreaLabSelected() {
		return programAreaLabSelected;
	}
	/**
	 * @param programAreaLabSelected the programAreaLabSelected to set
	 */
	public void setProgramAreaLabSelected(String[] programAreaLabSelected) {
		this.programAreaLabSelected = programAreaLabSelected;
	}
	/**
	 * @return the programAreaMorbSelected
	 */
	public String[] getProgramAreaMorbSelected() {
		return programAreaMorbSelected;
	}
	/**
	 * @param programAreaMorbSelected the programAreaMorbSelected to set
	 */
	public void setProgramAreaMorbSelected(String[] programAreaMorbSelected) {
		this.programAreaMorbSelected = programAreaMorbSelected;
	}
	/**
	 * @return the programAreaCaseSelected
	 */
	public String[] getProgramAreaCaseSelected() {
		return programAreaCaseSelected;
	}
	/**
	 * @param programAreaCaseSelected the programAreaCaseSelected to set
	 */
	public void setProgramAreaCaseSelected(String[] programAreaCaseSelected) {
		this.programAreaCaseSelected = programAreaCaseSelected;
	}
	/**
	 * @return the programAreaInvestigationSelected
	 */
	public String[] getProgramAreaInvestigationSelected() {
		return programAreaInvestigationSelected;
	}
	/**
	 * @param programAreaInvestigationSelected the programAreaInvestigationSelected to set
	 */
	public void setProgramAreaInvestigationSelected(String[] programAreaInvestigationSelected) {
		this.programAreaInvestigationSelected = programAreaInvestigationSelected;
	}
	private String epiLinkId;
	
	private String[] conditionSelected;
	private String[] programAreaSelected;
	private String[] programAreaLabSelected;
	private String[] programAreaMorbSelected;
	private String[] programAreaCaseSelected;
	private String[] programAreaInvestigationSelected;
	private String[] jurisdictionSelected ;
	private String investigationStatusSelected;
	private String caseStatusListSelected;
	private String caseStatusListValuesSelected;
	private String notificationStatusSelected;
	private String notificationValuesSelected;
	private String currentProcessStateSelected;
	private String currentProcessStateValuesSelected;
	private String investigatorSelected;
	private String reportingFacilitySelected;
	private String reportingProviderSelected;
	private String orderingProviderSelected;
	private String orderingFacilitySelected;
	private String notificationCodedValuesSelected;
	
	private String caseStatusCodedValuesSelected;
	private String currentProcessCodedValuesSelected;
	private String currentProcessingStatusValuesSelected;
	private String documentUpdateFullNameSelected;
	private String documentCreateFullNameSelected;
	
	
	/**
	 * @return the orderingProviderSelected
	 */
	public String getOrderingProviderSelected() {
		return orderingProviderSelected;
	}
	/**
	 * @param orderingProviderSelected the orderingProviderSelected to set
	 */
	public void setOrderingProviderSelected(String orderingProviderSelected) {
		this.orderingProviderSelected = orderingProviderSelected;
	}
	/**
	 * @return the orderingFacilitySelected
	 */
	public String getOrderingFacilitySelected() {
		return orderingFacilitySelected;
	}
	/**
	 * @param orderingFacilitySelected the orderingFacilitySelected to set
	 */
	public void setOrderingFacilitySelected(String orderingFacilitySelected) {
		this.orderingFacilitySelected = orderingFacilitySelected;
	}
	/**
	 * @return the reportingFacilityDescSelected
	 */
	public String getReportingFacilityDescSelected() {
		return reportingFacilityDescSelected;
	}
	/**
	 * @param reportingFacilityDescSelected the reportingFacilityDescSelected to set
	 */
	public void setReportingFacilityDescSelected(String reportingFacilityDescSelected) {
		this.reportingFacilityDescSelected = reportingFacilityDescSelected;
	}
	/**
	 * @return the reportingProviderDescSelected
	 */
	public String getReportingProviderDescSelected() {
		return reportingProviderDescSelected;
	}
	/**
	 * @param reportingProviderDescSelected the reportingProviderDescSelected to set
	 */
	public void setReportingProviderDescSelected(String reportingProviderDescSelected) {
		this.reportingProviderDescSelected = reportingProviderDescSelected;
	}
	/**
	 * @return the orderingFacilityDescSelected
	 */
	public String getOrderingFacilityDescSelected() {
		return orderingFacilityDescSelected;
	}
	/**
	 * @param orderingFacilityDescSelected the orderingFacilityDescSelected to set
	 */
	public void setOrderingFacilityDescSelected(String orderingFacilityDescSelected) {
		this.orderingFacilityDescSelected = orderingFacilityDescSelected;
	}
	/**
	 * @return the orderingProviderDescSelected
	 */
	public String getOrderingProviderDescSelected() {
		return orderingProviderDescSelected;
	}
	/**
	 * @param orderingProviderDescSelected the orderingProviderDescSelected to set
	 */
	public void setOrderingProviderDescSelected(String orderingProviderDescSelected) {
		this.orderingProviderDescSelected = orderingProviderDescSelected;
	}
	private String eventStatusInitialSelected;
	private String eventStatusUpdateSelected;
	private String pregnantSelected;
	private String[] outbreakNameSelected;
	private String dateOperator;
	private String DocOperator;
	private String documentCreate;
	private String reportType;
	private String custom;//custom queues: "true" means the queue to be shown in the search results page is a custom queue created from Event Search > Save button. It will be useful to hide unnecessary things like: Save button, Refine Search, etc.
	private String electronicSelected;
	private String manualSelected;
	private String electronicValueSelected;
	private String manualValueSelected;
	private String internalValueSelected;
	private String externalValueSelected;
	private String internalUserSelected;
	private String externalUserSelected;
	private String newInitialSelected;
	private String updateSelected;
	private String documentUpdateSelected;
	private String documentCreateSelected;
	private String providerFacilitySelected;
	private String rfSelected;
	private String ofSelected;
	private String opSelected;
	private String rpSelected;
	private String invSelected;
	private String reportingFacilityDescSelected;
	private String reportingProviderDescSelected;
	private String orderingFacilityDescSelected;
	private String orderingProviderDescSelected;
	private String investigatorDescSelected;
	public boolean isEpilink() {
		return isEpilink;
	}
	public void setEpilink(boolean isEpilink) {
		this.isEpilink = isEpilink;
	}
	   

	/**
	 * @return Returns the actType.
	 */
	public String getActType() {
		return actType;
	}
	/**
	 * @param actType The actType to set.
	 */
	public void setActType(String actType) {
		this.actType = actType;
	}
	private String actType;
	private String actId;
	private String dateFrom;
	private String dateTo;
	private String dateType;
	private String specimenSource;
	private String numericComparator;
	private String numericResult;
	private String numericUnit;
	private String textResultComparator;
	private String textResult;
	private String organism;
	private String codedResult;
	private String resultedTestCode;
	private String resultedTestCodeDropdown;
	private String resultedTestDescriptionWithCode;
	private String resultedTestDescriptionWithCodeValue;
	private String testDescription;
	private String codeResultOrganismDescriptionValue;
	private String resultDescription;
	private String resultDescriptionValue;	
	private String codeResultOrganismCode;
	private String codeResultOrganismDescription;
	private String codeResultOrganismDropdown;
	private String orderingProvider;
	private String orderingFacility;
	private String reportingProvider;
	private String reportingFacility;
	private String pregnant;
	private String[] outbreakName;
	private String notificationStatus;
	private String caseStatus;
	private String investigationStatus;
	private String investigator;
	
	
	

	/**
	 *
	 */
	public PersonSearchVO() {
	}

	/**
	 * Sets the value of the lastName property.
	 * @param newName the new value of the lastName property
	 */
	public void setLastName(String newName) {
		lastName = newName;
	}

	/**
	 * Sets the value of the firstName property.
	 * @param newName the new value of the firstName property
	 */
	public void setFirstName(String newName) {
		firstName = newName;
	}

	/**
	 * Sets the value of the cityDescTxt property.
	 * @param cityDescTxt the new value of the cityDescTxt property
	 */
	public void setCityDescTxt(String cityDescTxt) {
		this.cityDescTxt = cityDescTxt;
	}

	/**
	 * Sets the value of the streetAddr1 property.
	 * @param streetAddr1 the new value of the streetAddr1 property
	 */
	public void setStreetAddr1(String streetAddr1) {
		this.streetAddr1 = streetAddr1;
	}

	/**
	 * Sets the value of the currentSex property.
	 * @param newCurrentSex the new value of the currentSex property
	 */
	public void setCurrentSex(String newCurrentSex) {
		currentSex = newCurrentSex;
	}

	/**
	 * Sets the value of the afterBirthTime property.
	 * @param birthTime the new value of the afterBirthTime property
	 */
	public void setAfterBirthTime(String afterBirthTime) {
		this.afterBirthTime = afterBirthTime;
	}

	/**
	 * Sets the value of the beforeBirthTime property.
	 * @param birthTime the new value of the beforeBirthTime property
	 */
	public void setBeforeBirthTime(String beforeBirthTime) {
		this.beforeBirthTime = beforeBirthTime;
	}

	/**
	 * Sets the value of the birthTime property.
	 * @param birthTime the new value of the birthTime property
	 */
	public void setBirthTime(String birthTime) {
		this.birthTime = birthTime;
	}

	/**
	 * Sets the value of the birthTimeDay property.
	 * @param birthTime the new value of the birthTimeDay property
	 */
	public void setBirthTimeDay(String birthTimeDay) {
		this.birthTimeDay = birthTimeDay;
	}

	/**
	 * Sets the value of the birthTimeMonth property.
	 * @param birthTime the new value of the birthTime property
	 */
	public void setBirthTimeMonth(String birthTimeMonth) {
		this.birthTimeMonth = birthTimeMonth;
	}

	/**
	 * Sets the value of the birthTimeYear property.
	 * @param birthTime the new value of the birthTimeYear property
	 */
	public void setBirthTimeYear(String birthTimeYear) {
		this.birthTimeYear = birthTimeYear;
	}

	/**
	 * Access method for the lastName property.
	 * @return   the current value of the lastName property
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Access method for the firstName property.
	 * @return   the current value of the firstName property
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Access method for the cityDescTxt property.
	 * @return   the current value of the cityDescTxt property
	 */
	public String getCityDescTxt() {
		return cityDescTxt;
	}

	/**
	 * Access method for the currentSex property.
	 * @return   the current value of the currentSex property
	 */
	public String getCurrentSex() {
		return currentSex;
	}

	/**
	 * Access method for the birthTime property.
	 * @return   the current value of the birthTime property
	 */
	public String getBirthTime() {
		return birthTime;
	}

	/**
	 * Access method for the BeforebirthTime property.
	 * @return   the current value of the BeforebirthTime property
	 */
	public String getBeforeBirthTime() {
		return beforeBirthTime;
	}

	/**
	 * Access method for the afterBirthTime property.
	 * @return   the current value of the afterBirthTime property
	 */
	public String getAfterBirthTime() {
		return afterBirthTime;
	}

	/**
	 * Access method for the birthTimeDay property.
	 * @return   the current value of the birthTimeDay property
	 */
	public String getBirthTimeDay() {
		return birthTimeDay;
	}

	/**
	 * Access method for the birthTimeMonth property.
	 * @return   the current value of the birthTimeMonth property
	 */
	public String getBirthTimeMonth() {
		return birthTimeMonth;
	}

	/**
	 * Access method for the birthTimeYear property.
	 * @return   the current value of the birthTimeYear property
	 */
	public String getBirthTimeYear() {
		return birthTimeYear;
	}

	/**
	 * Access method for the streetAddr1 property.
	 * @return   the current value of the streetAddr1 property
	 */
	public String getStreetAddr1() {
		return streetAddr1;
	}

	/**
	 * Access method for the ageReported property.
	 * @return   the current value of the ageReported property
	 */
	public String getAgeReported() {
		return ageReported;
	}

	/**
	 * Sets the value of the ageReported property.
	 * @param ageReported the new value of the ageReported property
	 */
	public void setAgeReported(String ageReported) {
		this.ageReported = ageReported;
	}

	/**
	 * Access method for the ethnicGroupCd property.
	 * @return   the current value of the ethnicGroupCd property
	 */
	public String getEthnicGroupCd() {
		return ethnicGroupCd;
	}

	/**
	 * Sets the value of the ethnicGroupCd property.
	 * @param ethnicGroupCd the new value of the ethnicGroupCd property
	 */
	public void setEthnicGroupCd(String ethnicGroupCd) {
		this.ethnicGroupCd = ethnicGroupCd;
	}

	/**
	 * Access method for the typeCd property.
	 * @return   the current value of the typeCd property
	 */
	public String getTypeCd() {
		return typeCd;
	}

	/**
	 * Sets the value of the typeCd property.
	 * @param typeCd the new value of the typeCd property
	 */

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	
	/**
	 * Access method for the NOTIFICATION_ID property.
	 * @return   the current value of the NOTIFICATION_ID property
	 */
	public String getNotificationID() {
			return NOTIFICATION_ID;
		}
	
	/**
	 * Sets the value of the NOTIFICATION_ID property.
	 * @param NOTIFICATION_ID the new value of the NOTIFICATION_ID property
	 */
	public void setNotificationID(String NOTIFICATIONID) {
		this.NOTIFICATION_ID = NOTIFICATIONID;
	}

	/**
	 * Access method for the rootExtensionTxt property.
	 * @return   the current value of the rootExtensionTxt property
	 */
	public String getRootExtensionTxt() {
		return rootExtensionTxt;
	}

	/**
	 * Sets the value of the rootExtensionTxt property.
	 * @param rootExtensionTxt the new value of the rootExtensionTxt property
	 */
	public void setRootExtensionTxt(String rootExtensionTxt) {
		this.rootExtensionTxt = rootExtensionTxt;
	}

	/**
	 * Access method for the localID property.
	 * @return   the current value of the localID property
	 */
	public String getLocalID() {
		return localID;
	}

	/**
	 * Sets the value of the localID property.
	 * @param localID the new value of the localID property
	 */
	public void setLocalID(String localID) {
		this.localID = localID;
	}

	/**
	 * Access method for the ageReportedOperator property.
	 * @return   the current value of the ageReportedOperator property
	 */
	public String getAgeReportedOperator() {
		return ageReportedOperator;
	}

	/**
	 * Sets the value of the ageReportedOperator property.
	 * @param ageReportedOperator the new value of the ageReportedOperator property
	 */
	public void setAgeReportedOperator(String ageReportedOperator) {
		this.ageReportedOperator = ageReportedOperator;
	}

	/**
	 * Access method for the cityDescTxtOperator property.
	 * @return   the current value of the cityDescTxtOperator property
	 */
	public String getCityDescTxtOperator() {
		return cityDescTxtOperator;
	}

	/**
	 * Sets the value of the cityDescTxtOperator property.
	 * @param cityDescTxtOperator the new value of the cityDescTxtOperator property
	 */
	public void setCityDescTxtOperator(String cityDescTxtOperator) {
		this.cityDescTxtOperator = cityDescTxtOperator;
	}

	/**
	 * Access method for the birthTimeOperator property.
	 * @return   the current value of the birthTimeOperator property
	 */
	public String getBirthTimeOperator() {
		return birthTimeOperator;
	}

	/**
	 * Sets the value of the birthTimeOperator property.
	 * @param birthTimeOperator the new value of the birthTimeOperator property
	 */
	public void setBirthTimeOperator(String birthTimeOperator) {
		this.birthTimeOperator = birthTimeOperator;
	}

	/**
	 * Access method for the firstNameOperator property.
	 * @return   the current value of the firstNameOperator property
	 */
	public String getFirstNameOperator() {
		return firstNameOperator;
	}

	/**
	 * Sets the value of the firstNameOperator property.
	 * @param firstNameOperator the new value of the firstNameOperator property
	 */
	public void setFirstNameOperator(String firstNameOperator) {
		this.firstNameOperator = firstNameOperator;
	}

	/**
	 * Access method for the lastNameOperator property.
	 * @return   the current value of the lastNameOperator property
	 */
	public String getLastNameOperator() {
		return lastNameOperator;
	}

	/**
	 * Sets the value of the lastNameOperator property.
	 * @param lastNameOperator the new value of the lastNameOperator property
	 */
	public void setLastNameOperator(String lastNameOperator) {
		this.lastNameOperator = lastNameOperator;
	}

	/**
	 * Access method for the streetAddr1Operator property.
	 * @return   the current value of the streetAddr1Operator property
	 */
	public String getStreetAddr1Operator() {
		return streetAddr1Operator;
	}

	/**
	 * Sets the value of the streetAddr1Operator property.
	 * @param streetAddr1Operator the new value of the streetAddr1Operator property
	 */
	public void setStreetAddr1Operator(String streetAddr1Operator) {
		this.streetAddr1Operator = streetAddr1Operator;
	}

	/**
	 * Access method for the phoneNbrTxtOperator property.
	 * @return   the current value of the phoneNbrTxtOperator property
	 */
	public String getPhoneNbrTxtOperator() {
		return phoneNbrTxtOperator;
	}

	/**
	 * Sets the value of the phoneNbrTxtOperator property.
	 * @param phoneNbrTxtOperator the new value of the phoneNbrTxtOperator property
	 */
	public void setPhoneNbrTxtOperator(String phoneNbrTxtOperator) {
		this.phoneNbrTxtOperator = phoneNbrTxtOperator;
	}

	/**
	 * Access method for the zipCdOperator property.
	 * @return   the current value of the zipCdOperator property
	 */
	public String getZipCdOperator() {
		return zipCdOperator;
	}

	/**
	 * Sets the value of the zipCdOperator property.
	 * @param zipCdOperator the new value of the zipCdOperator property
	 */
	public void setZipCdOperator(String zipCdOperator) {
		this.zipCdOperator = zipCdOperator;
	}

	/**
	 * Access method for the personUID property.
	 * @return   the current value of the personUID property
	 */
	public Long getPersonUID() {
		return personUID;
	}

	/**
	 * Sets the value of the personUID property.
	 * @param personUID the new value of the personUID property
	 */
	public void setPersonUID(Long personUID) {
		this.personUID = personUID;
	}

	/**
	 * Access method for the phoneNbrTxt property.
	 * @return   the current value of the phoneNbrTxt property
	 */
	public String getPhoneNbrTxt() {
		return phoneNbrTxt;
	}

	/**
	 * Sets the value of the phoneNbrTxt property.
	 * @param phoneNbrTxt the new value of the phoneNbrTxt property
	 */
	public void setPhoneNbrTxt(String phoneNbrTxt) {
		this.phoneNbrTxt = phoneNbrTxt;
	}

	/**
	 * Access method for the emailAddress property.
	 * @return   the current value of the emailAddress property
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the value of the emailAddress property.
	 * @param phoneNbrTxt the new value of the emailAddress property
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	/**
	 * Access method for the raceCd property.
	 * @return   the current value of the raceCd property
	 */
	public String getRaceCd() {
		return raceCd;
	}

	/**
	 * Sets the value of the raceCd property.
	 * @param raceCd the new value of the raceCd property
	 */
	public void setRaceCd(String raceCd) {
		this.raceCd = raceCd;
	}

	/**
	 * Sets the value of the active and statusCodeActive properties.
	 * @param active the new value of the active property
	 */
	public void setActive(boolean active) {
		this.active = active;
		if (active)
			this.setStatusCodeActive(recordStatusActive);
	}

	/**
	 * Access method for the active property.
	 * @return   the current value of the active property
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the value of the inActive and statusCodeInActive properties.
	 * @param inActive the new value of the inActive property
	 */
	public void setInActive(boolean inActive) {
		this.inActive = inActive;
		if (inActive)
			this.setStatusCodeInActive(recordStatusInActive);
	}

	/**
	 * Access method for the inActive property.
	 * @return   the current value of the inActive property
	 */
	public boolean isInActive() {
		return inActive;
	}

	/**
	 * Sets the value of the motherLastOperator property.
	 * @param motherLastOperator the new value of the motherLastOperator property
	 */
	public void setMotherLastOperator(String motherLastOperator) {
		this.motherLastOperator = motherLastOperator;
	}

	/**
	 * Access method for the motherLastOperator property.
	 * @return   the current value of the motherLastOperator property
	 */
	public String getMotherLastOperator() {
		return motherLastOperator;
	}

	/**
	 * Sets the value of the motherFirstOperator property.
	 * @param motherFirstOperator the new value of the motherFirstOperator property
	 */
	public void setMotherFirstOperator(String motherFirstOperator) {
		this.motherFirstOperator = motherFirstOperator;
	}

	/**
	 * Access method for the motherFirstOperator property.
	 * @return   the current value of the motherFirstOperator property
	 */
	public String getMotherFirstOperator() {
		return motherFirstOperator;
	}

	/**
	 * Sets the value of the zipCd property.
	 * @param zipCd the new value of the zipCd property
	 */
	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
	}

	/**
	 * Access method for the zipCd property.
	 * @return   the current value of the zipCd property
	 */
	public String getZipCd() {
		return zipCd;
	}

	/**
	 * Returns the sSN.
	 * @return String
	 * @roseuid 3E8C678F033C
	 */
	public String getSsn() {
		return entityId;
	}

	/**
	 * Sets the sSN.
	 * @param entityID The sSN to set
	 * @roseuid 3E8C678F0399
	 */
	public void setSsn(String entityID) {
		entityId = entityID;
	}

	/**
	 * Sets the value of the ageReportedUnitCd property.
	 * @param ageReportedUnitCd the new value of the ageReportedUnitCd property
	 */
	public void setAgeReportedUnitCd(String ageReportedUnitCd) {
		this.ageReportedUnitCd = ageReportedUnitCd;
	}

	/**
	 * Access method for the ageReportedUnitCd property.
	 * @return   the current value of the ageReportedUnitCd property
	 */
	public String getAgeReportedUnitCd() {
		return ageReportedUnitCd;
	}

	/**
	 * Sets the value of the nameUseCd property.
	 * @param nameUseCd the new value of the nameUseCd property
	 */
	public void setNameUseCd(String nameUseCd) {
		this.nameUseCd = nameUseCd;
	}

	/**
	 * Access method for the nameUseCd property.
	 * @return   the current value of the nameUseCd property
	 */
	public String getNameUseCd() {
		return nameUseCd;
	}

	/**
	 * Sets the value of the cancelled and statusCodeCancelled properties.
	 * @param cancelled the new value of the cancelled property
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		if (cancelled)
			this.setStatusCodeCancelled("C");
	}

	/**
	 * Access method for the cancelled property.
	 * @return   the current value of the cancelled property
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the value of the locatorUseDesc property.
	 * @param locatorUseDesc the new value of the locatorUseDesc property
	 */
	public void setLocatorUseDesc(String locatorUseDesc) {
		this.locatorUseDesc = locatorUseDesc;
	}

	/**
	 * Access method for the locatorUseDesc property.
	 * @return   the current value of the locatorUseDesc property
	 */
	public String getLocatorUseDesc() {
		return locatorUseDesc;
	}

	/**
	 * Returns the logicalDeleted.
	 * @return boolean
	 * @roseuid 3E8C678F031C
	 */
	public boolean isLogicalDeleted() {
		return logicalDeleted;
	}

	/**
	 * Sets the logicalDeleted.
	 * @param logicalDeleted The logicalDeleted to set
	 * @roseuid 3E8C678F036B
	 */
	public void setLogicalDeleted(boolean logicalDeleted) {
		this.logicalDeleted = logicalDeleted;
	}

	/**
	 * Sets the value of the locatorUseDesc property.
	 * @param locatorUseDesc the new value of the locatorUseDesc property
	 */
	public void setNameUseDesc(String nameUseDesc) {
		this.nameUseDesc = nameUseDesc;
	}

	/**
	 * Access method for the nameUseDesc property.
	 * @return   the current value of the nameUseDesc property
	 */
	public String getNameUseDesc() {
		return nameUseDesc;
	}

	/**
	 * Sets the value of the statusCodeActive property.
	 * @param statusCodeActive the new value of the statusCodeActive property
	 */
	public void setStatusCodeActive(String statusCodeActive) {
		this.statusCodeActive = statusCodeActive;
	}

	/**
	 * Access method for the statusCodeActive property.
	 * @return   the current value of the statusCodeActive property
	 */
	public String getStatusCodeActive() {
		return statusCodeActive;
	}

	/**
	 * Sets the value of the statusCodeCancelled property.
	 * @param statusCodeCancelled the new value of the statusCodeCancelled property
	 */
	public void setStatusCodeCancelled(String statusCodeCancelled) {
		this.statusCodeCancelled = statusCodeCancelled;
	}

	/**
	 * Access method for the statusCodeCancelled property.
	 * @return   the current value of the statusCodeCancelled property
	 */
	public String getStatusCodeCancelled() {
		return statusCodeCancelled;
	}

	/**
	 * Sets the value of the statusCodeInActive property.
	 * @param statusCodeInActive the new value of the statusCodeInActive property
	 */
	public void setStatusCodeInActive(String statusCodeInActive) {
		this.statusCodeInActive = statusCodeInActive;
	}

	/**
	 * Access method for the statusCodeInActive property.
	 * @return   the current value of the statusCodeInActive property
	 */
	public String getStatusCodeInActive() {
		return statusCodeInActive;
	}

	/**
	 * Sets the value of the statusCodeNew property.
	 * @param statusCodeNew the new value of the statusCodeNew property
	 */
	public void setStatusCodeNew(String statusCodeNew) {
		this.statusCodeNew = statusCodeNew;
	}

	/**
	 * Access method for the statusCodeNew property.
	 * @return   the current value of the statusCodeNew property
	 */
	public String getStatusCodeNew() {
		return statusCodeNew;
	}

	/**
	 * Sets the value of the statusCodeSuperCeded property.
	 * @param statusCodeSuperCeded the new value of the statusCodeSuperCeded property
	 */
	public void setStatusCodeSuperCeded(String statusCodeSuperCeded) {
		this.statusCodeSuperCeded = statusCodeSuperCeded;
	}

	/**
	 * Access method for the statusCodeSuperCeded property.
	 * @return   the current value of the statusCodeSuperCeded property
	 */
	public String getStatusCodeSuperCeded() {
		return statusCodeSuperCeded;
	}

	/**
	 * Sets the value of the superceded and statusCodeSuperCeded properties.
	 * @param superceded the new value of the superceded property
	 */
	public void setSuperceded(boolean superceded) {
		this.superceded = superceded;
		if (superceded)
			this.setStatusCodeSuperCeded(recordStatusSuperceded);
	}

	/**
	 * Access method for the superceded property.
	 * @return   the current value of the superceded property
	 */
	public boolean isSuperceded() {
		return superceded;
	}

	/**
	 * Sets the value of the itNew and statusCodeNew properties.
	 * @param newItNew the new value of the itNew property
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
		if (itNew)
			this.setStatusCodeNew("N");
	}

	/**
	 * Access method for the itNew property.
	 * @return   the current value of the itNew property
	 */
	public boolean isItNew() {
		return itNew;
	}

	/**
	 * Sets the value of the rootExtensionTxtOperator property.
	 * @param rootExtensionTxtOperator the new value of the rootExtensionTxtOperator property
	 */
	public void setRootExtensionTxtOperator(String rootExtensionTxtOperator) {
		this.rootExtensionTxtOperator = rootExtensionTxtOperator;
	}

	/**
	 * Access method for the rootExtensionTxtOperator property.
	 * @return   the current value of the rootExtensionTxtOperator property
	 */
	public String getRootExtensionTxtOperator() {
		return rootExtensionTxtOperator;
	}

	/**
	 * Sets the value of the state property.
	 * @param state the new value of the state property
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Access method for the state property.
	 * @return   the current value of the state property
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the value of the raceDescTxt property.
	 * @param raceDescTxt the new value of the raceDescTxt property
	 */
	public void setRaceDescTxt(String raceDescTxt) {
		this.raceDescTxt = raceDescTxt;
	}

	/**
	 * Access method for the raceDescTxt property.
	 * @return   the current value of the raceDescTxt property
	 */
	public String getRaceDescTxt() {
		return raceDescTxt;
	}

	/**
	 * Sets the value of the ethnicDescTxt property.
	 * @param ethnicDescTxt the new value of the ethnicDescTxt property
	 */
	public void setEthnicDescTxt(String ethnicDescTxt) {
		this.ethnicDescTxt = ethnicDescTxt;
	}

	/**
	 * Access method for the ethnicDescTxt property.
	 * @return   the current value of the ethnicDescTxt property
	 */
	public String getEthnicDescTxt() {
		return ethnicDescTxt;
	}

	/**
	 * Sets the value of the ethnicGroupInd property.
	 * @param ethnicGroupInd the new value of the ethnicGroupInd property
	 */
	public void setEthnicGroupInd(String ethnicGroupInd) {
		this.ethnicGroupInd = ethnicGroupInd;
	}

	/**
	 * Access method for the ethnicGroupInd property.
	 * @return   the current value of the ethnicGroupInd property
	 */
	public String getEthnicGroupInd() {
		return ethnicGroupInd;
	}

	/**
	 * Sets the value of the role property.
	 * @param role the new value of the role property
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Access method for the role property.
	 * @return   the current value of the role property
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the value of the dataAccessWhereClause property.
	 * @param dataAccessWhereClause the new value of the dataAccessWhereClause property
	 */
	public void setDataAccessWhereClause(String dataAccessWhereClause) {
		this.dataAccessWhereClause = dataAccessWhereClause;
	}
	/**
	 * Access method for the dataAccessWhereClause property.
	 * @return   the current value of the dataAccessWhereClause property
	 */
	public String getDataAccessWhereClause() {
		return dataAccessWhereClause;
	}

	public String getPatientIDOperator() {
		return this.patientIDOperator;
	}

	public void setPatientIDOperator(String pidOpeartor) {
		this.patientIDOperator = pidOpeartor;
	}

	/**
	 * @return Returns the actId.
	 */
	public String getActId() {
		return actId;
	}
	/**
	 * @param actId The actId to set.
	 */
	public void setActId(String actId) {
		this.actId = actId;
	}
	
	private String oldLocalID;
	


	public String getOldLocalID() {
		return oldLocalID;
	}
	public void setOldLocalID(String oldLocalID) {
		this.oldLocalID = oldLocalID;
	}
	public String getEpiLinkId() {
		return epiLinkId;
	}
	public void setEpiLinkId(String epiLinkId) {
		this.epiLinkId = epiLinkId;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getDateType() {
		//TODO: change by the corresponding value set
		//return CachedDropDowns.getCodedValueForType(getProcessingDecisionLogic());
		//or in EventSearch.jsp change the property codedValue(PHVS_EVN_SEARCH_ABC)
		/*dateType.add("Date of Report");
		dateType.add("Date Received by Public Health");
		dateType.add("Date Specimen Collected");
		dateType.add("Investigation Closed Date");
		dateType.add("Investigation Start Date");
		dateType.add("Last Update Date");
		dateType.add("Notification Create Date");
		*/
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
public String[] getConditionSelected() {
		return conditionSelected;
	}
	public void setConditionSelected(String[] conditionSelected) {
		this.conditionSelected = conditionSelected;
	}
	//Use getProgramArea() instead
	@Deprecated
	public String[] getProgramAreaSelected() {
		//Use getProgramArea() instead
		return programAreaSelected;
	}
	public void setProgramAreaSelected(String[] programAreaSelected) {
		
		this.programAreaSelected = programAreaSelected;
		
	}
	
	public String[] getProgramArea() {
		if (getProgramAreaLabSelected() != null && getProgramAreaLabSelected().length > 1
				|| (getProgramAreaLabSelected() != null && getProgramAreaLabSelected().length == 1
						&& getProgramAreaLabSelected()[0].trim() != ""))
			return getProgramAreaLabSelected();
		if (getProgramAreaMorbSelected() != null && getProgramAreaMorbSelected().length > 1
				|| (getProgramAreaMorbSelected() != null && getProgramAreaMorbSelected().length == 1
						&& getProgramAreaMorbSelected()[0].trim() != ""))
			return getProgramAreaMorbSelected();
		if (getProgramAreaCaseSelected() != null && getProgramAreaCaseSelected().length > 1
				|| (getProgramAreaCaseSelected() != null && getProgramAreaCaseSelected().length == 1
						&& getProgramAreaCaseSelected()[0].trim() != ""))
			return getProgramAreaCaseSelected();
		if (getProgramAreaInvestigationSelected() != null && getProgramAreaInvestigationSelected().length > 1
				|| (getProgramAreaInvestigationSelected() != null && getProgramAreaInvestigationSelected().length == 1
						&& getProgramAreaInvestigationSelected()[0].trim() != ""))
			return getProgramAreaInvestigationSelected();
		return programAreaSelected;
	}
	public String[] getJurisdictionSelected() {
		return jurisdictionSelected;
	}
	public void setJurisdictionSelected(String[] jurisdictionSelected) {
		this.jurisdictionSelected = jurisdictionSelected;
	}
	
	public String getSpecimenSource() {		
		return specimenSource;
	}
	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}
	public String getNumericComparator() {
		return numericComparator;
	}
	public void setNumericComparator(String numericComparator) {
		this.numericComparator = numericComparator;
	}
	public String getNumericResult() {
		return numericResult;
	}
	public void setNumericResult(String numericResult) {
		this.numericResult = numericResult;
	}
	public String getNumericUnit() {
		return numericUnit;
	}
	public void setNumericUnit(String numericUnit) {
		this.numericUnit = numericUnit;
	}
	public String getTextResultComparator() {
		return textResultComparator;
	}
	public void setTextResultComparator(String textResultComparator) {
		this.textResultComparator = textResultComparator;
	}
	public String getTextResult() {
		return textResult;
	}
	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	public String getOrderingProvider() {
		return orderingProvider;
	}
	public void setOrderingProvider(String orderingProvider) {
		this.orderingProvider = orderingProvider;
	}
	public String getOrderingFacility() {
		return orderingFacility;
	}
	public void setOrderingFacility(String orderingFacility) {
		this.orderingFacility = orderingFacility;
	}
	public String getReportingProvider() {
		return reportingProvider;
	}
	public void setReportingProvider(String reportingProvider) {
		this.reportingProvider = reportingProvider;
	}
	public String getReportingFacility() {
		return reportingFacility;
	}
	public void setReportingFacility(String reportingFacility) {
		this.reportingFacility = reportingFacility;
	}
	public String getPregnant() {
		return pregnant;
	}
	public void setPregnant(String pregnant) {
		this.pregnant = pregnant;
	}
	public String[] getOutbreakName() {
		return outbreakName;
	}
	public void setOutbreakName(String[] outbreakName) {
		this.outbreakName = outbreakName;
	}
	public String getNotificationStatus() {
		return notificationStatus;
	}
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public String  getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public String getInvestigationStatus() {
		return investigationStatus;
	}
	public void setInvestigationStatus(String investigationStatus) {
		this.investigationStatus = investigationStatus;
	}
	public String getInvestigator() {
		return investigator;
	}
	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	public String getResultedTestCode() {
		return resultedTestCode;
	}
	public void setResultedTestCode(String resultedTestCode) {
		this.resultedTestCode = resultedTestCode;
	}
	public String getResultedTestDescriptionWithCode() {
		return resultedTestDescriptionWithCode;
	}
	public void setResultedTestDescriptionWithCode(
			String resultedTestDescriptionWithCode) {
		this.resultedTestDescriptionWithCode = resultedTestDescriptionWithCode;
	}
	public String getCodedResult() {
		return codedResult;
	}
	public void setCodedResult(String codedResult) {
		this.codedResult = codedResult;
	}
	public String getInvestigationStatusSelected() {
		return investigationStatusSelected;
	}
	public void setInvestigationStatusSelected(String investigationStatusSelected) {
		this.investigationStatusSelected = investigationStatusSelected;
	}
	public String getCaseStatusListSelected() {
		return caseStatusListSelected;
	}
	public void setCaseStatusListSelected(String caseStatusListSelected) {
		this.caseStatusListSelected = caseStatusListSelected;
	}
	public String getNotificationStatusSelected() {
		return notificationStatusSelected;
	}
	public void setNotificationStatusSelected(String notificationStatusSelected) {
		this.notificationStatusSelected = notificationStatusSelected;
	}
	public String getCurrentProcessStateSelected() {
		return currentProcessStateSelected;
	}
	public void setCurrentProcessStateSelected(String currentProcessStateSelected) {
		this.currentProcessStateSelected = currentProcessStateSelected;
	}public String getDocOperator() {
		
		return DocOperator;
	}
	public void setDocOperator(String docOperator) {
		DocOperator = docOperator;
	}
	
	public String getDocumentCreate() {
		return documentCreate;
	}
	public void setDocumentCreate(String documentCreate) {
		this.documentCreate = documentCreate;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

public String getResultedTestCodeDropdown() {
	return resultedTestCodeDropdown;
}
public void setResultedTestCodeDropdown(String resultedTestCodeDropdown) {
	this.resultedTestCodeDropdown = resultedTestCodeDropdown;
}
public String getResultDescription() {
	return resultDescription;
}
public void setResultDescription(String resultDescription) {
	resultDescription = resultDescription;
}
public String getCodeResultOrganismCode() {
	return codeResultOrganismCode;
}
public void setCodeResultOrganismCode(String codeResultOrganismCode) {
	this.codeResultOrganismCode = codeResultOrganismCode;
}
public String getCodeResultOrganismDescription() {
	return codeResultOrganismDescription;
}
public void setCodeResultOrganismDescription(
		String codeResultOrganismDescription) {
	this.codeResultOrganismDescription = codeResultOrganismDescription;
}
public String getCodeResultOrganismDropdown() {
	return codeResultOrganismDropdown;
}
public void setCodeResultOrganismDropdown(String codeResultOrganismDropdown) {
	this.codeResultOrganismDropdown = codeResultOrganismDropdown;
}
public String getPregnantSelected() {
	return pregnantSelected;
}
public void setPregnantSelected(String pregnantSelected) {
	this.pregnantSelected = pregnantSelected;
}
public String getDateOperator() {
	return dateOperator;
}
public void setDateOperator(String dateOperator) {
	this.dateOperator = dateOperator;
}
public String getElectronicSelected() {
	return electronicSelected;
}

public String getManualSelected() {
	return manualSelected;
}


public String getInternalUserSelected() {
	return internalUserSelected;
}

public String getExternalUserSelected() {
	return externalUserSelected;
}

public String getNewInitialSelected() {
	return newInitialSelected;
}

public String getUpdateSelected() {
	return updateSelected;
}

public String getDocumentUpdateSelected() {
	return documentUpdateSelected;
}
public void setDocumentUpdateSelected(String documentUpdateSelected) {
	this.documentUpdateSelected = documentUpdateSelected;
}
public String getDocumentCreateSelected() {
	return documentCreateSelected;
}
public void setDocumentCreateSelected(String documentCreateSelected) {
	this.documentCreateSelected = documentCreateSelected;
}
public String getProviderFacilitySelected() {
	return providerFacilitySelected;
}
public void setProviderFacilitySelected(String providerFacilitySelected) {
	this.providerFacilitySelected = providerFacilitySelected;
}
public String getRfSelected() {
	return rfSelected;
}
public void setRfSelected(String rfSelected) {
	this.rfSelected = rfSelected;
}
public String getOfSelected() {
	return ofSelected;
}
public void setOfSelected(String ofSelected) {
	this.ofSelected = ofSelected;
}
public String getOpSelected() {
	return opSelected;
}
public void setOpSelected(String opSelected) {
	this.opSelected = opSelected;
}
public String getRpSelected() {
	return rpSelected;
}
public void setRpSelected(String rpSelected) {
	this.rpSelected = rpSelected;
}
public String[] getOutbreakNameSelected() {
	return outbreakNameSelected;
}
public void setOutbreakNameSelected(String[] outbreakNameSelected) {
	this.outbreakNameSelected = outbreakNameSelected;
}
public String getNotificationValuesSelected() {
	return notificationValuesSelected;
}
public void setNotificationValuesSelected(String notificationValuesSelected) {
	this.notificationValuesSelected = notificationValuesSelected;
}
public String getCaseStatusListValuesSelected() {
	return caseStatusListValuesSelected;
}
public void setCaseStatusListValuesSelected(String caseStatusListValuesSelected) {
	this.caseStatusListValuesSelected = caseStatusListValuesSelected;
}
public String getCurrentProcessStateValuesSelected() {
	return currentProcessStateValuesSelected;
}
public void setCurrentProcessStateValuesSelected(
		String currentProcessStateValuesSelected) {
	this.currentProcessStateValuesSelected = currentProcessStateValuesSelected;
}
public String getInvestigatorSelected() {
	return investigatorSelected;
}
public void setInvestigatorSelected(String investigatorSelected) {
	this.investigatorSelected = investigatorSelected;
}
public String getReportingFacilitySelected() {
	return reportingFacilitySelected;
}
public void setReportingFacilitySelected(String reportingFacilitySelected) {
	this.reportingFacilitySelected = reportingFacilitySelected;
}
public String getReportingProviderSelected() {
	return reportingProviderSelected;
}
public void setReportingProviderSelected(String reportingProviderSelected) {
	this.reportingProviderSelected = reportingProviderSelected;
}
public String getEventStatusInitialSelected() {
	return eventStatusInitialSelected;
}
public void setEventStatusInitialSelected(String eventStatusInitialSelected) {
	this.eventStatusInitialSelected = eventStatusInitialSelected;
}
public String getEventStatusUpdateSelected() {
	return eventStatusUpdateSelected;
}
public void setEventStatusUpdateSelected(String eventStatusUpdateSelected) {
	this.eventStatusUpdateSelected = eventStatusUpdateSelected;
}
public String getInvestigatorDescSelected() {
	return investigatorDescSelected;
}
public void setInvestigatorDescSelected(String investigatorDescSelected) {
	this.investigatorDescSelected = investigatorDescSelected;
}
public String getNotificationCodedValuesSelected() {
	return notificationCodedValuesSelected;
}
public void setNotificationCodedValuesSelected(String notificationCodedValuesSelected) {
	this.notificationCodedValuesSelected = notificationCodedValuesSelected;
}
public String getCaseStatusCodedValuesSelected() {
	return caseStatusCodedValuesSelected;
}
public void setCaseStatusCodedValuesSelected(String caseStatusCodedValuesSelected) {
	this.caseStatusCodedValuesSelected = caseStatusCodedValuesSelected;
}
public String getCurrentProcessingStatusValuesSelected() {
	return currentProcessingStatusValuesSelected;
}
public void setCurrentProcessingStatusValuesSelected(String currentProcessingStatusValuesSelected) {
	this.currentProcessingStatusValuesSelected = currentProcessingStatusValuesSelected;
}
public String getDocumentUpdateFullNameSelected() {
	return documentUpdateFullNameSelected;
}
public void setDocumentUpdateFullNameSelected(String documentUpdateFullNameSelected) {
	this.documentUpdateFullNameSelected = documentUpdateFullNameSelected;
}
public String getDocumentCreateFullNameSelected() {
	return documentCreateFullNameSelected;
}
public void setDocumentCreateFullNameSelected(String documentCreateFullNameSelected) {
	this.documentCreateFullNameSelected = documentCreateFullNameSelected;
}
public String getCurrentProcessCodedValuesSelected() {
	return currentProcessCodedValuesSelected;
}
public void setCurrentProcessCodedValuesSelected(
		String currentProcessCodedValuesSelected) {
	this.currentProcessCodedValuesSelected = currentProcessCodedValuesSelected;
}
public String getElectronicValueSelected() {
	return electronicValueSelected;
}
public void setElectronicValueSelected(String electronicValueSelected) {
	this.electronicValueSelected = electronicValueSelected;
}
public String getManualValueSelected() {
	return manualValueSelected;
}
public void setManualValueSelected(String manualValueSelected) {
	this.manualValueSelected = manualValueSelected;
}
public String getInvSelected() {
	return invSelected;
}
public void setInvSelected(String invSelected) {
	this.invSelected = invSelected;
}
public String getInternalValueSelected() {
	return internalValueSelected;
}
public void setInternalValueSelected(String internalValueSelected) {
	this.internalValueSelected = internalValueSelected;
}
public String getExternalValueSelected() {
	return externalValueSelected;
}
public void setExternalValueSelected(String externalValueSelected) {
	this.externalValueSelected = externalValueSelected;
}
public String getTestDescription() {
	return testDescription;
}
public void setTestDescription(String testDescription) {
	this.testDescription = testDescription;
}
public String getResultedTestDescriptionWithCodeValue() {
	return resultedTestDescriptionWithCodeValue;
}
public void setResultedTestDescriptionWithCodeValue(
		String resultedTestDescriptionWithCodeValue) {
	this.resultedTestDescriptionWithCodeValue = resultedTestDescriptionWithCodeValue;
}
public String getCodeResultOrganismDescriptionValue() {
	return codeResultOrganismDescriptionValue;
}
public void setCodeResultOrganismDescriptionValue(
		String codeResultOrganismDescriptionValue) {
	this.codeResultOrganismDescriptionValue = codeResultOrganismDescriptionValue;
}
public String getResultDescriptionValue() {
	return resultDescriptionValue;
}
public void setResultDescriptionValue(String resultDescriptionValue) {
	this.resultDescriptionValue = resultDescriptionValue;
}
public String getMotherLastName() {
	return motherLastName;
}
public void setMotherLastName(String motherLastName) {
	this.motherLastName = motherLastName;
}
public String getMotherFirstName() {
	return motherFirstName;
}
public void setMotherFirstName(String motherFirstName) {
	this.motherFirstName = motherFirstName;
}
public String getMotherMaidenName() {
	return motherMaidenName;
}
public void setMotherMaidenName(String motherMaidenName) {
	this.motherMaidenName = motherMaidenName;
}
public String getMultipleBirthIndicator() {
	return multipleBirthIndicator;
}
public void setMultipleBirthIndicator(String multipleBirthIndicator) {
	this.multipleBirthIndicator = multipleBirthIndicator;
}
public Integer getBirthOrder() {
	return birthOrder;
}
public void setBirthOrder(Integer birthOrder) {
	this.birthOrder = birthOrder;
}
public String getMiddleName() {
	return middleName;
}
public void setMiddleName(String middleName) {
	this.middleName = middleName;
}
public String getCustom() {
	return custom;
}
public void setCustom(String custom) {
	this.custom = custom;
}



public String getProcessedState() {
	return processedState;
}

public void setProcessedState(String processedState) {
	this.processedState = processedState;
}

public String getUnProcessedState() {
	return unProcessedState;
}

public void setUnProcessedState(String unProcessedState) {
	this.unProcessedState = unProcessedState;
}

}