package gov.cdc.nbs.option.concept;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class ConceptOptionRowMapperTest {

  @Test
  void should_map_concept_from_row() throws SQLException {

    ConceptOptionRowMapper.Column columns = new ConceptOptionRowMapper.Column(3, 5, 7);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.value())).thenReturn("value-value");
    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getInt(columns.order())).thenReturn(113);

    ConceptOptionRowMapper mapper = new ConceptOptionRowMapper(columns);

    ConceptOption actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.value()).isEqualTo("value-value");
    assertThat(actual.name()).isEqualTo("name-value");
    assertThat(actual.order()).isEqualTo(113);
  }

  @Test
  void should_default_order_when_not_provided() throws SQLException {

    ConceptOptionRowMapper.Column columns = new ConceptOptionRowMapper.Column(3, 5, 7);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.value())).thenReturn("value-value");
    when(resultSet.getString(columns.name())).thenReturn("name-value");

    ConceptOptionRowMapper mapper = new ConceptOptionRowMapper(columns);

    ConceptOption actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.order()).isZero();
  }
}
