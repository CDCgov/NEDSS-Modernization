package gov.cdc.nbs.report;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.models.UpsertFilterRequest;
import gov.cdc.nbs.report.utils.ValueCountCalculator;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import gov.cdc.nbs.repository.FilterCodeRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReportFilterBuilder {
  private final DataSourceColumnRepository dataSourceColumnRepository;
  private final FilterCodeRepository filterCodeRepository;
  private final IdGeneratorService idGenerator;

  public ReportFilterBuilder(
      DataSourceColumnRepository dataSourceColumnRepository,
      FilterCodeRepository filterCodeRepository,
      IdGeneratorService idGenerator) {
    this.dataSourceColumnRepository = dataSourceColumnRepository;
    this.filterCodeRepository = filterCodeRepository;
    this.idGenerator = idGenerator;
  }

  public ReportFilter build(UpsertFilterRequest filter, Report report) {
    LocalDateTime now = LocalDateTime.now();

    DataSourceColumn dataSourceColumn = null;
    if (filter.columnUid() != null) {
      dataSourceColumn =
          dataSourceColumnRepository
              .findById(filter.columnUid())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Data source column not found for UID: " + filter.columnUid()));
    }

    FilterCode filterCode =
        filterCodeRepository
            .findById(filter.filterCodeUid())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Filter code not found for UID: " + filter.filterCodeUid()));

    ReportFilter.ReportFilterBuilder filterBuilder =
        ReportFilter.builder().report(report).filterCode(filterCode).statusCd(Status.ACTIVE_CODE);

    ValueCountCalculator.ReportValueCounts valueCounts =
        ValueCountCalculator.fromFilterRequest(filter);
    filterBuilder.minValueCnt(valueCounts.minValueCount());
    filterBuilder.maxValueCnt(valueCounts.maxValueCount());

    if (dataSourceColumn != null) {
      filterBuilder.dataSourceColumn(dataSourceColumn);
    }

    if (filter.isRequired()) {
      // TODO: org.springframework.orm.jpa.JpaSystemException: Identifier of entity 'gov.cdc.nbs.entity.odse.ReportFilterValidation' must be manually assigned before calling 'persist()'
      filterBuilder.filterValidation(
          ReportFilterValidation.builder()
              .reportFilterInd('Y')
              .statusCd(Status.ACTIVE_CODE)
              .statusTime(now)
              .build());
    } else {
      //  Delete corresponding filter validation record if it exists
      filterBuilder.filterValidation(null);
    }

    if (filter.id() == null) {
      filterBuilder.id(generateReportFilterId());
    } else {
      filterBuilder.id(filter.id());
    }

    return filterBuilder.build();
  }

  private Long generateReportFilterId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
