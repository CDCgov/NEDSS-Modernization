package gov.cdc.nbs.patient.file.demographics.summary;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientDemographicsSummaryRaceFinder {

  private static final String QUERY = """
      select
          coalesce([race].code_short_desc_txt, [patient].race_category_cd, '')
      from person_race [patient]
      
              left join NBS_SRTE..Code_value_general [race] on
                  [race].code_set_nm = 'RACE_CALCULATED'
              and [race].[code] = [patient].race_category_cd
      
      
      where [patient].person_uid = ?
          and [patient].record_status_cd = 'ACTIVE'
          and [patient].race_category_cd = [patient].race_cd
      order by
          [patient].[as_of_date] desc
      """;

  private final JdbcClient client;

  PatientDemographicsSummaryRaceFinder(final JdbcClient client) {
    this.client = client;
  }

  Collection<String> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(String.class)
        .list();
  }
}
