package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class InvestigationResolver {
  private final Integer maxPageSize;
  private final InvestigationFinder finder;

  public InvestigationResolver(
      @Value("${nbs.max-page-size: 50}")
      final Integer maxPageSize,
      final InvestigationFinder finder
  ) {
    this.maxPageSize = maxPageSize;
    this.finder = finder;
  }

  @QueryMapping
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  public Page<Investigation> findInvestigationsByFilter(
      @Argument InvestigationFilter filter,
      @Argument GraphQLPage page
  ) {
    return finder.find(
        filter,
        GraphQLPage.toPageable(page, maxPageSize)
    );
  }

}
