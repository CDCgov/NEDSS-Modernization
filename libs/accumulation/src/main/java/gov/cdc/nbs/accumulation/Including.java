package gov.cdc.nbs.accumulation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Including {

  public static <V> List<V> include(final Collection<V> items, final V item) {
    return Stream.concat(items.stream(), Stream.of(item)).toList();
  }

  private Including() {
    //  static class
  }
}
