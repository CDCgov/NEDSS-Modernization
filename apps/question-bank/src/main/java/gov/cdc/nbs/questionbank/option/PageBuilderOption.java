package gov.cdc.nbs.questionbank.option;

import io.swagger.v3.oas.annotations.media.Schema;

public record PageBuilderOption(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String value,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String label,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) int order) {

  public PageBuilderOption(String value, String name, int order) {
    this(value, name, name, order);
  }
}
