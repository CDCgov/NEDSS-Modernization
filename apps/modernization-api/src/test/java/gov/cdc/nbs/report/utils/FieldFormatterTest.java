package gov.cdc.nbs.report.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    String dateWithDay = formatter.formatField("DATE", "12/31/2023");

    assertThat(dateWithDay).isEqualTo("'2023-12-31'");

    String dateWithoutDay = formatter.formatField("DATE", "12/2023");

    assertThat(dateWithoutDay).isEqualTo("'2023-12-01'");
  }

  @Test
  void should_throw_exception_with_correct_message_for_invalid_date() {
    assertThatThrownBy(() -> formatter.formatField("DATE", "not-a-date"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Can't Convert Date!");
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
}
