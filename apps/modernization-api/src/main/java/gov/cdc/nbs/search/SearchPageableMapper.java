package gov.cdc.nbs.search;

import gov.cdc.nbs.data.pagination.PaginationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SearchPageableMapper {

  private final int maxPageSize;

  public SearchPageableMapper(
      @Value("${nbs.search.max-page-size}") final int maxPageSize
  ) {
    this.maxPageSize = maxPageSize;
  }

  public Pageable from(final PaginationRequest request) {
    return request == null
        ? PageRequest.of(0, maxPageSize)
        : asPageable(request);
  }

  private Pageable asPageable(final PaginationRequest request) {
    if (request.pageSize() != null && request.pageSize() > maxPageSize) {
      throw new IllegalArgumentException("Invalid page size: " + request.pageSize() + ". Max size allowed: " + maxPageSize);
    }

    int size = request.pageSize() == null ? maxPageSize : request.pageSize();
    int page = request.pageNumber() == null ? 0 : request.pageNumber();

    return asSort(request.sort())
        .map(sort -> PageRequest.of(page, size, sort))
        .orElseGet(() -> PageRequest.of(page, size));
  }

  private Optional<Sort> asSort(final PaginationRequest.Sort sorting) {
    if (sorting != null) {
      String field = sorting.property();
      Direction direction = sorting.direction();

      if (field != null && direction != null) {
        return Optional.of(Sort.by(direction, field));
      }
    }

    return Optional.empty();
  }
}
