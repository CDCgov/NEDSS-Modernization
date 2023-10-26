package gov.cdc.nbs.questionbank.page.detail;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageDescriptionRowMapperTest {

  @Test
  void should_map_to_detailed_page() throws SQLException {

    PageDescriptionRowMapper.Column columns = new PageDescriptionRowMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.identifier())).thenReturn(2143L);
    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");

    PageDescriptionRowMapper mapper = new PageDescriptionRowMapper(columns);

    PageDescription actual = mapper.mapRow(resultSet, -1);

    assertThat(actual.identifier()).isEqualTo(2143L);
    assertThat(actual.name()).isEqualTo("name-value");
    assertThat(actual.description()).isEqualTo("description-value");
  }
}

