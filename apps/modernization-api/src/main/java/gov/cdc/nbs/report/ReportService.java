package gov.cdc.nbs.report;

import gov.cdc.nbs.authentication.NbsUserDetails;
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
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.mappers.ReportSortColumnMapper;
import gov.cdc.nbs.report.models.AdminReportRequest;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.SaveAsReportRequest;
import gov.cdc.nbs.report.models.SortSpec;
import gov.cdc.nbs.repository.DataSourceRepository;
import gov.cdc.nbs.repository.ReportFilterRepository;
import gov.cdc.nbs.repository.ReportLibraryRepository;
import gov.cdc.nbs.repository.ReportRepository;
import gov.cdc.nbs.repository.ReportSectionRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

  private final ReportRepository reportRepository;
  private final DataSourceRepository dataSourceRepository;
  private final ReportLibraryRepository reportLibraryRepository;
  private final ReportSectionRepository reportSectionRepository;
  private final ReportFilterRepository reportFilterRepository;
  private final ReportMapper reportMapper;
  private final ReportSortColumnMapper reportSortColumnMapper;
  private final FilterValueMapper filterValueMapper;
  private final DisplayColumnBuilder displayColumnBuilder;

  private final ReportFilterBuilder reportFilterBuilder;

  public ReportService(
      final ReportRepository reportRepository,
      final DataSourceRepository dataSourceRepository,
      final ReportLibraryRepository reportLibraryRepository,
      final ReportSectionRepository reportSectionRepository,
      final ReportFilterRepository reportFilterRepository,
      ReportFilterBuilder reportFilterBuilder,
      ReportMapper reportMapper,
      ReportSortColumnMapper reportSortColumnMapper,
      FilterValueMapper filterValueMapper,
      DisplayColumnBuilder displayColumnBuilder,
      ReportFetcher reportFetcher) {

    this.reportRepository = reportRepository;
    this.dataSourceRepository = dataSourceRepository;
    this.reportLibraryRepository = reportLibraryRepository;
    this.reportSectionRepository = reportSectionRepository;
    this.reportFilterRepository = reportFilterRepository;
    this.reportMapper = reportMapper;
    this.reportSortColumnMapper = reportSortColumnMapper;
    this.filterValueMapper = filterValueMapper;
    this.displayColumnBuilder = displayColumnBuilder;

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
  public Report saveReport(ReportExecutionRequest request, ReportId reportId) {
    Report report =
        reportRepository
            .findById(reportId)
            .orElseThrow(() -> new NotFoundException(getReportNotFoundText(reportId)));

    updateDisplayColumns(report, request.columnUids());
    updateSortColumns(report, request.sort());
    updateAdvancedFilterValues(report, request.advancedFilter());
    updateBasicFilterValues(report, request.basicFilters());

    return reportRepository.save(report);
  }

  @Transactional
  public Report saveAsReport(SaveAsReportRequest request, NbsUserDetails user, ReportId reportId) {
    Report report =
        reportRepository
            .findById(reportId)
            .orElseThrow(() -> new NotFoundException(getReportNotFoundText(reportId)));

    Report duplicate = reportMapper.duplicate(report);

    duplicate.setReportTitle(request.reportTitle());
    duplicate.setSectionCd(request.sectionCode());
    duplicate.setShared(ReportConstants.reportGroupToDbChar(request.group()));
    duplicate.setOwnerUid(user.getId());

    if (request.description() != null) {
      duplicate.setDescTxt(request.description());
    }

    reportRepository.save(duplicate);

    return saveReport(request.executionRequest(), duplicate.getId());
  }

  private void updateBasicFilterValues(Report report, List<BasicFilterRequest> basicFilterReqs) {
    Map<Long, ReportFilter> basicFiltersById =
        report.getReportFilters().stream()
            .filter(ReportFilter::isBasicFilter)
            .collect(Collectors.toMap(ReportFilter::getId, Function.identity()));

    //  If no basic filter requests are provided, delete all filter values for all existing basic
    // filters
    if (basicFilterReqs == null || basicFilterReqs.isEmpty()) {
      basicFiltersById.values().forEach(basicFilter -> basicFilter.setFilterValues(null));
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
                BasicFilterRequest matchingReq = basicFilterReqsById.get(basicFilter.getId());
                //  If a basic filter request isn't present for a given basic filter,
                //  OR if it is present but has no values, then delete all filter values
                //  for that particular basic filter
                if (matchingReq == null || matchingReq.values().isEmpty()) {
                  basicFilter.setFilterValues(null);
                } else {
                  //  Otherwise, reset the filter values to the new values provided
                  basicFilter.getFilterValues().clear();

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
        report.getReportFilters().stream()
            .filter(ReportFilter::isAdvancedFilter)
            .findFirst()
            .orElse(null);

    if (advancedFilter == null && advFilterReq != null) {
      throw new IllegalArgumentException(
          "AdvancedFilterRequest included for report without an advanced filter");
    }

    if (advancedFilter != null) {
      if (advFilterReq == null) {
        advancedFilter.setFilterValues(null);
      } else {
        if (!advancedFilter.getId().equals(advFilterReq.reportFilterUid())) {
          throw new IllegalArgumentException(
              "AdvancedFilterRequest.reportFilterUid does not match existing advanced filter ID");
        }

        advancedFilter.getFilterValues().clear();

        List<FilterValue> advFilterValues =
            filterValueMapper.fromAdvancedFilterRequest(advancedFilter, advFilterReq);
        advancedFilter.getFilterValues().addAll(advFilterValues);
      }

      reportFilterRepository.save(advancedFilter);
    }
  }

  private void updateDisplayColumns(Report report, List<Long> displayColumnIds) {
    if (displayColumnIds == null || displayColumnIds.isEmpty()) {
      report.setDisplayColumns(null);
      return;
    }

    List<DataSourceColumn> reportColumns = report.getDataSource().getDataSourceColumns();

    report.getDisplayColumns().clear();

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

                  DisplayColumn col = displayColumnBuilder.build(report, matchingColumn, seq.get());

                  seq.getAndIncrement();
                  return col;
                })
            .toList();

    report.getDisplayColumns().addAll(newDisplayColumns);
  }

  private void updateSortColumns(Report report, SortSpec sort) {
    if (sort == null) {
      report.setReportSortColumns(null);
      return;
    }

    report.getReportSortColumns().clear();

    ReportSortColumn sortColumn = reportSortColumnMapper.fromSortSpec(report, sort);
    report.getReportSortColumns().add(sortColumn);
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

  public String getReportNotFoundText(ReportId reportId) {
    return String.format(
        "Report not found for Report UID: %d and Data Source UID: %d",
        reportId.getReportUid(), reportId.getDataSourceUid());
  }
}
