package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientSummaryResolverTest {

    @Test
    void should_resolve_summary_for_provided_patient_profile() {
        PatientSummaryFinder finder = mock(PatientSummaryFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(Optional.of(mock(PatientSummary.class)));

        PatientSummaryResolver resolver = new PatientSummaryResolver(finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);
        when(profile.asOf()).thenReturn(Instant.parse("2023-04-01T00:01:02Z"));

        Optional<PatientSummary> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient summary comes from the finder")
            .isPresent();

        verify(finder).find(2963L, Instant.parse("2023-04-01T00:01:02Z"));

    }

}
