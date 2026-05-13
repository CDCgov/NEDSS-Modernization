package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.FilterType;

public class AdvancedFilterConfigurationMapper {

  private AdvancedFilterConfigurationMapper() {}

  public static AdvancedFilterConfiguration fromReportFilter(ReportFilter filter) {
    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.filterType().equals(ReportConstants.ADV_FILTER_TYPE)) {
      throw new IllegalArgumentException(
          "Cannot create advanced filter from non where clause builder filter");
    }

    // For the future: Populate this
    // https://cdc-nbs.atlassian.net/browse/APP-505
    AdvancedQuery.RuleGroup defaultValue = null;

    return new AdvancedFilterConfiguration(filter.getId(), defaultValue);
  }
}
