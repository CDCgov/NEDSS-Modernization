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
import org.mockito.Mockito;
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
    Mockito.lenient().when(mockGeneratedId.getId()).thenReturn(generatedId);
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(mockGeneratedId);
  }

  @Test
  void fromSortSpec_should_set_all_fields_on_report_sort_column() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    ReportSortColumn result = mapper.fromSortSpec(mockReport, sortSpec);

    assertThat(result.getId()).isEqualTo(generatedId);
    assertThat(result.getReport()).isEqualTo(mockReport);
    assertThat(result.getReportSortOrderCode()).isEqualTo(SortDirection.ASC.toString());
    assertThat(result.getDataSourceColumnUid()).isEqualTo(100L);

    assertThat(result.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
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
  void fromSortSpec_should_use_nbs_entity_type_for_id_generation() {
    SortSpec sortSpec = new SortSpec(100L, SortDirection.ASC);

    mapper.fromSortSpec(mockReport, sortSpec);

    verify(idGenerator).getNextValidId(IdGeneratorService.EntityType.NBS);
  }
}
