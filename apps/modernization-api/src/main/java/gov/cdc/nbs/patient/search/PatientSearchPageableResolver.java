package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
class PatientSearchPageableResolver {

  private final int maxPageSize;

  PatientSearchPageableResolver(
      @Value("${nbs.search.patient.results.max}") final int maxPageSize
  ) {
    this.maxPageSize = maxPageSize;
  }

  Pageable from(final GraphQLPage page) {
    return GraphQLPage.toPageable(page, maxPageSize);
  }
}
