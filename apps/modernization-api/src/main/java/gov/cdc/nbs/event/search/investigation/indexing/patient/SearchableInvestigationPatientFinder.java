package gov.cdc.nbs.event.search.investigation.indexing.patient;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchableInvestigationPatientFinder {

  private static final String QUERY = """
      select
          [person].person_parent_uid,
          [person].local_id,
          [participation].type_cd,
          [participation].subject_class_cd,
          [name].first_nm,
          [name].last_nm,
          [person].[birth_time]
      from [Participation] [participation]
      
          join Person [person] on
                  [person].person_uid = [participation].[subject_entity_uid]
              and [person].cd = 'PAT'
      
          left join Person_name [name] on
                  [name].person_uid = [person].[person_uid]
              and [name].nm_use_cd = 'L'
              and [name].record_status_cd = 'ACTIVE'
      
      where   [participation].subject_class_cd = 'PSN'
          and [participation].record_status_cd = 'ACTIVE'
          and [participation].act_class_cd = 'CASE'
          and [participation].type_cd = 'SubjOfPHC'
          and [participation].[act_uid] = ?
      """;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int LOCAL_COLUMN = 2;
  private static final int TYPE_COLUMN = 3;
  private static final int SUBJECT_TYPE_COLUMN = 4;
  private static final int FIRST_NAME_COLUMN = 5;
  private static final int LAST_NAME_COLUMN = 6;
  private static final int GENDER_COLUMN = 7;
  private static final int BIRTHDAY_COLUMN = 8;
  private static final int INVESTIGATION_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableInvestigationPatientRowMapper mapper;

  public SearchableInvestigationPatientFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchableInvestigationPatientRowMapper(
        new SearchableInvestigationPatientRowMapper.Column(
            IDENTIFIER_COLUMN,
            LOCAL_COLUMN,
            TYPE_COLUMN,
            SUBJECT_TYPE_COLUMN,
            FIRST_NAME_COLUMN,
            LAST_NAME_COLUMN,
            GENDER_COLUMN,
            BIRTHDAY_COLUMN
        )
    );
  }

  public List<SearchableInvestigation.Person.Patient> find(final long investigation) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(INVESTIGATION_PARAMETER, investigation),
        this.mapper
    );
  }
}
