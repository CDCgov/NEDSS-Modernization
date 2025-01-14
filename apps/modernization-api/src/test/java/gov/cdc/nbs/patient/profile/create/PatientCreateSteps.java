package gov.cdc.nbs.patient.profile.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.Optional;

public class PatientCreateSteps {

  private final Active<NewPatient> input;
  private final Active<ResultActions> response;
  private final ObjectMapper mapper;
  private final Active<PatientIdentifier> activePatient;
  private final Available<PatientIdentifier> availablePatients;

  private final PatientCreateRequester requester;

  PatientCreateSteps(
      final Active<NewPatient> input,
      final Active<ResultActions> response,
      final Active<PatientIdentifier> activePatient,
      final Available<PatientIdentifier> availablePatients,
      final PatientCreateRequester requester,
      final ObjectMapper mapper
  ) {
    this.input = input;
    this.response = response;
    this.activePatient = activePatient;
    this.availablePatients = availablePatients;
    this.requester = requester;
    this.mapper = mapper;
  }

  @When("I create a patient with extended data")
  public void i_create_a_patient_with_extended_data() {
    this.input.maybeActive()
        .map(this.requester::create)
        .ifPresent(this::created);
  }


  private void created(final ResultActions result) {
    this.response.active(result);

    maybeCreatedPatient(result).ifPresent(this::include);
  }

  private Optional<PatientIdentifier> maybeCreatedPatient(final ResultActions result) {
    try {


      CreatedPatient created = mapper.readValue(
          result.andReturn().getResponse().getContentAsByteArray(),
          CreatedPatient.class
      );

      return Optional.of(new PatientIdentifier(created.id(), created.shortId(), created.local()));
    } catch (IOException exception) {
      //  The response did not contain a created patient
      return Optional.empty();
    }
  }

  private void include(final PatientIdentifier created) {
    this.activePatient.active(created);
    this.availablePatients.available(created);
  }
}
