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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
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

  private Random random = new Random();

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
    long nextId = 500L;

    @BeforeEach
    void setUp() {
      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(nextId));
    }

    @Test
    void duplicate_should_generate_new_id_for_report_filter() {
      ReportFilter originalFilter = buildReportFilter();

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getId()).isEqualTo(nextId);
      assertThat(duplicatedFilter.getId()).isNotEqualTo(originalFilter.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_report_filter_fields() {
      ReportFilter originalFilter = buildReportFilter();

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter).isNotNull();

      assertThat(duplicatedFilter.getReport()).isEqualTo(originalFilter.getReport());
      assertThat(duplicatedFilter.getFilterCode()).isEqualTo(originalFilter.getFilterCode());
      assertThat(duplicatedFilter.getDataSourceColumn())
          .isEqualTo(originalFilter.getDataSourceColumn());
      assertThat(duplicatedFilter.getMinValueCnt()).isEqualTo(originalFilter.getMinValueCnt());
      assertThat(duplicatedFilter.getMaxValueCnt()).isEqualTo(originalFilter.getMaxValueCnt());
    }

    @Test
    void duplicate_should_always_update_report_filter_status() {
      ReportFilter originalFilter1 = buildReportFilter();
      ReportFilter originalFilter2 = buildReportFilter();

      originalFilter1.setStatusCd(Status.INACTIVE_CODE);
      originalFilter2.setStatusCd(Status.ACTIVE_CODE);

      ReportFilter duplicatedFilter1 = builder.duplicate(originalFilter1);
      ReportFilter duplicatedFilter2 = builder.duplicate(originalFilter2);

      assertThat(duplicatedFilter1.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
      assertThat(duplicatedFilter2.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    }

    @Test
    void duplicate_should_generate_new_id_for_filter_validation() {
      ReportFilter originalFilter = buildReportFilter();

      ReportFilterValidation originalValidation = buildReportFilterValidation(originalFilter);
      originalFilter.setFilterValidation(originalValidation);

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      ReportFilterValidation duplicatedValidation = duplicatedFilter.getFilterValidation();

      assertThat(duplicatedValidation).isNotNull();
      assertThat(duplicatedValidation.getId()).isNotNull();
      assertThat(duplicatedValidation.getId()).isNotEqualTo(originalValidation.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_filter_validation_fields() {
      ReportFilter originalFilter = buildReportFilter();

      ReportFilterValidation originalValidation = buildReportFilterValidation(originalFilter);
      originalFilter.setFilterValidation(originalValidation);

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      ReportFilterValidation duplicatedValidation = duplicatedFilter.getFilterValidation();

      assertThat(duplicatedValidation).isNotNull();

      assertThat(duplicatedValidation.getReportFilter()).isEqualTo(duplicatedFilter);
      assertThat(duplicatedValidation.getReportFilterInd())
          .isEqualTo(originalValidation.getReportFilterInd());
    }

    @Test
    void duplicate_should_always_update_filter_validation_status() {
      ReportFilter originalFilter = buildReportFilter();

      ReportFilterValidation activeValidation = buildReportFilterValidation(originalFilter);
      activeValidation.setStatusCd(Status.ACTIVE_CODE);
      activeValidation.setStatusTime(LocalDateTime.now(clock).minusMonths(4));

      ReportFilterValidation inactiveValidation = buildReportFilterValidation(originalFilter);
      inactiveValidation.setStatusCd(Status.INACTIVE_CODE);
      inactiveValidation.setStatusTime(LocalDateTime.now(clock).minusYears(2));

      for (ReportFilterValidation validation : List.of(activeValidation, inactiveValidation)) {
        originalFilter.setFilterValidation(validation);

        ReportFilter duplicatedFilter = builder.duplicate(originalFilter);
        assertThat(duplicatedFilter.getFilterValidation().getStatusCd())
            .isEqualTo(Status.ACTIVE_CODE);
        assertThat(duplicatedFilter.getFilterValidation().getStatusTime())
            .isEqualToIgnoringNanos(LocalDateTime.now(clock));
      }
    }

    @Test
    void duplicate_should_handle_null_filter_validation() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValidation(null);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValidation()).isNull();
    }

    @Test
    void duplicate_should_generate_new_id_for_filter_values() {
      ReportFilter originalFilter = buildReportFilter();

      FilterValue originalFilterValue = buildFilterValue(originalFilter);
      originalFilter.setFilterValues(List.of(originalFilterValue));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).hasSize(1);
      FilterValue duplicatedFilterValue = duplicatedFilter.getFilterValues().getFirst();

      assertThat(duplicatedFilterValue.getId()).isNotNull();
      assertThat(duplicatedFilterValue.getId()).isNotEqualTo(originalFilterValue.getId());
    }

    @Test
    void duplicate_should_duplicate_all_relevant_filter_value_fields() {
      ReportFilter originalFilter = buildReportFilter();

      FilterValue originalFilterValue = buildFilterValue(originalFilter);
      originalFilter.setFilterValues(Collections.singletonList(originalFilterValue));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).hasSize(1);
      FilterValue duplicatedFilterValue = duplicatedFilter.getFilterValues().getFirst();

      assertThat(duplicatedFilterValue.getReportFilter()).isEqualTo(duplicatedFilter);
      assertThat(duplicatedFilterValue.getValueTxt()).isEqualTo(originalFilterValue.getValueTxt());
      assertThat(duplicatedFilterValue.getSequenceNumber())
          .isEqualTo(originalFilterValue.getSequenceNumber());
      assertThat(duplicatedFilterValue.getValueType())
          .isEqualTo(originalFilterValue.getValueType());
      assertThat(duplicatedFilterValue.getColumnUid())
          .isEqualTo(originalFilterValue.getColumnUid());
      assertThat(duplicatedFilterValue.getOperator()).isEqualTo(originalFilterValue.getOperator());
    }

    @Test
    void duplicate_should_handle_null_filter_values() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValues(null);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).isNull();
    }

    @Test
    void duplicate_should_handle_empty_filter_values() {
      ReportFilter originalFilter = buildReportFilter();
      originalFilter.setFilterValues(Collections.emptyList());

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(new IdGeneratorService.GeneratedId(500L));

      ReportFilter duplicatedFilter = builder.duplicate(originalFilter);

      assertThat(duplicatedFilter.getFilterValues()).isEmpty();
    }
  }

  private ReportFilter buildReportFilter() {
    return ReportFilter.builder()
        .id(random.nextLong(1000))
        .report(mock(Report.class))
        .filterCode(mock(FilterCode.class))
        .dataSourceColumn(mock(DataSourceColumn.class))
        .statusCd(Status.ACTIVE_CODE)
        .minValueCnt(1)
        .maxValueCnt(5)
        .build();
  }

  private ReportFilterValidation buildReportFilterValidation(ReportFilter reportFilter) {
    return ReportFilterValidation.builder()
        .id(random.nextLong(100000))
        .reportFilter(reportFilter)
        .reportFilterInd('Y')
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(LocalDateTime.now(clock))
        .build();
  }

  private FilterValue buildFilterValue(ReportFilter reportFilter) {
    return FilterValue.builder()
        .id(random.nextLong(100000))
        .reportFilter(reportFilter)
        .valueTxt(UUID.randomUUID().toString())
        .sequenceNumber(random.nextInt(1000))
        .valueType(ReportConstants.AdvancedFilterValueType.CLAUSE.toString())
        .columnUid(random.nextLong(10000))
        .operator(ReportConstants.Operator.EQ.toString())
        .build();
  }
}
