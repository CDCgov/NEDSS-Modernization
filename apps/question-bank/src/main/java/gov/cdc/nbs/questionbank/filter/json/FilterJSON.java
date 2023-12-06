package gov.cdc.nbs.questionbank.filter.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.questionbank.filter.DateFilter;
import gov.cdc.nbs.questionbank.filter.DateRangeFilter;
import gov.cdc.nbs.questionbank.filter.Filter;
import gov.cdc.nbs.questionbank.filter.MultiValueFilter;
import gov.cdc.nbs.questionbank.filter.SingleValueFilter;
import gov.cdc.nbs.questionbank.filter.ValueFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = FilterJSON.Date.class)
@JsonSubTypes({
    @JsonSubTypes.Type(FilterJSON.Date.class),
    @JsonSubTypes.Type(FilterJSON.DateRange.class),
    @JsonSubTypes.Type(FilterJSON.SingleValue.class),
    @JsonSubTypes.Type(FilterJSON.MultiValue.class)
})
@ApiModel(value = "Filter")
public sealed interface FilterJSON {

  String property();

  Filter asFilter();

  @ApiModel(value = "SingleValueFilter")
  record SingleValue(
      @ApiModelProperty(required = true)
      String property,
      @ApiModelProperty(required = true)
      ValueFilter.Operator operator,
      @ApiModelProperty(required = true)
      String value
  ) implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new SingleValueFilter(property, operator, value);
    }

  }


  @ApiModel(value = "MultiValueFilter")
  record MultiValue(
      @ApiModelProperty(required = true)
      String property,
      @ApiModelProperty(required = true)
      ValueFilter.Operator operator,
      @ApiModelProperty(required = true)
      Collection<String> values
  ) implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new MultiValueFilter(property, operator, values);
    }

  }


  @ApiModel(value = "DateFilter")
  record Date(
      @ApiModelProperty(required = true)
      String property,
      @ApiModelProperty(required = true)
      DateFilter.Operator operator
  ) implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new DateFilter(property, operator);
    }

  }


  @ApiModel(value = "DateRangeFilter")
  record DateRange(
      @ApiModelProperty(required = true)
      String property,
      LocalDate after,
      LocalDate before
  ) implements FilterJSON {

    @Override
    public Filter asFilter() {
      return new DateRangeFilter(property, after, before);
    }

  }
}
