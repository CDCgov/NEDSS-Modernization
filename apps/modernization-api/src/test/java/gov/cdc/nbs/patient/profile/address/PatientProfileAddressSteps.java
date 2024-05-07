package gov.cdc.nbs.patient.profile.address;

import net.datafaker.Faker;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.TestPatient;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.address.change.NewPatientAddressInput;
import gov.cdc.nbs.patient.profile.address.change.UpdatePatientAddressInput;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PatientProfileAddressSteps {

  private final Faker faker = new Faker(Locale.of("en-us"));

  @Autowired
  PatientMother mother;

  @Autowired
  Active<PatientIdentifier> activePatient;

  @Autowired
  PatientAddressResolver resolver;

  @Autowired
  Active<PatientInput> input;

  @Autowired
  TestPatient patient;

  @Autowired
  Active<NewPatientAddressInput> newRequest;

  @Autowired
  Active<UpdatePatientAddressInput> updateRequest;

  @Given("the patient has an address")
  public void the_patient_has_an_address() {
    mother.withAddress(activePatient.active());
  }

  @Given("the patient has a {string} address at {string} {string} {string}")
  public void the_patient_has_an_address_at(
      final String use,
      final String address,
      final String city,
      final String zip) {

    PatientIdentifier identifier = activePatient.active();

    String resolvedUse = resolveUse(use);

    mother.withAddress(
        identifier,
        resolvedUse,
        address,
        city,
        null,
        zip);

  }

  private String resolveUse(final String use) {
    return switch (use.toLowerCase()) {
      case "birth delivery address" -> "BDL";
      case "birth place" -> "BIR";
      case "death place" -> "DTH";
      case "home" -> "H";
      case "primary business" -> "PB";
      case "secondary business" -> "SB";
      case "temporary" -> "TMP";
      default -> throw new IllegalStateException("Unexpected address use: " + use.toLowerCase());
    };
  }

  @Given("the new patient's address is entered")
  public void the_new_patient_address_is_entered() {

    PatientInput.PostalAddress address = new PatientInput.PostalAddress(
        faker.address().streetAddress(),
        null,
        faker.address().city(),
        RandomUtil.getRandomStateCode(),
        RandomUtil.getRandomString(),
        RandomUtil.country(),
        RandomUtil.getRandomNumericString(15),
        null);

    this.input.active().getAddresses().add(address);
  }

  @Then("the new patient has the entered address")
  @Transactional
  public void the_new_patient_has_the_entered_address() {
    Person actual = patient.managed();

    Collection<PostalEntityLocatorParticipation> addresses = actual.addresses();

    if (!addresses.isEmpty()) {

      assertThat(addresses)
          .satisfiesExactlyInAnyOrder(PatientCreateAssertions.containsAddresses(input.active().getAddresses()));
    }

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
