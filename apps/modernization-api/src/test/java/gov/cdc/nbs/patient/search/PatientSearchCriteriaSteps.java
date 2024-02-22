package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

public class PatientSearchCriteriaSteps {

  private final Active<PatientFilter> criteria;

  PatientSearchCriteriaSteps(
      final Active<PatientFilter> criteria
  ) {
    this.criteria = criteria;
  }

  @Given("I add the patient criteria for a(n) {string} equal to {string}")
  public void i_add_the_patient_criteria_for_a_field_that_is_value(
      final String field,
      final String value
  ) {

    if (field == null || field.isEmpty()) {
      return;
    }

    this.criteria.active(filter -> applyCriteria(field, value, filter));

  }

  private PatientFilter applyCriteria(
      final String field,
      final String value,
      final PatientFilter criteria
  ) {
    switch (field.toLowerCase()) {
      case "first name" -> criteria.setFirstName(value);
      case "last name" -> criteria.setLastName(value);
      case "phone number" -> criteria.setPhoneNumber(value);
      case "email", "email address" -> criteria.setEmail(value);
      case "city" -> criteria.setCity(value);
      case "address" -> criteria.setAddress(value);
      case "identification type" -> criteria.getIdentification().setIdentificationType(value);
      case "identification value" -> criteria.getIdentification().setIdentificationNumber(value);
      default -> throw new IllegalStateException(
          String.format("Unexpected search criteria %s equal %s", field, value));
    }
    return criteria;
  }

  @Given("I add the patient criteria for a gender of {gender}")
  public void i_add_the_patient_criteria_for_a_gender(final String value) {
    this.criteria.maybeActive().ifPresent(found -> found.withGender(Gender.resolve(value).value()));
  }

  @Given("I would like patients that are {string}")
  public void i_add_the_partial_patient_criteria_record_status_of(final String status) {
    RecordStatus recordStatus = PatientStatusCriteriaResolver.resolve(status);

    this.criteria.active(new PatientFilter(recordStatus));
  }



}
