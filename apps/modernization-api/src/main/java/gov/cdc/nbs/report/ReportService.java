package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.mappers.AdvancedFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.BasicFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final DataSourceRepository dataSourceRepository;
  private final ReportLibraryRepository reportLibraryRepository;
  private final ReportFilterRepository reportFilterRepository;
  private final DataSourceColumnRepository dataSourceColumnRepository;
  private final FilterCodeRepository filterCodeRepository;

  private final RestClient reportExecutionClient;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;

  public ReportService(
      final ReportRepository reportRepository,
      final DataSourceRepository dataSourceRepository,
      final ReportLibraryRepository reportLibraryRepository,
      final ReportFilterRepository reportFilterRepository,
      final DataSourceColumnRepository dataSourceColumnRepository,
      final FilterCodeRepository filterCodeRepository,
      RestClient reportExecutionClient,
      final DataSourceNameConfiguration dataSourceNameConfig,
      WhereClauseService whereClauseService) {
    this.reportRepository = reportRepository;
    this.dataSourceRepository = dataSourceRepository;
    this.reportLibraryRepository = reportLibraryRepository;
    this.reportFilterRepository = reportFilterRepository;
    this.dataSourceColumnRepository = dataSourceColumnRepository;
    this.filterCodeRepository = filterCodeRepository;

    this.reportExecutionClient = reportExecutionClient;
    this.dataSourceNameUtils = new DataSourceNameUtils(dataSourceNameConfig);
    this.whereClauseService = whereClauseService;
  }

  @Transactional
  public Report createReport(CreateReportRequest request, NbsUserDetails user) {
    DataSource dataSource =
        dataSourceRepository
            .findById(request.dataSourceId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "No data source found for ID " + request.dataSourceId()));

    ReportLibrary reportLibrary =
        reportLibraryRepository
            .findById(request.libraryId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "No report library found for ID " + request.libraryId()));

    Report savedReport =
        reportRepository.save(
            ReportMapper.fromCreateReportRequest(request, user, reportLibrary, dataSource));

    //    if (request.reportFilters() != null && !request.reportFilters().isEmpty()) {
    //      createReportFilters(savedReport, request.reportFilters());
    //    }

    return savedReport;
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

              AdvancedFilterConfiguration advancedFilter =
                  report.getReportFilters().stream()
                      .filter(
                          f ->
                              f.getFilterCode()
                                  .getFilterType()
                                  .equals(ReportConstants.ADV_FILTER_TYPE))
                      .map(AdvancedFilterConfigurationMapper::fromReportFilter)
                      .findFirst()
                      .orElse(null);

              List<DataSourceColumn> dataSourceColumns =
                  report.getDataSource().getDataSourceColumns();
              if (dataSourceColumns == null) {
                throw new IllegalArgumentException("Invalid data source");
              }
              List<ReportColumn> reportColumns =
                  dataSourceColumns.stream().map(ReportColumnMapper::fromDataSourceColumn).toList();

              return new ReportConfiguration(
                  new ReportDataSource(report.getDataSource()),
                  new Library(report.getReportLibrary()),
                  report.getReportTitle(),
                  basicFilters,
                  advancedFilter,
                  reportColumns);
            })
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(
                        "Report not found for Report UID: %d and Data Source UID: %d",
                        reportUid, dataSourceUid)));
  }

  @Transactional(readOnly = true)
  public String getReportRunner(Long reportUid, Long dataSourceUid) {
    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    Report report =
        reportRepository
            .findById(reportId)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Report not found for Report UID: %d and Data Source UID: %d",
                            reportUid, dataSourceUid)));

    ReportLibrary reportLibrary = report.getReportLibrary();
    if (reportLibrary == null) {
      throw new UnprocessableEntityException(
          String.format("No report library exists for report %s", reportId));
    }

    return reportLibrary.getRunner();
  }

  public ResponseEntity<ReportResult> executeReport(ReportExecutionRequest request) {
    Long reportUid = request.reportUid();
    Long dataSourceUid = request.dataSourceUid();
    ReportConfiguration reportConfigResponse = getReport(reportUid, dataSourceUid);

    if (!reportConfigResponse.isPython()) {
      throw new NotImplementedException(
          String.format(
              "Report not implemented for %s", reportConfigResponse.reportLibrary().runner()),
          String.valueOf(HttpStatus.NOT_IMPLEMENTED));
    }

    if (request.columnUids() != null && request.columnUids().isEmpty()) {
      throw new UnprocessableEntityException(
          "Column UIDs cannot be empty - if omitting reportColumns, use `null`");
    }

    ReportSpecBuilder specBuilder =
        new ReportSpecBuilder(
            request, reportConfigResponse, dataSourceNameUtils, whereClauseService);
    ReportSpec reportSpec = specBuilder.build();

    return reportExecutionClient
        .post()
        .uri("/report/execute")
        .contentType(MediaType.APPLICATION_JSON)
        .body(reportSpec)
        .retrieve()
        .toEntity(ReportResult.class);
  }

  private void createReportFilters(Report report, List<CreateFilterRequest> filtersToCreate) {
    List<Long> filterCodeUids =
        filtersToCreate.stream().map(CreateFilterRequest::filterCodeUid).toList();

    Map<Long, FilterCode> filterCodeMap =
        filterCodeRepository.findAllById(filterCodeUids).stream()
            .collect(Collectors.toMap(FilterCode::getId, f -> f));

    List<Long> columnUids = filtersToCreate.stream().map(CreateFilterRequest::columnUid).toList();

    Map<Long, DataSourceColumn> columnMap =
        dataSourceColumnRepository.findAllById(columnUids).stream()
            .collect(Collectors.toMap(DataSourceColumn::getId, c -> c));

    List<ReportFilter> reportFilters =
        filtersToCreate.stream()
            .map(
                filterRequest -> {
                  FilterCode filterCode = filterCodeMap.get(filterRequest.filterCodeUid());
                  if (filterCode == null) {
                    throw new IllegalArgumentException(
                        "Unknown filterCodeUid provided: " + filterRequest.filterCodeUid());
                  }

                  DataSourceColumn dataSourceColumn = null;
                  if (filterRequest.columnUid() != null) {
                    dataSourceColumn = columnMap.get(filterRequest.columnUid());
                    if (dataSourceColumn == null) {
                      throw new IllegalArgumentException(
                          "Unknown columnUid provided: " + filterRequest.columnUid());
                    }
                  }

                  return ReportFilter.builder()
                      .report(report)
                      .filterCode(filterCode)
                      .dataSourceColumn(dataSourceColumn)
                      .build();
                })
            .toList();

    reportFilterRepository.saveAll(reportFilters);
  }
}
