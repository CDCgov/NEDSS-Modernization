package gov.cdc.nbs.patient.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import graphql.com.google.common.collect.Ordering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
class ElasticsearchPatientSearcher implements PatientSearcher {

  private final ElasticsearchClient client;
  private final PatientSearchResultFinder finder;
  private final PatientSearchCriteriaFilterResolver filterResolver;
  private final PatientSearchCriteriaQueryResolver queryResolver;
  private final PatientSearchCriteriaSortResolver sortResolver;

  ElasticsearchPatientSearcher(
      final ElasticsearchClient client,
      final PatientSearchResultFinder finder,
      final PatientSearchCriteriaFilterResolver filterResolver,
      final PatientSearchCriteriaQueryResolver queryResolver,
      final PatientSearchCriteriaSortResolver sortResolver
  ) {
    this.client = client;
    this.finder = finder;
    this.filterResolver = filterResolver;
    this.queryResolver = queryResolver;
    this.sortResolver = sortResolver;
  }

  @Override
  public Page<PatientSearchResult> search(
      final PatientFilter criteria,
      final Pageable pageable
  ) {

    Query filter = filterResolver.resolve(criteria);
    Query query = queryResolver.resolve(criteria);
    List<SortOptions> sorting = sortResolver.resolve(pageable);

    try {
      SearchResponse<SearchablePatient> response = client.search(
          search -> search.index("person")
              .source(source -> source.fetch(false))
              .postFilter(filter)
              .query(query)
              .sort(sorting)
              .from((int) pageable.getOffset())
              .size(pageable.getPageSize()),
          SearchablePatient.class
      );

      HitsMetadata<SearchablePatient> hits = response.hits();

      long total = hits.total().value();

      return total > 0
          ? paged(hits, pageable)
          : Page.empty(pageable);

    } catch (RuntimeException | IOException exception) {
      throw new IllegalStateException("An unexpected error occurred when searching for patients.", exception);
    }
  }

  private Page<PatientSearchResult> paged(
      final HitsMetadata<SearchablePatient> hits,
      final Pageable pageable
  ) {
    List<Long> ids = hits.hits()
        .stream()
        .map(hit -> Long.parseLong(hit.id()))
        .toList();

    List<PatientSearchResult> results = finder.find(ids)
        .stream()
        .sorted(Ordering.explicit(ids).onResultOf(PatientSearchResult::patient))
        .toList();

    return new PageImpl<>(
        results,
        pageable,
        hits.total().value()
    );
  }
}
