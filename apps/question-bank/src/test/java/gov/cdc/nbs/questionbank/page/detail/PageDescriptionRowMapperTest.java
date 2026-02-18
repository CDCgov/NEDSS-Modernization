package gov.cdc.nbs.questionbank.page.detail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class PageDescriptionRowMapperTest {

  @Test
  void should_map_to_detailed_page() throws SQLException {

    PageDescriptionRowMapper.Column columns = new PageDescriptionRowMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.identifier())).thenReturn(2143L);
    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.status())).thenReturn("status-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");

    PageDescriptionRowMapper mapper = new PageDescriptionRowMapper(columns);

    PageDescription actual = mapper.mapRow(resultSet, -1);

    assertThat(actual.identifier()).isEqualTo(2143L);
    assertThat(actual.name()).isEqualTo("name-value");
    assertThat(actual.status()).isEqualTo("status-value");
    assertThat(actual.description()).isEqualTo("description-value");
  }

  @Test
  void should_return_published() throws SQLException {
    int statusColNum = 3;
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(statusColNum)).thenReturn("Published");
    PageDescriptionRowMapper mapper = new PageDescriptionRowMapper();
    PageDescription actual = mapper.mapRow(resultSet, -1);
    assertThat(actual.status()).isEqualTo("Published");
  }

  @Test
  void should_return_initial_draft() throws SQLException {
    PageDescriptionRowMapper.Column columns = new PageDescriptionRowMapper.Column();
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(columns.status())).thenReturn("Draft");
    when(resultSet.getString(columns.publishVersionNumber())).thenReturn(null);
    PageDescriptionRowMapper mapper = new PageDescriptionRowMapper(columns);
    PageDescription actual = mapper.mapRow(resultSet, -1);
    assertThat(actual.status()).isEqualTo("Initial Draft");
  }

  @Test
  void should_return_published_with_draft() throws SQLException {
    PageDescriptionRowMapper.Column columns = new PageDescriptionRowMapper.Column();
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(columns.status())).thenReturn("Draft");
    PageDescriptionRowMapper mapper = new PageDescriptionRowMapper(columns);
    when(resultSet.getString(columns.publishVersionNumber())).thenReturn("1");
    PageDescription actual = mapper.mapRow(resultSet, -1);
    assertThat(actual.status()).isEqualTo("Published with Draft");
  }
}
