package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.ForbiddenException;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.ReportRepository;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/nbs/api/report")
@ConditionalOnProperty(
    prefix = "nbs.ui.features.report.execution",
    name = "enabled",
    havingValue = "true")
public class ReportController {
  private static final System.Logger LOGGER = System.getLogger(ReportController.class.getName());

  private final ReportService reportService;
  private final ReportFetcher reportFetcher;
  private final ReportRepository reportRepository;
  private final ReportExecutionServiceClient reportExecutionClient;

  private final Clock clock;

  public ReportController(
      ReportService reportService,
      ReportExecutionServiceClient reportExecutionClient,
      ReportRepository reportRepository,
      ReportFetcher reportFetcher,
      Clock clock) {
    this.reportService = reportService;
    this.reportRepository = reportRepository;
    this.reportExecutionClient = reportExecutionClient;
    this.reportFetcher = reportFetcher;
    this.clock = clock;
  }

  @PostMapping("/configuration")
  @PreAuthorize("hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportId> createReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @Valid @RequestBody AdminReportRequest request) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "CREATE report request received from user %s".formatted(user.getId()));

    Report report = reportService.createReport(request, user);
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @PutMapping("/configuration/{reportUid}/{dataSourceUid}")
  @PreAuthorize("hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportId> editReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @Valid @RequestBody AdminReportRequest request) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "EDIT report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    Report report = reportService.editReport(request, user, new ReportId(reportUid, dataSourceUid));
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @PutMapping("/configuration/{reportUid}/{dataSourceUid}/save")
  public ResponseEntity<ReportId> saveReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @Valid @RequestBody ReportExecutionRequest request) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "SAVE report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    Report existingReport = reportRepository.findById(reportId).orElse(null);
    if (existingReport == null) {
      throw new NotFoundException(reportService.getReportNotFoundText(reportId));
    }

    //  Only the report's owner should have permission to overwrite it
    //  We might consider investigating into creating a custom pre-authorizer for this sort of
    //  authorization check, should we ever need ownership permissions beyond this endpoint
    if (!existingReport.getOwnerUid().equals(user.getId())) {
      throw new ForbiddenException("Only report owners can save reports");
    }

    ReportConstants.ReportGroup reportGroup =
        ReportConstants.dbCharToReportGroup(existingReport.getShared());

    //  While long-term we likely want to map permissions 1:1 with report groups,
    //  this is currently how it works in 6, so we'll leave it be for now.
    switch (reportGroup) {
      case PUBLIC, REPORTING_FACILITY:
        Permission publicPermission =
            new Permission(
                ReportConstants.Permissions.EDITREPORTPUBLIC,
                ReportConstants.Permissions.REPORTINGOBJECT);
        Permission reportingFacilityPermission =
            new Permission(
                ReportConstants.Permissions.EDITREPORTREPORTINGFACILITY,
                ReportConstants.Permissions.REPORTINGOBJECT);

        if (!user.hasPermission(publicPermission)
            && !user.hasPermission(reportingFacilityPermission)) {
          throw new ForbiddenException(
              "User does not have permission to save " + reportGroup.name() + " reports");
        }
        break;
      case PRIVATE, TEMPLATE:
        Permission privatePermission =
            new Permission(
                ReportConstants.Permissions.EDITREPORTPRIVATE,
                ReportConstants.Permissions.REPORTINGOBJECT);

        if (!user.hasPermission(privatePermission)) {
          throw new ForbiddenException(
              "User does not have permission to save " + reportGroup.name() + " reports");
        }
        break;
    }

    Report report = reportService.saveReport(request, existingReport);
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @PostMapping("/configuration/{reportUid}/{dataSourceUid}/save-as")
  public ResponseEntity<ReportId> saveAsReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @Valid @RequestBody SaveAsReportRequest request) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "SAVE AS report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    String authOperationType;
    ReportConstants.ReportGroup reportGroup = request.group();

    authOperationType =
        switch (reportGroup) {
          case PUBLIC -> ReportConstants.Permissions.CREATEREPORTPUBLIC;
          case PRIVATE -> ReportConstants.Permissions.CREATEREPORTPRIVATE;
          case REPORTING_FACILITY -> ReportConstants.Permissions.CREATEREPORTREPORTINGFACILITY;
          case TEMPLATE ->
              throw new IllegalArgumentException(
                  "Template reports cannot be created using 'saveAs'");
        };

    Permission permission =
        new Permission(authOperationType, ReportConstants.Permissions.REPORTINGOBJECT);

    if (!user.hasPermission(permission)) {
      throw new ForbiddenException(
          "User does not have permission to create " + reportGroup.name() + " reports");
    }

    Report report =
        reportService.saveAsReport(request, user, new ReportId(reportUid, dataSourceUid));
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @GetMapping("/configuration/{reportUid}/{dataSourceUid}")
  @PreAuthorize(
      "hasAuthority('RUNREPORT-REPORTING') or hasAuthority('EXPORTREPORT-REPORTING') or hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportConfiguration> getReportConfiguration(
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @AuthenticationPrincipal NbsUserDetails user) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "GET report config request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    ReportConfiguration reportConfigResponse = reportFetcher.getReport(reportUid, dataSourceUid);
    return new ResponseEntity<>(reportConfigResponse, HttpStatus.OK);
  }

  @GetMapping("/runner/{reportUid}/{dataSourceUid}")
  @PreAuthorize(
      "hasAuthority('RUNREPORT-REPORTING') or hasAuthority('EXPORTREPORT-REPORTING') or hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<String> getReportRunner(
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @AuthenticationPrincipal NbsUserDetails user) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "GET report runner received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    String runner = reportFetcher.getReportRunner(reportUid, dataSourceUid);
    return new ResponseEntity<>(runner, HttpStatus.OK);
  }

  // Eventually, this will also need to support users deleting their own reports,
  // but right now that UI flow still lives in 6
  @DeleteMapping("/configuration/{reportUid}/{dataSourceUid}")
  @PreAuthorize("hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportId> deleteReport(
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @AuthenticationPrincipal NbsUserDetails user) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "DELETE report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), reportUid, dataSourceUid));

    ReportId reportId = new ReportId(reportUid, dataSourceUid);
    reportService.deleteReport(reportId);
    return new ResponseEntity<>(reportId, HttpStatus.OK);
  }

  @ApiResponse(
      responseCode = "200",
      description = "Run a report given an execution request and return a CSV plus metadata.",
      headers = {
        @Header(name = "X-Report-Description", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Context-Header", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Timestamp", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Query", schema = @Schema(type = "string"))
      },
      content =
          @Content(
              mediaType = "text/csv",
              schema = @Schema(type = "string"),
              examples =
                  @io.swagger.v3.oas.annotations.media.ExampleObject(
                      name = "Example CSV",
                      value =
                          "column1,column2,column3\nvalue1,value2,value3\nvalue4,value5,value6")))
  @PostMapping(value = "/run", produces = "text/csv")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public void runReport(
      @Valid @RequestBody ReportExecutionRequest request,
      @AuthenticationPrincipal NbsUserDetails user,
      HttpServletResponse response) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "RUN report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), request.reportUid(), request.dataSourceUid()));

    if (request.isExport())
      throw new IllegalArgumentException("isExport must be false when running a report");
    reportExecutionClient.executeReport(request, handleReportRes(response));
  }

  @ApiResponse(
      responseCode = "200",
      description = "Export a report given an execution request and return a CSV plus metadata.",
      headers = {
        @Header(name = "X-Report-Description", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Context-Header", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Timestamp", schema = @Schema(type = "string")),
        @Header(name = "X-Report-Query", schema = @Schema(type = "string"))
      },
      content =
          @Content(
              mediaType = "text/csv",
              schema = @Schema(type = "string"),
              examples =
                  @io.swagger.v3.oas.annotations.media.ExampleObject(
                      name = "Example CSV",
                      value =
                          "column1,column2,column3\nvalue1,value2,value3\nvalue4,value5,value6")))
  @PostMapping(value = "/export", produces = "text/csv")
  @PreAuthorize("hasAuthority('EXPORTREPORT-REPORTING')")
  public void exportReport(
      @Valid @RequestBody ReportExecutionRequest request,
      @AuthenticationPrincipal NbsUserDetails user,
      HttpServletResponse response) {
    LOGGER.log(
        System.Logger.Level.TRACE,
        "EXPORT report request received from user %s for report %s and datasource %s"
            .formatted(user.getId(), request.reportUid(), request.dataSourceUid()));

    if (!request.isExport())
      throw new IllegalArgumentException("isExport must be true when exporting a report");
    reportExecutionClient.executeReport(request, handleReportRes(response));
  }

  private BiConsumer<RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse, ReportSpec>
      handleReportRes(HttpServletResponse responseToSet) {
    return (RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse resp,
        ReportSpec reportSpec) -> {
      HttpHeaders headers = resp.getHeaders();

      String contextHeader = headers.getFirst("X-Report-Context-Header");
      String description = headers.getFirst("X-Report-Description");

      if (contextHeader != null) {
        responseToSet.setHeader("X-Report-Context-Header", contextHeader);
      }

      if (description != null) {
        responseToSet.setHeader("X-Report-Description", description);
      }

      responseToSet.setHeader("X-Report-Timestamp", LocalDateTime.now(this.clock).toString());
      responseToSet.setHeader("X-Report-Query", reportSpec.subsetQuery());

      responseToSet.setContentType("text/csv");
      responseToSet.setCharacterEncoding("UTF-8");

      try (InputStream inputStream = resp.getBody()) {
        inputStream.transferTo(responseToSet.getOutputStream());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
