package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameRendererTest {

    @Test
    void should_render_name() {
        String prefix = "prefix";
        String first = "first";
        String last = "last";
        Suffix suffix = Suffix.IV;

        String actual = NameRenderer.render(prefix, first, last, suffix);

        assertThat(actual).isEqualTo("prefix first last IV");
    }

    @Test
    void should_render_name_without_prefix() {
        String prefix = null;
        String first = "first";
        String last = "last";
        Suffix suffix = Suffix.IV;

        String actual = NameRenderer.render(prefix, first, last, suffix);

        assertThat(actual).isEqualTo("first last IV");
    }

    @Test
    void should_render_name_without_suffix() {
        String prefix = "prefix";
        String first = "first";
        String last = "last";
        Suffix suffix = null;

        String actual = NameRenderer.render(prefix, first, last, suffix);

        assertThat(actual).isEqualTo("prefix first last");
    }
}
