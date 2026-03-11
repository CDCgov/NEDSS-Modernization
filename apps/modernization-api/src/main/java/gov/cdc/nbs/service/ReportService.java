package gov.cdc.nbs.service;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportModel;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;

  public ReportService(final ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public ReportModel getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    Optional<Report> optionalReport = reportRepository.findById(id);
    Report fetchedReport;
    if (optionalReport.isPresent()) {
      fetchedReport = optionalReport.get();
    } else {
      throw new NotFoundException(
          String.format(
              "Report not found for Report UID: %d and Data Source UID: %d",
              reportUid, dataSourceUid));
    }

    return new ReportModel(
        fetchedReport.getId().getReportUid(),
        fetchedReport.getId().getDataSourceUid(),
        fetchedReport.getReportLibrary().getRunner());
  }
}
