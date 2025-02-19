package gov.cdc.nbs.patient.profile.identification;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.datafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PatientProfileIdentificationSteps {

  private final Active<PatientIdentifier> activePatient;
  private final Active<PatientInput> input;
  private final TestPatient patient;
  private final Active<Pageable> activePageable;
  private final PatientProfileIdentificationRequester requester;
  private final Active<ResultActions> response;

  private final Faker faker = new Faker();
  private final Active<Page<PatientIdentification>> results = new Active<>();

  PatientProfileIdentificationSteps(
      final Active<PatientIdentifier> activePatient,
      final Active<PatientInput> input,
      final TestPatient patient,
      final Active<Pageable> activePageable,
      final PatientProfileIdentificationRequester requester,
      final Active<ResultActions> response
  ) {
    this.activePatient = activePatient;
    this.input = input;
    this.patient = patient;
    this.activePageable = activePageable;
    this.requester = requester;
    this.response = response;
  }

  @Given("the new patient's Social Security Number is entered")
  public void the_new_patient_ssn_is_entered() {
    PatientInput.Identification identification = new PatientInput.Identification(
        faker.idNumber().ssnValid(),
        "SS",
        "ANY");

    this.input.active().getIdentifications().add(identification);
  }

  @Given("the new patient's identification is entered")
  public void the_new_patient_identification_is_entered() {
    PatientInput.Identification identification = new PatientInput.Identification(
        RandomUtil.getRandomString(),
        RandomUtil.getRandomString(),
        RandomUtil.getRandomString());

    this.input.active().getIdentifications().add(identification);
  }

  @Then("the new patient has the entered identification")
  @Transactional
  public void the_new_patient_has_the_entered_identification() {
    Person actual = patient.managed();

    Collection<EntityId> identifications = actual.identifications();

    if (!identifications.isEmpty()) {

      assertThat(identifications)
          .satisfiesExactlyInAnyOrder(
              PatientCreateAssertions.containsIdentifications(input.active().getIdentifications()));
    }

  }

  @Given("I view the Patient Profile Identification")
  public void i_view_the_patient_profile_identifications() {
    this.activePatient.maybeActive()
        .map(this::viewInvestigations)
        .ifPresent(this.response::active);
  }

  private ResultActions viewInvestigations(final PatientIdentifier patient) {
    return
        this.activePageable.maybeActive().isPresent()
            ? this.requester.identifications(patient, this.activePageable.active())
            : this.requester.identifications(patient);
  }

  @Then("the profile has no associated identifications")
  public void the_profile_has_no_associated_identifications() throws Exception {
    checkIdentificationTotal(0);
  }

  private void checkIdentificationTotal(int total) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.identification.total")
            .value(equalTo(total)));
  }

  @Then("the patient has {int} identification entries")
  public void the_patient_has_x_identification_entries(int x) throws Exception {
    checkIdentificationTotal(x);
  }

  @Then("the patient's identification includes {int} entries")
  public void the_patients_identification_includes_x_entries(int x) throws Exception {
    this.response.active()
        .andExpect(jsonPath("$.data.findPatientProfile.identification.content[*]")
            .value(hasSize(x)));
  }

  @Then("the patient's identifications include a(n) {identificationType} of {string}")
  public void the_patients_identification_includes_the_type(final String type, final String value) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')].value",
                type
            ).value(hasItem(value))
        );
  }

  @Then("the patient's identifications includes a(n) {identificationType}")
  public void the_patients_identification_includes_the_type(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')]",
                type
            ).exists()
        );
  }

  @Then("the patient's identifications does not include a(n) {identificationType}")
  public void the_patients_identification_does_not_include_the_type(final String type) throws Exception {
    this.response.active()
        .andExpect(
            jsonPath(
                "$.data.findPatientProfile.identification.content[?(@.type.id=='%s')]",
                type
            ).doesNotExist()
        );
  }
}
