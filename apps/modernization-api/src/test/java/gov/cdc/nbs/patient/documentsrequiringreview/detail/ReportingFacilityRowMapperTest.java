package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportingFacilityRowMapperTest {

  private static final ReportingFacilityRowMapper.Column columns = new ReportingFacilityRowMapper.Column(97);

  @Test
  void should_map_when_name_preset() throws SQLException {

    ReportingFacilityRowMapper mapper = new ReportingFacilityRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.name())).thenReturn("reporting-facility-value");

    Optional<DocumentRequiringReview.ReportingFacility> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).hasValueSatisfying(
        actual -> assertThat(actual.name()).isEqualTo("reporting-facility-value")
    );
  }

  @Test
  void should_not_map_when_name_not_present() throws SQLException {
    ReportingFacilityRowMapper mapper = new ReportingFacilityRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    Optional<DocumentRequiringReview.ReportingFacility> mapped = mapper.maybeMap(resultSet);

    assertThat(mapped).isEmpty();
  }
}
