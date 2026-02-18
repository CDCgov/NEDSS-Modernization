package gov.cdc.nbs.data.pagination;

import org.springframework.data.domain.Sort;

public class FlexibleDirectionConverter {

  public static Sort.Direction from(final String value) {
    try {
      return Sort.Direction.fromString(value);
    } catch (IllegalArgumentException exception) {
      //  don't fail, just return null
      return null;
    }
  }

  private FlexibleDirectionConverter() {}
}
