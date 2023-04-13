package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientSummaryResolverTest {

    @Test
    void should_resolve_summary_for_provided_patient_profile_as_of_the_given_date() {
        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));
        PatientSummaryFinder finder = mock(PatientSummaryFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(Optional.of(mock(PatientSummary.class)));

        PatientSummaryResolver resolver = new PatientSummaryResolver(clock, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        Optional<PatientSummary> actual = resolver.resolve(profile, Instant.parse("2023-04-01T00:01:02Z"));

        assertThat(actual)
            .as("The resolved patient summary comes from the finder")
            .isPresent();

        verify(finder).find(2963L, Instant.parse("2023-04-01T00:01:02Z"));

    }

    @Test
    void should_resolve_summary_for_provided_patient_profile_as_of_now_when_no_as_of_present() {
        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));
        PatientSummaryFinder finder = mock(PatientSummaryFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(Optional.of(mock(PatientSummary.class)));

        PatientSummaryResolver resolver = new PatientSummaryResolver(clock, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        Optional<PatientSummary> actual = resolver.resolve(profile, null);

        assertThat(actual)
            .as("The resolved patient summary comes from the finder")
            .isPresent();

        verify(finder).find(2963L, Instant.parse("2023-04-04T02:03:04Z"));

    }
}
