package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
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
    ReportConfiguration reportConfig = mockReportConfiguration(true);
    when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    spec =
        new ReportSpec(
            true,
            true,
            "nbs_custom",
            "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]",
            null,
            null,
            null,
            null,
            null);

    specBuilderMock =
        mockConstruction(
            ReportSpecBuilder.class, (builder, context) -> when(builder.build()).thenReturn(spec));

    Mockito.lenient().when(client.post()).thenReturn(requestBodyUriSpec);
    Mockito.lenient().when(requestBodyUriSpec.uri("/report/execute")).thenReturn(requestBodySpec);
    Mockito.lenient()
        .when(requestBodySpec.contentType(any(MediaType.class)))
        .thenReturn(requestBodySpec);
    Mockito.lenient()
        .when(requestBodySpec.accept(any(MediaType[].class)))
        .thenReturn(requestBodySpec);
    Mockito.lenient().when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);
    Mockito.lenient().when(requestBodySpec.exchange(any())).thenCallRealMethod();
  }

  @AfterEach
  void tearDown() {
    specBuilderMock.close();
  }

  @Test
  void executeReport_should_fetch_report_when_report_exists_and_runner_is_python() {
    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

    reportExecutionClient.executeReport(request, (res, reportSpec) -> {});

    ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
    verify(specBuilder).build();

    verify(client).post();
    verify(requestBodySpec).exchange(any());
  }

  @Test
  void executeReport_should_invoke_the_bifunction_parameter_provided() {
    final boolean[] wasCalled = {false};

    doAnswer(
            invocation -> {
              RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse response =
                  mock(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse.class);
              when(response.getStatusCode()).thenReturn(HttpStatus.OK);

              RestClient.RequestHeadersSpec.ExchangeFunction<HttpRequest> exchangeFunction =
                  invocation.getArgument(0);
              exchangeFunction.exchange(mock(HttpRequest.class), response);
              return null;
            })
        .when(requestBodySpec)
        .exchange(any());

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

    reportExecutionClient.executeReport(
        request,
        (res, reportSpec) -> {
          wasCalled[0] = true;
        });

    verify(requestBodySpec).exchange(any());
    assertThat(wasCalled[0]).isTrue();
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportConfiguration reportConfig = mockReportConfiguration(false);
    lenient().when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

    ReportExecutionRequest request =
        new ReportExecutionRequest(
            reportUid, dataSourceUid, true, List.of(17L), null, List.of(), null);

    assertThatThrownBy(() -> reportExecutionClient.executeReport(request, (res, reportSpec) -> {}))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for python");
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
}
