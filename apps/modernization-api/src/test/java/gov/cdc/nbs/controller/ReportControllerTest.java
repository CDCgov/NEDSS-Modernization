package gov.cdc.nbs.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.service.ReportService;
import java.util.HashMap;
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
  void should_get_report_configuration_response() {
    Long reportUid = 1L;
    Long dataSourceUid = 2L;

    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);

    ReportConfigurationResponse reportConfig = new ReportConfigurationResponse(idMap, "python");
    when(service.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ResponseEntity<ReportConfigurationResponse> response =
        controller.getReport(reportUid, dataSourceUid);

    assertEquals(reportConfig, response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
