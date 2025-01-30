package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;



public class StatusAssertions {

  public static Consumer<Status> active(final String when) {
    return status('A', when);
  }


  public static Consumer<Status> status(final char value, final String when) {
    return status(value, LocalDateTime.parse(when));
  }

  public static Consumer<Status> status(final char value, final LocalDateTime when) {
    return recordStatus -> assertThat(recordStatus)
        .describedAs("Status of %s at %s", value, when)
        .returns(value, Status::status)
        .returns(when, Status::appliedOn);

  }

  private StatusAssertions() {

  }
}
