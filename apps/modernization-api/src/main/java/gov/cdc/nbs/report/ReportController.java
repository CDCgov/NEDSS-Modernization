package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.ForbiddenException;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.ReportRepository;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nbs/api/report")
@ConditionalOnProperty(
    prefix = "nbs.ui.features.report.execution",
    name = "enabled",
    havingValue = "true")
public class ReportController {

  private final ReportService reportService;
  private final ReportRepository reportRepository;

  public ReportController(ReportService reportService, ReportRepository reportRepository) {
    this.reportService = reportService;
    this.reportRepository = reportRepository;
  }

  @PostMapping("/configuration")
  @PreAuthorize("hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportId> createReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @Valid @RequestBody AdminReportRequest request) {
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
    Report report = reportService.editReport(request, user, new ReportId(reportUid, dataSourceUid));
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @PutMapping("/configuration/{reportUid}/{dataSourceUid}/save")
  public ResponseEntity<ReportId> saveReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @Valid @RequestBody ReportExecutionRequest request) {
    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    Report existingReport = reportRepository.findById(reportId).orElse(null);
    //  We might consider investigating into creating a custom pre-authorizer for this sort of
    //  authorization check, should we ever need ownership permissions beyond this endpoint
    if (existingReport != null && !existingReport.getOwnerUid().equals(user.getId())) {
      throw new ForbiddenException("User does not have permission to save this report");
    }

    Report report = reportService.saveReport(request, reportId);
    return new ResponseEntity<>(report.getId(), HttpStatus.OK);
  }

  @PostMapping("/configuration/{reportUid}/{dataSourceUid}/save-as")
  //  TODO: Figure out how to handle permissions more granularly NOSONAR
  public ResponseEntity<ReportId> saveAsReport(
      @AuthenticationPrincipal NbsUserDetails user,
      @PathVariable Long reportUid,
      @PathVariable Long dataSourceUid,
      @Valid @RequestBody SaveAsReportRequest request) {
    //  @TODO: Finish implementation NOSONAR
    return null;
  }

  @GetMapping("/configuration/{reportUid}/{dataSourceUid}")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<ReportConfiguration> getReportConfiguration(
      @PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    ReportConfiguration reportConfigResponse = reportService.getReport(reportUid, dataSourceUid);
    return new ResponseEntity<>(reportConfigResponse, HttpStatus.OK);
  }

  @GetMapping("/runner/{reportUid}/{dataSourceUid}")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<String> getReportRunner(
      @PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    String runner = reportService.getReportRunner(reportUid, dataSourceUid);
    return new ResponseEntity<>(runner, HttpStatus.OK);
  }

  // Eventually, this will also need to support users deleting their own reports,
  // but right now that UI flow still lives in 6
  @DeleteMapping("/configuration/{reportUid}/{dataSourceUid}")
  @PreAuthorize("hasAuthority('REPORTADMIN-SYSTEM')")
  public ResponseEntity<ReportId> deleteReport(
      @PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    ReportId reportId = new ReportId(reportUid, dataSourceUid);
    reportService.deleteReport(reportId);
    return new ResponseEntity<>(reportId, HttpStatus.OK);
  }

  @PostMapping("/run")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<ReportExecutionResult> runReport(
      @Valid @RequestBody ReportExecutionRequest request) {
    if (request.isExport())
      throw new IllegalArgumentException("isExport must be false when running a report");

    return new ResponseEntity<>(reportService.executeReport(request), HttpStatus.OK);
  }

  @PostMapping("/export")
  @PreAuthorize("hasAuthority('EXPORTREPORT-REPORTING')")
  public ResponseEntity<ReportExecutionResult> exportReport(
      @Valid @RequestBody ReportExecutionRequest request) {
    if (!request.isExport())
      throw new IllegalArgumentException("isExport must be true when exporting a report");
    return new ResponseEntity<>(reportService.executeReport(request), HttpStatus.OK);
  }
}
