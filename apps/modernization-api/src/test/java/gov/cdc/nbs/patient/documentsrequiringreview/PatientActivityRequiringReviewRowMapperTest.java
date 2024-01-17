package gov.cdc.nbs.patient.documentsrequiringreview;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientActivityRequiringReviewRowMapperTest {

  @Test
  void should_map_to_Case_Report_for_unknown_types() throws SQLException {

    PatientActivityRequiringReviewRowMapper.Column columns =
        new PatientActivityRequiringReviewRowMapper.Column(2, 3, 5);

    PatientActivityRequiringReviewRowMapper mapper =
        new PatientActivityRequiringReviewRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(1063L);
    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getString(columns.type())).thenReturn("type-value");

    PatientActivityRequiringReview actual = mapper.mapRow(resultSet, 0);

    assertThat(actual).isInstanceOf(PatientActivityRequiringReview.CaseReport.class);

    assertThat(actual.id()).isEqualTo(37L);
    assertThat(actual.total()).isEqualTo(1063L);
    assertThat(actual.type()).isEqualTo("type-value");

  }

  @Test
  void should_map_to_Laboratory_Report_when_type_is_LabReport() throws SQLException {

    PatientActivityRequiringReviewRowMapper.Column columns =
        new PatientActivityRequiringReviewRowMapper.Column(2, 3, 5);

    PatientActivityRequiringReviewRowMapper mapper =
        new PatientActivityRequiringReviewRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(1063L);
    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getString(columns.type())).thenReturn("LabReport");

    PatientActivityRequiringReview actual = mapper.mapRow(resultSet, 0);

    assertThat(actual).isInstanceOf(PatientActivityRequiringReview.LabReport.class);

    assertThat(actual.type()).isEqualTo("Laboratory Report");

  }

  @Test
  void should_map_to_Morbidity_Report_when_type_is_MorbReport() throws SQLException {

    PatientActivityRequiringReviewRowMapper.Column columns =
        new PatientActivityRequiringReviewRowMapper.Column(2, 3, 5);

    PatientActivityRequiringReviewRowMapper mapper =
        new PatientActivityRequiringReviewRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(1063L);
    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getString(columns.type())).thenReturn("MorbReport");

    PatientActivityRequiringReview actual = mapper.mapRow(resultSet, 0);

    assertThat(actual).isInstanceOf(PatientActivityRequiringReview.MorbidityReport.class);

    assertThat(actual.type()).isEqualTo("Morbidity Report");

  }

}
