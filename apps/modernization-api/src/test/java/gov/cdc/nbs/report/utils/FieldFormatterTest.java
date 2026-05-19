package gov.cdc.nbs.report.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class FieldFormatterTest {

  private final FieldFormatter formatter = new FieldFormatter();

  @Test
  void should_quote_and_escape_strings() {
    // Standard string
    assertThat(formatter.formatField("STRING", "Apple")).isEqualTo("'Apple'");

    // String with single quote (SQL Injection prevention)
    assertThat(formatter.formatField("STRING", "O'Brian")).isEqualTo("'O''Brian'");

    // Multiple quotes
    assertThat(formatter.formatField("STRING", "''")).isEqualTo("''''''");
  }

  @Test
  void should_return_numbers_as_is() {
    assertThat(formatter.formatField("INTEGER", "123")).isEqualTo("123");
    assertThat(formatter.formatField("NUMBER", "45.67")).isEqualTo("45.67");
  }

  @Test
  void should_convert_date_to_sql_format() {
    // MM/dd/yyyy
    assertThat(formatter.formatField("DATE", "12/31/2023")).isEqualTo("'2023-12-31'");
  }

  @Test
  void should_handle_date_ranges_with_month_and_year_defaults() {
    // Test MM/yyyy Range
    List<String> monthRange = List.of("02/2024", "02/2024");
    List<String> monthResult = formatter.convertToSQLFromDateRange(monthRange);

    assertThat(monthResult.get(0)).isEqualTo("'2024-02-01'");
    assertThat(monthResult.get(1)).isEqualTo("'2024-02-29'"); // Leap year check

    // Test yyyy Range
    List<String> yearRange = List.of("2023", "2024");
    List<String> yearResult = formatter.convertToSQLFromDateRange(yearRange);

    assertThat(yearResult.get(0)).isEqualTo("'2023-01-01'");
    assertThat(yearResult.get(1)).isEqualTo("'2024-12-31'");
  }

  @Test
  void should_throw_exception_with_correct_message_for_invalid_date() {
    assertThatThrownBy(() -> formatter.formatField("DATE", "not-a-date"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Can't Convert Date: not-a-date");
  }

  @Test
  void should_throw_exception_for_unknown_column_type() {
    assertThatThrownBy(() -> formatter.formatField("BOOLEAN", "true"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Unexpected Column Type: BOOLEAN");
  }

  @Test
  void should_be_case_insensitive_for_column_type() {
    // Tests the .toUpperCase() logic in the switch expression
    assertThat(formatter.formatField("string", "test")).isEqualTo("'test'");
    assertThat(formatter.formatField("Date", "12/31/2023")).isEqualTo("'2023-12-31'");
  }

  @Test
  void should_throw_exception_for_invalid_range_size() {
    List<String> tooShort = List.of("2023");
    assertThatThrownBy(() -> formatter.convertToSQLFromDateRange(tooShort))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Date range must contain exactly two values.");

    List<String> tooLong = List.of("2023", "2024", "2025");
    assertThatThrownBy(() -> formatter.convertToSQLFromDateRange(tooLong))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Date range must contain exactly two values.");
  }

  @Test
  void should_throw_exception_for_malicious_numeric_input() {
    assertThatThrownBy(() -> formatter.formatField("NUMBER", "1; DROP TABLE Reports"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid numeric input");

    assertThatThrownBy(() -> formatter.formatField("INTEGER", "1 OR 1=1"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid numeric input");
  }
}
