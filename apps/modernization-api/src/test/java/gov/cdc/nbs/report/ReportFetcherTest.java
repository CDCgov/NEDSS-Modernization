package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportFetcherTest {

  @Mock private ReportRepository reportRepository;

  @InjectMocks private ReportFetcher reportFetcher;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;
  private final Long columnAId = 3L;
  private final Long columnBId = 4L;

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
                      FilterCode.ADV_FILTER_TYPE,
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
      Report report = buildTestReport(id, "python", "nbs_ods.PHCDemographic", reportFilters);
      Mockito.lenient().when(reportRepository.findById(id)).thenReturn(Optional.of(report));

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

    @Test
    void getReport_should_throw_when_library_not_found() {
      Report report = mock(Report.class);

      Mockito.lenient().when(report.getReportLibrary()).thenReturn(null);
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.of(report));

      assertThatThrownBy(() -> reportFetcher.getReport(reportUid, dataSourceUid))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("No library found for this report");
    }
  }

  @Nested
  class GetReportRunner {
    @Test
    void getReportRunner_should_return_runner_when_report_exists() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      Report report = buildTestReport(id, "python", "nbs_ods.PHCDemographic", List.of());
      Mockito.lenient().when(reportRepository.findById(id)).thenReturn(Optional.of(report));

      String runner = reportFetcher.getReportRunner(reportUid, dataSourceUid);

      assertThat(runner).isEqualTo("python");
    }

    @Test
    void getReportRunner_should_throw_when_report_not_found() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> reportFetcher.getReportRunner(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessage(
              "Report not found for Report UID: %s and Data Source UID: %s",
              reportUid, dataSourceUid);
    }

    @Test
    void getReportRunner_should_return_sas_when_report_has_no_library() {
      ReportId reportId = new ReportId(reportUid, dataSourceUid);
      Report report = buildTestReport(reportId, "python", "nbs_ods.PHCDemographic", List.of());

      report.setReportLibrary(null);

      Mockito.lenient().when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

      String runner = reportFetcher.getReportRunner(reportUid, dataSourceUid);

      assertThat(runner).isEqualTo("sas");
    }
  }

  private Report buildTestReport(
      ReportId id, String runner, String dataSourceName, List<ReportFilter> reportFilters) {
    Report report = Report.builder().id(id).build();

    ReportLibrary reportLibrary = new ReportLibrary();
    reportLibrary.setLibraryName("nbs_custom");
    reportLibrary.setRunner(runner);
    reportLibrary.setIsBuiltinInd('Y');
    reportLibrary.setColumnSelectInd('Y');

    DataSource dataSource =
        DataSource.builder()
            .id(dataSourceUid)
            .statusCd(Status.ACTIVE_CODE)
            .dataSourceName(dataSourceName)
            .build();

    ReportSortColumn reportSortColumn =
        ReportSortColumn.builder()
            .report(report)
            .reportSortOrderCode("ASC")
            .dataSourceColumnUid(columnAId)
            .build();

    DataSourceColumn dataSourceColA =
        DataSourceColumn.builder()
            .id(columnAId)
            .dataSource(dataSource)
            .columnName("Column A")
            .columnTitle("Column A Title")
            .statusCd(Status.ACTIVE_CODE)
            .build();
    DisplayColumn columnA =
        DisplayColumn.builder()
            .report(report)
            .dataSourceColumn(dataSourceColA)
            .dataSourceColumnId(dataSourceColA.getId())
            .sequenceNumber(2)
            .statusCd(Status.ACTIVE_CODE)
            .build();

    DataSourceColumn dataSourceColB =
        DataSourceColumn.builder()
            .id(columnBId)
            .dataSource(dataSource)
            .columnName("Column B")
            .columnTitle("Column B Title")
            .statusCd(Status.ACTIVE_CODE)
            .build();
    DisplayColumn columnB =
        DisplayColumn.builder()
            .report(report)
            .dataSourceColumn(dataSourceColB)
            .dataSourceColumnId(dataSourceColB.getId())
            .sequenceNumber(1)
            .statusCd(Status.ACTIVE_CODE)
            .build();

    dataSource.setDataSourceColumns(new ArrayList<>(List.of(dataSourceColA, dataSourceColB)));

    report.setReportLibrary(reportLibrary);
    report.setDataSource(dataSource);
    report.setReportSortColumns(new ArrayList<>(List.of(reportSortColumn)));
    report.setDisplayColumns(new ArrayList<>(List.of(columnA, columnB)));
    report.setReportFilters(reportFilters);
    report.setShared('P');

    return report;
  }
}
