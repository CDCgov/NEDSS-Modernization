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

    AdvancedQuery.RuleGroup ruleGroup = null;
    String query = null;
    String exceptionMsg = null;

    if (filter.getFilterValues() != null && !filter.getFilterValues().isEmpty()) {
      AdvancedQueryBuilder advQueryBuilder =
          new AdvancedQueryBuilder(filter.getFilterValues(), columns);
      query = advQueryBuilder.generateQueryString();

      try {
        ruleGroup = advQueryBuilder.build();
      } catch (AdvancedQueryException e) {
        exceptionMsg = e.getMessage();
        LOGGER.log(System.Logger.Level.WARNING, "Unable to parse saved advanced filter query", e);
      }
    }

    return new AdvancedFilterConfiguration(filter.getId(), ruleGroup, query, exceptionMsg);
  }
}
