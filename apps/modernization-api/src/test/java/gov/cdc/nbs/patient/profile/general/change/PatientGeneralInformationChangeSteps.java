package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional
public class PatientGeneralInformationChangeSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<GeneralInformationPendingChanges> activeChanges;
  private final PatientGeneralChangeRequester requester;
  private final Active<ResultActions> response;

  PatientGeneralInformationChangeSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<GeneralInformationPendingChanges> activeChanges,
      final PatientGeneralChangeRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.activeChanges = activeChanges;
    this.requester = requester;
    this.response = response;
  }

  @Given("I want to change the patient's general information to include the marital status of {maritalStatus}")
  public void i_want_to_change_the_marital_status(final String value) {
    this.activeChanges.active(current -> current.maritalStatus(value));
  }

  @Given("I want to change the patient's general information to include the mother's maiden name of {string}")
  public void i_want_to_change_the_mothers_maiden_name(final String value) {
    this.activeChanges.active(current -> current.maternalMaidenName(value));
  }

  @Given("I want to change the patient's general information to include {int} adults in the house")
  public void i_want_to_change_the_adults_in_house(final int value) {
    this.activeChanges.active(current -> current.adultsInHouse(value));
  }

  @Given("I want to change the patient's general information to include {int} children in the house")
  public void i_want_to_change_the_children_in_house(final int value) {
    this.activeChanges.active(current -> current.childrenInHouse(value));
  }

  @Given("I want to change the patient's general information to include the occupation of {occupation}")
  public void i_want_to_change_the_occupation(final String value) {
    this.activeChanges.active(current -> current.occupation(value));
  }

  @Given("I want to change the patient's general information to include an education level of {educationLevel}")
  public void i_want_to_change_the_education_level(final String value) {
    this.activeChanges.active(current -> current.educationLevel(value));
  }

  @Given("I want to change the patient's general information to include a primary language of {language}")
  public void i_want_to_change_the_primary_language(final String value) {
    this.activeChanges.active(current -> current.primaryLanguage(value));
  }

  @Given("I want to change the patient's general information to include that the patient {indicator} speak English")
  public void i_want_to_change_the_speaks_english(final String value) {
    this.activeChanges.active(current -> current.speaksEnglish(value));
  }

  @Given("I want to change the patient's general information to include that the patient is associated with state HIV case {string}")
  public void i_want_to_change_the_state_HIV_case(final String value) {
    this.activeChanges.active(current -> current.stateHIVCase(value));
  }

  @When("the patient profile general information changes are submitted as of {date}")
  public void the_patient_profile_general_information_changes_are_submitted(final Instant asOf) {
    this.activePatient.maybeActive().flatMap(
            patient -> this.activeChanges.maybeActive().map(pending -> pending.applied(patient.id(), asOf))
        ).map(this.requester::change)
        .ifPresent(this.response::active);

  }
}
