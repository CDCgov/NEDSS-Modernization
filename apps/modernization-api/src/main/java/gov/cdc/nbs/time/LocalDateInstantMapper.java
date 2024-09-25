package gov.cdc.nbs.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateInstantMapper {

  public static Instant from(final LocalDate localDate) {
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
  }

  private LocalDateInstantMapper() {
    // static
  }
}
