package gov.cdc.nbs.patient.profile.identification;

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

class PatientIdentificationResolverTest {

    @Test
    void should_resolve_identification_for_provided_patient_profile() {
        PatientIdentificationFinder finder = mock(PatientIdentificationFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientIdentification.class))));

        PatientIdentificationResolver resolver = new PatientIdentificationResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = new GraphQLPage(5, 2);

        Page<PatientIdentification> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient identification comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actualPageable = captor.getValue();

        assertThat(actualPageable.getPageNumber()).isEqualTo(2);
        assertThat(actualPageable.getPageSize()).isEqualTo(5);
    }

    @Test
    void should_resolve_identification_for_provided_patient_profile_without_page() {
        PatientIdentificationFinder finder = mock(PatientIdentificationFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientIdentification.class))));

        PatientIdentificationResolver resolver = new PatientIdentificationResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = null;

        Page<PatientIdentification> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient identification comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actualPageable = captor.getValue();

        assertThat(actualPageable.getPageNumber()).isZero();
        assertThat(actualPageable.getPageSize()).isEqualTo(10);
    }

}
