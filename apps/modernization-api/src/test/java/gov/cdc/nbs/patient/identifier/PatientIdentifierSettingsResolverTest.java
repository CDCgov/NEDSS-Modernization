package gov.cdc.nbs.patient.identifier;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentifierSettingsResolverTest {

    @Test
    void should_resolve_identifier_settings_with_initial_value() {

        PatientIdentifierAttributesFinder finder = mock(PatientIdentifierAttributesFinder.class);

        when(finder.find())
            .thenReturn(Optional.of(new PatientIdentifierAttributes("type-value", "suffix-value")));

        PatientIdentifierSettingsResolver resolver =
            new PatientIdentifierSettingsResolver(finder);

        PatientIdentifierSettings actual = resolver.resolve(1307L);

        assertThat(actual.initial()).isEqualTo(1307L);
        assertThat(actual.type()).isEqualTo("type-value");
        assertThat(actual.suffix()).isEqualTo("suffix-value");
    }

    @Test
    void should_error_when_identifier_settings_are_not_found() {

        PatientIdentifierAttributesFinder finder = mock(PatientIdentifierAttributesFinder.class);

        when(finder.find())
            .thenReturn(Optional.empty());

        PatientIdentifierSettingsResolver resolver =
            new PatientIdentifierSettingsResolver(finder);

        assertThatThrownBy(() -> resolver.resolve(1307L))
            .hasMessageContaining("Unable to resolve Patient Identifier Attributes");

    }
}
