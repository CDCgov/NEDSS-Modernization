package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.data.pagination.PaginationRequest;
import gov.cdc.nbs.event.search.LabReportFilter;
<<<<<<< HEAD
import gov.cdc.nbs.search.SearchPageableMapper;
=======
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.search.SearchGraphQLPageableMapper;
>>>>>>> 741e49d44 (Cnft2 2763 data elements table setup (#1754))
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class LabReportSearchResultResolver {
<<<<<<< HEAD
  private final SearchPageableMapper mapper;
  private final SearchResolver<LabReportFilter, LabReportSearchResult> resolver;

  LabReportSearchResultResolver(
      final SearchPageableMapper mapper,
=======
  private final SearchGraphQLPageableMapper mapper;
  private final SearchResolver<LabReportFilter, LabReportSearchResult> resolver;

  LabReportSearchResultResolver(
      final SearchGraphQLPageableMapper mapper,
>>>>>>> 741e49d44 (Cnft2 2763 data elements table setup (#1754))
      final SearchResolver<LabReportFilter, LabReportSearchResult> resolver
  ) {
    this.mapper = mapper;
    this.resolver = resolver;
  }

  @QueryMapping("findLabReportsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  SearchResult<LabReportSearchResult> search(
      @Argument final LabReportFilter filter,
      @Argument("page") PaginationRequest paginated
  ) {

    Pageable pageable = mapper.from(paginated);

    return this.resolver.search(
        filter,
        pageable
    );
  }

}
