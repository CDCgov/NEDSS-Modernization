package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.sql.AccumulatingResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientRaceDemographicFinder {

  private static final String QUERY = """
      select
          [person_race].as_of_date            as [as_of],
          [person_race].race_category_cd      as [race_value],
          [race].code_short_desc_txt          as [race_name],
          [person_race].[race_cd]             as [detailed_race_value],
          [detailed_race].code_short_desc_txt as [detailed_race_name]
      from Person_race [person_race]
      
          join NBS_SRTE..Code_value_general [race] on
                  [race].code_set_nm = 'RACE_CALCULATED'
              and [race].[code] = [person_race].race_category_cd
      
          left join NBS_SRTE..Race_code [detailed_race] on
                  [detailed_race].[code] = [person_race].race_cd
      
      where   [person_race].[person_uid] = ?
          and [person_race].[record_status_cd] = 'ACTIVE'
      
      order by
          [person_race].as_of_date desc,
          [person_race].add_time desc
      """;

  private final JdbcClient client;
  private final ResultSetExtractor<Collection<PatientRaceDemographic>> extractor;

  PatientRaceDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.extractor = new AccumulatingResultSetExtractor<>(
        (resultSet, rowNum) -> resultSet.getString(2),
        new PatientRaceDemographicRowMapper(),
        PatientRaceDemographicMerger::merge
    );
  }

  Collection<PatientRaceDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.extractor);
  }


}
