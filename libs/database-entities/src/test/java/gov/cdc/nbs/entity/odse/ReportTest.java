package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class ReportTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThrows(NullPointerException.class, () -> new Report(null, null, null));
  }

  @Test
  void should_create_report() {
    Long reportId = 1L;
    DataSource dataSource = new DataSource();
    ReportId id = new ReportId(reportId, dataSource);
    ReportLibrary reportLibrary = new ReportLibrary();
    String sectionCd = "1000";

    Report actual = new Report(id, reportLibrary, sectionCd);

    assertThat(actual)
        .satisfies(report -> assertEquals(reportId, report.getId().getReportUid()))
        .satisfies(report -> assertNull(report.getDescTxt()))
        .satisfies(report -> assertNull(report.getEffectiveFromTime()))
        .satisfies(report -> assertNull(report.getEffectiveToTime()))
        .satisfies(report -> assertNull(report.getFilterMode()))
        .satisfies(report -> assertNull(report.getIsModifiableIndicator()))
        .satisfies(report -> assertNull(report.getLocation()))
        .satisfies(report -> assertNull(report.getOwnerUid()))
        .satisfies(report -> assertNull(report.getOrgAccessPermission()))
        .satisfies(report -> assertNull(report.getProgAreaAccessPermission()))
        .satisfies(report -> assertNull(report.getReportTitle()))
        .satisfies(report -> assertNull(report.getReportTypeCode()))
        .satisfies(report -> assertNull(report.getShared()))
        .satisfies(report -> assertEquals(reportLibrary, report.getReportLibrary()))
        .satisfies(report -> assertEquals(sectionCd, report.getSectionCd()));
  }

  @Test
  void should_create_complete_report() {
    Long reportId = 1L;
    DataSource dataSource = new DataSource();
    ReportId id = new ReportId(reportId, dataSource);
    ReportLibrary reportLibrary = new ReportLibrary();
    String descTxt = "Counts of Reportable Diseases by County for Selected Time Frame";
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
    Character filterMode = 'B';
    Character isModifiableIndicator = 'N';
    String location = "MOCKREPORT.SAS";
    Integer ownerUid = 1;
    String orgAccessPermission = "ORG_ACCESS_PERMISSION";
    String progAreaAccessPermission = "PROG_AREA_ACCESS_PERMISSION";
    String reportTitle = "SR2: Counts of Reportable Diseases by County for Selected Time Frame";
    String reportTypeCode = "SAS_MOCK_CUSTOM";
    Character shared = 'S';
    String category = "Summary Report";
    String sectionCd = "1000";
    Audit audit = new Audit();
    Status status = new Status();

    Report actual =
        new Report(
            id,
            reportLibrary,
            descTxt,
            effectiveFromTime,
            effectiveToTime,
            filterMode,
            isModifiableIndicator,
            location,
            ownerUid,
            orgAccessPermission,
            progAreaAccessPermission,
            reportTitle,
            reportTypeCode,
            shared,
            category,
            sectionCd,
            audit,
            status);

    assertThat(actual)
        .satisfies(report -> assertEquals(reportId, report.getId().getReportUid()))
        .satisfies(report -> assertEquals(descTxt, report.getDescTxt()))
        .satisfies(report -> assertEquals(effectiveFromTime, report.getEffectiveFromTime()))
        .satisfies(report -> assertEquals(effectiveToTime, report.getEffectiveToTime()))
        .satisfies(report -> assertEquals(filterMode, report.getFilterMode()))
        .satisfies(report -> assertEquals(isModifiableIndicator, report.getIsModifiableIndicator()))
        .satisfies(report -> assertEquals(location, report.getLocation()))
        .satisfies(report -> assertEquals(ownerUid, report.getOwnerUid()))
        .satisfies(report -> assertEquals(orgAccessPermission, report.getOrgAccessPermission()))
        .satisfies(
            report -> assertEquals(progAreaAccessPermission, report.getProgAreaAccessPermission()))
        .satisfies(report -> assertEquals(reportTitle, report.getReportTitle()))
        .satisfies(report -> assertEquals(reportTypeCode, report.getReportTypeCode()))
        .satisfies(report -> assertEquals(shared, report.getShared()))
        .satisfies(report -> assertEquals(category, report.getCategory()))
        .satisfies(report -> assertEquals(reportLibrary, report.getReportLibrary()))
        .satisfies(report -> assertEquals(sectionCd, report.getSectionCd()));
  }
}
