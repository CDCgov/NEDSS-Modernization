package gov.cdc.nbs.controller;

import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.model.ReportExecutionRequest;
import gov.cdc.nbs.service.ReportService;
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
  @PreAuthorize("hasAuthority('VIEWREPORTPRIVATE-REPORTING')")
  public ResponseEntity<ReportConfigurationResponse> findReport(
      @PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    try {
      ReportConfigurationResponse reportRes = reportService.getReport(reportUid, dataSourceUid);
      return ResponseEntity.ok().body(reportRes);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/execute")
  // TODO: preauthorize
  public ResponseEntity<String> executeReport(@RequestBody ReportExecutionRequest request) {
    // TODO: validate request
    return reportService.executeReport(request);
  }
}
