package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.models.SaveAsReportRequest;
import gov.cdc.nbs.repository.ReportRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import lombok.SneakyThrows;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {
  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private ReportService service;
  @Mock private ReportFetcher reportFetcher;
  @Mock private ReportExecutionServiceClient reportExecutionClient;
  @Mock private ReportRepository reportRepository;

  @InjectMocks private ReportController controller;

  private NbsUserDetails user;

  @BeforeEach
  void setUp() {
    user = mock(NbsUserDetails.class);
  }

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

      ResponseEntity<ReportId> response = controller.deleteReport(reportUid, dataSourceUid, user);

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

      assertThatThrownBy(() -> controller.deleteReport(reportUid, dataSourceUid, user))
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

    private final Report report = mock(Report.class);

    @BeforeEach
    void setUp() {
      when(user.getId()).thenReturn(userId);
      lenient()
          .when(user.getAuthorities())
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

      lenient().when(reportRepository.findById(reportId)).thenReturn(java.util.Optional.of(report));
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
      lenient()
          .when(user.getAuthorities())
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
      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPRIVATE
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

      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.EDITREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);

      assertThatThrownBy(() -> controller.saveReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to save REPORTING_FACILITY reports");
    }
  }

  @Nested
  class SaveAsReport {
    long reportUid = 1L;
    long dataSourceUid = 2L;
    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    private final long userId = 48930L;

    private final Report existingReport = mock(Report.class);

    @BeforeEach
    void setUp() {
      when(user.getId()).thenReturn(userId);
      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      when(existingReport.getId()).thenReturn(reportId);
      when(existingReport.getOwnerUid()).thenReturn(userId);
      when(existingReport.getShared()).thenReturn('P');

      lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(java.util.Optional.of(existingReport));
    }

    @Test
    void saveAsReport_should_throw_403_if_user_does_not_have_permission_to_create_private_report() {
      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "New Report",
              "Description",
              ReportGroup.PRIVATE,
              new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null),
              "random descirption");

      assertThatThrownBy(() -> controller.saveAsReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to create PRIVATE reports");
    }

    @Test
    void saveAsReport_should_throw_403_if_user_does_not_have_permission_to_create_public_report() {
      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTREPORTINGFACILITY
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "New Report",
              "Description",
              ReportGroup.PUBLIC,
              new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null),
              "random descirption");

      assertThatThrownBy(() -> controller.saveAsReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining("User does not have permission to create PUBLIC reports");
    }

    @Test
    void
        saveAsReport_should_throw_403_if_user_does_not_have_permission_to_create_reporting_facility_report() {
      lenient()
          .when(user.getAuthorities())
          .thenReturn(
              Set.of(
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPRIVATE
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT),
                  new SimpleGrantedAuthority(
                      ReportConstants.Permissions.CREATEREPORTPUBLIC
                          + "-"
                          + ReportConstants.Permissions.REPORTINGOBJECT)));

      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "New Report",
              "Description",
              ReportGroup.REPORTING_FACILITY,
              new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null),
              "random descirption");

      assertThatThrownBy(() -> controller.saveAsReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(ForbiddenException.class)
          .hasMessageContaining(
              "User does not have permission to create REPORTING_FACILITY reports");
    }

    @Test
    void saveAsReport_should_throw_422_if_user_is_trying_to_create_template_report() {
      when(existingReport.getShared()).thenReturn('P');

      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "New Report",
              "Description",
              ReportGroup.TEMPLATE,
              new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null),
              "random descirption");

      assertThatThrownBy(() -> controller.saveAsReport(user, reportUid, dataSourceUid, request))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Template reports cannot be created using 'saveAs'");
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
          controller.getReportConfiguration(reportUid, dataSourceUid, user);

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

      assertThatThrownBy(() -> controller.getReportConfiguration(reportUid, dataSourceUid, user))
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

      ResponseEntity<String> response = controller.getReportRunner(reportUid, dataSourceUid, user);

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

      assertThatThrownBy(() -> controller.getReportRunner(reportUid, dataSourceUid, user))
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

      assertThatThrownBy(() -> controller.getReportRunner(reportUid, dataSourceUid, user))
          .isInstanceOf(UnprocessableEntityException.class)
          .hasMessageContaining("No report library exists for report " + reportId);
    }
  }

  @Nested
  class ExportReport {
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpHeaders responseHeaders = new HttpHeaders();

    private final RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse
        reportExecHttpResponse =
            mock(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse.class);

    @SneakyThrows
    @BeforeEach
    void setUp() {
      ServletOutputStream mockServlet = mock(ServletOutputStream.class);
      when(response.getOutputStream()).thenReturn(mockServlet);
      when(response.getHeader(anyString()))
          .thenAnswer(invocation -> responseHeaders.getFirst(invocation.getArgument(0)));
      doAnswer(
              invocation -> {
                responseHeaders.add(invocation.getArgument(0), invocation.getArgument(1));
                return null;
              })
          .when(response)
          .setHeader(anyString(), anyString());

      HttpHeaders reportExecHeaders = mock(HttpHeaders.class);

      when(reportExecHttpResponse.getHeaders()).thenReturn(reportExecHeaders);
      when(reportExecHttpResponse.getBody())
          .thenReturn(new ByteArrayInputStream("LOOK IM A REPORT".getBytes()));

      lenient()
          .when(reportExecHeaders.getFirst("X-Report-Context-Header"))
          .thenReturn("a test context header");
      lenient()
          .when(reportExecHeaders.getFirst("X-Report-Description"))
          .thenReturn("just a nice lil description");
    }

    @SneakyThrows
    @Test
    void exportReport_should_return_executed_report() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      ReportSpec reportSpec = mock(ReportSpec.class);
      when(reportSpec.subsetQuery()).thenReturn("SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]");

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

      doAnswer(
              invocation -> {
                BiConsumer<RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse, ReportSpec>
                    responseHandler = invocation.getArgument(1);
                responseHandler.accept(reportExecHttpResponse, reportSpec);
                return null;
              })
          .when(reportExecutionClient)
          .executeReport(eq(request), any());

      controller.exportReport(request, user, response);

      verify(response).getOutputStream();

      assertNotNull(response.getHeader("X-Report-Context-Header"));
      assertNotNull(response.getHeader("X-Report-Description"));
      assertNotNull(response.getHeader("X-Report-Timestamp"));
      assertNotNull(response.getHeader("X-Report-Query"));

      assertEquals(
          response.getHeader("X-Report-Context-Header"),
          reportExecHttpResponse.getHeaders().getFirst("X-Report-Context-Header"));
      assertEquals(
          response.getHeader("X-Report-Description"),
          reportExecHttpResponse.getHeaders().getFirst("X-Report-Description"));

      assertEquals(response.getHeader("X-Report-Timestamp"), LocalDateTime.now(clock).toString());
      assertEquals(response.getHeader("X-Report-Query"), reportSpec.subsetQuery());
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

      doThrow(new NotFoundException(errorMsg))
          .when(reportExecutionClient)
          .executeReport(eq(request), any());

      assertThatThrownBy(() -> controller.exportReport(request, user, response))
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

      doThrow(new NotImplementedException(errorMsg))
          .when(reportExecutionClient)
          .executeReport(eq(request), any());

      assertThatThrownBy(() -> controller.exportReport(request, user, response))
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

      doThrow(new RuntimeException(errorMsg))
          .when(reportExecutionClient)
          .executeReport(eq(request), any());

      assertThatThrownBy(() -> controller.exportReport(request, user, response))
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

      assertThatThrownBy(() -> controller.exportReport(request, user, response))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("isExport must be true when exporting a report");
    }
  }

  @Nested
  class RunReport {
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpHeaders responseHeaders = new HttpHeaders();

    private final RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse
        reportExecHttpResponse =
            mock(RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse.class);

    @SneakyThrows
    @BeforeEach
    void setUp() {
      ServletOutputStream mockServlet = mock(ServletOutputStream.class);
      when(response.getOutputStream()).thenReturn(mockServlet);
      when(response.getHeader(anyString()))
          .thenAnswer(invocation -> responseHeaders.getFirst(invocation.getArgument(0)));

      doAnswer(
              invocation -> {
                responseHeaders.add(invocation.getArgument(0), invocation.getArgument(1));
                return null;
              })
          .when(response)
          .setHeader(anyString(), anyString());

      HttpHeaders reportExecHeaders = mock(HttpHeaders.class);

      when(reportExecHttpResponse.getHeaders()).thenReturn(reportExecHeaders);
      when(reportExecHttpResponse.getBody())
          .thenReturn(new ByteArrayInputStream("LOOK IM A REPORT".getBytes()));

      lenient()
          .when(reportExecHeaders.getFirst("X-Report-Context-Header"))
          .thenReturn("a test context header");
      lenient()
          .when(reportExecHeaders.getFirst("X-Report-Description"))
          .thenReturn("just a nice lil description");
    }

    @SneakyThrows
    @Test
    void runReport_should_return_executed_report() {
      long reportUid = 1L;
      long dataSourceUid = 2L;

      ReportSpec reportSpec = mock(ReportSpec.class);
      when(reportSpec.subsetQuery()).thenReturn("SELECT * FROM [NBS_ODSE].[dbo].[PHCDemographic]");

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

      doAnswer(
              invocation -> {
                BiConsumer<RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse, ReportSpec>
                    responseHandler = invocation.getArgument(1);
                responseHandler.accept(reportExecHttpResponse, reportSpec);
                return null;
              })
          .when(reportExecutionClient)
          .executeReport(eq(request), any());

      controller.runReport(request, user, response);

      assertNotNull(response.getHeader("X-Report-Context-Header"));
      assertNotNull(response.getHeader("X-Report-Description"));
      assertNotNull(response.getHeader("X-Report-Timestamp"));
      assertNotNull(response.getHeader("X-Report-Query"));

      assertEquals(
          response.getHeader("X-Report-Context-Header"),
          reportExecHttpResponse.getHeaders().getFirst("X-Report-Context-Header"));
      assertEquals(
          response.getHeader("X-Report-Description"),
          reportExecHttpResponse.getHeaders().getFirst("X-Report-Description"));

      assertEquals(response.getHeader("X-Report-Timestamp"), LocalDateTime.now(clock).toString());
      assertEquals(response.getHeader("X-Report-Query"), reportSpec.subsetQuery());
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

      assertThatThrownBy(() -> controller.runReport(request, user, response))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("isExport must be false when running a report");
    }
  }
}
