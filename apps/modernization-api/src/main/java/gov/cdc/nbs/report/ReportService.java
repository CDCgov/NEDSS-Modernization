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
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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

  private final TransactionTemplate transactionTemplate;

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
      TransactionTemplate transactionTemplate) {

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
    this.transactionTemplate = transactionTemplate;
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
  public Report saveReport(ReportExecutionRequest request, Report report) {
    if (report == null) {
      ReportId reportId = new ReportId(request.reportUid(), request.dataSourceUid());
      throw new NotFoundException(getReportNotFoundText(reportId));
    }

    return transactionTemplate.execute(
        status -> {
          updateDisplayColumns(report, request.columnUids());
          updateSortColumns(report, request.sort());
          updateAdvancedFilterValues(report, request.advancedFilter());
          updateBasicFilterValues(report, request.basicFilters());

          return reportRepository.save(report);
        });
  }

  public Report saveAsReport(SaveAsReportRequest request, NbsUserDetails user, ReportId reportId) {
    return transactionTemplate.execute(
        status -> {
          Report report =
              reportRepository
                  .findById(reportId)
                  .orElseThrow(() -> new NotFoundException(getReportNotFoundText(reportId)));

          Report duplicate = reportMapper.duplicate(report, user);

          duplicate.setReportTitle(request.reportTitle());
          duplicate.setSectionCd(request.sectionCode());
          duplicate.setShared(ReportConstants.reportGroupToDbChar(request.group()));
          duplicate.setOwnerUid(user.getId());

          if (request.description() != null) {
            duplicate.setDescTxt(request.description());
          }

          Report newReport = reportRepository.save(duplicate);

          return saveReport(request.executionRequest(), newReport);
        });
  }

  private void updateBasicFilterValues(Report report, List<BasicFilterRequest> basicFilterReqs) {
    Map<Long, ReportFilter> basicFiltersById =
        report.getReportFilters().stream()
            .filter(ReportFilter::isBasicFilter)
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
        report.getReportFilters().stream()
            .filter(ReportFilter::isAdvancedFilter)
            .findFirst()
            .orElse(null);

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

                  return displayColumnBuilder.build(report, matchingColumn);
                })
            .toList();

    for (int i = 0; i < newDisplayColumns.size(); i++) {
      DisplayColumn newDisplayColumn = newDisplayColumns.get(i);
      newDisplayColumn.setSequenceNumber(i + 1);
    }

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
