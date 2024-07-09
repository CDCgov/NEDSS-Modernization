package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.search.LabReportFilter;
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
class LabReportSearchResultResolver {
  private final LabReportSearcher finder;
  private final SearchGraphQLPageableMapper mapper;


  LabReportSearchResultResolver(
      final LabReportSearcher finder,
      final SearchGraphQLPageableMapper mapper
  ) {
    this.finder = finder;
    this.mapper = mapper;
  }

  @QueryMapping("findLabReportsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  Page<LabReportSearchResult> search(
      @Argument final LabReportFilter filter,
      @Argument final GraphQLPage page
  ) {

    Pageable pageable = mapper.from(page);

    return finder.find(
        filter,
        pageable
    );
  }

}
