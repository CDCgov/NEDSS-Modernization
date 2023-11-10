package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.ConditionRowMapper;
import gov.cdc.nbs.questionbank.page.EventTypeRowMapper;
import gov.cdc.nbs.questionbank.page.MessageMappingGuideRowMapper;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageInformationRowMapperTest {

  PageInformationRowMapper.Columns columns = new PageInformationRowMapper.Columns(
      2,
      new EventTypeRowMapper.Columns(3, 5),
      new MessageMappingGuideRowMapper.Columns(7, 11),
      13,
      17,
      19,
      new ConditionRowMapper.Columns(23, 29)
  );

  @Test
  void should_map_to_page_information() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.page())).thenReturn(1877L);
    when(resultSet.getString(columns.name())).thenReturn("name-value");
    when(resultSet.getString(columns.datamart())).thenReturn("datamart-value");
    when(resultSet.getString(columns.description())).thenReturn("description-value");

    PageInformationRowMapper mapper = new PageInformationRowMapper(columns);

    PageInformation actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.page()).isEqualTo(1877L);
    assertThat(actual.name()).isEqualTo("name-value");
    assertThat(actual.datamart()).isEqualTo("datamart-value");
    assertThat(actual.description()).isEqualTo("description-value");

  }

  @Test
  void should_map_to_page_information_with_event_type() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.page())).thenReturn(1877L);
    when(resultSet.getString(columns.eventType().value())).thenReturn("event-type-value");
    when(resultSet.getString(columns.eventType().name())).thenReturn("event-type-name");

    PageInformationRowMapper mapper = new PageInformationRowMapper(columns);

    PageInformation actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.eventType().value()).isEqualTo("event-type-value");
    assertThat(actual.eventType().name()).isEqualTo("event-type-name");

  }

  @Test
  void should_map_to_page_information_with_message_mapping_guide() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.page())).thenReturn(1877L);
    when(resultSet.getString(columns.messageMappingGuide().value())).thenReturn("message-mapping-guide-value");
    when(resultSet.getString(columns.messageMappingGuide().name())).thenReturn("message-mapping-guide-name");

    PageInformationRowMapper mapper = new PageInformationRowMapper(columns);

    PageInformation actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.messageMappingGuide().value()).isEqualTo("message-mapping-guide-value");
    assertThat(actual.messageMappingGuide().name()).isEqualTo("message-mapping-guide-name");

  }

  @Test
  void should_map_to_page_information_with_condition() throws SQLException {

    ResultSet resultSet = mock(ResultSet.class);

    when(resultSet.getLong(columns.page())).thenReturn(1877L);
    when(resultSet.getString(columns.condition().value())).thenReturn("condition-value");
    when(resultSet.getString(columns.condition().name())).thenReturn("condition-name");

    PageInformationRowMapper mapper = new PageInformationRowMapper(columns);

    PageInformation actual = mapper.mapRow(resultSet, 0);

    assertThat(actual.conditions()).satisfiesExactly(
        condition -> assertAll(
            () -> assertThat(condition.value()).isEqualTo("condition-value"),
            () -> assertThat(condition.name()).isEqualTo("condition-name")
        )
    );

  }

}
