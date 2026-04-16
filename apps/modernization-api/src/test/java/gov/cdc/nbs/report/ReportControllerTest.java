package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.Filter;
import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

  @Mock private ReportService service;
  @InjectMocks private ReportController controller;

  @Test
  void getReport_should_return_report_configuration_response() {
    Long reportUid = 1L;
    Long dataSourceUid = 2L;

    DataSource dataSourceEntity = mock(DataSource.class);
    ReportLibrary reportLibraryEntity = mock(ReportLibrary.class);

    FilterConfiguration filterConfig = mock(FilterConfiguration.class);
    List<ReportColumn> columns = List.of(mock(ReportColumn.class));
    ReportConfiguration reportConfig =
        new ReportConfiguration(
            "python",
            new ReportDataSource(dataSourceEntity),
            new Library(reportLibraryEntity),
            "Report Title",
            List.of(filterConfig),
            columns);
    when(service.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ResponseEntity<ReportConfiguration> response =
        controller.getReportConfiguration(reportUid, dataSourceUid);

    assertEquals(reportConfig, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void getReport_should_return_400_status_code_when_report_not_found() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

    when(service.getReport(reportUid, dataSourceUid)).thenThrow(new NotFoundException(errorMsg));

    assertThatThrownBy(() -> controller.getReportConfiguration(reportUid, dataSourceUid))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(errorMsg);
  }

  @Test
  void exportReport_should_return_executed_report() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    Filter.Expr.Clause clause1 = new Filter.Expr.Clause(27L, "EQ", "47");
    Filter.Expr.Clause clause2 = new Filter.Expr.Clause(31L, "EQ", "35001");
    Filter.Expr.Connector connector = new Filter.Expr.Connector("OR", clause1, clause2);
    Filter.AdvancedFilter advancedFilter = new Filter.AdvancedFilter(false, connector);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, Arrays.asList(27L, 31L), List.of(advancedFilter));

    when(service.executeReport(request))
        .thenReturn(new ResponseEntity<>(getReportExecutionResponse(), HttpStatus.OK));

    ResponseEntity<ReportResult> response = controller.exportReport(request);
    assertEquals(getReportExecutionResponse(), response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void exportReport_should_return_400_status_code_when_report_not_found() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new NotFoundException(errorMsg));

    assertThatThrownBy(() -> controller.exportReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(errorMsg);
  }

  @Test
  void exportReport_should_return_501_status_code_when_report_not_implemented() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Report not implemented for python";

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new NotImplementedException(errorMsg));

    assertThatThrownBy(() -> controller.exportReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessageContaining(errorMsg);
  }

  @Test
  void exportReport_should_return_422_status_code_when_report_not_export() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            false,
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    assertThatThrownBy(() -> controller.exportReport(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("isExport must be true when exporting a report");
  }

  @Test
  void exportReport_should_return_500_status_code_when_unexpected_exception() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Uh oh!";

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new RuntimeException(errorMsg));

    assertThatThrownBy(() -> controller.exportReport(request))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining(errorMsg);
  }

  @Test
  void runReport_should_return_executed_report() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    Filter.Expr.Clause clause1 = new Filter.Expr.Clause(27L, "EQ", "47");
    Filter.Expr.Clause clause2 = new Filter.Expr.Clause(31L, "EQ", "35001");
    Filter.Expr.Connector connector = new Filter.Expr.Connector("OR", clause1, clause2);
    Filter.AdvancedFilter advancedFilter = new Filter.AdvancedFilter(false, connector);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, false, Arrays.asList(27L, 31L), List.of(advancedFilter));

    when(service.executeReport(request))
        .thenReturn(new ResponseEntity<>(getReportExecutionResponse(), HttpStatus.OK));

    ResponseEntity<ReportResult> response = controller.runReport(request);
    assertEquals(getReportExecutionResponse(), response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void runReport_should_return_422_status_code_when_report_not_run() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    assertThatThrownBy(() -> controller.runReport(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("isExport must be false when running a report");
  }

  private ReportResult getReportExecutionResponse() {
    return new ReportResult(
        "table",
        "report_uid,data_source_uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time",
        "result header",
        "result subheader",
        "result description");
  }
}
