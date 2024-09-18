package gov.cdc.nbs.patient.profile.administrative;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;

import java.time.Instant;

public class AdministrativeEntrySteps {

  private final Active<Administrative> input;

  AdministrativeEntrySteps(final Active<Administrative> input) {
    this.input = input;
  }

  @Given("I am entering the administrative as of date {date}")
  public void i_am_entering_patient_administrative_as_of(final Instant asOf) {
    this.input.active(new Administrative(asOf));
  }

  @Given("I enter the administrative comment {string}")
  public void i_enter_the_patient_administrative_comment(final String comment) {
    this.input.active(current -> current.withComment(comment));
  }
}
