package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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
     * @return the report days value (30, 60, 90) or {@code null} if not set.
     */
    @JsonIgnore
    public Integer getReportDays() {
        return options != null ? options.getReportDays() : null;
    }
}