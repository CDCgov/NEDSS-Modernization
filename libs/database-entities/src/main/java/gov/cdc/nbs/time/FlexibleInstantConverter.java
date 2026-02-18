package gov.cdc.nbs.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;

/**
 * Performs conversions between {@code Instant} and {@code String} to ensure consistency in how
 * {@code Instant} instances are persisted. The conversion from {@link String} to {@link Instant} is
 * flexible, accepting the following formats;
 *
 * <ul>
 *   <li>M/d/uuuu
 *   <li>M/d/uu
 *   <li>uuuu-MM-dd HH:mm:ss
 *   <li>uuuu-MM-dd HH:mm:ss.S
 *   <li>uuuu-MM-dd HH:mm:ss.SS
 *   <li>uuuu-MM-dd HH:mm:ss.SSS
 *   <li>uuuu-MM-ddTHH:mm:ss
 *   <li>uuuu-MM-ddTHH:mm:ss.SSS
 * </ul>
 *
 * The conversion from {@link Instant} to {@link String} will result in the {@code YYYY-MM-DD
 * hh:mm:ss[.nnn]} format acceptable for MS SQL Server.
 */
public class FlexibleInstantConverter {

  private static final List<DateTimeFormatter> READERS =
      List.of(
          new DateTimeFormatterBuilder()
              .append(DateTimeFormatter.ofPattern("M/d/[uuuu][uu]"))
              .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
              .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
              .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
              .toFormatter(),
          new DateTimeFormatterBuilder()
              .append(DateTimeFormatter.ISO_LOCAL_DATE)
              .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
              .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
              .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
              .toFormatter(),
          DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss[.[SSS][SS][S]]"),
          DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss[.SSS]"));
  private static final DateTimeFormatter WRITER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC);

  public static String toString(final Instant instant) {
    return instant == null ? null : WRITER.format(instant);
  }

  /**
   * Converts a {@link LocalDate} into an {@link Instant} at the start of the day in the UTC
   * Timezone.
   */
  public static String toString(final LocalDate date) {
    return date == null ? null : WRITER.format(date.atStartOfDay().toInstant(ZoneOffset.UTC));
  }

  public static Instant fromString(final String value) {

    return value == null ? null : tryParse(value);
  }

  private static Instant tryParse(final String value) {

    DateTimeParseException occurred = null;
    for (var format : READERS) {
      try {
        return LocalDateTime.parse(value, format).toInstant(ZoneOffset.UTC);
      } catch (DateTimeParseException e) {
        // ignore exception until all formats have been tried
        occurred = e;
      }
    }

    if (occurred != null) {
      throw occurred;
    }

    return null;
  }

  private FlexibleInstantConverter() {}
}
