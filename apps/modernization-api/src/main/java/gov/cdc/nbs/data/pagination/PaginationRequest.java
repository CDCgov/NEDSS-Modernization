package gov.cdc.nbs.data.pagination;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.Sort.Direction;

public record PaginationRequest(Integer pageSize, Integer pageNumber, Sort sort) {

  public record Sort (
      String property,
      @JsonDeserialize(using = FlexibleDirectionJsonDeserializer.class)
      Direction direction
  ){}


}
