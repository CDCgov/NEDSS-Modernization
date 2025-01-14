package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SearchGraphQLPageableMapper {

  private final int maxPageSize;

  public SearchGraphQLPageableMapper(
      @Value("${nbs.search.max-page-size}") final int maxPageSize
  ) {
    this.maxPageSize = maxPageSize;
  }

  public Pageable from(final GraphQLPage page) {
    return GraphQLPage.toPageable(page, maxPageSize);
  }

}
