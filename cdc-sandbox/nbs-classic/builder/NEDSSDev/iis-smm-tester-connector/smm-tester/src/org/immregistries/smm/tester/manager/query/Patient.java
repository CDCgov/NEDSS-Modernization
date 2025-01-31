package org.immregistries.smm.tester.manager.query;

import java.util.Date;

public class Patient {
  private String idNumber = "";
  private String idAuthority = "";
  private PatientIdType idType = PatientIdType.MR;
  private String nameLast = "";
  private String nameFirst = "";
  private String nameMiddle = "";
  private String motherNameMaiden = "";
  private String motherNameLast = "";
  private String motherNameFirst = "";
  private String motherNameMiddle = "";
  private Date birthDate = null;
  private String sex = "";
  private String addressStreet1 = "";
  private String addressStreet2 = "";
  private String addressCity = "";
  private String addressState = "";
  private String addressZip = "";
  private String addressCountry = "USA";
  private String phoneArea = "";
  private String phoneLocal = "";
  private boolean multipleBirthIndicator = false;
  private int multipleBirthOrder = 0;
  
  public String getIdNumber() {
    return idNumber;
  }
  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }
  public String getIdAuthority() {
    return idAuthority;
  }
  public void setIdAuthority(String idAuthority) {
    this.idAuthority = idAuthority;
  }
  public PatientIdType getIdType() {
    return idType;
  }
  public void setIdType(PatientIdType idType) {
    this.idType = idType;
  }
  public String getNameLast() {
    return nameLast;
  }
  public void setNameLast(String nameLast) {
    this.nameLast = nameLast;
  }
  public String getNameFirst() {
    return nameFirst;
  }
  public void setNameFirst(String nameFirst) {
    this.nameFirst = nameFirst;
  }
  public String getNameMiddle() {
    return nameMiddle;
  }
  public void setNameMiddle(String nameMiddle) {
    this.nameMiddle = nameMiddle;
  }
  public String getMotherNameMaiden() {
    return motherNameMaiden;
  }
  public void setMotherNameMaiden(String motherNameMaiden) {
    this.motherNameMaiden = motherNameMaiden;
  }
  public String getMotherNameLast() {
    return motherNameLast;
  }
  public void setMotherNameLast(String motherNameLast) {
    this.motherNameLast = motherNameLast;
  }
  public String getMotherNameFirst() {
    return motherNameFirst;
  }
  public void setMotherNameFirst(String motherNameFirst) {
    this.motherNameFirst = motherNameFirst;
  }
  public String getMotherNameMiddle() {
    return motherNameMiddle;
  }
  public void setMotherNameMiddle(String motherNameMiddle) {
    this.motherNameMiddle = motherNameMiddle;
  }
  public Date getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }
  public String getSex() {
    return sex;
  }
  public void setSex(String sex) {
    this.sex = sex;
  }
  public String getAddressStreet1() {
    return addressStreet1;
  }
  public void setAddressStreet1(String addressStreet1) {
    this.addressStreet1 = addressStreet1;
  }
  public String getAddressStreet2() {
    return addressStreet2;
  }
  public void setAddressStreet2(String addressStreet2) {
    this.addressStreet2 = addressStreet2;
  }
  public String getAddressCity() {
    return addressCity;
  }
  public void setAddressCity(String addressCity) {
    this.addressCity = addressCity;
  }
  public String getAddressState() {
    return addressState;
  }
  public void setAddressState(String addressState) {
    this.addressState = addressState;
  }
  public String getAddressZip() {
    return addressZip;
  }
  public void setAddressZip(String addressZip) {
    this.addressZip = addressZip;
  }
  public String getAddressCountry() {
    return addressCountry;
  }
  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }
  public String getPhoneArea() {
    return phoneArea;
  }
  public void setPhoneArea(String phoneArea) {
    this.phoneArea = phoneArea;
  }
  public String getPhoneLocal() {
    return phoneLocal;
  }
  public void setPhoneLocal(String phoneLocal) {
    this.phoneLocal = phoneLocal;
  }
  public boolean getMultipleBirthIndicator() {
    return multipleBirthIndicator;
  }
  public void setMultipleBirthIndicator(boolean multipleBirthIndicator) {
    this.multipleBirthIndicator = multipleBirthIndicator;
  }
  public int getMultipleBirthOrder() {
    return multipleBirthOrder;
  }
  public void setMultipleBirthOrder(int multipleBirthOrder) {
    this.multipleBirthOrder = multipleBirthOrder;
  }

}
