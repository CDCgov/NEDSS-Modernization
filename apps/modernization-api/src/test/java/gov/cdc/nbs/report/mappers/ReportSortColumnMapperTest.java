package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.report.ReportConstants.SortDirection;
import gov.cdc.nbs.report.models.SortSpec;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSortColumnMapperTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private IdGeneratorService idGenerator;
  @InjectMocks private ReportSortColumnMapper mapper;

  private Report mockReport;
  private Long generatedId;
  private LocalDateTime expectedTime;

  @BeforeEach
  void setup() {
    mockReport = mock(Report.class);
    generatedId = 123L;
    expectedTime = LocalDateTime.now(clock);

    GeneratedId mockGeneratedId = mock(GeneratedId.class);
    when(mockGeneratedId.getId()).thenReturn(generatedId);
    when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS)).thenReturn(mockGeneratedId);
  }

  @Test
  void fromSortSpec_should_create_sort_column_with_asc_direction() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getId()).isEqualTo(generatedId);
    assertThat(result.getReport()).isEqualTo(mockReport);
    assertThat(result.getReportSortOrderCode()).isEqualTo(SortDirection.ASC.toString());
    assertThat(result.getDataSourceColumnUid()).isEqualTo(100L);
    assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    assertThat(result.getStatusTime()).isNotNull();
  }

  @Test
  void fromSortSpec_should_create_sort_column_with_desc_direction() {
    SortSpec sortSpec = new SortSpec(200L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getId()).isEqualTo(generatedId);
    assertThat(result.getReport()).isEqualTo(mockReport);
    assertThat(result.getReportSortOrderCode()).isEqualTo(SortDirection.DESC.toString());
    assertThat(result.getDataSourceColumnUid()).isEqualTo(200L);
    assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    assertThat(result.getStatusTime()).isNotNull();
  }

  @Test
  void fromSortSpec_should_set_status_code_to_active() {
    SortSpec sortSpec = new SortSpec(50L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
  }

  @Test
  void fromSortSpec_should_set_status_time_to_current_time() {
    SortSpec sortSpec = new SortSpec(75L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getStatusTime()).isEqualToIgnoringNanos(expectedTime);
  }

  @Test
  void fromSortSpec_should_generate_unique_id() {
    SortSpec sortSpec1 = new SortSpec(100L, SortDirection.ASC);
    SortSpec sortSpec2 = new SortSpec(200L, SortDirection.DESC);

    long id1 = 150L;
    long id2 = 151L;

    GeneratedId mockGeneratedId1 = mock(GeneratedId.class);
    GeneratedId mockGeneratedId2 = mock(GeneratedId.class);
    when(mockGeneratedId1.getId()).thenReturn(id1);
    when(mockGeneratedId2.getId()).thenReturn(id2);

    when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(mockGeneratedId1)
        .thenReturn(mockGeneratedId2);

    ReportSortColumn result1 = mapper.fromSortSpec(mockReport, sortSpec1);
    ReportSortColumn result2 = mapper.fromSortSpec(mockReport, sortSpec2);

    assertThat(result1.getId()).isEqualTo(id1);
    assertThat(result2.getId()).isEqualTo(id2);
    assertThat(result1.getId()).isNotEqualTo(result2.getId());
  }

  @Test
  void fromSortSpec_should_preserve_column_uid() {
    long columnUid = 999L;
    SortSpec sortSpec = new SortSpec(columnUid, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getDataSourceColumnUid()).isEqualTo(columnUid);
  }

  @Test
  void fromSortSpec_should_preserve_report_reference() {
    Report differentReport = mock(Report.class);
    SortSpec sortSpec = new SortSpec(100L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(differentReport, sortSpec);

    assertThat(result.getReport()).isEqualTo(differentReport);
  }

  @Test
  void fromSortSpec_should_handle_large_column_uid() {
    long largeColumnUid = Long.MAX_VALUE - 1;
    SortSpec sortSpec = new SortSpec(largeColumnUid, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getDataSourceColumnUid()).isEqualTo(largeColumnUid);
  }

  @Test
  void fromSortSpec_should_handle_zero_column_uid() {
    SortSpec sortSpec = new SortSpec(0L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getDataSourceColumnUid()).isEqualTo(0L);
  }

  @Test
  void fromSortSpec_should_use_nbs_entity_type_for_id_generation() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    mapper.fromSortSpec(mockReport, sortSpec);

    verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
  }

  @Test
  void fromSortSpec_should_convert_asc_direction_to_string() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getReportSortOrderCode())
        .isEqualTo("ASC")
        .isEqualTo(SortDirection.ASC.toString());
  }

  @Test
  void fromSortSpec_should_convert_desc_direction_to_string() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getReportSortOrderCode())
        .isEqualTo("DESC")
        .isEqualTo(SortDirection.DESC.toString());
  }

  @Test
  void fromSortSpec_should_handle_all_sort_directions() {
    for (SortDirection direction : SortDirection.values()) {
      SortSpec sortSpec = new SortSpec(100L, direction);

      ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

      assertThat(result.getReportSortOrderCode()).isEqualTo(direction.toString());
    }
  }

  @Test
  void fromSortSpec_should_always_set_status_to_active_code() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
  }

  @Test
  void fromSortSpec_should_set_status_time_using_provided_clock() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.DESC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    LocalDateTime currentTime = LocalDateTime.now(clock);
    assertThat(result.getStatusTime()).isEqualToIgnoringSeconds(currentTime);
  }

  @Test
  void fromSortSpec_should_create_independent_instances() {
    SortSpec sortSpec1 = new SortSpec(100L, SortDirection.ASC);
    SortSpec sortSpec2 = new SortSpec(200L, SortDirection.DESC);

    ReportSortColumn result1 = mapper.fromSortSpec(mockReport, sortSpec1);
    ReportSortColumn result2 = mapper.fromSortSpec(mockReport, sortSpec2);

    assertThat(result1).isNotEqualTo(result2);
    assertThat(result1.getDataSourceColumnUid()).isNotEqualTo(result2.getDataSourceColumnUid());
    assertThat(result1.getReportSortOrderCode()).isNotEqualTo(result2.getReportSortOrderCode());
  }

  @Test
  void fromSortSpec_should_handle_repeated_calls_with_same_inputs() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result1 = mapper.fromSortSpec(mockReport, sortSpec);
    ReportSortColumn result2 = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result1.getDataSourceColumnUid()).isEqualTo(result2.getDataSourceColumnUid());
    assertThat(result1.getReportSortOrderCode()).isEqualTo(result2.getReportSortOrderCode());
    assertThat(result1.getReport()).isEqualTo(result2.getReport());
  }

  @Test
  void fromSortSpec_should_call_id_generator_once_per_invocation() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    mapper.fromSortSpec(mockReport, sortSpec);

    verify(idGenerator, times(1)).getNextValidId(IdGeneratorService.EntityType.NBS);
  }

  @Test
  void fromSortSpec_should_use_generated_id_from_valid_id() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);
    long expectedId = 555L;

    GeneratedId mockGeneratedId = mock(GeneratedId.class);
    when(mockGeneratedId.getId()).thenReturn(expectedId);
    when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS)).thenReturn(mockGeneratedId);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getId()).isEqualTo(expectedId);
  }

  @Test
  void fromSortSpec_should_handle_null_report_gracefully() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(null, sortSpec);

    assertThat(result.getReport()).isNull();
  }

  @Test
  void fromSortSpec_should_handle_single_column_uid() {
    SortSpec sortSpec = new SortSpec(1L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getDataSourceColumnUid()).isEqualTo(1L);
  }
}
