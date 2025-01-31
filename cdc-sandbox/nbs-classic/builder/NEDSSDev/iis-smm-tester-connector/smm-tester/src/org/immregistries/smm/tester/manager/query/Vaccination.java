package org.immregistries.smm.tester.manager.query;

import java.util.Date;

public class Vaccination {
  
  public static final String ACTION_CODE_ADD = "A";
  public static final String ACTION_CODE_UPDATE = "U";
  public static final String ACTION_CODE_DELETE = "D";
  
  public static final String INFORMATION_SOURCE_ADMINISTERED = "00";
  public static final String INFORMATION_SOURCE_HISTORICAL = "01";
  
  public static final String COMPLETION_STATUS_COMPLETE = "CP";
  public static final String COMPLETION_STATUS_REFUSED = "RE";
  public static final String COMPLETION_STATUS_NOT_ADMINISTERED = "NA";
  public static final String COMPLETION_STATUS_PARTIALLY_ADMINISTERED = "PA";
  
  private Date administrationDate = null;
  private String vaccineCvx = "";
  private String vaccineLabel = "";
  private String actionCode = "";
  private String administeredAmount = "";
  private String administeredAmountUnit = "";
  private String informationSource = "";
  private String informationSourceLabel = "";
  private String lotNumber = "";
  private String manufacturerMvx = "";
  private String manufacturerLabel = "";
  private String refusalReason = "";
  private String completionStatus = "";
  
  public String getInformationSourceLabel() {
    return informationSourceLabel;
  }
  public void setInformationSourceLabel(String informationSourceLabel) {
    this.informationSourceLabel = informationSourceLabel;
  }
  public String getManufacturerLabel() {
    return manufacturerLabel;
  }
  public void setManufacturerLabel(String manufacturerLabel) {
    this.manufacturerLabel = manufacturerLabel;
  }
  public String getAdministeredAmount() {
    return administeredAmount;
  }
  public void setAdministeredAmount(String administeredAmount) {
    this.administeredAmount = administeredAmount;
  }
  public String getAdministeredAmountUnit() {
    return administeredAmountUnit;
  }
  public void setAdministeredAmountUnit(String administeredAmountUnit) {
    this.administeredAmountUnit = administeredAmountUnit;
  }
  public String getInformationSource() {
    return informationSource;
  }
  public void setInformationSource(String informationSource) {
    this.informationSource = informationSource;
  }
  public String getLotNumber() {
    return lotNumber;
  }
  public void setLotNumber(String lotNumber) {
    this.lotNumber = lotNumber;
  }
  public String getManufacturerMvx() {
    return manufacturerMvx;
  }
  public void setManufacturerMvx(String manufacturerMvx) {
    this.manufacturerMvx = manufacturerMvx;
  }
  public String getRefusalReason() {
    return refusalReason;
  }
  public void setRefusalReason(String refusalReason) {
    this.refusalReason = refusalReason;
  }
  public String getCompletionStatus() {
    return completionStatus;
  }
  public void setCompletionStatus(String completionStatus) {
    this.completionStatus = completionStatus;
  }
  public String getActionCode() {
    return actionCode;
  }
  public void setActionCode(String actionCode) {
    this.actionCode = actionCode;
  }
  public Date getAdministrationDate() {
    return administrationDate;
  }
  public void setAdministrationDate(Date administrationDate) {
    this.administrationDate = administrationDate;
  }
  public String getVaccineCvx() {
    return vaccineCvx;
  }
  public void setVaccineCvx(String vaccineCvx) {
    this.vaccineCvx = vaccineCvx;
  }
  public String getVaccineLabel() {
    return vaccineLabel;
  }
  public void setVaccineLabel(String vaccineLabel) {
    this.vaccineLabel = vaccineLabel;
  }

}
