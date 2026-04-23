package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = Expr.Clause.class, name = "Clause"),
  @JsonSubTypes.Type(value = Expr.Connector.class, name = "Connector")
})
public sealed interface Expr {
  record Clause(
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long columnUid,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String filterOperatorCode,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String filterValue)
      implements Expr {}

  record Connector(
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED,
              allowableValues = {"OR", "AND"})
          String operator,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Expr left,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Expr right)
      implements Expr {}
}
