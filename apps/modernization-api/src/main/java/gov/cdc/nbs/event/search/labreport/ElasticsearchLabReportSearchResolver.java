package gov.cdc.nbs.event.search.labreport;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import gov.cdc.nbs.search.SearchResultResolver;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
class ElasticsearchLabReportSearchResolver
    implements SearchResolver<LabReportFilter, LabReportSearchResult> {

  private static final Permission PERMISSION = new Permission("View", "ObservationLabReport");
  private final PermissionScopeResolver resolver;
  private final LabReportSearchCriteriaFilterResolver filterResolver;
  private final LabReportSearchCriteriaQueryResolver queryResolver;
  private final LabReportSearchCriteriaSortResolver sortResolver;
  private final ElasticsearchClient client;
  private final SearchResultResolver resultResolver;
  private final LabTestSummaryFinder labTestSummaryFinder;

  ElasticsearchLabReportSearchResolver(
      final PermissionScopeResolver resolver,
      final LabReportSearchCriteriaFilterResolver filterResolver,
      final LabReportSearchCriteriaQueryResolver queryResolver,
      final LabReportSearchCriteriaSortResolver sortResolver,
      final ElasticsearchClient client,
      final SearchResultResolver resultResolver,
      final LabTestSummaryFinder labTestSummaryFinder) {
    this.resolver = resolver;
    this.filterResolver = filterResolver;
    this.queryResolver = queryResolver;
    this.sortResolver = sortResolver;
    this.client = client;
    this.resultResolver = resultResolver;
    this.labTestSummaryFinder = labTestSummaryFinder;
  }

  @Override
  public SearchResult<LabReportSearchResult> search(
      final LabReportFilter criteria, final Pageable pageable) {
    PermissionScope scope = this.resolver.resolve(PERMISSION);

    Query filter = filterResolver.resolve(criteria, scope);
    Query query = queryResolver.resolve(criteria);
    List<SortOptions> sorting = sortResolver.resolve(pageable);

    try {

      SearchResponse<SearchableLabReport> response =
          client.search(
              search ->
                  search
                      .index("lab_report")
                      .postFilter(filter)
                      .query(query)
                      .sort(sorting)
                      .from((int) pageable.getOffset())
                      .size(pageable.getPageSize()),
              SearchableLabReport.class);

      HitsMetadata<SearchableLabReport> hits = response.hits();

      long total = hits.total().value();

      return total > 0 ? paged(hits, pageable) : this.resultResolver.empty(pageable);

    } catch (RuntimeException | IOException exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when searching for lab reports.", exception);
    }
  }

  private SearchResult<LabReportSearchResult> paged(
      final HitsMetadata<SearchableLabReport> hits, final Pageable pageable) {

    List<LabReportSearchResult> results =
        hits.hits().stream()
            .filter(hit -> hit.source() != null)
            .map(
                hit ->
                    LabReportSearchResultConverter.convert(
                        hit.source(), hit.score(), labTestSummaryFinder))
            .toList();

    return resultResolver.resolve(results, pageable, hits.total().value());
  }
}
