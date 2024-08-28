package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.search.SearchGraphQLPageableMapper;
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
  private final SearchGraphQLPageableMapper mapper;

  InvestigationSearchResultResolver(
      final SearchResolver<InvestigationFilter, InvestigationSearchResult> resolver,
      final SearchGraphQLPageableMapper mapper
  ) {
    this.resolver = resolver;
    this.mapper = mapper;
  }

  @QueryMapping("findInvestigationsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  SearchResult<InvestigationSearchResult> search(
      @Argument InvestigationFilter filter,
      @Argument GraphQLPage page
  ) {
    Pageable pageable = mapper.from(page);

    return resolver.search(
        filter,
        pageable
    );
  }

}
