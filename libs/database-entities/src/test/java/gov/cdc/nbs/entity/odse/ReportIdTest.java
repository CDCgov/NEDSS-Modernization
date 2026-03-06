package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  void reportIds_do_not_match() {
    Long id = 1L;
    Long dataSource = 2L;
    ReportId reportId = new ReportId(id, dataSource);

    Long dataSourceOther = 3L;
    ReportId notEqaualsReportId = new ReportId(id, dataSourceOther);

    // Explicitly testing the overridden .equals() method
    assertFalse(reportId.equals(notEqaualsReportId)); // NOSONAR
  }

  @Test
  void reportIds_do_match() {
    Long id = 1L;
    Long dataSource = 2L;
    ReportId reportId = new ReportId(id, dataSource);

    Long idOther = 1L;
    Long dataSourceOther = 2L;
    ReportId eqaualsReportId = new ReportId(idOther, dataSourceOther);

    // Explicitly testing the overridden .equals() method
    assertTrue(reportId.equals(eqaualsReportId)); // NOSONAR
  }

  @Test
  void reportId_does_not_match_non_ReportId_type() {
    Long id = 1L;
    Long dataSource = 2L;
    ReportId reportId = new ReportId(id, dataSource);

    // Explicitly testing the overridden .equals() method
    assertFalse(reportId.equals("text")); // NOSONAR
  }

  @Test
  void reportIds_should_match_hash_codes() {
    ReportId reportId = new ReportId(1L, 2L);
    ReportId identicalReportId = new ReportId(1L, 2L);

    assertThat(reportId).hasSameHashCodeAs(identicalReportId);
  }
}
