package gov.cdc.nbs.patient.file.demographics.identification;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientIdentificationDemographicFinder {

  private static final String QUERY = """
      select
          [identification].[entity_id_seq]        as [identifier],
          [identification].[as_of_date]           as [as_of],
          [identification].[type_cd]              as [type_value],
          [type].code_short_desc_txt              as [type_name],
          [identification].assigning_authority_cd as [authority_value],
          [authority].code_short_desc_txt         as [authority_name],
          [identification].root_extension_txt     as [value]
      from [Entity_id] [identification]\s
      
          join NBS_SRTE..Code_value_general [type] with (nolock) on
                  [type].code_set_nm = 'EI_TYPE_PAT'
              and [type].code = [identification].[type_cd]
      
          left join NBS_SRTE..Code_value_general [authority] with (nolock) on
                  [authority].[code_set_nm] = 'EI_AUTH_PAT'
              and [authority].[code] = [identification].assigning_authority_cd
      
      where   [identification].[entity_uid] = ?
          and [identification].[record_status_cd] = 'ACTIVE'
      """;

  private final JdbcClient client;
  private final RowMapper<PatientIdentificationDemographic> mapper;

  PatientIdentificationDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientIdentificationDemographicRowMapper();
  }

  List<PatientIdentificationDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .list();
  }
}
