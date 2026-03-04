package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ReportFilterTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThrows(NullPointerException.class, () -> new ReportFilter(null, null, null));
  }

  @Test
  void should_create_report_filter() {
    Long reportFilterId = 1L;
    Long reportUid = 2L;
    FilterCode filterCode = new FilterCode();
    DataSourceColumn dataSourceColumn = new DataSourceColumn();
    Integer maxValueCnt = 10;
    Integer minValueCnt = 1;
    DataSource dataSource = new DataSource();

    ReportFilter actual =
        new ReportFilter(
            reportFilterId,
            new ReportId(reportUid, dataSource),
            filterCode,
            dataSourceColumn,
            maxValueCnt,
            minValueCnt);

    assertThat(actual)
        .satisfies(rf -> assertEquals(reportFilterId, rf.getId()))
        .satisfies(rf -> assertEquals(reportUid, rf.getReportId().getReportUid()))
        .satisfies(rf -> assertEquals(filterCode, rf.getFilterCode()))
        .satisfies(rf -> assertEquals(dataSourceColumn, rf.getDataSourceColumn()))
        .satisfies(rf -> assertEquals(maxValueCnt, rf.getMaxValueCnt()))
        .satisfies(rf -> assertEquals(minValueCnt, rf.getMinValueCnt()));
  }
}
