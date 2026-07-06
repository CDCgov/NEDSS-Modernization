package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.FilterValue;
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
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.mappers.ReportSortColumnMapper;
import gov.cdc.nbs.report.models.AdminReportRequest;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.LibraryExecutionResult;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportExecutionResult;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.models.SortSpec;
import gov.cdc.nbs.repository.DataSourceRepository;
import gov.cdc.nbs.repository.ReportFilterRepository;
import gov.cdc.nbs.repository.ReportLibraryRepository;
import gov.cdc.nbs.repository.ReportRepository;
import gov.cdc.nbs.repository.ReportSectionRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class ReportService {

  private final Clock clock;

  private final ReportRepository reportRepository;
  private final DataSourceRepository dataSourceRepository;
  private final ReportLibraryRepository reportLibraryRepository;
  private final ReportSectionRepository reportSectionRepository;
  private final ReportFilterRepository reportFilterRepository;
  private final ReportMapper reportMapper;
  private final ReportSortColumnMapper reportSortColumnMapper;
  private final FilterValueMapper filterValueMapper;
  private final DisplayColumnBuilder displayColumnBuilder;

  private final RestClient reportExecutionClient;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;
  private final ReportFilterBuilder reportFilterBuilder;

  public ReportService(
      final Clock clock,
      final ReportRepository reportRepository,
      final DataSourceRepository dataSourceRepository,
      final ReportLibraryRepository reportLibraryRepository,
      final ReportSectionRepository reportSectionRepository,
      final ReportFilterRepository reportFilterRepository,
      RestClient reportExecutionClient,
      final DataSourceNameUtils dataSourceNameUtils,
      WhereClauseService whereClauseService,
      ReportFilterBuilder reportFilterBuilder,
      ReportMapper reportMapper,
      ReportSortColumnMapper reportSortColumnMapper,
      FilterValueMapper filterValueMapper,
      DisplayColumnBuilder displayColumnBuilder) {
    this.clock = clock;

    this.reportRepository = reportRepository;
    this.dataSourceRepository = dataSourceRepository;
    this.reportLibraryRepository = reportLibraryRepository;
    this.reportSectionRepository = reportSectionRepository;
    this.reportFilterRepository = reportFilterRepository;
    this.reportMapper = reportMapper;
    this.reportSortColumnMapper = reportSortColumnMapper;
    this.filterValueMapper = filterValueMapper;
    this.displayColumnBuilder = displayColumnBuilder;

    this.reportExecutionClient = reportExecutionClient;
    this.dataSourceNameUtils = dataSourceNameUtils;
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
    Report existingReport =
        reportRepository
            .findById(existingReportId)
            .orElseThrow(() -> new NotFoundException(getReportNotFoundText(existingReportId)));

    return upsertReport(request, user, existingReport);
  }

  @Transactional
  public void deleteReport(ReportId existingReportId) {
    Report existingReport =
        reportRepository
            .findById(existingReportId)
            .orElseThrow(() -> new NotFoundException(getReportNotFoundText(existingReportId)));

    reportRepository.delete(existingReport);
  }

  private Report upsertReport(
      AdminReportRequest request, NbsUserDetails user, Report existingReport) {
    ReportMetadata metadata = verifyReportMetadata(request);

    Report report =
        reportMapper.fromAdminReportRequest(
            request, user, metadata.reportLibrary, metadata.dataSource, existingReport);

    List<ReportFilter> reportFilters =
        request.filterRequests().stream().map(f -> reportFilterBuilder.build(f, report)).toList();
    if (report.getReportFilters() != null) {
      // For orphan detection/removal to work, need to keep the same container
      report.getReportFilters().clear();
      report.getReportFilters().addAll(reportFilters);
    } else {
      report.setReportFilters(reportFilters);
    }

    return reportRepository.save(report);
  }

  /**
   * The `save` action, which overwrites the various filter/sort details of a given report, without
   * changing the actual report mechanics themselves.
   */
  @Transactional
  public Report saveReport(ReportExecutionRequest request, Report report) {
    if (report == null) {
      ReportId reportId = new ReportId(request.reportUid(), request.dataSourceUid());
      throw new NotFoundException(getReportNotFoundText(reportId));
    }

    updateDisplayColumns(report, request.columnUids());
    updateSortColumns(report, request.sort());
    updateAdvancedFilterValues(report, request.advancedFilter());
    updateBasicFilterValues(report, request.basicFilters());

    return reportRepository.save(report);
  }

  public ReportConfiguration getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    return reportRepository
        .findById(id)
        .map(
            report -> {
              ReportLibrary library = report.getReportLibrary();
              if (library == null) {
                throw new IllegalArgumentException(
                    "No library found for this report. This can happen if a report was created or save-as'ed with NBS 6, but is trying to be opened in NBS 7. Ask your NBS administrator to sync the report and report library tables using the query in the report admin guide.");
              }

              List<BasicFilterConfiguration> basicFilters =
                  report.getReportFilters().stream()
                      .filter(this::isBasicFilter)
                      .map(BasicFilterConfigurationMapper::fromReportFilter)
                      .toList();

              List<DataSourceColumn> dataSourceColumns =
                  report.getDataSource().getDataSourceColumns();
              if (dataSourceColumns == null) {
                throw new IllegalArgumentException("Invalid data source");
              }

              AdvancedFilterConfiguration advancedFilter =
                  report.getReportFilters().stream()
                      .filter(this::isAdvancedFilter)
                      .map(
                          f ->
                              AdvancedFilterConfigurationMapper.fromReportFilter(
                                  f, dataSourceColumns))
                      .findFirst()
                      .orElse(null);

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
                  new Library(library),
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
    // If the library is null, that means this report was save-as'ed with NBS 6 from a sas report
    if (reportLibrary == null) {
      return "sas";
    }

    return reportLibrary.getRunner();
  }

  public ReportExecutionResult executeReport(ReportExecutionRequest request) {
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

    LibraryExecutionResult result =
        reportExecutionClient
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
            .toEntity(LibraryExecutionResult.class)
            .getBody();

    if (result == null) {
      throw new IllegalStateException(
          "No error response and no body parsed from report execution service");
    }

    return new ReportExecutionResult(
        result, reportSpec.subsetQuery(), LocalDateTime.now(this.clock));
  }

  private void updateBasicFilterValues(Report report, List<BasicFilterRequest> basicFilterReqs) {
    Map<Long, ReportFilter> basicFiltersById =
        report.getReportFilters().stream()
            .filter(this::isBasicFilter)
            .collect(Collectors.toMap(ReportFilter::getId, Function.identity()));

    //  If no basic filter requests are provided, delete all filter values for all existing basic
    // filters
    if (basicFilterReqs == null || basicFilterReqs.isEmpty()) {
      basicFiltersById.values().forEach(basicFilter -> basicFilter.getFilterValues().clear());
    } else if (basicFilterReqs.stream()
        .anyMatch(req -> !basicFiltersById.containsKey(req.reportFilterUid()))) {
      throw new IllegalArgumentException(
          "BasicFilterRequest.reportFilterUid does not match existing basic filter ID");
    } else {
      Map<Long, BasicFilterRequest> basicFilterReqsById =
          basicFilterReqs.stream()
              .collect(Collectors.toMap(BasicFilterRequest::reportFilterUid, Function.identity()));

      basicFiltersById
          .values()
          .forEach(
              basicFilter -> {
                //  First, delete all existing filter values for this basic filter
                basicFilter.getFilterValues().clear();

                BasicFilterRequest matchingReq = basicFilterReqsById.get(basicFilter.getId());

                //  Then, if there's a matching request, add the new filter values from the request
                if (matchingReq != null && !matchingReq.values().isEmpty()) {
                  List<FilterValue> basicFilterValues =
                      filterValueMapper.fromBasicFilterRequest(basicFilter, matchingReq);
                  basicFilter.getFilterValues().addAll(basicFilterValues);
                }
              });
    }

    reportFilterRepository.saveAll(basicFiltersById.values());
  }

  private void updateAdvancedFilterValues(Report report, AdvancedFilterRequest advFilterReq) {
    ReportFilter advancedFilter =
        report.getReportFilters().stream().filter(this::isAdvancedFilter).findFirst().orElse(null);

    if (advancedFilter == null && advFilterReq != null) {
      throw new IllegalArgumentException(
          "AdvancedFilterRequest included for report without an advanced filter");
    }

    if (advancedFilter != null) {
      advancedFilter.getFilterValues().clear();

      if (advFilterReq != null) {
        if (!advancedFilter.getId().equals(advFilterReq.reportFilterUid())) {
          throw new IllegalArgumentException(
              "AdvancedFilterRequest.reportFilterUid does not match existing advanced filter ID");
        }

        List<FilterValue> advFilterValues =
            filterValueMapper.fromAdvancedFilterRequest(advancedFilter, advFilterReq);
        advancedFilter.getFilterValues().addAll(advFilterValues);
      }

      reportFilterRepository.save(advancedFilter);
    }
  }

  private void updateDisplayColumns(Report report, List<Long> displayColumnIds) {
    report.getDisplayColumns().clear();

    if (displayColumnIds != null && !displayColumnIds.isEmpty()) {
      List<DataSourceColumn> reportColumns = report.getDataSource().getDataSourceColumns();

      AtomicInteger seq = new AtomicInteger(1);
      List<DisplayColumn> newDisplayColumns =
          displayColumnIds.stream()
              .map(
                  columnId -> {
                    DataSourceColumn matchingColumn =
                        reportColumns.stream()
                            .filter(c -> c.getId().equals(columnId))
                            .findFirst()
                            .orElseThrow(
                                () ->
                                    new NotFoundException(
                                        "No matching column found for ID " + columnId));

                    return displayColumnBuilder.build(
                        report, matchingColumn, seq.getAndIncrement());
                  })
              .toList();

      report.getDisplayColumns().addAll(newDisplayColumns);
    }
  }

  private void updateSortColumns(Report report, SortSpec sort) {
    report.getReportSortColumns().clear();

    if (sort != null) {
      ReportSortColumn sortColumn = reportSortColumnMapper.fromSortSpec(report, sort);
      report.getReportSortColumns().add(sortColumn);
    }
  }

  private boolean isAdvancedFilter(ReportFilter filter) {
    return filter.getFilterCode().getFilterType().equals(ReportConstants.ADV_FILTER_TYPE);
  }

  private boolean isBasicFilter(ReportFilter filter) {
    return filter.getFilterCode().getFilterType().startsWith(ReportConstants.BASIC_FILTER_PREFIX);
  }

  protected String getReportNotFoundText(ReportId reportId) {
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
