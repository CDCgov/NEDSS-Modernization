package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.audit.Status;
import org.junit.jupiter.api.Test;

public class DisplayColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    Throwable exception =
        assertThrows(
            NullPointerException.class,
            () -> {
              new DisplayColumn(null, null, null);
            });

    assertEquals("dataSourceColumn is marked non-null but is null", exception.getMessage());
  }

  @Test
  void should_create_complete_display_column() {
    Long id = 1L;
    DataSourceColumn dataSourceColumn = new DataSourceColumn();
    Long reportUid = 2L;
    DataSource dataSource = new DataSource();
    Integer sequenceNumber = 10;
    Status status = new Status();

    DisplayColumn actual =
        new DisplayColumn(
            id, dataSourceColumn, new ReportId(reportUid, dataSource), sequenceNumber, status);

    assertThat(actual)
        .satisfies(dc -> assertEquals(id, dc.getId()))
        .satisfies(dc -> assertEquals(dataSourceColumn, dc.getDataSourceColumn()))
        .satisfies(dc -> assertEquals(reportUid, dc.getReportId().getReportUid()))
        .satisfies(dc -> assertEquals(sequenceNumber, dc.getSequenceNumber()))
        .satisfies(dc -> assertEquals(status, dc.getStatus()));
  }
}
