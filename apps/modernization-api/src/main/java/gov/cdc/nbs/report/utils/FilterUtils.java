package gov.cdc.nbs.report.utils;

import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.report.ReportConstants;

public class FilterUtils {
  private FilterUtils() {}

  public static boolean isAdvancedFilter(ReportFilter filter) {
    return filter.getFilterCode().getFilterType().equals(ReportConstants.ADV_FILTER_TYPE);
  }

  public static boolean isBasicFilter(ReportFilter filter) {
    return filter.getFilterCode().getFilterType().startsWith(ReportConstants.BASIC_FILTER_PREFIX);
  }
}
