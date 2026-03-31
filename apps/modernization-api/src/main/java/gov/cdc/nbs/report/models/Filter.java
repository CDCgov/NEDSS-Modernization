package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
          @NotNull @AssertTrue boolean isBasic,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long reportFilterUid,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotEmpty List<String> values)
      implements Filter {}

  record AdvancedFilter(
      @Schema(
              requiredMode = Schema.RequiredMode.REQUIRED,
              type = "boolean",
              allowableValues = {"false"})
          @NotNull @AssertFalse boolean isBasic,
      @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Expr logic)
      implements Filter {}

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Expr.Clause.class, name = "Clause"),
    @JsonSubTypes.Type(value = Expr.Connector.class, name = "Connector")
  })
  sealed interface Expr {
    record Clause(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long columnUid,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String filterOperatorCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull @NotBlank String filterValue)
        implements Expr {}

    record Connector(
        @Schema(
                requiredMode = Schema.RequiredMode.REQUIRED,
                allowableValues = {"OR", "AND"})
            @NotNull @Pattern(regexp = "^(OR|AND)$")
            String operator,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Expr left,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Expr right)
        implements Expr {}
  }
}
