package gov.cdc.nbs.patient.file.demographics.summary;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
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
  
  private final JdbcClient client;
  private final RowMapper<SummarizedPatientDemographics> mapper;


  SummarizedPatientDemographicsFinder(
      final JdbcClient client
  ) {
    this.client = client;
    this.mapper = new SummarizedPatientDemographicsRowMapper();
  }

  Optional<SummarizedPatientDemographics> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .optional();
  }


}
