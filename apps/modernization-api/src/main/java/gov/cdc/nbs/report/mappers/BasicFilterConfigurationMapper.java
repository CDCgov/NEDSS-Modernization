package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.FilterType;
import java.util.List;

public class BasicFilterConfigurationMapper {
  private BasicFilterConfigurationMapper() {}

  public static BasicFilterConfiguration fromReportFilter(ReportFilter filter) {
    Long columnUid = null;

    if (filter.getDataSourceColumn() != null) {
      columnUid = filter.getDataSourceColumn().getId();
    }

    FilterType filterType = FilterTypeMapper.fromFilterCode(filter.getFilterCode());

    if (!filterType.filterType().startsWith("BAS_")) {
      throw new IllegalArgumentException("Cannot create basic filter from advanced filter");
    }

    Boolean isRequired = false;
    if (filter.getFilterValidation() != null
        && filter.getFilterValidation().getReportFilterInd() != null) {
      isRequired = "Y".equals(filter.getFilterValidation().getReportFilterInd().toString());
    }

    // For the future: A list of strings may end up being too simple for all use cases,
    // may need to evolve to be a small object with a key and value
    List<String> defaultValue = null;
    if (filter.getFilterValues() != null) {
      defaultValue = filter.getFilterValues().stream().map(r -> r.getValueTxt()).toList();
    }
    return new BasicFilterConfiguration(
        filter.getId(),
        columnUid,
        defaultValue,
        filter.getMinValueCnt(),
        filter.getMaxValueCnt(),
        isRequired,
        filterType);
  }
}
