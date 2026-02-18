package gov.cdc.nbs.patient.demographics.phone;

import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographic.email;
import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographic.phoneNumber;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.java.en.Given;
import java.time.LocalDate;
import java.util.Objects;

public class PhoneDemographicEntrySteps {

  private final Available<PhoneDemographic> available;

  PhoneDemographicEntrySteps(final Available<PhoneDemographic> available) {
    this.available = available;
  }

  @Given("I am entering the {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void enteringPhone(
      final String type, final String use, final String number, final LocalDate asOf) {
    this.available.selected(
        current -> current.withPhoneValues(asOf, type, use, number),
        () -> phoneNumber(asOf, type, use, number));
  }

  @Given("I am entering the {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void enteringEmail(
      final String type, final String use, final String emailAddress, final LocalDate asOf) {
    this.available.available(email(asOf, type, use, emailAddress));
    this.available.selected(
        current -> current.withEmailValues(asOf, type, use, emailAddress),
        () -> email(asOf, type, use, emailAddress));
  }

  @Given("I select the entered phone that is as of {localDate}")
  public void select(final LocalDate asOf) {
    this.available.select(item -> Objects.equals(item.asOf(), asOf));
  }

  @Given("I remove the entered phone as of {localDate}")
  public void remove(final LocalDate asOf) {
    this.available.removeIf(item -> Objects.equals(item.asOf(), asOf));
  }
}
