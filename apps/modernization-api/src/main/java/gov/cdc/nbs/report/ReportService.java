package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.mappers.DataSourceColumnMapper;
import gov.cdc.nbs.report.mappers.FilterCodeMapper;
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
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
            report -> {
              List<FilterConfiguration> filters =
                  report.getReportFilters().stream()
                      .map(
                          dbReportFilter -> {
                            DataSourceColumn column = null;

                            if (dbReportFilter.getDataSourceColumn() != null) {
                              column =
                                  DataSourceColumnMapper.fromDb(
                                      dbReportFilter.getDataSourceColumn());
                            }

                            FilterCode filterCode =
                                FilterCodeMapper.fromDb(dbReportFilter.getFilterCode());
                            List<FilterValue> filterValues =
                                dbReportFilter.getFilterValues().stream()
                                    .map(FilterValueMapper::fromDb)
                                    .toList();

                            return new FilterConfiguration(
                                dbReportFilter.getId(), column, filterCode, filterValues);
                          })
                      .toList();

              return new ReportConfiguration(report.getReportLibrary().getRunner(), filters);
            })
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

    ReportSpec reportSpec = specBuilder.build();
    return reportExecutionClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.APPLICATION_JSON)
        .body(reportSpec)
        .retrieve()
        .toEntity(String.class);
  }
}
