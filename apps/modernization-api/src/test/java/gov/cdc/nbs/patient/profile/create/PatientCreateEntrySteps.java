package gov.cdc.nbs.patient.profile.create;

import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import gov.cdc.nbs.patient.profile.birth.BirthDemographic;
import gov.cdc.nbs.patient.profile.ethnicity.EthnicityDemographic;
import gov.cdc.nbs.patient.profile.gender.GenderDemographic;
import gov.cdc.nbs.patient.profile.general.GeneralInformationDemographic;
import gov.cdc.nbs.patient.profile.mortality.MortalityDemographic;
import gov.cdc.nbs.patient.profile.names.NameDemographic;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientCreateEntrySteps {

  private final Active<Administrative> activeAdministrative;
  private final Active<BirthDemographic> activeBirthDemographic;
  private final Active<GenderDemographic> activeGenderDemographic;
  private final Active<NameDemographic> activeName;
  private final Active<EthnicityDemographic> activeEthnicity;
  private final Active<MortalityDemographic> activeMortalityDemographic;
  private final Active<GeneralInformationDemographic> activeGeneralInformation;
  private final Active<PhoneDemographic> activePhoneDemographic;
  private final Active<NewPatient> input;

  public PatientCreateEntrySteps(
      final Active<Administrative> activeAdministrative,
      final Active<BirthDemographic> activeBirthDemographic,
      final Active<GenderDemographic> activeGenderDemographic,
      final Active<NameDemographic> activeName,
      final Active<EthnicityDemographic> activeEthnicity,
      final Active<MortalityDemographic> activeMortalityDemographic,
      final Active<GeneralInformationDemographic> activeGeneralInformation,
      final Active<PhoneDemographic> activePhoneDemographic,
      final Active<NewPatient> input
  ) {
    this.activeAdministrative = activeAdministrative;
    this.activeBirthDemographic = activeBirthDemographic;
    this.activeGenderDemographic = activeGenderDemographic;
    this.activeMortalityDemographic = activeMortalityDemographic;
    this.activeName = activeName;
    this.activeEthnicity = activeEthnicity;
    this.activeGeneralInformation = activeGeneralInformation;
    this.activePhoneDemographic = activePhoneDemographic;
    this.input = input;
  }

  @Given("the ethnicity is included in the extended patient data")
  public void the_ethnicity_is_included_in_the_extended_patient_data() {
    this.activeEthnicity.maybeActive()
        .ifPresent(ethnicity -> this.input.active(current -> current.withEthnicity(ethnicity)));
  }

  @Given("the administrative is included in the extended patient data")
  public void the_administrative_is_included_in_the_extended_patient_data() {
    this.activeAdministrative.maybeActive()
        .ifPresent(administrative -> this.input.active(current -> current.withAdministrative(administrative)));
  }

  @Given("the name is included with the extended patient data")
  public void i_add_the_current_name() {
    this.activeName.maybeActive().ifPresent(
        name -> this.input.active(current -> current.withName(name)));
  }

  @Given("the phone is included with the extended patient data")
  public void includePhone() {
    this.activePhoneDemographic.maybeActive().ifPresent(
        demographic -> this.input.active(current -> current.withPhoneEmail(demographic)));
  }

  @Given("the birth demographics are included in the extended patient data")
  public void the_birth_demographics_are_included_in_the_extended_patient_data() {
    this.activeBirthDemographic.maybeActive()
        .ifPresent(demographic -> this.input.active(current -> current.withBirth(demographic)));
  }

  @Given("the gender demographics are included in the extended patient data")
  public void the_gender_demographics_are_included_in_the_extended_patient_data() {
    this.activeGenderDemographic.maybeActive()
        .ifPresent(demographic -> this.input.active(current -> current.withGender(demographic)));
  }

  @Given("the mortality demographics are included in the extended patient data")
  public void the_mortality_demographics_are_included_in_the_extended_patient_data() {
    this.activeMortalityDemographic.maybeActive()
        .ifPresent(demographic -> this.input.active(current -> current.withMortality(demographic)));
  }

  @Given("the general information demographics are included in the extended patient data")
  public void the_general_information_is_included_in_the_extended_patient_data() {
    this.activeGeneralInformation.maybeActive()
        .ifPresent(demographic -> this.input.active(current -> current.withGeneralInformation(demographic)));
  }
}
