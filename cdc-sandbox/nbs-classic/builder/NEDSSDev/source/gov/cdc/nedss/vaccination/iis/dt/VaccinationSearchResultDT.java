package gov.cdc.nedss.vaccination.iis.dt;

import gov.cdc.nedss.nnd.dt.VaccinationDT;

public class VaccinationSearchResultDT extends VaccinationDT{

	private String registryPatientID;
	private String provider;
	private String vaccineAdministered;
	private String lotInformation;
	private String vaccAdminDtS;
	private String vaccSeqNbr; // use to extract selected vaccinations
	private String providerFirstName;
	private String providerLastName;
	private String providerMiddleInitial;
	private String providerSuffix;
	private String providerPrefix;
	private String providerDegree;
	private String providerId;
	private String idTypeCd;
	private String providerAssigningAuthority;
	
	private String orgName;
	private String orgAddress1;
	private String orgAddress2;
	private String orgCity;
	private String orgState;
	private String orgZip;
	private String orgCountry;
	private String orgLocatorDescTxt;
	private String hl7msg;
	private String vaccinationIdentifier; // ORC-3 - Filler Order number (Entity identifier (ST))
	private String vaccSourceNm;
	private String vaccDocNm;
	private String vaccMessageId;
	
	public String getRegistryPatientID() {
		return registryPatientID;
	}
	public void setRegistryPatientID(String registryPatientID) {
		this.registryPatientID = registryPatientID;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getVaccineAdministered() {
		return vaccineAdministered;
	}
	public void setVaccineAdministered(String vaccineAdministered) {
		this.vaccineAdministered = vaccineAdministered;
	}
	public String getLotInformation() {
		return lotInformation;
	}
	public void setLotInformation(String lotInformation) {
		this.lotInformation = lotInformation;
	}
	public String getVaccAdminDtS() {
		return vaccAdminDtS;
	}
	public void setVaccAdminDt(String vaccAdminDtS) {
		this.vaccAdminDtS = vaccAdminDtS;
	}
	public String getVaccSeqNbr() {
		return vaccSeqNbr;
	}
	public void setVaccSeqNbr(String vaccSeqNbr) {
		this.vaccSeqNbr = vaccSeqNbr;
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
	public String getProviderMiddleInitial() {
		return providerMiddleInitial;
	}
	public void setProviderMiddleInitial(String providerMiddleInitial) {
		this.providerMiddleInitial = providerMiddleInitial;
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
	public String getProviderDegree() {
		return providerDegree;
	}
	public void setProviderDegree(String providerDegree) {
		this.providerDegree = providerDegree;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getIdTypeCd() {
		return idTypeCd;
	}
	public void setIdTypeCd(String idTypeCd) {
		this.idTypeCd = idTypeCd;
	}
	public String getProviderAssigningAuthority() {
		return providerAssigningAuthority;
	}
	public void setProviderAssigningAuthority(String providerAssigningAuthority) {
		this.providerAssigningAuthority = providerAssigningAuthority;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgAddress1() {
		return orgAddress1;
	}
	public void setOrgAddress1(String orgAddress1) {
		this.orgAddress1 = orgAddress1;
	}
	public String getOrgAddress2() {
		return orgAddress2;
	}
	public void setOrgAddress2(String orgAddress2) {
		this.orgAddress2 = orgAddress2;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getOrgState() {
		return orgState;
	}
	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}
	public String getOrgZip() {
		return orgZip;
	}
	public void setOrgZip(String orgZip) {
		this.orgZip = orgZip;
	}
	public String getOrgCountry() {
		return orgCountry;
	}
	public void setOrgCountry(String orgCountry) {
		this.orgCountry = orgCountry;
	}
	public String getOrgLocatorDescTxt() {
		return orgLocatorDescTxt;
	}
	public void setOrgLocatorDescTxt(String orgLocatorDescTxt) {
		this.orgLocatorDescTxt = orgLocatorDescTxt;
	}
	public String getHl7msg() {
		return hl7msg;
	}
	public void setHl7msg(String hl7msg) {
		this.hl7msg = hl7msg;
	}
	public String getVaccinationIdentifier() {
		return vaccinationIdentifier;
	}
	public void setVaccinationIdentifier(String vaccinationIdentifier) {
		this.vaccinationIdentifier = vaccinationIdentifier;
	}
	public String getVaccSourceNm() {
		return vaccSourceNm;
	}
	public void setVaccSourceNm(String vaccSourceNm) {
		this.vaccSourceNm = vaccSourceNm;
	}
	public String getVaccDocNm() {
		return vaccDocNm;
	}
	public void setVaccDocNm(String vaccDocNm) {
		this.vaccDocNm = vaccDocNm;
	}
	public String getVaccMessageId() {
		return vaccMessageId;
	}
	public void setVaccMessageId(String vaccMessageId) {
		this.vaccMessageId = vaccMessageId;
	}
	
}
