package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.mappers.FilterDefaultValueMapper;
import gov.cdc.nbs.report.mappers.FilterTypeMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.FilterDefaultValue;
import gov.cdc.nbs.report.models.FilterType;
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
              List<FilterConfiguration> filters =
                  report.getReportFilters().stream()
                      .map(
                          dbReportFilter -> {
                            Long columnUid = null;

                            if (dbReportFilter.getDataSourceColumn() != null) {
                              columnUid = dbReportFilter.getDataSourceColumn().getId();
                            }

                            FilterType filterType =
                                FilterTypeMapper.fromFilterCode(dbReportFilter.getFilterCode());
                            List<FilterDefaultValue> filterDefaultValues =
                                dbReportFilter.getFilterValues().stream()
                                    .map(FilterDefaultValueMapper::fromFilterValue)
                                    .toList();

                            return new FilterConfiguration(
                                dbReportFilter.getId(), columnUid, filterType, filterDefaultValues);
                          })
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
                  new ReportDataSource(report.getDataSource()),
                  new Library(report.getReportLibrary()),
                  report.getReportTitle(),
                  filters,
                  reportColumns);
            })
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "Report not found for Report UID: %d and Data Source UID: %d",
                        reportUid, dataSourceUid)));
  }

  public String getReportRunner(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    Report report =
        reportRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Report not found for Report UID: %d and Data Source UID: %d",
                            reportUid, dataSourceUid)));

    ReportLibrary reportLibrary = report.getReportLibrary();
    if (reportLibrary == null) {
      throw new UnprocessableEntityException(
          String.format("No report library exists for report %d", reportUid));
    }

    return reportLibrary.getRunner();
  }

  public ResponseEntity<ReportResult> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          String.format("Report not implemented for %s", reportConfigResponse.reportLibrary().runner()),
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
