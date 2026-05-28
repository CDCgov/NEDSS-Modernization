package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.*;
import gov.cdc.nbs.report.models.UpsertFilterRequest;
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
    Character statusCd = 'A';
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

    Integer minValueCount = null;
    Integer maxValueCount = null;

    switch (filter.filterCodeUid().intValue()) {
      case 7 // Where Clause Builder
          -> {
        minValueCount = 0;
        maxValueCount = -1;
      }
      case 5, // Time Range
          6, // Time Period
          12, // Time Range (Including NULLS)
          13, // Time Period (Including NULLS)
          14, // Month Year Range
          15, // Month Year Range (Including NULLS)
          17, // Time Range Custom
          18 // Basic Text Filter
          -> {
        minValueCount = 1;
        maxValueCount = 2;
      }
      case 1, // Diseases
          2, // States
          3, // Counties
          8, // Diseases (Including NULLS)
          9, // States (Including NULLS)
          10, // Counties (Including NULLS)
          16, // Case Diagnosis
          19, // STD Case Diagnosis
          20, // HIV Case Diagnosis
          21 // STD HIV Workers
          -> {
        switch (filter.selectType()) {
          case ReportConstants.SelectType.MULTI -> {
            minValueCount = 1;
            maxValueCount = -1;
          }
          case ReportConstants.SelectType.SINGLE -> {
            minValueCount = 1;
            maxValueCount = 1;
          }
        }
      }
    }

    ReportFilter.ReportFilterBuilder filterBuilder =
        ReportFilter.builder()
            .report(report)
            .filterCode(filterCode)
            .statusCd(statusCd);

    if (minValueCount != null) {
      filterBuilder.minValueCnt(minValueCount);
    }

    if (maxValueCount != null) {
      filterBuilder.maxValueCnt(maxValueCount);
    }

    if (dataSourceColumn != null) {
        filterBuilder.dataSourceColumn(dataSourceColumn);
    }

    if (filter.isRequired()) {
      filterBuilder.filterValidation(
          ReportFilterValidation.builder()
              .reportFilterInd('Y')
              .statusCd(statusCd)
              .statusTime(now)
              .build());
    } else {
        filterBuilder.filterValidation(null);
    }

      if (filter.id() != null) {
          filterBuilder.id(filter.id());
      }

    return filterBuilder.build();
  }
}
