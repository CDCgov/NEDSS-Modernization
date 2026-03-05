package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DisplayColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    Throwable exception =
        assertThrows(
            NullPointerException.class,
            () -> {
              new DisplayColumn(null, null, null, null);
            });

    assertEquals("dataSourceColumn is marked non-null but is null", exception.getMessage());
  }

  @Test
  void should_create_complete_display_column() {
    Long id = 1L;
    DataSourceColumn dataSourceColumn = new DataSourceColumn();
    Long reportUid = 2L;
    Long dataSource = 3L;
    Integer sequenceNumber = 10;
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    DisplayColumn actual =
        new DisplayColumn(
            id,
            dataSourceColumn,
            new ReportId(reportUid, dataSource),
            sequenceNumber,
            statusCd,
            statusTime);

    assertThat(actual)
        .satisfies(dc -> assertEquals(id, dc.getId()))
        .satisfies(dc -> assertEquals(dataSourceColumn, dc.getDataSourceColumn()))
        .satisfies(dc -> assertEquals(reportUid, dc.getReportId().getReportUid()))
        .satisfies(dc -> assertEquals(sequenceNumber, dc.getSequenceNumber()))
        .satisfies(dc -> assertEquals(statusCd, dc.getStatusCd()))
        .satisfies(dc -> assertEquals(statusTime, dc.getStatusTime()));
  }
}
