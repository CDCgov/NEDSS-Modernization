package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.GraphQLPageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class LabReportSearchResultResolver {
  private final LabReportFinder finder;
  private final GraphQLPageableMapper mapper;


  LabReportSearchResultResolver(
      final LabReportFinder finder,
      final GraphQLPageableMapper mapper
  ) {
    this.finder = finder;
    this.mapper = mapper;
  }

  @QueryMapping("findLabReportsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  Page<LabReport> search(
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
