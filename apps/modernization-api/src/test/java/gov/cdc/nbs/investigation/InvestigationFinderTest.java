package gov.cdc.nbs.investigation;

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
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.InvestigationFilter.InvestigationStatus;

class InvestigationFinderTest {
    @Mock
    private ElasticsearchOperations operations;

    @Mock
    private InvestigationQueryBuilder queryBuilder;

    @Captor
    private ArgumentCaptor<InvestigationFilter> filterCaptor;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @InjectMocks
    private InvestigationFinder finder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_perform_search() {
        var filter = InvestigationTestUtil.createFilter();
        var pageable = Pageable.ofSize(100);
        mockMethods();

        var response = finder.find(filter, pageable);

        verify(queryBuilder).buildInvestigationQuery(filterCaptor.capture(), pageableCaptor.capture());
        verify(operations, times(1)).search(Mockito.any(NativeSearchQuery.class), eq(Investigation.class));
        var actualFilter = filterCaptor.getValue();
        var actualPageable = pageableCaptor.getValue();
        assertEquals(filter, actualFilter);
        assertEquals(pageable, actualPageable);
        assertEquals(investgiationSearchHits().getTotalHits(), response.getNumberOfElements());
    }

    @Test
    void should_create_open_investigation_filter() {
        Long patientId = 123L;
        mockMethods();
        finder.find(patientId, null);

        verify(queryBuilder).buildInvestigationQuery(filterCaptor.capture(), pageableCaptor.capture());
        verify(operations, times(1)).search(Mockito.any(NativeSearchQuery.class), eq(Investigation.class));
        var actualFilter = filterCaptor.getValue();
        assertEquals(patientId, actualFilter.getPatientId());
        assertEquals(InvestigationStatus.OPEN, actualFilter.getInvestigationStatus());
    }

    private void mockMethods() {
                when(queryBuilder.buildInvestigationQuery(Mockito.any(), Mockito.any()))
                        .thenReturn(new NativeSearchQuery(QueryBuilders.boolQuery()));
        when(operations.search(Mockito.any(NativeSearchQuery.class), eq(Investigation.class)))
                .thenReturn(investgiationSearchHits());
    }

    private SearchHits<Investigation> investgiationSearchHits() {

        var list = new ArrayList<SearchHit<Investigation>>();
        list.add(new SearchHit<Investigation>(
                null,
                null,
                null,
                1.0f,
                null, null,
                null,
                null,
                null,
                null,
                Investigation.builder().id("1").build()));
        list.add(new SearchHit<Investigation>(
                null,
                null,
                null,
                1.0f,
                null, null,
                null,
                null,
                null,
                null,
                Investigation.builder().id("2").build()));
        return new SearchHitsImpl<>(2, null, 0, null, list, null, null);
    }
}
