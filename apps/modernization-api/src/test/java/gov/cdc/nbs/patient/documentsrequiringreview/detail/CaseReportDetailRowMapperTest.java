package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CaseReportDetailRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    CaseReportDetailRowMapper.Column columns =
        new CaseReportDetailRowMapper.Column(2, 3, 5, 7, 11, 13, 17);

    CaseReportDetailRowMapper mapper =
        new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("1989-03-19T00:00:00Z")));
    when(resultSet.getString(columns.type())).thenReturn("type-value");
    when(resultSet.getString(columns.event())).thenReturn("event-value");
    when(resultSet.getBoolean(columns.updated())).thenReturn(true);

    DocumentRequiringReview actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.id()).isEqualTo(37L);
    assertThat(actual.localId()).isEqualTo("event-value");
    assertThat(actual.type()).isEqualTo("type-value");
    assertThat(actual.dateReceived()).isEqualTo("1989-03-19T00:00:00Z");
    assertThat(actual.eventDate()).isEqualTo("1989-03-19T00:00:00Z");
    assertThat(actual.isUpdate()).isTrue();
    assertThat(actual.isElectronic()).isFalse();

  }

  @Test
  void should_map_with_providers_from_result_set() throws SQLException {

    CaseReportDetailRowMapper.Column columns =
        new CaseReportDetailRowMapper.Column(2, 3, 5, 7, 11, 13, 17);

    CaseReportDetailRowMapper mapper =
        new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("1989-03-19T00:00:00Z")));
    when(resultSet.getString(columns.sendingFacility())).thenReturn("sending-facility-value");



    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped).extracting(DocumentRequiringReview::facilityProviders)
        .extracting(DocumentRequiringReview.FacilityProviders::getSendingFacility)
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("sending-facility-value")
        );
  }

  @Test
  void should_map_with_description_from_result_set() throws SQLException {

    CaseReportDetailRowMapper.Column columns =
        new CaseReportDetailRowMapper.Column(2, 3, 5, 7, 11, 13, 17);

    CaseReportDetailRowMapper mapper =
        new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getTimestamp(columns.receivedOn())).thenReturn(
        Timestamp.from(Instant.parse("1989-03-19T00:00:00Z")));
    when(resultSet.getString(columns.condition())).thenReturn("condition-value");

    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.descriptions()).satisfiesExactly(
        actual -> assertThat(actual.title()).isEqualTo("condition-value")
    );

  }

}
