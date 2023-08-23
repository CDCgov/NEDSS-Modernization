package gov.cdc.nbs.event.labreport;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.LabReportFilter;
import gov.cdc.nbs.event.LabReportFilter.ProcessingStatus;

class LabReportFinderTest {

    @Mock
    private ElasticsearchOperations operations;

    @Mock
    private LabReportQueryBuilder queryBuilder;

    @Captor
    private ArgumentCaptor<LabReportFilter> filterCaptor;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @InjectMocks
    private LabReportFinder finder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_perform_search() {
        var filter = LabReportTestUtil.createFilter();
        var pageable = Pageable.ofSize(100);
        mockMethods();

        var response = finder.find(filter, pageable);

        verify(queryBuilder).buildLabReportQuery(filterCaptor.capture(), pageableCaptor.capture());
        verify(operations, times(1)).search(Mockito.any(NativeSearchQuery.class), eq(LabReport.class));
        var actualFilter = filterCaptor.getValue();
        var actualPageable = pageableCaptor.getValue();
        assertEquals(filter, actualFilter);
        assertEquals(pageable, actualPageable);
        assertEquals(labReportSearchHits().getTotalHits(), response.getNumberOfElements());
    }

    @Test
    void should_create_unprocessed_document_filter() {
        Long patientId = 123L;
        mockMethods();
        finder.findUnprocessedDocumentsForPatient(patientId, null);

        verify(queryBuilder).buildLabReportQuery(filterCaptor.capture(), pageableCaptor.capture());
        verify(operations, times(1)).search(Mockito.any(NativeSearchQuery.class), eq(LabReport.class));
        var actualFilter = filterCaptor.getValue();
        assertEquals(patientId, actualFilter.getPatientId());
        assertTrue(actualFilter.getProcessingStatus().contains(ProcessingStatus.UNPROCESSED));
        assertEquals(1, actualFilter.getProcessingStatus().size());
    }

    private void mockMethods() {
                when(queryBuilder.buildLabReportQuery(Mockito.any(), Mockito.any()))
                        .thenReturn(new NativeSearchQuery(QueryBuilders.boolQuery()));
        when(operations.search(Mockito.any(NativeSearchQuery.class), eq(LabReport.class)))
                .thenReturn(labReportSearchHits());
    }

    private SearchHits<LabReport> labReportSearchHits() {

        var list = new ArrayList<SearchHit<LabReport>>();
        list.add(new SearchHit<LabReport>(
                null,
                null,
                null,
                1.0f,
                null,
                null,
                null,
                null,
                null,
                null,
                LabReport.builder().id("1").build()));
        list.add(new SearchHit<LabReport>(
                null,
                null,
                null,
                1.0f,
                null,
                null,
                null,
                null,
                null,
                null,
                LabReport.builder().id("2").build()));
        return new SearchHitsImpl<>(2, null, 0, null, list, null, null);
    }
}
