package gov.cdc.nbs.report.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class FieldFormatter {
  public String formatField(String type, String value) {
    return switch (type.toUpperCase()) {
      case "STRING" -> "'" + value.replace("'", "''").replace("[", "[[]").replace("%", "[%]") + "'";
      case "DATE", "DATETIME" -> convertToSQLDate(value);
      case "INTEGER", "NUMBER" -> validateNumeric(value);
      default -> throw new IllegalArgumentException("Unexpected Column Type: " + type);
    };
  }

  /**
   * Generates a string that contains the colName in brackets which protects against SQL reserved
   * words
   */
  public String convertToSQLColName(String colName, String colType) {
    String sqlColName = "[" + colName + "]";

    // Ensures comparison operators (e.g. <>, =, >, IN, etc...) work for DATETIME columns
    // when compared against a date value (e.g. '2026-05-28') since frontend passes date values
    if (colType.equals("DATETIME")) {
      return "CAST(" + sqlColName + " AS DATE)";
    }
    return sqlColName;
  }

  private static String convertToSQLDate(String date) {

    LocalDate localDate;
    try {
      localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Can't Convert Date: " + date);
    }
    // Produces 'YYYY-MM-DD'
    return "'" + localDate.toString() + "'";
  }

  public List<String> convertToSQLFromDateRange(List<String> dateValues) {
    if (dateValues == null || dateValues.size() != 2) {
      throw new IllegalArgumentException("Date range must contain exactly two values.");
    }

    String startDateRaw = dateValues.get(0);
    String endDateRaw = dateValues.get(1);

    // Parse Start Date (Index 0) - Defaults to 1st of month
    LocalDate startDate = parseDate(startDateRaw, true);

    // Parse End Date (Index 1) - Defaults to last day of month
    LocalDate endDate = parseDate(endDateRaw, false);

    return List.of("'" + startDate.toString() + "'", "'" + endDate.toString() + "'");
  }

  private static LocalDate parseDate(String date, boolean isStart) {
    try {
      int length = date.length();

      if (length > 7) {
        // Full date: MM/dd/yyyy
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      } else if (length > 4) {
        // Partial date: MM/yyyy
        YearMonth ym = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy"));
        return isStart ? ym.atDay(1) : ym.atEndOfMonth();
      } else {
        // Year only: yyyy
        java.time.Year y = java.time.Year.parse(date, DateTimeFormatter.ofPattern("yyyy"));
        return isStart ? y.atDay(1) : y.atDay(y.length());
        // y.length() returns 365 or 366, effectively giving Dec 31st
      }
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Can't Convert Date: " + date);
    }
  }

  /** Validates that the input is a well-formed number to prevent SQL Injection. */
  private String validateNumeric(String value) {
    if (NumberUtils.isCreatable(value)) {
      return value;
    }
    throw new IllegalArgumentException("Invalid numeric input: " + value);
  }
}
