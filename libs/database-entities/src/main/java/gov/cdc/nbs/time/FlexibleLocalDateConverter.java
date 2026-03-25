package gov.cdc.nbs.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Performs conversions between {@code LocalDate} and {@code String}. The conversion from {@link
 * String} to {@link LocalDate} is flexible, accepting the following formats;
 *
 * <ul>
 *   <li>M/d/uuuu
 *   <li>M/d/uu
 *   <li>uuuu-MM-dd
 * </ul>
 *
 * The conversion from {@link LocalDate} to {@link String} will result in the {@code YYYY-MM-DD
 * hh:mm:ss[.nnn]} format acceptable for MS SQL Server.
 */
public class FlexibleLocalDateConverter {

  private static final DateTimeFormatter READER =
      new DateTimeFormatterBuilder()
          .appendOptional(DateTimeFormatter.ofPattern("M/d/[uuuu][uu]"))
          .appendOptional(DateTimeFormatter.ISO_DATE)
          .toFormatter();
  private static final DateTimeFormatter WRITER = DateTimeFormatter.ISO_DATE;

  public static String toString(final LocalDate input) {
    return input == null ? null : WRITER.format(input);
  }

  public static LocalDate fromString(final String input) {
    return input == null ? null : tryParse(input);
  }

  private static LocalDate tryParse(final String input) {
    return LocalDate.parse(input, READER);
  }

  private FlexibleLocalDateConverter() {}
}
