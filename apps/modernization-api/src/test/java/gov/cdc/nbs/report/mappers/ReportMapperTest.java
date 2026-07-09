package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
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
import java.util.List;
import java.util.Random;
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
  @Mock private ReportFilterBuilder reportFilterBuilder;
  @Mock private ReportSortColumnMapper reportSortColumnMapper;
  @Mock private DisplayColumnBuilder displayColumnBuilder;

  private final Random random = new Random();

  private final Long userId = 123L;
  private final Long ownerId = 456L;
  private final Long dataSourceId = 789L;
  private final String reportTitle = "Test Report";
  private final String sectionCd = "SEC";
  private final String description = "Test Description";

  @InjectMocks private ReportMapper reportMapper;

  @BeforeEach
  void setUp() {
    user = mock(NbsUserDetails.class);
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
      verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
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
  }

  @Nested
  class Duplicate {
    Long nextReportId = 200L;

    @BeforeEach
    void setUp() {
      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(nextReportId));
    }

    @Test
    void duplicate_should_generate_new_id_for_report() {
      Report originalReport = buildReport();

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      assertThat(duplicatedReport.getId()).isNotNull();
      assertThat(duplicatedReport.getId()).isNotEqualTo(originalReport.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_report_fields() {
      Report originalReport = buildReport();

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

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
    }

    @Test
    void duplicate_should_always_update_report_status() {
      Report originalReport1 = buildReport();
      Report originalReport2 = buildReport();

      originalReport1.setStatus(
          new Status(Status.ACTIVE_CODE, LocalDateTime.now(clock).minusMonths(4)));
      originalReport2.setStatus(
          new Status(Status.INACTIVE_CODE, LocalDateTime.now(clock).minusYears(3)));

      Report duplicatedReport1 = reportMapper.duplicate(originalReport1, user);
      Report duplicatedReport2 = reportMapper.duplicate(originalReport2, user);

      for (Report report : List.of(duplicatedReport1, duplicatedReport2)) {
        assertThat(report.getAddTime()).isEqualTo(LocalDateTime.now(clock));

        assertThat(report.getStatus().status()).isEqualTo(Status.ACTIVE_CODE);
        assertThat(report.getStatus().appliedOn()).isEqualToIgnoringNanos(LocalDateTime.now(clock));
      }
    }

    @Test
    void duplicate_should_always_update_report_creator() {
      Report originalReport1 = buildReport();
      Report originalReport2 = buildReport();

      originalReport1.setAddTime(LocalDateTime.now(clock).minusMonths(7));
      originalReport2.setAddUserUid(random.nextLong(100000));
      originalReport2.setAddTime(LocalDateTime.now(clock).minusYears(5));
      originalReport2.setAddUserUid(random.nextLong(100000));

      Report duplicatedReport1 = reportMapper.duplicate(originalReport1, user);
      Report duplicatedReport2 = reportMapper.duplicate(originalReport2, user);

      for (Report report : List.of(duplicatedReport1, duplicatedReport2)) {
        assertThat(report.getAddTime()).isEqualToIgnoringNanos(LocalDateTime.now(clock));
        assertThat(report.getAddUserUid()).isEqualTo(user.getId());
      }
    }

    @Test
    void duplicate_should_generate_new_id_for_report_sort_columns() {
      Report originalReport = buildReport();

      ReportSortColumn originalSortColumn = buildReportSortColumn(originalReport);
      originalReport.setReportSortColumns(Collections.singletonList(originalSortColumn));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      assertThat(duplicatedReport.getReportSortColumns()).hasSize(1);

      ReportSortColumn duplicatedSortColumn = duplicatedReport.getReportSortColumns().getFirst();

      assertThat(duplicatedSortColumn).isNotNull();
      assertThat(duplicatedSortColumn.getId()).isNotNull();
      assertThat(duplicatedSortColumn.getId()).isNotEqualTo(originalSortColumn.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_report_sort_column_fields() {
      Report originalReport = buildReport();

      ReportSortColumn originalSortColumn = buildReportSortColumn(originalReport);
      originalReport.setReportSortColumns(Collections.singletonList(originalSortColumn));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      assertThat(duplicatedReport.getReportSortColumns()).hasSize(1);

      ReportSortColumn duplicatedSortColumn = duplicatedReport.getReportSortColumns().getFirst();

      assertThat(duplicatedSortColumn).isNotNull();
      assertThat(duplicatedSortColumn.getReportSortOrderCode())
          .isEqualTo(originalSortColumn.getReportSortOrderCode());
      assertThat(duplicatedSortColumn.getReportSortSequenceNum())
          .isEqualTo(originalSortColumn.getReportSortSequenceNum());
      assertThat(duplicatedSortColumn.getDataSourceColumnUid())
          .isEqualTo(originalSortColumn.getDataSourceColumnUid());
    }

    @Test
    void duplicate_should_always_update_report_sort_column_status() {
      Report originalReport = buildReport();

      ReportSortColumn originalSortColumn1 = buildReportSortColumn(originalReport);
      ReportSortColumn originalSortColumn2 = buildReportSortColumn(originalReport);

      originalSortColumn1.setStatusCd(Status.ACTIVE_CODE);
      originalSortColumn1.setStatusTime(LocalDateTime.now(clock).minusMonths(4));

      originalSortColumn2.setStatusCd(Status.INACTIVE_CODE);
      originalSortColumn2.setStatusTime(LocalDateTime.now(clock).minusYears(3));

      originalReport.setReportSortColumns(Arrays.asList(originalSortColumn1, originalSortColumn2));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      for (ReportSortColumn reportSortColumn : duplicatedReport.getReportSortColumns()) {
        assertThat(reportSortColumn.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
        assertThat(reportSortColumn.getStatusTime())
            .isEqualToIgnoringNanos(LocalDateTime.now(clock));
      }
    }

    @Test
    void duplicate_should_generate_new_id_for_display_columns() {
      Report originalReport = buildReport();

      DisplayColumn originalDisplayColumn = buildDisplayColumn(originalReport);
      originalReport.setDisplayColumns(Collections.singletonList(originalDisplayColumn));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      assertThat(duplicatedReport.getDisplayColumns()).hasSize(1);

      DisplayColumn duplicatedDisplayColumn = duplicatedReport.getDisplayColumns().getFirst();

      assertThat(duplicatedDisplayColumn).isNotNull();
      assertThat(duplicatedDisplayColumn.getId()).isNotNull();
      assertThat(duplicatedDisplayColumn.getId()).isNotEqualTo(originalDisplayColumn.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_display_column_fields() {
      Report originalReport = buildReport();

      DisplayColumn originalDisplayColumn = buildDisplayColumn(originalReport);
      originalReport.setDisplayColumns(Collections.singletonList(originalDisplayColumn));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      assertThat(duplicatedReport.getDisplayColumns()).hasSize(1);

      DisplayColumn duplicatedDisplayColumn = duplicatedReport.getDisplayColumns().getFirst();

      assertThat(duplicatedDisplayColumn).isNotNull();
      assertThat(duplicatedDisplayColumn.getReport()).isEqualTo(duplicatedReport);
      assertThat(duplicatedDisplayColumn.getDataSourceColumn())
          .isEqualTo(originalDisplayColumn.getDataSourceColumn());
      assertThat(duplicatedDisplayColumn.getDataSourceColumnId())
          .isEqualTo(originalDisplayColumn.getDataSourceColumnId());
      assertThat(duplicatedDisplayColumn.getSequenceNumber())
          .isEqualTo(originalDisplayColumn.getSequenceNumber());
    }

    @Test
    void duplicate_should_always_update_display_column_status() {
      Report originalReport = buildReport();

      DisplayColumn originalDisplayColumn1 = buildDisplayColumn(originalReport);
      DisplayColumn originalDisplayColumn2 = buildDisplayColumn(originalReport);

      originalDisplayColumn1.setStatusCd(Status.ACTIVE_CODE);
      originalDisplayColumn1.setStatusTime(LocalDateTime.now(clock).minusMonths(4));

      originalDisplayColumn2.setStatusCd(Status.INACTIVE_CODE);
      originalDisplayColumn2.setStatusTime(LocalDateTime.now(clock).minusYears(3));

      originalReport.setDisplayColumns(
          Arrays.asList(originalDisplayColumn1, originalDisplayColumn2));

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      for (DisplayColumn displayColumn : duplicatedReport.getDisplayColumns()) {
        assertThat(displayColumn.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
        assertThat(displayColumn.getStatusTime()).isEqualToIgnoringNanos(LocalDateTime.now(clock));
      }
    }

    @Test
    void duplicate_should_duplicate_report_filters() {
      Report originalReport = buildReport();

      ReportFilter originalReportFilter = buildReportFilter(originalReport);
      originalReport.setReportFilters(Collections.singletonList(originalReportFilter));

      ReportFilter expectedReportFilter = buildReportFilter(originalReport);
      when(reportFilterBuilder.duplicate(originalReportFilter)).thenReturn(expectedReportFilter);

      Report duplicatedReport = reportMapper.duplicate(originalReport, user);

      verify(reportFilterBuilder).duplicate(originalReportFilter);

      assertThat(duplicatedReport.getReportFilters()).hasSize(1);

      ReportFilter duplicatedReportFilter = duplicatedReport.getReportFilters().getFirst();
      assertThat(duplicatedReportFilter.getReport()).isEqualTo(duplicatedReport);
    }
  }

  private Report buildReport() {
    ReportId reportId = new ReportId(random.nextLong(100000), dataSourceId);
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

  private ReportFilter buildReportFilter(Report report) {
    return ReportFilter.builder()
        .id(random.nextLong(1000))
        .report(report)
        .filterCode(mock(FilterCode.class))
        .dataSourceColumn(mock(DataSourceColumn.class))
        .statusCd(Status.ACTIVE_CODE)
        .minValueCnt(1)
        .maxValueCnt(5)
        .build();
  }

  private ReportSortColumn buildReportSortColumn(Report report) {
    return ReportSortColumn.builder()
        .id(random.nextLong(10000))
        .reportSortOrderCode("random code")
        .reportSortSequenceNum(random.nextInt(1000))
        .report(report)
        .dataSourceColumnUid(random.nextLong(1000))
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(LocalDateTime.now(clock))
        .build();
  }

  private DisplayColumn buildDisplayColumn(Report report) {
    return DisplayColumn.builder()
        .id(random.nextLong(100000))
        .dataSourceColumn(mock(DataSourceColumn.class))
        .dataSourceColumnId(random.nextLong(100000))
        .report(report)
        .sequenceNumber(random.nextInt(1000))
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(LocalDateTime.now(clock))
        .build();
  }
}
