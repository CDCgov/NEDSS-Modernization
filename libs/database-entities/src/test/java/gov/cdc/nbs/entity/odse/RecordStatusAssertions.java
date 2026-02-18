package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.audit.RecordStatus;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class RecordStatusAssertions {

  public static Consumer<RecordStatus> active(final String when) {
    return status("ACTIVE", when);
  }

  public static Consumer<RecordStatus> inactive(final String when) {
    return status("INACTIVE", when);
  }

  public static Consumer<RecordStatus> status(final String value, final String when) {
    return status(value, LocalDateTime.parse(when));
  }

  public static Consumer<RecordStatus> status(final String value, final LocalDateTime when) {
    return recordStatus ->
        assertThat(recordStatus)
            .describedAs("Record status of %s at %s", value, when)
            .returns(value, RecordStatus::status)
            .returns(when, RecordStatus::appliedOn);
  }

  private RecordStatusAssertions() {}
}
