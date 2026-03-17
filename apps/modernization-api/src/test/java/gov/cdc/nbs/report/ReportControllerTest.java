package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.exception.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);

    ReportConfiguration reportConfig = new ReportConfiguration(idMap, "python");
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

    when(service.getReport(reportUid, dataSourceUid))
        .thenThrow(
            new NotFoundException("Report not found for Report UID: 1 and Data Source UID: 2"));

    assertThatThrownBy(() -> controller.getReportConfiguration(reportUid, dataSourceUid))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  @Test
  void executeReport_should_return_executed_report() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            new ArrayList<>(Arrays.asList("state_cd", "cnty_cd")),
            new ArrayList<>(
                List.of(
                    new Filter.BasicFilter(true, "10066724", new ArrayList<>(List.of("test"))))));

    when(service.executeReport(request)).thenReturn(new ResponseEntity<>("blah", HttpStatus.OK));

    ResponseEntity<String> response = controller.executeReport(request);
    assertEquals("blah", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void executeReport_should_return_400_status_code_when_report_not_found() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            new ArrayList<>(Arrays.asList("state_cd", "cnty_cd")),
            new ArrayList<>(
                List.of(
                    new Filter.BasicFilter(true, "10066724", new ArrayList<>(List.of("test"))))));

    when(service.executeReport(request))
        .thenThrow(
            new NotFoundException("Report not found for Report UID: 1 and Data Source UID: 2"));
    ;

    assertThatThrownBy(() -> controller.executeReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  @Test
  void executeReport_should_return_501_status_code_when_report_not_implemented() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            new ArrayList<>(Arrays.asList("state_cd", "cnty_cd")),
            new ArrayList<>(
                List.of(
                    new Filter.BasicFilter(true, "10066724", new ArrayList<>(List.of("test"))))));

    when(service.executeReport(request))
        .thenThrow(new NotImplementedException("Report not implemented for python"));

    assertThatThrownBy(() -> controller.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessageContaining("Report not implemented for python");
  }
}
