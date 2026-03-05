package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.audit.Status;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class DataSourceColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    Throwable exception =
        assertThrows(
            NullPointerException.class,
            () -> {
              new DataSourceColumn(null);
            });

    assertEquals("dataSource is marked non-null but is null", exception.getMessage());
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
    Status status = new Status();

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
            status);

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
        .satisfies(dc -> assertEquals(status, dc.getStatus()));
  }
}
