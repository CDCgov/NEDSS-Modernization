package gov.cdc.nbs.patient.search.indexing.telecom;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientPhoneMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchablePatientPhoneMapper.Column columns = new SearchablePatientPhoneMapper.Column(61, 67, 68, 69);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.number())).thenReturn("number-value");
    when(resultSet.getString(columns.extension())).thenReturn("extension-value");

    SearchablePatientPhoneMapper mapper = new SearchablePatientPhoneMapper(columns);

    SearchablePatient.Phone mapped = mapper.map(resultSet);

    assertThat(mapped.number()).isEqualTo("number-value");
    assertThat(mapped.extension()).isEqualTo("extension-value");
  }

  @Test
  void should_map_from_result_set_when_number_is_present() throws SQLException {

    SearchablePatientPhoneMapper.Column columns = new SearchablePatientPhoneMapper.Column(61, 67, 68, 69);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.number())).thenReturn("number-value");
    when(resultSet.getString(columns.extension())).thenReturn("extension-value");

    SearchablePatientPhoneMapper mapper = new SearchablePatientPhoneMapper(columns);

    Optional<SearchablePatient.Phone> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertAll(
            () -> assertThat(actual.number()).isEqualTo("number-value"),
            () -> assertThat(actual.extension()).isEqualTo("extension-value")));

  }

  @Test
  void should_not_map_from_result_set_when_number_is_not_present() throws SQLException {

    SearchablePatientPhoneMapper.Column columns = new SearchablePatientPhoneMapper.Column(61, 67, 68, 69);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.extension())).thenReturn("extension-value");

    SearchablePatientPhoneMapper mapper = new SearchablePatientPhoneMapper(columns);

    Optional<SearchablePatient.Phone> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();

  }

  @Test
  void should_not_map_from_result_set_when_number_is_blank() throws SQLException {

    SearchablePatientPhoneMapper.Column columns = new SearchablePatientPhoneMapper.Column(61, 67, 68, 69);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.number())).thenReturn("");
    when(resultSet.getString(columns.extension())).thenReturn("extension-value");

    SearchablePatientPhoneMapper mapper = new SearchablePatientPhoneMapper(columns);

    Optional<SearchablePatient.Phone> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();

  }

}
