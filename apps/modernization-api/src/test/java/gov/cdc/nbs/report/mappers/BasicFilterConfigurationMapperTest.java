package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportFilterValidation;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class BasicFilterConfigurationMapperTest {
  // JPA creates circular references, which are tedious to construct properly and
  // we don't really care here
  Report emptyReport = new Report(new ReportId(9L, 8L), "section");
  ReportFilter emptyFilter = new ReportFilter(emptyReport, new FilterCode("NONE"));
  DataSource dataSource = DataSource.builder().id(100L).statusCd('A').build();
  DataSourceColumn column =
      DataSourceColumn.builder()
          .id(1L)
          .columnMaxLength(255)
          .columnName("column_name")
          .columnTitle("Column Title")
          .columnSourceTypeCode("VARCHAR")
          .dataSource(dataSource)
          .descTxt("Some description")
          .displayable('Y')
          .filterable('N')
          .statusCd('A')
          .statusTime(LocalDateTime.of(2024, 3, 31, 12, 0))
          .build();
  FilterCode filterCode =
      FilterCode.builder()
          .id(4L)
          .code("T_T01")
          .codeTable("NONE")
          .filterName("Test Filter")
          .filterType("BAS_TXT")
          .build();
  ReportFilterValidation filterValidation =
      ReportFilterValidation.builder()
          .id(5L)
          .reportFilterInd('Y')
          .reportFilter(emptyFilter)
          .build();
  FilterValue filterValue =
      FilterValue.builder()
          .id(6L)
          .valueType("CODE")
          .valueTxt("value")
          .reportFilter(emptyFilter)
          .build();

  @Test
  void fromReportFilter_should_map_all_fields() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .dataSourceColumn(column)
            .filterValidation(filterValidation)
            .filterCode(filterCode)
            .filterValues(List.of(filterValue))
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    BasicFilterConfiguration mapped = BasicFilterConfigurationMapper.fromReportFilter(reportFilter);

    assertThat(mapped.reportFilterUid()).isEqualTo(reportFilter.getId());
    assertThat(mapped.reportColumnUid()).isEqualTo(column.getId());
    assertThat(mapped.defaultValue()).isEqualTo(List.of("value"));
    assertThat(mapped.minValueCount()).isEqualTo(1);
    assertThat(mapped.maxValueCount()).isEqualTo(-1);
    assertThat(mapped.isRequired()).isEqualTo(true);
    assertThat(mapped.filterType()).isEqualTo(FilterTypeMapper.fromFilterCode(filterCode));
  }

  @Test
  void fromReportFilter_should_default_not_required() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .dataSourceColumn(column)
            .filterCode(filterCode)
            .filterValues(List.of(filterValue))
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    BasicFilterConfiguration mapped = BasicFilterConfigurationMapper.fromReportFilter(reportFilter);

    assertThat(mapped.isRequired()).isEqualTo(false);
  }

  @Test
  void fromReportFilter_should_error_on_non_basic_filter() {
    FilterCode advFilterCode =
        FilterCode.builder()
            .id(4L)
            .code("T_T01")
            .filterName("Test Filter")
            .filterType("ADV_FILTER")
            .codeTable("NONE")
            .build();
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .dataSourceColumn(column)
            .filterCode(advFilterCode)
            .filterValues(List.of(filterValue))
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    assertThatThrownBy(() -> BasicFilterConfigurationMapper.fromReportFilter(reportFilter))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot create basic filter from advanced filter");
  }

  @Test
  void fromReportFilter_should_not_require_column() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .filterCode(filterCode)
            .filterValues(List.of(filterValue))
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    BasicFilterConfiguration mapped = BasicFilterConfigurationMapper.fromReportFilter(reportFilter);

    assertThat(mapped.reportColumnUid()).isNull();
  }

  @Test
  void fromReportFilter_should_not_require_values() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .filterCode(filterCode)
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    BasicFilterConfiguration mapped = BasicFilterConfigurationMapper.fromReportFilter(reportFilter);

    assertThat(mapped.defaultValue()).isNull();
  }

  @Test
  void fromReportFilter_should_handle_empty_values() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .filterCode(filterCode)
            .filterValues(List.of())
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    BasicFilterConfiguration mapped = BasicFilterConfigurationMapper.fromReportFilter(reportFilter);

    assertThat(mapped.defaultValue()).isEmpty();
  }
}
