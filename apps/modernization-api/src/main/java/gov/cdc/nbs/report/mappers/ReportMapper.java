package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.ReportFilterBuilder;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ReportMapper {
  private final Clock clock;
  private final IdGeneratorService idGenerator;
  private final ReportSortColumnMapper reportSortColumnMapper;
  private final ReportFilterBuilder reportFilterBuilder;

  public ReportMapper(final Clock clock, IdGeneratorService idGenerator, ReportSortColumnMapper reportSortColumnMapper, ReportFilterBuilder reportFilterBuilder) {
    this.clock = clock;
    this.idGenerator = idGenerator;
    this.reportSortColumnMapper = reportSortColumnMapper;
    this.reportFilterBuilder = reportFilterBuilder;
  }

  public Report duplicate(Report report) {
    Report newReport = Report.builder()
            .id(new ReportId(generateReportId(), report.getDataSource().getId()))
            .dataSource(report.getDataSource())
                    .reportLibrary(report.getReportLibrary())
            .displayColumns()
            .descTxt(report.getDescTxt())
            .effectiveTime(report.getEffectiveTime())
            .filterMode(report.getFilterMode())
            .isModifiableIndicator(report.getIsModifiableIndicator())
            .location(report.getLocation())
            .ownerUid(report.getOwnerUid())
            .orgAccessPermission(report.getOrgAccessPermission())
            .progAreaAccessPermission(report.getProgAreaAccessPermission())
            .reportTitle(report.getReportTitle())
            .reportTypeCode(report.getReportTypeCode())
            .shared(report.getShared())
            .category(report.getCategory())
            .sectionCd(report.getSectionCd())
            .addReasonCd(report.getSectionCd())
            .addTime(report.getAddTime())
            .addUserUid(report.getAddUserUid())
            .status(report.getStatus())
            .build();

    if (report.getReportSortColumns() != null) {
      newReport.setReportSortColumns(report.getReportSortColumns().stream().map(reportSortColumnMapper::duplicate).toList());
    }

    if (report.getReportFilters() != null) {
      newReport.setReportFilters(report.getReportFilters().stream().map(reportFilterBuilder::duplicate).toList());
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
              .id(new ReportId(generateReportId(), dataSource.getId()))
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

  private Long generateReportId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
