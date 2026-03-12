package gov.cdc.nbs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.model.ReportConfigurationResponse;
import gov.cdc.nbs.model.ReportExecutionRequest;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final RestClient reportExecutionClient;

  public ReportService(final ReportRepository reportRepository, RestClient reportExecutionClient) {
    this.reportRepository = reportRepository;
    this.reportExecutionClient = reportExecutionClient;
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

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        json.put("version", 1);
        json.put("is_export", true);
        json.put("is_builtin", true);
        json.put("report_title", "Test report");
        json.put("library_name", "nbs_custom");
        json.put("data_source_name", "[NBS_ODSE].[dbo].[Report]");
        json.put("subset_query", "SELECT * FROM [NBS_ODSE].[dbo].[Report]");
        json.putNull("time_range");

        return reportExecutionClient
            .post()
            .uri("/report/execute")
            .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
            .body(json)
            .retrieve()
            .toEntity(String.class);
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
