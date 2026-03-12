package gov.cdc.nbs.patient.search.indexing.telecom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchablePatientTelecomRowCallbackHandlerTest {

  @Test
  void should_map_phone_from_result_set_when_number_is_present() throws SQLException {

    SearchablePatientEmailMapper.Column emailColumns = new SearchablePatientEmailMapper.Column(3);
    SearchablePatientPhoneMapper.Column phoneColumns =
        new SearchablePatientPhoneMapper.Column(5, 7, 8, 9);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(phoneColumns.number())).thenReturn("number-value");
    when(resultSet.getString(phoneColumns.extension())).thenReturn("extension-value");

    SearchablePatientTelecomRowCallbackHandler handler =
        new SearchablePatientTelecomRowCallbackHandler(emailColumns, phoneColumns);

    handler.processRow(resultSet);

    SearchablePatientTelecom mapped = handler.telecom();

    assertThat(mapped.phones())
        .satisfiesExactly(actual -> assertThat(actual.number()).isEqualTo("number-value"));

    assertThat(mapped.emails()).isEmpty();
  }

  @Test
  void should_map_email_from_result_set_when_email_present() throws SQLException {
    SearchablePatientEmailMapper.Column emailColumns = new SearchablePatientEmailMapper.Column(3);
    SearchablePatientPhoneMapper.Column phoneColumns =
        new SearchablePatientPhoneMapper.Column(5, 7, 8, 9);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(emailColumns.address())).thenReturn("address-value");

    SearchablePatientTelecomRowCallbackHandler handler =
        new SearchablePatientTelecomRowCallbackHandler(emailColumns, phoneColumns);

    handler.processRow(resultSet);

    SearchablePatientTelecom mapped = handler.telecom();

    assertThat(mapped.phones()).isEmpty();

    assertThat(mapped.emails())
        .satisfiesExactly(actual -> assertThat(actual.address()).isEqualTo("address-value"));
  }
}
