package gov.cdc.nbs.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
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

  public ReportConfiguration getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    Optional<Report> optionalReport = reportRepository.findById(id);
    Report fetchedReport;
    ReportId fetchedReportId;

    if (optionalReport.isPresent()) {
      fetchedReport = optionalReport.get();
      fetchedReportId = fetchedReport.getId();

      return new ReportConfiguration(
          createReportId(fetchedReportId.getReportUid(), fetchedReportId.getDataSourceUid()),
          fetchedReport.getReportLibrary().getRunner());
    }

    throw new NotFoundException(
        String.format(
            "Report not found for Report UID: %d and Data Source UID: %d",
            reportUid, dataSourceUid));
  }

  public ResponseEntity<String> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (reportConfigResponse != null) {
      if (Objects.equals(reportConfigResponse.runner(), "python")) {
        ObjectNode reportSpec = getReportSpec();
        return reportExecutionClient
            .post()
            .uri("/report/execute")
            .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
            .body(reportSpec)
            .retrieve()
            .toEntity(String.class);
      }
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }
    throw new NotFoundException(
        String.format(
            "Report not found for Report UID: %d and Data Source UID: %d",
            reportUid, dataSourceUid));
  }

  private ObjectNode getReportSpec() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode objectNode = mapper.createObjectNode();

    objectNode.put("version", 1);
    objectNode.put("is_export", true);
    objectNode.put("is_builtin", true);
    objectNode.put("report_title", "Test report");
    objectNode.put("library_name", "nbs_custom");
    objectNode.put("data_source_name", "[NBS_ODSE].[dbo].[Report]");
    objectNode.put("subset_query", "SELECT * FROM [NBS_ODSE].[dbo].[Report]");
    objectNode.putNull("time_range");

    return objectNode;
  }

  private HashMap<String, Long> createReportId(long reportUid, Long dataSourceUid) {
    HashMap<String, Long> idMap = new HashMap<>();
    idMap.put("reportUid", reportUid);
    idMap.put("dataSourceUid", dataSourceUid);
    return idMap;
  }
}
