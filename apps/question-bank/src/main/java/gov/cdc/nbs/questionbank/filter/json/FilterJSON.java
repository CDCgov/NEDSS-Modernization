package gov.cdc.nbs.questionbank.filter.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.questionbank.filter.DateFilter;
import gov.cdc.nbs.questionbank.filter.DateRangeFilter;
import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = FilterJSON.Date.class)
@JsonSubTypes({
  @JsonSubTypes.Type(FilterJSON.Date.class),
  @JsonSubTypes.Type(FilterJSON.DateRange.class),
  @JsonSubTypes.Type(FilterJSON.SingleValue.class),
  @JsonSubTypes.Type(FilterJSON.MultiValue.class)
})
@Schema(title = "Filter")
public sealed interface FilterJSON {

  String property();

  Filter asFilter();

  @Schema(title = "SingleValueFilter")
  record SingleValue(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String property,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ValueFilter.Operator operator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String value)
      implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new SingleValueFilter(property, operator, value);
    }
  }

  @Schema(title = "MultiValueFilter")
  record MultiValue(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String property,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ValueFilter.Operator operator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Collection<String> values)
      implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new MultiValueFilter(property, operator, values);
    }
  }

  @Schema(title = "DateFilter")
  record Date(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String property,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) DateFilter.Operator operator)
      implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new DateFilter(property, operator);
    }
  }

  @Schema(title = "DateRangeFilter")
  record DateRange(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String property,
      LocalDate after,
      LocalDate before)
      implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new DateRangeFilter(property, after, before);
    }
  }
}
