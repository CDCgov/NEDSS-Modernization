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

  public ReportFilter buildBasicReportFilter(
      CreateFilterRequest.BasicFilter filter, Report report) {
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
                        "Filter code not found for type: " + filter.filterCodeUid()));

    if (!filterCode.getFilterType().startsWith("BAS_")) {
      throw new IllegalArgumentException("Cannot create basic filter from advanced filter");
    }

    int minValueCount;
    int maxValueCount;

    switch (filter.type()) {
      case ReportConstants.FilterType.MS -> {
        minValueCount = 1;
        maxValueCount = -1;
      }
      case ReportConstants.FilterType.SS -> {
        minValueCount = 1;
        maxValueCount = 1;
      }
      default -> throw new IllegalArgumentException("Unsupported filter type: " + filter.type());
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
