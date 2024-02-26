package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import org.springframework.data.domain.Sort;

public class SearchSorting {

  public static SortOptions asSortOption(final String name, final SortOrder order) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)
        )
    );
  }

  public static SortOptions asSortOption(
      final String path,
      final String name,
      final SortOrder order
  ) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)
                .nested(nested -> nested.path(path))
        )
    );
  }

  public static SortOrder asSortOrder(final Sort.Direction direction) {
    return direction == Sort.Direction.DESC
        ? SortOrder.Desc
        : SortOrder.Asc;
  }

  private SearchSorting() {

  }

}
