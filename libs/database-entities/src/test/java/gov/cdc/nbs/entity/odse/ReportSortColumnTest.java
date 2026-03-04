package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import gov.cdc.nbs.audit.Status;
import org.junit.jupiter.api.Test;

public class ReportSortColumnTest {
  @Test
  void should_create_empty_report_sort_column() {
    ReportSortColumn actual = new ReportSortColumn();

    assertThat(actual)
        .satisfies(col -> assertNull(col.getId()))
        .satisfies(col -> assertNull(col.getOrderCode()))
        .satisfies(col -> assertNull(col.getSequenceNumber()))
        .satisfies(col -> assertNull(col.getReportUid()))
        .satisfies(col -> assertNull(col.getColumnUid()))
        .satisfies(col -> assertNull(col.getStatus()));
  }

  @Test
  void should_create_complete_report_sort_column() {
    Long reportSortColId = 1L;
    String orderCode = "orderCode";
    Integer sequenceNumber = 1;
    DataSource dataSource = new DataSource();
    Long reportUid = 2L;
    ReportId reportId = new ReportId(reportUid, dataSource);
    Long columnId = 3L;
    Status status = new Status();

    ReportSortColumn actual =
        new ReportSortColumn(
            reportSortColId, orderCode, sequenceNumber, reportId, columnId, status);

    assertThat(actual)
        .satisfies(col -> assertEquals(reportSortColId, col.getId()))
        .satisfies(col -> assertEquals(orderCode, col.getOrderCode()))
        .satisfies(col -> assertEquals(sequenceNumber, col.getSequenceNumber()))
        .satisfies(col -> assertEquals(reportUid, col.getReportUid().getReportUid()))
        .satisfies(col -> assertEquals(columnId, col.getColumnUid()))
        .satisfies(col -> assertEquals(status, col.getStatus()));
  }
}
