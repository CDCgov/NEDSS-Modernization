package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.LocalDateTime;

public class ReportMapper {
  private ReportMapper() {}

  public static Report fromAdminReportRequest(
      AdminReportRequest request,
      NbsUserDetails user,
      ReportLibrary reportLibrary,
      DataSource dataSource,
      ReportId existingReportId) {
    LocalDateTime now = LocalDateTime.now();

    Report.ReportBuilder builder = Report.builder();

    if (existingReportId != null) {
      builder = builder.id(existingReportId);
    }

    return builder
        .dataSource(dataSource)
        .addTime(now)
        .addUserUid(user.getId())
        .descTxt(request.description())
        .isModifiableIndicator('N') // consistently "N" in DB, so just continuing that pattern
        .ownerUid(request.ownerId())
        .reportTitle(request.reportTitle())
        .shared(reportGroupToDbChar(ReportConstants.ReportGroup.valueOf(request.group())))
        .status(new Status(Status.ACTIVE_CODE, now))
        .sectionCd(request.sectionCode())
        .reportLibrary(reportLibrary)
        .build();
  }

  private static Character reportGroupToDbChar(ReportConstants.ReportGroup group) {
    return switch (group) {
      case PRIVATE -> 'P';
      case REPORTING_FACILITY -> 'R';
      case PUBLIC -> 'S';
      case TEMPLATE -> 'T';
    };
  }
}
