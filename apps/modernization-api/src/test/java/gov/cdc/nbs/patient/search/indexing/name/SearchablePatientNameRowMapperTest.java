package gov.cdc.nbs.patient.search.indexing.name;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchablePatientNameRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientNameRowMapper.Column columns =
        new SearchablePatientNameRowMapper.Column(2, 3, 5, 7, 11, 13, 17);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.use())).thenReturn("use-value");
    when(resultSet.getString(columns.first())).thenReturn("first-value");
    when(resultSet.getString(columns.middle())).thenReturn("middle-value");
    when(resultSet.getString(columns.last())).thenReturn("last-value");
    when(resultSet.getString(columns.prefix())).thenReturn("prefix-value");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix-value");

    SearchablePatientNameRowMapper mapper = new SearchablePatientNameRowMapper(columns);

    SearchablePatient.Name mapped = mapper.mapRow(resultSet, 293);

    assertThat(mapped.use()).isEqualTo("use-value");
    assertThat(mapped.first()).isEqualTo("first-value");
    assertThat(mapped.firstSoundex()).isEqualTo("F623");
    assertThat(mapped.middle()).isEqualTo("middle-value");
    assertThat(mapped.last()).isEqualTo("last-value");
    assertThat(mapped.lastSoundex()).isEqualTo("L231");
    assertThat(mapped.prefix()).isEqualTo("prefix-value");
    assertThat(mapped.suffix()).isEqualTo("suffix-value");
  }
}
