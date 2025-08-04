package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class GeneralInformationDemographicEntrySteps {

  private final Clock clock;
  private final Active<GeneralInformationDemographic> input;

  GeneralInformationDemographicEntrySteps(final Clock clock, final Active<GeneralInformationDemographic> input) {
    this.clock = clock;
    this.input = input;
  }

  private Supplier<GeneralInformationDemographic> initial() {
    return () -> new GeneralInformationDemographic(LocalDate.now(clock));
  }

  @Given("I enter the general information as of date {localDate}")
  public void i_enter_the_general_as_of(final LocalDate asOf) {
    this.input.active(current -> current.withAsOf(asOf), initial());
  }

  @Given("I enter the general information marital status as {maritalStatus}")
  public void i_enter_the_general_marital_status(final String value) {
    this.input.active(current -> current.withMaritalStatus(value), initial());
  }

  @Given("I enter the general information maternal maiden name as {string}")
  public void i_enter_the_general_maternal_maiden_name(final String value) {
    this.input.active(current -> current.withMaternalMaidenName(value), initial());
  }

  @Given("I enter the general information adults in residence as {int}")
  public void i_enter_the_general_adults_in_residence(final int value) {
    this.input.active(current -> current.withAdultsInResidence(value), initial());
  }

  @Given("I enter the general information children in residence as {int}")
  public void i_enter_the_general_children_in_residence(final int value) {
    this.input.active(current -> current.withChildrenInResidence(value), initial());
  }

  @Given("I enter the general information primary occupation of {occupation}")
  public void i_enter_the_general_primary_occupation(final String value) {
    this.input.active(current -> current.withPrimaryOccupation(value), initial());
  }

  @Given("I enter the general information education level of {educationLevel}")
  public void i_enter_the_general_education_level(final String value) {
    this.input.active(current -> current.withEducationLevel(value), initial());
  }

  @Given("I enter the general information primary language of {language}")
  public void i_enter_the_general_primary_language(final String value) {
    this.input.active(current -> current.withPrimaryLanguage(value), initial());
  }

  @Given("I enter the general information that the patient {indicator} speak english")
  public void i_enter_the_general_speaks_english(final String value) {
    this.input.active(current -> current.withSpeaksEnglish(value), initial());
  }

  @Given("I enter the general information state HIV case of {string}")
  public void i_enter_the_general_state_HIV_case(final String value) {
    this.input.active(current -> current.withStateHIVCase(value), initial());
  }

}
