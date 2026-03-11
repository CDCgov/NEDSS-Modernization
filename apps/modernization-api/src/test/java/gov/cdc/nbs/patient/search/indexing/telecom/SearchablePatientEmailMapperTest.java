package gov.cdc.nbs.patient.search.indexing.telecom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SearchablePatientEmailMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientEmailMapper.Column columns = new SearchablePatientEmailMapper.Column(67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.address())).thenReturn("address-value");

    SearchablePatientEmailMapper mapper = new SearchablePatientEmailMapper(columns);

    SearchablePatient.Email mapped = mapper.map(resultSet);

    assertThat(mapped.address()).isEqualTo("address-value");
  }

  @Test
  void should_map_from_result_set_when_email_present() throws SQLException {

    SearchablePatientEmailMapper.Column columns = new SearchablePatientEmailMapper.Column(67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.address())).thenReturn("address-value");

    SearchablePatientEmailMapper mapper = new SearchablePatientEmailMapper(columns);

    Optional<SearchablePatient.Email> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped)
        .hasValueSatisfying(actual -> assertThat(actual.address()).isEqualTo("address-value"));
  }

  @Test
  void should_not_map_from_result_set_when_email_not_present() throws SQLException {

    SearchablePatientEmailMapper.Column columns = new SearchablePatientEmailMapper.Column(67);

    ResultSet resultSet = mock(ResultSet.class);

    SearchablePatientEmailMapper mapper = new SearchablePatientEmailMapper(columns);

    Optional<SearchablePatient.Email> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();
  }

  @Test
  void should_not_map_from_result_set_when_email_is_blank() throws SQLException {

    SearchablePatientEmailMapper.Column columns = new SearchablePatientEmailMapper.Column(67);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.address())).thenReturn("");

    SearchablePatientEmailMapper mapper = new SearchablePatientEmailMapper(columns);

    Optional<SearchablePatient.Email> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();
  }
}
