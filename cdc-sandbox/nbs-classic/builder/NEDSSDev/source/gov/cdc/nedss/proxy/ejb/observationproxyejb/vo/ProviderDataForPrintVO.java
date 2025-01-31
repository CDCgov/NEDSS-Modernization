package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import java.io.Serializable;

/**
 * Utility class for print CDC form(release 4.5). This class contains many specific fields that are specific mapping required for CDC.
 * Thsi VO will only be populated for CDC print form purposes. 
 * @author Pradeep Kumar Shamra
 *
 */
public class ProviderDataForPrintVO implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -6215612766789766219L;
	private String providerStreetAddress1= "";
	  private String providerCity;
	  private String providerState;
	  private String providerZip;
	  private String providerPhone= "";
	  private String providerPhoneExtension;
	  private String facilityName= "";
	  private String facilityCity;
	  private String facilityState;
	  private String facilityAddress1= "";
	  private String facilityAddress2= "";
	  private String facility= "";
	  private String facilityZip= "";
	  private String facilityPhoneExtension;
	  private String facilityPhone= "";
	  
	  
	public String getFacilityPhone() {
		return facilityPhone;
	}
	public void setFacilityPhone(String facilityPhone) {
		this.facilityPhone = facilityPhone;
	}
	public String getFacilityCity() {
		return facilityCity;
	}
	public void setFacilityCity(String facilityCity) {
		this.facilityCity = facilityCity;
	}
	public String getFacilityState() {
		return facilityState;
	}
	public void setFacilityState(String facilityState) {
		this.facilityState = facilityState;
	}
	public String getFacilityZip() {
		return facilityZip;
	}
	public void setFacilityZip(String facilityZip) {
		this.facilityZip = facilityZip;
	}
	public String getFacilityAddress1() {
		return facilityAddress1;
	}
	public void setFacilityAddress1(String facilityAddress1) {
		this.facilityAddress1 = facilityAddress1;
	}
	public String getFacilityAddress2() {
		return facilityAddress2;
	}
	public void setFacilityAddress2(String facilityAddress2) {
		this.facilityAddress2 = facilityAddress2;
	}
	public String getFacility() {
		return facility;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public String getFacilityPhoneExtension() {
		return facilityPhoneExtension;
	}
	public void setFacilityPhoneExtension(String facilityPhoneExtension) {
		this.facilityPhoneExtension = facilityPhoneExtension;
	}
	public String getProviderStreetAddress1() {
		return providerStreetAddress1;
	}
	public void setProviderStreetAddress1(String providerStreetAddress1) {
		this.providerStreetAddress1 = providerStreetAddress1;
	}
	public String getProviderCity() {
		return providerCity;
	}
	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	public String getProviderZip() {
		return providerZip;
	}
	public void setProviderZip(String providerZip) {
		this.providerZip = providerZip;
	}
	public String getProviderPhone() {
		return providerPhone;
	}
	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
	}
	public String getProviderPhoneExtension() {
		return providerPhoneExtension;
	}
	public void setProviderPhoneExtension(String providerPhoneExtension) {
		this.providerPhoneExtension = providerPhoneExtension;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	  
	  
}
