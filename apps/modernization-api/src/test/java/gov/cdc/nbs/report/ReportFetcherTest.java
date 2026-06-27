package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ReportFetcherTest {

  @Mock private ReportRepository reportRepository;

  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private ReportSortColumn reportSortColumn;

  @Mock private DisplayColumn columnA;
  @Mock private DisplayColumn columnB;

  @InjectMocks private ReportFetcher reportFetcher;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;
  private final Long columnAId = 3L;
  private final Long columnBId = 4L;

  private Report mockReport(
      ReportId id, String runner, String dataSourceName, List<ReportFilter> reportFilters) {
    return mockReport(id, runner, dataSourceName, reportFilters, "DESC");
  }

  private Report mockReport(
      ReportId id,
      String runner,
      String dataSourceName,
      List<ReportFilter> reportFilters,
      String sortDir) {
    Report report = mock(Report.class);

    Mockito.lenient().when(report.getReportLibrary()).thenReturn(reportLibrary);
    Mockito.lenient().when(report.getDataSource()).thenReturn(dataSource);
    Mockito.lenient().when(dataSource.getDataSourceName()).thenReturn(dataSourceName);
    Mockito.lenient().when(report.getReportFilters()).thenReturn(reportFilters);
    Mockito.lenient().when(report.getDisplayColumns()).thenReturn(List.of(columnA, columnB));
    Mockito.lenient().when(report.getShared()).thenReturn('P');
    Mockito.lenient().when(columnA.getDataSourceColumnId()).thenReturn(columnAId);
    Mockito.lenient().when(columnB.getDataSourceColumnId()).thenReturn(columnBId);
    Mockito.lenient().when(columnA.getSequenceNumber()).thenReturn(2);
    Mockito.lenient().when(columnB.getSequenceNumber()).thenReturn(1);
    Mockito.lenient().when(report.getReportSortColumns()).thenReturn(List.of(reportSortColumn));
    Mockito.lenient().when(reportSortColumn.getReportSortOrderCode()).thenReturn(sortDir);
    Mockito.lenient().when(reportSortColumn.getDataSourceColumnUid()).thenReturn(columnAId);
    Mockito.lenient().when(reportLibrary.getRunner()).thenReturn(runner);
    Mockito.lenient().when(reportLibrary.getLibraryName()).thenReturn("nbs_custom");
    Mockito.lenient().when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    return report;
  }

  @Nested
  class GetReport {
    @Test
    void getReport_should_return_configuration_when_report_exists() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      List<ReportFilter> reportFilters =
          List.of(
              new ReportFilter(
                  3L,
                  mock(Report.class),
                  new FilterCode(4L, "NONE", null, null, "J_S01", null, "BAS_JUR_LIST", null, null),
                  null,
                  null,
                  null,
                  null,
                  null,
                  null),
              new ReportFilter(
                  6L,
                  mock(Report.class),
                  new FilterCode(
                      5L,
                      "NONE",
                      null,
                      null,
                      "A_W01",
                      null,
                      ReportConstants.ADV_FILTER_TYPE,
                      null,
                      null),
                  null,
                  List.of(
                      FilterValue.builder()
                          .id(47L)
                          .sequenceNumber(1)
                          .valueType("CLAUSE")
                          .columnUid(9L)
                          .operator("EQUALS")
                          .valueTxt("value1")
                          .build()),
                  null,
                  null,
                  null,
                  null));
      mockReport(id, "python", "nbs_ods.PHCDemographic", reportFilters);

      ReportConfiguration config = reportFetcher.getReport(reportUid, dataSourceUid);

      assertThat(config.dataSource().name()).isEqualTo("nbs_ods.PHCDemographic");
      assertThat(config.basicFilters())
          .hasSize(1)
          .allSatisfy(
              filterConfig -> {
                assertThat(filterConfig.reportFilterUid()).isEqualTo(3L);

                assertThat(filterConfig.filterType().code()).isEqualTo("J_S01");
              });
      assertThat(config.advancedFilter().reportFilterUid()).isEqualTo(6L);
      assertThat(config.defaultColumnUids()).isEqualTo(List.of(columnBId, columnAId));
      assertThat(config.group()).isEqualTo(ReportConstants.ReportGroup.PRIVATE);
    }

    @Test
    void getReport_should_throw_when_report_not_found() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> reportFetcher.getReport(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
    }
  }

  @Nested
  class GetReportRunner {
    @Test
    void getReportRunner_should_return_runner_when_report_exists() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      mockReport(id, "python", "nbs_ods.PHCDemographic", List.of());

      String runner = reportFetcher.getReportRunner(reportUid, dataSourceUid);

      assertThat(runner).isEqualTo("python");
    }

    @Test
    void getReportRunner_should_throw_when_report_not_found() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> reportFetcher.getReportRunner(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
    }

    @Test
    void getReportRunner_should_throw_when_report_has_no_library() {
      ReportId reportId = new ReportId(reportUid, dataSourceUid);
      Report report = mockReport(reportId, "python", "nbs_ods.PHCDemographic", List.of());

      when(report.getReportLibrary()).thenReturn(null);

      assertThatThrownBy(() -> reportFetcher.getReportRunner(reportUid, dataSourceUid))
          .isInstanceOf(UnprocessableEntityException.class)
          .hasMessage("No report library exists for report %s", reportId);
    }
  }
}
