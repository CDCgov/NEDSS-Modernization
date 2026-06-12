package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.AdvancedQueryBuilder;
import gov.cdc.nbs.report.AdvancedQueryException;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.FilterType;
import java.util.Arrays;
import java.util.List;

public class AdvancedFilterConfigurationMapper {
  private static final System.Logger LOGGER =
      System.getLogger(AdvancedFilterConfigurationMapper.class.getName());

  private AdvancedFilterConfigurationMapper() {}

  public static AdvancedFilterConfiguration fromReportFilter(ReportFilter filter) {
    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.type().equals(ReportConstants.ADV_FILTER_TYPE)) {
      throw new IllegalArgumentException(
          "Cannot create advanced filter from non where clause builder filter");
    }

    AdvancedQuery.RuleGroup value = null;
    String query = null;
    try {
      value = new AdvancedQueryBuilder(filter.getFilterValues()).build();
    } catch (AdvancedQueryException e) {
      LOGGER.log(System.Logger.Level.WARNING, "Error occurred while building AdvancedQuery", e);

      query = e.generateQueryString();
    }

    return new AdvancedFilterConfiguration(filter.getId(), value, query);
  }
}
