package gov.cdc.nbs.option;

import io.swagger.v3.oas.annotations.media.Schema;

public record Option(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String value,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String label,
    int order) {

  public Option(String value, String name) {
    this(value, name, name, 1);
  }

  public Option(String value) {
    this(value, value);
  }
}
