package gov.cdc.nbs.event.search.labreport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.GraphQLPage;

class LabReportResolverTest {
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
        assertEquals(filter.getEventId(), actualFilter.getEventId());
        assertEquals(filter.getEventDate(), actualFilter.getEventDate());
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

}
