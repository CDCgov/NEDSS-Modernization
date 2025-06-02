package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.address.change.NewPatientAddressInput;
import gov.cdc.nbs.patient.profile.address.change.UpdatePatientAddressInput;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileAddressSteps {

  private final Active<PatientIdentifier> activePatient;

  private final PatientAddressResolver resolver;

  private final Active<NewPatientAddressInput> newRequest;

  private final Active<UpdatePatientAddressInput> updateRequest;

  PatientProfileAddressSteps(
      final Active<PatientIdentifier> activePatient,
      final PatientAddressResolver resolver,
      final Active<NewPatientAddressInput> newRequest,
      final Active<UpdatePatientAddressInput> updateRequest) {
    this.activePatient = activePatient;
    this.resolver = resolver;
    this.newRequest = newRequest;
    this.updateRequest = updateRequest;
  }


  @Then("the profile has associated addresses")
  public void the_profile_has_associated_addresses() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(5);

    Page<PatientAddress> actual = this.resolver.resolve(profile, page);
    assertThat(actual).isNotEmpty();
  }

  @Then("the patient profile has the new address")
  public void the_patient_profile_has_the_new_address() {
    PatientIdentifier patient = this.activePatient.active();

    PatientProfile profile = new PatientProfile(patient.id(), patient.local(), (short) patient.shortId());

    GraphQLPage page = new GraphQLPage(5);

    NewPatientAddressInput input = newRequest.active();

    Page<PatientAddress> found = this.resolver.resolve(profile, page);

    assertThat(found).anySatisfy(
        actual -> assertThat(actual)
            .returns(input.type(), i -> i.type().id())
            .returns(input.use(), i -> i.use().id())
            .returns(input.asOf(), PatientAddress::asOf)
            .returns(input.address1(), PatientAddress::address1)
            .returns(input.address2(), PatientAddress::address2)
            .returns(input.city(), PatientAddress::city)
            .returns(input.state(), i -> i.state().id())
            .returns(input.county(), i -> i.county().id())
            .returns(input.zipcode(), PatientAddress::zipcode)
            .returns(input.country(), i -> i.country().id())
            .returns(input.censusTract(), PatientAddress::censusTract));
  }

  @Then("the patient profile has the expected address")
  public void the_patient_profile_has_the_expected_address() {
    PatientIdentifier patient = this.activePatient.active();

    PatientProfile profile = new PatientProfile(patient.id(), patient.local(), (short) patient.shortId());

    GraphQLPage page = new GraphQLPage(5);

    UpdatePatientAddressInput input = updateRequest.active();

    Page<PatientAddress> found = this.resolver.resolve(profile, page);

    assertThat(found).anySatisfy(
        actual -> assertThat(actual)
            .returns(input.type(), i -> i.type().id())
            .returns(input.use(), i -> i.use().id())
            .returns(input.asOf(), PatientAddress::asOf)
            .returns(input.address1(), PatientAddress::address1)
            .returns(input.address2(), PatientAddress::address2)
            .returns(input.city(), PatientAddress::city)
            .returns(input.state(), i -> i.state().id())
            .returns(input.county(), i -> i.county().id())
            .returns(input.zipcode(), PatientAddress::zipcode)
            .returns(input.country(), i -> i.country().id())
            .returns(input.censusTract(), PatientAddress::censusTract));
  }

  @Then("the profile has no associated addresses")
  public void the_profile_has_no_associated_addresses() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(5);

    Page<PatientAddress> actual = this.resolver.resolve(profile, page);
    assertThat(actual).isEmpty();
  }

  @Then("the profile addresses are not accessible")
  public void the_profile_address_is_not_accessible() {
    long patient = this.activePatient.active().id();

    PatientProfile profile = new PatientProfile(patient, "local", (short) 1, RecordStatus.ACTIVE.toString());

    GraphQLPage page = new GraphQLPage(5);

    assertThatThrownBy(
        () -> this.resolver.resolve(profile, page))
        .isInstanceOf(AccessDeniedException.class);
  }


}
