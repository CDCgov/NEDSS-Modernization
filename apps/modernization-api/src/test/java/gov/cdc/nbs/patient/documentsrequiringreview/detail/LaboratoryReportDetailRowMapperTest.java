package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LaboratoryReportDetailRowMapperTest {

  private final LaboratoryReportDetailRowMapper.Column columns =
      new LaboratoryReportDetailRowMapper.Column(2, 3, 5,
          new FacilityProvidersRowMapper.Column(
              new ReportingFacilityRowMapper.Column(7),
              new ProviderNameRowMapper.Column(41, 43, 47, 53),
              null
          ),
          11, 13,
          new LabTestSummaryRowMapper.Column(89, 97, 101, 103, 107, 109, 113)
      );

  @Test
  void should_map_from_result_set() throws SQLException {

    LaboratoryReportDetailRowMapper mapper = new LaboratoryReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.eventDate())).thenReturn(
        Timestamp.from(Instant.parse("1989-03-19T00:00:00Z"))
    );
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("2010-12-05T00:00:00Z"))
    );

    when(resultSet.getBoolean(columns.electronic())).thenReturn(true);

    when(resultSet.getString(columns.event())).thenReturn("event-value");

    DocumentRequiringReview actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.id()).isEqualTo(37L);
    assertThat(actual.localId()).isEqualTo("event-value");
    assertThat(actual.type()).isEqualTo("Laboratory Report");
    assertThat(actual.dateReceived()).isEqualTo("2010-12-05T00:00:00Z");
    assertThat(actual.eventDate()).isEqualTo("1989-03-19T00:00:00Z");
    assertThat(actual.isUpdate()).isFalse();
    assertThat(actual.isElectronic()).isTrue();

  }

  @Test
  void should_map_with_facility_from_result_set() throws SQLException {
    LaboratoryReportDetailRowMapper mapper = new LaboratoryReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);

    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("2010-12-05T00:00:00Z"))
    );

    when(resultSet.getString(columns.facilities().reporting().name())).thenReturn("reporting-facility-value");

    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped).extracting(DocumentRequiringReview::facilityProviders)
        .extracting(DocumentRequiringReview.FacilityProviders::getReportingFacility)
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("reporting-facility-value")
        );
  }


  @Test
  void should_map_with_provider_from_result_set() throws SQLException {
    LaboratoryReportDetailRowMapper mapper = new LaboratoryReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("2010-12-05T00:00:00Z"))
    );

    when(resultSet.getString(columns.facilities().ordering().prefix())).thenReturn("prefix");
    when(resultSet.getString(columns.facilities().ordering().first())).thenReturn("first");
    when(resultSet.getString(columns.facilities().ordering().last())).thenReturn("last");
    when(resultSet.getString(columns.facilities().ordering().suffix())).thenReturn("suffix");

    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped).extracting(DocumentRequiringReview::facilityProviders)
        .extracting(DocumentRequiringReview.FacilityProviders::getOrderingProvider)
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("prefix first last suffix")
        );

  }

  @Test
  void should_map_with_coded_result_lab_test_from_result_set() throws SQLException {

    LaboratoryReportDetailRowMapper mapper = new LaboratoryReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("2010-12-05T00:00:00Z"))
    );

    when(resultSet.getString(columns.tests().name())).thenReturn("test-name-value");
    when(resultSet.getString(columns.tests().coded())).thenReturn("coded-value");

    when(resultSet.getString(columns.event())).thenReturn("event-value");

    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(
        mapped.descriptions()).satisfiesExactly(
        actual -> assertAll(
            () -> assertThat(actual.title()).isEqualTo("test-name-value"),
            () -> assertThat(actual.value()).isEqualTo("coded-value")
        )
    );

  }
}
