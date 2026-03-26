package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.utils.DataSourceNameUtils;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSpecBuilderTest {

  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @Mock private DataSourceNameUtils dataSourceNameUtils;
  @InjectMocks private ReportSpecBuilder specBuilder;

  final String dataSourceName = "nbs_ods.PHCDemographic";
  final String standardizedDataSourceName = "NBS_ODSE.dbo.PHCDemographic";

  private DataSourceColumn mockColumn(String columnName, String columnTitle) {
    DataSourceColumn column = Mockito.mock(DataSourceColumn.class);
    Mockito.lenient().when(column.getColumnName()).thenReturn(columnName);
    Mockito.lenient().when(column.getColumnTitle()).thenReturn(columnTitle);

    return column;
  }

  @Test
  void should_build_hardcoded_report_spec() {
    String libraryName = "nbs_sr_05";
    DataSourceColumn column1 = mockColumn("column1", "Column 1");
    DataSourceColumn column2 = mockColumn("column2", "Column 2");
    List<Long> columnUids = List.of(1L, 2L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column1, column2));
    when(dataSourceNameUtils.buildDataSourceName(dataSourceName))
        .thenReturn(standardizedDataSourceName);

    ReportSpec reportSpec =
        specBuilder
            .setDataSourceName(dataSourceName)
            .setColumns(columnUids)
            .setLibraryName(libraryName)
            .build();

    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_sr_05");
    assertThat(reportSpec.dataSourceName()).isEqualTo(standardizedDataSourceName);
    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            String.format(
                "SELECT [column1] AS \"Column 1\", [column2] AS \"Column 2\" FROM %s",
                standardizedDataSourceName));
  }

  @Test
  void setColumns_should_throw_illegal_argument_when_columns_not_found() {
    List<Long> columnUids = List.of(1L, 2L);
    DataSourceColumn column1 = mockColumn("column1", "Column 1");
    when(dataSourceColumnRepository.findAllById(columnUids))
        .thenReturn(List.of(column1)); // Only one found

    assertThatThrownBy(() -> specBuilder.setColumns(columnUids))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("One or more of the columns provided is invalid");
  }

  @Test
  void setColumns_should_set_columns_when_all_found() {
    DataSourceColumn column1 = mockColumn("column1", "Column 1");
    DataSourceColumn column2 = mockColumn("column2", "Column 2");
    List<Long> columnUids = List.of(1L, 2L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column1, column2));

    ReportSpecBuilder result = specBuilder.setColumns(columnUids);

    assertThat(result).isEqualTo(specBuilder);
    assertThat(result.getColumns()).containsExactly(column1, column2);
  }

  @ParameterizedTest
  @MethodSource("columnProvider")
  void build_should_generate_correct_select_clause(Map<String, String> columns) {
    DataSourceColumn column = mockColumn(columns.get("name"), columns.get("title"));
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));
    when(dataSourceNameUtils.buildDataSourceName(dataSourceName))
        .thenReturn(standardizedDataSourceName);

    ReportSpec reportSpec =
        specBuilder.setDataSourceName(dataSourceName).setColumns(columnUids).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            String.format(
                "SELECT [%s] AS \"%s\" FROM %s",
                columns.get("name"), columns.get("title"), standardizedDataSourceName));
  }

  private static Stream<Map<String, String>> columnProvider() {
    Map<String, String> col1 = new HashMap<>();
    col1.put("name", "column1");
    col1.put("title", "Column 1");

    Map<String, String> col2 = new HashMap<>();
    col1.put("name", "first column");
    col1.put("title", "Column 1");

    Map<String, String> col3 = new HashMap<>();
    col1.put("name", "user");
    col1.put("title", "User Column");

    return Stream.of(col1, col2, col3);
  }

  @Test
  void build_should_generate_correct_select_clause_for_multiple_columns() {
    DataSourceColumn column1 = mockColumn("col1", "Col 1");
    DataSourceColumn column2 = mockColumn("col2", "Col 2");
    DataSourceColumn column3 = mockColumn("col3", "Col 3");

    List<Long> columnUids = List.of(1L, 2L, 3L);
    when(dataSourceColumnRepository.findAllById(columnUids))
        .thenReturn(List.of(column1, column2, column3));
    when(dataSourceNameUtils.buildDataSourceName(dataSourceName))
        .thenReturn(standardizedDataSourceName);

    ReportSpec reportSpec =
        specBuilder.setDataSourceName(dataSourceName).setColumns(columnUids).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            String.format(
                "SELECT [col1] AS \"Col 1\", [col2] AS \"Col 2\", [col3] AS \"Col 3\" FROM %s",
                standardizedDataSourceName));
  }

  @Test
  void build_should_generate_correct_select_clause_for_no_columns() {
    when(dataSourceNameUtils.buildDataSourceName(dataSourceName))
        .thenReturn(standardizedDataSourceName);
    ReportSpec reportSpec1 = specBuilder.setDataSourceName(dataSourceName).build();

    assertThat(reportSpec1.subsetQuery())
        .isEqualTo(String.format("SELECT * FROM %s", standardizedDataSourceName));
  }

  @Test
  void build_should_generate_correct_select_clause_for_no_empty_column_list() {
    when(dataSourceNameUtils.buildDataSourceName(dataSourceName))
        .thenReturn(standardizedDataSourceName);
    ReportSpec reportSpec1 =
        specBuilder.setDataSourceName(dataSourceName).setColumns(new ArrayList<>()).build();

    assertThat(reportSpec1.subsetQuery())
        .isEqualTo(String.format("SELECT * FROM %s", standardizedDataSourceName));
  }

  @Test
  void setVersion_should_update_report_spec_version() {
    DataSourceColumn column = mockColumn("col1", "Col 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    ReportSpec reportSpec = specBuilder.setVersion(2).setColumns(columnUids).build();

    assertThat(reportSpec.version()).isEqualTo(2);
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.isBuiltin()).isTrue();
  }

  @Test
  void setIsExport_should_update_report_spec_isExport() {
    DataSourceColumn column = mockColumn("col1", "Col 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    ReportSpec reportSpec = specBuilder.setIsExport(false).setColumns(columnUids).build();

    assertThat(reportSpec.isExport()).isFalse();
  }

  @Test
  void setIsBuiltin_should_update_report_spec_isBuiltin() {
    DataSourceColumn column = mockColumn("col1", "Col 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    ReportSpec reportSpec = specBuilder.setIsBuiltin(false).setColumns(columnUids).build();

    assertThat(reportSpec.isBuiltin()).isFalse();
  }

  @Test
  void setReportTitle_should_update_report_spec_reportTitle() {
    DataSourceColumn column = mockColumn("col1", "Col 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    ReportSpec reportSpec =
        specBuilder.setReportTitle("Custom Report").setColumns(columnUids).build();

    assertThat(reportSpec.reportTitle()).isEqualTo("Custom Report");
  }

  @Test
  void setLibraryName_should_throw_illegal_argument_when_empty_libraryname() {
    assertThatThrownBy(() -> specBuilder.setLibraryName(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Report library name cannot be empty");
  }

  @Test
  void setDataSourceName_should_throw_illegal_argument_when_empty_data_source_name() {
    assertThatThrownBy(() -> specBuilder.setDataSourceName(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Data source name cannot be empty");
  }

  @Test
  void setTimeRange_should_update_report_spec_timeRange() {
    DataSourceColumn column = mockColumn("col1", "Col 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    Map<String, LocalDate> timeRange =
        Map.of("start", LocalDate.of(2024, 1, 1), "end", LocalDate.of(2024, 12, 31));

    ReportSpec reportSpec = specBuilder.setTimeRange(timeRange).setColumns(columnUids).build();

    assertThat(reportSpec.timeRange()).isEqualTo(timeRange);
  }
}
