package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DisplayColumnTest {
  @Test
  void should_throw_exception_with_null_values() {
    assertThatThrownBy(() -> new DisplayColumn(null, null, null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("dataSourceColumn is marked non-null but is null");
  }

  @Test
  void should_create_complete_display_column() {
    Long id = 1L;
    DataSourceColumn dataSourceColumn = new DataSourceColumn();
    Report report = new Report();
    Integer sequenceNumber = 10;
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    DisplayColumn actual =
        new DisplayColumn(id, dataSourceColumn, report, sequenceNumber, statusCd, statusTime);

    assertThat(actual)
        .satisfies(dc -> assertEquals(id, dc.getId()))
        .satisfies(dc -> assertEquals(dataSourceColumn, dc.getDataSourceColumn()))
        .satisfies(dc -> assertEquals(report, dc.getReport()))
        .satisfies(dc -> assertEquals(sequenceNumber, dc.getSequenceNumber()))
        .satisfies(dc -> assertEquals(statusCd, dc.getStatusCd()))
        .satisfies(dc -> assertEquals(statusTime, dc.getStatusTime()));
  }

  @Test
  void should_instantiate_via_protected_constructor() {
    DisplayColumn actual = new DisplayColumn();

    assertThat(actual)
        .isNotNull()
        .extracting(
            "id",
            "dataSourceColumn",
            "sequenceNumber") // Extracts fields directly, bypassing getters
        .containsOnlyNulls();
  }
}
