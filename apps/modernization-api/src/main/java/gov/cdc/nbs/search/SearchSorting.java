package gov.cdc.nbs.search;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public class SearchSorting {

  public static SortOptions asSortOption(final String name, final SortOrder order) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)));
  }

  private static String resolveMissing(SortOrder order) {
    return order.name().equals("Asc") ? "_first" : "_last";
  }

  public static SortOptions asHandlingNullSortOption(final String name, final SortOrder order) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)
                .missing(resolveMissing(order))));
  }

  public static SortOptions asSortOption(
      final String path,
      final String name,
      final SortOrder order) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)
                .nested(nested -> nested.path(path))
                .missing(resolveMissing(order))));
  }

  public static SortOptions asFilteredSortOption(
      final String path,
      final String name,
      final SortOrder order,
      final Function<Query.Builder, ObjectBuilder<Query>> filter) {
    return SortOptions.of(
        sort -> sort.field(
            field -> field.field(name)
                .order(order)
                .nested(nested -> nested.path(path)
                    .filter(filter))));
  }

  public static SortOrder asSortOrder(final Sort.Direction direction) {
    return direction == Sort.Direction.DESC
        ? SortOrder.Desc
        : SortOrder.Asc;
  }

  private SearchSorting() {

  }

}
