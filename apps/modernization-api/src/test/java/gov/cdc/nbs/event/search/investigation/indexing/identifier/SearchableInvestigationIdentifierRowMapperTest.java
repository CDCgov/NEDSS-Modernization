package gov.cdc.nbs.event.search.investigation.indexing.identifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableInvestigationIdentifierRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableInvestigationIdentifierRowMapper.Column columns =
        new SearchableInvestigationIdentifierRowMapper.Column(2, 3, 5);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getInt(columns.sequence())).thenReturn(5);
    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.value())).thenReturn("value-value");

    SearchableInvestigationIdentifierRowMapper mapper =
        new SearchableInvestigationIdentifierRowMapper(columns);

    SearchableInvestigation.Identifier mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.sequence()).isEqualTo(5);
    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.value()).isEqualTo("value-value");
  }
}
