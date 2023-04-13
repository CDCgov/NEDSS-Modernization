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
    void should_find_patient_profile_by_id() {

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.find(anyLong())).thenReturn(Optional.of(profile));

        PatientProfileResolver resolver = new PatientProfileResolver(finder);

        Optional<PatientProfile> actual = resolver.find(2963L);

        assertThat(actual).contains(profile);

        verify(finder).find(2963L);
    }

}
