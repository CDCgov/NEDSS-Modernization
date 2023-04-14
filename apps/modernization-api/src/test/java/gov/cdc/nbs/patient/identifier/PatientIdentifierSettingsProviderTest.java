package gov.cdc.nbs.patient.identifier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PatientIdentifierSettingsProviderTest {

    @Test
    void should_provide_identifier_settings_with_specific_initial_value() {

        PatientIdentifierSettingsResolver resolver = mock(PatientIdentifierSettingsResolver.class);

        when(resolver.resolve(anyLong())).thenAnswer(answer -> new PatientIdentifierSettings(
            "type",
            answer.getArgument(0, Long.class),
            "suffix"
        ));

        PatientIdentifierSettingsProvider provider = new PatientIdentifierSettingsProvider();

        PatientIdentifierSettings actual = provider.patientIdentifierSettings(2099L, resolver);

        assertThat(actual.initial()).isEqualTo(2099L);

        verify(resolver).resolve(2099L);

    }

    @Test
    void should_provide_identifier_settings_with_specific_default_value() {

        PatientIdentifierSettingsResolver resolver = mock(PatientIdentifierSettingsResolver.class);

        when(resolver.resolve(anyLong())).thenAnswer(answer -> new PatientIdentifierSettings(
            "type",
            answer.getArgument(0, Long.class),
            "suffix"
        ));

        PatientIdentifierSettingsProvider provider = new PatientIdentifierSettingsProvider();

        provider.defaultPatientIdentifierSettings(resolver);

        verify(resolver).resolve(10000000L);

    }
}
