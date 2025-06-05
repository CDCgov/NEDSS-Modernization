package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.LocalDate;

public class AddressDemographicEntrySteps {

  private final Active<AddressDemographic> active;

  AddressDemographicEntrySteps(final Active<AddressDemographic> active) {
    this.active = active;
  }

  @Given("I am entering the {addressType} - {addressUse} address at {string} {string} {string} as of {localDate}")
  public void the_patient_has_an_address_as_of_at(
      final String type,
      final String use,
      final String address,
      final String city,
      final String zip,
      final LocalDate asOf
  ) {
    this.active.active(
        new AddressDemographic(
            asOf,
            type,
            use,
            address,
            null,
            city,
            null,
            zip,
            null,
            null,
            null,
            null
        )
    );
  }
}
