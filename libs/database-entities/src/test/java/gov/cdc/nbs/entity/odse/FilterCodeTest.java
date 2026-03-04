package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.audit.Status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class FilterCodeTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThrows(NullPointerException.class, () -> new FilterCode(null));
  }

  @Test
  void should_create_filter_code() {
    Long filterCodeId = 1L;
    String codeTable = "nbs_srt..code_value_general";
    String descText = "Basic Condition Filter";
    LocalDateTime effectiveFromTime = LocalDateTime.parse("2020-03-03T10:15:30");
    LocalDateTime effectiveToTime = LocalDateTime.parse("2020-03-04T10:15:30");
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
            effectiveFromTime,
            effectiveToTime,
            filterCode,
            filterCodeSetName,
            filterType,
            filterName,
            status);

    assertThat(actual)
        .satisfies(fc -> assertEquals(filterCodeId, fc.getId()))
        .satisfies(fc -> assertEquals(codeTable, fc.getCodeTable()))
        .satisfies(fc -> assertEquals(descText, fc.getDescTxt()))
        .satisfies(fc -> assertEquals(effectiveFromTime, fc.getEffectiveFromTime()))
        .satisfies(fc -> assertEquals(effectiveToTime, fc.getEffectiveToTime()))
        .satisfies(fc -> assertEquals(filterCode, fc.getFilterCode()))
        .satisfies(fc -> assertEquals(filterCodeSetName, fc.getFilterCodeSetName()))
        .satisfies(fc -> assertEquals(filterType, fc.getFilterType()))
        .satisfies(fc -> assertEquals(filterName, fc.getFilterName()))
        .satisfies(fc -> assertEquals(status, fc.getStatus()));
  }
}
