package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.utils.ValueCountCalculator;
import gov.cdc.nbs.report.utils.ValueCountCalculator.ReportValueCounts;
import java.util.List;

public class BasicFilterConfigurationMapper {
  private BasicFilterConfigurationMapper() {}

  public static BasicFilterConfiguration fromReportFilter(ReportFilter filter) {
    Long columnUid = null;

    if (filter.getDataSourceColumn() != null) {
      columnUid = filter.getDataSourceColumn().getId();
    }

    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.type().startsWith(ReportConstants.BASIC_FILTER_PREFIX)) {
      throw new IllegalArgumentException("Cannot create basic filter from advanced filter");
    }

    boolean isRequired = false;
    if (filter.getFilterValidation() != null
        && filter.getFilterValidation().getReportFilterInd() != null) {
      isRequired = "Y".equals(filter.getFilterValidation().getReportFilterInd().toString());
    }

    // For the future: A list of strings may end up being too simple for all use cases,
    // may need to evolve to be a small object with a key and value
    List<String> defaultValues = null;
    boolean defaultIncludeNulls = false;
    if (filter.getFilterValues() != null) {
      defaultValues =
          filter.getFilterValues().stream()
              .filter(v -> !ReportConstants.BASIC_FILTER_ALLOW_NULLS_OP.equals(v.getOperator()))
              .map(FilterValue::getValueTxt)
              .toList();
      defaultIncludeNulls =
          filter.getFilterValues().stream()
              .anyMatch(v -> ReportConstants.BASIC_FILTER_ALLOW_NULLS_OP.equals(v.getOperator()));
    }
    return new BasicFilterConfiguration(
        filter.getId(),
        columnUid,
        defaultValues,
        defaultIncludeNulls,
        ValueCountCalculator.toSelectType(
            new ReportValueCounts(filter.getMinValueCnt(), filter.getMaxValueCnt())),
        isRequired,
        filterType);
  }
}
