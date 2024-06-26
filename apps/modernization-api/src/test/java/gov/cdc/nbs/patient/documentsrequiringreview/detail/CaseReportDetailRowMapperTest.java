package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CaseReportDetailRowMapperTest {

  private final CaseReportDetailRowMapper.Column columns = new CaseReportDetailRowMapper.Column(2, 3, 5,
      new FacilityProvidersRowMapper.Column(
          null,
          null,
          new SendingFacilityRowMapper.Column(9)
      ),
      11, 13, 17
  );

  @Test
  void should_map_from_result_set() throws SQLException {

    CaseReportDetailRowMapper mapper = new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getObject(columns.receivedOn(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            1989, Month.MARCH, 19,
            0, 0, 0
        )
    );

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

    CaseReportDetailRowMapper mapper = new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getObject(columns.receivedOn(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            1989, Month.MARCH, 19,
            0, 0, 0
        )
    );
    when(resultSet.getString(columns.facilities().sending().name())).thenReturn("sending-facility-value");



    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped).extracting(DocumentRequiringReview::facilityProviders)
        .extracting(DocumentRequiringReview.FacilityProviders::getSendingFacility)
        .satisfies(
            actual -> assertThat(actual.name()).isEqualTo("sending-facility-value")
        );
  }

  @Test
  void should_map_with_description_from_result_set() throws SQLException {

    CaseReportDetailRowMapper mapper = new CaseReportDetailRowMapper(columns);

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.identifier())).thenReturn(37L);
    when(resultSet.getObject(columns.receivedOn(), LocalDateTime.class)).thenReturn(
        LocalDateTime.of(
            1989, Month.MARCH, 19,
            0, 0, 0
        )
    );
    when(resultSet.getString(columns.condition())).thenReturn("condition-value");

    DocumentRequiringReview mapped = mapper.mapRow(resultSet, 0);

    assertThat(mapped.descriptions()).satisfiesExactly(
        actual -> assertThat(actual.title()).isEqualTo("condition-value")
    );

  }

}
