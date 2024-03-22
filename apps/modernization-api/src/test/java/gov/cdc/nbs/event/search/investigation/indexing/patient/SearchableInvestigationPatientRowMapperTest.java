package gov.cdc.nbs.event.search.investigation.indexing.patient;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchableInvestigationPatientRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {
    SearchableInvestigationPatientRowMapper.Column columns = new SearchableInvestigationPatientRowMapper.Column(
        2,
        3,
        5,
        7,
        11,
        13,
        17,
        19
    );

    ResultSet resultSet = mock(ResultSet.class);


    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.subjectType())).thenReturn("subject-type-value");
    when(resultSet.getString(columns.firstName())).thenReturn("first-name-value");
    when(resultSet.getString(columns.lastName())).thenReturn("last-name-value");
    when(resultSet.getString(columns.gender())).thenReturn("gender-value");
    when(resultSet.getObject(columns.birthday(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            1943, Month.NOVEMBER, 3,
            0, 0, 0
        )
    );
    when(resultSet.getLong(columns.identifier())).thenReturn(829L);

    SearchableInvestigationPatientRowMapper mapper = new SearchableInvestigationPatientRowMapper(columns);

    SearchableInvestigation.Person.Patient mapped = mapper.mapRow(resultSet, 1049);

    assertThat(mapped.type()).isEqualTo("type-value");
    assertThat(mapped.code()).isEqualTo("PAT");
    assertThat(mapped.subjectType()).isEqualTo("subject-type-value");
    assertThat(mapped.firstName()).isEqualTo("first-name-value");
    assertThat(mapped.lastName()).isEqualTo("last-name-value");
    assertThat(mapped.gender()).isEqualTo("gender-value");
    assertThat(mapped.birthday()).isEqualTo("1943-11-03");
    assertThat(mapped.identifier()).isEqualTo(829L);
  }
}
