package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class ReportMapperTest {

  @Mock private NbsUserDetails user;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;

  private final Long userId = 123L;
  private final Long ownerId = 456L;
  private final String reportTitle = "Test Report";
  private final String sectionCd = "SEC";
  private final String description = "Test Description";

  private AdminReportRequest buildAdminReportRequest(ReportConstants.ReportGroup group) {
    return new AdminReportRequest(
        2L,
        1L,
        reportTitle,
        sectionCd,
        ownerId,
        group.toString(),
        Collections.emptyList(),
        description);
  }

  @BeforeEach
  void setUp() {
    user = Mockito.mock(NbsUserDetails.class);
    Mockito.lenient().when(user.getId()).thenReturn(userId);
  }

  @Test
  void fromAdminReportRequest_should_set_all_fields_when_existing_report_id_is_null() {
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    LocalDateTime beforeCreation = LocalDateTime.now();
    Report result =
        ReportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);
    LocalDateTime afterCreation = LocalDateTime.now();

    assertThat(result.getId()).isNull();

    // Should always be set to 'N'
    assertThat(result.getIsModifiableIndicator()).isEqualTo('N');
    // Should always be set to 'ACTIVE'
    assertThat(result.getStatus()).isNotNull();
    assertThat(result.getStatus().status()).isEqualTo(Status.ACTIVE_CODE);
    assertThat(result.getStatus().appliedOn()).isBetween(beforeCreation, afterCreation);

    assertThat(result.getDataSource()).isEqualTo(dataSource);
    assertThat(result.getAddTime()).isNotNull().isBetween(beforeCreation, afterCreation);
    assertThat(result.getAddUserUid()).isEqualTo(userId);
    assertThat(result.getDescTxt()).isEqualTo(description);
    assertThat(result.getOwnerUid()).isEqualTo(ownerId);
    assertThat(result.getReportTitle()).isEqualTo(reportTitle);
    assertThat(result.getShared()).isEqualTo('R');
    assertThat(result.getSectionCd()).isEqualTo(sectionCd);
    assertThat(result.getReportLibrary()).isEqualTo(reportLibrary);
  }

  @Test
  void fromAdminReportRequest_should_set_all_fields_correctly_with_existing_id() {
    ReportId existingReportId = new ReportId(50L, 75L);
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    LocalDateTime beforeCreation = LocalDateTime.now();
    Report result =
        ReportMapper.fromAdminReportRequest(
            request, user, reportLibrary, dataSource, existingReportId);
    LocalDateTime afterCreation = LocalDateTime.now();

    assertThat(result.getId()).isEqualTo(existingReportId);

    // Should always be set to 'N'
    assertThat(result.getIsModifiableIndicator()).isEqualTo('N');
    // Should always be set to 'ACTIVE'
    assertThat(result.getStatus()).isNotNull();
    assertThat(result.getStatus().status()).isEqualTo(Status.ACTIVE_CODE);
    assertThat(result.getStatus().appliedOn()).isBetween(beforeCreation, afterCreation);

    assertThat(result.getDataSource()).isEqualTo(dataSource);
    assertThat(result.getDescTxt()).isEqualTo(description);
    assertThat(result.getOwnerUid()).isEqualTo(ownerId);
    assertThat(result.getReportTitle()).isEqualTo(reportTitle);
    assertThat(result.getShared()).isEqualTo('R');
    assertThat(result.getSectionCd()).isEqualTo(sectionCd);
    assertThat(result.getReportLibrary()).isEqualTo(reportLibrary);
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_P_when_group_is_private() {
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.PRIVATE);

    Report result =
        ReportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('P');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_R_when_group_is_reporting_facility() {
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    Report result =
        ReportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('R');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_S_when_group_is_public() {
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.PUBLIC);

    Report result =
        ReportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('S');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_T_when_group_is_template() {
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.TEMPLATE);

    Report result =
        ReportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('T');
  }
}
