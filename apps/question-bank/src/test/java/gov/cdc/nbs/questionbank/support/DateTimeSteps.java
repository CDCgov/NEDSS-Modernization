package gov.cdc.nbs.questionbank.support;

import io.cucumber.java.ParameterType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateTimeSteps {

  @ParameterType(name = "past", value = "(a|[0-9]+) (day|week|month|year)(?:s?) ago")
  public Instant past(final String modifier, final String unit) {

    long value = Objects.equals(modifier, "a") ? 1 : Long.parseLong(modifier);

    ChronoUnit chronoUnit = resolve(unit);

    return LocalDate.now().minus(value, chronoUnit).atStartOfDay().toInstant(ZoneOffset.UTC);
  }

  private ChronoUnit resolve(final String unit) {
    return switch (unit.toLowerCase()) {
      case "day" -> ChronoUnit.DAYS;
      case "week" -> ChronoUnit.WEEKS;
      case "month" -> ChronoUnit.MONTHS;
      case "year" -> ChronoUnit.YEARS;

      default -> throw new IllegalStateException("Unexpected time unit value: " + unit);
    };
  }

  @ParameterType(name = "localDate", value = "^\\d{4}-([0]\\d|1[0-2])-([0-2]\\d|3[01])$")
  public LocalDate localDate(final String value) {
    return LocalDate.parse(value);
  }
}
