package gov.cdc.nbs.report.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Component
public class FieldFormatter {
  public String formatField(String type, String value) {
    return switch (type.toUpperCase()) {
      case "STRING" -> "'" + value.replace("'", "''") + "'";
      case "DATE" -> convertToSQLDate(value);
      case "INTEGER", "NUMBER" -> value;
      default -> throw new IllegalArgumentException("Unexpected Column Type: " + type);
    };
  }

  private static String convertToSQLDate(String date) {
    if (date == null || date.isBlank()) return null;

    LocalDate localDate;
    try {
      if (date.length() > 7) {
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      } else {
        localDate = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy")).atDay(1);
      }
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Can't Convert Date!");
    }
    // Produces '2023-12-31'
    return "'" + localDate.toString() + "'";
  }
}
