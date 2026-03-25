package gov.cdc.nbs.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import gov.cdc.nbs.message.enums.Gender;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GenderStringConverterTest {

  @Test
  void should_convert_null_Gender_as_null_String() {
    String actual = GenderStringConverter.toString(null);

    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("genderToString")
  void should_convert_known_Gender_as_String(final Gender in, final String expected) {

    String actual = GenderStringConverter.toString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> genderToString() {
    return Arrays.stream(Gender.values()).map(e -> arguments(e, e.name()));
  }

  @ParameterizedTest
  @MethodSource("stringToGender")
  void should_convert_known_String_values_as_Gender(final String in, final Gender expected) {

    Gender actual = GenderStringConverter.fromString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> stringToGender() {
    return Arrays.stream(Gender.values()).map(e -> arguments(e.name(), e));
  }

  @Test
  void should_convert_null_String_as_null_Gender() {
    Gender actual = GenderStringConverter.fromString(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_convert_unknown_String_as_null_Gender() {
    Gender actual = GenderStringConverter.fromString("unknown-value");

    assertThat(actual).isNull();
  }
}
