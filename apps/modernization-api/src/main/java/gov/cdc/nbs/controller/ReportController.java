package gov.cdc.nbs.controller;

import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.model.ReportExecutionRequest;
import gov.cdc.nbs.service.ReportService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  public ResponseEntity<ReportConfigurationResponse> getReport(
      @PathVariable Long reportUid, @PathVariable Long dataSourceUid) {
    ReportConfigurationResponse reportConfigResponse =
        reportService.getReport(reportUid, dataSourceUid);
    return new ResponseEntity<>(reportConfigResponse, HttpStatus.OK);
  }

  @PostMapping("/execute")
  @PreAuthorize("hasAuthority('RUNREPORT-REPORTING')")
  public ResponseEntity<String> executeReport(@RequestBody ReportExecutionRequest request) {
    // TODO: validate request
    return reportService.executeReport(request);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFound(NotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<String> handleNotImplemented(NotImplementedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
  }
}
