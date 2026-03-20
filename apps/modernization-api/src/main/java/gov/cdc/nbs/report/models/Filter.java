package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Filter.BasicFilter.class, name = "BasicFilter"),
  @JsonSubTypes.Type(value = Filter.AdvancedFilter.class, name = "AdvancedFilter")
})
public sealed interface Filter {
  record BasicFilter(
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED,
              type = "boolean",
              allowableValues = {"true"})
          boolean isBasic,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String filterCode,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<String> values)
      implements Filter {}

  record AdvancedFilter(
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED,
              type = "boolean",
              allowableValues = {"false"})
          boolean isBasic,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Expr logic)
      implements Filter {}

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Expr.Clause.class, name = "Clause"),
    @JsonSubTypes.Type(value = Expr.Connector.class, name = "Connector")
  })
  sealed interface Expr {
    record Clause(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String fieldName,
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
}
