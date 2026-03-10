package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.time.EffectiveTime;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReportTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new Report(null, null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("id is marked non-null but is null");
  }

  @Test
  void should_create_report() {
    Long reportId = 1L;
    Long dataSource = 2L;
    ReportId id = new ReportId(reportId, dataSource);
    ReportLibrary reportLibrary = new ReportLibrary();
    String sectionCd = "1000";

    Report actual = new Report(id, reportLibrary, sectionCd);

    assertThat(actual)
        .satisfies(report -> assertEquals(reportId, report.getId().getReportUid()))
        .satisfies(report -> assertEquals(dataSource, report.getId().getDataSourceUid()))
        .satisfies(report -> assertNull(report.getDescTxt()))
        .satisfies(report -> assertNull(report.getEffectiveTime()))
        .satisfies(report -> assertNull(report.getFilterMode()))
        .satisfies(report -> assertNull(report.getIsModifiableIndicator()))
        .satisfies(report -> assertNull(report.getLocation()))
        .satisfies(report -> assertNull(report.getOwnerUid()))
        .satisfies(report -> assertNull(report.getOrgAccessPermission()))
        .satisfies(report -> assertNull(report.getProgAreaAccessPermission()))
        .satisfies(report -> assertNull(report.getReportTitle()))
        .satisfies(report -> assertNull(report.getReportTypeCode()))
        .satisfies(report -> assertNull(report.getShared()))
        .satisfies(report -> assertNull(report.getAddReasonCd()))
        .satisfies(report -> assertNull(report.getAddTime()))
        .satisfies(report -> assertNull(report.getAddUserUid()))
        .satisfies(report -> assertEquals(reportLibrary, report.getReportLibrary()))
        .satisfies(report -> assertEquals(sectionCd, report.getSectionCd()));
  }

  @Test
  void should_create_complete_report() {
    Long reportId = 1L;
    Long dataSource = 1L;
    ReportId id = new ReportId(reportId, dataSource);
    DataSource dataSourceObj = new DataSource();
    ReportLibrary reportLibrary = new ReportLibrary();
    String descTxt = "Counts of Reportable Diseases by County for Selected Time Frame";
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
    EffectiveTime effectiveTime = new EffectiveTime(effectiveFromTime, effectiveToTime);
    Character filterMode = 'B';
    Character isModifiableIndicator = 'N';
    String location = "MOCKREPORT.SAS";
    Long ownerUid = 1L;
    String orgAccessPermission = "ORG_ACCESS_PERMISSION";
    String progAreaAccessPermission = "PROG_AREA_ACCESS_PERMISSION";
    String reportTitle = "SR2: Counts of Reportable Diseases by County for Selected Time Frame";
    String reportTypeCode = "SAS_MOCK_CUSTOM";
    Character shared = 'S';
    String category = "Summary Report";
    String sectionCd = "1000";
    String addReasonCd = "new report";
    Long addUserUid = 9L;
    LocalDateTime addTime = LocalDateTime.parse("2020-03-04T10:15:30");
    Status status = new Status();

    Report actual =
        new Report(
            id,
            dataSourceObj,
            reportLibrary,
            descTxt,
            effectiveTime,
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
            addReasonCd,
            addTime,
            addUserUid,
            status);

    assertThat(actual)
        .satisfies(report -> assertEquals(reportId, report.getId().getReportUid()))
        .satisfies(report -> assertEquals(reportId, report.getId().getDataSourceUid()))
        .satisfies(report -> assertEquals(descTxt, report.getDescTxt()))
        .satisfies(
            report ->
                assertEquals(effectiveFromTime, report.getEffectiveTime().getEffectiveFromTime()))
        .satisfies(
            report -> assertEquals(effectiveToTime, report.getEffectiveTime().getEffectiveToTime()))
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
        .satisfies(report -> assertEquals(sectionCd, report.getSectionCd()))
        .satisfies(report -> assertEquals(addReasonCd, report.getAddReasonCd()))
        .satisfies(report -> assertEquals(addTime, report.getAddTime()))
        .satisfies(report -> assertEquals(addUserUid, report.getAddUserUid()))
        .satisfies(report -> assertEquals(status, report.getStatus()))
        .satisfies(report -> assertEquals(dataSourceObj, report.getDataSource()));
  }

  @Test
  void should_instantiate_via_protected_constructor() {
    Report actual = new Report();

    assertThat(actual)
        .isNotNull()
        .extracting(
            "id", "reportLibrary", "sectionCd") // Extracts fields directly, bypassing getters
        .containsOnlyNulls();
  }
}
