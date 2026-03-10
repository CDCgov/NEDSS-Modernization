package gov.cdc.nbs.patient.file.events.treatment;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientFileTreatmentFinder {
  private static final String QUERY =
      """
      select
          [treated].subject_entity_uid          as [patient],
          [therapy].treatment_uid               as [id],
          [therapy].local_id                    as [local],
          [therapy].add_time                    as [created_on],
          [administered].EFFECTIVE_FROM_TIME    as [treated_on],
          case [therapy].cd
              when 'OTH' then [therapy].cd_desc_txt
                  else [treatment].treatment_desc_txt
          end                                   as [description],
          [organization].display_nm             as [organization],
          [prefix].[code_short_desc_txt]        as [provider_prefix],
          [provider].[first_nm]                 as [provider_first_name],
          [provider].[last_nm]                  as [provider_last_name]
      from Participation [treated] with (nolock)

          join Treatment [therapy] with (nolock) on
                  [therapy].treatment_uid = [treated].act_uid
              and [therapy].record_status_cd = 'ACTIVE'

          join Treatment_administered [administered] with (nolock) on
                  [administered].treatment_uid = [therapy].treatment_uid

          join [NBS_SRTE]..Treatment_code [treatment] with (nolock) on
                  [treatment].treatment_cd = [therapy].cd

          left join Participation [treatment_provider] with (nolock) on
                  [treatment_provider].act_uid = [therapy].[treatment_uid]
              and [treatment_provider].type_cd = 'ProviderOfTrmt'
              and [treatment_provider].subject_class_cd = 'PSN'

          left join person_name [provider] with (nolock) on
                  [provider].person_uid = [treatment_provider].[subject_entity_uid]

          left join Participation [oraganization_participation] with (nolock) on
                  [oraganization_participation].act_uid = [therapy].[treatment_uid]
              and [oraganization_participation].act_class_cd = 'TRMT'
              and [oraganization_participation].type_cd = 'ReporterOfTrmt'
              and [oraganization_participation].subject_class_cd = 'ORG'

          left join Organization  with (nolock) on
              [organization].organization_uid = [oraganization_participation].[subject_entity_uid]

          left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                      [prefix].[code_set_nm] = 'P_NM_PFX'
                  and [prefix].code = [provider].nm_prefix

      where   [treated].subject_entity_uid = ?
          and [treated].type_cd = 'SubjOfTrmt'
          and [treated].ACT_CLASS_CD = 'TRMT'
          and [treated].SUBJECT_CLASS_CD = 'PSN'
          and [treated].record_status_cd = 'ACTIVE'

      order by
          [therapy].[local_id] desc
      """;

  private final JdbcClient client;
  private final PatientFileTreatmentRowMapper mapper;

  PatientFileTreatmentFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientFileTreatmentRowMapper();
  }

  List<PatientFileTreatment> find(final long patient) {
    return this.client.sql(QUERY).param(patient).query(this.mapper).list();
  }
}
