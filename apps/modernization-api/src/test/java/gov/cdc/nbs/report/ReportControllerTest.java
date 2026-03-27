package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.Filter;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.time.EffectiveTime;
import java.time.LocalDateTime;
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

    DataSource dataSourceEntity = getDataSource();
    ReportLibrary reportLibraryEntity = getLibrary();

    ReportConfiguration reportConfig =
        new ReportConfiguration(
            "python", new ReportDataSource(dataSourceEntity), new Library(reportLibraryEntity));
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

    Filter.Expr.Clause clause1 = new Filter.Expr.Clause(27L, "EQ", "47");
    Filter.Expr.Clause clause2 = new Filter.Expr.Clause(31L, "EQ", "35001");
    Filter.Expr.Connector connector = new Filter.Expr.Connector("OR", clause1, clause2);
    Filter.AdvancedFilter advancedFilter = new Filter.AdvancedFilter(false, connector);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, Arrays.asList(27L, 31L), List.of(advancedFilter));

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
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

    when(service.executeReport(request)).thenThrow(new NotFoundException(errorMsg));

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
            Arrays.asList(27L, 31L),
            List.of(new Filter.BasicFilter(true, 10066724L, List.of("35001"))));

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

  private DataSource getDataSource() {
    Long id = 1L;
    Integer maxLen = 123;
    Character conditionSecurity = 'N';
    Character jurisdictionSecurity = 'Y';
    Character reportingFacilitySecurity = 'Y';
    String dataSourceName = "nbs_ods.PHCDemographic";
    String dataSourceTitle = "Disease Counts by County";
    String dataSourceTypeCode = "N220";
    String descTxt = "Disease Counts by County sumarized to the Case Level";
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
    EffectiveTime effectiveTime = new EffectiveTime(effectiveFromTime, effectiveToTime);
    String orgAccessPermission = "N";
    String progAreaAccessPermission = "N";
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    return new DataSource(
        id,
        maxLen,
        conditionSecurity,
        jurisdictionSecurity,
        reportingFacilitySecurity,
        dataSourceName,
        dataSourceTitle,
        dataSourceTypeCode,
        descTxt,
        effectiveTime,
        orgAccessPermission,
        progAreaAccessPermission,
        statusCd,
        statusTime);
  }

  private ReportLibrary getLibrary() {
    String libName = "MOCK_CA01_DIAGNOSIS.SAS";
    String descTxt =
        "CA01: Chalk Talk Report: Case. This report includes information on the patients in the same Lot (Epi-linked group) for a specific disease.";
    String runner = "sas";
    Character builtIn = 'Y';
    LocalDateTime addTime = LocalDateTime.parse("2020-03-03T10:15:30");
    Long userId = 9L;
    LocalDateTime lastChgTime = LocalDateTime.parse("2020-02-28T09:15:30");

    return new ReportLibrary(
        libName, descTxt, runner, builtIn, addTime, userId, lastChgTime, userId);
  }
}
