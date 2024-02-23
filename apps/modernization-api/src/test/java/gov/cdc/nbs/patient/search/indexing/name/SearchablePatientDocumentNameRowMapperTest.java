package gov.cdc.nbs.patient.search.indexing.name;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientDocumentNameRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientNameRowMapper.Column
        columns = new SearchablePatientNameRowMapper.Column(2, 3, 5, 7, 11, 13, 17, 19);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.use())).thenReturn("use-value");
    when(resultSet.getString(columns.first())).thenReturn("first-value");
    when(resultSet.getString(columns.firstSoundex())).thenReturn("first-soundex-value");
    when(resultSet.getString(columns.middle())).thenReturn("middle-value");
    when(resultSet.getString(columns.last())).thenReturn("last-value");
    when(resultSet.getString(columns.lastSoundex())).thenReturn("last-soundex-value");
    when(resultSet.getString(columns.prefix())).thenReturn("prefix-value");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix-value");


    SearchablePatientNameRowMapper mapper = new SearchablePatientNameRowMapper(columns);

    SearchablePatient.Name mapped = mapper.mapRow(resultSet, 293);

    assertThat(mapped.use()).isEqualTo("use-value");
    assertThat(mapped.first()).isEqualTo("first-value");
    assertThat(mapped.firstSoundex()).isEqualTo("first-soundex-value");
    assertThat(mapped.middle()).isEqualTo("middle-value");
    assertThat(mapped.last()).isEqualTo("last-value");
    assertThat(mapped.lastSoundex()).isEqualTo("last-soundex-value");
    assertThat(mapped.prefix()).isEqualTo("prefix-value");
    assertThat(mapped.suffix()).isEqualTo("suffix-value");
  }

}
