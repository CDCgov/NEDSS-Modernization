package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.ReportConstants.SortDirection;
import gov.cdc.nbs.report.mappers.AdvancedFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.BasicFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.models.AdminReportRequest;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.models.SortSpec;
import gov.cdc.nbs.repository.DataSourceRepository;
import gov.cdc.nbs.repository.ReportFilterRepository;
import gov.cdc.nbs.repository.ReportLibraryRepository;
import gov.cdc.nbs.repository.ReportRepository;
import gov.cdc.nbs.repository.ReportSectionRepository;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final DataSourceRepository dataSourceRepository;
  private final ReportLibraryRepository reportLibraryRepository;
  private final ReportFilterRepository reportFilterRepository;
  private final ReportSectionRepository reportSectionRepository;
  private final ReportMapper reportMapper;

  private final RestClient reportExecutionClient;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;
  private final ReportFilterBuilder reportFilterBuilder;

  public ReportService(
      final ReportRepository reportRepository,
      final DataSourceRepository dataSourceRepository,
      final ReportLibraryRepository reportLibraryRepository,
      final ReportFilterRepository reportFilterRepository,
      final ReportSectionRepository reportSectionRepository,
      RestClient reportExecutionClient,
      final DataSourceNameConfiguration dataSourceNameConfig,
      WhereClauseService whereClauseService,
      ReportFilterBuilder reportFilterBuilder,
      ReportFilterValidationBuilder reportFilterValidationBuilder,
      ReportMapper reportMapper) {
    this.reportRepository = reportRepository;
    this.dataSourceRepository = dataSourceRepository;
    this.reportLibraryRepository = reportLibraryRepository;
    this.reportFilterRepository = reportFilterRepository;
    this.reportSectionRepository = reportSectionRepository;
    this.reportMapper = reportMapper;

    this.reportExecutionClient = reportExecutionClient;
    this.dataSourceNameUtils = new DataSourceNameUtils(dataSourceNameConfig);
    this.whereClauseService = whereClauseService;
    this.reportFilterBuilder = reportFilterBuilder;
  }

  @Transactional
  public Report createReport(AdminReportRequest request, NbsUserDetails user) {
    return upsertReport(request, user, null);
  }

  @Transactional
  public Report editReport(
      AdminReportRequest request, NbsUserDetails user, ReportId existingReportId) {
    if (existingReportId != null && !reportRepository.existsById(existingReportId)) {
      throw new NotFoundException(getReportNotFoundText(existingReportId));
    }

    Report savedReport = upsertReport(request, user, existingReportId);

    List<ReportFilter> existingFilters = savedReport.getReportFilters();

    List<ReportFilter> filtersToDelete =
        existingFilters.stream()
            .filter(
                f ->
                    request.filterRequests().stream()
                        .noneMatch(fr -> fr.id() != null && fr.id().equals(f.getId())))
            .toList();

    if (!filtersToDelete.isEmpty()) {
      reportFilterRepository.deleteAll(filtersToDelete);
    }

    return savedReport;
  }

  private Report upsertReport(
      AdminReportRequest request, NbsUserDetails user, ReportId existingReportId) {
    ReportMetadata metadata = verifyReportMetadata(request);

    Report savedReport =
        reportRepository.save(
            reportMapper.fromAdminReportRequest(
                request, user, metadata.reportLibrary, metadata.dataSource, existingReportId));

    if (!request.filterRequests().isEmpty()) {
      List<ReportFilter> reportFilters =
          request.filterRequests().stream()
              .map(f -> reportFilterBuilder.build(f, savedReport))
              .toList();

      reportFilterRepository.saveAll(reportFilters);
    }

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

              List<Long> defaultColumnUids =
                  report.getDisplayColumns().stream()
                      .sorted(Comparator.comparing(DisplayColumn::getSequenceNumber))
                      .map(DisplayColumn::getDataSourceColumnId)
                      .toList();

              ReportSortColumn reportSortColumn =
                  report.getReportSortColumns().stream().findFirst().orElse(null);
              SortSpec defaultSort = null;
              if (reportSortColumn != null) {
                defaultSort =
                    new SortSpec(
                        reportSortColumn.getDataSourceColumnUid(),
                        "ASC".equalsIgnoreCase(reportSortColumn.getReportSortOrderCode())
                            ? SortDirection.ASC
                            : SortDirection.DESC);
              }

              return new ReportConfiguration(
                  new ReportDataSource(report.getDataSource()),
                  new Library(report.getReportLibrary()),
                  report.getReportTitle(),
                  report.getDescTxt(),
                  report.getOwnerUid(),
                  ReportConstants.dbCharToReportGroup(report.getShared()),
                  report.getSectionCd(),
                  basicFilters,
                  advancedFilter,
                  reportColumns,
                  defaultColumnUids,
                  defaultSort);
            })
        .orElseThrow(() -> new NotFoundException(getReportNotFoundText(id)));
  }

  @Transactional(readOnly = true)
  public String getReportRunner(Long reportUid, Long dataSourceUid) {
    ReportId reportId = new ReportId(reportUid, dataSourceUid);

    Report report =
        reportRepository
            .findById(reportId)
            .orElseThrow(() -> new NotFoundException(getReportNotFoundText(reportId)));

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
          String.format("Report not implemented for %s", reportConfigResponse.library().runner()),
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
        .onStatus(
            status -> status.value() >= 400,
            (req, resp) -> {
              throw new RestClientResponseException(
                  "Error response from the report-execution service",
                  resp.getStatusCode(),
                  resp.getStatusText(),
                  resp.getHeaders(),
                  resp.getBody().readAllBytes(),
                  null);
            })
        .toEntity(ReportResult.class);
  }

  private String getReportNotFoundText(ReportId reportId) {
    return String.format(
        "Report not found for Report UID: %d and Data Source UID: %d",
        reportId.getReportUid(), reportId.getDataSourceUid());
  }

  private record ReportMetadata(DataSource dataSource, ReportLibrary reportLibrary) {}

  private ReportMetadata verifyReportMetadata(AdminReportRequest request) {
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

    if (!reportSectionRepository.existsBySectionCd(request.sectionCode())) {
      throw new IllegalArgumentException(
          "No report section found for code " + request.sectionCode());
    }

    return new ReportMetadata(dataSource, reportLibrary);
  }
}
