package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.mappers.FilterTypeMapper;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestBodySpec;
import org.springframework.web.client.RestClient.RequestBodyUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

  @Mock private ReportRepository reportRepository;
  @Mock private RestClient reportExecutionClient;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private List<ReportFilter> reportFilters;

  @Mock private RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RequestBodySpec requestBodySpec;
  @Mock private ResponseSpec responseSpec;

  @InjectMocks private ReportService service;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;

  private Report mockReport(ReportId id, String runner, String dataSourceName) {
    Report report = mock(Report.class);

    when(report.getReportLibrary()).thenReturn(reportLibrary);
    when(report.getDataSource()).thenReturn(dataSource);
    when(dataSource.getDataSourceName()).thenReturn(dataSourceName);
    when(report.getReportFilters()).thenReturn(reportFilters);
    when(reportLibrary.getRunner()).thenReturn(runner);
    when(reportLibrary.getLibraryName()).thenReturn("nbs_custom");
    when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    return report;
  }

  @Test
  void getReport_should_return_configuration_when_report_exists() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python", "nbs_ods.PHCDemographic");

    ReportConfiguration config = service.getReport(reportUid, dataSourceUid);

    assertThat(config.dataSource().name()).isEqualTo("nbs_ods.PHCDemographic");
    assertThat(config.filters())
        .allSatisfy(
            filterConfig -> {
              Optional<ReportFilter> matchingReportFilter =
                  reportFilters.stream()
                      .filter(f -> f.getId().equals(filterConfig.reportFilterUid()))
                      .findAny();

              assertThat(matchingReportFilter).isPresent();

              assertThat(filterConfig.reportColumnUid())
                  .isEqualTo(matchingReportFilter.get().getDataSourceColumn().getId());

              assertThat(filterConfig.filterType())
                  .isEqualTo(
                      FilterTypeMapper.fromFilterCode(matchingReportFilter.get().getFilterCode()));
            });
  }

  @Test
  void getReport_should_throw_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getReport(reportUid, dataSourceUid))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  @Test
  void getReportRunner_should_return_runner_when_report_exists() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python", "nbs_ods.PHCDemographic");

    String runner = service.getReportRunner(reportUid, dataSourceUid);

    assertThat(runner).isEqualTo("python");
  }

  @Test
  void getReportRunner_should_throw_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getReportRunner(reportUid, dataSourceUid))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  @Test
  void getReportRunner_should_throw_when_report_has_no_library() {
    ReportId reportId = new ReportId(reportUid, dataSourceUid);
    Report report = mockReport(reportId, "python", "nbs_ods.PHCDemographic");

    when(report.getReportLibrary()).thenReturn(null);

    assertThatThrownBy(() -> service.getReportRunner(reportUid, dataSourceUid))
        .isInstanceOf(UnprocessableEntityException.class)
        .hasMessage("No report library exists for report %s", reportId);
  }

  @Test
  void executeReport_should_return_response_when_report_exists_and_runner_is_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "python", "nbs_ods.PHCDemographic");

    ReportSpec spec =
        new ReportSpec(
            true,
            true,
            "Test Report",
            "nbs_custom",
            "[NBS_ODSE].[dbo].[PHCDemographic]",
            "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]");
    try (MockedConstruction<ReportSpecBuilder> specBuilderMock =
        mockConstruction(
            ReportSpecBuilder.class,
            (builder, context) -> when(builder.build()).thenReturn(spec))) {
      when(reportExecutionClient.post()).thenReturn(requestBodyUriSpec);
      when(requestBodyUriSpec.uri("/report/execute")).thenReturn(requestBodySpec);
      when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
      when(requestBodySpec.body(any(ReportSpec.class))).thenReturn(requestBodySpec);
      when(requestBodySpec.retrieve()).thenReturn(responseSpec);

      ResponseEntity<ReportResult> expectedResponse =
          new ResponseEntity<>(getReportExecutionResponse(), HttpStatus.OK);
      when(responseSpec.toEntity(ReportResult.class)).thenReturn(expectedResponse);

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, List.of());

      ResponseEntity<ReportResult> response = service.executeReport(request);

      assertThat(response).isEqualTo(expectedResponse);
      ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
      verify(specBuilder).build();
    }
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "java", "nbs_rdb.V_CHALK_TALK");

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(17L), List.of());

    assertThatThrownBy(() -> service.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for java");
  }

  @Test
  void executeReport_should_throw_not_found_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(18L), List.of());

    assertThatThrownBy(() -> service.executeReport(request))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
  }

  private ReportResult getReportExecutionResponse() {
    return new ReportResult(
        "table",
        "report_uid,data_source _uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time",
        "result header",
        "result subheader",
        "result description");
  }
}
