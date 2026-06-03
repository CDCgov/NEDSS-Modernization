package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReportSortColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new ReportSortColumn(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("report is marked non-null but is null");
  }

  @Test
  void should_create_complete_data_source_column() {
    Long id = 1L;
    String code = "ASC";
    Long columnId = 2L;
    Integer seqNum = 1;
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");
    Report report = new Report();

    ReportSortColumn actual =
        new ReportSortColumn(id, code, seqNum, report, columnId, statusCd, statusTime);

    assertThat(actual)
        .satisfies(dc -> assertEquals(id, dc.getId()))
        .satisfies(dc -> assertEquals(code, dc.getReportSortOrderCode()))
        .satisfies(dc -> assertEquals(seqNum, dc.getReportSortSequenceNum()))
        .satisfies(dc -> assertEquals(report, dc.getReport()))
        .satisfies(dc -> assertEquals(columnId, dc.getDataSourceColumnUid()))
        .satisfies(dc -> assertEquals(statusCd, dc.getStatusCd()))
        .satisfies(dc -> assertEquals(statusTime, dc.getStatusTime()));
  }
}
