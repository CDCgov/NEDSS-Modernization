package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

public class PatientProfileDeleteRaceSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<DeletePatientRace> input;
  private final Active<ResultActions> response;
  private final PatientProfileDeleteRaceRequester requester;

  PatientProfileDeleteRaceSteps(
      final Active<PatientIdentifier> patient,
      final Active<DeletePatientRace> input,
      final Active<ResultActions> response,
      final PatientProfileDeleteRaceRequester requester
  ) {
    this.patient = patient;
    this.input = input;
    this.response = response;
    this.requester = requester;
  }

  @Before("@patient-profile-race-change")
  public void reset() {
    this.input.active(new DeletePatientRace(Long.MIN_VALUE, null));
  }

  @Given("I want to remove the patient's {raceCategory} race entry")
  public void i_want_to_remove_the_patient_race_entry_of(final String category) {
    this.input.active(current -> current.withCategory(category));
  }

  @When("I remove a patient's race")
  public void i_remove_the_patient_race() {
    this.patient.maybeActive().flatMap(
            current -> this.input.maybeActive()
                .map(in -> in.withPatient(current.id()))
        )
        .map(this.requester::delete)
        .ifPresent(this.response::active);
  }

}
