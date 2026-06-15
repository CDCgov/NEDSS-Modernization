package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportMapperTest {

  @Mock private NbsUserDetails user;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private IdGeneratorService idGenerator;

  private final Long userId = 123L;
  private final Long ownerId = 456L;
  private final Long dataSourceId = 789L;
  private final String reportTitle = "Test Report";
  private final String sectionCd = "SEC";
  private final String description = "Test Description";

  @InjectMocks private ReportMapper reportMapper;

  private AdminReportRequest buildAdminReportRequest(ReportConstants.ReportGroup group) {
    return new AdminReportRequest(
        dataSourceId,
        1L,
        reportTitle,
        sectionCd,
        ownerId,
        group,
        Collections.emptyList(),
        description);
  }

  @BeforeEach
  void setUp() {
    user = Mockito.mock(NbsUserDetails.class);
    Mockito.lenient().when(user.getId()).thenReturn(userId);

    Mockito.lenient().when(dataSource.getId()).thenReturn(dataSourceId);

    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(new IdGeneratorService.GeneratedId(47L));

    Mockito.lenient().when(reportLibrary.getLibraryName()).thenReturn("hello.sas");
    Mockito.lenient().when(reportLibrary.getColumnSelectInd()).thenReturn('Y');
  }

  @Test
  void fromAdminReportRequest_should_set_all_fields_when_existing_report_id_is_null() {
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    LocalDateTime beforeCreation = LocalDateTime.now();

    Long nextReportId = 100L;
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(new IdGeneratorService.GeneratedId(nextReportId));

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);
    LocalDateTime afterCreation = LocalDateTime.now();

    assertThat(result.getId()).isNotNull();
    assertThat(result.getId().getDataSourceUid()).isEqualTo(request.dataSourceId());
    Mockito.verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
    assertThat(result.getId().getReportUid()).isEqualTo(nextReportId);

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
    assertThat(result.getLocation()).isEqualTo("hello.sas");
    assertThat(result.getReportTypeCode()).isEqualTo("SAS_CUSTOM");
  }

  @Test
  void fromAdminReportRequest_should_set_all_fields_correctly_with_existing_id() {
    ReportId existingReportId = new ReportId(50L, 75L);
    Report existingReport = new Report(existingReportId);
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    LocalDateTime beforeCreation = LocalDateTime.now();
    Report result =
        reportMapper.fromAdminReportRequest(
            request, user, reportLibrary, dataSource, existingReport);
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
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('P');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_R_when_group_is_reporting_facility() {
    AdminReportRequest request =
        buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('R');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_S_when_group_is_public() {
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.PUBLIC);

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('S');
  }

  @Test
  void fromAdminReportRequest_should_set_shared_field_to_T_when_group_is_template() {
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.TEMPLATE);

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getShared()).isEqualTo('T');
  }

  @Test
  void fromAdminReportRequest_should_set_report_type_code_to_html_when_no_column_select() {
    Mockito.lenient().when(reportLibrary.getColumnSelectInd()).thenReturn('N');
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.TEMPLATE);

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getReportTypeCode()).isEqualTo("SAS_ODS_HTML");
  }

  @Test
  void fromAdminReportRequest_should_not_set_report_type_code_or_location_if_not_sas() {
    Mockito.lenient().when(reportLibrary.getLibraryName()).thenReturn("py_lib");
    AdminReportRequest request = buildAdminReportRequest(ReportConstants.ReportGroup.TEMPLATE);

    Report result =
        reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

    assertThat(result.getReportTypeCode()).isNull();
    assertThat(result.getLocation()).isNull();
  }
}
