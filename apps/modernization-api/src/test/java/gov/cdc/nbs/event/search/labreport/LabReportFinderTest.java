package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

  @Test
  void should_perform_search() {
    var filter = LabReportTestUtil.createFilter();
    var pageable = Pageable.ofSize(100);
    mockMethods();

    var response = finder.find(filter, pageable);

    verify(queryBuilder).buildLabReportQuery(any(PermissionScope.class), filterCaptor.capture(),
        pageableCaptor.capture());
    verify(operations, times(1)).search(any(NativeSearchQuery.class), eq(LabReport.class));
    var actualFilter = filterCaptor.getValue();
    var actualPageable = pageableCaptor.getValue();
    assertEquals(filter, actualFilter);
    assertEquals(pageable, actualPageable);
    assertEquals(labReportSearchHits().getTotalHits(), response.getNumberOfElements());
  }

  private void mockMethods() {
    when(queryBuilder.buildLabReportQuery(any(), any(), any()))
        .thenReturn(new NativeSearchQuery(QueryBuilders.boolQuery()));
    when(operations.search(any(NativeSearchQuery.class), eq(LabReport.class)))
        .thenReturn(labReportSearchHits());
  }

  private SearchHits<LabReport> labReportSearchHits() {

    var list = new ArrayList<SearchHit<LabReport>>();
    list.add(new SearchHit<>(
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
    list.add(new SearchHit<>(
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
