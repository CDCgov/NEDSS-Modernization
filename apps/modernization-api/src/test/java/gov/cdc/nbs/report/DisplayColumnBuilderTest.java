package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
class DisplayColumnBuilderTest {

  @Spy private Clock clock = Clock.fixed(Instant.ofEpochMilli(1000000), ZoneId.systemDefault());

  @Mock private IdGeneratorService idGenerator;
  @InjectMocks private DisplayColumnBuilder builder;

  private Report mockReport;
  private DataSourceColumn mockColumn;
  private Long generatedId;
  private LocalDateTime expectedTime;

  @BeforeEach
  void setup() {
    mockReport = mock(Report.class);
    mockColumn = mock(DataSourceColumn.class);
    generatedId = 500L;
    expectedTime = LocalDateTime.now(clock);

    GeneratedId mockGeneratedId = mock(GeneratedId.class);
    Mockito.lenient().when(mockGeneratedId.getId()).thenReturn(generatedId);
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(mockGeneratedId);
  }

    @Test
    void build_should_create_display_column_with_correct_properties() {
      int sequence = 1;

      DisplayColumn result = builder.build(mockReport, mockColumn, sequence);

      assertThat(result).isNotNull();
      assertThat(result.getId()).isEqualTo(generatedId);
      assertThat(result.getDataSourceColumn()).isEqualTo(mockColumn);
      assertThat(result.getReport()).isEqualTo(mockReport);
      assertThat(result.getSequenceNumber()).isEqualTo(sequence);
      assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
      assertThat(result.getStatusTime()).isNotNull();
    }

    @Test
    void build_should_set_sequence_number_correctly() {
      DisplayColumn result1 = builder.build(mockReport, mockColumn, 1);
      DisplayColumn result2 = builder.build(mockReport, mockColumn, 5);
      DisplayColumn result3 = builder.build(mockReport, mockColumn, 100);

      assertThat(result1.getSequenceNumber()).isEqualTo(1);
      assertThat(result2.getSequenceNumber()).isEqualTo(5);
      assertThat(result3.getSequenceNumber()).isEqualTo(100);
    }

    @Test
    void build_should_set_status_to_active() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    }

    @Test
    void build_should_set_status_time_to_current_time() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      assertThat(result.getStatusTime()).isEqualToIgnoringNanos(expectedTime);
    }

    @Test
    void build_should_generate_unique_id() {
      long id1 = 500L;
      long id2 = 501L;

      GeneratedId mockGeneratedId1 = mock(GeneratedId.class);
      GeneratedId mockGeneratedId2 = mock(GeneratedId.class);
      when(mockGeneratedId1.getId()).thenReturn(id1);
      when(mockGeneratedId2.getId()).thenReturn(id2);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(mockGeneratedId1)
          .thenReturn(mockGeneratedId2);

      DisplayColumn result1 = builder.build(mockReport, mockColumn, 1);
      DisplayColumn result2 = builder.build(mockReport, mockColumn, 2);

      assertThat(result1.getId()).isEqualTo(id1);
      assertThat(result2.getId()).isEqualTo(id2);
      assertThat(result1.getId()).isNotEqualTo(result2.getId());
    }

    @Test
    void build_should_preserve_report_reference() {
      Report differentReport = mock(Report.class);

      DisplayColumn result = builder.build(differentReport, mockColumn, 1);

      assertThat(result.getReport()).isEqualTo(differentReport);
    }

    @Test
    void build_should_preserve_data_source_column_reference() {
      DataSourceColumn differentColumn = mock(DataSourceColumn.class);

      DisplayColumn result = builder.build(mockReport, differentColumn, 1);

      assertThat(result.getDataSourceColumn()).isEqualTo(differentColumn);
    }

    @Test
    void build_should_handle_large_sequence() {
      int largeSequence = Integer.MAX_VALUE;

      DisplayColumn result = builder.build(mockReport, mockColumn, largeSequence);

      assertThat(result.getSequenceNumber()).isEqualTo(largeSequence);
    }

    @Test
    void build_should_use_nbs_entity_type_for_id_generation() {
      builder.build(mockReport, mockColumn, 1);

      verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
    }

    @Test
    void build_should_call_now_on_clock() {
      builder.build(mockReport, mockColumn, 1);

      verify(clock, atLeastOnce()).instant();
    }

    @Test
    void build_should_always_set_status_to_active() {
      DisplayColumn result1 = builder.build(mockReport, mockColumn, 1);
      DisplayColumn result2 = builder.build(mockReport, mockColumn, 2);

      assertThat(result1.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
      assertThat(result2.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
    }

    @Test
    void build_should_set_status_time_using_provided_clock() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      LocalDateTime currentTime = LocalDateTime.now(clock);
      assertThat(result.getStatusTime()).isEqualToIgnoringSeconds(currentTime);
    }

    @Test
    void build_should_not_be_null_status_time() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      assertThat(result.getStatusTime()).isNotNull();
    }

    @Test
    void build_should_call_id_generator_once_per_build() {
      builder.build(mockReport, mockColumn, 1);

      verify(idGenerator, times(1)).getNextValidId(IdGeneratorService.EntityType.NBS);
    }

    @Test
    void build_should_use_generated_id_from_valid_id() {
      long expectedId = 999L;

      GeneratedId mockGeneratedId = mock(GeneratedId.class);
      when(mockGeneratedId.getId()).thenReturn(expectedId);
      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(mockGeneratedId);

      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      assertThat(result.getId()).isEqualTo(expectedId);
    }

    @Test
    void build_should_generate_different_ids_for_multiple_builds() {
      long id1 = 600L;
      long id2 = 601L;
      long id3 = 602L;

      GeneratedId mockGeneratedId1 = mock(GeneratedId.class);
      GeneratedId mockGeneratedId2 = mock(GeneratedId.class);
      GeneratedId mockGeneratedId3 = mock(GeneratedId.class);
      when(mockGeneratedId1.getId()).thenReturn(id1);
      when(mockGeneratedId2.getId()).thenReturn(id2);
      when(mockGeneratedId3.getId()).thenReturn(id3);

      when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
          .thenReturn(mockGeneratedId1)
          .thenReturn(mockGeneratedId2)
          .thenReturn(mockGeneratedId3);

      DisplayColumn result1 = builder.build(mockReport, mockColumn, 1);
      DisplayColumn result2 = builder.build(mockReport, mockColumn, 2);
      DisplayColumn result3 = builder.build(mockReport, mockColumn, 3);

      assertThat(result1.getId()).isEqualTo(id1);
      assertThat(result2.getId()).isEqualTo(id2);
      assertThat(result3.getId()).isEqualTo(id3);
      assertThat(result1.getId()).isNotEqualTo(result2.getId());
      assertThat(result2.getId()).isNotEqualTo(result3.getId());
    }

    @Test
    void build_should_handle_different_reports_and_columns() {
      Report report1 = mock(Report.class);
      Report report2 = mock(Report.class);
      DataSourceColumn column1 = mock(DataSourceColumn.class);
      DataSourceColumn column2 = mock(DataSourceColumn.class);

      DisplayColumn result1 = builder.build(report1, column1, 1);
      DisplayColumn result2 = builder.build(report2, column2, 2);

      assertThat(result1.getReport()).isEqualTo(report1);
      assertThat(result2.getReport()).isEqualTo(report2);
      assertThat(result1.getDataSourceColumn()).isEqualTo(column1);
      assertThat(result2.getDataSourceColumn()).isEqualTo(column2);
    }

    @Test
    void build_should_use_clock_for_current_time() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      LocalDateTime now = LocalDateTime.now(clock);
      assertThat(result.getStatusTime()).isAfterOrEqualTo(expectedTime).isBeforeOrEqualTo(now);
    }

    @Test
    void build_should_have_consistent_time_with_clock() {
      LocalDateTime beforeBuild = LocalDateTime.now(clock);
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);
      LocalDateTime afterBuild = LocalDateTime.now(clock);

      assertThat(result.getStatusTime())
          .isAfterOrEqualTo(beforeBuild)
          .isBeforeOrEqualTo(afterBuild);
    }

    @Test
    void build_should_not_allow_modification_of_returned_values() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 1);

      Long originalId = result.getId();
      result.setId(999L);

      assertThat(originalId).isNotEqualTo(result.getId());
    }

    @Test
    void build_should_create_column_with_all_properties() {
      DisplayColumn result = builder.build(mockReport, mockColumn, 5);

      assertThat(result.getId()).isNotNull();
      assertThat(result.getDataSourceColumn()).isNotNull();
      assertThat(result.getReport()).isNotNull();
      assertThat(result.getSequenceNumber()).isNotNull();
      assertThat(result.getStatusCd()).isNotNull();
      assertThat(result.getStatusTime()).isNotNull();
    }
}
