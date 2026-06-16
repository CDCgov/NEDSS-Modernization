package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ReportMapper {
  private final IdGeneratorService idGenerator;

  public ReportMapper(IdGeneratorService idGenerator) {
    this.idGenerator = idGenerator;
  }

  public Report fromAdminReportRequest(
      AdminReportRequest request,
      NbsUserDetails user,
      ReportLibrary reportLibrary,
      DataSource dataSource,
      Report existingReport) {
    LocalDateTime now = LocalDateTime.now();

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
