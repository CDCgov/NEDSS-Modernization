package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.mappers.BasicFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.utils.DataSourceNameUtils;
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
  private final DataSourceNameUtils dataSourceNameUtils;

  public ReportService(
      final ReportRepository reportRepository,
      RestClient reportExecutionClient,
      final DataSourceNameConfiguration dataSourceNameConfig) {
    this.reportRepository = reportRepository;
    this.reportExecutionClient = reportExecutionClient;
    this.dataSourceNameUtils = new DataSourceNameUtils(dataSourceNameConfig);
  }

  public ReportConfiguration getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    return reportRepository
        .findById(id)
        .map(
            report -> {
              List<BasicFilterConfiguration> basicFilters =
                  report.getReportFilters().stream()
                      .filter(f -> f.getFilterCode().getFilterType().startsWith("BAS_"))
                      .map(BasicFilterConfigurationMapper::fromReportFilter)
                      .toList();

              List<ReportColumn> reportColumns = null;
              List<DataSourceColumn> dataSourceColumns =
                  report.getDataSource().getDataSourceColumns();
              if (dataSourceColumns != null) {
                reportColumns =
                    dataSourceColumns.stream()
                        .map(ReportColumnMapper::fromDataSourceColumn)
                        .toList();
              }

              return new ReportConfiguration(
                  report.getReportLibrary().getRunner(),
                  new ReportDataSource(report.getDataSource()),
                  new Library(report.getReportLibrary()),
                  report.getReportTitle(),
                  basicFilters,
                  null, // advanced filter
                  reportColumns);
            })
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "Report not found for Report UID: %d and Data Source UID: %d",
                        reportUid, dataSourceUid)));
  }

  public ResponseEntity<ReportResult> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }

    if (request.columnUids() != null && request.columnUids().isEmpty()) {
      throw new UnprocessableEntityException(
          "Column UIDs cannot be empty - if omitting reportColumns, use `null`");
    }

    ReportSpecBuilder specBuilder =
        new ReportSpecBuilder(request, reportConfigResponse, dataSourceNameUtils);
    ReportSpec reportSpec = specBuilder.build();

    return reportExecutionClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.APPLICATION_JSON)
        .body(reportSpec)
        .retrieve()
        .toEntity(ReportResult.class);
  }
}
