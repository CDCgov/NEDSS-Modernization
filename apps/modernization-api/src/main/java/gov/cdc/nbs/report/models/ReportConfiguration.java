package gov.cdc.nbs.report.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
=======
import gov.cdc.nbs.report.ReportConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
>>>>>>> 4f092526b0eabfdf79eb39b782f1ba74ace55f1a

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

/**
 * Configuration for a report, including data source, filters, columns,
 * and an extensible options container.
 */
public record ReportConfiguration(
<<<<<<< HEAD
    ReportDataSource dataSource,
    Library library,
    String title,
    List<BasicFilterConfiguration> basicFilters,
=======
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportDataSource dataSource,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Library library,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String title,
    String description,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long ownerUid,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ReportConstants.ReportGroup group,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String sectionCd,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<BasicFilterConfiguration> basicFilters,
>>>>>>> 4f092526b0eabfdf79eb39b782f1ba74ace55f1a
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