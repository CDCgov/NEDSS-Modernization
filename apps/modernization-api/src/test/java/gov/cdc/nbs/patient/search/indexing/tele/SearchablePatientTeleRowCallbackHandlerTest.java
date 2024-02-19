package gov.cdc.nbs.patient.search.indexing.tele;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchablePatientTeleRowCallbackHandlerTest {

  @Test
  void should_map_phone_from_result_set_when_number_is_present() throws SQLException {

    SearchablePatientTeleRowCallbackHandler.Column columns = new SearchablePatientTeleRowCallbackHandler.Column(
        2,
        new SearchablePatientEmailMapper.Column(3),
        new SearchablePatientPhoneMapper.Column(5, 7)
    );

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.type())).thenReturn("anything");
    when(resultSet.getString(columns.phone().number())).thenReturn("number-value");
    when(resultSet.getString(columns.phone().extension())).thenReturn("extension-value");

    SearchablePatientTeleRowCallbackHandler handler = new SearchablePatientTeleRowCallbackHandler(columns);

    handler.processRow(resultSet);

    assertThat(handler.phones()).satisfiesExactly(
        actual -> assertThat(actual.number()).isEqualTo("number-value")
    );
  }

  @Test
  void should_map_email_from_result_set_when_email_present() throws SQLException {
    SearchablePatientTeleRowCallbackHandler.Column columns = new SearchablePatientTeleRowCallbackHandler.Column(
        2,
        new SearchablePatientEmailMapper.Column(3),
        new SearchablePatientPhoneMapper.Column(5, 7)
    );

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(columns.email().address())).thenReturn("address-value");


  }
}
