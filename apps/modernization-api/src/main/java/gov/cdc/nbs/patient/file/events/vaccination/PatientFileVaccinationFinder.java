package gov.cdc.nbs.patient.file.events.vaccination;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientFileVaccinationFinder {
  private static final String QUERY = """
      with revisions (person_uid, mpr_id) as (
          select
              [patient].[person_uid],
              [patient].person_parent_uid
          from  Person [patient] with (nolock)
          where   [patient].person_parent_uid = ?
              and [patient].person_parent_uid <> [patient].person_uid
              and [patient].cd = 'PAT'
              and [patient].record_status_cd = 'ACTIVE'
      )
      select
          [revisions].mpr_id                      as [patient],
          [vaccination].intervention_uid          as [id],
          [vaccination].local_id                  as [local],
          [vaccination].[add_time]                as [created_on],
          [organization].display_nm               as [organization],
          [prefix].[code_short_desc_txt]          as [provider_prefix],
          [provider].[first_nm]                   as [provider_first_name],
          [provider].[last_nm]                    as [provider_last_name],
          [vaccination].activity_from_time        as [administered_on],
          [vaccine].code_short_desc_txt           as [administered]
      from revisions
      
          join participation [vaccinated] on
                  [vaccinated].record_status_cd = 'ACTIVE'
              and [vaccinated].[type_cd] in ('SubOfVacc', 'VaccGiven')
              and [vaccinated].[subject_class_cd] = 'PAT'
              and [vaccinated].[subject_entity_uid] = [revisions].person_uid
      
      
          join Intervention [vaccination] on
                  [vaccination].[intervention_uid] = [vaccinated].[act_uid]
              and [vaccination].record_status_cd = 'ACTIVE'
      
          join NBS_SRTE..Code_value_general [vaccine] on
                  [vaccine].[code_set_nm] = 'VAC_NM'
              and [vaccine].[code] = [vaccination].[material_cd]
      
          left join Participation [oraganization_participation] with (nolock) on
                  [oraganization_participation].act_uid = [vaccination].intervention_uid
              and [oraganization_participation].act_class_cd = 'INTV'
              and [oraganization_participation].type_cd = 'PerformerOfVacc'
              and [oraganization_participation].subject_class_cd = 'ORG'
      
          left join Organization  with (nolock) on
              [organization].organization_uid = [oraganization_participation].[subject_entity_uid]
      
          left join Participation [provided_by] on
                  [provided_by].act_uid = [vaccination].[intervention_uid]
              and [provided_by].type_cd = 'PerformerOfVacc'
              and [provided_by].subject_class_cd = 'PSN'
      
          left join person_name [provider] on
                              [provider].person_uid = [provided_by].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                      [prefix].[code_set_nm] = 'P_NM_PFX'
                  and [prefix].code = [provider].nm_prefix
      order by
          [vaccination].[local_id] desc
      """;

  private final JdbcClient client;
  private final PatientVaccinationRowMapper mapper;

  PatientFileVaccinationFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientVaccinationRowMapper();
  }

  List<PatientVaccination> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .list();
  }
}
