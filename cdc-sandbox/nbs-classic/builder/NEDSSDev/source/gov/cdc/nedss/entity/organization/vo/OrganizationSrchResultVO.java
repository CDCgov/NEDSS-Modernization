/**
 * Title: OrganizationSrchResultVO helper class.
 * Description: A helper class for organization search result value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.vo;

import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.util.NEDSSConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

public class OrganizationSrchResultVO implements Serializable {

	  private java.util.Collection<Object>  organizationNameColl;
	  private java.util.Collection<Object>  organizationLocatorsColl;
	  private Long organizationUID;
	  private String recordStatusCd;
	  private String organizationId;
	  private String countyCd;
	  private java.util.Collection<Object>  organizationIdColl;
	  private String name;
	  private String address;
	  private String telephone;
	  private String id;
	  private String actionLink;
	  private Integer versionCtrlNbr;
	  
  public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}

public OrganizationSrchResultVO() {
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  /**
    * Set method for the theOrganizationNameColl property.
    * Sets all the associated names for a organization
    * @void Sets the current value of the theOrganizationNameColl property
    */
  public void setOrganizationNameColl(java.util.Collection<Object>  organizationNameColl) {
    this.organizationNameColl = organizationNameColl;
  }

  /**
    * Access method for the theOrganizationNameColl property.
    * returns all the associated names for a organization
    * @return the current value of the theOrganizationNameColl property
    */
  public java.util.Collection<Object>  getOrganizationNameColl() {
    return organizationNameColl;
  }

  /**
    * Set method for the theOrganizationLocatorColl property.
    * Sets all the locators associated with a organization
    * @return void
    */
  public void setOrganizationLocatorsColl(java.util.Collection<Object>  organizationLocatorsColl) {
    this.organizationLocatorsColl = organizationLocatorsColl;
  }

  /**
    * Access method for the theOrganizationLocatorColl property.
    * returns all the locators associated with a organization
    * @return the current value of the theOrganizationLocatorColl property
    */
  public java.util.Collection<Object>  getOrganizationLocatorsColl() {
    return organizationLocatorsColl;
  }

  /**
   * Sets the value of the organizationUID property.
   * @param organizationUID the new value of the organizationUID property
   */
  public void setOrganizationUID(Long organizationUID) {
    this.organizationUID = organizationUID;
  }

  /**
    * Access method for the organizationUID property.
    * @return   the current value of the organizationUID property
    */
  public Long getOrganizationUID() {
    return organizationUID;
  }

  /* For sorting resultset by organization name this method is required. It will return organization
     name for name type=Legal". returns null if that name type does not exist
  */
  /**
    * Access method for an organization's current home address
    * For sorting resultset by organization address this method is required. It will return organization
    * address for address type=Current and address use=Home". returns null if that type and use does not exist
    * @return   the current home address value of an organization
    */
  public String getaOrganizationAddress(){
    if(organizationLocatorsColl != null){
     Iterator<Object>  addressIter = organizationLocatorsColl.iterator();
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

  /* For sorting resultset by organization telephone number this method is required. It will return organization
     telephone number for telephone number type=Phone and use=Home". returns null if that type and use does not exist
  */
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
    * Access method for the countyCd property.
    * @return   the current value of the countyCd property
    */
  public String getCountyCd() {
	return countyCd;
  }

  /**
   * Sets the value of the countyCd property.
   * @param countyCd the new value of the countyCd property
   */
  public void setCountyCd(String countyCd) {
	this.countyCd = countyCd;
  }

  /**
    * Access method for the organizationId property.
    * @return   the current value of the organizationId property
    */
  public String getOrganizationId() {
    return organizationId;
  }

  /**
   * Sets the value of the organizationIdColl property.
   * @param organizationIdColl the new value of the organizationIdColl property
   */
  public void setOrganizationIdColl(java.util.Collection<Object>  organizationIdColl) {
    this.organizationIdColl = organizationIdColl;
  }

  /**
    * Access method for the organizationIdColl property.
    * @return   the current value of the organizationIdColl property
    */
  public java.util.Collection<Object>  getOrganizationIdColl() {
    return organizationIdColl;
  }

  /**
   * Sets the value of the organizationId property.
   * @param organizationId the new value of the organizationId property
   */
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
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

public String getActionLink() {
	return actionLink;
}

public void setActionLink(String actionLink) {
	this.actionLink = actionLink;
}

}