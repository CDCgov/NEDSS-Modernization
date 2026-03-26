package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.ReportRepository;
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
    return reportRepository
        .findById(id)
        .map(
            report ->
                new ReportConfiguration(
                    report.getReportLibrary().getRunner(),
                    new ReportDataSource(report.getDataSource()),
                    new Library(report.getReportLibrary())))
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "Report not found for Report UID: %d and Data Source UID: %d",
                        reportUid, dataSourceUid)));
  }

  public ResponseEntity<String> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }

    ReportSpec reportSpec =
        specBuilder
            .setLibraryName(reportConfigResponse.reportLibrary().libraryName())
            .setDataSourceName(reportConfigResponse.dataSource().name())
            .setColumns(request.columnUids())
            .build();

    return reportExecutionClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.APPLICATION_JSON)
        .body(reportSpec)
        .retrieve()
        .toEntity(String.class);
  }
}
