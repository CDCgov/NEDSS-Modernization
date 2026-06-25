package gov.cdc.nbs.report;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.report.mappers.FilterValueMapper;
import gov.cdc.nbs.report.models.UpsertFilterRequest;
import gov.cdc.nbs.report.utils.ValueCountCalculator;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import gov.cdc.nbs.repository.FilterCodeRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportFilterBuilder {
  private final Clock clock;
  private final DataSourceColumnRepository dataSourceColumnRepository;
  private final FilterCodeRepository filterCodeRepository;
  private final IdGeneratorService idGenerator;

  private final FilterValueMapper filterValueMapper;

  public ReportFilterBuilder(
      final Clock clock,
      DataSourceColumnRepository dataSourceColumnRepository,
      FilterCodeRepository filterCodeRepository,
      IdGeneratorService idGenerator,
      FilterValueMapper filterValueMapper) {
    this.clock = clock;
    this.dataSourceColumnRepository = dataSourceColumnRepository;
    this.filterCodeRepository = filterCodeRepository;
    this.idGenerator = idGenerator;
    this.filterValueMapper = filterValueMapper;
  }

  public ReportFilter duplicate(ReportFilter filter) {
    ReportFilter newFilter =
        ReportFilter.builder()
            .id(generateId())
            .report(filter.getReport())
            .filterCode(filter.getFilterCode())
            .dataSourceColumn(filter.getDataSourceColumn())
            .statusCd(filter.getStatusCd())
            .maxValueCnt(filter.getMaxValueCnt())
            .minValueCnt(filter.getMinValueCnt())
            .build();

    ReportFilterValidation validation = filter.getFilterValidation();
    if (validation != null) {
      ReportFilterValidation newValidation =
          ReportFilterValidation.builder()
              .id(generateId())
              .reportFilter(newFilter)
              .reportFilterInd(validation.getReportFilterInd())
              .statusCd(validation.getStatusCd())
              .statusTime(validation.getStatusTime())
              .build();

      newFilter.setFilterValidation(newValidation);
    }

    List<FilterValue> filterValues = filter.getFilterValues();
    if (filter.getFilterValues() != null) {
      List<FilterValue> newFilterValues =
          filterValues.stream().map(filterValueMapper::duplicate).toList();
      newFilter.setFilterValues(newFilterValues);
    }

    return newFilter;
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

    ReportFilter filter = null;
    if (filterRequest.id() == null) {
      filter =
          ReportFilter.builder()
              .report(report)
              .filterCode(filterCode)
              .statusCd(Status.ACTIVE_CODE)
              .id(generateId())
              .build();
    } else {
      filter =
          report.getReportFilters().stream()
              .filter(f -> f.getId().equals(filterRequest.id()))
              .findFirst()
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Unknown report filter cannot be updated: %s"
                              .formatted(filterRequest.id())));
      if (!filter.getFilterCode().getId().equals(filterCode.getId())) {
        throw new IllegalArgumentException(
            "Cannot update filter type on an existing filter. Delete the filter and create a new one to change the type");
      }
    }

    ValueCountCalculator.ReportValueCounts valueCounts =
        ValueCountCalculator.fromFilterRequest(filterRequest);
    filter.setMinValueCnt(valueCounts.minValueCount());
    filter.setMaxValueCnt(valueCounts.maxValueCount());

    if (dataSourceColumn != null) {
      filter.setDataSourceColumn(dataSourceColumn);
    }

    handleReportFilterValidation(filter, filterRequest);

    return filter;
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
    LocalDateTime now = LocalDateTime.now(this.clock);
    ReportFilterValidation validation =
        ReportFilterValidation.builder()
            .id(generateId())
            .reportFilter(filter)
            .reportFilterInd('Y')
            .statusCd(Status.ACTIVE_CODE)
            .statusTime(now)
            .build();
    filter.setFilterValidation(validation);
  }

  private Long generateId() {
    var generatedId = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS);
    return generatedId.getId();
  }
}
