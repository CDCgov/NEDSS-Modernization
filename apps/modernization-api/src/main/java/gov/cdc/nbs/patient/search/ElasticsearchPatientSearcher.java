package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import gov.cdc.nbs.search.SearchResultResolver;
import graphql.com.google.common.collect.Ordering;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
class ElasticsearchPatientSearcher
    implements SearchResolver<PatientSearchCriteria, PatientSearchResult> {

  public static final String PATIENT_INDEX_NAME = "person";

  private final ElasticsearchClient client;
  private final PatientSearchResultFinder finder;
  private final PatientSearchCriteriaFilterResolver filterResolver;
  private final PatientSearchCriteriaQueryResolver queryResolver;
  private final PatientSearchCriteriaSortResolver sortResolver;
  private final SearchResultResolver resultResolver;

  ElasticsearchPatientSearcher(
      final ElasticsearchClient client,
      final PatientSearchResultFinder finder,
      final PatientSearchCriteriaFilterResolver filterResolver,
      final PatientSearchCriteriaQueryResolver queryResolver,
      final PatientSearchCriteriaSortResolver sortResolver,
      final SearchResultResolver resultResolver) {
    this.client = client;
    this.finder = finder;
    this.filterResolver = filterResolver;
    this.queryResolver = queryResolver;
    this.sortResolver = sortResolver;
    this.resultResolver = resultResolver;
  }

  @Override
  public SearchResult<PatientSearchResult> search(
      final PatientSearchCriteria criteria, final Pageable pageable) {
    try {
      Query filter = filterResolver.resolve(criteria);
      Query query = queryResolver.resolve(criteria);
      List<SortOptions> sorting = sortResolver.resolve(pageable);

      return search(pageable, filter, query, sorting);

    } catch (UnresolvableSearchException exception) {
      return resultResolver.empty(pageable);
    }
  }

  private SearchResult<PatientSearchResult> search(
      final Pageable pageable,
      final Query filter,
      final Query query,
      final List<SortOptions> sorting) {
    try {
      SearchResponse<SearchablePatient> response =
          client.search(
              search ->
                  search
                      .index(PATIENT_INDEX_NAME)
                      //  we don't want to return the documents, just the identifiers
                      .source(source -> source.fetch(false))
                      .postFilter(filter)
                      .query(query)
                      .sort(sorting)
                      .from((int) pageable.getOffset())
                      .size(pageable.getPageSize()),
              SearchablePatient.class);

      HitsMetadata<SearchablePatient> hits = response.hits();

      long total = total(hits);

      return total > 0 ? paged(hits, pageable, total) : resultResolver.empty(pageable);

    } catch (RuntimeException | IOException exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when searching for patients.", exception);
    }
  }

  private SearchResult<PatientSearchResult> paged(
      final HitsMetadata<SearchablePatient> hits, final Pageable pageable, final long total) {
    List<Long> ids =
        hits.hits().stream().map(Hit::id).filter(Objects::nonNull).map(Long::parseLong).toList();

    List<PatientSearchResult> results =
        finder.find(ids).stream()
            .sorted(Ordering.explicit(ids).onResultOf(PatientSearchResult::patient))
            .toList();

    return resultResolver.resolve(results, pageable, total);
  }

  private long total(final HitsMetadata<SearchablePatient> hits) {
    return hits.total() == null ? 0 : hits.total().value();
  }
}
