package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class LabReportResolver {
  private final LabReportFinder finder;
  private final Integer maxPageSize;


  public LabReportResolver(
      @Value("${nbs.max-page-size: 50}") final int maxPageSize,
      final LabReportFinder finder
  ) {
    this.finder = finder;
    this.maxPageSize = maxPageSize;
  }

  @QueryMapping("findLabReportsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
  public Page<LabReport> search(@Argument LabReportFilter filter, @Argument GraphQLPage page) {
    return finder.find(filter, GraphQLPage.toPageable(page, maxPageSize));
  }

}
