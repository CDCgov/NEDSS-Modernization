package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.time.EffectiveTime;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class FilterCodeTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new FilterCode(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("codeTable is marked non-null but is null");
  }

  @Test
  void should_create_filter_code() {
    Long filterCodeId = 1L;
    String codeTable = "nbs_srt..code_value_general";
    String descText = "Basic Condition Filter";
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
    EffectiveTime effectiveTime = new EffectiveTime(effectiveFromTime, effectiveToTime);
    String filterCode = "C_D01";
    String filterCodeSetName = "PHC_TYPE";
    String filterType = "BAS_CON_LIST";
    String filterName = "Diseases";
    Status status = new Status();

    FilterCode actual =
        new FilterCode(
            filterCodeId,
            codeTable,
            descText,
            effectiveTime,
            filterCode,
            filterCodeSetName,
            filterType,
            filterName,
            status);

    assertThat(actual)
        .satisfies(fc -> assertEquals(filterCodeId, fc.getId()))
        .satisfies(fc -> assertEquals(codeTable, fc.getCodeTable()))
        .satisfies(fc -> assertEquals(descText, fc.getDescTxt()))
        .satisfies(
            fc -> assertEquals(effectiveFromTime, fc.getEffectiveTime().getEffectiveFromTime()))
        .satisfies(fc -> assertEquals(effectiveToTime, fc.getEffectiveTime().getEffectiveToTime()))
        .satisfies(fc -> assertEquals(filterCode, fc.getCode()))
        .satisfies(fc -> assertEquals(filterCodeSetName, fc.getFilterCodeSetName()))
        .satisfies(fc -> assertEquals(filterType, fc.getFilterType()))
        .satisfies(fc -> assertEquals(filterName, fc.getFilterName()))
        .satisfies(fc -> assertEquals(status, fc.getStatus()));
  }
}
