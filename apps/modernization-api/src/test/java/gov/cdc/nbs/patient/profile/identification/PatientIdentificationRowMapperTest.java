package gov.cdc.nbs.patient.profile.identification;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentificationRowMapperTest {

  @Test
  void should_map_identification_from_tuple() throws SQLException {

    PatientIdentificationRowMapper.Column columns = new PatientIdentificationRowMapper.Column(
        2, 3, 5, 7, 11, 13, 17, 19, 23
    );

    PatientIdentificationRowMapper mapper = new PatientIdentificationRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.patient())).thenReturn(2357L);
    when(resultSet.getTimestamp(columns.asOf())).thenReturn(Timestamp.from(Instant.parse("2023-01-17T22:54:43Z")));

    when(resultSet.getObject(columns.asOf(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            2023, Month.JANUARY, 17,
            0, 0, 0
        )
    );

    when(resultSet.getShort(columns.sequence())).thenReturn((short) 557);
    when(resultSet.getShort(columns.version())).thenReturn((short) 227);

    when(resultSet.getString(columns.typeId())).thenReturn("type-id");
    when(resultSet.getString(columns.typeDescription())).thenReturn("type-description");

    when(resultSet.getString(columns.value())).thenReturn("value");

    PatientIdentification actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.patient()).isEqualTo(2357L);
    assertThat(actual.version()).isEqualTo((short) 227);
    assertThat(actual.sequence()).isEqualTo((short) 557);
    assertThat(actual.asOf()).isEqualTo("2023-01-17");

    assertThat(actual.type().id()).isEqualTo("type-id");
    assertThat(actual.type().description()).isEqualTo("type-description");

    assertThat(actual.authority()).isNull();

    assertThat(actual.value()).isEqualTo("value");
  }

  @Test
  void should_map_identification_from_tuple_with_authority() throws SQLException {

    PatientIdentificationRowMapper.Column columns = new PatientIdentificationRowMapper.Column(
        2, 3, 5, 7, 11, 13, 17, 19, 23
    );

    PatientIdentificationRowMapper mapper = new PatientIdentificationRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.patient())).thenReturn(2357L);
    when(resultSet.getObject(columns.asOf(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            2023, Month.JANUARY, 17,
            0, 0, 0
        )
    );

    when(resultSet.getShort(columns.sequence())).thenReturn((short) 557);
    when(resultSet.getShort(columns.version())).thenReturn((short) 227);

    when(resultSet.getString(columns.authorityId())).thenReturn("authority-id");
    when(resultSet.getString(columns.authorityDescription())).thenReturn("authority-description");

    when(resultSet.getString(columns.value())).thenReturn("value");

    PatientIdentification actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.authority().id()).isEqualTo("authority-id");
    assertThat(actual.authority().description()).isEqualTo("authority-description");

  }

  @Test
  void should_map_identification_from_tuple_with_authority_lacking_description() throws SQLException {

    PatientIdentificationRowMapper.Column columns = new PatientIdentificationRowMapper.Column(
        2, 3, 5, 7, 11, 13, 17, 19, 23
    );

    PatientIdentificationRowMapper mapper = new PatientIdentificationRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.patient())).thenReturn(2357L);
    when(resultSet.getObject(columns.asOf(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            2023, Month.JANUARY, 17,
            0, 0, 0
        )
    );
    when(resultSet.getShort(columns.sequence())).thenReturn((short) 557);
    when(resultSet.getShort(columns.version())).thenReturn((short) 227);

    when(resultSet.getString(columns.authorityId())).thenReturn("authority-id");

    when(resultSet.getString(columns.value())).thenReturn("value");

    PatientIdentification actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.authority().id()).isEqualTo("authority-id");

    assertThat(actual.authority().id()).isEqualTo("authority-id");
    assertThat(actual.authority().description()).isEqualTo("authority-id");
  }
}
