package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.repository.ReportRepository;
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
  private final ReportSpecBuilder specBuilder;

  public ReportService(
      final ReportRepository reportRepository,
      RestClient reportExecutionClient,
      ReportSpecBuilder specBuilder) {
    this.reportRepository = reportRepository;
    this.reportExecutionClient = reportExecutionClient;
    this.specBuilder = specBuilder;
  }

  public ReportConfiguration getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);
    Optional<Report> optionalReport = reportRepository.findById(id);
    Report fetchedReport;
    ReportId fetchedReportId;

    if (optionalReport.isEmpty()) {
      throw new NotFoundException(
          String.format(
              "Report not found for Report UID: %d and Data Source UID: %d",
              reportUid, dataSourceUid));
    }

    fetchedReport = optionalReport.get();
    fetchedReportId = fetchedReport.getId();

    return new ReportConfiguration(
        fetchedReportId.getReportUid(),
        fetchedReportId.getDataSourceUid(),
        fetchedReport.getReportLibrary().getRunner());
  }

  public ResponseEntity<String> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (!Objects.equals(reportConfigResponse.runner(), "python")) {
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }

    ReportSpec reportSpec = specBuilder.build();
    return reportExecutionClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
        .body(reportSpec)
        .retrieve()
        .toEntity(String.class);
  }
}
