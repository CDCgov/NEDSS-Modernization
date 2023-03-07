package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ElasticsearchSuffixConverterTest {

    @Test
    void should_write_null_Suffix_as_null_String() {
        ElasticsearchSuffixConverter converter = new ElasticsearchSuffixConverter();

        Object actual = converter.write(null);

        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @MethodSource("suffixToString")
    void should_write_known_Suffix_as_string(final Suffix in, final String expected) {

        ElasticsearchSuffixConverter converter = new ElasticsearchSuffixConverter();

        Object actual = converter.write(in);

        assertThat(actual).isEqualTo(expected);

    }

    static Stream<Arguments> suffixToString() {
        return Arrays.stream(Suffix.values())
                .map(e -> arguments(e, e.name()));
    }

    @Test
    void should_read_null_String_as_null_Suffix() {
        ElasticsearchSuffixConverter converter = new ElasticsearchSuffixConverter();

        Object actual = converter.read(null);

        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @MethodSource("stringToSuffix")
    void should_read_known_string_values_as_Suffix(final String in, final Suffix expected) {
        ElasticsearchSuffixConverter converter = new ElasticsearchSuffixConverter();

        Object actual = converter.read(in);

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> stringToSuffix() {
        return Arrays.stream(Suffix.values())
                .map(e -> arguments(e.name(), e));
    }

    @Test
    void should_read_unknown_string_as_null_Suffix() {
        ElasticsearchSuffixConverter converter = new ElasticsearchSuffixConverter();

        Object actual = converter.read("unknown-value");

        assertThat(actual).isNull();
    }
}
