package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportColumn;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReportColumnMapperTest {

  @Test
  void fromDataSourceColumn_should_map_all_fields() {
    DataSource dataSource = DataSource.builder().id(100L).statusCd('A').build();
    DataSourceColumn dbColumn =
        DataSourceColumn.builder()
            .id(1L)
            .columnMaxLength(255)
            .columnName("column_name")
            .columnTitle("Column Title")
            .columnSourceTypeCode("VARCHAR")
            .dataSource(dataSource)
            .descTxt("Some description")
            .displayable('Y')
            .filterable('N')
            .statusCd('A')
            .statusTime(LocalDateTime.of(2024, 3, 31, 12, 0))
            .build();

    ReportColumn mapped = ReportColumnMapper.fromDataSourceColumn(dbColumn);

    assertThat(mapped.id()).isEqualTo(dbColumn.getId());
    assertThat(mapped.columnMaxLength()).isEqualTo(dbColumn.getColumnMaxLength());
    assertThat(mapped.columnName()).isEqualTo(dbColumn.getColumnName());
    assertThat(mapped.columnTitle()).isEqualTo(dbColumn.getColumnTitle());
    assertThat(mapped.columnSourceTypeCode()).isEqualTo(dbColumn.getColumnSourceTypeCode());
    assertThat(mapped.descTxt()).isEqualTo(dbColumn.getDescTxt());
    assertThat(mapped.displayable()).isEqualTo(dbColumn.getDisplayable());
    assertThat(mapped.filterable()).isEqualTo(dbColumn.getFilterable());
    assertThat(mapped.statusCd()).isEqualTo(dbColumn.getStatusCd());
    assertThat(mapped.statusTime()).isEqualTo(dbColumn.getStatusTime());
  }
}
