package gov.cdc.nbs.patient.file.events.report.morbidity;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class PatientMorbidityReportFinder {
  private static final String QUERY = """
      
      with revisions (person_uid, mpr_id) as (
          select
              [patient].[person_uid],
              [patient].person_parent_uid
          from  Person [patient] with (nolock)
          where   [patient].person_parent_uid = :patient
              and [patient].person_parent_uid <> [patient].person_uid
              and [patient].cd = 'PAT'
              and [patient].record_status_cd = 'ACTIVE'
      )
      select
          [revisions].mpr_id                              as [patient],
          [morbidity].[observation_uid]                   as [id],
          [morbidity].[local_id]                          as [local],
          [jurisdiction].code_short_desc_txt              as [jurisduction],
          [morbidity].[add_time]                          as [added_on],
          [morbidity].[rpt_to_state_time]                 as [received_on],
          [morbidity].[activity_to_time]                  as [reported_on],
          [condition].condition_short_nm                  as [condition],
          [reporting_facility].display_nm                 as [reporting_facility],
          [ordering_provider_prefix].code_short_desc_txt  as [ordering_provider_prefix],
          [ordering_provider].[first_nm]                  as [ordering_provider_first_name],
          [ordering_provider].[last_nm]                   as [ordering_provider_last_name],
          [reporting_provider_prefix].code_short_desc_txt as [reporting_provider_prefix],
          [reporting_provider].[first_nm]                 as [reporting_provider_first_name],
          [reporting_provider].[last_nm]                  as [reporting_provider_last_name]
      
      from revisions
      
          join [Participation] [subject_of_morbidity] on
                  [subject_of_morbidity].type_cd='SubjOfMorbReport'
              and [subject_of_morbidity].act_class_cd = 'OBS'
              and [subject_of_morbidity].subject_class_cd = 'PSN'
              and [subject_of_morbidity].subject_entity_uid = revisions.person_uid
              and [subject_of_morbidity].record_status_cd = 'ACTIVE'
      
          join [Observation] [morbidity] with (nolock) on
                  [morbidity].observation_uid = [subject_of_morbidity].act_uid
              and [morbidity].ctrl_cd_display_form = 'MorbReport'
              and [morbidity].obs_domain_cd_st_1 = 'Order'
              and [morbidity].program_jurisdiction_oid in (:any)
      
          join NBS_SRTE..Jurisdiction_code [jurisdiction] with (nolock) on
                  [jurisdiction].[code]  = [morbidity].[jurisdiction_cd]
      
          join nbs_srte..Condition_code [condition] ON
                          [condition].condition_cd = [morbidity].cd
      
          join Participation [reporting_facility_participation] with (nolock) on
                  [reporting_facility_participation].act_uid = [morbidity].observation_uid
              and [reporting_facility_participation].type_cd = 'ReporterOfMorbReport'
              and [reporting_facility_participation].subject_class_cd = 'ORG'
      
          join Organization [reporting_facility] with (nolock) on
                  [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
      
          left join Participation [ordering_provider_participation] with (nolock) on
                  [ordering_provider_participation].act_uid = [morbidity].observation_uid
              and [ordering_provider_participation].type_cd = 'PhysicianOfMorb'
              and [ordering_provider_participation].subject_class_cd = 'PSN'
      
          left join person_name [ordering_provider] with (nolock) on
                  [ordering_provider].person_uid = [ordering_provider_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [ordering_provider_prefix] with (nolock) on
                  [ordering_provider_prefix].[code_set_nm] = 'P_NM_PFX'
              and [ordering_provider_prefix].code = [ordering_provider].nm_prefix
      
          left join Participation [reportying_provider_participation] with (nolock) on
                  [reportying_provider_participation].act_uid = [morbidity].observation_uid
              and [reportying_provider_participation].type_cd = 'ReporterOfMorbReport'
              and [reportying_provider_participation].subject_class_cd = 'PSN'
      
          left join person_name [reporting_provider] with (nolock) on
                  [reporting_provider].person_uid = [reportying_provider_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [reporting_provider_prefix] with (nolock) on
                  [reporting_provider_prefix].[code_set_nm] = 'P_NM_PFX'
              and [reporting_provider_prefix].code = [reporting_provider].nm_prefix
      
          order by
              [morbidity].[local_id] desc
      """;

  private final JdbcClient client;
  private final PatientMorbidityReportRowMapper mapper;

  PatientMorbidityReportFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientMorbidityReportRowMapper();
  }

  List<PatientMorbidityReport> find(final long patient, final PermissionScope scope) {
    if (!scope.allowed()) {
      return Collections.emptyList();
    }
    return this.client.sql(QUERY)
        .param("patient", patient)
        .param("any", scope.any())
        .query(this.mapper)
        .list();
  }
}
