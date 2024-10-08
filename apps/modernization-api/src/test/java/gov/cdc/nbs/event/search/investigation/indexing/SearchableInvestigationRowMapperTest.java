package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchableInvestigationRowMapperTest {

  @Test
  void should_map_from_result_set() throws SQLException {

    SearchableInvestigationRowMapper.Column columns = new SearchableInvestigationRowMapper.Column(
        2,
        3,
        5,
        7,
        11,
        19,
        23,
        29,
        31,
        37,
        41,
        43,
        47,
        53,
        59,
        61,
        67,
        71,
        73,
        79,
        83,
        89,
        97,
        101,
        103,
        107,
        108);

    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(columns.identifier())).thenReturn(419L);
    when(resultSet.getString(columns.classCode())).thenReturn("class-code-value");
    when(resultSet.getString(columns.mood())).thenReturn("mood-value");
    when(resultSet.getString(columns.programArea())).thenReturn("program-area-value");
    when(resultSet.getString(columns.jurisdiction())).thenReturn("jurisdiction-value");
    when(resultSet.getLong(columns.oid())).thenReturn(1019L);
    when(resultSet.getString(columns.pregnancyStatus())).thenReturn("pregnancy-status-value");
    when(resultSet.getString(columns.local())).thenReturn("local-value");

    when(resultSet.getString(columns.caseClass())).thenReturn("case-class-value");
    when(resultSet.getString(columns.caseType())).thenReturn("case-type-value");

    when(resultSet.getString(columns.outbreak())).thenReturn("outbreak-value");
    when(resultSet.getString(columns.conditionName())).thenReturn("condition-name-value");
    when(resultSet.getString(columns.condition())).thenReturn("condition-value");

    when(resultSet.getLong(columns.createdBy())).thenReturn(1013L);
    when(resultSet.getObject(columns.createdOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2022, Month.FEBRUARY, 22).atStartOfDay());

    when(resultSet.getLong(columns.updatedBy())).thenReturn(1061L);
    when(resultSet.getObject(columns.updatedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2024, Month.APRIL, 24).atStartOfDay());

    when(resultSet.getObject(columns.reportedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2010, Month.FEBRUARY, 13).atStartOfDay());

    when(resultSet.getObject(columns.startedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2007, Month.NOVEMBER, 7).atStartOfDay());

    when(resultSet.getObject(columns.closedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2023, Month.JULY, 1).atStartOfDay());

    when(resultSet.getString(columns.processing())).thenReturn("processing-value");
    when(resultSet.getString(columns.status())).thenReturn("status-value");

    when(resultSet.getString(columns.notification())).thenReturn("notification-value");
    when(resultSet.getString(columns.notificationStatus())).thenReturn("notification-status-value");
    when(resultSet.getObject(columns.notifiedOn(), LocalDateTime.class))
        .thenReturn(LocalDate.of(2005, Month.MAY, 19).atStartOfDay());

    SearchableInvestigationRowMapper mapper = new SearchableInvestigationRowMapper(columns);

    SearchableInvestigation mapped = mapper.mapRow(resultSet, 853);

    assertThat(mapped.identifier()).isEqualTo(419L);
    assertThat(mapped.classCode()).isEqualTo("class-code-value");
    assertThat(mapped.mood()).isEqualTo("mood-value");
    assertThat(mapped.programArea()).isEqualTo("program-area-value");
    assertThat(mapped.jurisdiction()).isEqualTo("jurisdiction-value");
    assertThat(mapped.oid()).isEqualTo(1019L);
    assertThat(mapped.pregnancyStatus()).isEqualTo("pregnancy-status-value");
    assertThat(mapped.local()).isEqualTo("local-value");

    assertThat(mapped.caseClass()).isEqualTo("case-class-value");
    assertThat(mapped.caseType()).isEqualTo("case-type-value");

    assertThat(mapped.outbreak()).isEqualTo("outbreak-value");
    assertThat(mapped.conditionName()).isEqualTo("condition-name-value");
    assertThat(mapped.condition()).isEqualTo("condition-value");

    assertThat(mapped.reportedOn()).isEqualTo("2010-02-13");
    assertThat(mapped.startedOn()).isEqualTo("2007-11-07");
    assertThat(mapped.closedOn()).isEqualTo("2023-07-01");

    assertThat(mapped.createdBy()).isEqualTo(1013L);
    assertThat(mapped.createdOn()).isEqualTo("2022-02-22");

    assertThat(mapped.updatedBy()).isEqualTo(1061L);
    assertThat(mapped.updatedOn()).isEqualTo("2024-04-24");

    assertThat(mapped.processing()).isEqualTo("processing-value");
    assertThat(mapped.status()).isEqualTo("status-value");

    assertThat(mapped.notification()).isEqualTo("notification-value");
    assertThat(mapped.notificationStatus()).isEqualTo("notification-status-value");
    assertThat(mapped.notifiedOn()).isEqualTo("2005-05-19");
  }
}
