package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.graphql.GraphQLPage;
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

class PatientNamedByContactResolverTest {

    @Test
    void should_find_paginated_contacts_named_by_contact_for_the_provided_patient_using_the_finder() {

        PatientNamedByContactFinder finder = mock(PatientNamedByContactFinder.class);

        when(finder.find(anyLong(), any()))
            .thenAnswer(args -> new PageImpl<>(
                    List.of(mock(PatientContacts.NamedByPatient.class)),
                    args.getArgument(1, Pageable.class),
                    1L
                )
            );

        PatientNamedByContactResolver resolver = new PatientNamedByContactResolver(25, finder);

        GraphQLPage page = new GraphQLPage(17, 2);

        Page<PatientContacts.NamedByContact> actual = resolver.find(1861L, page);

        assertThat(actual)
            .as("The resolved paginated list of patients comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(1861L), captor.capture());

        Pageable actualPageable = captor.getValue();

        assertThat(actualPageable.getPageNumber()).isEqualTo(2);
        assertThat(actualPageable.getPageSize()).isEqualTo(17);
    }

}
