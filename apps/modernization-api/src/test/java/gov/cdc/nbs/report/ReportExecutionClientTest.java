package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.LibraryExecutionResult;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportExecutionResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.ReportRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class ReportExecutionClientTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private ReportRepository reportRepository;

  @Mock private RestClient client;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private ReportSortColumn reportSortColumn;

  @Mock private RestClient.RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RestClient.RequestBodySpec requestBodySpec;
  @Mock private RestClient.ResponseSpec responseSpec;
  @Mock private DisplayColumn columnA;
  @Mock private DisplayColumn columnB;

  @InjectMocks private ReportExecutionClient reportExecutionClient;

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

  @Test
  void executeReport_should_return_response_when_report_exists_and_runner_is_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python", "nbs_ods.PHCDemographic", List.of());

    ReportSpec spec =
        new ReportSpec(
            true,
            true,
            "Test Report",
            "nbs_custom",
            "[NBS_ODSE].[dbo].[PHCDemographic]",
            "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]",
            null,
            null,
            null,
            null);
    try (MockedConstruction<ReportSpecBuilder> specBuilderMock =
        mockConstruction(
            ReportSpecBuilder.class,
            (builder, context) -> when(builder.build()).thenReturn(spec))) {
      when(client.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/report/execute")).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
      when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);
      when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

      ResponseEntity<LibraryExecutionResult> expectedResponse =
          new ResponseEntity<>(getReportExecutionResponse().result(), HttpStatus.OK);
      when(responseSpec.toEntity(LibraryExecutionResult.class)).thenReturn(expectedResponse);

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

      ReportExecutionResult response = reportExecutionClient.executeReport(request);

      assertThat(response.result()).isEqualTo(expectedResponse.getBody());
      ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
      verify(specBuilder).build();
    }
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "java", "nbs_rdb.V_CHALK_TALK", List.of());

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, List.of(17L), null, List.of(), null);

    assertThatThrownBy(() -> reportExecutionClient.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for java");
  }

  @Test
  void executeReport_should_throw_not_found_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, List.of(18L), null, List.of(), null);

    assertThatThrownBy(() -> reportExecutionClient.executeReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  private ReportExecutionResult getReportExecutionResponse() {
    return new ReportExecutionResult(
        new LibraryExecutionResult(
            "table",
            "report_uid,data_source _uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time",
            "result header",
            "result subheader",
            "result description"),
        "SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]",
        LocalDateTime.of(2025, Month.MAY, 5, 12, 23));
  }
}
