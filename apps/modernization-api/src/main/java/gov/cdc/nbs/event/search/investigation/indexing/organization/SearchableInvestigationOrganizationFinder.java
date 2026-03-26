package gov.cdc.nbs.event.search.investigation.indexing.organization;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableInvestigationOrganizationFinder {

  private static final String QUERY =
      """
      select
          [participation].subject_entity_uid,
          [participation].type_cd
      from [Participation] [participation]

      where   [participation].record_status_cd = 'ACTIVE'
          and [participation].[subject_class_cd] = 'ORG'
          and [participation].[act_class_cd] = 'CASE'
          and [participation].[act_uid] = ?
      """;

  private static final int INVESTIGATION_PARAMETER = 1;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int TYPE_COLUMN = 2;

  private final JdbcTemplate template;
  private final SearchableInvestigationOrganizationRowMapper mapper;

  public SearchableInvestigationOrganizationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableInvestigationOrganizationRowMapper(
            new SearchableInvestigationOrganizationRowMapper.Column(
                IDENTIFIER_COLUMN, TYPE_COLUMN));
  }

  public List<SearchableInvestigation.Organization> find(final long investigation) {
    return this.template.query(
        QUERY, statement -> statement.setLong(INVESTIGATION_PARAMETER, investigation), this.mapper);
  }
}
