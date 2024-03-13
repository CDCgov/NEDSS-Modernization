package gov.cdc.nbs.event.search.labreport.indexing.provider;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchableLabReportProviderFinder {

  private static final String QUERY = """
      select
          [participation].[subject_entity_uid] as [entity_id],
          [participation].type_cd,
          [participation].subject_class_cd,
          [name].first_nm,
          [name].last_nm
      from [Participation] [participation]
            
          join Person_name [name] on
                  [name].person_uid = [participation].[subject_entity_uid]
              and [name].nm_use_cd = 'L'
            
      where   [participation].subject_class_cd = 'PSN'
          and [participation].type_cd <> 'PATSBJ'
          and [participation].record_status_cd = 'ACTIVE'
          and [participation].[act_uid] = ?
      """;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int TYPE_COLUMN = 2;
  private static final int SUBJECT_TYPE_COLUMN = 3;
  private static final int FIRST_NAME_COLUMN = 4;
  private static final int LAST_NAME_COLUMN = 5;
  private static final int LAB_REPORT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableLabReportProviderRowMapper mapper;

  public SearchableLabReportProviderFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchableLabReportProviderRowMapper(
        new SearchableLabReportProviderRowMapper.Column(
            IDENTIFIER_COLUMN,
            TYPE_COLUMN,
            SUBJECT_TYPE_COLUMN,
            FIRST_NAME_COLUMN,
            LAST_NAME_COLUMN
        )
    );
  }

  public List<SearchableLabReport.Person.Provider> find(final long lab) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(LAB_REPORT_PARAMETER, lab),
        this.mapper
    );
  }
}
