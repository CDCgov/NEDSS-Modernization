package gov.cdc.nbs.questionbank.option;

import io.swagger.annotations.ApiModelProperty;

public record PageBuilderOption(
    @ApiModelProperty(required = true)
    String value,
    @ApiModelProperty(required = true)
    String name,
    @ApiModelProperty(required = true)
    String label,
    @ApiModelProperty(required = true)
    int order
) {

  public PageBuilderOption(String value, String name, int order) {
    this(value, name, name, order);
  }

}
