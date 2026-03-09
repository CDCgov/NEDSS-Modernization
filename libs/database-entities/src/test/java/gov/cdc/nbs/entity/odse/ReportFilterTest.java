package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReportFilterTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new ReportFilter(null, null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("reportId is marked non-null but is null");
  }

  @Test
  void should_create_report_filter() {
    Long reportFilterId = 1L;
    Long reportUid = 2L;
    FilterCode filterCode = new FilterCode();
    DataSourceColumn dataSourceColumn = new DataSourceColumn();
    Character statusCd = 'A';
    Integer maxValueCnt = 10;
    Integer minValueCnt = 1;
    Long dataSource = 3L;

    ReportFilter actual =
        new ReportFilter(
            reportFilterId,
            new ReportId(reportUid, dataSource),
            filterCode,
            dataSourceColumn,
            statusCd,
            maxValueCnt,
            minValueCnt);

    assertThat(actual)
        .satisfies(rf -> assertEquals(reportFilterId, rf.getId()))
        .satisfies(rf -> assertEquals(reportUid, rf.getReportId().getReportUid()))
        .satisfies(rf -> assertEquals(dataSource, rf.getReportId().getDataSourceUid()))
        .satisfies(rf -> assertEquals(filterCode, rf.getFilterCode()))
        .satisfies(rf -> assertEquals(dataSourceColumn, rf.getDataSourceColumn()))
        .satisfies(rf -> assertEquals(statusCd, rf.getStatusCd()))
        .satisfies(rf -> assertEquals(maxValueCnt, rf.getMaxValueCnt()))
        .satisfies(rf -> assertEquals(minValueCnt, rf.getMinValueCnt()));
  }

  @Test
  void should_instantiate_via_protected_constructor() {
    ReportFilter actual = new ReportFilter();

    assertThat(actual)
        .isNotNull()
        .extracting("id", "reportId", "maxValueCnt") // Extracts fields directly, bypassing getters
        .containsOnlyNulls();
  }
}
