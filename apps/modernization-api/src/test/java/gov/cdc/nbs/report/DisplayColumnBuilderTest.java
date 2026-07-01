package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceCodeset;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.GeneratedId;
import gov.cdc.nbs.time.EffectiveTime;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
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

  private Report report;
  private DataSourceColumn dataSourceColumn;
  private Long generatedId;

  private final Random random = new Random();

  @BeforeEach
  void setup() {
    report = mock(Report.class);
    dataSourceColumn = buildDataSourceColumn();
    generatedId = 500L;

    GeneratedId mockGeneratedId = mock(GeneratedId.class);
    Mockito.lenient().when(mockGeneratedId.getId()).thenReturn(generatedId);
    Mockito.lenient()
        .when(idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS))
        .thenReturn(mockGeneratedId);
  }

  @Test
  void build_should_create_display_column_with_correct_properties() {
    DisplayColumn result = builder.build(report, dataSourceColumn);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(generatedId);
    assertThat(result.getDataSourceColumn()).isEqualTo(dataSourceColumn);
    assertThat(result.getReport()).isEqualTo(report);
  }

  @Test
  void build_should_always_update_status() {
    DataSourceColumn dataSourceColumn1 = buildDataSourceColumn();
    DataSourceColumn dataSourceColumn2 = buildDataSourceColumn();

    dataSourceColumn1.setStatusCd(Status.INACTIVE_CODE);
    dataSourceColumn1.setStatusTime(LocalDateTime.now(clock).minusYears(4));

    dataSourceColumn2.setStatusCd(Status.ACTIVE_CODE);
    dataSourceColumn2.setStatusTime(LocalDateTime.now(clock).minusMonths(4));

    DisplayColumn result1 = builder.build(report, dataSourceColumn1);
    DisplayColumn result2 = builder.build(report, dataSourceColumn2);

    for (DisplayColumn displayColumn : List.of(result1, result2)) {
      assertThat(displayColumn.getStatusCd()).isEqualTo(Status.ACTIVE_CODE);
      assertThat(displayColumn.getStatusTime()).isEqualToIgnoringNanos(LocalDateTime.now(clock));
    }
  }

  @Test
  void build_should_preserve_report_reference() {
    Report differentReport = mock(Report.class);

    DisplayColumn result = builder.build(differentReport, dataSourceColumn);

    assertThat(result.getReport()).isEqualTo(differentReport);
  }

  @Test
  void build_should_preserve_data_source_column_reference() {
    DataSourceColumn differentColumn = mock(DataSourceColumn.class);

    DisplayColumn result = builder.build(report, differentColumn);

    assertThat(result.getDataSourceColumn()).isEqualTo(differentColumn);
  }

  private DataSourceColumn buildDataSourceColumn() {
    return DataSourceColumn.builder()
        .id(random.nextLong(100000L))
        .columnMaxLength(random.nextInt(100))
        .columnName("Random Column Name")
        .columnTitle("Random Column Title")
        .columnSourceTypeCode("type code")
        .dataSource(mock(DataSource.class))
        .codeset(mock(DataSourceCodeset.class))
        .descTxt("Some description test")
        .displayable('Y')
        .effectiveTime(
            new EffectiveTime(
                LocalDateTime.now(clock).minusMonths(4), LocalDateTime.now(clock).plusYears(3)))
        .filterable('Y')
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(LocalDateTime.now(clock))
        .build();
  }
}
