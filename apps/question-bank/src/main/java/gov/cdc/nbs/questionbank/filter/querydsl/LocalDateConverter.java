package gov.cdc.nbs.questionbank.filter.querydsl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateConverter {

  public static Instant asInstant(final LocalDate date) {
    return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
  }

  private LocalDateConverter() {}
}
