package gov.cdc.nbs.controller;

import gov.cdc.nbs.model.ReportModel;
import gov.cdc.nbs.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  //    @PreAuthorize("hasAuthority('VIEWREPORTPRIVATE-REPORTING')")
  public ReportModel findReport(@PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    return reportService.getReport(reportUid, dataSourceUid);
  }
}
