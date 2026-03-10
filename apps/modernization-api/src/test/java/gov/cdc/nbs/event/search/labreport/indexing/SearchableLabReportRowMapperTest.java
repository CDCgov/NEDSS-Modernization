package gov.cdc.nbs.event.search.labreport.indexing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

class SearchableLabReportRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchableLabReportRowMapper.Column columns =
        new SearchableLabReportRowMapper.Column(
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.identifier())).thenReturn(419L);
    when(resultSet.getString(columns.classCode())).thenReturn("class-code-value");
    when(resultSet.getString(columns.mood())).thenReturn("mood-value");
    when(resultSet.getString(columns.programArea())).thenReturn("program-area-value");
    when(resultSet.getString(columns.jurisdiction())).thenReturn("jurisdiction-value");
    when(resultSet.getLong(columns.oid())).thenReturn(1019L);
    when(resultSet.getString(columns.pregnancyStatus())).thenReturn("pregnancy-status-value");
    when(resultSet.getString(columns.local())).thenReturn("local-value");

    when(resultSet.getObject(columns.reportedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2010, Month.FEBRUARY, 13).atStartOfDay());

    when(resultSet.getObject(columns.collectedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2007, Month.NOVEMBER, 7).atStartOfDay());

    when(resultSet.getObject(columns.receivedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2023, Month.JULY, 1).atStartOfDay());

    when(resultSet.getLong(columns.version())).thenReturn(1087L);
    when(resultSet.getString(columns.status())).thenReturn("status-value");
    when(resultSet.getString(columns.electronicEntry())).thenReturn("electronic-entry-value");

    when(resultSet.getLong(columns.createdBy())).thenReturn(1013L);
    when(resultSet.getObject(columns.createdOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2022, Month.FEBRUARY, 22).atStartOfDay());

    when(resultSet.getLong(columns.updatedBy())).thenReturn(1061L);
    when(resultSet.getObject(columns.updatedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2024, Month.APRIL, 24).atStartOfDay());

    SearchableLabReportRowMapper mapper = new SearchableLabReportRowMapper(columns);

    SearchableLabReport mapped = mapper.mapRow(resultSet, 631);

    assertThat(mapped.identifier()).isEqualTo(419L);
    assertThat(mapped.classCode()).isEqualTo("class-code-value");
    assertThat(mapped.mood()).isEqualTo("mood-value");
    assertThat(mapped.programArea()).isEqualTo("program-area-value");
    assertThat(mapped.jurisdiction()).isEqualTo("jurisdiction-value");
    assertThat(mapped.oid()).isEqualTo(1019L);
    assertThat(mapped.pregnancyStatus()).isEqualTo("pregnancy-status-value");
    assertThat(mapped.local()).isEqualTo("local-value");

    assertThat(mapped.reportedOn()).isEqualTo("2010-02-13");
    assertThat(mapped.collectedOn()).isEqualTo("2007-11-07");
    assertThat(mapped.receivedOn()).isEqualTo("2023-07-01");

    assertThat(mapped.version()).isEqualTo(1087L);
    assertThat(mapped.status()).isEqualTo("status-value");
    assertThat(mapped.electronicEntry()).isEqualTo("electronic-entry-value");

    assertThat(mapped.createdBy()).isEqualTo(1013L);
    assertThat(mapped.createdOn()).isEqualTo("2022-02-22");

    assertThat(mapped.updatedBy()).isEqualTo(1061L);
    assertThat(mapped.updatedOn()).isEqualTo("2024-04-24");
  }
}
