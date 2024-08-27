package gov.cdc.nbs.data.pagination;

import org.springframework.data.domain.Sort.Direction;

public record PaginationRequest(Integer pageSize, Integer pageNumber, Sort sort) {

  public record Sort (String property, Direction direction){}


}
