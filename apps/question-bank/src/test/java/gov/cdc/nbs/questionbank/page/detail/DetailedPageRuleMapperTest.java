package gov.cdc.nbs.questionbank.page.detail;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DetailedPageRuleMapperTest {

  @Test
  void should_map_to_detailed_page_rule() throws SQLException {

    DetailedPageRuleMapper.Column columns = new DetailedPageRuleMapper.Column();

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.id())).thenReturn(991L);
    when(resultSet.getLong(columns.page())).thenReturn(1123L);
    when(resultSet.getString(columns.logic())).thenReturn("logic-value");
    when(resultSet.getString(columns.values())).thenReturn("values-value");
    when(resultSet.getString(columns.function())).thenReturn("function-value");
    when(resultSet.getString(columns.source())).thenReturn("source-value");
    when(resultSet.getString(columns.target())).thenReturn("target-value");

    DetailedPageRuleMapper mapper = new DetailedPageRuleMapper(columns);

    DetailedPageRule actual = mapper.mapRow(resultSet, -1);

    assertThat(actual.id()).isEqualTo(991L);
    assertThat(actual.page()).isEqualTo(1123L);
    assertThat(actual.logic()).isEqualTo("logic-value");
    assertThat(actual.values()).isEqualTo("values-value");
    assertThat(actual.function()).isEqualTo("function-value");
    assertThat(actual.source()).isEqualTo("source-value");
    assertThat(actual.target()).isEqualTo("target-value");
  }
}
