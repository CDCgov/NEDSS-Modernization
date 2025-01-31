package gov.cdc.nedss.webapp.nbs.form.organization;

import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class EntitySearchAddOrganizationForm extends BaseForm{


	private String quickCode;
	private String name;
	private String streetAddress1;
	private String streetAddress2;
	private String city ;
	private String zip ;
	private String country ;
	private String state;
	private ArrayList<Object> stateList ;
	private ArrayList<Object> countryList;
	private String county;
	private ArrayList<Object>  countyList ;
	private String  telephone;
	private String ext ;
	private ArrayList<Object> dwrCounties = new ArrayList<Object> ();
	private String eMail ;
	//private ArrayList<Object> errorList = new ArrayList<Object> ();

	public String getQuickCode() {
		return quickCode;
	}

	public void setQuickCode(String quickCode) {
		this.quickCode = quickCode;
	}


	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTelephoneNumber() {
		return telephone;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephone = telephoneNumber;
	}



	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public void reset(){
		 quickCode = null;
		 name= null;
		 streetAddress1= null;
		 streetAddress2= null;
		 city = null;
		 zip = null;
		 country = null;
		 state= null;
		 county= null;
		 telephone= null;
		 ext= null ;
		 eMail = null;


	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		 ActionErrors errors = super.validate(mapping,request);
		    if(errors == null)
		    	errors = new ActionErrors();
	      return errors;
	  }
	public Object getCodedValue(String codesetNm) {
		return CachedDropDowns.getCodedValueForType(codesetNm);
	}
	public ArrayList<Object> getDwrCountiesForState(String state) {
		this.dwrCounties = CachedDropDowns.getCountyCodes(state);
		return dwrCounties;
	}

	public void setDwrCounties(ArrayList<Object>  dwrCounties) {
		this.dwrCounties = dwrCounties;
	}

	public ArrayList<Object> getDwrCounties() {
		return dwrCounties;
	}

	public ArrayList<Object> getStateList(){
		this.stateList = CachedDropDowns.getStateList();
		return stateList;
	}
	public ArrayList<Object> getCountryList(){
		this.countryList = CachedDropDowns.getCountryList();
		return countryList;
	}

//	public List<Object> getStateList() {
//		return stateList;
//	}

	public void setStateList(ArrayList<Object>  stateList) {
		this.stateList = stateList;
	}

	public List<Object> getCountyList() {
		return countyList;
	}

	public void setCountyList(ArrayList<Object>  countyList) {
		this.countyList = countyList;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String mail) {
		eMail = mail;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
