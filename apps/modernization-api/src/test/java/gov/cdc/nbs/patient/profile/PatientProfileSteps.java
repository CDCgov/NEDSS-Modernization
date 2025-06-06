package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientProfileSteps {

  private final Active<PatientIdentifier> patient;

  private final PersonRepository repository;

  private final PatientShortIdentifierResolver shortIdentifierResolver;

  private final PatientProfileResolver resolver;

  PatientProfileSteps(
      final Active<PatientIdentifier> patient,
      final PersonRepository repository,
      final PatientShortIdentifierResolver shortIdentifierResolver,
      final PatientProfileResolver resolver
  ) {
    this.patient = patient;
    this.repository = repository;
    this.shortIdentifierResolver = shortIdentifierResolver;
    this.resolver = resolver;
  }

  PatientProfile profile;

  Exception exception;

  @Before
  public void clear() {
    this.profile = null;
    this.exception = null;
  }

  @When("a profile is requested by patient identifier")
  public void a_profile_is_loaded_by_patient_identifier() {
    try {
      this.profile = resolver.find(String.valueOf(this.patient.active().id()), null).orElse(null);
    } catch (Exception thrown) {
      this.exception = thrown;
    }
  }

  @When("a profile is requested by short identifier")
  public void a_profile_is_loaded_by_short_identifier() {

    PatientIdentifier active = patient.active();

    Person person = repository.findById(active.id()).orElseThrow();

    long shortId = shortIdentifierResolver.resolve(person.getLocalId()).orElseThrow();

    try {
      this.profile = resolver.find(null, String.valueOf(shortId)).orElse(null);
    } catch (Exception thrown) {
      this.exception = thrown;
    }

  }

  @Then("the profile is found")
  public void the_profile_is_found() {

    assertThat(this.profile).isNotNull();
    assertThat(this.exception).isNull();
  }

  @Then("the profile is not accessible")
  public void the_profile_has_no_associated_document() {

    assertThat(this.profile).isNull();
    assertThat(this.exception).isInstanceOf(AccessDeniedException.class);
  }

}
