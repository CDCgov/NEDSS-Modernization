package gov.cdc.nbs.event.search.investigation.indexing.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableInvestigationProviderRowMapperTest {
  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableInvestigationProviderRowMapper.Column columns =
        new SearchableInvestigationProviderRowMapper.Column(2, 3, 5, 7);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(953L);
    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.firstName())).thenReturn("first-name-value");
    when(resultSet.getString(columns.lastName())).thenReturn("last-name-value");

    SearchableInvestigationProviderRowMapper mapper =
        new SearchableInvestigationProviderRowMapper(columns);

    SearchableInvestigation.Person.Provider mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.identifier()).isEqualTo(953L);
    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.code()).isEqualTo("PRV");
    assertThat(mapped.firstName()).isEqualTo("first-name-value");
    assertThat(mapped.lastName()).isEqualTo("last-name-value");
  }
}
