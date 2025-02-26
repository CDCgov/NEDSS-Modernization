package gov.cdc.nbs.patient.search.indexing.identification;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientIdentificationRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientIdentificationRowMapper.Column columns =
        new SearchablePatientIdentificationRowMapper.Column(61, 67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.value())).thenReturn("value-value");

    SearchablePatientIdentificationRowMapper mapper = new SearchablePatientIdentificationRowMapper(columns);

    SearchablePatient.Identification mapped = mapper.mapRow(resultSet, 727);

    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.value()).isEqualTo("value-value");
  }

}
