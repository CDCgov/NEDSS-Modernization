package gov.cdc.nedss.vaccination.iis.dt;

import java.sql.Timestamp;
import java.util.ArrayList;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

public class PatientSearchResultDT extends AbstractVO implements RootDTInterface{
	private String registryPatientID;
	private String patientName;
	private String ageDobSex;
	private String address;
	private String phone; // to display in UI.
	private String phoneNbr; //Home phone number
	private String phoneTypeCd; // PH or CP
	private String workPhoneNbr; 
	private String workPhoneExt;
	private String motherName;
	//private String motherMaidenName;
	private String motherMaidenFirstName;
	private String motherMaidenLastName;
	private String registryPatientIDLink;
	private String sex;
	private String dob;
	private String dobYYYYMMDD;
	private String patientNameWithPrefix;
	private String firstName;
	private String lastName;
	private String middleName;
	private String streetAddress;
	private String streetAddress2;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String sexCd;
	private String ageAtVaccination;
	private String ageAtVaccinationUnit;
	private String countryCd;
	private String cntyCd;
	private ArrayList<String> raceList;
	private String ethnicity;
	private String multipleBirthIndicator;
	private Integer birthOrder;
	private String maritalStatus;
	private String deceasedInd;
	private Timestamp deceasedTime;
	private String suffix;
	private String prefix;
	private String degree;
	private Timestamp personNameEffectiveDate;
	private Timestamp personNameExpirationDate;
	
	private String listOfregistryPatientID;
	private String assigningAuthorities;
	private String identifierTypeCodes;
	private String assigningFacilities;
	private String effectiveDates;	//For Entity_id
	private String expirationDates; //For Entity_id
	
	private Timestamp addressFromDate;
	private Timestamp addressToDate;
	
	private Timestamp personAsOfDate;
	
	public String getRegistryPatientID() {
		return registryPatientID;
	}
	public void setRegistryPatientID(String registryPatientID) {
		this.registryPatientID = registryPatientID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getAgeDobSex() {
		return ageDobSex;
	}
	public void setAgeDobSex(String ageDobSex) {
		this.ageDobSex = ageDobSex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getRegistryPatientIDLink() {
		return registryPatientIDLink;
	}
	public void setRegistryPatientIDLink(String registryPatientIDLink) {
		this.registryPatientIDLink = registryPatientIDLink;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getPatientNameWithPrefix() {
		return patientNameWithPrefix;
	}
	public void setPatientNameWithPrefix(String patientNameWithPrefix) {
		this.patientNameWithPrefix = patientNameWithPrefix;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getStreetAddress2() {
		return streetAddress2;
	}
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCntyCd() {
		return cntyCd;
	}
	public void setCntyCd(String cntyCd) {
		this.cntyCd = cntyCd;
	}
	public String getSexCd() {
		return sexCd;
	}
	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}
	public String getAgeAtVaccination() {
		return ageAtVaccination;
	}
	public void setAgeAtVaccination(String ageAtVaccination) {
		this.ageAtVaccination = ageAtVaccination;
	}
	public String getAgeAtVaccinationUnit() {
		return ageAtVaccinationUnit;
	}
	public void setAgeAtVaccinationUnit(String ageAtVaccinationUnit) {
		this.ageAtVaccinationUnit = ageAtVaccinationUnit;
	}
	public String getListOfregistryPatientID() {
		return listOfregistryPatientID;
	}
	public void setListOfregistryPatientID(String listOfregistryPatientID) {
		this.listOfregistryPatientID = listOfregistryPatientID;
	}
	public String getAssigningAuthorities() {
		return assigningAuthorities;
	}
	public void setAssigningAuthorities(String assigningAuthorities) {
		this.assigningAuthorities = assigningAuthorities;
	}
	public String getIdentifierTypeCodes() {
		return identifierTypeCodes;
	}
	public void setIdentifierTypeCodes(String identifierTypeCodes) {
		this.identifierTypeCodes = identifierTypeCodes;
	}
	public String getAssigningFacilities() {
		return assigningFacilities;
	}
	public void setAssigningFacilities(String assigningFacilities) {
		this.assigningFacilities = assigningFacilities;
	}
	public String getCountryCd() {
		return countryCd;
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	public String getPhoneNbr() {
		return phoneNbr;
	}
	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}
	public ArrayList<String> getRaceList() {
		return raceList;
	}
	public void setRaceList(ArrayList<String> raceList) {
		this.raceList = raceList;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	/*public String getMotherMaidenName() {
		return motherMaidenName;
	}
	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}*/
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
	public String getDobYYYYMMDD() {
		return dobYYYYMMDD;
	}
	public void setDobYYYYMMDD(String dobYYYYMMDD) {
		this.dobYYYYMMDD = dobYYYYMMDD;
	}
	public String getMotherMaidenFirstName() {
		return motherMaidenFirstName;
	}
	public void setMotherMaidenFirstName(String motherMaidenFirstName) {
		this.motherMaidenFirstName = motherMaidenFirstName;
	}
	public String getMotherMaidenLastName() {
		return motherMaidenLastName;
	}
	public void setMotherMaidenLastName(String motherMaidenLastName) {
		this.motherMaidenLastName = motherMaidenLastName;
	}
	public String getWorkPhoneNbr() {
		return workPhoneNbr;
	}
	public void setWorkPhoneNbr(String workPhoneNbr) {
		this.workPhoneNbr = workPhoneNbr;
	}
	public String getWorkPhoneExt() {
		return workPhoneExt;
	}
	public void setWorkPhoneExt(String workPhoneExt) {
		this.workPhoneExt = workPhoneExt;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getDeceasedInd() {
		return deceasedInd;
	}
	public void setDeceasedInd(String deceasedInd) {
		this.deceasedInd = deceasedInd;
	}
	public Timestamp getDeceasedTime() {
		return deceasedTime;
	}
	public void setDeceasedTime(Timestamp deceasedTime) {
		this.deceasedTime = deceasedTime;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Timestamp getPersonNameEffectiveDate() {
		return personNameEffectiveDate;
	}
	public void setPersonNameEffectiveDate(Timestamp personNameEffectiveDate) {
		this.personNameEffectiveDate = personNameEffectiveDate;
	}
	public Timestamp getPersonNameExpirationDate() {
		return personNameExpirationDate;
	}
	public void setPersonNameExpirationDate(Timestamp personNameExpirationDate) {
		this.personNameExpirationDate = personNameExpirationDate;
	}
	public String getPhoneTypeCd() {
		return phoneTypeCd;
	}
	public void setPhoneTypeCd(String phoneTypeCd) {
		this.phoneTypeCd = phoneTypeCd;
	}
	public String getEffectiveDates() {
		return effectiveDates;
	}
	public void setEffectiveDates(String effectiveDates) {
		this.effectiveDates = effectiveDates;
	}
	public String getExpirationDates() {
		return expirationDates;
	}
	public void setExpirationDates(String expirationDates) {
		this.expirationDates = expirationDates;
	}
	public Timestamp getAddressFromDate() {
		return addressFromDate;
	}
	public void setAddressFromDate(Timestamp addressFromDate) {
		this.addressFromDate = addressFromDate;
	}
	public Timestamp getAddressToDate() {
		return addressToDate;
	}
	public void setAddressToDate(Timestamp addressToDate) {
		this.addressToDate = addressToDate;
	}
	public Timestamp getPersonAsOfDate() {
		return personAsOfDate;
	}
	public void setPersonAsOfDate(Timestamp personAsOfDate) {
		this.personAsOfDate = personAsOfDate;
	}
	
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
