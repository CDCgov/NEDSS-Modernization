package gov.cdc.nbs.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GraphQLPageableMapper {

  private final int maxPageSize;

  public GraphQLPageableMapper(@Value("${nbs.max-page-size}") final int maxPageSize) {
    this.maxPageSize = maxPageSize;
  }

  public Pageable from(final GraphQLPage page) {
    return GraphQLPage.toPageable(page, maxPageSize);
  }
}
