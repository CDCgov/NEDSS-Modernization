package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.time.ZoneId;

public class PatientNameDemographicSteps {

  private final PatientMother mother;
  private final Active<PatientIdentifier> patient;

  PatientNameDemographicSteps(
      final PatientMother mother,
      final Active<PatientIdentifier> patient
  ) {
    this.mother = mother;
    this.patient = patient;
  }

  @Given("the patient has a name")
  public void the_patient_has_a_name() {
    mother.withName(patient.active());
  }

  @Given("the patient has the {string} name {string} {string}")
  public void the_patient_has_the_name(
      final String type,
      final String first,
      final String last) {

    String resolvedType = resolveType(type);

    mother.withName(
        patient.active(),
        resolvedType,
        first,
        last);
  }

  @Given("the patient has the {string} name {string} {string} as of {string}")
  public void the_patient_has_the_name_as_of(
      final String type,
      final String first,
      final String last,
      final String asOf) {

    String resolvedType = resolveType(type);

    mother.withName(
        patient.active(),
        LocalDate.parse(asOf).atStartOfDay(ZoneId.systemDefault()).toInstant(),
        resolvedType,
        first,
        last);
  }

  @Given("the patient has the {string} name {string} {string} {string}, {string} as of {string}")
  public void the_patient_has_the_name_use_first_middle_last_suffix_as_of(
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix,
      final String asOf) {

    String resolvedType = resolveType(type);
    String resolvedSuffix = resolveSuffix(suffix);

    mother.withName(
        patient.active(),
        LocalDate.parse(asOf).atStartOfDay(ZoneId.systemDefault()).toInstant(),
        resolvedType,
        first,
        middle,
        last,
        resolvedSuffix);

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

}
