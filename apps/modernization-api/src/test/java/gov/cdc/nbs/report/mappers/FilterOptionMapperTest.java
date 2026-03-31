package gov.cdc.nbs.report.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.report.models.FilterOption;
import org.junit.jupiter.api.Test;

class FilterOptionMapperTest {

  @Test
  void fromDb_should_map_all_fields() {
    FilterCode dbFilterCode =
        FilterCode.builder()
            .id(2L)
            .codeTable("code_table")
            .descTxt("Filter code description")
            .code("CODE")
            .filterCodeSetName("filter_set")
            .filterType("TYPE")
            .filterName("Filter Name")
            .build();

    FilterOption mapped = FilterCodeMapper.fromDb(dbFilterCode);

    assertThat(mapped.id()).isEqualTo(dbFilterCode.getId());
    assertThat(mapped.codeTable()).isEqualTo(dbFilterCode.getCodeTable());
    assertThat(mapped.descTxt()).isEqualTo(dbFilterCode.getDescTxt());
    assertThat(mapped.code()).isEqualTo(dbFilterCode.getCode());
    assertThat(mapped.filterCodeSetName()).isEqualTo(dbFilterCode.getFilterCodeSetName());
    assertThat(mapped.filterType()).isEqualTo(dbFilterCode.getFilterType());
    assertThat(mapped.filterName()).isEqualTo(dbFilterCode.getFilterName());
  }
}
