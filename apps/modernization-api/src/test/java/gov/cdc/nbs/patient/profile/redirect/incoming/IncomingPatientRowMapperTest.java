package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.OptionalLong;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

class IncomingPatientRowMapperTest {

  @Test
  void should_map_result_set_to_incoming_patient() throws SQLException {

    PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);

    when(resolver.resolve(anyString())).thenReturn(OptionalLong.of(5317L));

    IncomingPatientRowMapper mapper = new IncomingPatientRowMapper((resolver));

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(ArgumentMatchers.anyInt())).thenReturn("local-id-value");

    Optional<IncomingPatient> actual = mapper.mapRow(resultSet, 0);

    assertThat(actual)
        .hasValueSatisfying(incoming -> assertThat(incoming.identifier()).isEqualTo(5317L));

    verify(resolver).resolve("local-id-value");
  }

  @Test
  void should_not_return_an_incoming_patient_when_short_id_is_not_resolved_() throws SQLException {
    PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);

    when(resolver.resolve(anyString())).thenReturn(OptionalLong.empty());

    IncomingPatientRowMapper mapper = new IncomingPatientRowMapper((resolver));

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(ArgumentMatchers.anyInt())).thenReturn("local-id-value");

    Optional<IncomingPatient> actual = mapper.mapRow(resultSet, 0);

    assertThat(actual).isNotPresent();
  }
}
