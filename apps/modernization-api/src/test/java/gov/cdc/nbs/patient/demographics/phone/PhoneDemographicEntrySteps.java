package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographic.email;
import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographic.phoneNumber;

public class PhoneDemographicEntrySteps {

  private final Active<PhoneDemographic> active;

  PhoneDemographicEntrySteps(final Active<PhoneDemographic> active) {
    this.active = active;
  }

  @Given("I am entering the {phoneType} - {phoneUse} number of {string} as of {localDate}")
  public void enteringPhone(
      final String type,
      final String use,
      final String number,
      final LocalDate asOf
  ) {
    this.active.active(phoneNumber(asOf, type, use, number));
  }

  @Given("I am entering the {phoneType} - {phoneUse} email address of {string} as of {localDate}")
  public void enteringEmail(
      final String type,
      final String use,
      final String emailAddress,
      final LocalDate asOf
  ) {
    this.active.active(email(asOf, type, use, emailAddress));
  }
}
