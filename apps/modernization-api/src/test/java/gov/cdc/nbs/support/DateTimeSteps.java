package gov.cdc.nbs.support;

import io.cucumber.java.ParameterType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeSteps {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @ParameterType(name = "localDate", value = "(?:[0]\\d|1[0-2])/(?:[0-2]\\d|3[01])/(?:(?:19|20)\\d{2})")
  public LocalDate localDate(final String value) {
    return LocalDate.parse(value, DATE_FORMATTER);
  }

  @ParameterType(name = "date", value = "(?:[0]\\d|1[0-2])/(?:[0-2]\\d|3[01])/(?:(?:19|20)\\d{2})")
  public Instant date(final String value) {
    return localDate(value)
        .atStartOfDay()
        .atZone(ZoneOffset.UTC)
        .toInstant();
  }
}
