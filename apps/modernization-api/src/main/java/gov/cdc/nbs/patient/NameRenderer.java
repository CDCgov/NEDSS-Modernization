package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Suffix;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameRenderer {

  private static final String NAME_SEPARATOR = " ";

  public static String render(
      String prefix,
      String first,
      String last,
      Suffix suffix
  ) {
    String suffixDisplay = suffix == null ? null : suffix.name();
    return render(
        prefix,
        first,
        last,
        suffixDisplay
    );
  }

  public static String render(
      String prefix,
      String first,
      String last,
      String suffix
  ) {
    return Stream.of(
            prefix,
            first,
            last,
            suffix
        ).filter(Objects::nonNull)
        .collect(Collectors.joining(NAME_SEPARATOR));
  }

  public static String render(
      String first,
      String last
  ) {
    return render(null, first, last, (String) null);
  }

  private NameRenderer() {
  }
}
