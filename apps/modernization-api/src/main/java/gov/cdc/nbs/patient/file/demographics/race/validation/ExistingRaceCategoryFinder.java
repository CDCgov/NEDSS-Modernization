package gov.cdc.nbs.patient.file.demographics.race.validation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ExistingRaceCategoryFinder {

  private static final String QUERY = """
      select
          [patient_race].race_category_cd     as [race_id],
          [race].code_short_desc_txt          as [race_description]
      from Person_race [patient_race]
            
          join NBS_SRTE..Code_value_general [race] on
                  [race].code_set_nm = 'RACE_CALCULATED'
              and [race].[code] = [patient_race].race_category_cd
            
            
      where   [patient_race].[person_uid] = ?
          and [patient_race].[race_category_cd] = ?
          and [patient_race].[record_status_cd] = 'ACTIVE'
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int CATEGORY_PARAMETER = 2;

  private final JdbcTemplate template;
  private final ExistingRaceCategoryResultSetMapper mapper;

  ExistingRaceCategoryFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new ExistingRaceCategoryResultSetMapper(new ExistingRaceCategoryResultSetMapper.Column());
  }

  Optional<ExistingRaceCategory> find(final long patient, final String category) {
    return this.template.query(
            QUERY,
            statement -> {
              statement.setLong(PATIENT_PARAMETER, patient);
              statement.setString(CATEGORY_PARAMETER, category);
            },
            this.mapper
        ).stream()
        .findFirst();
  }


}
