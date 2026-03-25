package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class LabTestSummaryRowMapperTest {

  private final LabTestSummaryRowMapper.Column columns =
      new LabTestSummaryRowMapper.Column(2, 3, 5, 6, 11, 13, 17);

  @Test
  void should_map_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.coded()).isNull();
    assertThat(mapped.numeric()).isNull();
    assertThat(mapped.high()).isNull();
    assertThat(mapped.low()).isNull();
    assertThat(mapped.unit()).isNull();
  }

  @Test
  void should_map_with_coded_result_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.coded())).thenReturn("coded-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.coded()).isEqualTo("coded-value");
  }

  @Test
  void should_map_with_numeric_result_from_result_set() throws SQLException {
    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getBigDecimal(columns.numeric())).thenReturn(new BigDecimal("739.53"));

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.numeric()).isEqualTo("739.53");
  }

  @Test
  void should_map_with_high_range_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.high())).thenReturn("high-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.high()).isEqualTo("high-value");
  }

  @Test
  void should_map_with_low_range_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.low())).thenReturn("low-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.low()).isEqualTo("low-value");
  }

  @Test
  void should_map_with_unit_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.unit())).thenReturn("unit-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.unit()).isEqualTo("unit-value");
  }

  @Test
  void should_map_with_status_from_result_set() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.status())).thenReturn("status-value");

    LabTestSummaryRowMapper mapper = new LabTestSummaryRowMapper(columns);

    LabTestSummary mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.status()).isEqualTo("status-value");
  }
}
