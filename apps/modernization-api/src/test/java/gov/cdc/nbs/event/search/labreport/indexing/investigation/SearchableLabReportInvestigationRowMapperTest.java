package gov.cdc.nbs.event.search.labreport.indexing.investigation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableLabReportInvestigationRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableLabReportInvestigationRowMapper.Column columns =
        new SearchableLabReportInvestigationRowMapper.Column(2, 3);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.local())).thenReturn("local-value");
    when(resultSet.getString(columns.condition())).thenReturn("condition-value");
    SearchableLabReportInvestigationRowMapper mapper =
        new SearchableLabReportInvestigationRowMapper(columns);

    SearchableLabReport.Investigation mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.local()).isEqualTo("local-value");
    assertThat(mapped.condition()).isEqualTo("condition-value");
  }
}
