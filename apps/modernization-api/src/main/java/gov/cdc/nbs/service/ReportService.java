package gov.cdc.nbs.service;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportModel;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.HashMap;
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
    ReportId fetchedReportId;
    if (optionalReport.isPresent()) {
      fetchedReport = optionalReport.get();
      fetchedReportId = fetchedReport.getId();
    } else {
      throw new NotFoundException(
          String.format(
              "Report not found for Report UID: %d and Data Source UID: %d",
              reportUid, dataSourceUid));
    }

    return new ReportModel(
        createReportId(fetchedReportId.getReportUid(), fetchedReportId.getDataSourceUid()),
        fetchedReport.getReportLibrary().getRunner());
  }

  private HashMap<String, Long> createReportId(Long reportId, Long dataSourceId) {
    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportId", reportId);
    idMap.put("dataSourceId", dataSourceId);
    return idMap;
  }
}
