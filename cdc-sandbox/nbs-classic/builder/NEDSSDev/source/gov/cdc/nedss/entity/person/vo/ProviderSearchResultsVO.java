package gov.cdc.nedss.entity.person.vo;

import java.io.Serializable;

public class ProviderSearchResultsVO implements Serializable {


	private Long personUID;
	
	private String fullName;

	private String address;

	private String telephone;

	private String id;
	
	private String actionLink;

	
	public void reset() {
		fullName = null;
		address = null;
		telephone = null;
		id = null;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getActionLink() {
	   	return actionLink;
	   }

	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}

	public Long getPersonUID() {
		return personUID;
	}

	public void setPersonUID(Long personUID) {
		this.personUID = personUID;
	}  	
}