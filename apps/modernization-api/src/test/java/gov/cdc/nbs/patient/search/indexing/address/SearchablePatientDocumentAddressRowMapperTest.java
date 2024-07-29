package gov.cdc.nbs.patient.search.indexing.address;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientDocumentAddressRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientAddressRowMapper.Column columns =
        new SearchablePatientAddressRowMapper.Column(2, 3, 5, 7, 11, 13, 17, 18, 19, 20);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.address1())).thenReturn("address-1-value");
    when(resultSet.getString(columns.address2())).thenReturn("address-2-value");
    when(resultSet.getString(columns.city())).thenReturn("city-value");
    when(resultSet.getString(columns.state())).thenReturn("state-value");
    when(resultSet.getString(columns.zip())).thenReturn("zip-value");
    when(resultSet.getString(columns.county())).thenReturn("county-value");
    when(resultSet.getString(columns.country())).thenReturn("country-value");

    SearchablePatientAddressRowMapper mapper = new SearchablePatientAddressRowMapper(columns);

    SearchablePatient.Address mapped = mapper.mapRow(resultSet, 233);

    assertThat(mapped.address1()).isEqualTo("address-1-value");
    assertThat(mapped.address2()).isEqualTo("address-2-value");
    assertThat(mapped.city()).isEqualTo("city-value");
    assertThat(mapped.state()).isEqualTo("state-value");
    assertThat(mapped.zip()).isEqualTo("zip-value");
    assertThat(mapped.county()).isEqualTo("county-value");
    assertThat(mapped.country()).isEqualTo("country-value");
  }

}
