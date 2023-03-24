package gov.cdc.nbs.labreport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import gov.cdc.nbs.exception.QueryException;
import gov.cdc.nbs.graphql.GraphQLPage;

public class LabReportResolverTest {
    @Mock
    private LabReportFinder finder;

    @Captor
    private ArgumentCaptor<LabReportFilter> filterCaptor;

    @Captor
    private ArgumentCaptor<Long> patientIdCaptor;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    private final int maxPageSize = 23;

    private LabReportResolver resolver;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        resolver = new LabReportResolver(maxPageSize, finder);
    }

    @Test
    void should_pass_filter() {
        // mock
        var filter = LabReportTestUtil.createFilter();

        // method call
        resolver.findLabReportsByFilter(filter, new GraphQLPage(maxPageSize));

        // assertions
        verify(finder).find(filterCaptor.capture(), Mockito.any());
        var actualFilter = filterCaptor.getValue();

        assertEquals(filter.getPatientId(), actualFilter.getPatientId());
        assertEquals(filter.getProgramAreas(), actualFilter.getProgramAreas());
        assertEquals(filter.getJurisdictions(), actualFilter.getJurisdictions());
        assertEquals(filter.getPregnancyStatus(), actualFilter.getPregnancyStatus());
        assertEquals(filter.getEventIdType(), actualFilter.getEventIdType());
        assertEquals(filter.getEventId(), actualFilter.getEventId());
        assertEquals(filter.getEventDateSearch(), actualFilter.getEventDateSearch());
        assertEquals(filter.getEntryMethods(), actualFilter.getEntryMethods());
        assertEquals(filter.getEnteredBy(), actualFilter.getEnteredBy());
        assertEquals(filter.getEventStatus(), actualFilter.getEventStatus());
        assertEquals(filter.getProcessingStatus(), actualFilter.getProcessingStatus());
        assertEquals(filter.getCreatedBy(), actualFilter.getCreatedBy());
        assertEquals(filter.getLastUpdatedBy(), actualFilter.getLastUpdatedBy());
        assertEquals(filter.getProviderSearch(), actualFilter.getProviderSearch());
        assertEquals(filter.getResultedTest(), actualFilter.getResultedTest());
        assertEquals(filter.getCodedResult(), actualFilter.getCodedResult());
    }

    @Test
    void should_pass_patientId() {
        Long patientId = 123L;
        resolver.findDocumentsRequiringReviewForPatient(patientId, new GraphQLPage(maxPageSize));
        verify(finder).findUnprocessedDocumentsForPatient(patientIdCaptor.capture(), Mockito.any());
        var actualPatient = patientIdCaptor.getValue();
        assertEquals(patientId, actualPatient);
    }

    @Test
    void should_limit_max_page_size() {
        Long patientId = 123L;
        var requestedPageSize = maxPageSize + 1;
        QueryException ex = null;
        try {
            resolver.findDocumentsRequiringReviewForPatient(patientId, new GraphQLPage(requestedPageSize));
        } catch (QueryException e) {
            ex = e;
        }
        assertNotNull(ex);
    }

    @Test
    void should_pass_correct_page_size() {
        Long patientId = 123L;
        QueryException ex = null;
        try {
            resolver.findDocumentsRequiringReviewForPatient(patientId, new GraphQLPage(maxPageSize));
        } catch (QueryException e) {
            ex = e;
        }
        assertNull(ex);
        verify(finder).findUnprocessedDocumentsForPatient(Mockito.anyLong(), pageableCaptor.capture());
        var actualPageable = pageableCaptor.getValue();
        assertEquals(maxPageSize, actualPageable.getPageSize());
    }
}
