package gov.cdc.nbs.patient.profile.race.change;

import gov.cdc.nbs.message.patient.input.RaceInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Optional;

public class PatientProfileUpdateRaceSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<RaceInput> input;
  private final Active<ResultActions> response;
  private final PatientProfileUpdateRaceRequester requester;

  PatientProfileUpdateRaceSteps(
      final Active<PatientIdentifier> patient,
      final Active<RaceInput> input,
      final Active<ResultActions> response,
      final PatientProfileUpdateRaceRequester requester
  ) {
    this.patient = patient;
    this.input = input;
    this.response = response;
    this.requester = requester;
  }

  @When("I update a patient's race category of {raceCategory} as of {localDate}")
  public void i_update_the_patient_race(final String category, final LocalDate asOf) {
    this.patient.maybeActive().flatMap(current -> withInput(current, category, asOf))
        .map(this.requester::update)
        .ifPresent(this.response::active);
  }

  private Optional<RaceInput> withInput(
      final PatientIdentifier identifier,
      final String category,
      final LocalDate asOf
  ) {
    return this.input.maybeActive()
        .map(
            current -> current.setPatient(identifier.id())
                .setCategory(category)
                .setAsOf(asOf)
        );
  }
}
