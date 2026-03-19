package gov.cdc.nbs.event.search.labreport.indexing.identifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableLabReportIdentifierRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableLabReportIdentifierRowMapper.Column columns =
        new SearchableLabReportIdentifierRowMapper.Column(2, 3, 5);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");
    when(resultSet.getString(columns.value())).thenReturn("value-value");

    SearchableLabReportIdentifierRowMapper mapper =
        new SearchableLabReportIdentifierRowMapper(columns);

    SearchableLabReport.Identifier mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.description()).isEqualTo("description-value");
    assertThat(mapped.value()).isEqualTo("value-value");
  }
}
