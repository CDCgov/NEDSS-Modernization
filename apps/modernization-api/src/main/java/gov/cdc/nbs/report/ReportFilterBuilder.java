package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.report.models.CreateFilterRequest;
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

  public ReportFilter build(CreateFilterRequest filter, Report report) {
    Character statusCd = 'A';
    LocalDateTime now = LocalDateTime.now();

    DataSourceColumn dataSourceColumn =
        dataSourceColumnRepository
            .findById(filter.columnUid())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Data source column not found for UID: " + filter.columnUid()));

    FilterCode filterCode =
        filterCodeRepository
            .findById(filter.filterCodeUid())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Filter code not found for selectType: " + filter.filterCodeUid()));

    int minValueCount;
    int maxValueCount;

    switch (filter.selectType()) {
      case ReportConstants.SelectType.MULTI -> {
        minValueCount = 1;
        maxValueCount = -1;
      }
      case ReportConstants.SelectType.SINGLE -> {
        minValueCount = 1;
        maxValueCount = 1;
      }
      default ->
          throw new IllegalArgumentException(
              "Unsupported filter selectType: " + filter.selectType());
    }

    ReportFilter.ReportFilterBuilder filterBuilder =
        ReportFilter.builder()
            .report(report)
            .dataSourceColumn(dataSourceColumn)
            .minValueCnt(minValueCount)
            .maxValueCnt(maxValueCount)
            .filterCode(filterCode)
            .statusCd(statusCd);

    if (filter.isRequired()) {
      filterBuilder.filterValidation(
          ReportFilterValidation.builder()
              .reportFilterInd('Y')
              .statusCd(statusCd)
              .statusTime(now)
              .build());
    }

    return filterBuilder.build();
  }
}
