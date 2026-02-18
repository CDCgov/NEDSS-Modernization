package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.data.pagination.PaginationRequest;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.search.SearchPageableMapper;
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class InvestigationSearchResultResolver {

  private final SearchResolver<InvestigationFilter, InvestigationSearchResult> resolver;
  private final SearchPageableMapper mapper;

  InvestigationSearchResultResolver(
      final SearchResolver<InvestigationFilter, InvestigationSearchResult> resolver,
      final SearchPageableMapper mapper) {
    this.resolver = resolver;
    this.mapper = mapper;
  }

  @QueryMapping("findInvestigationsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  SearchResult<InvestigationSearchResult> search(
      @Argument InvestigationFilter filter, @Argument("page") PaginationRequest paginated) {
    Pageable pageable = mapper.from(paginated);

    return resolver.search(filter, pageable);
  }
}
