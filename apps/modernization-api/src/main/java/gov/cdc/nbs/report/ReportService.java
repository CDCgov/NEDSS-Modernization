package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.List;
import java.util.stream.Collectors;
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
                            DataSourceColumn column =
                                new DataSourceColumn(
                                    dbReportFilter.getDataSourceColumn().getId(),
                                    dbReportFilter.getDataSourceColumn().getColumnMaxLength(),
                                    dbReportFilter.getDataSourceColumn().getColumnName(),
                                    dbReportFilter.getDataSourceColumn().getColumnTitle(),
                                    dbReportFilter.getDataSourceColumn().getColumnSourceTypeCode(),
                                    dbReportFilter.getDataSourceColumn().getDescTxt(),
                                    dbReportFilter.getDataSourceColumn().getDisplayable(),
                                    dbReportFilter.getDataSourceColumn().getFilterable(),
                                    dbReportFilter.getDataSourceColumn().getStatusCd(),
                                    dbReportFilter.getDataSourceColumn().getStatusTime());

                            FilterCode filterCode =
                                new FilterCode(
                                    dbReportFilter.getFilterCode().getId(),
                                    dbReportFilter.getFilterCode().getCodeTable(),
                                    dbReportFilter.getFilterCode().getDescTxt(),
                                    dbReportFilter.getFilterCode().getCode(),
                                    dbReportFilter.getFilterCode().getFilterCodeSetName(),
                                    dbReportFilter.getFilterCode().getFilterType(),
                                    dbReportFilter.getFilterCode().getFilterName());
                            List<FilterValue> filterValues =
                                dbReportFilter.getFilterValues().stream()
                                    .map(
                                        dbFilterValue ->
                                            new FilterValue(
                                                dbFilterValue.getId(),
                                                dbFilterValue.getSequenceNumber(),
                                                dbFilterValue.getValueType(),
                                                dbFilterValue.getColumnUid(),
                                                dbFilterValue.getOperator(),
                                                dbFilterValue.getValueTxt()))
                                    .collect(Collectors.toList());

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
