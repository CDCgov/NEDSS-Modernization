/**
 * Title: ObservationSearchVO helper class.
 * Description: A helper class for person search value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.observation.vo;

import java.sql.*;
import java.io.*;
import gov.cdc.nedss.util.*;

public  class ObservationSearchVO implements Serializable
{
  private String labName;
  private String searchType;

  public String getLabName(){
	return labName;
  }

  private void setLabName(String labName){
	this.labName = labName;
  }

  public String getSearchType(){
	return this.searchType;
  }

  public void setSearchType(String searchType){
//System.out.println("\n\n SETTING THE SERARCH TYPE --> " + searchType + " \n\n");
	this.searchType = searchType;
  }
  
  
  
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
  private String birthTime;
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
  private String SSN;

  /**
   *
   */
  public ObservationSearchVO() {
  }

  /**
   * Sets the value of the lastName property.
   * @param newName the new value of the lastName property
   */
  public void setLastName(String newName){
    lastName = newName;
  }

  /**
   * Sets the value of the firstName property.
   * @param newName the new value of the firstName property
   */
  public void setFirstName(String newName){
    firstName = newName;
  }

  /**
   * Sets the value of the cityDescTxt property.
   * @param cityDescTxt the new value of the cityDescTxt property
   */
  public void setCityDescTxt(String cityDescTxt){
     this.cityDescTxt = cityDescTxt;
  }

  /**
   * Sets the value of the streetAddr1 property.
   * @param streetAddr1 the new value of the streetAddr1 property
   */
  public void setStreetAddr1(String streetAddr1){
    this.streetAddr1 = streetAddr1;
  }

  /**
   * Sets the value of the currentSex property.
   * @param newCurrentSex the new value of the currentSex property
   */
  public void setCurrentSex(String newCurrentSex){
    currentSex = newCurrentSex;
  }

  /**
   * Sets the value of the birthTime property.
   * @param birthTime the new value of the birthTime property
   */
  public void setBirthTime(String birthTime){
    this.birthTime = birthTime;
  }

  /**
   * Access method for the lastName property.
   * @return   the current value of the lastName property
   */
  public String getLastName(){
    return lastName ;
  }

  /**
   * Access method for the firstName property.
   * @return   the current value of the firstName property
   */
  public String getFirstName(){
    return firstName ;
  }




  /**
   * Access method for the cityDescTxt property.
   * @return   the current value of the cityDescTxt property
   */
  public String getCityDescTxt(){
    return cityDescTxt;
  }

  /**
   * Access method for the currentSex property.
   * @return   the current value of the currentSex property
   */
  public String getCurrentSex(){
    return currentSex ;
  }

  /**
   * Access method for the birthTime property.
   * @return   the current value of the birthTime property
   */
  public String getBirthTime(){
    return birthTime;
  }

  /**
   * Access method for the streetAddr1 property.
   * @return   the current value of the streetAddr1 property
   */
  public String getStreetAddr1(){
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
//System.out.println("\n\n SETTING THE OPERATOR --> " + lastNameOperator + " \n\n");

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
    this.setStatusCodeActive( recordStatusActive);
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
    this.setStatusCodeInActive( recordStatusInActive);
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
  public String getSSN()
  {
       return SSN;
  }

  /**
 * Sets the sSN.
 * @param sSN The sSN to set
 * @roseuid 3E8C678F0399
 */
 public void setSSN(String sSN)
 {
     SSN = sSN;
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
  public boolean isLogicalDeleted()
  {
       return logicalDeleted;
  }


  /**
   * Sets the logicalDeleted.
   * @param logicalDeleted The logicalDeleted to set
   * @roseuid 3E8C678F036B
   */
  public void setLogicalDeleted(boolean logicalDeleted)
  {
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

 }