package gov.cdc.nbs.event.search.investigation;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.event.search.InvestigationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
class InvestigationSearcher {
  private static final Permission PERMISSION = new Permission("view", "investigation");
  private final PermissionScopeResolver resolver;
  private final InvestigationSearchCriteriaFilterResolver filterResolver;
  private final InvestigationSearchCriteriaQueryResolver queryResolver;
  private final InvestigationSearchCriteriaSortResolver sortResolver;
  private final ElasticsearchClient client;

  InvestigationSearcher(
      final PermissionScopeResolver resolver,
      final InvestigationSearchCriteriaFilterResolver filterResolver,
      final InvestigationSearchCriteriaQueryResolver queryResolver,
      final InvestigationSearchCriteriaSortResolver sortResolver,
      final ElasticsearchClient client
  ) {
    this.resolver = resolver;
    this.filterResolver = filterResolver;
    this.queryResolver = queryResolver;
    this.sortResolver = sortResolver;
    this.client = client;
  }

  Page<InvestigationSearchResult> find(
      final InvestigationFilter criteria,
      final Pageable pageable
  ) {
    PermissionScope scope = this.resolver.resolve(PERMISSION);

    Query filter = filterResolver.resolve(criteria, scope);
    Query query = queryResolver.resolve(criteria);
    List<SortOptions> sorting = sortResolver.resolve(pageable);

    try {

      SearchResponse<SearchableInvestigation> response = client.search(
          search -> search.index("investigation")
              .postFilter(filter)
              .query(query)
              .sort(sorting)
              .from((int) pageable.getOffset())
              .size(pageable.getPageSize()),
          SearchableInvestigation.class
      );

      HitsMetadata<SearchableInvestigation> hits = response.hits();

      long total = hits.total().value();

      return total > 0
          ? paged(hits, pageable)
          : Page.empty(pageable);

    } catch (RuntimeException | IOException exception) {
      throw new IllegalStateException("An unexpected error occurred when searching for lab reports.", exception);
    }

  }

  private Page<InvestigationSearchResult> paged(
      final HitsMetadata<SearchableInvestigation> hits,
      final Pageable pageable
  ) {

    List<InvestigationSearchResult> results = hits.hits().stream()
        .filter(hit -> hit.source() != null)
        .map(hit -> InvestigationSearchResultConverter.convert(hit.source(), hit.score()))
        .toList();

    return new PageImpl<>(
        results,
        pageable,
        hits.total().value()
    );
  }
}
