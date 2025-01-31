/**
 * Title: OrganizationList helper class.
 * Description:	This class helps to set or get an organization record
 * stored in an organization list object.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.util;

import java.io.*;
import java.lang.*;

public class OrganizationList implements Serializable{
  int organizationId;
  String organizationName;
  String organizationAddress;
  String organizationCity;
  String organizationCounty;
  String organizationState;
  Integer organizationAge;
  String organizationEthnicity;
  String organizationSex;
  String organizationStatus;
  String organizationLocatorCd;
  String organizationSsn;
  String organizationDob;
  String organizationNameCd;

  public OrganizationList() {
  }

 /**
  * Access method for the organizationId property.
  * @return   the current value of the organizationId property
  */
  public int getOrganizationId() {
    return organizationId;
  }

 /**
  * Access method for the organizationName property.
  * @return   the current value of the organizationName property
  */
  public String getOrganizationName() {
    return organizationName;
  }

 /**
  * Access method for the organizationAddress property.
  * @return   the current value of the organizationAddress property
  */
  public String getOrganizationAddress() {
    return organizationAddress;
  }

 /**
  * Access method for the organizationCity property.
  * @return   the current value of the organizationCity property
  */
  public String getOrganizationCity() {
    return organizationCity;
  }

 /**
  * Access method for the organizationCounty property.
  * @return   the current value of the organizationCounty property
  */
  public String getOrganizationCounty() {
    return organizationCounty;
  }

 /**
  * Access method for the organizationState property.
  * @return   the current value of the organizationState property
  */
  public String getOrganizationState() {
    return organizationState;
  }

 /**
  * Access method for the organizationAge property.
  * @return   the current value of the organizationAge property
  */
  public Integer getOrganizationAge() {
    return organizationAge;
  }

 /**
  * Access method for the organizationEthnicity property.
  * @return   the current value of the organizationEthnicity property
  */
  public String getOrganizationEthnicity() {
    return organizationEthnicity;
  }

 /**
  * Access method for the organizationSex property.
  * @return   the current value of the organizationSex property
  */
  public String getOrganizationSex() {
    return organizationSex;
  }

 /**
  * Access method for the organizationStatus property.
  * @return   the current value of the organizationStatus property
  */
  public String getOrganizationStatus() {
    return organizationStatus;
  }

 /**
  * Access method for the organizationLocatorCd property.
  * @return   the current value of the organizationLocatorCd property
  */
  public String getLocatorCd() {
    return organizationLocatorCd;
  }

 /**
  * Access method for the organizationSsn property.
  * @return   the current value of the organizationSsn property
  */
  public String getOrganizationSsn() {
    return organizationSsn;
  }

 /**
  * Access method for the organizationDob property.
  * @return   the current value of the organizationDob property
  */
  public String getOrganizationDob() {
    return organizationDob;
  }

 /**
  * Access method for the organizationNameCd property.
  * @return   the current value of the organizationNameCd property
  */
  public String getOrganizationNameCd() {
    return organizationNameCd;
  }

 /**
  * Sets the value of the organizationId property
  * @param newId the new value of the organizationId property
  */
  public void setOrganizationId(int newId) {
    organizationId = newId;
  }

 /**
  * Sets the value of the organizationName property
  * @param newName the new value of the organizationName property
  */
  public void setOrganizationName(String newName) {
    organizationName = newName;
  }

 /**
  * Sets the value of the organizationAddress property
  * @param newAddress the new value of the organizationAddress property
  */
  public void setOrganizationAddress(String newAddress) {
    organizationAddress = newAddress;
  }

 /**
  * Sets the value of the organizationCity property
  * @param newCity the new value of the organizationCity property
  */
  public void setOrganizationCity(String newCity) {
    organizationCity = newCity;
  }

 /**
  * Sets the value of the organizationCounty property
  * @param newCounty the new value of the organizationCounty property
  */
  public void setOrganizationCounty(String newCounty) {
    organizationCounty = newCounty;
  }

 /**
  * Sets the value of the organizationState property
  * @param newState the new value of the organizationState property
  */
  public void setOrganizationState(String newState) {
    organizationState = newState;
  }

 /**
  * Sets the value of the organizationEthnicity property
  * @param newEthnicity the new value of the organizationEthnicity property
  */
  public void setOrganizationEthnicity(String newEthnicity) {
    organizationEthnicity = newEthnicity;
  }

 /**
  * Sets the value of the organizationAge property
  * @param newAge the new value of the organizationAge property
  */
  public void setOrganizationAge(Integer newAge) {
    organizationAge = newAge;
  }

 /**
  * Sets the value of the organizationSex property
  * @param newSex the new value of the organizationSex property
  */
  public void setOrganizationSex(String newSex) {
    organizationSex = newSex;
  }

 /**
  * Sets the value of the organizationStatus property
  * @param newStatus the new value of the organizationStatus property
  */
  public void setOrganizationStatus(String newStatus) {
    organizationStatus = newStatus;
  }

 /**
  * Sets the value of the organizationLocatorCd property
  * @param newLocatorCd the new value of the organizationLocatorCd property
  */
  public void setOrganizationLocatorCd(String newLocatorCd) {
    organizationLocatorCd = newLocatorCd;
  }

 /**
  * Sets the value of the organizationSsn property
  * @param newSsn the new value of the organizationSsn property
  */
  public void setOrganizationSsn(String newSsn) {
    organizationSsn = newSsn;
  }

 /**
  * Sets the value of the organizationDob property
  * @param newDob the new value of the organizationDob property
  */
  public void setOrganizationDob(String newDob) {
    organizationDob = newDob;
  }

 /**
  * Sets the value of the organizationNameCd property
  * @param newNameCd the new value of the organizationNameCd property
  */
  public void setOrganizationNameCd(String newNameCd) {
    organizationNameCd = newNameCd;
  }
}