package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ReportIdTest {
  @Test
  void should_create_empty_report_id() {
    ReportId actual = new ReportId();

    assertThat(actual)
        .satisfies(ri -> assertNull(ri.getReportUid()))
        .satisfies(ri -> assertNull(ri.getDataSourceUid()));
  }

  @Test
  void should_create_complete_report_id() {
    Long id = 1L;
    Long dataSource = 2L;

    ReportId actual = new ReportId(id, dataSource);

    assertThat(actual)
        .satisfies(ri -> assertEquals(id, ri.getReportUid()))
        .satisfies(ds -> assertEquals(dataSource, ds.getDataSourceUid()));
  }
}
