package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.AdvancedQueryBuilder;
import gov.cdc.nbs.report.AdvancedQueryException;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.FilterType;
import java.util.List;

public class AdvancedFilterConfigurationMapper {
  private static final System.Logger LOGGER =
      System.getLogger(AdvancedFilterConfigurationMapper.class.getName());

  private AdvancedFilterConfigurationMapper() {}

  public static AdvancedFilterConfiguration fromReportFilter(
      ReportFilter filter, List<DataSourceColumn> columns) {
    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.type().equals(ReportConstants.ADV_FILTER_TYPE)) {
      throw new IllegalArgumentException(
          "Cannot create advanced filter from non where clause builder filter");
    }

    AdvancedQueryBuilder advQueryBuilder =
        new AdvancedQueryBuilder(filter.getFilterValues(), columns);
    String query = advQueryBuilder.generateQueryString();

    AdvancedQueryException exception = null;
    AdvancedQuery.RuleGroup value = null;
    try {
      value = advQueryBuilder.build();
    } catch (AdvancedQueryException e) {
      exception = e;
      LOGGER.log(System.Logger.Level.WARNING, "Error occurred while building AdvancedQuery", e);
    }

    return new AdvancedFilterConfiguration(filter.getId(), value, query, exception);
  }
}
