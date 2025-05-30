package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import gov.cdc.nbs.patient.file.summary.drr.DocumentsRequiringReviewCriteria;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class MorbidityReportRequiringReviewFinder {

  public static final String PATIENT_PARAMETER = "patient";
  public static final String ANY_PARAMETER = "any";

  private static final String QUERY = """
      with revisions (person_uid, mpr_id) as (
          select
              [patient].[person_uid],
              [patient].person_parent_uid
          from  Person [patient]
          where   [patient].person_parent_uid = :patient
              and [patient].person_parent_uid <> [patient].person_uid
              and [patient].cd = 'PAT'
              and [patient].record_status_cd = 'ACTIVE'
      )
      select
        [revisions].mpr_id                                  as [patient],
        [morbidity].[observation_uid]                       as [identifier],
        [morbidity].[add_time]                              as [received_on],
        [morbidity].activity_to_time                        as [event_date],
        [reporting_facility].display_nm                     as [reporting_facility],
        [condition].condition_short_nm                      as [condition],
        [morbidity].[local_id]                              as [local],
        case [morbidity].electronic_ind
            when 'Y' then 1
            else 0
        end                                                 as [electronic],
        [prefix].code_short_desc_txt                        as [ordering_provider_prefix],
        [ordering_provider].[first_nm]                      as [ordering_provider_first_name],
        [ordering_provider].[last_nm]                       as [ordering_provider_last_name]
      from revisions
      
          join [Participation] [subject_of_report] with (nolock) on
                  [subject_of_report].type_cd='SubjOfMorbReport'
              and [subject_of_report].act_class_cd = 'OBS'
              and [subject_of_report].subject_class_cd = 'PSN'
              and [subject_of_report].subject_entity_uid = revisions.person_uid
              and [subject_of_report].record_status_cd = 'ACTIVE'
      
          join [Observation] [morbidity] with (nolock) on
                  [morbidity].observation_uid = [subject_of_report].act_uid
              and [morbidity].ctrl_cd_display_form = 'MorbReport'
              and [morbidity].obs_domain_cd_st_1 = 'Order'
              and [morbidity].record_status_cd = 'UNPROCESSED'
              and [morbidity].program_jurisdiction_oid in (:any)
      
          join Participation [reporting_facility_participation] with (nolock) on
                  [reporting_facility_participation].act_uid = [morbidity].observation_uid
              and [reporting_facility_participation].type_cd = 'ReporterOfMorbReport'
              and [reporting_facility_participation].subject_class_cd = 'ORG'
      
          join Organization [reporting_facility] with (nolock) on
                  [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
      
          left join nbs_srte..Condition_code [condition] with (nolock) on
                  [condition].condition_cd = [morbidity].cd
      
          left join Participation [ordering_provider_participation] with (nolock) on
                  [ordering_provider_participation].act_uid = [morbidity].observation_uid
              and [ordering_provider_participation].type_cd = 'PhysicianOfMorb'
              and [ordering_provider_participation].subject_class_cd = 'PSN'
      
          left join person_name [ordering_provider] with (nolock) on
                  [ordering_provider].person_uid = [ordering_provider_participation].[subject_entity_uid]
      
          left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                  [prefix].[code_set_nm] = 'P_NM_PFX'
              and [prefix].code = [ordering_provider].nm_prefix
      """;

  private final JdbcClient client;
  private final RowMapper<DocumentRequiringReview> mapper;

  MorbidityReportRequiringReviewFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new MorbidityReportRequiringReviewRowMapper();
  }

  List<DocumentRequiringReview> find(final DocumentsRequiringReviewCriteria criteria) {
    return this.client.sql(QUERY)
        .param(PATIENT_PARAMETER, criteria.patient())
        .param(ANY_PARAMETER, criteria.morbidityReportScope().any())
        .query(this.mapper)
        .list();
  }
}
