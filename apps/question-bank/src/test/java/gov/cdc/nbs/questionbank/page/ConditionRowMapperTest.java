package gov.cdc.nbs.questionbank.page;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConditionRowMapperTest {

  @Test
  void should_map_to_condition() throws SQLException {

    ConditionRowMapper.Columns columns = new ConditionRowMapper.Columns(13, 17);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.value())).thenReturn("value");
    when(resultSet.getString(columns.name())).thenReturn("name");

    ConditionRowMapper mapper = new ConditionRowMapper(columns);

    Condition actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.value()).isEqualTo("value");
    assertThat(actual.name()).isEqualTo("name");
  }

  @Test
  void should_map_to_null_when_value_not_present() throws SQLException {

    ConditionRowMapper.Columns columns = new ConditionRowMapper.Columns(13, 17);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name");

    ConditionRowMapper mapper = new ConditionRowMapper(columns);

    Condition actual = mapper.mapRow(resultSet, 0);

    assertThat(actual).isNull();
  }
}
