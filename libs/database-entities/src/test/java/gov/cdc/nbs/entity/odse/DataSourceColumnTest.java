package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DataSourceColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new DataSourceColumn(null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("dataSource is marked non-null but is null");
  }

  @Test
  void should_create_complete_data_source_column() {
    Long id = 1L;
    Integer maxLength = 50;
    String columnName = "public_health_case_uid";
    String columnTitle = "Investigation ID";
    String columnSourceTypeCode = "INTEGER";
    DataSource dataSource = new DataSource();
    String descTxt = "Investigation ID";
    Character displayable = 'Y';
    LocalDate effectiveFromTime = LocalDate.parse("2020-03-03");
    LocalDate effectiveToTime = LocalDate.parse("2020-03-04");
    Character filterable = 'Y';
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    DataSourceColumn actual =
        new DataSourceColumn(
            id,
            maxLength,
            columnName,
            columnTitle,
            columnSourceTypeCode,
            dataSource,
            descTxt,
            displayable,
            effectiveFromTime,
            effectiveToTime,
            filterable,
            statusCd,
            statusTime);

    assertThat(actual)
        .satisfies(dc -> assertEquals(id, dc.getId()))
        .satisfies(dc -> assertEquals(maxLength, dc.getColumnMaxLength()))
        .satisfies(dc -> assertEquals(columnName, dc.getColumnName()))
        .satisfies(dc -> assertEquals(columnTitle, dc.getColumnTitle()))
        .satisfies(dc -> assertEquals(columnSourceTypeCode, dc.getColumnSourceTypeCode()))
        .satisfies(dc -> assertEquals(dataSource, dc.getDataSource()))
        .satisfies(dc -> assertEquals(descTxt, dc.getDescTxt()))
        .satisfies(dc -> assertEquals(displayable, dc.getDisplayable()))
        .satisfies(dc -> assertEquals(effectiveFromTime, dc.getEffectiveFromTime()))
        .satisfies(dc -> assertEquals(effectiveToTime, dc.getEffectiveToTime()))
        .satisfies(dc -> assertEquals(filterable, dc.getFilterable()))
        .satisfies(dc -> assertEquals(statusCd, dc.getStatusCd()))
        .satisfies(dc -> assertEquals(statusTime, dc.getStatusTime()));
  }
}
