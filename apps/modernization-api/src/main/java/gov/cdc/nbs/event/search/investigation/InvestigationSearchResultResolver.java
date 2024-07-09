package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.GraphQLPageableMapper;
import gov.cdc.nbs.patient.search.SearchGraphQLPageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class InvestigationSearchResultResolver {

  private final InvestigationSearcher searcher;
  private final SearchGraphQLPageableMapper mapper;

  InvestigationSearchResultResolver(
      final InvestigationSearcher searcher,
      final SearchGraphQLPageableMapper mapper
  ) {
    this.searcher = searcher;
    this.mapper = mapper;
  }

  @QueryMapping("findInvestigationsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  Page<InvestigationSearchResult> search(
      @Argument InvestigationFilter filter,
      @Argument GraphQLPage page
  ) {
    Pageable pageable = mapper.from(page);

    return searcher.find(
        filter,
        pageable
    );
  }

}
