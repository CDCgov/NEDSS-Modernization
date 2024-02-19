package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientDocumentRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientRowMapper.Column columns =
        new SearchablePatientRowMapper.Column(2, 3, 5, 7, 11, 13, 17);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(373L);
    when(resultSet.getString(columns.local())).thenReturn("local-value");
    when(resultSet.getString(columns.status())).thenReturn("status-value");

    when(resultSet.getString(columns.birthday())).thenReturn("1943-11-03T00:00:00");

    when(resultSet.getString(columns.deceased())).thenReturn("deceased-value");
    when(resultSet.getString(columns.gender())).thenReturn("gender-value");
    when(resultSet.getString(columns.ethnicity())).thenReturn("ethnicity-value");

    SearchablePatientRowMapper mapper = new SearchablePatientRowMapper(columns);

    SearchablePatient mapped = mapper.mapRow(resultSet, 13);

    assertThat(mapped.identifier()).isEqualTo(373L);
    assertThat(mapped.local()).isEqualTo("local-value");
    assertThat(mapped.status()).isEqualTo("status-value");
    assertThat(mapped.birthday()).isEqualTo("1943-11-03T00:00:00");
    assertThat(mapped.deceased()).isEqualTo("deceased-value");
    assertThat(mapped.gender()).isEqualTo("gender-value");
    assertThat(mapped.ethnicity()).isEqualTo("ethnicity-value");
  }
}
