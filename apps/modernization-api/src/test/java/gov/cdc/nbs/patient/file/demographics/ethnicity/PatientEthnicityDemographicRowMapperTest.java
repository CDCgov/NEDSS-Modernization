package gov.cdc.nbs.patient.file.demographics.ethnicity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.data.selectable.Selectable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PatientEthnicityDemographicRowMapperTest {

  private final PatientEthnicityDemographicRowMapper mapper =
      new PatientEthnicityDemographicRowMapper();

  @Test
  void should_map_row() throws SQLException {
    // Mock
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    LocalDateTime asOf = LocalDateTime.now();
    when(resultSet.getObject(1, LocalDateTime.class)).thenReturn(asOf); // as of
    // ethnicity
    when(resultSet.getString(2)).thenReturn("2135-2");
    when(resultSet.getString(3)).thenReturn("Hispanic or Latino");
    // unknown reason
    when(resultSet.getString(4)).thenReturn(null);
    when(resultSet.getString(5)).thenReturn(null);
    // detail
    when(resultSet.getString(6)).thenReturn("2155-0");
    when(resultSet.getString(7)).thenReturn("Central American");

    // Act
    PatientEthnicityDemographic demographic = mapper.mapRow(resultSet, 1);

    // Verify
    assertThat(demographic.asOf()).isEqualTo(asOf.toLocalDate());

    assertThat(demographic.ethnicGroup().value()).isEqualTo("2135-2");
    assertThat(demographic.ethnicGroup().name()).isEqualTo("Hispanic or Latino");

    assertThat(demographic.unknownReason()).isNull();

    Selectable detailed = demographic.detailed().toArray(new Selectable[1])[0];
    assertThat(detailed.value()).isEqualTo("2155-0");
    assertThat(detailed.name()).isEqualTo("Central American");
  }

  @Test
  void should_map_row_unknown() throws SQLException {
    // Mock
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    LocalDateTime asOf = LocalDateTime.now();
    when(resultSet.getObject(1, LocalDateTime.class)).thenReturn(asOf); // as of
    // ethnicity
    when(resultSet.getString(2)).thenReturn("UNK");
    when(resultSet.getString(3)).thenReturn("unknown");
    // unknown reason
    when(resultSet.getString(4)).thenReturn("0");
    when(resultSet.getString(5)).thenReturn("Refused to answer");
    // detail
    when(resultSet.getString(6)).thenReturn(null);
    when(resultSet.getString(7)).thenReturn(null);

    // Act
    PatientEthnicityDemographic demographic = mapper.mapRow(resultSet, 1);

    // Verify
    assertThat(demographic.asOf()).isEqualTo(asOf.toLocalDate());

    assertThat(demographic.ethnicGroup().value()).isEqualTo("UNK");
    assertThat(demographic.ethnicGroup().name()).isEqualTo("unknown");

    assertThat(demographic.unknownReason().value()).isEqualTo("0");
    assertThat(demographic.unknownReason().name()).isEqualTo("Refused to answer");

    Selectable detailed = demographic.detailed().toArray(new Selectable[1])[0];
    assertThat(detailed).isNull();
  }

  @Test
  void should_map_row_null() throws SQLException {
    // Mock
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    LocalDateTime asOf = LocalDateTime.now();
    when(resultSet.getObject(1, LocalDateTime.class)).thenReturn(asOf); // as of
    // ethnicity
    when(resultSet.getString(2)).thenReturn(null);
    when(resultSet.getString(3)).thenReturn(null);
    // unknown reason
    when(resultSet.getString(4)).thenReturn(null);
    when(resultSet.getString(5)).thenReturn(null);
    // detail
    when(resultSet.getString(6)).thenReturn(null);
    when(resultSet.getString(7)).thenReturn(null);

    // Act
    PatientEthnicityDemographic demographic = mapper.mapRow(resultSet, 1);

    // Verify
    assertThat(demographic.asOf()).isEqualTo(asOf.toLocalDate());

    assertThat(demographic.ethnicGroup()).isNull();

    assertThat(demographic.unknownReason()).isNull();

    Selectable detailed = demographic.detailed().toArray(new Selectable[1])[0];
    assertThat(detailed).isNull();
  }

  @Test
  void should_map_matching_detail() throws SQLException {
    // Mock
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    LocalDateTime asOf = LocalDateTime.now();
    when(resultSet.getObject(1, LocalDateTime.class)).thenReturn(asOf); // as of
    // ethnicity
    when(resultSet.getString(2)).thenReturn("2135-2");
    when(resultSet.getString(3)).thenReturn("Hispanic or Latino");
    // unknown reason
    when(resultSet.getString(4)).thenReturn(null);
    when(resultSet.getString(5)).thenReturn(null);
    // detail
    when(resultSet.getString(6)).thenReturn("2135-2");
    when(resultSet.getString(7)).thenReturn("Hispanic or Latino");

    // Act
    PatientEthnicityDemographic demographic = mapper.mapRow(resultSet, 1);

    // Verify
    assertThat(demographic.detailed()).isEmpty();
  }
}
