package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.DataSourceRepository;
import gov.cdc.nbs.repository.ReportFilterRepository;
import gov.cdc.nbs.repository.ReportLibraryRepository;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
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
  @Mock private DataSourceRepository dataSourceRepository;
  @Mock private ReportLibraryRepository reportLibraryRepository;
  @Mock private ReportFilterRepository reportFilterRepository;

  @Mock private RestClient reportExecutionClient;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;

  @Mock private RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RequestBodySpec requestBodySpec;
  @Mock private ResponseSpec responseSpec;

  @InjectMocks private ReportService service;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;
  private final Long libraryId = 20L;

  private Report mockReport(
      ReportId id, String runner, String dataSourceName, List<ReportFilter> reportFilters) {
    Report report = mock(Report.class);

    Mockito.lenient().when(report.getReportLibrary()).thenReturn(reportLibrary);
    Mockito.lenient().when(report.getDataSource()).thenReturn(dataSource);
    Mockito.lenient().when(dataSource.getDataSourceName()).thenReturn(dataSourceName);
    Mockito.lenient().when(report.getReportFilters()).thenReturn(reportFilters);
    Mockito.lenient().when(reportLibrary.getRunner()).thenReturn(runner);
    Mockito.lenient().when(reportLibrary.getLibraryName()).thenReturn("nbs_custom");
    Mockito.lenient().when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    return report;
  }

  @Test
  void createReport_should_create_and_return_report_when_all_inputs_are_valid() {
    // Arrange
    DataSource mockDataSource = mock(DataSource.class);
    ReportLibrary mockReportLibrary = mock(ReportLibrary.class);
    List<Long> filterIds = List.of(1L, 2L, 3L);
    List<ReportFilter> mockFilters =
        List.of(mock(ReportFilter.class), mock(ReportFilter.class), mock(ReportFilter.class));

    when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.of(mockDataSource));
    when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.of(mockReportLibrary));
    when(reportFilterRepository.findAllById(filterIds)).thenReturn(mockFilters);

    Report savedReport = mock(Report.class);
    when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

    CreateReportRequest request =
        new CreateReportRequest(
            dataSourceUid,
            libraryId,
            "Test Report Title",
            filterIds,
            "Test Report Description",
            "Test Owner Id",
            "123");

    // Act
    Report result = service.createReport(request);

    // Assert
    assertThat(result).isEqualTo(savedReport);
    verify(dataSourceRepository).findById(dataSourceUid);
    verify(reportLibraryRepository).findById(libraryId);
    verify(reportFilterRepository).findAllById(filterIds);
    verify(reportRepository).save(any(Report.class));
  }

  @Test
  void createReport_should_create_report_when_filter_ids_are_empty() {
    // Arrange
    DataSource mockDataSource = mock(DataSource.class);
    ReportLibrary mockReportLibrary = mock(ReportLibrary.class);
    List<Long> emptyFilterIds = List.of();

    when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.of(mockDataSource));
    when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.of(mockReportLibrary));

    Report savedReport = mock(Report.class);
    when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

    CreateReportRequest request =
        new CreateReportRequest(
            dataSourceUid,
            libraryId,
            "Test Report Title",
            emptyFilterIds,
            "Test Report Description",
            "Test Owner Id",
            "123");

    // Act
    Report result = service.createReport(request);

    // Assert
    assertThat(result).isEqualTo(savedReport);
    verify(dataSourceRepository).findById(dataSourceUid);
    verify(reportLibraryRepository).findById(libraryId);
    verify(reportFilterRepository, never()).findAllById(any());

    // Assert that the report was saved
    verify(reportRepository).save(any(Report.class));
  }

  @Test
  void createReport_should_throw_when_data_source_not_found() {
    // Arrange
    List<Long> filterIds = List.of(1L, 2L);

    when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

    CreateReportRequest request =
        new CreateReportRequest(
            dataSourceUid,
            libraryId,
            "Test Report Title",
            filterIds,
            "Test Report Description",
            "Test Owner Id",
            "123");

    // Act & Assert
    assertThatThrownBy(() -> service.createReport(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No data source found for ID " + dataSourceUid);

    verify(dataSourceRepository).findById(dataSourceUid);
    verify(reportLibraryRepository, never()).findById(any());
    verify(reportFilterRepository, never()).findAllById(any());

    // Assert that the report was not saved
    verify(reportRepository, never()).save(any());
  }

  @Test
  void createReport_should_throw_when_report_library_not_found() {
    // Arrange
    DataSource mockDataSource = mock(DataSource.class);
    List<Long> filterIds = List.of(1L, 2L);

    when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.of(mockDataSource));
    when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

    CreateReportRequest request =
        new CreateReportRequest(
            dataSourceUid,
            libraryId,
            "Test Report Title",
            filterIds,
            "Test Report Description",
            "Test Owner Id",
            "123");

    // Act & Assert
    assertThatThrownBy(() -> service.createReport(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report library found for ID " + libraryId);

    verify(dataSourceRepository).findById(dataSourceUid);
    verify(reportLibraryRepository).findById(libraryId);
    verify(reportFilterRepository, never()).findAllById(any());

    // Assert that the report was not saved
    verify(reportRepository, never()).save(any());
  }

  @Test
  void createReport_should_throw_when_filter_ids_are_invalid() {
    // Arrange
    DataSource mockDataSource = mock(DataSource.class);
    ReportLibrary mockReportLibrary = mock(ReportLibrary.class);
    List<Long> requestedFilterIds = List.of(1L, 2L, 3L);
    List<ReportFilter> returnedFilters =
        List.of(
            mock(ReportFilter.class),
            mock(ReportFilter.class)); // Only 2 filters returned instead of 3

    when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.of(mockDataSource));
    when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.of(mockReportLibrary));
    when(reportFilterRepository.findAllById(requestedFilterIds)).thenReturn(returnedFilters);

    CreateReportRequest request =
        new CreateReportRequest(
            dataSourceUid,
            libraryId,
            "Test Report Title",
            requestedFilterIds,
            "Test Report Description",
            "Test Owner Id",
            "123");

    // Act & Assert
    assertThatThrownBy(() -> service.createReport(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("One or more filter IDs are invalid in filterId list " + requestedFilterIds);

    verify(dataSourceRepository).findById(dataSourceUid);
    verify(reportLibraryRepository).findById(libraryId);
    verify(reportFilterRepository).findAllById(requestedFilterIds);

    // Assert that the report was not saved
    verify(reportRepository, never()).save(any());
  }

  @Test
  void getReport_should_return_configuration_when_report_exists() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    List<ReportFilter> reportFilters =
        List.of(
            new ReportFilter(
                3L,
                mock(Report.class),
                new FilterCode(4L, "NONE", null, null, "J_S01", null, "BAS_JUR_LIST", null, null),
                null,
                null,
                null,
                null,
                null,
                null),
            new ReportFilter(
                6L,
                mock(Report.class),
                new FilterCode(
                    5L,
                    "NONE",
                    null,
                    null,
                    "A_W01",
                    null,
                    ReportConstants.ADV_FILTER_TYPE,
                    null,
                    null),
                null,
                null,
                null,
                null,
                null,
                null));
    mockReport(id, "python", "nbs_ods.PHCDemographic", reportFilters);

    ReportConfiguration config = service.getReport(reportUid, dataSourceUid);

    assertThat(config.dataSource().name()).isEqualTo("nbs_ods.PHCDemographic");
    assertThat(config.basicFilters())
        .hasSize(1)
        .allSatisfy(
            filterConfig -> {
              assertThat(filterConfig.reportFilterUid()).isEqualTo(3L);

              assertThat(filterConfig.filterType().code()).isEqualTo("J_S01");
            });
    assertThat(config.advancedFilter().reportFilterUid()).isEqualTo(6L);
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
    mockReport(id, "python", "nbs_ods.PHCDemographic", List.of());

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
    Report report = mockReport(reportId, "python", "nbs_ods.PHCDemographic", List.of());

    when(report.getReportLibrary()).thenReturn(null);

    assertThatThrownBy(() -> service.getReportRunner(reportUid, dataSourceUid))
        .isInstanceOf(UnprocessableEntityException.class)
        .hasMessage("No report library exists for report %s", reportId);
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
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, List.of(), null);

      ResponseEntity<ReportResult> response = service.executeReport(request);

      assertThat(response).isEqualTo(expectedResponse);
      ReportSpecBuilder specBuilder = specBuilderMock.constructed().getFirst();
      verify(specBuilder).build();
    }
  }

  @Test
  void executeReport_should_throw_not_implemented_when_runner_not_python() {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    mockReport(id, "java", "nbs_rdb.V_CHALK_TALK", List.of());

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(17L), List.of(), null);

    assertThatThrownBy(() -> service.executeReport(request))
        .isInstanceOf(NotImplementedException.class)
        .hasMessage("Report not implemented for java");
  }

  @Test
  void executeReport_should_throw_not_found_when_report_not_found() {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    when(reportRepository.findById(id)).thenReturn(Optional.empty());

    ReportExecutionRequest request =
        new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(18L), List.of(), null);

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
