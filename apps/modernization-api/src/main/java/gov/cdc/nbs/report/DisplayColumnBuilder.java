package gov.cdc.nbs.report;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.DisplayColumn;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.id.IdGeneratorService;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DisplayColumnBuilder {
  private final Clock clock;
  private final IdGeneratorService idGenerator;

  public DisplayColumnBuilder(final Clock clock, IdGeneratorService idGenerator) {
    this.clock = clock;
    this.idGenerator = idGenerator;
  }

  public DisplayColumn build(Report report, DataSourceColumn column, int sequence) {
    LocalDateTime now = LocalDateTime.now(this.clock);

    return DisplayColumn.builder()
        .id(generateColumnId())
        .dataSourceColumn(column)
        .report(report)
        .sequenceNumber(sequence)
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(now)
        .build();
  }

  private Long generateColumnId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
