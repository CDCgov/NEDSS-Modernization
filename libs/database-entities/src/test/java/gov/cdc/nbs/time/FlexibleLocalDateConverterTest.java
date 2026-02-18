package gov.cdc.nbs.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FlexibleLocalDateConverterTest {

  @Test
  void should_convert_null_LocalDate_to_null_string() {
    String actual = FlexibleLocalDateConverter.toString(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_convert_LocalDate_to_String() {

    String actual = FlexibleLocalDateConverter.toString(LocalDate.of(2025, Month.FEBRUARY, 17));

    assertThat(actual).isEqualTo("2025-02-17");
  }

  @Test
  void should_convert_null_string_to_null_localDate() {
    LocalDate actual = FlexibleLocalDateConverter.fromString(null);

    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("stringToLocalDate")
  void should_convert_string_to_localDate(final String in, final LocalDate expected) {
    LocalDate actual = FlexibleLocalDateConverter.fromString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> stringToLocalDate() {
    return Stream.of(
        arguments("1/1/2022", LocalDate.of(2022, Month.JANUARY, 1)),
        arguments("12/30/22", LocalDate.of(2022, Month.DECEMBER, 30)),
        arguments("12/30/2022", LocalDate.of(2022, Month.DECEMBER, 30)),
        arguments("2022-12-30", LocalDate.of(2022, Month.DECEMBER, 30)));
  }

  @Test
  void should_throw_error_when_converting_from_unknown_format() {
    assertThatThrownBy(() -> FlexibleLocalDateConverter.fromString("unknown"))
        .isInstanceOf(DateTimeParseException.class);
  }
}
