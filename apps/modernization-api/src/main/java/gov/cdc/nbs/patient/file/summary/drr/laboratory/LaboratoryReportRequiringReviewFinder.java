package gov.cdc.nbs.patient.file.summary.drr.laboratory;

import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import gov.cdc.nbs.patient.file.summary.drr.DocumentsRequiringReviewCriteria;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class LaboratoryReportRequiringReviewFinder {

  public static final String PATIENT_PARAMETER = "patient";
  public static final String ANY_PARAMETER = "any";

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
          [revisions].mpr_id                  as [patient],
          [lab].[observation_uid]             as [id],
          [lab].[local_id]                    as [local],
          [lab].[rpt_to_state_time]           as [received_on],
          [lab].effective_from_time           as [collected_on],
          case [lab].electronic_ind
              when 'Y' then 1
              else 0
          end                                 as [electronic],
          [reporting_facility].display_nm     as [reporting_facility],
          [ordering_facility].display_nm      as [ordering_facility],
          [prefix].code_short_desc_txt        as [ordering_provider_prefix],
          [ordering_provider].[first_nm]      as [ordering_provider_first_name],
          [ordering_provider].[last_nm]       as [ordering_provider_last_name]
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
              and [lab].record_status_cd = 'UNPROCESSED'
              and [lab].program_jurisdiction_oid in (:any)
      
          join Participation [reporting_facility_participation] with (nolock) on
                  [reporting_facility_participation].act_uid = [lab].observation_uid
              and [reporting_facility_participation].type_cd = 'AUT'
              and [reporting_facility_participation].subject_class_cd = 'ORG'
      
          join Organization [reporting_facility] with (nolock) on
                  [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
      
          left join Participation [ordering_facility_participation] with (nolock) on
                  [ordering_facility_participation].act_uid = [lab].observation_uid
              and [ordering_facility_participation].type_cd = 'ORD'
              and [ordering_facility_participation].subject_class_cd = 'ORG'
      
          left join Organization [ordering_facility] with (nolock) on
                  [ordering_facility].organization_uid = [ordering_facility_participation].[subject_entity_uid]
      
          left join Participation [ordering_provider_participation] with (nolock) on
                  [ordering_provider_participation].act_uid = [lab].observation_uid
              and [ordering_provider_participation].type_cd = 'ORD'
              and [ordering_provider_participation].subject_class_cd = 'PSN'
      
          left join person_name [ordering_provider] with (nolock) on
                  [ordering_provider].person_uid = [ordering_provider_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                  [prefix].[code_set_nm] = 'P_NM_PFX'
              and [prefix].code = [ordering_provider].nm_prefix
      """;

  private final JdbcClient client;
  private final RowMapper<DocumentRequiringReview> mapper;

  LaboratoryReportRequiringReviewFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new LaboratoryReportRequiringReviewRowMapper();
  }

  List<DocumentRequiringReview> find(final DocumentsRequiringReviewCriteria criteria) {
    return this.client.sql(QUERY)
        .param(PATIENT_PARAMETER, criteria.patient())
        .param(ANY_PARAMETER, criteria.labReportScope().any())
        .query(this.mapper)
        .list();
  }
}
