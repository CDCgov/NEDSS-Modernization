package gov.cdc.nedss.entity.organization.vo;

public class OrganizationDisplayVO {

	private String name;

	private String address;

	private String telephone;

	private String id;

	private String organizationUid ;


	public void reset() {
		name = null;
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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizationUid() {
		return organizationUid;
	}

	public void setOrganizationUid(String organizationUid) {
		this.organizationUid = organizationUid;
	}

}
