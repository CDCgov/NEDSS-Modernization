package gov.cdc.nbs.option.race;

import com.google.common.collect.Ordering;
import gov.cdc.nbs.option.Option;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

class SpecificRaceSorter {

  private static final List<String> ORDERING = List.of(
      "1002-5",
      "2028-9",
      "2054-5",
      "2076-8",
      "2106-3",
      "2131-1",
      "M",
      "PHC1175",
      "NASK",
      "U"
  );

  private static final Comparator<Option> SPECIFIC = Ordering.explicit(ORDERING)
      .onResultOf(Option::value);

  /**
   * Sorts a {@link Collection} of Race options using the specified ordering of
   *
   * <ul>
   *     <li>American Indian or Alaska Native</li>
   *     <li>Asian</li>
   *     <li>Black or African American</li>
   *     <li>Native Hawaiian or Other Pacific Islander</li>
   *     <li>White</li>
   *     <li>Other</li>
   *     <li>Multi-Race</li>
   *     <li>Refused to answer</li>
   *     <li>Not Asked</li>
   *     <li>Unknown</li>
   * </ul>
   *
   * If the {@code options} contain any unknown values the ordering is not changed.
   *
   * @param options The collection of {@code Option} objects to sort.
   * @return The ordered {@code options}.
   */
  static Collection<Option> sorted(final Collection<Option> options) {
    try {
      return options.stream().sorted(SPECIFIC).toList();
    } catch (ClassCastException exception) {
      return options;
    }
  }

  private SpecificRaceSorter() {
  }
}
