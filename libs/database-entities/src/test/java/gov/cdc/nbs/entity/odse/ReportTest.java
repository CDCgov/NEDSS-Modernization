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
    ReportLibrary reportLibrary = new ReportLibrary();
    ReportId id = new ReportId(reportId, dataSource);

    Report actual = new Report(id, reportLibrary, "1000");

    assertThat(actual)
        .satisfies(report -> assertEquals(1L, report.getId().getReportUid()))
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
        .satisfies(report -> assertEquals("1000", report.getSectionCd()));
  }

  @Test
  void should_create_complete_report() {
    Long reportId = 1L;
    DataSource dataSource = new DataSource();
    ReportLibrary reportLibrary = new ReportLibrary();
    ReportId id = new ReportId(reportId, dataSource);
    Audit audit = new Audit();
    Status status = new Status();

    Report actual =
        new Report(
            id,
            reportLibrary,
            "description",
            LocalDateTime.parse("2020-03-03T10:15:30"),
            LocalDateTime.parse("2020-03-04T10:15:30"),
            'Y',
            'N',
            "MOCKREPORT.SAS",
            1,
            "ORG_ACCESS_PERMISSION",
            "PROG_AREA_ACCESS_PERMISSION",
            "New Report",
            "SAS_MOCK_CUSTOM",
            'S',
            "Summary Report",
            "1000",
            audit,
            status);

    assertThat(actual)
        .satisfies(report -> assertEquals(1L, report.getId().getReportUid()))
        .satisfies(report -> assertEquals("description", report.getDescTxt()))
        .satisfies(
            report ->
                assertEquals(
                    LocalDateTime.parse("2020-03-03T10:15:30"), report.getEffectiveFromTime()))
        .satisfies(
            report ->
                assertEquals(
                    LocalDateTime.parse("2020-03-04T10:15:30"), report.getEffectiveToTime()))
        .satisfies(report -> assertEquals('Y', report.getFilterMode()))
        .satisfies(report -> assertEquals('N', report.getIsModifiableIndicator()))
        .satisfies(report -> assertEquals("MOCKREPORT.SAS", report.getLocation()))
        .satisfies(report -> assertEquals(1, report.getOwnerUid()))
        .satisfies(report -> assertEquals("ORG_ACCESS_PERMISSION", report.getOrgAccessPermission()))
        .satisfies(
            report ->
                assertEquals("PROG_AREA_ACCESS_PERMISSION", report.getProgAreaAccessPermission()))
        .satisfies(report -> assertEquals("New Report", report.getReportTitle()))
        .satisfies(report -> assertEquals("SAS_MOCK_CUSTOM", report.getReportTypeCode()))
        .satisfies(report -> assertEquals('S', report.getShared()))
        .satisfies(report -> assertEquals("Summary Report", report.getCategory()))
        .satisfies(report -> assertEquals("1000", report.getSectionCd()));
  }
}
