package gov.cdc.nbs.event.search.labreport.indexing.organization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class SearchableLabReportOrganizationRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableLabReportOrganizationRowMapper.Column columns =
        new SearchableLabReportOrganizationRowMapper.Column(2, 3, 5, 7);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(953L);
    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.subjectType())).thenReturn("subject-type-value");
    when(resultSet.getString(columns.name())).thenReturn("name-value");

    SearchableLabReportOrganizationRowMapper mapper =
        new SearchableLabReportOrganizationRowMapper(columns);

    SearchableLabReport.Organization mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.identifier()).isEqualTo(953L);
    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.subjectType()).isEqualTo("subject-type-value");
    assertThat(mapped.name()).isEqualTo("name-value");
  }
}
