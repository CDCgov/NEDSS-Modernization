package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReportMapper {
  private final Clock clock;
  private final IdGeneratorService idGenerator;

  public ReportMapper(final Clock clock, IdGeneratorService idGenerator) {
    this.clock = clock;
    this.idGenerator = idGenerator;
  }

  public Report duplicate(Report existingReport) {
    Report newReport =
        existingReport.toBuilder()
            .id(new ReportId(generateId(), existingReport.getDataSource().getId()))
            .build();

    if (existingReport.getReportSortColumns() != null) {
      List<ReportSortColumn> newSortColumns =
          existingReport.getReportSortColumns().stream()
              .map(c -> c.toBuilder().id(generateId()).build())
              .toList();

      newSortColumns.forEach(newSortColumn -> newSortColumn.setReport(newReport));
      newReport.setReportSortColumns(newSortColumns);
    }

    if (existingReport.getReportFilters() != null) {
      List<ReportFilter> newFilters =
          existingReport.getReportFilters().stream()
              .map(f -> f.toBuilder().id(generateId()).build())
              .toList();

      newFilters.forEach(newFilter -> newFilter.setReport(newReport));
      newReport.setReportFilters(newFilters);
    }

    if (existingReport.getDisplayColumns() != null) {
      List<DisplayColumn> newDisplayColumns =
          existingReport.getDisplayColumns().stream()
              .map(c -> c.toBuilder().id(generateId()).build())
              .toList();

      newDisplayColumns.forEach(newDisplayColumn -> newDisplayColumn.setReport(newReport));
      newReport.setDisplayColumns(newDisplayColumns);
    }

    return newReport;
  }

  public Report fromAdminReportRequest(
      AdminReportRequest request,
      NbsUserDetails user,
      ReportLibrary reportLibrary,
      DataSource dataSource,
      Report existingReport) {
    LocalDateTime now = LocalDateTime.now(this.clock);

    Report report = existingReport;
    if (report == null) {
      report =
          Report.builder()
              .id(new ReportId(generateId(), dataSource.getId()))
              .addTime(now)
              .addUserUid((user.getId()))
              .dataSource(dataSource)
              .isModifiableIndicator('N') // consistently "N" in DB, so just continuing that pattern
              .filterMode('B') // consistently "B" in DB, so just continuing that pattern"
              .build();
    }

    // adding a SAS library - need to make sure the location and type are set for NBS 6 to use
    if (reportLibrary.getLibraryName().toUpperCase().endsWith(".SAS")) {
      report.setLocation(reportLibrary.getLibraryName());
      report.setReportTypeCode(
          reportLibrary.getColumnSelectInd().toString().equals("Y")
              ? "SAS_CUSTOM"
              : "SAS_ODS_HTML");
    }

    report.setDescTxt(request.description());
    report.setOwnerUid(request.ownerId());
    report.setReportTitle(request.reportTitle());
    report.setShared(ReportConstants.reportGroupToDbChar(request.group()));
    report.setStatus(new Status(Status.ACTIVE_CODE, now));
    report.setSectionCd(request.sectionCode());
    report.setReportLibrary(reportLibrary);

    return report;
  }

  private Long generateId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
