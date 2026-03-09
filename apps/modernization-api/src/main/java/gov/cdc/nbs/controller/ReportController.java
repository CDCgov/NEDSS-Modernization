package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

  private final ReportRepository reportRepository;

  public ReportController(final ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @GetMapping
  public Optional<Report> findReport(ReportId reportId) {
    return reportRepository.findById(reportId);
  }
}
