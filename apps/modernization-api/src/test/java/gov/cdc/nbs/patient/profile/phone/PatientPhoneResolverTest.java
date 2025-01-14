package gov.cdc.nbs.patient.profile.phone;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientPhoneResolverTest {

    @Test
    void should_resolve_phone_for_provided_patient_profile() {
        PatientPhoneFinder finder = mock(PatientPhoneFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientPhone.class))));

        PatientPhoneResolver resolver = new PatientPhoneResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = new GraphQLPage(5, 2);

        Page<PatientPhone> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient phonees comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actualPageable = captor.getValue();

        assertThat(actualPageable.getPageNumber()).isEqualTo(2);
        assertThat(actualPageable.getPageSize()).isEqualTo(5);
    }

    @Test
    void should_resolve_phone_for_provided_patient_profile_without_page() {
        PatientPhoneFinder finder = mock(PatientPhoneFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientPhone.class))));

        PatientPhoneResolver resolver = new PatientPhoneResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = null;

        Page<PatientPhone> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient phonees comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actualPageable = captor.getValue();

        assertThat(actualPageable.getPageNumber()).isZero();
        assertThat(actualPageable.getPageSize()).isEqualTo(10);
    }
}
