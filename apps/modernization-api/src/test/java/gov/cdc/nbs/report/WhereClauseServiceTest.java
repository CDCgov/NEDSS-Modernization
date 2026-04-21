package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    when(fieldFormatter.formatField(anyString(), anyString())).thenAnswer(ff -> ff.getArgument(1));
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

    assertThat(whereClause).isEqualTo("WHERE (ColumnName = condition1)");
  }

  @Test
  void should_handle_multiple_value_in_clause() {

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

    assertThat(whereClause).isEqualTo("WHERE (ColumnName = condition1)");
  }
}
