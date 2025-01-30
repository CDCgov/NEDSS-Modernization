package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.demographic.GeneralInformation;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;


public class PatientCreateMutationGeneralSteps {

  private final Active<PatientInput> input;
  private final Active<Person> patient;


  PatientCreateMutationGeneralSteps(
      final Active<PatientInput> input,
      final Active<Person> patient
  ) {
    this.input = input;
    this.patient = patient;
  }

  @Given("the new patient's marital status is entered")
  public void the_new_patient_marital_status_is_entered() {
    PatientInput active = this.input.active();

    active.setAsOf(RandomUtil.dateInPast());
    active.setMaritalStatus(RandomUtil.getRandomString());
  }

  @Then("the new patient has the entered marital status")
  public void the_new_patient_has_the_entered_martial_status() {
    Person actual = patient.active();
    PatientInput expected = this.input.active();

    assertThat(actual)
        .extracting(Person::getGeneralInformation)
        .returns(expected.getMaritalStatus(), GeneralInformation::maritalStatus);
  }

}
