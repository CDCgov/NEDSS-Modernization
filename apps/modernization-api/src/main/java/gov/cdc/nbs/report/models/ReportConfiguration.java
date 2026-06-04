package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * Configuration for a report, including data source, filters, columns,
 * and an extensible options container.
 */
public record ReportConfiguration(
    ReportDataSource dataSource,
    Library library,
    String title,
    List<BasicFilterConfiguration> basicFilters,
    AdvancedFilterConfiguration advancedFilter,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) List<ReportColumn> columns,
    List<Long> defaultColumnUids,
    SortSpec defaultSort) {

    /**
     * @return the report parameters as a map (JSON object) from the underlying library,
     *         or {@code null} if the library has no parameters.
     */
    @JsonIgnore
    public Map<String, Object> getReportParams() {
        return library != null ? library.reportParams() : null;
    }
}