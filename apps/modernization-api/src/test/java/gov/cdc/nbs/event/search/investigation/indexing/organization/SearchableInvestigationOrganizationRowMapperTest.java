package gov.cdc.nbs.event.search.investigation.indexing.organization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableInvestigationOrganizationRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableInvestigationOrganizationRowMapper.Column columns =
        new SearchableInvestigationOrganizationRowMapper.Column(2, 3);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(953L);
    when(resultSet.getString(columns.type())).thenReturn("type-value");

    SearchableInvestigationOrganizationRowMapper mapper =
        new SearchableInvestigationOrganizationRowMapper(columns);

    SearchableInvestigation.Organization mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.identifier()).isEqualTo(953L);
    assertThat(mapped.type()).isEqualTo("type-value");
  }
}
