package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDate;
import java.util.function.Supplier;

public class AdministrativeEntrySteps {

  private final Clock clock;
  private final Active<Administrative> input;

  AdministrativeEntrySteps(final Clock clock, final Active<Administrative> input) {
    this.clock = clock;
    this.input = input;
  }

  private Supplier<Administrative> initial() {
    return () -> new Administrative(LocalDate.now(clock));
  }

  @Given("I enter the patient administrative as of date {localDate}")
  public void i_enter_the_patient_administrative_as_of(final LocalDate asOf) {
    this.input.active(current -> current.withAsOf(asOf), initial());
  }

  @Given("I enter the administrative comment {string}")
  public void i_enter_the_patient_administrative_comment(final String comment) {
    this.input.active(current -> current.withComment(comment), initial());
  }
}
