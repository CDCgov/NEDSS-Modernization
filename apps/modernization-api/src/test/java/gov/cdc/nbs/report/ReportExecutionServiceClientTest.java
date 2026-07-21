package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.LibraryExecutionResult;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportExecutionResult;
import gov.cdc.nbs.report.models.ReportSpec;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
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
class ReportExecutionServiceClientTest {
  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private RestClient client;
  @Mock private ReportFetcher reportFetcher;

  @Mock private RestClient.RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RestClient.RequestBodySpec requestBodySpec;
  @Mock private RestClient.ResponseSpec responseSpec;

  @InjectMocks private ReportExecutionServiceClient reportExecutionClient;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;

  @Test
  void executeReport_should_return_response_when_report_exists_and_runner_is_python() {
    ReportConfiguration reportConfig = mockReportConfiguration(true);

    when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

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
    ReportConfiguration reportConfig = mockReportConfiguration(false);

    Mockito.lenient()
        .when(reportFetcher.getReport(reportUid, dataSourceUid))
        .thenReturn(reportConfig);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, List.of(17L), null, List.of(), null);

    assertThatThrownBy(() -> reportExecutionClient.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for python");
  }

  private ReportExecutionResult getReportExecutionResponse() {
    return new ReportExecutionResult(
        new LibraryExecutionResult(
            "report_uid,data_source _uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time",
            "result subheader",
            "result description"),
        "SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]",
        LocalDateTime.of(2025, Month.MAY, 5, 12, 23));
  }

  private ReportConfiguration mockReportConfiguration(boolean isPython) {
    ReportConfiguration reportConfig = mock(ReportConfiguration.class);
    Library library = mock(Library.class);

    Mockito.lenient().when(library.isBuiltin()).thenReturn(true);
    Mockito.lenient().when(library.runner()).thenReturn(isPython ? "python" : "sas");

    Mockito.lenient().when(reportConfig.isPython()).thenReturn(isPython);
    Mockito.lenient().when(reportConfig.library()).thenReturn(library);

    return reportConfig;
  }
}
