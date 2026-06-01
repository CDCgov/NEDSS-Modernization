package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.AdvancedQueryBuilder;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.FilterType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedFilterConfigurationMapper {
  private static final List<String> KNOWN_OPERATORS = Arrays.asList(
          "GE",
          "LE",
          "GT",
          "LT",
          "BW",
          "CO",
          "SW",
          "EQ",
          "NE",
          "IN",
          "NN"
  );

  private AdvancedFilterConfigurationMapper() {
  }

  public static AdvancedFilterConfiguration fromReportFilter(ReportFilter filter) {
    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.type().equals(ReportConstants.ADV_FILTER_TYPE)) {
      throw new IllegalArgumentException(
              "Cannot create advanced filter from non where clause builder filter");
    }

    return new AdvancedFilterConfiguration(filter.getId(), new AdvancedQueryBuilder(filter).build());
  }
}
