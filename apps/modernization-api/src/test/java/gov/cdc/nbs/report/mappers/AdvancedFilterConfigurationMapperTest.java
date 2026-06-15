package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.entity.odse.DataSource;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.report.ReportConstants;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class AdvancedFilterConfigurationMapperTest {
  // JPA creates circular references, which are tedious to construct properly and
  // we don't really care here
  Report emptyReport = new Report(new ReportId(), "section");
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
          .filterType(ReportConstants.ADV_FILTER_TYPE)
          .build();

  @Test
  void fromReportFilter_should_map_all_fields() {
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .dataSourceColumn(column)
            .filterCode(filterCode)
            .filterValues(List.of())
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    AdvancedFilterConfiguration mapped =
        AdvancedFilterConfigurationMapper.fromReportFilter(reportFilter, Collections.emptyList());

    assertThat(mapped.reportFilterUid()).isEqualTo(reportFilter.getId());
    assertThat(mapped.defaultValue()).isNull();
  }

  @Test
  void fromReportFilter_should_error_on_non_where_clause_builder_filter() {
    FilterCode basFilterCode =
        FilterCode.builder()
            .id(4L)
            .code("T_T01")
            .filterName("Test Filter")
            .filterType("BAS_TXT")
            .codeTable("NONE")
            .build();
    ReportFilter reportFilter =
        ReportFilter.builder()
            .id(2L)
            .dataSourceColumn(column)
            .filterCode(basFilterCode)
            .filterValues(List.of())
            .minValueCnt(1)
            .maxValueCnt(-1)
            .report(emptyReport)
            .build();

    assertThatThrownBy(
            () ->
                AdvancedFilterConfigurationMapper.fromReportFilter(
                    reportFilter, Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Cannot create advanced filter from non where clause builder filter");
  }
}
