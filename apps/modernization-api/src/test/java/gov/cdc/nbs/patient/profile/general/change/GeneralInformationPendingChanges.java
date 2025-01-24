package gov.cdc.nbs.patient.profile.general.change;

import java.time.LocalDate;

public class GeneralInformationPendingChanges {

  private String maritalStatus;
  private String maternalMaidenName;
  private Integer adultsInHouse;
  private Integer childrenInHouse;
  private String occupation;
  private String educationLevel;
  private String primaryLanguage;
  private String speaksEnglish;
  private String stateHIVCase;

  public String maritalStatus() {
    return maritalStatus;
  }

  public GeneralInformationPendingChanges maritalStatus(final String maritalStatus) {
    this.maritalStatus = maritalStatus;
    return this;
  }

  public String maternalMaidenName() {
    return maternalMaidenName;
  }

  public GeneralInformationPendingChanges maternalMaidenName(String maternalMaidenName) {
    this.maternalMaidenName = maternalMaidenName;
    return this;
  }

  public Integer adultsInHouse() {
    return adultsInHouse;
  }

  public GeneralInformationPendingChanges adultsInHouse(Integer adultsInHouse) {
    this.adultsInHouse = adultsInHouse;
    return this;
  }

  public Integer childrenInHouse() {
    return childrenInHouse;
  }

  public GeneralInformationPendingChanges childrenInHouse(Integer childrenInHouse) {
    this.childrenInHouse = childrenInHouse;
    return this;
  }

  public String occupation() {
    return occupation;
  }

  public GeneralInformationPendingChanges occupation(String occupation) {
    this.occupation = occupation;
    return this;
  }

  public String educationLevel() {
    return educationLevel;
  }

  public GeneralInformationPendingChanges educationLevel(String educationLevel) {
    this.educationLevel = educationLevel;
    return this;
  }

  public String primaryLanguage() {
    return primaryLanguage;
  }

  public GeneralInformationPendingChanges primaryLanguage(String primaryLanguage) {
    this.primaryLanguage = primaryLanguage;
    return this;
  }

  public String speaksEnglish() {
    return speaksEnglish;
  }

  public GeneralInformationPendingChanges speaksEnglish(String speaksEnglish) {
    this.speaksEnglish = speaksEnglish;
    return this;
  }

  public String stateHIVCase() {
    return stateHIVCase;
  }

  public GeneralInformationPendingChanges stateHIVCase(String stateHIVCase) {
    this.stateHIVCase = stateHIVCase;
    return this;
  }

  UpdateGeneralInformation applied(final long patient, final LocalDate asOf) {
    return new UpdateGeneralInformation(
        patient,
        asOf,
        maritalStatus(),
        maternalMaidenName(),
        adultsInHouse(),
        childrenInHouse(),
        occupation(),
        educationLevel(),
        primaryLanguage(),
        speaksEnglish(),
        stateHIVCase()
    );
  }
}
