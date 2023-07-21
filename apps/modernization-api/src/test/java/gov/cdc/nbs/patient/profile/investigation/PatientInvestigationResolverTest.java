package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.investigation.PatientInvestigationFinder.Criteria;
import gov.cdc.nbs.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientInvestigationResolverTest {

    @Test
    void should_find_paginated_investigations_for_the_provided_patient_using_the_finder() {

        PatientInvestigationFinder finder = mock(PatientInvestigationFinder.class);
        SecurityService securityService = mock(SecurityService.class);

        when(finder.find(any(), any()))
            .thenAnswer(args -> new PageImpl<>(
                    List.of(mock(PatientInvestigation.class)),
                    args.getArgument(1, Pageable.class),
                    1L
                )
            );

        PatientInvestigationResolver resolver = new PatientInvestigationResolver(25, finder, securityService);

        GraphQLPage page = new GraphQLPage(25, 2);


        Page<PatientInvestigation> actual = resolver.find(1861L, null, page);

        assertThat(actual)
            .as("The resolved paginated list of patient investigations comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Criteria> criteriaCaptor =
            ArgumentCaptor.forClass(Criteria.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(criteriaCaptor.capture(), pageableCaptor.capture());

        Criteria actual_criteria = criteriaCaptor.getValue();

        assertThat(actual_criteria.patient()).isEqualTo(1861L);

        assertThat(actual_criteria.status()).contains(
            Criteria.Status.OPEN,
            Criteria.Status.CLOSED
        );

        Pageable actual_pageable = pageableCaptor.getValue();

        assertThat(actual_pageable.getPageNumber()).isEqualTo(2);
        assertThat(actual_pageable.getPageSize()).isEqualTo(25);
    }

    @Test
    void should_find_paginated_open_investigations_for_the_provided_patient_using_the_finder() {

        PatientInvestigationFinder finder = mock(PatientInvestigationFinder.class);
        SecurityService securityService = mock(SecurityService.class);

        when(finder.find(any(), any()))
            .thenAnswer(args -> new PageImpl<>(
                    List.of(mock(PatientInvestigation.class)),
                    args.getArgument(1, Pageable.class),
                    1L
                )
            );

        PatientInvestigationResolver resolver = new PatientInvestigationResolver(25, finder, securityService);

        GraphQLPage page = new GraphQLPage(25, 2);


        Page<PatientInvestigation> actual = resolver.find(1861L, true, page);

        assertThat(actual)
            .as("The resolved paginated list of patient investigations comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Criteria> criteriaCaptor =
            ArgumentCaptor.forClass(Criteria.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(criteriaCaptor.capture(), pageableCaptor.capture());

        Criteria actual_criteria = criteriaCaptor.getValue();

        assertThat(actual_criteria.patient()).isEqualTo(1861L);
        assertThat(actual_criteria.status()).contains(Criteria.Status.OPEN);

        Pageable actual_pageable = pageableCaptor.getValue();

        assertThat(actual_pageable.getPageNumber()).isEqualTo(2);
        assertThat(actual_pageable.getPageSize()).isEqualTo(25);
    }
}
