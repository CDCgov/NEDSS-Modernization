package gov.cdc.nbs.report;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Filter.BasicFilter.class, name = "BasicFilter"),
  @JsonSubTypes.Type(value = Filter.AdvancedFilter.class, name = "AdvancedFilter")
})
public sealed interface Filter {
  record BasicFilter(
      @Schema(
              type = "boolean",
              allowableValues = {"true"})
          boolean isBasic,
      String filterCode,
      ArrayList<String> values)
      implements Filter {}

  record AdvancedFilter(
      @Schema(
              type = "boolean",
              allowableValues = {"false"})
          boolean isBasic,
      Expr logic)
      implements Filter {}

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
  @JsonSubTypes({
    @JsonSubTypes.Type(value = Expr.Clause.class, name = "Clause"),
    @JsonSubTypes.Type(value = Expr.Connector.class, name = "Connector")
  })
  sealed interface Expr {
    record Clause(String fieldName, String filterOperatorCode, String filterValue)
        implements Expr {}

    record Connector(String operator, Expr left, Expr right) implements Expr {}
  }
}
