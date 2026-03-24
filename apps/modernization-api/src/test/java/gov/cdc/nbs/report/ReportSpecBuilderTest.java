package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSpecBuilderTest {

  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @InjectMocks private ReportSpecBuilder specBuilder;

  private DataSourceColumn mockColumn(String columnName, String columnTitle) {
    DataSourceColumn column = Mockito.mock(DataSourceColumn.class);
    when(column.getColumnName()).thenReturn(columnName);
    when(column.getColumnTitle()).thenReturn(columnTitle);

    return column;
  }

  @Test
  void should_build_hardcoded_report_spec() {
    DataSourceColumn column1 = mockColumn("column1", "Column 1");
    DataSourceColumn column2 = mockColumn("column2", "Column 2");
    List<Long> columnUids = List.of(1L, 2L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column1, column2));

    ReportSpec reportSpec = specBuilder.setColumns(columnUids).build();

    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT column1 AS Column 1, column2 AS Column 2 FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void setColumns_should_throw_illegal_argument_when_column_uids_empty() {
    List<Long> emptyUids = List.of();

    assertThatThrownBy(() -> specBuilder.setColumns(emptyUids))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No column UIDs specified");
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

  @Test
  void build_should_generate_correct_select_clause_for_single_column() {
    DataSourceColumn column = mockColumn("column1", "Column 1");
    List<Long> columnUids = List.of(1L);
    when(dataSourceColumnRepository.findAllById(columnUids)).thenReturn(List.of(column));

    ReportSpec reportSpec = specBuilder.setColumns(columnUids).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT single_column AS Single Column FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_multiple_columns() {
    DataSourceColumn column1 = mockColumn("col1", "Col 1");
    DataSourceColumn column2 = mockColumn("col2", "Col 2");
    DataSourceColumn column3 = mockColumn("col3", "Col 3");

    List<Long> columnUids = List.of(1L, 2L, 3L);
    when(dataSourceColumnRepository.findAllById(columnUids))
        .thenReturn(List.of(column1, column2, column3));

    ReportSpec reportSpec = specBuilder.setColumns(columnUids).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT col1 AS Col 1, col2 AS Col 2, col3 AS Col 3 FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }
}
