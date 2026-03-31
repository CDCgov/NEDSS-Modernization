package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.entity.odse.Report;
import gov.cdc.nbs.entity.odse.ReportFilter;
import gov.cdc.nbs.entity.odse.ReportId;
import gov.cdc.nbs.entity.odse.ReportLibrary;
import gov.cdc.nbs.report.models.FilterValueOption;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FilterValueOptionMapperTest {

  @Test
  void fromFilterValue_should_map_all_fields() {
    ReportLibrary reportLibrary = new ReportLibrary();
    reportLibrary.setId(10L);
    reportLibrary.setLibraryName("lib_name");
    reportLibrary.setDescTxt("Library description");
    reportLibrary.setRunner("python");
    reportLibrary.setIsBuiltinIndex('N');
    reportLibrary.setAddTime(LocalDateTime.of(2024, 1, 1, 1, 0));
    reportLibrary.setAddUserId(1L);
    reportLibrary.setLastChgTime(LocalDateTime.of(2024, 1, 1, 1, 0));
    reportLibrary.setLastChgUserId(1L);

    Report report =
        Report.builder()
            .id(new ReportId(1L, 2L))
            .reportLibrary(reportLibrary)
            .sectionCd("SEC")
            .build();

    FilterCode filterCode =
        FilterCode.builder()
            .id(5L)
            .codeTable("code_table")
            .code("CODE")
            .filterCodeSetName("filter_set")
            .filterType("TYPE")
            .filterName("Filter Name")
            .build();

    ReportFilter reportFilter =
        ReportFilter.builder().id(3L).report(report).filterCode(filterCode).build();

    FilterValue dbFilterValue =
        FilterValue.builder()
            .id(4L)
            .reportFilter(reportFilter)
            .sequenceNumber(5)
            .valueType("TEXT")
            .columnUid(6L)
            .operator("=")
            .valueTxt("text value")
            .build();

    FilterValueOption mapped = FilterValueOptionMapper.fromFilterValue(dbFilterValue);

    assertThat(mapped.id()).isEqualTo(dbFilterValue.getId());
    assertThat(mapped.sequenceNumber()).isEqualTo(dbFilterValue.getSequenceNumber());
    assertThat(mapped.valueType()).isEqualTo(dbFilterValue.getValueType());
    assertThat(mapped.columnUid()).isEqualTo(dbFilterValue.getColumnUid());
    assertThat(mapped.operator()).isEqualTo(dbFilterValue.getOperator());
    assertThat(mapped.valueTxt()).isEqualTo(dbFilterValue.getValueTxt());
  }
}
