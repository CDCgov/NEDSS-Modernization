package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
  @Mock private FilterCodeRepository filterCodeRepository;
  @Mock private DataSourceColumnRepository dataSourceColumnRepository;

  @Mock private RestClient reportExecutionClient;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;

  @Mock private RequestBodyUriSpec requestBodyUriSpec;
  @Mock private RequestBodySpec requestBodySpec;
  @Mock private ResponseSpec responseSpec;
  @Mock private DisplayColumn columnA;
  @Mock private DisplayColumn columnB;

  @InjectMocks private ReportService service;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;
  private final Long libraryId = 20L;
  private final Long columnAId = 3L;
  private final Long columnBId = 4L;

  private Report mockReport(
      ReportId id, String runner, String dataSourceName, List<ReportFilter> reportFilters) {
    Report report = mock(Report.class);

    Mockito.lenient().when(report.getReportLibrary()).thenReturn(reportLibrary);
    Mockito.lenient().when(report.getDataSource()).thenReturn(dataSource);
    Mockito.lenient().when(dataSource.getDataSourceName()).thenReturn(dataSourceName);
    Mockito.lenient().when(report.getReportFilters()).thenReturn(reportFilters);
    Mockito.lenient().when(report.getDisplayColumns()).thenReturn(List.of(columnA, columnB));
    Mockito.lenient().when(columnA.getDataSourceColumnId()).thenReturn(columnAId);
    Mockito.lenient().when(columnB.getDataSourceColumnId()).thenReturn(columnBId);
    Mockito.lenient().when(columnA.getSequenceNumber()).thenReturn(2);
    Mockito.lenient().when(columnB.getSequenceNumber()).thenReturn(1);
    Mockito.lenient().when(reportLibrary.getRunner()).thenReturn(runner);
    Mockito.lenient().when(reportLibrary.getLibraryName()).thenReturn("nbs_custom");
    Mockito.lenient().when(reportRepository.findById(id)).thenReturn(Optional.of(report));

    return report;
  }

  @Nested
  class UpsetReport {
    private final Long filterCodeUid = 7L;
    private final Long columnUid = 8L;

    private NbsUserDetails user;

    @BeforeEach
    void setup() {
      DataSource mockDataSource = mock(DataSource.class);
      ReportLibrary mockReportLibrary = mock(ReportLibrary.class);
      user = mock(NbsUserDetails.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      Mockito.lenient().when(mockFilterCode.getId()).thenReturn(filterCodeUid);

      DataSourceColumn mockColumn = mock(DataSourceColumn.class);
      Mockito.lenient().when(mockColumn.getId()).thenReturn(columnUid);

      Mockito.lenient()
          .when(dataSourceRepository.findById(dataSourceUid))
          .thenReturn(Optional.of(mockDataSource));
      Mockito.lenient()
          .when(reportLibraryRepository.findById(libraryId))
          .thenReturn(Optional.of(mockReportLibrary));
      Mockito.lenient()
          .when(filterCodeRepository.findAllById(any()))
          .thenReturn(List.of(mockFilterCode));
      Mockito.lenient()
          .when(dataSourceColumnRepository.findAllById(any()))
          .thenReturn(List.of(mockColumn));
    }

    private AdminReportRequest buildAdminReportRequest(Boolean includeFilters) {
      List<CreateFilterRequest> filterRequests =
          includeFilters
              ? List.of(
                  new CreateFilterRequest(7L, columnUid, ReportConstants.SelectType.MULTI, false))
              : Collections.emptyList();
      return new AdminReportRequest(
          dataSourceUid,
          libraryId,
          "Test Report Title",
          "123",
          0L,
          ReportConstants.ReportGroup.REPORTING_FACILITY.toString(),
          filterRequests,
          "Test Report Description");
    }

    @Test
    void upsertReport_should_create_and_return_report_when_all_inputs_are_valid() {
      Report savedReport = mock(Report.class);
      when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

      AdminReportRequest request = buildAdminReportRequest(true);

      Report result = service.upsertReport(request, user, null);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void upsertReport_should_create_report_when_filters_are_empty() {
      Report savedReport = mock(Report.class);
      when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

      AdminReportRequest request = buildAdminReportRequest(false);

      Report result = service.upsertReport(request, user, null);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository).save(any(Report.class));
      verify(reportFilterRepository, never()).saveAll(any());
    }

    @Test
    void upsertReport_should_throw_when_data_source_not_found() {
      when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.upsertReport(request, user, null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No data source found for ID " + dataSourceUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository, never()).findById(any());

      verify(reportRepository, never()).save(any());
      verify(reportFilterRepository, never()).findAllById(any());
    }

    @Test
    void upsertReport_should_throw_when_report_library_not_found() {
      when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.upsertReport(request, user, null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report library found for ID " + libraryId);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository, never()).save(any());
      verify(reportFilterRepository, never()).saveAll(any());
    }

    @Test
    void upsertReport_should_throw_when_filter_has_unknown_filter_code() {
      Report savedReport = mock(Report.class);
      when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

      when(filterCodeRepository.findAllById(any())).thenReturn(Collections.emptyList());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.upsertReport(request, user, null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Unknown filterCodeUid provided: " + filterCodeUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportFilterRepository, never()).saveAll(any());
    }

    @Test
    void upsertReport_should_throw_when_filter_has_unknown_column() {
      Report savedReport = mock(Report.class);
      when(reportRepository.save(any(Report.class))).thenReturn(savedReport);

      when(dataSourceColumnRepository.findAllById(any())).thenReturn(Collections.emptyList());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.upsertReport(request, user, null))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Unknown columnUid provided: " + columnUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportFilterRepository, never()).saveAll(any());
    }
  }

  @Nested
  class GetReport {
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
      assertThat(config.defaultColumnUids()).isEqualTo(List.of(columnBId, columnAId));
    }

    @Test
    void getReport_should_throw_when_report_not_found() {
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> service.getReport(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
    }
  }

  @Nested
  class GetReportRunner {
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
  }

  @Nested
  class ExecuteReport {
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
