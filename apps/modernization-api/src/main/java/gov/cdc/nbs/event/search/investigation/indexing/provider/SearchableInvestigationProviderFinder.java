package gov.cdc.nbs.event.search.investigation.indexing.provider;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchableInvestigationProviderFinder {

  private static final String QUERY =
      """
      select
          [participation].[subject_entity_uid] as [entity_id],
          [participation].type_cd,
          [name].first_nm,
          [name].last_nm
      from [Participation] [participation]

          join Person_name [name] on
                  [name].person_uid = [participation].[subject_entity_uid]
              and [name].nm_use_cd = 'L'

      where   [participation].subject_class_cd = 'PSN'
          and [participation].act_class_cd = 'CASE'
          and [participation].type_cd <> 'SubjOfPHC'
          and [participation].record_status_cd = 'ACTIVE'
          and [participation].[act_uid] = ?
      """;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int TYPE_COLUMN = 2;
  private static final int FIRST_NAME_COLUMN = 3;
  private static final int LAST_NAME_COLUMN = 4;
  private static final int INVESTIGATION_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableInvestigationProviderRowMapper mapper;

  public SearchableInvestigationProviderFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableInvestigationProviderRowMapper(
            new SearchableInvestigationProviderRowMapper.Column(
                IDENTIFIER_COLUMN, TYPE_COLUMN, FIRST_NAME_COLUMN, LAST_NAME_COLUMN));
  }

  public List<SearchableInvestigation.Person.Provider> find(final long investigation) {
    return this.template.query(
        QUERY, statement -> statement.setLong(INVESTIGATION_PARAMETER, investigation), this.mapper);
  }
}
