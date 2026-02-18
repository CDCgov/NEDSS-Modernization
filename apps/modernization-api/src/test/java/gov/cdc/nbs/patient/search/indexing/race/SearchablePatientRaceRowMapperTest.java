package gov.cdc.nbs.patient.search.indexing.race;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchablePatientRaceRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientRaceRowMapper.Column columns =
        new SearchablePatientRaceRowMapper.Column(61, 67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.category())).thenReturn("category-value");
    when(resultSet.getString(columns.detail())).thenReturn("detail-value");

    SearchablePatientRaceRowMapper mapper = new SearchablePatientRaceRowMapper(columns);

    SearchablePatient.Race mapped = mapper.mapRow(resultSet, 727);

    assertThat(mapped.category()).isEqualTo("category-value");
    assertThat(mapped.detail()).isEqualTo("detail-value");
  }
}
