package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.data.pagination.PaginationRequest;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.search.SearchPageableMapper;
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class LabReportSearchResultResolver {
  private final SearchPageableMapper mapper;
  private final SearchResolver<LabReportFilter, LabReportSearchResult> resolver;

  LabReportSearchResultResolver(
      final SearchPageableMapper mapper,
      final SearchResolver<LabReportFilter, LabReportSearchResult> resolver) {
    this.mapper = mapper;
    this.resolver = resolver;
  }

  @QueryMapping("findLabReportsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  SearchResult<LabReportSearchResult> search(
      @Argument final LabReportFilter filter, @Argument("page") PaginationRequest paginated) {

    Pageable pageable = mapper.from(paginated);

    return this.resolver.search(filter, pageable);
  }
}
