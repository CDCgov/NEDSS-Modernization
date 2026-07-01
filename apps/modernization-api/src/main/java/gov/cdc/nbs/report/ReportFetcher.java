package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.exception.UnprocessableEntityException;
import gov.cdc.nbs.report.mappers.AdvancedFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.BasicFilterConfigurationMapper;
import gov.cdc.nbs.report.mappers.ReportColumnMapper;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.SortSpec;
import gov.cdc.nbs.repository.ReportRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReportFetcher {

  private final ReportRepository reportRepository;

  public ReportFetcher(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  public ReportConfiguration getReport(Long reportUid, Long dataSourceUid) {
    ReportId id = new ReportId(reportUid, dataSourceUid);

    return reportRepository
        .findById(id)
        .map(
            report -> {
              List<BasicFilterConfiguration> basicFilters =
                  report.getReportFilters().stream()
                      .filter(ReportFilter::isBasicFilter)
                      .map(BasicFilterConfigurationMapper::fromReportFilter)
                      .toList();

              List<DataSourceColumn> dataSourceColumns =
                  report.getDataSource().getDataSourceColumns();
              if (dataSourceColumns == null) {
                throw new IllegalArgumentException("Invalid data source");
              }

              AdvancedFilterConfiguration advancedFilter =
                  report.getReportFilters().stream()
                      .filter(ReportFilter::isAdvancedFilter)
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
                            ? ReportConstants.SortDirection.ASC
                            : ReportConstants.SortDirection.DESC);
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

  private String getReportNotFoundText(ReportId reportId) {
    return String.format(
        "Report not found for Report UID: %d and Data Source UID: %d",
        reportId.getReportUid(), reportId.getDataSourceUid());
  }
}
