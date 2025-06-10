package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.accumulation.Accumulator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

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
      """;

  private final JdbcClient client;
  private final RowMapper<PatientRaceDemographic> mapper;
  private final PatientRaceDemographicMerger merger;

  PatientRaceDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientRaceDemographicRowMapper();
    this.merger = new PatientRaceDemographicMerger();
  }

  List<PatientRaceDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .list()
        .stream()
        .collect(
            Accumulator.collecting(
                PatientRaceDemographic::race,
                merger::merge
            )
        ).stream()
        .sorted(Comparator.comparing(PatientRaceDemographic::asOf).reversed())
        .toList();
  }


}
