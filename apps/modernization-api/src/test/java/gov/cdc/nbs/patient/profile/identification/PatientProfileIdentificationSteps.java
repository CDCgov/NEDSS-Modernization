package gov.cdc.nbs.patient.profile.identification;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.support.Active;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileIdentificationSteps {

  private final Faker faker = new Faker();

  @Autowired
  PatientMother mother;

  @Autowired
  Active<PatientIdentifier> activePatient;

  @Autowired
  PatientIdentificationResolver resolver;

  @Autowired
  Active<PatientInput> input;

  @Autowired
  TestPatient patient;

  private final Active<Page<PatientIdentification>> results = new Active<>();

  @Given("the new patient's Social Security Number is entered")
  public void the_new_patient_ssn_is_entered() {
    PatientInput.Identification identification = new PatientInput.Identification(
        faker.idNumber().ssnValid(),
        "SS",
        "ANY"
    );


    this.input.active().getIdentifications().add(identification);
  }

  @Given("the new patient's identification is entered")
  public void the_new_patient_identification_is_entered() {
    PatientInput.Identification identification = new PatientInput.Identification(
        RandomUtil.getRandomString(),
        RandomUtil.getRandomString(),
        RandomUtil.getRandomString()
    );


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

  @Then("the profile has no associated identifications")
  public void the_profile_has_no_associated_identifications() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(1);

    Page<PatientIdentification> actual = this.resolver.resolve(profile, page);
    assertThat(actual).isEmpty();
  }

  @Then("the profile identifications are not accessible")
  public void the_profile_identification_is_not_accessible() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(1);

    assertThatThrownBy(
        () -> this.resolver.resolve(profile, page)
    )
        .isInstanceOf(AccessDeniedException.class);
  }

  @Before("@patient-profile-identifications")
  public void reset() {
    this.results.reset();
  }

  @Given("the patient has identification")
  @Transactional
  public void the_patient_has_identification() {
    mother.withIdentification(this.activePatient.active());
  }

  @Given("the patient has {int} identifications")
  @Transactional
  public void the_patient_has_x_identifications(int total) {
    for (int i = 0; i < total; i++) {
      mother.withIdentification(this.activePatient.active());
    }
  }

  @Given("the patient can be identified with a {string} of {string}")
  @Given("the patient can be identified with an {string} of {string}")
  @Transactional
  public void the_patient_has_identification(
      final String type,
      final String value
  ) {
    mother.withIdentification(
        this.activePatient.active(),
        resolveType(type),
        value
    );
  }

  private String resolveType(final String type) {
    return switch (type.toLowerCase()) {
      case "account number" -> "AN";
      case "alternate person number" -> "APT";
      case "chip identification number" -> "CI";
      case "driver's license number" -> "DL";
      case "immunization registry id" -> "IIS";
      case "medicaid number" -> "MA";
      case "medical record number" -> "MR";
      case "medicare number" -> "MC";
      case "mother's identifier" -> "MO";
      case "national unique individual identifier" -> "NI";
      case "other" -> "OTH";
      case "partner services patient number" -> "PSID";
      case "patient external identifier" -> "PT";
      case "patient internal identifier" -> "PI";
      case "person number" -> "PN";
      case "prison identification number" -> "PIN";
      case "ryan white identifier" -> "RW";
      case "social security" -> "SS";
      case "visa/passport" -> "VS";
      case "wic identifier" -> "WC";
      default -> type;
    };
  }

  @When("the profile requests at least {int} identifications")
  public void i_request_at_least_x_identifications(final int requested) {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(requested);

    this.results.active(this.resolver.resolve(profile, page));
  }

  @Then("the profile has {int} associated identifications")
  public void the_profile_has_x_associated_identifications(int total) {
    Page<PatientIdentification> actual = this.results.active();
    assertThat(actual.getTotalElements()).isEqualTo(total);
  }

  @Then("the profile shows {int} associated identifications")
  public void the_profile_shows_x_associated_identifications(int total) {
    Page<PatientIdentification> actual = this.results.active();
    assertThat(actual.getNumberOfElements()).isEqualTo(total);
  }
}
