package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportSortColumn;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.models.SortSpec;
import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class ReportSortColumnMapper {
  private final Clock clock;
  private final IdGeneratorService idGenerator;

  public ReportSortColumnMapper(final Clock clock, IdGeneratorService idGenerator) {
    this.clock = clock;
    this.idGenerator = idGenerator;
  }

  public ReportSortColumn fromSortSpec(Report report, SortSpec sortSpec) {
    return ReportSortColumn.builder()
        .id(generateSortColumnId())
        .report(report)
        .reportSortOrderCode(sortSpec.direction().toString())
        .dataSourceColumnUid(sortSpec.columnUid())
        .statusCd(Status.ACTIVE_CODE)
        .statusTime(LocalDateTime.now(this.clock))
        .build();
  }

  private Long generateSortColumnId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
