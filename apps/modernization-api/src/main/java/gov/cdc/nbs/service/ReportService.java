package gov.cdc.nbs.service;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.model.ReportExecutionRequest;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;

  public ReportService(final ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public ReportConfigurationResponse getReport(Long reportUid, Long dataSourceUid) {
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

    return new ReportConfigurationResponse(
        createReportId(fetchedReportId.getReportUid(), fetchedReportId.getDataSourceUid()),
        fetchedReport.getReportLibrary().getRunner());
  }

  public boolean executeReport(ReportExecutionRequest request) {
    ReportConfigurationResponse reportConfigRes =
        getReport(request.reportUid(), request.dataSourceUid());

    if (reportConfigRes != null) {
      if (Objects.equals(reportConfigRes.runner(), "python")) {
        // request to report execution service
        return true;
      } else {
        // throw 501
        return false;
      }
    }
    return false;
  }

  private HashMap<String, Long> createReportId(Long reportUid, Long dataSourceUid) {
    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);
    return idMap;
  }
}
