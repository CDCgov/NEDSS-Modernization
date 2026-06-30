package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.exception.NotFoundException;
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.mappers.ReportMapper;
import gov.cdc.nbs.report.mappers.ReportSortColumnMapper;
import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.repository.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
class ReportServiceTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private ReportRepository reportRepository;
  @Mock private DataSourceRepository dataSourceRepository;
  @Mock private ReportLibraryRepository reportLibraryRepository;
  @Mock private FilterCodeRepository filterCodeRepository;
  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @Mock private ReportSectionRepository reportSectionRepository;
  @Mock private ReportFilterRepository reportFilterRepository;
  @Mock private ReportMapper reportMapper;
  @Mock private ReportSortColumnMapper reportSortColumnMapper;
  @Mock private FilterValueMapper filterValueMapper;
  @Mock private DisplayColumnBuilder displayColumnBuilder;
  @Mock private ReportFetcher reportFetcher;

  @Mock private DataSource dataSource;
  @Mock private ReportFilterBuilder reportFilterBuilder;

  @InjectMocks private ReportService service;

  private final Long reportUid = 1L;
  private final Long dataSourceUid = 2L;
  private final Long libraryId = 20L;

  @Nested
  class CreateReport {
    private final Long filterCodeUid = 7L;
    private final Long columnUid = 8L;
    private final String sectionCd = "1000";

    private NbsUserDetails mockUser;
    private DataSource mockDataSource;
    private ReportLibrary mockReportLibrary;

    @BeforeEach
    void setup() {
      mockDataSource = mock(DataSource.class);
      mockReportLibrary = mock(ReportLibrary.class);
      mockUser = mock(NbsUserDetails.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      Mockito.lenient().when(mockFilterCode.getId()).thenReturn(filterCodeUid);

      DataSourceColumn mockColumn = mock(DataSourceColumn.class);
      Mockito.lenient().when(mockColumn.getId()).thenReturn(columnUid);

      Mockito.lenient()
          .when(dataSourceRepository.findById(dataSourceUid))
          .thenReturn(Optional.of(mockDataSource));
      Mockito.lenient()
          .when(reportLibraryRepository.findById(libraryId))
          .thenReturn(Optional.of(mockReportLibrary));
      Mockito.lenient()
          .when(filterCodeRepository.findById(any()))
          .thenReturn(Optional.of(mockFilterCode));
      Mockito.lenient()
          .when(dataSourceColumnRepository.findById(any()))
          .thenReturn(Optional.of(mockColumn));
      Mockito.lenient().when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(true);
    }

    private AdminReportRequest buildAdminReportRequest(Boolean includeFilters) {
      List<UpsertFilterRequest> filterRequests =
          includeFilters
              ? List.of(
                  new UpsertFilterRequest(
                      null, 7L, columnUid, ReportConstants.SelectType.MULTI, false))
              : Collections.emptyList();
      return new AdminReportRequest(
          dataSourceUid,
          libraryId,
          "Test Report Title",
          sectionCd,
          0L,
          ReportConstants.ReportGroup.REPORTING_FACILITY,
          filterRequests,
          "Test Report Description");
    }

    @Test
    void createReport_should_create_and_return_report_when_all_inputs_are_valid() {
      Report savedReport = mock(Report.class);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, null))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.createReport(request, mockUser);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void createReport_should_create_report_when_filters_are_empty() {
      Report savedReport = mock(Report.class);

      AdminReportRequest request = buildAdminReportRequest(false);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, null))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.createReport(request, mockUser);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportMapper)
          .fromAdminReportRequest(request, mockUser, mockReportLibrary, mockDataSource, null);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void createReport_should_throw_when_data_source_not_found() {
      when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No data source found for ID " + dataSourceUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository, never()).findById(any());

      verify(reportRepository, never()).save(any());
    }

    @Test
    void createReport_should_throw_when_report_library_not_found() {
      when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report library found for ID " + libraryId);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void createReport_should_throw_when_report_section_not_found() {
      when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(false);

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.createReport(request, mockUser))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report section found for code " + sectionCd);

      verify(reportRepository, never()).save(any());
    }
  }

  @Nested
  class EditReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;
    private final Long libraryId = 20L;
    private final Long filterCodeUid = 7L;
    private final Long columnUid = 8L;
    private final String sectionCd = "1000";

    private NbsUserDetails mockUser;
    private DataSource mockDataSource;
    private ReportLibrary mockReportLibrary;
    private Report savedReport;
    private ReportId reportId = new ReportId(reportUid, dataSourceUid);

    @BeforeEach
    void setup() {
      mockDataSource = mock(DataSource.class);
      mockReportLibrary = mock(ReportLibrary.class);
      mockUser = mock(NbsUserDetails.class);
      savedReport = mock(Report.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      Mockito.lenient().when(mockFilterCode.getId()).thenReturn(filterCodeUid);

      DataSourceColumn mockColumn = mock(DataSourceColumn.class);
      Mockito.lenient().when(mockColumn.getId()).thenReturn(columnUid);

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(Optional.of(savedReport));

      Mockito.lenient()
          .when(dataSourceRepository.findById(dataSourceUid))
          .thenReturn(Optional.of(mockDataSource));
      Mockito.lenient()
          .when(reportLibraryRepository.findById(libraryId))
          .thenReturn(Optional.of(mockReportLibrary));
      Mockito.lenient()
          .when(filterCodeRepository.findById(any()))
          .thenReturn(Optional.of(mockFilterCode));
      Mockito.lenient()
          .when(dataSourceColumnRepository.findById(any()))
          .thenReturn(Optional.of(mockColumn));
      Mockito.lenient().when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(true);
    }

    private AdminReportRequest buildAdminReportRequest(Boolean includeFilters) {
      List<UpsertFilterRequest> filterRequests =
          includeFilters
              ? List.of(
                  new UpsertFilterRequest(
                      null, filterCodeUid, columnUid, ReportConstants.SelectType.MULTI, false))
              : Collections.emptyList();
      return new AdminReportRequest(
          dataSourceUid,
          libraryId,
          "Test Report Title",
          sectionCd,
          0L,
          ReportConstants.ReportGroup.REPORTING_FACILITY,
          filterRequests,
          "Test Report Description");
    }

    @Test
    void editReport_should_edit_and_return_report_when_all_inputs_are_valid() {
      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);
      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void editReport_should_create_report_when_filters_are_empty() {
      AdminReportRequest request = buildAdminReportRequest(false);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository).save(any(Report.class));
    }

    @Test
    void editReport_should_throw_when_data_source_not_found() {
      when(dataSourceRepository.findById(dataSourceUid)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No data source found for ID " + dataSourceUid);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository, never()).findById(any());

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_throw_when_report_library_not_found() {
      when(reportLibraryRepository.findById(libraryId)).thenReturn(Optional.empty());

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report library found for ID " + libraryId);

      verify(dataSourceRepository).findById(dataSourceUid);
      verify(reportLibraryRepository).findById(libraryId);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_throw_when_report_section_not_found() {
      when(reportSectionRepository.existsBySectionCd(sectionCd)).thenReturn(false);

      AdminReportRequest request = buildAdminReportRequest(true);

      assertThatThrownBy(() -> service.editReport(request, mockUser, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("No report section found for code " + sectionCd);

      verify(reportRepository, never()).save(any());
    }

    @Test
    void editReport_should_delete_filters_not_included_in_request() {
      FilterCode filterCode = mock(FilterCode.class);
      ArrayList<ReportFilter> existingFilters = new ArrayList<>();
      existingFilters.add(new ReportFilter(100L, filterCode));
      existingFilters.add(new ReportFilter(101L, filterCode));

      Mockito.lenient().when(reportRepository.save(any(Report.class))).thenReturn(savedReport);
      Mockito.lenient().when(savedReport.getReportFilters()).thenReturn(existingFilters);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      // updates the structure
      assertThat(result.getReportFilters()).isEqualTo(existingFilters);
      assertThat(existingFilters).hasSize(1);
    }

    @Test
    void editReport_should_upsert_filters_included_in_request() {
      FilterCode filterCode = mock(FilterCode.class);
      ArrayList<ReportFilter> existingFilters = new ArrayList<>();
      existingFilters.add(new ReportFilter(100L, filterCode));
      existingFilters.add(new ReportFilter(101L, filterCode));

      Mockito.lenient().when(reportRepository.save(any(Report.class))).thenReturn(savedReport);
      Mockito.lenient().when(savedReport.getReportFilters()).thenReturn(existingFilters);

      ReportFilter mockReportFilter = mock(ReportFilter.class);
      Mockito.lenient()
          .when(reportFilterBuilder.build(any(UpsertFilterRequest.class), any(Report.class)))
          .thenReturn(mockReportFilter);

      AdminReportRequest request = buildAdminReportRequest(true);

      when(reportMapper.fromAdminReportRequest(
              request, mockUser, mockReportLibrary, mockDataSource, savedReport))
          .thenReturn(savedReport);
      when(reportRepository.save(savedReport)).thenReturn(savedReport);

      Report result = service.editReport(request, mockUser, reportId);

      assertThat(result).isEqualTo(savedReport);
      verify(reportFilterBuilder).build(request.filterRequests().getFirst(), savedReport);
    }
  }

  @Nested
  class DeleteReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;

    private Report savedReport;
    private ReportId reportId = new ReportId(reportUid, dataSourceUid);

    @BeforeEach
    void setup() {
      savedReport = mock(Report.class);

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(Optional.of(savedReport));
    }

    @Test
    void deleteReport_should_delete_and_return_report_when_all_inputs_are_valid() {
      service.deleteReport(reportId);

      verify(reportRepository).findById(reportId);
      verify(reportRepository).delete(any(Report.class));
    }

    @Test
    void deleteReport_should_delete_and_throws_when_unknown_report() {
      when(reportRepository.findById(reportId)).thenReturn(Optional.ofNullable(null));

      assertThatThrownBy(() -> service.deleteReport(reportId))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining("Report not found for Report UID: 1 and Data Source UID: 2");

      verify(reportRepository).findById(reportId);
    }
  }

  @Nested
  class SaveReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;
    private final ReportId reportId = new ReportId(reportUid, dataSourceUid);
    private Report savedReport;

    @BeforeEach
    void setup() {
      savedReport = mock(Report.class);

      Mockito.lenient()
          .when(reportRepository.findById(reportId))
          .thenReturn(Optional.of(savedReport));
      Mockito.lenient().when(reportRepository.save(any(Report.class))).thenReturn(savedReport);
    }

    @Test
    void saveReport_should_update_display_columns_when_provided() {
      List<Long> displayColumnIds = List.of(3L, 4L, 5L);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, displayColumnIds, null, List.of(), null);

      DataSourceColumn column1 = mock(DataSourceColumn.class);
      DataSourceColumn column2 = mock(DataSourceColumn.class);
      DataSourceColumn column3 = mock(DataSourceColumn.class);

      when(column1.getId()).thenReturn(3L);
      when(column2.getId()).thenReturn(4L);
      when(column3.getId()).thenReturn(5L);
      when(dataSource.getDataSourceColumns()).thenReturn(List.of(column1, column2, column3));
      when(savedReport.getDataSource()).thenReturn(dataSource);
      when(savedReport.getDisplayColumns()).thenReturn(new ArrayList<>());

      DisplayColumn displayColumn1 = mock(DisplayColumn.class);
      DisplayColumn displayColumn2 = mock(DisplayColumn.class);
      DisplayColumn displayColumn3 = mock(DisplayColumn.class);

      when(displayColumnBuilder.build(savedReport, column1)).thenReturn(displayColumn1);
      when(displayColumnBuilder.build(savedReport, column2)).thenReturn(displayColumn2);
      when(displayColumnBuilder.build(savedReport, column3)).thenReturn(displayColumn3);

      Report result = service.saveReport(request, reportId);

      verify(savedReport, times(2)).getDisplayColumns();
      verify(displayColumnBuilder, times(3)).build(any(Report.class), any(DataSourceColumn.class));

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_clear_display_columns_when_null() {
      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, List.of(), null);

      Report result = service.saveReport(request, reportId);

      verify(savedReport).setDisplayColumns(null);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_clear_display_columns_when_empty() {
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, new ArrayList<>(), null, List.of(), null);

      Report result = service.saveReport(request, reportId);

      verify(savedReport).setDisplayColumns(null);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_throw_when_column_not_found() {
      List<Long> displayColumnIds = List.of(3L, 999L);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, displayColumnIds, null, List.of(), null);

      DataSourceColumn column1 = mock(DataSourceColumn.class);

      when(column1.getId()).thenReturn(3L);
      when(dataSource.getDataSourceColumns()).thenReturn(List.of(column1));
      when(savedReport.getDataSource()).thenReturn(dataSource);
      when(savedReport.getDisplayColumns()).thenReturn(new ArrayList<>());

      assertThatThrownBy(() -> service.saveReport(request, reportId))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining("No matching column found for ID 999");
    }

    @Test
    void saveReport_should_update_sort_column_when_provided() {
      SortSpec sortSpec = new SortSpec(100L, ReportConstants.SortDirection.ASC);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), sortSpec, List.of(), null);

      ReportSortColumn sortColumn = mock(ReportSortColumn.class);
      List<ReportSortColumn> sortColumns =
          spy(new ArrayList<>(Collections.singletonList(sortColumn)));

      when(reportSortColumnMapper.fromSortSpec(savedReport, sortSpec)).thenReturn(sortColumn);
      when(savedReport.getReportSortColumns()).thenReturn(sortColumns);

      Report result = service.saveReport(request, reportId);

      verify(reportSortColumnMapper).fromSortSpec(savedReport, sortSpec);
      verify(savedReport, times(2)).getReportSortColumns();
      verify(sortColumns).clear();
      verify(sortColumns).add(sortColumn);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_clear_sort_column_when_null() {
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), null);

      Report result = service.saveReport(request, reportId);

      verify(savedReport).setReportSortColumns(null);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_update_basic_filters_when_provided() {
      Long basicFilterUid = 10L;
      BasicFilterRequest basicFilterRequest =
          new BasicFilterRequest(basicFilterUid, List.of("value1"), false);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(basicFilterRequest), null);

      ReportFilter basicFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.BASIC_FILTER_PREFIX + "TEST");
      when(basicFilter.getId()).thenReturn(basicFilterUid);
      when(basicFilter.getFilterCode()).thenReturn(filterCode);
      when(basicFilter.getFilterValues()).thenReturn(new ArrayList<>());
      when(savedReport.getReportFilters()).thenReturn(List.of(basicFilter));

      List<FilterValue> filterValues = List.of(mock(FilterValue.class));
      when(filterValueMapper.fromBasicFilterRequest(basicFilter, basicFilterRequest))
          .thenReturn(filterValues);

      Report result = service.saveReport(request, reportId);

      verify(filterValueMapper).fromBasicFilterRequest(basicFilter, basicFilterRequest);
      verify(reportFilterRepository).saveAll(any());

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_throw_when_basic_filter_uid_mismatch() {
      Long basicFilterUid = 10L;
      Long differentUid = 20L;
      BasicFilterRequest basicFilterRequest =
          new BasicFilterRequest(differentUid, List.of("value1"), false);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(basicFilterRequest), null);

      ReportFilter basicFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.BASIC_FILTER_PREFIX + "TEST");
      when(basicFilter.getId()).thenReturn(basicFilterUid);
      when(basicFilter.getFilterCode()).thenReturn(filterCode);
      when(savedReport.getReportFilters()).thenReturn(List.of(basicFilter));

      assertThatThrownBy(() -> service.saveReport(request, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(
              "BasicFilterRequest.reportFilterUid does not match existing basic filter ID");
    }

    @Test
    void saveReport_should_clear_basic_filters_when_values_empty() {
      Long basicFilterUid = 10L;
      BasicFilterRequest basicFilterRequest =
          new BasicFilterRequest(basicFilterUid, List.of(), false);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(basicFilterRequest), null);

      ReportFilter basicFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.BASIC_FILTER_PREFIX + "TEST");
      when(basicFilter.getId()).thenReturn(basicFilterUid);
      when(basicFilter.getFilterCode()).thenReturn(filterCode);
      when(savedReport.getReportFilters()).thenReturn(List.of(basicFilter));

      Report result = service.saveReport(request, reportId);

      verify(basicFilter).setFilterValues(null);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_clear_all_basic_filters_when_no_requests() {
      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, List.of(), null, null, null);

      ReportFilter basicFilter1 = mock(ReportFilter.class);
      when(basicFilter1.getId()).thenReturn(10L);
      ReportFilter basicFilter2 = mock(ReportFilter.class);
      when(basicFilter2.getId()).thenReturn(20L);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.BASIC_FILTER_PREFIX + "TEST");
      when(basicFilter1.getFilterCode()).thenReturn(filterCode);
      when(basicFilter2.getFilterCode()).thenReturn(filterCode);
      when(savedReport.getReportFilters()).thenReturn(List.of(basicFilter1, basicFilter2));

      Report result = service.saveReport(request, reportId);

      verify(basicFilter1).setFilterValues(null);
      verify(basicFilter2).setFilterValues(null);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_update_advanced_filter_when_provided() {
      Long advancedFilterUid = 15L;
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest advancedFilterRequest =
          new AdvancedFilterRequest(advancedFilterUid, ruleGroup);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), advancedFilterRequest);

      ReportFilter advancedFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.ADV_FILTER_TYPE);
      when(advancedFilter.getId()).thenReturn(advancedFilterUid);
      when(advancedFilter.getFilterCode()).thenReturn(filterCode);
      when(advancedFilter.getFilterValues()).thenReturn(new ArrayList<>());
      when(savedReport.getReportFilters()).thenReturn(List.of(advancedFilter));

      List<FilterValue> filterValues = List.of(mock(FilterValue.class));
      when(filterValueMapper.fromAdvancedFilterRequest(advancedFilter, advancedFilterRequest))
          .thenReturn(filterValues);

      Report result = service.saveReport(request, reportId);

      verify(filterValueMapper).fromAdvancedFilterRequest(advancedFilter, advancedFilterRequest);
      verify(reportFilterRepository).save(advancedFilter);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_throw_when_advanced_filter_provided_but_not_exists() {
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest advancedFilterRequest = new AdvancedFilterRequest(15L, ruleGroup);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), advancedFilterRequest);

      when(savedReport.getReportFilters()).thenReturn(List.of());

      assertThatThrownBy(() -> service.saveReport(request, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(
              "AdvancedFilterRequest included for report without an advanced filter");
    }

    @Test
    void saveReport_should_throw_when_advanced_filter_uid_mismatch() {
      Long advancedFilterUid = 15L;
      Long differentUid = 25L;
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest advancedFilterRequest =
          new AdvancedFilterRequest(differentUid, ruleGroup);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), advancedFilterRequest);

      ReportFilter advancedFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.ADV_FILTER_TYPE);
      when(advancedFilter.getId()).thenReturn(advancedFilterUid);
      when(advancedFilter.getFilterCode()).thenReturn(filterCode);
      when(savedReport.getReportFilters()).thenReturn(List.of(advancedFilter));

      assertThatThrownBy(() -> service.saveReport(request, reportId))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining(
              "AdvancedFilterRequest.reportFilterUid does not match existing advanced filter ID");
    }

    @Test
    void saveReport_should_clear_advanced_filter_when_null() {
      Long advancedFilterUid = 15L;
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), null);

      ReportFilter advancedFilter = mock(ReportFilter.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCode.getFilterType()).thenReturn(ReportFilter.ADV_FILTER_TYPE);
      Mockito.lenient().when(advancedFilter.getId()).thenReturn(advancedFilterUid);
      when(advancedFilter.getFilterCode()).thenReturn(filterCode);
      when(savedReport.getReportFilters()).thenReturn(List.of(advancedFilter));

      Report result = service.saveReport(request, reportId);

      verify(advancedFilter).setFilterValues(null);
      verify(reportFilterRepository).save(advancedFilter);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_throw_when_report_not_found() {
      when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), null);

      assertThatThrownBy(() -> service.saveReport(request, reportId))
          .isInstanceOf(NotFoundException.class)
          .hasMessageContaining("Report not found for Report UID: 1 and Data Source UID: 2");
    }

    @Test
    void saveReport_should_save_report_after_updates() {
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, List.of(), null, List.of(), null);

      Report result = service.saveReport(request, reportId);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_handle_all_parameters_together() {
      List<Long> displayColumnIds = List.of(3L);
      SortSpec sortSpec = new SortSpec(100L, ReportConstants.SortDirection.DESC);
      Long basicFilterUid = 10L;
      BasicFilterRequest basicFilterRequest =
          new BasicFilterRequest(basicFilterUid, List.of("value1"), false);
      Long advancedFilterUid = 15L;
      AdvancedQuery.Rule rule = new AdvancedQuery.Rule("rule1", 1L, "EQUALS", "value1");
      AdvancedQuery.RuleGroup ruleGroup =
          new AdvancedQuery.RuleGroup(
              "group1", ReportConstants.QueryCombinators.AND, List.of(rule));
      AdvancedFilterRequest advancedFilterRequest =
          new AdvancedFilterRequest(advancedFilterUid, ruleGroup);

      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid,
              dataSourceUid,
              true,
              displayColumnIds,
              sortSpec,
              List.of(basicFilterRequest),
              advancedFilterRequest);

      DataSourceColumn column1 = mock(DataSourceColumn.class);

      when(column1.getId()).thenReturn(3L);
      when(dataSource.getDataSourceColumns()).thenReturn(List.of(column1));
      when(savedReport.getDataSource()).thenReturn(dataSource);
      when(savedReport.getDisplayColumns()).thenReturn(new ArrayList<>());
      when(savedReport.getReportSortColumns()).thenReturn(new ArrayList<>());

      ReportFilter basicFilter = mock(ReportFilter.class);
      ReportFilter advancedFilter = mock(ReportFilter.class);
      FilterCode basicFilterCode = mock(FilterCode.class);
      FilterCode advancedFilterCode = mock(FilterCode.class);

      when(basicFilterCode.getFilterType()).thenReturn(ReportFilter.BASIC_FILTER_PREFIX + "TEST");
      when(advancedFilterCode.getFilterType()).thenReturn(ReportFilter.ADV_FILTER_TYPE);
      when(basicFilter.getId()).thenReturn(basicFilterUid);
      when(advancedFilter.getId()).thenReturn(advancedFilterUid);
      when(basicFilter.getFilterCode()).thenReturn(basicFilterCode);
      when(advancedFilter.getFilterCode()).thenReturn(advancedFilterCode);
      when(basicFilter.getFilterValues()).thenReturn(new ArrayList<>());
      when(advancedFilter.getFilterValues()).thenReturn(new ArrayList<>());
      when(savedReport.getReportFilters()).thenReturn(List.of(basicFilter, advancedFilter));

      DisplayColumn displayColumn = mock(DisplayColumn.class);
      when(displayColumnBuilder.build(savedReport, column1)).thenReturn(displayColumn);

      ReportSortColumn sortColumn = mock(ReportSortColumn.class);
      when(reportSortColumnMapper.fromSortSpec(savedReport, sortSpec)).thenReturn(sortColumn);

      List<FilterValue> basicFilterValues = List.of(mock(FilterValue.class));
      List<FilterValue> advancedFilterValues = List.of(mock(FilterValue.class));
      when(filterValueMapper.fromBasicFilterRequest(basicFilter, basicFilterRequest))
          .thenReturn(basicFilterValues);
      when(filterValueMapper.fromAdvancedFilterRequest(advancedFilter, advancedFilterRequest))
          .thenReturn(advancedFilterValues);

      Report result = service.saveReport(request, reportId);

      verify(displayColumnBuilder).build(any(Report.class), any(DataSourceColumn.class));
      verify(reportSortColumnMapper).fromSortSpec(any(), any());
      verify(filterValueMapper).fromBasicFilterRequest(any(), any());
      verify(filterValueMapper).fromAdvancedFilterRequest(any(), any());
      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_handle_only_display_columns() {
      List<Long> displayColumnIds = List.of(3L, 4L);
      ReportExecutionRequest request =
          new ReportExecutionRequest(
              reportUid, dataSourceUid, true, displayColumnIds, null, null, null);

      DataSourceColumn column1 = mock(DataSourceColumn.class);
      DataSourceColumn column2 = mock(DataSourceColumn.class);

      when(column1.getId()).thenReturn(3L);
      when(column2.getId()).thenReturn(4L);
      when(dataSource.getDataSourceColumns()).thenReturn(List.of(column1, column2));
      when(savedReport.getDataSource()).thenReturn(dataSource);
      when(savedReport.getDisplayColumns()).thenReturn(new ArrayList<>());

      DisplayColumn displayColumn1 = mock(DisplayColumn.class);
      DisplayColumn displayColumn2 = mock(DisplayColumn.class);

      when(displayColumnBuilder.build(savedReport, column1)).thenReturn(displayColumn1);
      when(displayColumnBuilder.build(savedReport, column2)).thenReturn(displayColumn2);

      Report result = service.saveReport(request, reportId);

      verify(displayColumnBuilder, times(2)).build(any(Report.class), any(DataSourceColumn.class));

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }

    @Test
    void saveReport_should_handle_only_sort_spec() {
      SortSpec sortSpec = new SortSpec(100L, ReportConstants.SortDirection.ASC);
      ReportExecutionRequest request =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, sortSpec, null, null);

      ReportSortColumn sortColumn = mock(ReportSortColumn.class);
      when(reportSortColumnMapper.fromSortSpec(savedReport, sortSpec)).thenReturn(sortColumn);
      when(savedReport.getReportSortColumns()).thenReturn(new ArrayList<>());

      Report result = service.saveReport(request, reportId);

      verify(reportSortColumnMapper).fromSortSpec(savedReport, sortSpec);

      verify(reportRepository).save(savedReport);
      assertThat(result).isEqualTo(savedReport);
    }
  }

  @Nested
  class SaveAsReport {
    private final Long reportUid = 1L;
    private final Long dataSourceUid = 2L;

    private final ReportId existingReportId = new ReportId(reportUid, dataSourceUid);
    private Report existingReport;

    private Report newReport;

    private NbsUserDetails mockUser;

    @BeforeEach
    void setup() {
      mockUser = mock(NbsUserDetails.class);

      existingReport = mock(Report.class);
      newReport = buildTestReport();

      Mockito.lenient().when(existingReport.getId()).thenReturn(existingReportId);
      Mockito.lenient()
          .when(reportRepository.findById(existingReportId))
          .thenReturn(Optional.of(existingReport));

      Mockito.lenient()
          .when(reportRepository.findById(newReport.getId()))
          .thenReturn(Optional.of(newReport));

      Mockito.lenient().when(reportMapper.duplicate(existingReport)).thenReturn(newReport);
      Mockito.lenient().when(reportRepository.save(newReport)).thenReturn(newReport);
    }

    @Test
    void saveAsReport_should_duplicate_original_report_and_override_select_fields() {
      ReportExecutionRequest reportExecutionRequest =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);
      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "Custom Title",
              "Custom",
              ReportConstants.ReportGroup.PUBLIC,
              reportExecutionRequest,
              "some description text");

      Report result = service.saveAsReport(request, mockUser, existingReportId);

      verify(reportMapper).duplicate(existingReport);

      assertThat(result.getReportTitle()).isEqualTo(request.reportTitle());
      assertThat(result.getSectionCd()).isEqualTo(request.sectionCode());
      assertThat(result.getShared())
          .isEqualTo(ReportConstants.reportGroupToDbChar(request.group()));
      assertThat(result.getDescTxt()).isEqualTo(request.description());
    }

    @Test
    void saveAsReport_should_set_owner_uid_to_user_making_request() {
      ReportExecutionRequest reportExecutionRequest =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);
      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "Custom Title",
              "Custom",
              ReportConstants.ReportGroup.PUBLIC,
              reportExecutionRequest,
              "some description text");

      Report result = service.saveAsReport(request, mockUser, existingReportId);

      assertThat(result.getOwnerUid()).isEqualTo(mockUser.getId());
    }

    @Test
    void saveAsReport_should_invoke_saveReport_with_new_report_id() {
      ReportExecutionRequest reportExecutionRequest =
          new ReportExecutionRequest(reportUid, dataSourceUid, true, null, null, null, null);
      SaveAsReportRequest request =
          new SaveAsReportRequest(
              "Custom Title",
              "Custom",
              ReportConstants.ReportGroup.PUBLIC,
              reportExecutionRequest,
              "some description text");

      Report finalReport = mock(Report.class);
      when(service.saveReport(reportExecutionRequest, newReport.getId())).thenReturn(finalReport);

      Report result = service.saveAsReport(request, mockUser, existingReportId);

      assertThat(result).isEqualTo(finalReport);
    }
  }

  private Report buildTestReport() {
    ReportId reportId = new ReportId(50L, dataSourceUid);
    return Report.builder()
        .id(reportId)
        .dataSource(dataSource)
        .reportFilters(Collections.emptyList())
        .descTxt("Test Description")
        .addTime(LocalDateTime.now(clock))
        .filterMode('B')
        .isModifiableIndicator('N')
        .location("test location")
        .ownerUid(84930L)
        .reportTitle("Test Report")
        .reportTypeCode("SAS_CUSTOM")
        .shared(ReportConstants.reportGroupToDbChar(ReportConstants.ReportGroup.PUBLIC))
        .category("Test Category")
        .sectionCd("section")
        .addTime(LocalDateTime.now(clock))
        .addUserUid(849032L)
        .status(new Status(Status.ACTIVE_CODE, LocalDateTime.now(clock)))
        .build();
  }
}
