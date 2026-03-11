package gov.cdc.nbs.service;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.model.ReportExecutionRequest;
import gov.cdc.nbs.repository.ReportRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final ReportExecutionClient client;

  public ReportService(final ReportRepository reportRepository, ReportExecutionClient client) {
    this.reportRepository = reportRepository;
    this.client = client;
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

  public ResponseEntity<String> executeReport(ReportExecutionRequest request) {
    ReportConfigurationResponse reportConfigRes =
        getReport(request.reportUid(), request.dataSourceUid());

    if (reportConfigRes != null) {
      if (Objects.equals(reportConfigRes.runner(), "python")) {
        JsonObject json =
            Json.createObjectBuilder()
                .add("version", "1")
                .add("is_export", true)
                .add("is_builtin", true)
                .add("report_title", "Test report")
                .add("library_name", "nbs_custom")
                .add("data_source_name", "[NBS_ODSE].[dbo].[Report]")
                .add("subset_query", "SELECT * FROM [NBS_ODSE].[dbo].[Report]")
                .build();

        return client.executeReport(json);
        // request to report execution service
      }
    }
    // return 501
    throw new NotFoundException("not found");
  }

  private HashMap<String, Long> createReportId(Long reportUid, Long dataSourceUid) {
    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);
    return idMap;
  }
}
