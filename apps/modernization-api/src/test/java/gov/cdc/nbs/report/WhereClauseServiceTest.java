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

  @Test
  void should_combine_multiple_filters_with_and() {
    Long filter1 = 101L;
    Long col1 = 1L;
    Long filter2 = 102L;
    Long col2 = 2L;

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filter1, col1, false),
                createBasicFilterConfiguration(List.of(), filter2, col2, false)),
            List.of(
                mockReportColumn(col1, "STRING", "ColumnName1"),
                mockReportColumn(col2, "STRING", "ColumnName2")));

    List<BasicFilterRequest> request =
        List.of(
            new BasicFilterRequest(filter1, List.of("A"), false),
            new BasicFilterRequest(filter2, List.of("B"), false));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig, request);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName1] IN (A)) AND ([ColumnName2] IN (B))");
  }

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
  void should_handle_only_nulls_requested() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true));
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    // Request with empty values but includeNulls = true
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(filterUid, List.of(), true));

    String whereClause = mockWhereClauseService.buildBasicWhereClause(reportConfig, request);

    assertThat(whereClause).isEqualTo("WHERE ([ColumnName] IS NULL)");
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

  @Test
  void should_throw_exception_when_filter_config_is_missing() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of());
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(999L, List.of("val"), false));

    assertThatThrownBy(() -> mockWhereClauseService.buildBasicWhereClause(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No basic filter configuration found for UID: 999");
  }
}
