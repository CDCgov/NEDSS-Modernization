package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientCreateMutationBirthSteps {

  private final Active<PatientInput> input;

  private final Available<PatientIdentifier> patients;

  private final PersonRepository repository;

  PatientCreateMutationBirthSteps(
      final Active<PatientInput> input,
      final Available<PatientIdentifier> patients,
      final PersonRepository repository
  ) {
    this.input = input;
    this.patients = patients;
    this.repository = repository;
  }

  @Given("the new patient's birth is entered")
  public void the_new_patient_birth_is_entered() {
    this.input.active().setAsOf(RandomUtil.dateInPast());
    this.input.active().setDateOfBirth(RandomUtil.dateInPast());
    this.input.active().setBirthGender(RandomUtil.gender());
  }

  @Then("the new patient has the entered birth")
  public void the_new_patient_has_the_entered_birth() {
    Person actual = repository.findById(this.patients.one().id()).orElseThrow();
    PatientInput expected = this.input.active();

    assertThat(actual)
        .returns(expected.getAsOf(), Person::getAsOfDateSex)
        .returns(expected.getBirthGender(), Person::getBirthGenderCd)
    ;

    if (expected.getDateOfBirth() != null) {
      LocalDateTime expectedBirthTime = expected.getDateOfBirth()
          .atStartOfDay();

      assertThat(actual.getBirthTime()).isEqualTo(expectedBirthTime);
    } else {
      assertThat(actual.getBirthTime()).isNull();
    }
  }

}
