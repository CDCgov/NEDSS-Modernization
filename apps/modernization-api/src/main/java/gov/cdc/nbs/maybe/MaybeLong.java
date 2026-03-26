package gov.cdc.nbs.maybe;

import java.util.Optional;

public class MaybeLong {

  public static Optional<Long> from(final String value) {
    if (value == null) {
      return Optional.empty();
    }

    try {
      return Optional.of(Long.valueOf(value));
    } catch (NumberFormatException exception) {
      return Optional.empty();
    }
  }

  private MaybeLong() {}
}
