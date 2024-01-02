package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Optional;

public class PatientProfileAddRaceSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<RaceInput> input;
  private final PatientProfileAddRaceRequester requester;
  private final Active<ResultActions> response;

  PatientProfileAddRaceSteps(
      final Active<PatientIdentifier> patient,
      final Active<RaceInput> input,
      final PatientProfileAddRaceRequester requester,
      final Active<ResultActions> response
  ) {
    this.patient = patient;
    this.input = input;
    this.requester = requester;
    this.response = response;
  }

  @When("I add a patient's race as of {date}")
  public void i_add_the_patient_race_as_of(final Instant asOf) {
    this.patient.maybeActive().flatMap(current -> withInput(current, asOf))
        .map(this.requester::add)
        .ifPresent(this.response::active);
  }

  private Optional<RaceInput> withInput(
      final PatientIdentifier identifier,
      final Instant asOf
  ) {
    return this.input.maybeActive()
        .map(current -> current.patient(identifier.id()).asOf(asOf));
  }
}
