package gov.cdc.nbs.report;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.id.IdGeneratorService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReportFilterValidationBuilder {
  private final IdGeneratorService idGenerator;

  public ReportFilterValidationBuilder(IdGeneratorService idGenerator) {
    this.idGenerator = idGenerator;
  }

  public ReportFilterValidation build(Report report, ReportFilter filter) {
    LocalDateTime now = LocalDateTime.now();

    ReportFilterValidation.ReportFilterValidationBuilder validationBuilder =
        ReportFilterValidation.builder()
            .reportFilterInd('Y')
            .statusCd(Status.ACTIVE_CODE)
            .statusTime(now);

    Long validationUid = null;
    validationBuilder.reportFilter(filter);
    if (filter.getFilterValidation() != null) {
      validationUid = filter.getFilterValidation().getId();
    } else {
      validationUid = generateReportFilterValidationId();
    }
    validationBuilder.id(validationUid);

    return validationBuilder.build();
  }

  private Long generateReportFilterValidationId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
