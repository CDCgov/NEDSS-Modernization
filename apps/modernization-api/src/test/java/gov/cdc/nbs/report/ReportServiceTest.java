package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.ReportConstants.ReportGroup;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.mockito.Spy;
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

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private ReportRepository reportRepository;
  @Mock private DataSourceRepository dataSourceRepository;
  @Mock private ReportLibraryRepository reportLibraryRepository;
  @Mock private FilterCodeRepository filterCodeRepository;
  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @Mock private ReportSectionRepository reportSectionRepository;
  @Mock private ReportMapper reportMapper;

  @Mock private RestClient reportExecutionClient;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private ReportSortColumn reportSortColumn;
  @Mock private ReportFilterBuilder reportFilterBuilder;

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

  @Nested
  class CreateReport {
    private final Long filterCodeUid = 7L;
    private final Long columnUid = 8L;
    private final String sectionCd = "1000";

    private NbsUserDetails mockUser;
    private DataSource mockDataSource;
    private ReportLibrary mockReportLibrary;

    @BeforeEach
    void setup() {
      mockDataSource = mock(DataSource.class);
      mockReportLibrary = mock(ReportLibrary.class);
      mockUser = mock(NbsUserDetails.class);

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
          .when(filterCodeRepository.findById(any()))
          .thenReturn(Optional.of(mockFilterCode));
      Mockito.lenient()
          .when(dataSourceColumnRepository.findById(any()))
          .thenReturn(Optional.of(mockColumn));
      Mockito.lenient().when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(true);
    }

    private AdminReportRequest buildAdminReportRequest(Boolean includeFilters) {
      List<UpsertFilterRequest> filterRequests =
          includeFilters
              ? List.of(
                  new UpsertFilterRequest(
                      null, 7L, columnUid, ReportConstants.SelectType.MULTI, false))
              : Collections.emptyList();
      return new AdminReportRequest(
          dataSourceUid,
          libraryId,
          "Test Report Title",
          sectionCd,
          0L,
          ReportConstants.ReportGroup.REPORTING_FACILITY,
          filterRequests,
          "Test Report Description");
    }

    @Test
    void createReport_should_create_and_return_report_when_all_inputs_are_valid() {
      Report savedReport = mock(Report.class);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, null))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.createReport(request, mockUser);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void createReport_should_create_report_when_filters_are_empty() {
      Report savedReport = mock(Report.class);

      AdminReportRequest request = buildAdminReportRequest(false);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, null))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.createReport(request, mockUser);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportMapper)
          .fromAdminReportRequest(request, mockUser, mockReportLibrary, mockDataSource, null);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void createReport_should_throw_when_data_source_not_found() {
      when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No data source found for ID " + dataSourceUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository, never()).findById(any());

      verify(reportRepository, never()).save(any());
    }

    @Test
    void createReport_should_throw_when_report_library_not_found() {
      when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report library found for ID " + libraryId);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void createReport_should_throw_when_report_section_not_found() {
      when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(false);

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report section found for code " + sectionCd);

      verify(reportRepository, never()).save(any());
    }
  }

  @Nested
  class EditReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;
    private final Long libraryId = 20L;
    private final Long filterCodeUid = 7L;
    private final Long columnUid = 8L;
    private final String sectionCd = "1000";

    private NbsUserDetails mockUser;
    private DataSource mockDataSource;
    private ReportLibrary mockReportLibrary;
    private Report savedReport;
    private ReportId reportId = new ReportId(reportUid, dataSourceUid);

    @BeforeEach
    void setup() {
      mockDataSource = mock(DataSource.class);
      mockReportLibrary = mock(ReportLibrary.class);
      mockUser = mock(NbsUserDetails.class);
      savedReport = mock(Report.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      Mockito.lenient().when(mockFilterCode.getId()).thenReturn(filterCodeUid);

      DataSourceColumn mockColumn = mock(DataSourceColumn.class);
      Mockito.lenient().when(mockColumn.getId()).thenReturn(columnUid);

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(Optional.of(savedReport));

      Mockito.lenient()
          .when(dataSourceRepository.findById(dataSourceUid))
          .thenReturn(Optional.of(mockDataSource));
      Mockito.lenient()
          .when(reportLibraryRepository.findById(libraryId))
          .thenReturn(Optional.of(mockReportLibrary));
      Mockito.lenient()
          .when(filterCodeRepository.findById(any()))
          .thenReturn(Optional.of(mockFilterCode));
      Mockito.lenient()
          .when(dataSourceColumnRepository.findById(any()))
          .thenReturn(Optional.of(mockColumn));
      Mockito.lenient().when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(true);
    }

    private AdminReportRequest buildAdminReportRequest(Boolean includeFilters) {
      List<UpsertFilterRequest> filterRequests =
          includeFilters
              ? List.of(
                  new UpsertFilterRequest(
                      null, filterCodeUid, columnUid, ReportConstants.SelectType.MULTI, false))
              : Collections.emptyList();
      return new AdminReportRequest(
          dataSourceUid,
          libraryId,
          "Test Report Title",
          sectionCd,
          0L,
          ReportConstants.ReportGroup.REPORTING_FACILITY,
          filterRequests,
          "Test Report Description");
    }

    @Test
    void editReport_should_edit_and_return_report_when_all_inputs_are_valid() {
      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void editReport_should_create_report_when_filters_are_empty() {
      AdminReportRequest request = buildAdminReportRequest(false);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void editReport_should_throw_when_data_source_not_found() {
      when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No data source found for ID " + dataSourceUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository, never()).findById(any());

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_throw_when_report_library_not_found() {
      when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report library found for ID " + libraryId);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_throw_when_report_section_not_found() {
      when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(false);

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report section found for code " + sectionCd);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_delete_filters_not_included_in_request() {
      FilterCode filterCode = mock(FilterCode.class);
      ArrayList<ReportFilter> existingFilters = new ArrayList<>();
      existingFilters.add(new ReportFilter(100L, filterCode));
      existingFilters.add(new ReportFilter(101L, filterCode));

      Mockito.lenient().when(reportRepository.save(any(Report.class))).thenReturn(savedReport);
      Mockito.lenient().when(savedReport.getReportFilters()).thenReturn(existingFilters);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      // updates the structure
      assertThat(result.getReportFilters()).isEqualTo(existingFilters);
      assertThat(existingFilters).hasSize(1);
    }

    @Test
    void editReport_should_upsert_filters_included_in_request() {
      FilterCode filterCode = mock(FilterCode.class);
      ArrayList<ReportFilter> existingFilters = new ArrayList<>();
      existingFilters.add(new ReportFilter(100L, filterCode));
      existingFilters.add(new ReportFilter(101L, filterCode));

      Mockito.lenient().when(reportRepository.save(any(Report.class))).thenReturn(savedReport);
      Mockito.lenient().when(savedReport.getReportFilters()).thenReturn(existingFilters);

      ReportFilter mockReportFilter = mock(ReportFilter.class);
      Mockito.lenient()
          .when(reportFilterBuilder.build(any(UpsertFilterRequest.class), any(Report.class)))
          .thenReturn(mockReportFilter);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(reportFilterBuilder).build(request.filterRequests().getFirst(), savedReport);
    }
  }

  @Nested
  class DeleteReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;

    private Report savedReport;
    private ReportId reportId = new ReportId(reportUid, dataSourceUid);

    @BeforeEach
    void setup() {
      savedReport = mock(Report.class);

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(Optional.of(savedReport));
    }

    @Test
    void deleteReport_should_delete_and_return_report_when_all_inputs_are_valid() {
      service.deleteReport(reportId);

      verify(reportRepository).findById(reportId);
      verify(reportRepository).delete(any(Report.class));
    }

    @Test
    void deleteReport_should_delete_and_throws_when_unknown_report() {
      when(reportRepository.findById(reportId)).thenReturn(Optional.ofNullable(null));

      assertThatThrownBy(() -> service.deleteReport(reportId))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining("Report not found for Report UID: 1 and Data Source UID: 2");

      verify(reportRepository).findById(reportId);
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
                  List.of(
                      FilterValue.builder()
                          .id(47L)
                          .sequenceNumber(1)
                          .valueType("CLAUSE")
                          .columnUid(9L)
                          .operator("EQUALS")
                          .valueTxt("value1")
                          .build()),
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
      assertThat(config.group()).isEqualTo(ReportGroup.PRIVATE);
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
    void getReport_should_throw_when_library_not_found() {
      Report report = mock(Report.class);

      Mockito.lenient().when(report.getReportLibrary()).thenReturn(null);
      ReportId id = new ReportId(reportUid, dataSourceUid);
      when(reportRepository.findById(id)).thenReturn(Optional.of(report));

      assertThatThrownBy(() -> service.getReport(reportUid, dataSourceUid))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("No library found for this report");
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
    void getReportRunner_should_return_sas_when_report_has_no_library() {
      ReportId reportId = new ReportId(reportUid, dataSourceUid);
      Report report = mockReport(reportId, "python", "nbs_ods.PHCDemographic", List.of());

      when(report.getReportLibrary()).thenReturn(null);

      String runner = service.getReportRunner(reportUid, dataSourceUid);

      assertThat(runner).isEqualTo("sas");
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
              "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]",
              null,
              null,
              null,
              null);
      try (MockedConstruction<ReportSpecBuilder> specBuilderMock =
          mockConstruction(
              ReportSpecBuilder.class,
              (builder, context) -> when(builder.build()).thenReturn(spec))) {
        when(reportExecutionClient.post()).thenReturn(requestBodyUriSpec);
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

        ReportExecutionResult response = service.executeReport(request);

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

      assertThatThrownBy(() -> service.executeReport(request))
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

      assertThatThrownBy(() -> service.executeReport(request))
          .isInstanceOf(NotFoundException.class)
          .hasMessage("Report not found for Report UID: 1 and Data Source UID: 2");
    }
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
