package gov.cdc.nbs.data.pagination;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginatedRequestJSONMapper {

  private final ObjectMapper mapper;

  public PaginatedRequestJSONMapper(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public JsonNode map(final Pageable paging, final SortCriteria sorting) {
    JsonNode sort =
        mapper
            .createObjectNode()
            .put("property", sorting.field())
            .put("direction", sorting.direction().name());

    return mapper
        .createObjectNode()
        .put("pageNumber", paging.getPageNumber())
        .put("pageSize", paging.getPageSize())
        .set("sort", sort);
  }
}
