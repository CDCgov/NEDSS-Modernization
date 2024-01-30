package gov.cdc.nbs.provider;



import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProviderNameRowMapperTest {

  @Test
  void should_map_full_provider_name_from_ResultSet() throws SQLException {

    ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.first())).thenReturn("first-name");
    when(resultSet.getString(columns.last())).thenReturn("last-name");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix");

    ProviderNameRowMapper mapper = new ProviderNameRowMapper(columns);

    String actual = mapper.map(resultSet);

    assertThat(actual).isEqualTo("prefix first-name last-name suffix");

  }

  @Test
  void should_map_full_provider_name_from_ResultSet_without_prefix() throws SQLException {

    ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.first())).thenReturn("first-name");
    when(resultSet.getString(columns.last())).thenReturn("last-name");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix");

    ProviderNameRowMapper mapper = new ProviderNameRowMapper(columns);

    String actual = mapper.map(resultSet);

    assertThat(actual).isEqualTo("first-name last-name suffix");

  }

  @Test
  void should_map_full_provider_name_from_ResultSet_without_first_name() throws SQLException {

    ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.last())).thenReturn("last-name");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix");

    ProviderNameRowMapper mapper = new ProviderNameRowMapper(columns);

    String actual = mapper.map(resultSet);

    assertThat(actual).isEqualTo("prefix last-name suffix");

  }

  @Test
  void should_map_full_provider_name_from_ResultSet_without_last_name() throws SQLException {

    ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.first())).thenReturn("first-name");
    when(resultSet.getString(columns.suffix())).thenReturn("suffix");

    ProviderNameRowMapper mapper = new ProviderNameRowMapper(columns);

    String actual = mapper.map(resultSet);

    assertThat(actual).isEqualTo("prefix first-name suffix");

  }

  @Test
  void should_map_full_provider_name_from_ResultSet_without_suffix() throws SQLException {

    ProviderNameRowMapper.Column columns = new ProviderNameRowMapper.Column(41, 43, 47, 53);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.first())).thenReturn("first-name");
    when(resultSet.getString(columns.last())).thenReturn("last-name");

    ProviderNameRowMapper mapper = new ProviderNameRowMapper(columns);

    String actual = mapper.map(resultSet);

    assertThat(actual).isEqualTo("prefix first-name last-name");

  }

}
