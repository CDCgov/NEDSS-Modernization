package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.profile.general.GeneralInformationDemographic;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class GeneralInformationDemographicEntrySteps {

  private final Active<GeneralInformationDemographic> input;

  GeneralInformationDemographicEntrySteps(final Active<GeneralInformationDemographic> input) {
    this.input = input;
  }

  @Given("I enter the general information as of date {localDate}")
  public void i_enter_the_general_as_of(final LocalDate asOf) {
    this.input.active(current -> current.withAsOf(asOf));
  }

  @Given("I enter the general information marital status as {maritalStatus}")
  public void i_enter_the_general_marital_status(final String value) {
    this.input.active(current -> current.withMaritalStatus(value));
  }

  @Given("I enter the general information maternal maiden name as {string}")
  public void i_enter_the_general_maternal_maiden_name(final String value) {
    this.input.active(current -> current.withMaternalMaidenName(value));
  }

  @Given("I enter the general information adults in residence as {int}")
  public void i_enter_the_general_adults_in_residence(final int value) {
    this.input.active(current -> current.withAdultsInResidence(value));
  }

  @Given("I enter the general information children in residence as {int}")
  public void i_enter_the_general_children_in_residence(final int value) {
    this.input.active(current -> current.withChildrenInResidence(value));
  }

  @Given("I enter the general information primary occupation of {occupation}")
  public void i_enter_the_general_primary_occupation(final String value) {
    this.input.active(current -> current.withPrimaryOccupation(value));
  }

  @Given("I enter the general information education level of {educationLevel}")
  public void i_enter_the_general_education_level(final String value) {
    this.input.active(current -> current.withEducationLevel(value));
  }

  @Given("I enter the general information primary language of {language}")
  public void i_enter_the_general_primary_language(final String value) {
    this.input.active(current -> current.withPrimaryLanguage(value));
  }

  @Given("I enter the general information that the patient {indicator} speak english")
  public void i_enter_the_general_speaks_english(final String value) {
    this.input.active(current -> current.withSpeaksEnglish(value));
  }

  @Given("I enter the general information state HIV case of {string}")
  public void i_enter_the_general_state_HIV_case(final String value) {
    this.input.active(current -> current.withStateHIVCase(value));
  }

}
