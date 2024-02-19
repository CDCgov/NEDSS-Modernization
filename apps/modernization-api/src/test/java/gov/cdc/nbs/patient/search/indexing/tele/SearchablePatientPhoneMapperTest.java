package gov.cdc.nbs.patient.search.indexing.tele;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientPhoneMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientPhoneMapper.Column columns = new SearchablePatientPhoneMapper.Column(61, 67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.number())).thenReturn("number-value");
    when(resultSet.getString(columns.extension())).thenReturn("extension-value");

    SearchablePatientPhoneMapper mapper = new SearchablePatientPhoneMapper(columns);

    SearchablePatient.Phone mapped = mapper.mapRow(resultSet, 727);

    assertThat(mapped.number()).isEqualTo("number-value");
    assertThat(mapped.extension()).isEqualTo("extension-value");
  }

}
