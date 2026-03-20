package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.ReportConfiguration;
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

  @PostMapping("/execute")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<String> executeReport(@RequestBody ReportExecutionRequest request) {
    // TODO: validate request // NOSONAR
    return reportService.executeReport(request);
  }
}
