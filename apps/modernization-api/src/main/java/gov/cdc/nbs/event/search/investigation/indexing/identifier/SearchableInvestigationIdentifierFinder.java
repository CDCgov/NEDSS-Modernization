package gov.cdc.nbs.event.search.investigation.indexing.identifier;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableInvestigationIdentifierFinder {

  private static final String QUERY =
      """
      select
          [identifier].act_id_seq  as [sequence],
          [identifier].type_cd,
          [identifier].[root_extension_txt]
      from [Act_id] [identifier]
      where   [identifier].[act_uid] = ?
           and [root_extension_txt] <> ''
      """;

  private static final int INVESTIGATION_PARAMETER = 1;
  private static final int SEQUENCE_COLUMN = 1;
  private static final int TYPE_COLUMN = 2;
  private static final int VALUE_COLUMN = 3;

  private final JdbcTemplate template;
  private final SearchableInvestigationIdentifierRowMapper mapper;

  public SearchableInvestigationIdentifierFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableInvestigationIdentifierRowMapper(
            new SearchableInvestigationIdentifierRowMapper.Column(
                SEQUENCE_COLUMN, TYPE_COLUMN, VALUE_COLUMN));
  }

  public List<SearchableInvestigation.Identifier> find(final long investigation) {
    return this.template.query(
        QUERY, statement -> statement.setLong(INVESTIGATION_PARAMETER, investigation), this.mapper);
  }
}
