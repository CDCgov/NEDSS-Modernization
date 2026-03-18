package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.exception.NotFoundException;
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

    ReportConfiguration reportConfig = new ReportConfiguration(reportUid, dataSourceUid, "python");
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
  void executeReport_should_return_executed_report() {
    long reportUid = 1L;
    long dataSourceUid = 2L;

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList("state_cd", "cnty_cd"),
            List.of(new Filter.BasicFilter(true, "10066724", List.of("35001"))));

    when(service.executeReport(request))
        .thenReturn(new ResponseEntity<>(getReportExecutionResponse(), HttpStatus.OK));

    ResponseEntity<String> response = controller.executeReport(request);
    assertEquals(getReportExecutionResponse(), response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void executeReport_should_return_400_status_code_when_report_not_found() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList("state_cd", "cnty_cd"),
            List.of(new Filter.BasicFilter(true, "10066724", List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new NotFoundException(errorMsg));
    ;

    assertThatThrownBy(() -> controller.executeReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining(errorMsg);
  }

  @Test
  void executeReport_should_return_501_status_code_when_report_not_implemented() {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    String errorMsg = "Report not implemented for python";

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid,
            dataSourceUid,
            true,
            Arrays.asList("state_cd", "cnty_cd"),
            List.of(new Filter.BasicFilter(true, "10066724", List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new NotImplementedException(errorMsg));

    assertThatThrownBy(() -> controller.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessageContaining(errorMsg);
  }

  private String getReportExecutionResponse() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode objectNode = mapper.createObjectNode();

    objectNode.put("content_type", "table");
    objectNode.put(
        "content",
        "report_uid,data_source_uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time");
    objectNode.put("description", "Custom Report For Table: nbs_ods.NBS_configuration");

    return objectNode.toPrettyString();
  }
}
