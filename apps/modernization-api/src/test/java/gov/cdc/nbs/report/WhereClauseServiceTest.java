package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
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

  private ReportConfiguration createReportConfig(
      List<BasicFilterConfiguration> basicFilterConfigurations, List<ReportColumn> columns) {
    return new ReportConfiguration(
        "python",
        Mockito.mock(ReportDataSource.class),
        Mockito.mock(Library.class),
        "Test Report",
        basicFilterConfigurations,
        null,
        columns);
  }

  private BasicFilterConfiguration createBasicFilterConfiguration(
      List<String> filterDefaultValues,
      Long reportFilterUid,
      Long reportColumnUid,
      Boolean defaultIncludeNulls) {
    return new BasicFilterConfiguration(
        reportFilterUid,
        reportColumnUid,
        filterDefaultValues,
        null,
        null,
        null,
        Mockito.mock(FilterType.class),
        defaultIncludeNulls);
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

    Long filterUid = 100L;
    Long columnUid = 2L;

    String filterDefaultValue = "condition1";

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                List.of(filterDefaultValue), filterUid, columnUid, null));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(filterDefaultValue), false));

    String whereClause =
        mockWhereClauseService.buildBasicWhereClause(reportConfig, basicFilterRequest);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] IN (condition1))");
  }

  //
  //  @Test
  //  void should_handle_none_default_value_type() {
  //
  //    Long columnUid = 2L;
  //
  //    FilterDefaultValue filterDefaultValue =
  //        new FilterDefaultValue(1L, 1, "none", 10L, "EQ", "condition1");
  //
  //    FilterConfiguration filterConfig =
  //        mockBasicFilterConfiguration(List.of(filterDefaultValue), columnUid, 1, 1);
  //
  //    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
  //
  //    ReportConfiguration reportConfig =
  //        createReportConfig(List.of(filterConfig), List.of(reportColumn));
  //
  //    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);
  //
  //    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] IS NULL)");
  //  }

  //  @Test
  //  void should_handle_actual_value_plus_none_type() {
  //    Long columnUid = 2L;
  //    FilterDefaultValue filterDefaultValue1 =
  //        new FilterDefaultValue(1L, 1, "CLAUSE", 10L, "EQ", "condition1");
  //    FilterDefaultValue filterDefaultValueNone =
  //        new FilterDefaultValue(1L, 1, "none", 10L, "EQ", "condition1");
  //
  //    FilterConfiguration filterConfig =
  //        mockBasicFilterConfiguration(
  //            List.of(filterDefaultValue1, filterDefaultValueNone), columnUid, 1, -1);
  //    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
  //    ReportConfiguration reportConfig =
  //        createReportConfig(List.of(filterConfig), List.of(reportColumn));
  //
  //    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig);
  //
  //    // Verifies the OR logic and that 'none' is not inside the IN clause
  //    assertThat(whereClause)
  //        .isEqualTo("WHERE ([ColumnName] IN (condition1) OR [ColumnName] IS NULL)");
  //  }

  @Test
  void should_handle_allow_nulls_operator() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    String filterDefaultValue = "condition1";

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                List.of(filterDefaultValue), filterUid, columnUid, true));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(filterDefaultValue), true));

    String whereClause =
        mockWhereClauseService.buildBasicWhereClause(reportConfig, basicFilterRequest);

    assertThat(whereClause)
        .isEqualTo("WHERE ([ColumnName] IN (condition1) OR [ColumnName] IS NULL)");
  }

  @Test
  void should_throw_exception_when_column_metadata_is_missing() {

    Long filterUid = 100L;
    Long columnUid = 2L;

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true));
    // Column list is empty or doesn't contain 2L
    ReportConfiguration reportConfig = createReportConfig(basicFilterConfigs, List.of());

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(), true));

    assertThatThrownBy(
            () -> mockWhereClauseService.buildBasicWhereClause(reportConfig, basicFilterRequest))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid: 2");
  }
}
