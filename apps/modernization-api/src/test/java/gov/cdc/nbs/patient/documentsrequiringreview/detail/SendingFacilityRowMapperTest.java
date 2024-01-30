package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SendingFacilityRowMapperTest {

  private static final SendingFacilityRowMapper.Column columns = new SendingFacilityRowMapper.Column(163);

  @Test
  void should_map_when_name_preset() throws SQLException {

    SendingFacilityRowMapper mapper = new SendingFacilityRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("sending-facility-value");

    Optional<DocumentRequiringReview.SendingFacility> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.name()).isEqualTo("sending-facility-value")
    );
  }

  @Test
  void should_not_map_when_name_not_present() throws SQLException {
    SendingFacilityRowMapper mapper = new SendingFacilityRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    Optional<DocumentRequiringReview.SendingFacility> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();
  }
}
