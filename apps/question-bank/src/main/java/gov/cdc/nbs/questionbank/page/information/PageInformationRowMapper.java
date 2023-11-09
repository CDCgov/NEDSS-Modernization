package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.questionbank.page.SelectableCondition;
import gov.cdc.nbs.questionbank.page.ConditionRowMapper;
import gov.cdc.nbs.questionbank.page.SelectableEventType;
import gov.cdc.nbs.questionbank.page.EventTypeRowMapper;
import gov.cdc.nbs.questionbank.page.SelectableMessageMappingGuide;
import gov.cdc.nbs.questionbank.page.MessageMappingGuideRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

class PageInformationRowMapper implements RowMapper<PageInformation> {

  record Columns(
      int page,
      EventTypeRowMapper.Columns eventType,
      MessageMappingGuideRowMapper.Columns messageMappingGuide,
      int name,
      int datamart,
      int description,
      ConditionRowMapper.Columns condition
  ) {
  }


  private final Columns columns;
  private final EventTypeRowMapper eventTypeRowMapper;
  private final MessageMappingGuideRowMapper messageMappingGuideRowMapper;
  private final ConditionRowMapper conditionRowMapper;

  PageInformationRowMapper(final Columns columns) {
    this.columns = columns;
    this.eventTypeRowMapper = new EventTypeRowMapper(columns.eventType());
    this.messageMappingGuideRowMapper = new MessageMappingGuideRowMapper(columns.messageMappingGuide());
    this.conditionRowMapper = new ConditionRowMapper(columns.condition());
  }

  @Override
  public PageInformation mapRow(final ResultSet resultSet, int rowNum) throws SQLException {

    long page = resultSet.getLong(this.columns.page());
    SelectableEventType eventType = eventTypeRowMapper.mapRow(resultSet, rowNum);
    SelectableMessageMappingGuide messageMappingGuide = messageMappingGuideRowMapper.mapRow(resultSet, rowNum);

    String name = resultSet.getString(this.columns.name());
    String datamart = resultSet.getString(this.columns.datamart());
    String description = resultSet.getString(this.columns.description());

    Collection<SelectableCondition> associated = asConditions(resultSet, rowNum);

    return new PageInformation(
        page,
        eventType,
        messageMappingGuide,
        name,
        datamart,
        description,
        associated
    );
  }

  private Collection<SelectableCondition> asConditions(final ResultSet resultSet, final int rowNum) throws SQLException {
    SelectableCondition condition = conditionRowMapper.mapRow(resultSet, rowNum);

    return condition == null ? List.of() : List.of(condition);
  }
}
