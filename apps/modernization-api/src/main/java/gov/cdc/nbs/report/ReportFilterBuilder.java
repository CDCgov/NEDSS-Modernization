package gov.cdc.nbs.report;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
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

  public ReportFilterBuilder(
      DataSourceColumnRepository dataSourceColumnRepository,
      FilterCodeRepository filterCodeRepository) {
    this.dataSourceColumnRepository = dataSourceColumnRepository;
    this.filterCodeRepository = filterCodeRepository;
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

    if (filter.id() != null) {
      filterBuilder.id(filter.id());
    }

    return filterBuilder.build();
  }
}
