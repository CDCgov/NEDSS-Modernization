package gov.cdc.nbs.patient.file.demographics.summary;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SummarizedPatientDemographicsFinder {

  private static final String QUERY = """
      select
          [ethnicity].[code_short_desc_txt]
      from Person [patient]
      
          left join NBS_SRTE..Code_value_general [ethnicity] on
                  [ethnicity].code_set_nm = 'PHVS_ETHNICITYGROUP_CDC_UNK'
              and [ethnicity].[code] = [patient].[ethnic_group_ind]
      
      where [patient].[cd] = 'PAT'
          and [patient].[person_uid] = ?
      """;

  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<SummarizedPatientDemographics> mapper;


  SummarizedPatientDemographicsFinder(
      final JdbcTemplate template
  ) {
    this.template = template;
    this.mapper = new SummarizedPatientDemographicsRowMapper();
  }

  Optional<SummarizedPatientDemographics> find(final long patient) {
    return this.template.query(
            QUERY, statement -> statement.setLong(PATIENT_PARAMETER, patient),
            this.mapper
        ).stream()
        .findFirst();
  }


}
