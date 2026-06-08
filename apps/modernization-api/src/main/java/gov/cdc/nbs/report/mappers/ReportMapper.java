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
      ReportId existingReportId) {
    LocalDateTime now = LocalDateTime.now();

    Report.ReportBuilder builder = Report.builder();

    if (existingReportId != null) {
      builder.id(existingReportId);
    } else {
      builder.id(new ReportId(generateReportId(), dataSource.getId()));
      builder.addTime(now).addUserUid(user.getId());
    }

    // adding a SAS library - need to make sure the location and type are set for NBS 6 to use
    if (reportLibrary.getLibraryName().toUpperCase().endsWith(".SAS")) {
      builder.location(reportLibrary.getLibraryName());
      builder.reportTypeCode(
          reportLibrary.getColumnSelectInd().toString().equals("Y")
              ? "SAS_CUSTOM"
              : "SAS_ODS_HTML");
    }

    return builder
        .dataSource(dataSource)
        .descTxt(request.description())
        .isModifiableIndicator('N') // consistently "N" in DB, so just continuing that pattern
        .filterMode('B') // consistently "B" in DB, so just continuing that pattern"
        .ownerUid(request.ownerId())
        .reportTitle(request.reportTitle())
        .shared(ReportConstants.reportGroupToDbChar(request.group()))
        .status(new Status(Status.ACTIVE_CODE, now))
        .sectionCd(request.sectionCode())
        .reportLibrary(reportLibrary)
        .build();
  }

  private Long generateReportId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
