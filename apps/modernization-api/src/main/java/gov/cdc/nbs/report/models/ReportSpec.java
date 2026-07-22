package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ReportSpec(
    @JsonProperty(value = "is_export", required = true) boolean isExport,
    @JsonProperty(value = "is_builtin", required = true) boolean isBuiltin,
    @JsonProperty(value = "report_title", required = true) String reportTitle,
    @JsonProperty(value = "library_name", required = true) String libraryName,
    @JsonProperty(value = "subset_query", required = true) String subsetQuery,
    @JsonProperty(value = "column_map") List<List<String>> columnMap,
    @JsonProperty(value = "sort_by") String sortBy,
    @JsonProperty(value = "days_value") Integer daysValue,
    @JsonProperty(value = "library_params") String libraryParams) {}
