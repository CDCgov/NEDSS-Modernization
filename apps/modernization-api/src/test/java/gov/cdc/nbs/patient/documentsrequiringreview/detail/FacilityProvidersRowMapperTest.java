package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FacilityProvidersRowMapperTest {

  private final FacilityProvidersRowMapper.Column columns = new FacilityProvidersRowMapper.Column(
      new ReportingFacilityRowMapper.Column(97),
      new ProviderNameRowMapper.Column(41, 43, 47, 53),
      new SendingFacilityRowMapper.Column(163)
  );

  @Test
  void should_map_with_reporting_facility() throws SQLException {

    FacilityProvidersRowMapper mapper = new FacilityProvidersRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.reporting().name())).thenReturn("reporting-facility-value");

    DocumentRequiringReview.FacilityProviders mapped = mapper.map(resultSet);

    assertThat(mapped.getReportingFacility())
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("reporting-facility-value")
        );

    assertThat(mapped.getOrderingProvider()).isNull();
    assertThat(mapped.getSendingFacility()).isNull();
  }

  @Test
  void should_map_with_ordering_provider_when_present() throws SQLException {
    FacilityProvidersRowMapper mapper = new FacilityProvidersRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.ordering().first())).thenReturn("first-name");
    when(resultSet.getString(columns.ordering().last())).thenReturn("last-name");

    DocumentRequiringReview.FacilityProviders mapped = mapper.map(resultSet);

    assertThat(mapped.getReportingFacility()).isNull();

    assertThat(mapped.getOrderingProvider())
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("first-name last-name")
        );

    assertThat(mapped.getSendingFacility()).isNull();
  }

  @Test
  void should_map_with_sending_facility_when_present() throws SQLException {

    FacilityProvidersRowMapper mapper = new FacilityProvidersRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getString(columns.sending().name())).thenReturn("sending-facility-value");

    DocumentRequiringReview.FacilityProviders mapped = mapper.map(resultSet);

    assertThat(mapped.getReportingFacility()).isNull();
    assertThat(mapped.getOrderingProvider()).isNull();

    assertThat(mapped.getSendingFacility())
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("sending-facility-value")
        );
  }
}
