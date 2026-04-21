package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.FilterDefaultValue;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WhereClauseServiceTest {

  @Mock private FieldFormatter fieldFormatter;

  private WhereClauseService mockWhereClauseService;

  @BeforeEach
  void setUp() {
    mockWhereClauseService = new WhereClauseService(fieldFormatter);
    // Default behavior for formatter: return value as is
    Mockito.lenient()
        .when(fieldFormatter.formatField(anyString(), anyString()))
        .thenAnswer(ff -> ff.getArgument(1));
  }

  private ReportConfiguration createConfig(
      List<FilterConfiguration> filters, List<ReportColumn> columns) {
    return new ReportConfiguration(
        "python",
        Mockito.mock(ReportDataSource.class),
        Mockito.mock(Library.class),
        "Test Report",
        filters,
        columns);
  }

  private FilterConfiguration mockFilterConfiguration(
      List<FilterDefaultValue> filterDefaultValues,
      Long reportColumnUid,
      Integer maxValueCnt,
      Integer minValueCnt) {
    FilterConfiguration filterConfig = Mockito.mock(FilterConfiguration.class);

    Mockito.lenient().when(filterConfig.filterDefaultValues()).thenReturn(filterDefaultValues);
    Mockito.lenient().when(filterConfig.reportColumnUid()).thenReturn(reportColumnUid);
    Mockito.lenient().when(filterConfig.maxValueCnt()).thenReturn(maxValueCnt);
    Mockito.lenient().when(filterConfig.minValueCnt()).thenReturn(minValueCnt);

    return filterConfig;
  }

  private ReportColumn mockReportColumn(Long id, String columnSourceTypeCode, String columnName) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(id);
    Mockito.lenient().when(reportColumn.columnSourceTypeCode()).thenReturn(columnSourceTypeCode);
    Mockito.lenient().when(reportColumn.columnName()).thenReturn(columnName);

    return reportColumn;
  }

  @Test
  void should_handle_single_value_equality() {

    Long columnUid = 2L;

    FilterDefaultValue filterDefaultValue =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(List.of(filterDefaultValue), columnUid, 1, 1);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] = condition1)");
  }

  @Test
  void should_handle_multiple_value_equality_with_single_max_min() {

    Long columnUid = 2L;

    FilterDefaultValue filterDefaultValue1 =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");
    FilterDefaultValue filterDefaultValue2 =
        new FilterDefaultValue(2L, 1, "CLAUSE", 10L, "EQ", "condition2");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(List.of(filterDefaultValue1, filterDefaultValue2), columnUid, 1, 1);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] = condition1)");
  }

  @Test
  void should_handle_multiple_value_equality_with_multi_max_min() {

    Long columnUid = 2L;

    FilterDefaultValue filterDefaultValue1 =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");
    FilterDefaultValue filterDefaultValue2 =
        new FilterDefaultValue(2L, 1, "CLAUSE", 10L, "EQ", "condition2");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(
            List.of(filterDefaultValue1, filterDefaultValue2), columnUid, 1, -1);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] IN (condition1, condition2))");
  }

  @Test
  void should_handle_none_default_value_type() {

    Long columnUid = 2L;

    FilterDefaultValue filterDefaultValue =
        new FilterDefaultValue(1L, 1, "none", 10L, "EQ", "condition1");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(List.of(filterDefaultValue), columnUid, 1, 1);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] IS NULL)");
  }

  @Test
  void should_combine_multiple_filters_with_and_plus_single_min_max_plus_multi_min_max() {

    Long columnUid = 1L;
    Long columnUid2 = 2L;

    FilterDefaultValue filterDefaultValue1 =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");
    FilterDefaultValue filterDefaultValue2 =
        new FilterDefaultValue(2L, 1, "CLAUSE", 10L, "EQ", "condition2");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(List.of(filterDefaultValue1), columnUid, 1, 1);
    FilterConfiguration filterConfig2 =
        mockFilterConfiguration(List.of(filterDefaultValue2), columnUid2, 1, -1);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName1");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "STRING", "ColumnName2");

    ReportConfiguration reportConfig =
        createConfig(List.of(filterConfig, filterConfig2), List.of(reportColumn, reportColumn2));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause)
        .isEqualTo("WHERE ([ColumnName1] = condition1) AND ([ColumnName2] IN (condition2))");
  }

  @Test
  void should_handle_actual_value_plus_none_type() {
    Long columnUid = 2L;
    FilterDefaultValue filterDefaultValue1 =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");
    FilterDefaultValue filterDefaultValueNone =
        new FilterDefaultValue(1L, 1, "none", 10L, "EQ", "condition1");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(
            List.of(filterDefaultValue1, filterDefaultValueNone), columnUid, 1, -1);
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    // Verifies the OR logic and that 'none' is not inside the IN clause
    assertThat(whereClause)
        .isEqualTo("WHERE ([ColumnName] IN (condition1) OR [ColumnName] IS NULL)");
  }

  @Test
  void should_handle_allow_nulls_operator() {
    Long columnUid = 2L;
    FilterDefaultValue filterDefaultValue1 =
        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "ALLOW_NULLS", "condition1");

    FilterConfiguration filterConfig =
        mockFilterConfiguration(List.of(filterDefaultValue1), columnUid, 1, 1);
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of(reportColumn));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] = condition1 OR [ColumnName] IS NULL)");
  }

  @Test
  void should_skip_filter_when_column_metadata_is_missing() {
    FilterConfiguration filterConfig = mockFilterConfiguration(List.of(), 999L, 1, 1);
    // Column list is empty or doesn't contain 999L
    ReportConfiguration reportConfig = createConfig(List.of(filterConfig), List.of());

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);

    assertThat(whereClause).isEmpty();
  }
}
