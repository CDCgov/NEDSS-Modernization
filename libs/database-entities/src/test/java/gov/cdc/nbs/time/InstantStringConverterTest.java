package gov.cdc.nbs.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InstantStringConverterTest {

  @Test
  void should_convert_null_instant_to_null_string() {
    String actual = InstantStringConverter.toString(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_convert_Instant_to_String() {

    String actual = InstantStringConverter.toString(Instant.parse("1955-11-12T22:04:00Z"));

    assertThat(actual).isEqualTo("1955-11-12 22:04:00.000");

  }

  @Test
  void should_convert_null_string_to_null_instant() {
    Instant actual = InstantStringConverter.fromString(null);

    assertThat(actual).isNull();
  }


  @ParameterizedTest
  @MethodSource("stringToInstant")
  void should_convert_string_to_instant(final String in, final Instant expected) {
    Instant actual = InstantStringConverter.fromString(in);

    assertThat(actual).isEqualTo(expected);
  }


  static Stream<Arguments> stringToInstant() {
    return Stream.of(
        arguments("1/1/2022", Instant.parse("2022-01-01T00:00:00Z")),
        arguments("12/30/22", Instant.parse("2022-12-30T00:00:00Z")),
        arguments("12/30/2022", Instant.parse("2022-12-30T00:00:00Z")),
        arguments("12/30/2022", Instant.parse("2022-12-30T00:00:00Z")),
        arguments("2022-12-30", Instant.parse("2022-12-30T00:00:00Z")),
        arguments("2022-11-18 22:27:13.302", Instant.parse("2022-11-18T22:27:13.302Z")),
        arguments("2022-11-18 22:27:13.83", Instant.parse("2022-11-18T22:27:13.83Z")),
        arguments("2022-11-18 22:27:13.8", Instant.parse("2022-11-18T22:27:13.8Z")),
        arguments("2022-11-18T22:27:13.317", Instant.parse("2022-11-18T22:27:13.317Z")),
        arguments("2022-11-18T22:27:13", Instant.parse("2022-11-18T22:27:13Z"))

    );
  }

  @Test
  void should_throw_error_when_converting_from_unknown_format() {
    assertThatThrownBy(() -> InstantStringConverter.fromString("unknown"))
        .isInstanceOf(DateTimeParseException.class);
  }
}
