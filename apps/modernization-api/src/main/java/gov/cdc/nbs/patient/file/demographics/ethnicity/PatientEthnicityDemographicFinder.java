package gov.cdc.nbs.patient.file.demographics.ethnicity;

import gov.cdc.nbs.sql.MergingResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientEthnicityDemographicFinder {

  private static final String QUERY = """
      select
          [patient].as_of_date_ethnicity          as [as_of],
          [patient].[ethnic_group_ind]            as [ethnicity_value],
          [ethnicity].[code_short_desc_txt]       as [ethnicity_name],
          [patient].ethnic_unk_reason_cd          as [unknown_reason_value],
          [unknown_reason].code_short_desc_txt    as [unknown_reason_name],
          [ethnicity_detail].[code]               as [ethnicity_detail_value],
          [ethnicity_detail].code_short_desc_txt  as [ethnicity_detail_name]
      
      from Person [patient]
      
          left join NBS_SRTE..Code_value_general [ethnicity] on
                  [ethnicity].code_set_nm = 'PHVS_ETHNICITYGROUP_CDC_UNK'
              and [ethnicity].[code] = [patient].[ethnic_group_ind]
      
          left join NBS_SRTE..Code_value_general [unknown_reason] on
                  [unknown_reason].code_set_nm = 'P_ETHN_UNK_REASON'
              and [unknown_reason].[code] = [patient].[ethnic_unk_reason_cd]
      
          left join [Person_ethnic_group] [ethnic_group] on
                  [ethnic_group].person_uid = [patient].[person_uid]
              and [ethnic_group].record_status_cd = 'ACTIVE'
      
          left join NBS_SRTE..Code_value_general [ethnicity_detail] on
                  [ethnicity_detail].code_set_nm = 'P_ETHN'
              and [ethnicity_detail].[code] = [ethnic_group].ethnic_group_cd
      
      where [patient].person_uid = ?
          and [patient].cd = 'PAT'
          and [patient].[as_of_date_ethnicity] is not null
      """;

  private final JdbcClient client;

  private final ResultSetExtractor<Optional<PatientEthnicityDemographic>> extractor;

  PatientEthnicityDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.extractor = new MergingResultSetExtractor<>(
        new PatientEthnicityDemographicRowMapper(),
        PatientEthnicityDemographicMerger::merge
    );
  }

  Optional<PatientEthnicityDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.extractor);
  }


}
