package gov.cdc.nbs.patient.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import gov.cdc.nbs.message.enums.Gender;


public class ElasticsearchGenderConverterTest {
    @Test
    void should_write_null_Gender_as_null_String() {
        ElasticsearchGenderConverter converter = new ElasticsearchGenderConverter();

        Object actual = converter.write(null);
        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @MethodSource("genderToString")
    void should_write_known_Gender_as_string(final Gender in, final String expected) {

        ElasticsearchGenderConverter converter = new ElasticsearchGenderConverter();

        Object actual = converter.write(in);
        assertThat(actual).isEqualTo(expected);

    }

    static Stream<Arguments> genderToString() {
        return Arrays.stream(Gender.values())
                .map(e -> arguments(e, e.name()));
    }

    @Test
    void should_read_null_String_as_null_Gender() {
        ElasticsearchGenderConverter converter = new ElasticsearchGenderConverter();

        Object actual = converter.read(null);
        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @MethodSource("stringToGender")
    void should_read_known_string_values_as_Gender(final String in, final Gender expected) {
        ElasticsearchGenderConverter converter = new ElasticsearchGenderConverter();

        Object actual = converter.read(in);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> stringToGender() {
        return Arrays.stream(Gender.values())
                .map(e -> arguments(e.name(), e));
    }

    @Test
    void should_read_unknown_string_as_null_Gender() {
        ElasticsearchGenderConverter converter = new ElasticsearchGenderConverter();

        Object actual = converter.read("unknown-value");
        assertThat(actual).isNull();
    }

}
