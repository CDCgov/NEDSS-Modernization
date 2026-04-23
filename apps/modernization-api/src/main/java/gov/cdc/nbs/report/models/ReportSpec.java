package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReportSpec(
    @JsonProperty(value = "is_export", required = true) @NotNull boolean isExport,
    @JsonProperty(value = "is_builtin", required = true) @NotNull boolean isBuiltin,
    @JsonProperty(value = "report_title", required = true) @NotNull @NotBlank String reportTitle,
    @JsonProperty(value = "library_name", required = true) @NotNull @NotBlank String libraryName,
    @JsonProperty(value = "data_source_name", required = true) @NotNull @NotBlank String dataSourceName,
    @JsonProperty(value = "subset_query", required = true) @NotNull @NotBlank String subsetQuery) {}
