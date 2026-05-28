package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdminReportRequest;
import gov.cdc.nbs.repository.ReportRepository;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ReportMapper {
  private final ReportRepository reportRepository;

  private ReportMapper(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
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
      builder.id(new ReportId(reportRepository.getNextReportId(), dataSource.getId()));
      builder.addTime(now).addUserUid(user.getId());
    }

    ReportConstants.ReportGroup group =
        Arrays.stream(ReportConstants.ReportGroup.values())
            .filter(g -> g.toString().equals(request.group()))
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid report group: " + request.group()));

    return builder
        .dataSource(dataSource)
        .descTxt(request.description())
        .isModifiableIndicator('N') // consistently "N" in DB, so just continuing that pattern
        .ownerUid(request.ownerId())
        .reportTitle(request.reportTitle())
        .shared(reportGroupToDbChar(group))
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
