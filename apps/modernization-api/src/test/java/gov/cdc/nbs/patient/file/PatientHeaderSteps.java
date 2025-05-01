package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import jakarta.persistence.EntityManager;
import org.springframework.test.web.servlet.ResultActions;

public class PatientHeaderSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientHeaderRequester requester;
  private final Active<ResultActions> response;
  Exception exception;

  PatientHeaderSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientHeaderRequester requester,
      final SoundexResolver soundexResolver,
      final EntityManager entityManager,
      final Active<ResultActions> response) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
  }

  @Then("I view the Patient File Header")
  public void i_view_the_patient_file_header() {
    try {
      this.response.active(
          this.requester.request(activePatient.active().shortId()));
    } catch (Exception thrown) {
      this.exception = thrown;
    }
  }
}
