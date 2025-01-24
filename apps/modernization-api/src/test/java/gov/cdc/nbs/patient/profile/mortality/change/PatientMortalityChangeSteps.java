package gov.cdc.nbs.patient.profile.mortality.change;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
public class PatientMortalityChangeSteps {

  private final Faker faker = new Faker(Locale.of("en-us"));

  private final Available<PatientIdentifier> patients;

  private final PatientMortalityController controller;

  private final EntityManager entityManager;

  private UpdatePatientMortality changes;

  PatientMortalityChangeSteps(
      final Available<PatientIdentifier> patients,
      final PatientMortalityController controller,
      final EntityManager entityManager
  ) {
    this.patients = patients;
    this.controller = controller;
    this.entityManager = entityManager;
  }

  @Before("@patient-profile-mortality-change")
  public void reset() {
    this.changes = null;
  }

  @When("a patient is deceased")
  public void a_patient_is_deceased() {
    PatientIdentifier patient = this.patients.one();

    this.changes = new UpdatePatientMortality(
        patient.id(),
        RandomUtil.dateInPast(),
        Deceased.Y.value(),
        RandomUtil.dateInPast(),
        faker.address().city(),
        RandomUtil.getRandomStateCode(),
        RandomUtil.maybeOneFrom("25009", "34013", "36031", "50009", "51057"),
        RandomUtil.country());

    controller.update(changes);
  }

  @Then("the patient profile contains the details of mortality")
  @Transactional
  public void the_patient_contains_the_details_of_mortality() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual)
        .returns(changes.asOf(), Person::getAsOfDateMorbidity)
        .returns(Deceased.resolve(changes.deceased()), Person::getDeceasedIndCd)
        .returns(changes.deceasedOn(), Person::getDeceasedTime)
        .satisfies(
            addresses -> assertThat(addresses.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns(changes.city(), PostalLocator::getCityDescTxt)
                        .returns(changes.state(), PostalLocator::getStateCd)
                        .returns(changes.county(), PostalLocator::getCntyCd)
                        .returns(changes.country(), PostalLocator::getCntryCd)));
  }

  @When("a patient is not known to be deceased")
  public void a_patient_is_not_known_to_be_deceased() {
    PatientIdentifier patient = this.patients.one();

    this.changes = new UpdatePatientMortality(
        patient.id(),
        RandomUtil.dateInPast(),
        RandomUtil.maybeOneFrom(Deceased.N.value(), Deceased.UNK.value()),
        RandomUtil.dateInPast(),
        faker.address().city(),
        RandomUtil.getRandomStateCode(),
        RandomUtil.maybeOneFrom("25009", "34013", "36031", "50009", "51057"),
        RandomUtil.country());

    controller.update(changes);
  }

  @Then("the patient does not contain the details of mortality")
  @Transactional
  public void the_patient_does_not_contain_the_details_of_mortality() {
    PatientIdentifier patient = this.patients.one();

    Person actual = this.entityManager.find(Person.class, patient.id());

    assertThat(actual)
        .returns(changes.asOf(), Person::getAsOfDateMorbidity)
        .returns(Deceased.resolve(changes.deceased()), Person::getDeceasedIndCd)
        .returns(null, Person::getDeceasedTime)
        .satisfies(
            addresses -> assertThat(addresses.addresses())
                .noneSatisfy(
                    address -> assertThat(address)
                        .returns("U", PostalEntityLocatorParticipation::getCd)
                        .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                        .extracting(PostalEntityLocatorParticipation::getLocator)
                        .returns(changes.city(), PostalLocator::getCityDescTxt)
                        .returns(changes.state(), PostalLocator::getStateCd)
                        .returns(changes.county(), PostalLocator::getCntyCd)
                        .returns(changes.country(), PostalLocator::getCntryCd)));
  }

  @Then("I am unable to change a patient's mortality")
  public void i_am_unable_to_change_a_patient_mortality() {
    assertThatThrownBy(() -> controller.update(changes))
        .isInstanceOf(AccessDeniedException.class);
  }
}
