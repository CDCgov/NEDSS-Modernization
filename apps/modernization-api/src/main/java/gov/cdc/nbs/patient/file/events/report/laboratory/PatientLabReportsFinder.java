package gov.cdc.nbs.patient.file.events.report.laboratory;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class PatientLabReportsFinder {
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
          [revisions].mpr_id                                  as [patient],
          [lab].[observation_uid]                             as [id],
          [lab].[local_id]                                    as [local],
          [program_area].prog_area_desc_txt                   as [Program Area],
          [jurisdiction].code_short_desc_txt                  as [Jurisduction],
          coalesce(
              [processing_decision].code_short_desc_txt,
              [lab].processing_decision_txt
          )                                                   as [Processing Decision],
          [lab].[rpt_to_state_time]                           as [Date Received],
          case [lab].electronic_ind
                   when 'Y' then 1
                   else 0
          end                                                 as [electronic],
          [reporting_facility].display_nm                     as [reporting_facility],
          [ordering_facility].display_nm                      as [ordering_facility],
          [prefix].[code_short_desc_txt]                      as [provider_prefix],
          [provider].[first_nm]                               as [provider_first_name],
          [provider].[last_nm]                                as [provider_last_name],
          [lab].[effective_from_time]                         as [Date Collected],
          coalesce(
              [specimen_site].[code_desc_txt],
              [lab].target_site_cd
          )                                                   as [Specimen Site],
          coalesce(
              [specimen_source].[code_desc_txt],
              [specimen_source_material].cd_desc_txt
          )                                                   as [Speciman Source]
      from revisions
      
          join [Participation] [subject_of_report] with (nolock) on
                  [subject_of_report].type_cd='PATSBJ'
              and [subject_of_report].act_class_cd = 'OBS'
              and [subject_of_report].record_status_cd = 'ACTIVE'
              and [subject_of_report].subject_entity_uid = [revisions].[person_uid]
              and [subject_of_report].subject_class_cd = 'PSN'
      
          join [Observation] [lab] with (nolock) on
                  [lab].observation_uid = [subject_of_report].act_uid
              and [lab].[ctrl_cd_display_form] = 'LabReport'
              and [lab].obs_domain_cd_st_1 = 'Order'
              and [lab].program_jurisdiction_oid in (:any)
      
          join NBS_SRTE..Program_area_code [program_area] with (nolock) on
                  [program_area].[prog_area_cd] = [lab].prog_area_cd
      
          join NBS_SRTE..Jurisdiction_code [jurisdiction] with (nolock) on
                  [jurisdiction].[code]  = [lab].[jurisdiction_cd]
      
          join Participation [reporting_facility_participation] with (nolock) on
                  [reporting_facility_participation].act_uid = [lab].observation_uid
              and [reporting_facility_participation].type_cd = 'AUT'
              and [reporting_facility_participation].subject_class_cd = 'ORG'
      
          join Organization [reporting_facility] with (nolock) on
              [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [processing_decision] with (nolock) on
                [processing_decision].code_set_nm in ('NBS_NO_ACTION_RSN', 'STD_NBS_PROCESSING_DECISION_ALL')
            and [processing_decision].code = [lab].processing_decision_cd
      
          left join Participation [ordering_facility_participation] with (nolock) on
                  [ordering_facility_participation].act_uid = [lab].observation_uid
              and [ordering_facility_participation].type_cd = 'ORD'
              and [ordering_facility_participation].subject_class_cd = 'ORG'
      
          left join Organization [ordering_facility] with (nolock) on
              [ordering_facility].organization_uid = [ordering_facility_participation].[subject_entity_uid]
      
          left join Participation [report_provider] with (nolock) on
                  [report_provider].act_uid = [lab].observation_uid
              and [report_provider].type_cd = 'ORD'
              and [report_provider].subject_class_cd = 'PSN'
      
          left join person_name [provider] with (nolock) on
                              [provider].person_uid = [report_provider].[subject_entity_uid]
          left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                      [prefix].[code_set_nm] = 'P_NM_PFX'
                  and [prefix].code = [provider].nm_prefix
      
          left join NBS_SRTE..Code_value_general [specimen_site] with (nolock) on
                  [specimen_site].code_set_nm = 'ANATOMIC_SITE'
              and [specimen_site].code = [lab].target_site_cd
      
          left join Participation [specimen_source_participation] with (nolock) on
                  [specimen_source_participation].act_uid = [lab].[observation_uid]
              and [specimen_source_participation].act_class_cd = 'OBS'
              and [specimen_source_participation].type_cd = 'SPC'
              and [specimen_source_participation].subject_class_cd = 'MAT'
              and [specimen_source_participation].[record_status_cd] = 'ACTIVE'
      
          left join Material [specimen_source_material] with (nolock) on
                  [specimen_source_material].material_uid = [specimen_source_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [specimen_source] with (nolock) on
                  [specimen_source].code = [specimen_source_material].cd
              and [specimen_source].code_set_nm = 'SPECMN_SRC'
      order by
          [lab].[local_id] desc
      """;

  private final JdbcClient client;
  private final PatientLabReportsRowMapper mapper;

  PatientLabReportsFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientLabReportsRowMapper();
  }

  List<PatientLabReport> find(final long patient, final PermissionScope scope) {
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
