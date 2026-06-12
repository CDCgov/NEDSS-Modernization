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

  public ReportFilter build(UpsertFilterRequest filterRequest, Report report) {
    DataSourceColumn dataSourceColumn = null;
    if (filterRequest.columnUid() != null) {
      dataSourceColumn =
          dataSourceColumnRepository
              .findById(filterRequest.columnUid())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Data source column not found for UID: " + filterRequest.columnUid()));
    }

    FilterCode filterCode =
        filterCodeRepository
            .findById(filterRequest.filterCodeUid())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Filter code not found for UID: " + filterRequest.filterCodeUid()));

    ReportFilter builtFilter = null;
    if (filterRequest.id() == null) {
      builtFilter =
          ReportFilter.builder()
              .report(report)
              .filterCode(filterCode)
              .statusCd(Status.ACTIVE_CODE)
              .id(generateReportFilterId())
              .build();
    } else {
      builtFilter =
          report.getReportFilters().stream()
              .filter(f -> f.getId() == filterRequest.id())
              .findFirst()
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Unknown report filter cannot be updated: %s"
                              .formatted(filterRequest.id())));
      if (builtFilter.getFilterCode() != filterCode) {
        throw new IllegalArgumentException(
            "Cannot update filter type on an existing filter. Delete the filter and create a new one to change the type");
      }
    }

    ValueCountCalculator.ReportValueCounts valueCounts =
        ValueCountCalculator.fromFilterRequest(filterRequest);
    builtFilter.setMinValueCnt(valueCounts.minValueCount());
    builtFilter.setMaxValueCnt(valueCounts.maxValueCount());

    if (dataSourceColumn != null) {
      builtFilter.setDataSourceColumn(dataSourceColumn);
    }

    handleReportFilterValidation(builtFilter, filterRequest);

    return builtFilter;
  }

  private void handleReportFilterValidation(ReportFilter filter, UpsertFilterRequest request) {
    // Delete corresponding filter validation record if it exists
    if (!request.isRequired()) {
      filter.setFilterValidation(null);
      return;
    }

    // no changes to make
    ReportFilterValidation origValidation = filter.getFilterValidation();
    if (origValidation != null) {
      if (!Character.valueOf('Y').equals(origValidation.getReportFilterInd())) {
        origValidation.setReportFilterInd('Y');
      }
      return;
    }

    // Net new validation
    LocalDateTime now = LocalDateTime.now();
    ReportFilterValidation validation =
        ReportFilterValidation.builder()
            .id(generateReportFilterId())
            .reportFilter(filter)
            .reportFilterInd('Y')
            .statusCd(Status.ACTIVE_CODE)
            .statusTime(now)
            .build();
    filter.setFilterValidation(validation);
    return;
  }

  private Long generateReportFilterId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
