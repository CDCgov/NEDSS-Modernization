package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.datafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientCreateMutationNameSteps {

  private final Faker faker = new Faker();

  private final Active<PatientInput> input;
  private final Active<Person> created;

  PatientCreateMutationNameSteps(
      final Active<PatientInput> input,
      final Active<Person> created
  ) {
    this.input = input;
    this.created = created;
  }


  @Given("the new patient's name is entered")
  public void the_new_patient_name_is_entered() {

    PatientInput.Name name = new PatientInput.Name();
    name.setUse(PatientInput.NameUseCd.L);
    name.setFirst(faker.name().firstName());
    name.setLast(faker.name().lastName());

    this.input.active().getNames().add(name);
  }

  @Then("the new patient has the entered name")
  public void the_new_patient_has_the_entered_name() {
    Person actual = created.active();

    if (!actual.getNames().isEmpty()) {
      assertThat(actual.getNames())
          .satisfiesExactly(PatientCreateAssertions.containsNames(this.input.active().getNames()));
    }
  }
}
