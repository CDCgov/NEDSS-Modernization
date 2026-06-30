package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.models.UpsertFilterRequest;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import gov.cdc.nbs.repository.FilterCodeRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportFilterBuilderTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(10000000), ZoneId.systemDefault());
  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @Mock private FilterCodeRepository filterCodeRepository;
  @Mock private IdGeneratorService idGenerator;
  @Mock private FilterValueMapper filterValueMapper;

  @InjectMocks private ReportFilterBuilder builder;

  @BeforeEach
  void setUp() {
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(new IdGeneratorService.GeneratedId(47L));
  }

  @Nested
  class Build {
    @Test
    void build_should_throw_when_column_id_provided_but_data_source_column_not_found() {
      Long columnUid = 999L;
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 5L, columnUid, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      when(dataSourceColumnRepository.findById(columnUid)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> builder.build(filter, report))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Data source column not found for UID: " + columnUid);
    }

    @Test
    void build_should_throw_when_filter_code_not_found() {
      Long filterCodeId = 42L;
      UpsertFilterRequest filter =
          new UpsertFilterRequest(
              null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      when(filterCodeRepository.findById(filterCodeId)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> builder.build(filter, report))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Filter code not found for UID: " + filterCodeId);
    }

    @Test
    void build_should_set_filter_validation_when_required() {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 5L, null, ReportConstants.SelectType.SINGLE, true);
      Report report = mock(Report.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      when(filterCodeRepository.findById(5L)).thenReturn(Optional.of(mockFilterCode));

      ReportFilter result = builder.build(filter, report);
      // once for filter, once for validation
      Mockito.verify(idGenerator, times(2)).getNextValidId(IdGeneratorService.EntityType.NBS);

      ReportFilterValidation validation = result.getFilterValidation();
      assertThat(validation).isNotNull();
      assertThat(validation.getReportFilterInd()).isEqualTo('Y');
      assertThat(validation.getStatusCd()).isEqualTo('A');
      assertThat(validation.getStatusTime()).isNotNull();
    }

    @Test
    void build_should_set_filter_validation_to_null_when_not_required() {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 5L, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      FilterCode mockFilterCode = mock(FilterCode.class);
      when(filterCodeRepository.findById(5L)).thenReturn(Optional.of(mockFilterCode));

      ReportFilter result = builder.build(filter, report);

      ReportFilterValidation validation = result.getFilterValidation();
      assertThat(validation).isNull();
    }

    @Test
    void build_should_set_min_max_for_where_clause_builder_case_7() {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 7L, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      when(filterCodeRepository.findById(7L)).thenReturn(Optional.of(mock(FilterCode.class)));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getMinValueCnt()).isZero();
      assertThat(result.getMaxValueCnt()).isEqualTo(-1);
    }

    @ParameterizedTest
    @MethodSource("fetchMinMaxTimeRangeTestParams")
    void build_should_set_min_max_for_time_range_cases(Long filterCodeId) {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(
              null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      when(filterCodeRepository.findById(filterCodeId))
          .thenReturn(Optional.of(mock(FilterCode.class)));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getMinValueCnt()).isEqualTo(1);
      assertThat(result.getMaxValueCnt()).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("fetchMinMaxSelectGroupTestParams")
    void build_should_set_min_max_for_multi_select_group(Long filterCodeId) {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(
              null, filterCodeId, null, ReportConstants.SelectType.MULTI, false);
      Report report = mock(Report.class);

      when(filterCodeRepository.findById(filterCodeId))
          .thenReturn(Optional.of(mock(FilterCode.class)));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getMinValueCnt()).isEqualTo(1);
      assertThat(result.getMaxValueCnt()).isEqualTo(-1);
    }

    @ParameterizedTest
    @MethodSource("fetchMinMaxSelectGroupTestParams")
    void build_should_set_min_max_for_single_select_group(Long filterCodeId) {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(
              null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      when(filterCodeRepository.findById(filterCodeId))
          .thenReturn(Optional.of(mock(FilterCode.class)));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getMinValueCnt()).isEqualTo(1);
      assertThat(result.getMaxValueCnt()).isEqualTo(1);
    }

    @Test
    void build_should_attach_data_source_column_when_present() {
      long columnUid = 77L;
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 5L, columnUid, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);

      DataSourceColumn column = mock(DataSourceColumn.class);
      when(dataSourceColumnRepository.findById(columnUid)).thenReturn(Optional.of(column));
      when(filterCodeRepository.findById(5L)).thenReturn(Optional.of(mock(FilterCode.class)));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getDataSourceColumn()).isEqualTo(column);
    }

    @Test
    void build_should_set_default_values_on_success() {
      UpsertFilterRequest filter =
          new UpsertFilterRequest(null, 5L, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);
      FilterCode filterCode = mock(FilterCode.class);

      when(filterCodeRepository.findById(5L)).thenReturn(Optional.of(filterCode));

      ReportFilter result = builder.build(filter, report);

      assertThat(result.getStatusCd()).isEqualTo('A');
      assertThat(result.getReport()).isEqualTo(report);
      assertThat(result.getFilterCode()).isEqualTo(filterCode);
    }

    @Test
    void build_should_edit_existing_report_when_id_is_specified() {
      Long filterId = 4L;
      Long filterCodeId = 5L;
      UpsertFilterRequest filterReq =
          new UpsertFilterRequest(
              filterId, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);
      FilterCode filterCode = mock(FilterCode.class);
      ReportFilter filter =
          ReportFilter.builder().id(filterId).filterCode(filterCode).report(report).build();

      when(filterCodeRepository.findById(filterCodeId)).thenReturn(Optional.of(filterCode));
      when(report.getReportFilters()).thenReturn(List.of(filter));

      ReportFilter result = builder.build(filterReq, report);

      // We update the existing filter, so should be actually equal
      assertThat(result).isEqualTo(filter);
    }

    @Test
    void build_should_not_edit_existing_report_when_filter_type_changes() {
      Long filterId = 4L;
      Long filterCodeId = 5L;
      UpsertFilterRequest filterReq =
          new UpsertFilterRequest(
              filterId, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
      Report report = mock(Report.class);
      FilterCode origFilterCode = new FilterCode(3L);
      FilterCode newFilterCode = new FilterCode(filterCodeId);
      ReportFilter filter =
          ReportFilter.builder().id(filterId).filterCode(origFilterCode).report(report).build();

      when(filterCodeRepository.findById(filterCodeId)).thenReturn(Optional.of(newFilterCode));
      when(report.getReportFilters()).thenReturn(List.of(filter));

      assertThatThrownBy(() -> builder.build(filterReq, report))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(
              "Cannot update filter type on an existing filter. Delete the filter and create a new one to change the type");
    }

    private static Stream<Arguments> fetchMinMaxTimeRangeTestParams() {
      return Stream.of(
          Arguments.of(5L),
          Arguments.of(6L),
          Arguments.of(12L),
          Arguments.of(13L),
          Arguments.of(14L),
          Arguments.of(15L),
          Arguments.of(17L),
          Arguments.of(18L));
    }

    private static Stream<Arguments> fetchMinMaxSelectGroupTestParams() {
      return Stream.of(
          Arguments.of(1L),
          Arguments.of(2L),
          Arguments.of(3L),
          Arguments.of(8L),
          Arguments.of(9L),
          Arguments.of(10L),
          Arguments.of(16L),
          Arguments.of(19L),
          Arguments.of(20L),
          Arguments.of(21L));
    }
  }

  @Nested
  class Duplicate {

    @Test
    void duplicate_should_generate_new_id() {
      ReportFilter originalFilter = buildReportFilter();

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getId()).isEqualTo(500L);
      assertThat(duplicatedFilter.getId()).isNotEqualTo(originalFilter.getId());
    }

    @Test
    void duplicate_should_preserve_report_reference() {
      Report mockReport = mock(Report.class);
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setReport(mockReport);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getReport()).isEqualTo(mockReport);
    }

    @Test
    void duplicate_should_preserve_filter_code() {
      FilterCode filterCode = mock(FilterCode.class);
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterCode(filterCode);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterCode()).isEqualTo(filterCode);
    }

    @Test
    void duplicate_should_preserve_data_source_column() {
      DataSourceColumn dataSourceColumn = mock(DataSourceColumn.class);
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setDataSourceColumn(dataSourceColumn);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getDataSourceColumn()).isEqualTo(dataSourceColumn);
    }

    @Test
    void duplicate_should_preserve_status_code() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setStatusCd('I');

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getStatusCd()).isEqualTo('I');
    }

    @Test
    void duplicate_should_preserve_min_value_count() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setMinValueCnt(2);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getMinValueCnt()).isEqualTo(2);
    }

    @Test
    void duplicate_should_preserve_max_value_count() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setMaxValueCnt(10);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getMaxValueCnt()).isEqualTo(10);
    }

    @Test
    void duplicate_should_duplicate_filter_validation_when_present() {
      ReportFilter originalFilter = buildReportFilter();
      ReportFilterValidation validation = mock(ReportFilterValidation.class);
      Mockito.lenient().when(validation.getReportFilterInd()).thenReturn('Y');
      Mockito.lenient().when(validation.getStatusCd()).thenReturn('A');
      originalFilter.setFilterValidation(validation);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L))
          .thenReturn(new IdGeneratorService.GeneratedId(501L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValidation()).isNotNull();
      assertThat(duplicatedFilter.getFilterValidation().getReportFilterInd()).isEqualTo('Y');
      assertThat(duplicatedFilter.getFilterValidation().getStatusCd()).isEqualTo('A');
      assertThat(duplicatedFilter.getFilterValidation().getReportFilter())
          .isEqualTo(duplicatedFilter);
    }

    @Test
    void duplicate_should_generate_new_validation_id() {
      ReportFilter originalFilter = buildReportFilter();
      ReportFilterValidation validation = mock(ReportFilterValidation.class);
      when(validation.getReportFilterInd()).thenReturn('Y');
      when(validation.getStatusCd()).thenReturn('A');
      originalFilter.setFilterValidation(validation);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L))
          .thenReturn(new IdGeneratorService.GeneratedId(501L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValidation().getId()).isEqualTo(501L);
    }

    @Test
    void duplicate_should_handle_null_filter_validation() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValidation(null);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValidation()).isNull();
    }

    @Test
    void duplicate_should_duplicate_filter_values() {
      ReportFilter originalFilter = buildReportFilter();
      FilterValue filterValue = mock(FilterValue.class);
      filterValue.setId(1L);
      filterValue.setValueTxt("test_value");
      originalFilter.setFilterValues(Collections.singletonList(filterValue));

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      FilterValue duplicatedFilterValue = mock(FilterValue.class);
      duplicatedFilterValue.setId(2L);
      duplicatedFilterValue.setValueTxt("test_value");

      Mockito.when(filterValueMapper.duplicate(filterValue)).thenReturn(duplicatedFilterValue);

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).hasSize(1);
      assertThat(duplicatedFilter.getFilterValues().getFirst()).isEqualTo(duplicatedFilterValue);
    }

    @Test
    void duplicate_should_handle_null_filter_values() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValues(null);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).isNull();
    }

    @Test
    void duplicate_should_handle_empty_filter_values() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValues(Collections.emptyList());

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).isEmpty();
    }

    @Test
    void duplicate_should_preserve_all_fields_when_fully_populated() {
      Report mockReport = mock(Report.class);
      FilterCode filterCode = mock(FilterCode.class);
      DataSourceColumn dataSourceColumn = mock(DataSourceColumn.class);
      ReportFilterValidation validation = mock(ReportFilterValidation.class);
      when(validation.getReportFilterInd()).thenReturn('Y');
      when(validation.getStatusCd()).thenReturn(Status.ACTIVE_CODE);

      ReportFilter originalFilter =
          ReportFilter.builder()
              .id(100L)
              .report(mockReport)
              .filterCode(filterCode)
              .dataSourceColumn(dataSourceColumn)
              .statusCd(Status.ACTIVE_CODE)
              .minValueCnt(1)
              .maxValueCnt(5)
              .filterValidation(validation)
              .build();

      FilterValue filterValue = mock(FilterValue.class);
      filterValue.setId(1L);
      originalFilter.setFilterValues(Collections.singletonList(filterValue));

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L))
          .thenReturn(new IdGeneratorService.GeneratedId(501L));

      FilterValue duplicatedFilterValue = mock(FilterValue.class);
      duplicatedFilterValue.setId(2L);
      Mockito.when(filterValueMapper.duplicate(filterValue)).thenReturn(duplicatedFilterValue);

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter).isNotNull();
      assertThat(duplicatedFilter.getId()).isEqualTo(500L);
      assertThat(duplicatedFilter.getReport()).isEqualTo(mockReport);
      assertThat(duplicatedFilter.getFilterCode()).isEqualTo(filterCode);
      assertThat(duplicatedFilter.getDataSourceColumn()).isEqualTo(dataSourceColumn);
      assertThat(duplicatedFilter.getStatusCd()).isEqualTo('A');
      assertThat(duplicatedFilter.getMinValueCnt()).isEqualTo(1);
      assertThat(duplicatedFilter.getMaxValueCnt()).isEqualTo(5);
      assertThat(duplicatedFilter.getFilterValidation()).isNotNull();
      assertThat(duplicatedFilter.getFilterValidation().getId()).isEqualTo(501L);
      assertThat(duplicatedFilter.getFilterValues()).hasSize(1);
    }

    @Test
    void duplicate_should_call_id_generator_multiple_times_when_validation_exists() {
      ReportFilter originalFilter = buildReportFilter();
      ReportFilterValidation validation = mock(ReportFilterValidation.class);
      Mockito.lenient().when(validation.getReportFilterInd()).thenReturn('Y');
      Mockito.lenient().when(validation.getStatusCd()).thenReturn('A');
      originalFilter.setFilterValidation(validation);

      Mockito.when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L))
          .thenReturn(new IdGeneratorService.GeneratedId(501L));

      builder.duplicate(originalFilter);

      Mockito.verify(idGenerator, Mockito.times(2))
          .getNextValidId(IdGeneratorService.EntityType.NBS);
    }
  }

  private ReportFilter buildReportFilter() {
    return ReportFilter.builder()
        .id(100L)
        .report(mock(Report.class))
        .filterCode(mock(FilterCode.class))
        .statusCd('A')
        .build();
  }
}
