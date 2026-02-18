package gov.cdc.nbs.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import gov.cdc.nbs.message.enums.Indicator;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IndicatorStringConverterTest {

  @Test
  void should_convert_null_Indicator_as_null_String() {
    String actual = IndicatorStringConverter.toString(null);

    assertThat(actual).isNull();
  }

  @ParameterizedTest
  @MethodSource("indicatorToString")
  void should_convert_known_Indicator_as_String(final Indicator in, final String expected) {

    String actual = IndicatorStringConverter.toString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> indicatorToString() {
    return Arrays.stream(Indicator.values()).map(e -> arguments(e, e.getId()));
  }

  @ParameterizedTest
  @MethodSource("stringToIndicator")
  void should_convert_known_String_values_as_Indicator(final String in, final Indicator expected) {

    Indicator actual = IndicatorStringConverter.fromString(in);

    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> stringToIndicator() {
    return Arrays.stream(Indicator.values()).map(e -> arguments(e.getId(), e));
  }

  @Test
  void should_convert_null_String_as_null_Indicator() {
    Indicator actual = IndicatorStringConverter.fromString(null);

    assertThat(actual).isNull();
  }

  @Test
  void should_convert_unknown_String_as_null_Indicator() {
    Indicator actual = IndicatorStringConverter.fromString("unknown-value");

    assertThat(actual).isNull();
  }
}
