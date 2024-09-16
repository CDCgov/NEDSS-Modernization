package gov.cdc.nbs.patient.profile.administrative;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.support.util.RandomUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class PatientCreateMutationAdministrativeSteps {

  private final Active<PatientInput> input;

  private final Active<Person> patient;

  PatientCreateMutationAdministrativeSteps(
      final Active<PatientInput> input,
      final Active<Person> patient
  ) {
    this.input = input;
    this.patient = patient;
  }

  @Given("the new patient comment is entered")
  public void the_new_patient_comment_is_entered() {
    PatientInput active = this.input.active();

    active.setAsOf(RandomUtil.getRandomDateInPast());
    active.setComments(RandomUtil.getRandomString());
  }

  @Then("the new patient has the entered comment")
  public void the_new_patient_has_the_entered_comment() {
    Person actual = patient.active();
    PatientInput expected = this.input.active();

    assertThat(actual)
        .returns(expected.getAsOf(), Person::getAsOfDateAdmin)
        .returns(expected.getComments(), Person::getDescription);


  }
}
