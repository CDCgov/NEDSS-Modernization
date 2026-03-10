package gov.cdc.nbs.service;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;

  public ReportService(final ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public Report getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    return reportRepository
        .findById(id)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "Report not found for Report UID: %d and Data Source UID: %d",
                        reportUid, dataSourceUid)));
  }
}
