package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class ReportExecutionServiceClientTest {
  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private RestClient client;
  @Mock private ReportFetcher reportFetcher;

  private MockedConstruction<ReportSpecBuilder> specBuilderMock;

  @Mock private RestClient.RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RestClient.RequestBodySpec requestBodySpec;

  @InjectMocks private ReportExecutionServiceClient reportExecutionClient;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;

  private ReportSpec spec;

  @BeforeEach
  void setUp() {
    spec =
        new ReportSpec(
            true,
            true,
            "nbs_custom",
            "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]",
            null,
            null,
            null,
            null);

    specBuilderMock =
        mockConstruction(
            ReportSpecBuilder.class, (builder, context) -> when(builder.build()).thenReturn(spec));

    when(client.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri("/report/execute")).thenReturn(requestBodySpec);
    when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
    when(requestBodySpec.accept(any(MediaType[].class))).thenReturn(requestBodySpec);
    when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);
    when(requestBodySpec.exchange(any())).thenCallRealMethod();
  }

  @SuppressWarnings("unchecked")
  @Test
  void executeReport_should_fetch_report_when_report_exists_and_runner_is_python() {
    ReportConfiguration reportConfig = mockReportConfiguration(true);

    when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    LibraryExecutionResult expectedResponse = getReportExecutionResponse().result();
    when(requestBodySpec.exchange(any())).thenReturn(expectedResponse);

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

    reportExecutionClient.executeReport(request, (res, reportSpec) -> {});

    ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
    verify(specBuilder).build();

    verify(client).post();
    verify(requestBodySpec).exchange(any());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void executeReport_should_set_context_and_description_from_response_headers() {
    ReportConfiguration reportConfig = mockReportConfiguration(true);
    RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse mockResponse =
        mockReportExecHttpResponse();

    when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

    reportExecutionClient.executeReport(request, (res, reportSpec) -> {});

    assertThat(response.result().contextHeader())
        .isEqualTo(
            Objects.requireNonNull(mockResponse.getHeaders().get("X-Report-Context-Header"))
                .getFirst());
    assertThat(response.result().description())
        .isEqualTo(
            Objects.requireNonNull(mockResponse.getHeaders().get("X-Report-Description"))
                .getFirst());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  void executeReport_should_set_context_and_description_from_response_headers() {
    ReportConfiguration reportConfig = mockReportConfiguration(true);
    RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse mockResponse =
        mockReportExecHttpResponse();

    when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ReportSpec spec =
        new ReportSpec(
            true,
            true,
            "nbs_custom",
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
      when(requestBodySpec.accept(any(MediaType[].class))).thenReturn(requestBodySpec);
      when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);

      when(requestBodySpec.exchange(any(RestClient.RequestHeadersSpec.ExchangeFunction.class)))
          .thenAnswer(
              invocation -> {
                RestClient.RequestHeadersSpec.ExchangeFunction exchangeFunction =
                    invocation.getArgument(0);
                return exchangeFunction.exchange(mock(HttpRequest.class), mockResponse);
              });

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

      ReportExecutionResult response = reportExecutionClient.executeReport(request);

      ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
      verify(specBuilder).build();

      assertThat(response.result().contextHeader())
          .isEqualTo(
              Objects.requireNonNull(mockResponse.getHeaders().get("X-Report-Context-Header"))
                  .getFirst());
      assertThat(response.result().description())
          .isEqualTo(
              Objects.requireNonNull(mockResponse.getHeaders().get("X-Report-Description"))
                  .getFirst());
    }
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportConfiguration reportConfig = mockReportConfiguration(false);

    lenient().when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, List.of(17L), null, List.of(), null);

    assertThatThrownBy(() -> reportExecutionClient.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for python");
  }

  private ReportExecutionResult getReportExecutionResponse() {
    RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse httpResponse =
        mockReportExecHttpResponse();
    HttpHeaders headers = httpResponse.getHeaders();

    String body = assertDoesNotThrow(() -> httpResponse.getBody().toString());

    return new ReportExecutionResult(
        new LibraryExecutionResult(
            body,
            headers.getFirst("X-Report-Context-Header"),
            headers.getFirst("X-Report-Description")),
        "SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]",
        LocalDateTime.of(2025, Month.MAY, 5, 12, 23));
  }

  private ReportConfiguration mockReportConfiguration(boolean isPython) {
    ReportConfiguration reportConfig = mock(ReportConfiguration.class);
    Library library = mock(Library.class);

    lenient().when(library.isBuiltin()).thenReturn(true);
    lenient().when(library.runner()).thenReturn(isPython ? "python" : "sas");

    lenient().when(reportConfig.isPython()).thenReturn(isPython);
    lenient().when(reportConfig.library()).thenReturn(library);

    return reportConfig;
  }

  private HttpHeaders buildReportExecResponseHeaders() {
    HttpHeaders headers = new HttpHeaders();

    headers.add("X-Report-Context-Header", "Look at all this context");
    headers.add("X-Report-Description", "WOW SUCH DESCRIPTION");
    return headers;
  }

  private RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse mockReportExecHttpResponse() {
    RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse mockResponse =
        mock(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse.class);

    assertDoesNotThrow(
        () ->
            Mockito.lenient()
                .when(mockResponse.getStatusCode())
                .thenReturn(org.springframework.http.HttpStatus.OK));

    HttpHeaders headers = buildReportExecResponseHeaders();

    InputStream responseBody =
        new ByteArrayInputStream(
            "report_uid,data_source _uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time"
                .getBytes());

    Mockito.lenient().when(mockResponse.getHeaders()).thenReturn(headers);
    assertDoesNotThrow(
        () -> Mockito.lenient().when(mockResponse.getBody()).thenReturn(responseBody));
    return mockResponse;
  }
}
