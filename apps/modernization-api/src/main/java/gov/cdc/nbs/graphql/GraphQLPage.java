package gov.cdc.nbs.graphql;

import gov.cdc.nbs.exception.QueryException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GraphQLPage {
  private Integer pageSize;
  private Integer pageNumber;
  private Direction sortDirection;
  private String sortField;

  public GraphQLPage(int pageSize, int pageNumber) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
  }

  public GraphQLPage(int pageSize) {
    this.pageSize = pageSize;
    this.pageNumber = 0;
  }

  public int getOffset() {
    return this.getPageNumber() * this.getPageSize();
  }

  public static Pageable toPageable(GraphQLPage page, int maxPageSize) {
    if (page == null) {
      return PageRequest.of(0, maxPageSize);
    }
    if (page.pageSize != null && page.pageSize > maxPageSize) {
      throw new QueryException(
          "Invalid page size: " + page.pageSize + ". Max size allowed: " + maxPageSize);
    }
    if (page.pageSize == null) {
      page.pageSize = maxPageSize;
    }
    if (page.pageNumber == null) {
      page.pageNumber = 0;
    }
    if (page.sortDirection != null && page.sortField != null) {
      return PageRequest.of(
          page.pageNumber, page.pageSize, Sort.by(page.sortDirection, page.sortField));
    } else {
      return PageRequest.of(page.pageNumber, page.pageSize);
    }
  }
}
