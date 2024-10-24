package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.time.Month;

public class PatientSearchCriteriaSteps {

  private final Active<PatientIdentifier> patient;
  private final Active<PatientFilter> activeCriteria;

  PatientSearchCriteriaSteps(
      final Active<PatientIdentifier> patient,
      final Active<PatientFilter> activeCriteria) {
    this.patient = patient;
    this.activeCriteria = activeCriteria;
  }

  @Given("I add the patient criteria for a(n) {string} equal to {string}")
  public void i_add_the_patient_criteria_for_a_field_that_is_value(
      final String field,
      final String value
  ) {

    if (field == null || field.isEmpty()) {
      return;
    }

    this.activeCriteria.active(filter -> applyCriteria(field, value, filter));

  }

  private PatientFilter applyCriteria(
      final String field,
      final String value,
      final PatientFilter criteria
  ) {
    switch (field.toLowerCase()) {
      case "patient id" -> criteria.setId(value);
      case "first name" -> criteria.setFirstName(value);
      case "last name" -> criteria.setLastName(value);
      case "disable soundex" -> criteria.setDisableSoundex(value.equals("true"));
      case "phone number" -> criteria.setPhoneNumber(value);
      case "email", "email address" -> criteria.setEmail(value);
      case "city" -> criteria.setCity(value);
      case "address" -> criteria.setAddress(value);
      case "identification type" -> criteria.getIdentification().setIdentificationType(value);
      case "identification value" -> criteria.getIdentification().setIdentificationNumber(value);
      default -> throw new IllegalStateException(
          "Unexpected search criteria %s equal %s".formatted(field, value));
    }
    return criteria;
  }

  @Given("I add the patient criteria for a gender of {gender}")
  public void i_add_the_patient_criteria_for_a_gender(final String value) {
    this.activeCriteria.maybeActive().ifPresent(found -> found.withGender(Gender.resolve(value).value()));
  }

  @Given("I would like patients that are {string}")
  public void i_add_the_partial_patient_criteria_record_status_of(final String status) {
    RecordStatus recordStatus = PatientStatusCriteriaResolver.resolve(status);

    this.activeCriteria.active(new PatientFilter(recordStatus));
  }

  @Given("I would like to search for a patient using a short ID")
  public void i_would_like_to_search_for_a_patient_using_a_short_ID() {

    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withId(String.valueOf(found.shortId()))));

  }

  @Given("I would like to search for a patient using a local ID")
  public void i_would_like_to_search_for_a_patient_using_a_local_ID() {
    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withId(found.local())));
  }

  @Given("I add the patient criteria for patient's born on the {nth} day of the month")
  public void i_would_like_to_search_for_a_patient_born_on_a_specific_day_of_the_month(final int value) {
    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withBornOnDay(value))
    );
  }

  @Given("I add the patient criteria for patient's born in the month of {month}")
  public void i_would_like_to_search_for_a_patient_born_on_a_specific_month(final Month month) {
    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withBornOnMonth(month.getValue()))
    );
  }

  @Given("I add the patient criteria for patient's born in the year {int}")
  public void i_would_like_to_search_for_a_patient_born_on_a_specific_year(final int year) {
    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withBornOnYear(year))
    );
  }

  @Given("I add the patient criteria for patient's born between {localDate} and {localDate}")
  public void i_would_like_to_search_for_a_patient_born_between(final LocalDate from, final LocalDate to) {
    this.patient.maybeActive().ifPresent(
        found -> this.activeCriteria.active(criteria -> criteria.withBornBetween(from,to))
    );
  }
}
