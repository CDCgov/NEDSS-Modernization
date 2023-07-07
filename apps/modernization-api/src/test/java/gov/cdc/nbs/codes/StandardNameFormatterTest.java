package gov.cdc.nbs.codes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StandardNameFormatterTest {

    @Test
    void should_format_to_only_first_letter_capitalized() {

        String actual = StandardNameFormatter.formatted("Only The First CHARACTER is CapiTalized");

        assertThat(actual).isEqualTo("Only the first character is capitalized");

    }

    @Test
    void should_handle_null_values() {

        String actual = StandardNameFormatter.formatted(null);

        assertThat(actual).isNull();

    }
}
