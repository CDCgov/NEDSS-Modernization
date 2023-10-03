package gov.cdc.nbs.patient.profile.names;

import com.github.javafaker.Faker;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCreateAssertions;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientProfileNameSteps {

  private final Faker faker = new Faker();

  @Autowired
  PatientMother mother;

  @Autowired
  TestActive<PatientIdentifier> patient;

  @Autowired
  TestActive<PatientInput> input;

  @Autowired
  TestActive<Person> created;

  @Given("the patient has a name")
  public void the_patient_has_a_name() {
    mother.withName(patient.active());
  }

  @Given("the patient has the {string} name {string} {string}")
  public void the_patient_has_the_name(
      final String type,
      final String first,
      final String last
  ) {

    String resolvedType = resolveType(type);

    mother.withName(
        patient.active(),
        resolvedType,
        first,
        last
    );
  }

  @Given("the patient has the {string} name {string} {string} as of {string}")
  public void the_patient_has_the_name_as_of(
      final String type,
      final String first,
      final String last,
      final String asOf
  ) {

    String resolvedType = resolveType(type);

    mother.withName(
        patient.active(),
        LocalDate.parse(asOf).atStartOfDay(ZoneId.systemDefault()).toInstant(),
        resolvedType,
        first,
        last
    );
  }

  @Given("the patient has the {string} name {string} {string} {string}, {string} as of {string}")
  public void the_patient_has_the_name_use_first_middle_last_suffix_as_of(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final String asOf
  ) {

    String resolvedType = resolveType(type);
    String resolvedSuffix = resolveSuffix(suffix);

    mother.withName(
        patient.active(),
        LocalDate.parse(asOf).atStartOfDay(ZoneId.systemDefault()).toInstant(),
        resolvedType,
        first,
        middle,
        last,
        resolvedSuffix
    );

  }

  private String resolveType(final String use) {
    return switch (use.toLowerCase()) {
      case "legal" -> "L";
      case "alias" -> "AL";
      default -> throw new IllegalStateException("Unexpected name type: " + use);
    };
  }

  private String resolveSuffix(final String suffix) {
    return switch (suffix.toLowerCase()) {
      case "junior", "jr" -> "JR";
      case "esquire" -> "ESQ";
      default -> throw new IllegalStateException("Unexpected name suffix: " + suffix);
    };
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
