package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

public class PatientHeaderSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientHeaderRequester requester;
  private final Active<ResultActions> response;
  private final SoundexResolver soundexResolver;
  private final EntityManager entityManager;
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
    this.entityManager = entityManager;
    this.soundexResolver = soundexResolver;
  }

  @Transactional
  @Given("the patient has a legal name")
  public void patient_has_a_legal_name() {
    Person patient = this.entityManager.find(Person.class, activePatient.active().id());
    patient.add(soundexResolver, new PatientCommand.AddName(
        activePatient.active().id(),
        LocalDate.parse("2023-01-17"),
        "first",
        null,
        "last",
        null,
        "L",
        131L,
        LocalDateTime.parse("2020-03-03T10:15:30")));
    this.entityManager.persist(patient);
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
