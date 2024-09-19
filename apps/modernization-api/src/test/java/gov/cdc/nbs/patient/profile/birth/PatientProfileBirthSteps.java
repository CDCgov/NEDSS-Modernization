package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileBirthSteps {

  private final Active<PatientIdentifier> activePatient;
  private final PatientProfileBirthRequester requester;
  private final Active<ResultActions> response;

  private final PatientBirthResolver resolver;

  PatientProfileBirthSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientProfileBirthRequester requester,
      final Active<ResultActions> response,
      final PatientBirthResolver resolver
  ) {
    this.activePatient = activePatient;
    this.requester = requester;
    this.response = response;
    this.resolver = resolver;
  }

  @Then("the profile has no associated birth")
  public void the_profile_has_no_associated_birth() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    Optional<PatientBirth> actual = this.resolver.resolve(profile);
    assertThat(actual).isEmpty();
  }

  @Then("the profile birth is not accessible")
  public void the_profile_birth_is_not_accessible() {
    long patient = this.activePatient.active().id();


    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    assertThatThrownBy(
        () -> this.resolver.resolve(profile)
    )
        .isInstanceOf(AccessDeniedException.class);
  }

  @Then("I view the Patient Profile Birth demographics")
  public void i_view_the_patient_profile_administration() {
    this.activePatient.maybeActive()
        .map(requester::birth)
        .ifPresent(this.response::active);
  }
}
