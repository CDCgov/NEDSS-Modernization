package gov.cdc.nedss.entity.person.vo;

public class ProviderDisplayVO {
	
	private String fullName;

	private String address;

	private String telephone;

	private String id;
	
	private String providerUID ;
	
	
	public void reset() {
		fullName = null;
		address = null;
		telephone = null;
		id = null;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProviderUID() {
		return providerUID;
	}


	public void setProviderUID(String providerUID) {
		this.providerUID = providerUID;
	}

}
