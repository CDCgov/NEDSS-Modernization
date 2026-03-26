package gov.cdc.nbs.graphql;

import static org.junit.jupiter.api.Assertions.*;

import gov.cdc.nbs.exception.QueryException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort.Direction;

class GraphQLPageTest {

  @Test
  void should_throw_exception_page_size_too_large() {
    var page = new GraphQLPage(1000);
    assertThrows(QueryException.class, () -> GraphQLPage.toPageable(page, 10));
  }

  @Test
  void should_convert_to_pageable() {
    int maxPageSize = 100;
    var page = new GraphQLPage(maxPageSize, 1);
    var pageable = GraphQLPage.toPageable(page, maxPageSize);
    assertNotNull(pageable);
    assertEquals(maxPageSize, pageable.getPageSize());
    assertEquals(1, pageable.getPageNumber());
  }

  @Test
  void should_convert_null_to_pageable() {
    int maxPageSize = 100;
    var pageable = GraphQLPage.toPageable(null, maxPageSize);
    assertNotNull(pageable);
    assertEquals(maxPageSize, pageable.getPageSize());
    assertEquals(0, pageable.getPageNumber());
  }

  @Test
  void should_convert_sort() {
    int maxPageSize = 100;
    var sortField = "MySortField";
    var page = new GraphQLPage(maxPageSize, 1, Direction.ASC, "MySortField");
    var pageable = GraphQLPage.toPageable(page, maxPageSize);
    var sort = pageable.getSort().get().findFirst().get();
    assertEquals(Direction.ASC, sort.getDirection());
    assertEquals(sortField, sort.getProperty());
  }
}
