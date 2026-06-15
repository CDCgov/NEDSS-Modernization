package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.models.UpsertFilterRequest;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import gov.cdc.nbs.repository.FilterCodeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportFilterBuilderTest {

  @Mock private DataSourceColumnRepository dataSourceColumnRepository;
  @Mock private FilterCodeRepository filterCodeRepository;
  @Mock private IdGeneratorService idGenerator;

  @InjectMocks private ReportFilterBuilder builder;

  @BeforeEach
  void setUp() {
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(new IdGeneratorService.GeneratedId(47L));
  }

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
        new UpsertFilterRequest(null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
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
        new UpsertFilterRequest(null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
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
        new UpsertFilterRequest(null, filterCodeId, null, ReportConstants.SelectType.MULTI, false);
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
        new UpsertFilterRequest(null, filterCodeId, null, ReportConstants.SelectType.SINGLE, false);
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
