package gov.cdc.nbs.testing.interaction.http.page;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.data.domain.Pageable;

public class PageableJsonNodeMapper {

  public static JsonNode asJsonNode(final Pageable pageable) {
    return JsonNodeFactory.instance
        .objectNode()
        .put("pageNumber", pageable.getPageNumber())
        .put("pageSize", pageable.getPageSize());
  }

  private PageableJsonNodeMapper() {}
}
