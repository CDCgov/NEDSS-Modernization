package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReportFilterValidationTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new ReportFilterValidation(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("reportFilter is marked non-null but is null");
  }

  @Test
  void should_create_complete_report_filter_validation() {
    Long id = 1L;
    ReportFilter reportFilter = new ReportFilter();
    Character reportFilterInd = 'Y';
    Character statusCd = 'A';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    ReportFilterValidation actual =
        new ReportFilterValidation(id, reportFilter, reportFilterInd, statusCd, statusTime);

    assertThat(actual)
        .satisfies(rfv -> assertEquals(id, rfv.getId()))
        .satisfies(rfv -> assertEquals(reportFilter, rfv.getReportFilter()))
        .satisfies(rfv -> assertEquals(reportFilterInd, rfv.getReportFilterInd()))
        .satisfies(rfv -> assertEquals(statusCd, rfv.getStatusCd()))
        .satisfies(rfv -> assertEquals(statusTime, rfv.getStatusTime()));
  }
}
