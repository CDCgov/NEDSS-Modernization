package gov.cdc.nbs.event.search.labreport.indexing.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableLabReportLabTestRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableLabReportLabTestRowMapper.Column columns =
        new SearchableLabReportLabTestRowMapper.Column(2, 3, 5);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.result())).thenReturn("result-value");
    when(resultSet.getString(columns.alternative())).thenReturn("alternative-value");

    SearchableLabReportLabTestRowMapper mapper = new SearchableLabReportLabTestRowMapper(columns);

    SearchableLabReport.LabTest mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.name()).isEqualTo("name-value");
    assertThat(mapped.result()).isEqualTo("result-value");
    assertThat(mapped.alternative()).isEqualTo("alternative-value");
  }
}
