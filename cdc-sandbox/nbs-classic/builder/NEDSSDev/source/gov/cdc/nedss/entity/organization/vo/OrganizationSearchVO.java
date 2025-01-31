/**
 * Title: OrganizationSearchVO helper class.
 * Description: A helper class for organization search value object
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.organization.vo;

import java.sql.*;
import java.io.*;
import gov.cdc.nedss.util.*;

public class OrganizationSearchVO implements Serializable
{
  private Long localUID;
  private String localID;
  private String nmTxt;
  private String streetAddr1;
  private String cityDescTxt;
  private String zipCd;
  private String typeCd;
  private String roleCd;
  private String rootExtensionTxt;

  private String nmTxtOperator;
  private String cityDescTxtOperator;
  private String stateCdOperator;
  private String zipCdOperator;
  private String typeCdOperator;
  private String rootExtensionTxtOperator;
  private String roleCdOperator;
  private boolean active = false;
  private boolean inActive = false;
  private String statusCodeActive;
  private String statusCodeInActive;
  private String statusCodeSuperCeded;
  private boolean itNew = false;
  private boolean cancelled = false;
  private boolean superceded = false;
  private String streetAddr1Operator;
  private String progAreaAccessPriv;
  private String orgAccessPriv;
  private String statusCodeNew;
  private String statusCodeCancelled;
  private String stateCd;
  private String dataAccessWhereClause;
  private String recordStatusCdIn;
  private String recordStatusActive = NEDSSConstants.RECORD_STATUS_ACTIVE;
  private String recordStatusInActive = NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE;
  private String phoneNbrTxt;


  public OrganizationSearchVO() {
  }

 /**
  * Sets the value of the nmTxt property.
  * @param nmTxt the new value of the nmTxt property
  */
  public void setNmTxt(String nmTxt){
    this.nmTxt = nmTxt;
  }

 /**
  * Sets the value of the cancelled and setStatusCodeCancelled properties.
  * @param cancelled the new value of the cancelled property
  */
  public void setCancelled(boolean cancelled)
  {
    this.cancelled = cancelled;
    if (cancelled)
      this.setStatusCodeCancelled("C");
  }

 /**
  * Sets the value of the streetAddr1 property.
  * @param streetAddr1 the new value of the streetAddr1 property
  */
  public void setStreetAddr1(String streetAddr1){
    this.streetAddr1 = streetAddr1;
  }

 /**
  * Sets the value of the cityDescTxt property.
  * @param cityDescTxt the new value of the cityDescTxt property
  */
  public void setCityDescTxt(String cityDescTxt){
     this.cityDescTxt = cityDescTxt;
  }

 /**
  * Sets the value of the zipCd property.
  * @param zipCd the new value of the zipCd property
  */
  public void setZipCd(String zipCd){
    this.zipCd = zipCd;
  }

 /**
  * Sets the value of the typeCd property.
  * @param typeCd the new value of the typeCd property
  */
  public void setTypeCd(String typeCd){
    this.typeCd = typeCd;
  }

 /**
  * Sets the value of the rootExtensionTxt property.
  * @param rootExtensionTxt the new value of the rootExtensionTxt property
  */
  public void setRootExtensionTxt(String rootExtensionTxt){
    this.rootExtensionTxt = rootExtensionTxt;
  }

 /**
  * Sets the value of the roleCd property.
  * @param roleCd the new value of the roleCd property
  */
  public void setRoleCd(String roleCd){
    this.roleCd = roleCd;
  }

 /**
  * Access method for the cityDescTxt property.
  * @return   the current value of the cityDescTxt property
  */
  public String getCityDescTxt(){
    return cityDescTxt;
  }

 /**
  * Access method for the rootExtensionTxt property.
  * @return   the current value of the rootExtensionTxt property
  */
  public String getRootExtensionTxt() {
    return rootExtensionTxt;
  }

 /**
  * Access method for the zipCd property.
  * @return   the current value of the zipCd property
  */
  public String getZipCd() {
    return zipCd;
  }

 /**
  * Access method for the typeCd property.
  * @return   the current value of the typeCd property
  */
  public String getTypeCd() {
    return typeCd;
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
  * Access method for the orgAccessPriv property.
  * @return   the current value of the orgAccessPriv property
  */
  public String getOrgAccessPriv() {
    return orgAccessPriv;
  }

 /**
  * Sets the value of the orgAccessPriv property.
  * @param orgAccessPriv the new value of the orgAccessPriv property
  */
  public void setOrgAccessPriv(String orgAccessPriv) {
    this.orgAccessPriv = orgAccessPriv;
  }

 /**
  * Sets the value of the superceded and setStatusCodeSuperCeded properties.
  * @param superceded the new value of the superceded property
  */
  public void setSuperceded(boolean superceded)
  {
    this.superceded = superceded;
    if (superceded)
        this.setStatusCodeSuperCeded("S");
  }

 /**
  * Access method for the superceded property.
  * @return   the current value of the superceded property
  */
  public boolean isSuperceded() {
    return superceded;
  }

 /**
  * Sets the value of the localUID property.
  * @param localUID the new value of the localUID property
  */
  public void setLocalUID(Long localUID) {
    this.localUID = localUID;
  }

 /**
  * Access method for the localUID property.
  * @return   the current value of the localUID property
  */
  public Long getLocalUID() {
    return localUID;
  }

 /**
  * Sets the value of the active and setStatusCodeActive properties.
  * @param active the new value of the active property
  */
  public void setActive(boolean active)
  {
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
  * Sets the value of the inActive and setStatusCodeInActive properties.
  * @param inActive the new value of the inActive property
  */
  public void setInActive(boolean inActive)
  {
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
  * Sets the value of the itNew and setStatusCodeNew properties.
  * @param newItNew the new value of the itNew property
  */
  public void setItNew(boolean newItNew)
  {
    this.itNew = newItNew;
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
  * Access method for the nmTxt property.
  * @return   the current value of the nmTxt property
  */
  public String getNmTxt() {
    return nmTxt;
  }

 /**
  * Access method for the nmTxtOperator property.
  * @return   the current value of the nmTxtOperator property
  */
  public String getNmTxtOperator() {
    return nmTxtOperator;
  }

 /**
  * Sets the value of the nmTxtOperator property.
  * @param nmTxtOperator the new value of the nmTxtOperator property
  */
  public void setNmTxtOperator(String nmTxtOperator) {
    this.nmTxtOperator = nmTxtOperator;
  }

 /**
  * Access method for the roleCd property.
  * @return   the current value of the roleCd property
  */
  public String getRoleCd() {
    return roleCd;
  }

 /**
  * Access method for the roleCdOperator property.
  * @return   the current value of the roleCdOperator property
  */
  public String getRoleCdOperator() {
    return roleCdOperator;
  }

 /**
  * Sets the value of the roleCdOperator property.
  * @param roleCdOperator the new value of the roleCdOperator property
  */
  public void setRoleCdOperator(String roleCdOperator) {
    this.roleCdOperator = roleCdOperator;
  }

 /**
  * Access method for the stateCdOperator property.
  * @return   the current value of the stateCdOperator property
  */
  public String getStateCdOperator() {
    return stateCdOperator;
  }

 /**
  * Sets the value of the stateCdOperator property.
  * @param stateCdOperator the new value of the stateCdOperator property
  */
  public void setStateCdOperator(String stateCdOperator) {
    this.stateCdOperator = stateCdOperator;
  }

 /**
  * Access method for the streetAddr1 property.
  * @return   the current value of the streetAddr1 property
  */
  public String getStreetAddr1() {
    return streetAddr1;
  }

 /**
  * Access method for the typeCdOperator property.
  * @return   the current value of the typeCdOperator property
  */
  public String getTypeCdOperator() {
    return typeCdOperator;
  }

 /**
  * Sets the value of the typeCdOperator property.
  * @param typeCdOperator the new value of the typeCdOperator property
  */
  public void setTypeCdOperator(String typeCdOperator) {
    this.typeCdOperator = typeCdOperator;
  }

 /**
  * Sets the value of the streetAddr1Operator property.
  * @param streetAddr1Operator the new value of the streetAddr1Operator property
  */
  public void setStreetAddr1Operator(String streetAddr1Operator) {
    this.streetAddr1Operator = streetAddr1Operator;
  }

 /**
  * Access method for the streetAddr1Operator property.
  * @return   the current value of the streetAddr1Operator property
  */
  public String getStreetAddr1Operator() {
    return streetAddr1Operator;
  }

 /**
  * Sets the value of the progAreaAccessPriv property.
  * @param progAreaAccessPriv the new value of the progAreaAccessPriv property
  */
  public void setProgAreaAccessPriv(String progAreaAccessPriv) {
    this.progAreaAccessPriv = progAreaAccessPriv;
  }

 /**
  * Access method for the progAreaAccessPriv property.
  * @return   the current value of the progAreaAccessPriv property
  */
  public String getProgAreaAccessPriv() {
    return progAreaAccessPriv;
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
  * Access method for the cancelled property.
  * @return   the current value of the cancelled property
  */
    public boolean isCancelled() {
    return cancelled;
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
  * Sets the value of the stateCd property.
  * @param stateCd the new value of the stateCd property
  */
  public void setStateCd(String stateCd) {
    this.stateCd = stateCd;
  }

 /**
  * Access method for the stateCd property.
  * @return   the current value of the stateCd property
  */
  public String getStateCd() {
    return stateCd;
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

 /**
  * Sets the value of the recordStatusCdIn property.
  * @param recordStatusCdIn the new value of the recordStatusCdIn property
  */
  public void setRecordStatusCdIn(String recordStatusCdIn) {
    this.recordStatusCdIn = recordStatusCdIn;
  }

 /**
  * Access method for the recordStatusCdIn property.
  * @return   the current value of the recordStatusCdIn property
  */
  public String getRecordStatusCdIn() {
    return recordStatusCdIn;
  }
  public String getPhoneNbrTxt() {
    return phoneNbrTxt;
  }
  public void setPhoneNbrTxt(String phoneNbrTxt) {
    this.phoneNbrTxt = phoneNbrTxt;
  }
 }