package gov.cdc.nbs.patient.file.demographics.race.validation;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExistingRaceCategoryResultSetMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    ExistingRaceCategoryResultSetMapper.Column columns = new ExistingRaceCategoryResultSetMapper.Column(3, 5);

    ExistingRaceCategoryResultSetMapper mapper = new ExistingRaceCategoryResultSetMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.identifier())).thenReturn("identifier-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");

    ExistingRaceCategory actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.identifier()).isEqualTo("identifier-value");
    assertThat(actual.description()).isEqualTo("description-value");

    verify(resultSet).getString(columns.identifier());
    verify(resultSet).getString(columns.description());
    verifyNoMoreInteractions(resultSet);
  }
}
