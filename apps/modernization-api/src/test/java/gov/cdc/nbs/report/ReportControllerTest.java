package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.ForbiddenException;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.ReportConstants.ReportGroup;
import gov.cdc.nbs.report.models.AdminReportRequest;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.LibraryExecutionResult;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportExecutionResult;
import gov.cdc.nbs.repository.ReportRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

  @Mock private ReportService service;
  @Mock private ReportFetcher reportFetcher;
  @Mock private ReportExecutionServiceClient reportExecutionClient;
  @Mock private ReportRepository reportRepository;

  @InjectMocks private ReportController controller;

  @Nested
  class CreateReport {
    @Test
    void createReport_should_return_created_report_response() {
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Test Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Description");
      Report expectedReport = mock(Report.class);
      NbsUserDetails user = mock(NbsUserDetails.class);

      when(service.createReport(request, user)).thenReturn(expectedReport);

      ResponseEntity<ReportId> response = controller.createReport(user, request);

      assertEquals(expectedReport.getId(), response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createReport_should_return_422_exception_when_data_source_not_found() {
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Test Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Description");
      NbsUserDetails user = mock(NbsUserDetails.class);

      String errorMsg = "No data source found for ID " + request.dataSourceId();

      when(service.createReport(request, user)).thenThrow(new IllegalArgumentException(errorMsg));

      assertThatThrownBy(() -> controller.createReport(user, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void createReport_should_return_422_when_report_library_invalid() {
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Test Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Description");
      NbsUserDetails user = mock(NbsUserDetails.class);

      String errorMsg = "No report library found for ID " + request.libraryId();

      when(service.createReport(request, user)).thenThrow(new IllegalArgumentException(errorMsg));

      assertThatThrownBy(() -> controller.createReport(user, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(errorMsg);
    }
  }

  @Nested
  class EditReport {
    @Test
    void editReport_should_return_updated_report_response() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Updated Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Updated Description");
      Report expectedReport = mock(Report.class);
      NbsUserDetails user = mock(NbsUserDetails.class);
      ReportId reportId = new ReportId(reportUid, dataSourceUid);

      when(service.editReport(request, user, reportId)).thenReturn(expectedReport);

      ResponseEntity<ReportId> response =
          controller.editReport(user, reportUid, dataSourceUid, request);

      assertEquals(expectedReport.getId(), response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void editReport_should_return_422_exception_when_data_source_not_found() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Updated Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Updated Description");
      NbsUserDetails user = mock(NbsUserDetails.class);
      ReportId reportId = new ReportId(reportUid, dataSourceUid);

      String errorMsg = "No data source found for ID " + request.dataSourceId();

      when(service.editReport(request, user, reportId))
          .thenThrow(new IllegalArgumentException(errorMsg));

      assertThatThrownBy(() -> controller.editReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void editReport_should_return_422_when_report_library_invalid() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;
      AdminReportRequest request =
          new AdminReportRequest(
              2L,
              3L,
              "Updated Report",
              "SEC",
              0L,
              ReportConstants.ReportGroup.REPORTING_FACILITY,
              Collections.emptyList(),
              "Updated Description");
      NbsUserDetails user = mock(NbsUserDetails.class);
      ReportId reportId = new ReportId(reportUid, dataSourceUid);

      String errorMsg = "No report library found for ID " + request.libraryId();

      when(service.editReport(request, user, reportId))
          .thenThrow(new IllegalArgumentException(errorMsg));

      assertThatThrownBy(() -> controller.editReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(errorMsg);
    }
  }

  @Nested
  class DeleteReport {
    @Test
    void deleteReport_should_return_report_idresponse() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;
      ReportId reportId = new ReportId(reportUid, dataSourceUid);

      ResponseEntity<ReportId> response = controller.deleteReport(reportUid, dataSourceUid);

      assertEquals(reportId, response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteReport_should_return_404_status_code_when_report_not_found() {
      long reportUid = 1L;
      long dataSourceUid = 2L;
      ReportId reportId = new ReportId(reportUid, dataSourceUid);
      String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

      doThrow(new NotFoundException(errorMsg)).when(service).deleteReport(reportId);

      assertThatThrownBy(() -> controller.deleteReport(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining(errorMsg);
    }
  }

  @Nested
  class SaveReport {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    private final long userId = 48930L;

    private final NbsUserDetails user = mock(NbsUserDetails.class);
    private final Report report = mock(Report.class);

    @BeforeEach
    void setUp() {
      when(user.getId()).thenReturn(userId);
      when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      when(report.getId()).thenReturn(reportId);
      when(report.getOwnerUid()).thenReturn(userId);
      when(report.getShared()).thenReturn('P');

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(java.util.Optional.of(report));
    }

    @Test
    void saveReport_should_throw_404_if_report_not_found() {
      when(service.getReportNotFoundText(reportId)).thenCallRealMethod();

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      when(reportRepository.findById(reportId)).thenReturn(java.util.Optional.empty());

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining(
              "Report not found for Report UID: "
                  + reportUid
                  + " and Data Source UID: "
                  + dataSourceUid);
    }

    @Test
    void saveReport_should_throw_403_if_user_is_not_owner_of_report() {
      when(user.getId()).thenReturn(54321L);
      when(report.getOwnerUid()).thenReturn(12345L);

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("Only report owners can save reports");
    }

    @Test
    void saveReport_should_throw_403_if_user_does_not_have_permission_to_edit_private_report() {
      when(report.getShared()).thenReturn('P');
      when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to save PRIVATE reports");
    }

    @Test
    void saveReport_should_throw_403_if_user_does_not_have_permission_to_edit_public_report() {
      when(report.getShared()).thenReturn('S');
      when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to save PUBLIC reports");
    }

    @Test
    void
        saveReport_should_throw_403_if_user_does_not_have_permission_to_edit_reporting_facility_report() {
      when(report.getShared()).thenReturn('R');

      when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to save REPORTING_FACILITY reports");
    }

    @Test
    void saveReport_should_throw_422_if_report_is_template() {
      when(report.getShared()).thenReturn('T');

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Template reports cannot be updated using 'save'");
    }
  }

  @Nested
  class GetReport {
    @Test
    void getReport_should_return_report_configuration_response() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;
      Long ownerUid = 0L;
      ReportGroup group = ReportGroup.PUBLIC;
      String sectionCd = "1002";

      DataSource dataSourceEntity = mock(DataSource.class);
      ReportLibrary reportLibraryEntity = mock(ReportLibrary.class);

      BasicFilterConfiguration basicFilterConfig = mock(BasicFilterConfiguration.class);
      AdvancedFilterConfiguration advancedFilterConfig = mock(AdvancedFilterConfiguration.class);
      List<ReportColumn> columns = List.of(mock(ReportColumn.class));
      ReportConfiguration reportConfig =
          new ReportConfiguration(
              new ReportDataSource(dataSourceEntity),
              new Library(reportLibraryEntity),
              "Report Title",
              "Report description prose",
              ownerUid,
              group,
              sectionCd,
              List.of(basicFilterConfig),
              advancedFilterConfig,
              columns,
              null,
              null);
      when(reportFetcher.getReport(reportUid, dataSourceUid)).thenReturn(reportConfig);

      ResponseEntity<ReportConfiguration> response =
          controller.getReportConfiguration(reportUid, dataSourceUid);

      assertEquals(reportConfig, response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getReport_should_return_404_status_code_when_report_not_found() {
      long reportUid = 1L;
      long dataSourceUid = 2L;
      String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

      when(reportFetcher.getReport(reportUid, dataSourceUid))
          .thenThrow(new NotFoundException(errorMsg));

      assertThatThrownBy(() -> controller.getReportConfiguration(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining(errorMsg);
    }
  }

  @Nested
  class GetReportRunner {
    @Test
    void getReportRunner_should_return_report_lib_runner() {
      Long reportUid = 1L;
      Long dataSourceUid = 2L;

      when(reportFetcher.getReportRunner(reportUid, dataSourceUid)).thenReturn("python");

      ResponseEntity<String> response = controller.getReportRunner(reportUid, dataSourceUid);

      assertEquals("python", response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getReportRunner_should_return_404_status_code_when_report_not_found() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

      when(reportFetcher.getReportRunner(reportUid, dataSourceUid))
          .thenThrow(new NotFoundException(errorMsg));

      assertThatThrownBy(() -> controller.getReportRunner(reportUid, dataSourceUid))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void getReportRunner_should_return_422_status_code_when_report_has_no_library() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      ReportId reportId = new ReportId(reportUid, dataSourceUid);

      String errorMsg = "No report library exists for report " + reportId;

      when(reportFetcher.getReportRunner(reportUid, dataSourceUid))
          .thenThrow(new UnprocessableEntityException(errorMsg));

      assertThatThrownBy(() -> controller.getReportRunner(reportUid, dataSourceUid))
          .isInstanceOf(UnprocessableEntityException.class)
          .hasMessageContaining("No report library exists for report " + reportId);
    }
  }

  @Nested
  class ExportReport {
    @Test
    void exportReport_should_return_executed_report() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("123-123-123", 27L, "EQ", "47");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("124-124-124", 31L, "EQ", "35001");
      AdvancedQuery.RuleGroup connector =
          new AdvancedQuery.RuleGroup(
              "125-125-125", ReportConstants.QueryCombinators.OR, List.of(rule1, rule2));
      AdvancedFilterRequest advancedFilter = new AdvancedFilterRequest(3L, connector);

      BasicFilterRequest basicFilter = new BasicFilterRequest(4L, Arrays.asList("test"), true);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              Arrays.asList(27L, 31L),
              null,
              List.of(basicFilter),
              advancedFilter);

      when(reportExecutionClient.executeReport(request)).thenReturn(getReportExecutionResponse());

      ResponseEntity<ReportExecutionResult> response = controller.exportReport(request);
      assertEquals(getReportExecutionResponse(), response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void exportReport_should_return_400_status_code_when_report_not_found() {
      long reportUid = 1L;
      long dataSourceUid = 2L;
      String errorMsg = "Report not found for Report UID: 1 and Data Source UID: 2";

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              Arrays.asList(27L, 31L),
              null,
              List.of(new BasicFilterRequest(10066724L, List.of("35001"), false)),
              null);

      when(reportExecutionClient.executeReport(request)).thenThrow(new NotFoundException(errorMsg));

      assertThatThrownBy(() -> controller.exportReport(request))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void exportReport_should_return_501_status_code_when_report_not_implemented() {
      long reportUid = 1L;
      long dataSourceUid = 2L;
      String errorMsg = "Report not implemented for python";

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              Arrays.asList(27L, 31L),
              null,
              List.of(new BasicFilterRequest(10066724L, List.of("35001"), false)),
              null);

      when(reportExecutionClient.executeReport(request))
          .thenThrow(new NotImplementedException(errorMsg));

      assertThatThrownBy(() -> controller.exportReport(request))
          .isInstanceOf(NotImplementedException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void exportReport_should_return_500_status_code_when_unexpected_exception() {
      long reportUid = 1L;
      long dataSourceUid = 2L;
      String errorMsg = "Uh oh!";

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              Arrays.asList(27L, 31L),
              null,
              List.of(new BasicFilterRequest(10066724L, List.of("35001"), false)),
              null);

      when(reportExecutionClient.executeReport(request)).thenThrow(new RuntimeException(errorMsg));

      assertThatThrownBy(() -> controller.exportReport(request))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining(errorMsg);
    }

    @Test
    void exportReport_should_return_422_status_code_when_report_not_export() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              false,
              Arrays.asList(27L, 31L),
              null,
              List.of(new BasicFilterRequest(10066724L, List.of("35001"), false)),
              null);

      assertThatThrownBy(() -> controller.exportReport(request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("isExport must be true when exporting a report");
    }
  }

  @Nested
  class RunReport {
    @Test
    void runReport_should_return_executed_report() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      AdvancedQuery.Rule rule1 = new AdvancedQuery.Rule("123-123-123", 27L, "EQ", "47");
      AdvancedQuery.Rule rule2 = new AdvancedQuery.Rule("124-124-124", 31L, "EQ", "35001");
      AdvancedQuery.RuleGroup connector =
          new AdvancedQuery.RuleGroup(
              "125-125-125", ReportConstants.QueryCombinators.OR, List.of(rule1, rule2));
      AdvancedFilterRequest advancedFilter = new AdvancedFilterRequest(3L, connector);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              false,
              Arrays.asList(27L, 31L),
              null,
              List.of(),
              advancedFilter);

      when(reportExecutionClient.executeReport(request)).thenReturn(getReportExecutionResponse());

      ResponseEntity<ReportExecutionResult> response = controller.runReport(request);
      assertEquals(getReportExecutionResponse(), response.getBody());
      assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void runReport_should_return_422_status_code_when_report_not_run() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              Arrays.asList(27L, 31L),
              null,
              List.of(new BasicFilterRequest(10066724L, List.of("35001"), false)),
              null);

      assertThatThrownBy(() -> controller.runReport(request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("isExport must be false when running a report");
    }
  }

  private ReportExecutionResult getReportExecutionResponse() {
    return new ReportExecutionResult(
        new LibraryExecutionResult(
            "table",
            "report_uid,data_source_uid,add_reason_cd,add_time,add_user_uid,desc_txt,effective_from_time,effective_to_time,report_title,report_type_codestatus_time",
            "result header",
            "result subheader",
            "result description"),
        "SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]",
        LocalDateTime.of(2025, Month.MAY, 5, 12, 23));
  }
}
