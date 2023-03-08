package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SuffixStringConverterTest {

  @Test
  void should_convert_null_Suffix_as_null_String() {
    String actual = SuffixStringConverter.toString(null);

    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("suffixToString")
  void should_convert_known_Suffix_as_String(final Suffix in, final String expected) {

    String actual = SuffixStringConverter.toString(in);

    assertThat(actual).isEqualTo(expected);

  }

  static Stream<Arguments> suffixToString() {
    return Arrays.stream(Suffix.values())
        .map(e -> arguments(e, e.name()));
  }


  @ParameterizedTest
  @MethodSource("stringToSuffix")
  void should_convert_known_String_values_as_Suffix(final String in, final Suffix expected) {

    Suffix actual = SuffixStringConverter.fromString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> stringToSuffix() {
    return Arrays.stream(Suffix.values())
        .map(e -> arguments(e.name(), e));
  }

  @Test
  void should_convert_null_String_as_null_Suffix() {
    Suffix actual = SuffixStringConverter.fromString(null);

    assertThat(actual).isNull();
  }
  
  @Test
  void should_convert_unknown_String_as_null_Suffix() {
    Suffix actual = SuffixStringConverter.fromString("unknown-value");

    assertThat(actual).isNull();
  }

}
