package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Then;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientProfileMortalitySteps {


  private final Available<PatientIdentifier> patients;


  private final PatientMortalityResolver resolver;

  PatientProfileMortalitySteps(
      final Available<PatientIdentifier> patients,
      final PatientMortalityResolver resolver
  ) {

    this.patients = patients;
    this.resolver = resolver;
  }

  @Then("the profile has no associated mortality")
  public void the_profile_has_no_associated_mortality() {
    long patient = this.patients.one().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    Optional<PatientMortality> actual = this.resolver.resolve(profile);
    assertThat(actual).isEmpty();
  }

  @Then("the profile mortality is not accessible")
  public void the_profile_mortality_is_not_accessible() {
    long patient = this.patients.one().id();


    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    assertThatThrownBy(
        () -> this.resolver.resolve(profile)
    )
        .isInstanceOf(AccessDeniedException.class);
  }
}
