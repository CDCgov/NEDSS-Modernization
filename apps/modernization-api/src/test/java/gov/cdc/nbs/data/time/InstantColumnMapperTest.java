package gov.cdc.nbs.data.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Test;

class InstantColumnMapperTest {

  @Test
  void should_map_sql_server_datetime_to_instant() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getObject(107, LocalDateTime.class))
        .thenReturn(LocalDateTime.of(1989, Month.JULY, 11, 0, 0, 0));

    Instant actual = InstantColumnMapper.map(resultSet, 107);

    assertThat(actual).isEqualTo("1989-07-11T00:00:00Z");
  }

  @Test
  void should_not_map_sql_server_datetime_to_instant_when_null() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    Instant actual = InstantColumnMapper.map(resultSet, 107);

    assertThat(actual).isNull();
  }
}
