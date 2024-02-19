package gov.cdc.nbs.patient.search.indexing.tele;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientEmailMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientEmailMapper.Column columns = new SearchablePatientEmailMapper.Column(67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.address())).thenReturn("address-value");

    SearchablePatientEmailMapper mapper = new SearchablePatientEmailMapper(columns);

    SearchablePatient.Email mapped = mapper.mapRow(resultSet, 727);

    assertThat(mapped.address()).isEqualTo("address-value");
  }

}
