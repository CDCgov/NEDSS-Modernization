package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.DisplayColumnBuilder;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.ReportFilterBuilder;
import gov.cdc.nbs.report.models.AdminReportRequest;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportMapperTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(100000), ZoneId.systemDefault());
  @Mock private NbsUserDetails user;
  @Mock private ReportLibrary reportLibrary;
  @Mock private DataSource dataSource;
  @Mock private IdGeneratorService idGenerator;
  @Mock private ReportSortColumnMapper reportSortColumnMapper;
  @Mock private ReportFilterBuilder reportFilterBuilder;
  @Mock private DisplayColumnBuilder displayColumnBuilder;

  private final Long userId = 123L;
  private final Long ownerId = 456L;
  private final Long dataSourceId = 789L;
  private final String reportTitle = "Test Report";
  private final String sectionCd = "SEC";
  private final String description = "Test Description";

  @InjectMocks private ReportMapper reportMapper;

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

  @Nested
  class FromAdminReportRequest {
    @Test
    void fromAdminReportRequest_should_set_all_fields_when_existing_report_id_is_null() {
      AdminReportRequest request =
          buildAdminReportRequest(ReportConstants.ReportGroup.REPORTING_FACILITY);

      Long nextReportId = 100L;
      Mockito.lenient()
          .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(nextReportId));

      Report result =
          reportMapper.fromAdminReportRequest(request, user, reportLibrary, dataSource, null);

      assertThat(result.getId()).isNotNull();
      assertThat(result.getId().getDataSourceUid()).isEqualTo(request.dataSourceId());
      Mockito.verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
      assertThat(result.getId().getReportUid()).isEqualTo(nextReportId);

      // Should always be set to 'N'
      assertThat(result.getIsModifiableIndicator()).isEqualTo('N');
      // Should always be set to 'ACTIVE'
      assertThat(result.getStatus()).isNotNull();
      assertThat(result.getStatus().status()).isEqualTo(Status.ACTIVE_CODE);
      assertThat(result.getStatus().appliedOn()).isEqualTo(LocalDateTime.now(clock));

      assertThat(result.getDataSource()).isEqualTo(dataSource);
      assertThat(result.getAddTime()).isNotNull().isEqualTo(LocalDateTime.now(clock));
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

      Report result =
          reportMapper.fromAdminReportRequest(
              request, user, reportLibrary, dataSource, existingReport);

      assertThat(result.getId()).isEqualTo(existingReportId);
      // we update the existing report, so should be equal
      assertThat(result).isEqualTo(existingReport);

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

  @Nested
  class Duplicate {
    Long nextReportId = 200L;

    @BeforeEach
    void setUp() {
      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(nextReportId));
    }

    @Test
    void duplicate_should_preserve_all_basic_fields() {
      Report originalReport = buildTestReport();

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(100L));

      Report duplicatedReport = reportMapper.duplicate(originalReport);

      assertThat(duplicatedReport.getDataSource()).isEqualTo(originalReport.getDataSource());
      assertThat(duplicatedReport.getReportLibrary()).isEqualTo(originalReport.getReportLibrary());
      assertThat(duplicatedReport.getDescTxt()).isEqualTo(originalReport.getDescTxt());
      assertThat(duplicatedReport.getEffectiveTime()).isEqualTo(originalReport.getEffectiveTime());
      assertThat(duplicatedReport.getFilterMode()).isEqualTo(originalReport.getFilterMode());
      assertThat(duplicatedReport.getIsModifiableIndicator())
          .isEqualTo(originalReport.getIsModifiableIndicator());
      assertThat(duplicatedReport.getLocation()).isEqualTo(originalReport.getLocation());
      assertThat(duplicatedReport.getOwnerUid()).isEqualTo(originalReport.getOwnerUid());
      assertThat(duplicatedReport.getOrgAccessPermission())
          .isEqualTo(originalReport.getOrgAccessPermission());
      assertThat(duplicatedReport.getProgAreaAccessPermission())
          .isEqualTo(originalReport.getProgAreaAccessPermission());
      assertThat(duplicatedReport.getReportTitle()).isEqualTo(originalReport.getReportTitle());
      assertThat(duplicatedReport.getReportTypeCode())
          .isEqualTo(originalReport.getReportTypeCode());
      assertThat(duplicatedReport.getShared()).isEqualTo(originalReport.getShared());
      assertThat(duplicatedReport.getCategory()).isEqualTo(originalReport.getCategory());
      assertThat(duplicatedReport.getSectionCd()).isEqualTo(originalReport.getSectionCd());
      assertThat(duplicatedReport.getAddTime()).isEqualTo(originalReport.getAddTime());
      assertThat(duplicatedReport.getAddUserUid()).isEqualTo(originalReport.getAddUserUid());
      assertThat(duplicatedReport.getStatus()).isEqualTo(originalReport.getStatus());
    }

    @Test
    void duplicate_should_have_different_id_than_original() {
      Report originalReport = buildTestReport();

      Report duplicatedReport = reportMapper.duplicate(originalReport);

      assertThat(duplicatedReport.getId()).isNotEqualTo(originalReport.getId());
      assertThat(duplicatedReport.getId().getReportUid()).isEqualTo(nextReportId);
    }

    @Test
    void duplicate_should_duplicate_report_sort_columns() {
      Report originalReport = buildTestReport();
      ReportSortColumn sortColumn1 = mock(ReportSortColumn.class);
      ReportSortColumn sortColumn2 = mock(ReportSortColumn.class);

      originalReport.setReportSortColumns(Arrays.asList(sortColumn1, sortColumn2));

      ReportSortColumn duplicatedSortColumn1 = mock(ReportSortColumn.class);
      ReportSortColumn duplicatedSortColumn2 = mock(ReportSortColumn.class);

      Mockito.when(reportSortColumnMapper.duplicate(sortColumn1)).thenReturn(duplicatedSortColumn1);
      Mockito.when(reportSortColumnMapper.duplicate(sortColumn2)).thenReturn(duplicatedSortColumn2);

      Report duplicatedReport = reportMapper.duplicate(originalReport);

      verify(reportSortColumnMapper).duplicate(sortColumn1);
      verify(reportSortColumnMapper).duplicate(sortColumn2);

      assertThat(duplicatedReport.getReportSortColumns())
          .hasSize(2)
          .containsExactly(duplicatedSortColumn1, duplicatedSortColumn2);
    }

    @Test
    void duplicate_should_duplicate_report_filters() {
      Report originalReport = buildTestReport();

      ReportFilter reportFilter1 = mock(ReportFilter.class);
      ReportFilter reportFilter2 = mock(ReportFilter.class);
      originalReport.setReportFilters(Arrays.asList(reportFilter1, reportFilter2));

      ReportFilter duplicatedFilter1 = mock(ReportFilter.class);
      ReportFilter duplicatedFilter2 = mock(ReportFilter.class);

      Mockito.when(reportFilterBuilder.duplicate(reportFilter1)).thenReturn(duplicatedFilter1);
      Mockito.when(reportFilterBuilder.duplicate(reportFilter2)).thenReturn(duplicatedFilter2);

      Report duplicatedReport = reportMapper.duplicate(originalReport);

      verify(reportFilterBuilder).duplicate(reportFilter1);
      verify(reportFilterBuilder).duplicate(reportFilter2);

      assertThat(duplicatedReport.getReportFilters())
          .hasSize(2)
          .containsExactly(duplicatedFilter1, duplicatedFilter2);
    }

    @Test
    void duplicate_should_duplicate_display_columns() {
      Report originalReport = buildTestReport();

      DisplayColumn displayColumn1 = mock(DisplayColumn.class);
      DisplayColumn displayColumn2 = mock(DisplayColumn.class);
      originalReport.setDisplayColumns(Arrays.asList(displayColumn1, displayColumn2));

      DisplayColumn duplicatedDisplayColumn1 = mock(DisplayColumn.class);
      DisplayColumn duplicatedDisplayColumn2 = mock(DisplayColumn.class);

      Mockito.when(displayColumnBuilder.duplicate(displayColumn1))
          .thenReturn(duplicatedDisplayColumn1);
      Mockito.when(displayColumnBuilder.duplicate(displayColumn2))
          .thenReturn(duplicatedDisplayColumn2);

      Report duplicatedReport = reportMapper.duplicate(originalReport);

      verify(displayColumnBuilder).duplicate(displayColumn1);
      verify(displayColumnBuilder).duplicate(displayColumn2);

      assertThat(duplicatedReport.getDisplayColumns())
          .hasSize(2)
          .containsExactly(duplicatedDisplayColumn1, duplicatedDisplayColumn2);
    }
  }

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

  private Report buildTestReport() {
    ReportId reportId = new ReportId(50L, dataSourceId);
    return Report.builder()
        .id(reportId)
        .dataSource(dataSource)
        .reportLibrary(reportLibrary)
        .descTxt("Test Description")
        .addTime(LocalDateTime.now(clock))
        .filterMode('B')
        .isModifiableIndicator('N')
        .location("test location")
        .ownerUid(ownerId)
        .reportTitle("Test Report")
        .reportTypeCode("SAS_CUSTOM")
        .shared(ReportConstants.reportGroupToDbChar(ReportConstants.ReportGroup.PUBLIC))
        .category("Test Category")
        .sectionCd(sectionCd)
        .addTime(LocalDateTime.now(clock))
        .addUserUid(userId)
        .status(new Status(Status.ACTIVE_CODE, LocalDateTime.now(clock)))
        .build();
  }
}
