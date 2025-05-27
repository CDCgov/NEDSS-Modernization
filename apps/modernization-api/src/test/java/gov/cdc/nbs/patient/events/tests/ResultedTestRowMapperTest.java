package gov.cdc.nbs.patient.events.tests;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultedTestRowMapperTest {

  private final ResultedTestRowMapper.Column columns =
      new ResultedTestRowMapper.Column(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

  @Test
  void should_map_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).isEmpty();
  }

  @Test
  void should_map_coded_result_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.coded())).thenReturn("coded-value");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).contains("coded-value");
  }

  @Test
  void should_map_text_result_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.text())).thenReturn("text-value");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).contains("text-value");
  }

  @Test
  void should_map_text_result_with_status_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.text())).thenReturn("text-value");
    when(resultSet.getString(columns.status())).thenReturn("status-value");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).contains("text-value - (status-value)");
  }

  @Test
  void should_map_numeric_result_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("739.53"));
    when(resultSet.getInt(columns.scale())).thenReturn(2);
    when(resultSet.getString(columns.comparator())).thenReturn("=");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).isEqualTo("=739.53");
  }

  @Test
  void should_map_numeric_result_with_unit_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("739.53"));
    when(resultSet.getInt(columns.scale())).thenReturn(2);
    when(resultSet.getString(columns.unit())).thenReturn("unit");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.description()).isEqualTo("739.53 unit");
  }

  @Test
  void should_map_numeric_result_with_full_reference_range_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("45.5"));
    when(resultSet.getInt(columns.scale())).thenReturn(1);
    when(resultSet.getString(columns.high())).thenReturn("high");
    when(resultSet.getString(columns.low())).thenReturn("low");
    when(resultSet.getString(columns.status())).thenReturn("status");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.description()).isEqualTo("45.5\nReference Range - (low-high) - (status)");
  }

  @Test
  void should_map_numeric_result_with_only_high_reference_range_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("45.5"));
    when(resultSet.getInt(columns.scale())).thenReturn(1);
    when(resultSet.getString(columns.high())).thenReturn("high");
    when(resultSet.getString(columns.status())).thenReturn("status");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.description()).isEqualTo("45.5\nReference Range - (high) - (status)");
  }

  @Test
  void should_map_numeric_result_with_only_low_reference_range_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("45.5"));
    when(resultSet.getInt(columns.scale())).thenReturn(1);
    when(resultSet.getString(columns.low())).thenReturn("low");
    when(resultSet.getString(columns.status())).thenReturn("status");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.description()).isEqualTo("45.5\nReference Range - (low) - (status)");
  }

  @Test
  void should_not_map_reference_range_without_numeric_result() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");

    when(resultSet.getString(columns.high())).thenReturn("high");
    when(resultSet.getString(columns.low())).thenReturn("low");
    when(resultSet.getString(columns.status())).thenReturn("status");

    ResultedTestRowMapper mapper = new ResultedTestRowMapper(columns);

    ResultedTest mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.description()).isEmpty();
  }
}

