package gov.cdc.nbs.report.utils;

import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.FilterDefaultValue;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;

public class WhereClauseUtils {
  private static final String BAS_TXT = "BAS_TXT";
  private static final String BAS_DAYS = "BAS_DAYS";
  private static final String BAS_CVG_LIST = "BAS_CVG_LIST";
  private static final String BAS_STD_HIV_WRKR = "BAS_STD_HIV_WRKR";
  private static final String BAS_CON_LIST = "BAS_CON_LIST";
  private static final String BAS_JUR_LIST = "BAS_JUR_LIST";
  public static final String BAS_TIM_LIST = "BAS_TIM_LIST";
  private static final String BAS_TIM_RANGE = "BAS_TIM_RANGE";
  private static final String BAS_TIM_RANGE_CUSTOM = "BAS_TIM_RANGE_CUSTOM";
  private static final String BAS_TIM_RANGE_LIST = "BAS_TIM_RANGE_LIST";
  //    public static final String BAS_MM_YYYY_RANGE = "BAS_MM_YYYY_RANGE";

  private static final String ALLOW_NULLS = "ALLOW_NULLS";

  public static final String COL_TYPE_STRING = "STRING";
  public static final String COL_TYPE_DATE = "DATE";
  public static final String COL_TYPE_DATETIME = "DATETIME";
  public static final String COL_TYPE_INTEGER = "INTEGER";
  public static final String COL_TYPE_NUMBER = "NUMBER";

  private static final List<String> BASIC_LIST_FILTER_TYPES =
      List.of(BAS_CON_LIST, BAS_JUR_LIST, BAS_TIM_LIST, BAS_CVG_LIST, BAS_TXT, BAS_STD_HIV_WRKR);

  // TODO: worked on translating directly from original then was going to go back and clean up
  public String buildBasicWhereClause(
      ReportConfiguration reportConfig, ReportExecutionRequest reportExecRequest) {
    StringJoiner whereClause = new StringJoiner(" AND ", "WHERE ", "");
    List<FilterConfiguration> filters = reportConfig.filters();
    if (filters.isEmpty()) {}
    filters.stream()
        .map(
            (filterConfig) -> {
              ReportColumn reportColumn =
                  reportConfig.reportColumns().stream()
                      .filter(rc -> Objects.equals(rc.id(), filterConfig.reportColumnUid()))
                      .findFirst()
                      .orElse(null);

              if (reportColumn == null) {
                // throw error?
                // todo just continue;
              }
              StringBuilder clause = new StringBuilder();

              FilterType filterCode = filterConfig.filterType();
              List<FilterDefaultValue> filterDefaultValues = filterConfig.filterDefaultValues();

              if (BASIC_LIST_FILTER_TYPES.contains(filterCode.type().toUpperCase())) {
                String dsColName = reportColumn.columnName();
                String dsColTitle = reportColumn.columnTitle();
                String dsColType = reportColumn.columnSourceTypeCode();
                String fName = filterCode.name();
                String fCode = filterCode.code();
                Integer maxVal = filterConfig.maxValueCnt();
                Integer minVal = filterConfig.minValueCnt();

                boolean includesNoneValues = includesNoneValueType(filterDefaultValues);
                AtomicBoolean allowNulls = new AtomicBoolean(false);

                if (!Objects.equals(maxVal, minVal)) {
                  if (!includesNoneValues) {
                    clause.append(dsColName).append(" in (");
                  }

                    for (int i = 0; i < filterDefaultValues.size(); i++) {
                        FilterDefaultValue fdv = filterDefaultValues.get(i);

                        if (ALLOW_NULLS.equals(fdv.operator()) && !"none".equalsIgnoreCase(fdv.valueType())) {
                            allowNulls.set(true);
                        }

                        if (includesNoneValues) {
                            clause.append(dsColName).append(" IS NULL ");
                            continue;
                        }

                        clause.append(formatField(dsColType, fdv.valueTxt()));

                        if (i < filterDefaultValues.size() - 1) {
                            clause.append(", ");
                        }
                    }

                  if (!includesNoneValues) {
                    clause.append(")");
                  }
                }

                if (maxVal.equals(minVal)) {
                  clause.append(dsColName).append(" = ");
                  filterDefaultValues.forEach(fdv -> {
                        if (ALLOW_NULLS.equals(fdv.operator())
                            && !"none".equalsIgnoreCase(fdv.valueType())) {
                          allowNulls.set(true);
                        }
                        clause.append(formatField(dsColType, fdv.valueTxt()));
                        if (includesNoneValues) {
                          clause.append(dsColName).append(" IS NULL ");
                          continue;
                        }
                      });
                }

                if (allowNulls.get()) {
                  clause.append(dsColName).append(" IS NULL ");
                }

                clause.append(")");
              }

              return null;
            });
    return "";
  }

  private boolean includesNoneValueType(List<FilterDefaultValue> filterDefaultValues) {
    return filterDefaultValues.stream().anyMatch(fdv -> "none".equalsIgnoreCase(fdv.valueType()));
  }

  private String formatField(String type, String value) {
    return switch (type.toUpperCase()) {
      case COL_TYPE_STRING -> getQuotedValue(value);
      case COL_TYPE_DATE -> convertToSQLDate(value);
      case COL_TYPE_INTEGER, COL_TYPE_NUMBER -> value;
      default -> throw new IllegalArgumentException();
    };
  }

  private static String getQuotedValue(String str) {
    if (str == null) {
      return null;
    }
    return "'" + str.replace("'", "''") + "'";
  }

  private static String convertToSQLDate(String date) {
    if (date == null || date.isBlank()) return null;

    LocalDate localDate;
    try {
      if (date.length() > 7) {
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      } else {
        localDate = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yyyy")).atDay(1);
      }
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("invalid date");
    }
    return localDate.toString();
  }

  private static String buildBasicListWhereClause(
      ReportConfiguration reportConfig, ReportExecutionRequest reportExecRequest) {}
}
