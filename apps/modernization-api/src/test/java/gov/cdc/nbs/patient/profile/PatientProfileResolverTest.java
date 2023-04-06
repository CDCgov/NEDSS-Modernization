package gov.cdc.nbs.patient.profile;

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

class PatientProfileResolverTest {

    @Test
    void should_find_patient_profile_by_id_as_of_the_given_date() {
        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.find(anyLong(), any())).thenReturn(Optional.of(profile));

        PatientProfileResolver resolver = new PatientProfileResolver(clock, finder);

        Optional<PatientProfile> actual = resolver.find(2963L, Instant.parse("2023-04-01T00:01:02Z"));

        assertThat(actual).contains(profile);

        verify(finder).find(2963L, Instant.parse("2023-04-01T00:01:02Z"));
    }

    @Test
    void should_find_patient_profile_by_id_as_of_now_when_no_as_of_present() {
        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.find(anyLong(), any())).thenReturn(Optional.of(profile));

        PatientProfileResolver resolver = new PatientProfileResolver(clock, finder);

        Optional<PatientProfile> actual = resolver.find(2963L, null);

        assertThat(actual).contains(profile);

        verify(finder).find(2963L, Instant.parse("2023-04-04T02:03:04Z"));
    }
}
