package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nbs/api/report")
@ConditionalOnProperty(
    prefix = "nbs.ui.features.report.execution",
    name = "enabled",
    havingValue = "true")
public class ReportController {

  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
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

  @PostMapping("/run")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<ReportResult> runReport(@RequestBody ReportExecutionRequest request) {
    // TODO: validate request // NOSONAR
    if (request.isExport())
      throw new IllegalArgumentException("isExport must be false when running a report");
    return reportService.executeReport(request);
  }

  @PostMapping("/export")
  @PreAuthorize("hasAuthority('EXPORTREPORT-REPORTING')")
  public ResponseEntity<ReportResult> exportReport(@RequestBody ReportExecutionRequest request) {
    // TODO: validate request // NOSONAR
    if (!request.isExport())
      throw new IllegalArgumentException("isExport must be true when exporting a report");
    return reportService.executeReport(request);
  }
}
