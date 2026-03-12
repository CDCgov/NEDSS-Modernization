package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class GeneralInformation {

  private static final Permission HIV_PERMISSION = new Permission("HIVQuestions", "Global");

  @Column(name = "as_of_date_general")
  private LocalDate asOf;

  @Column(name = "marital_status_cd", length = 20)
  private String maritalStatus;

  @Column(name = "mothers_maiden_nm", length = 50)
  private String mothersMaidenName;

  @Column(name = "adults_in_house_nbr")
  private Integer adultsInHouse;

  @Column(name = "children_in_house_nbr")
  private Integer childrenInHouse;

  @Column(name = "occupation_cd", length = 20)
  private String occupation;

  @Column(name = "education_level_cd", length = 20)
  private String educationLevel;

  @Column(name = "prim_lang_cd", length = 20)
  private String primaryLanguage;

  @Column(name = "speaks_english_cd", length = 20)
  private String speaksEnglish;

  @Column(name = "ehars_id", length = 20)
  private String stateHIVCase;

  public void update(final PatientCommand.UpdateGeneralInfo info) {
    this.asOf = info.asOf();
    this.maritalStatus = info.maritalStatus();
    this.mothersMaidenName = info.mothersMaidenName();
    this.adultsInHouse = info.adultsInHouseNumber();
    this.childrenInHouse = info.childrenInHouseNumber();
    this.occupation = info.occupationCode();
    this.educationLevel = info.educationLevelCode();
    this.primaryLanguage = info.primaryLanguageCode();
    this.speaksEnglish = info.speaksEnglishCode();
  }

  public void clear() {
    this.maritalStatus = null;
    this.mothersMaidenName = null;
    this.adultsInHouse = null;
    this.childrenInHouse = null;
    this.occupation = null;
    this.educationLevel = null;
    this.primaryLanguage = null;
    this.speaksEnglish = null;

    if (stateHIVCase == null) {
      this.asOf = null;
    }
  }

  public void associate(
      final PermissionScopeResolver resolver,
      final PatientCommand.AssociateStateHIVCase associate) {
    PermissionScope scope = resolver.resolve(HIV_PERMISSION);
    if (scope.allowed()) {
      this.stateHIVCase = associate.stateHIVCase();
    }
  }

  public void disassociate(final PermissionScopeResolver resolver) {
    PermissionScope scope = resolver.resolve(HIV_PERMISSION);
    if (scope.allowed()) {
      this.stateHIVCase = null;

      if (this.maritalStatus == null
          && this.mothersMaidenName == null
          && this.adultsInHouse == null
          && this.childrenInHouse == null
          && this.occupation == null
          && this.educationLevel == null
          && this.primaryLanguage == null
          && this.speaksEnglish == null) {
        this.asOf = null;
      }
    }
  }

  public LocalDate asOf() {
    return asOf;
  }

  public String maritalStatus() {
    return maritalStatus;
  }

  public String mothersMaidenName() {
    return mothersMaidenName;
  }

  public Integer adultsInHouse() {
    return adultsInHouse;
  }

  public Integer childrenInHouse() {
    return childrenInHouse;
  }

  public String occupation() {
    return occupation;
  }

  public String educationLevel() {
    return educationLevel;
  }

  public String primaryLanguage() {
    return primaryLanguage;
  }

  public String speaksEnglish() {
    return speaksEnglish;
  }

  public String stateHIVCase() {
    return stateHIVCase;
  }

  public long signature() {
    return Objects.hash(
        asOf,
        maritalStatus,
        mothersMaidenName,
        adultsInHouse,
        childrenInHouse,
        occupation,
        educationLevel,
        primaryLanguage,
        speaksEnglish,
        stateHIVCase);
  }
}
