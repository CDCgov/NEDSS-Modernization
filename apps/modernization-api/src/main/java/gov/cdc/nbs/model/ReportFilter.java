package gov.cdc.nbs.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ReportFilter.BasicFilter.class, name = "BasicFilter"),
  @JsonSubTypes.Type(value = ReportFilter.AdvancedFilter.class, name = "AdvancedFilter")
})
public sealed interface ReportFilter {
  record BasicFilter(
      @Schema(
              type = "boolean",
              allowableValues = {"true"})
          boolean isBasic,
      String filterCode,
      ArrayList<String> values)
      implements ReportFilter {}

  record AdvancedFilter(
      @Schema(
              type = "boolean",
              allowableValues = {"false"})
          boolean isBasic,
      Expr logic)
      implements ReportFilter {}

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
