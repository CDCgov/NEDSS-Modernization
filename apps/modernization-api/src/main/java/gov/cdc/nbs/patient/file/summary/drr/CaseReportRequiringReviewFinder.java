package gov.cdc.nbs.patient.file.summary.drr;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class CaseReportRequiringReviewFinder {

  private static final String QUERY = """
      with revisions (person_uid) as (
          select
              [patient].[person_uid]
          from  Person [patient]
          where   [patient].person_parent_uid = :patient
          and [patient].cd = 'PAT'
          and [patient].record_status_cd = 'ACTIVE'
      )  
      select
          [document].nbs_document_uid             as [id],
          [document].add_time                     as [date_received],
          [document_type].code_short_desc_txt     as [document_type],
          [document].sending_facility_nm          as [sending_facility],
          [condition].condition_short_nm          as [condition],
          [document].local_id                     as [local],
          case
              when external_version_ctrl_nbr > 1
                  then 1
              else 0
          end                                     as [updated]
      from revisions
          join Participation [participation] with (nolock) on
                  [participation].subject_class_cd = 'PSN'
              and [participation].record_status_cd = 'ACTIVE'
              and [participation].act_class_cd = 'DOC'
              and [participation].type_cd = 'SubjOfDoc'
              and [participation].subject_entity_uid = [revisions].[person_uid]
      
          join nbs_document [document] with (nolock) on
                  [document].nbs_document_uid = [participation].act_uid
              and [document].record_status_cd = 'UNPROCESSED'
              and [document].program_jurisdiction_oid in (:any)
      
          join NBS_SRTE..Code_value_general [document_type] on
                  [document_type].[code_set_nm] = 'PUBLIC_HEALTH_EVENT'
              and [document_type].code = [document].doc_type_cd
      
          join nbs_srte..Condition_code [condition] on
                  [condition].condition_cd = [document].cd
      """;

  public static final String PATIENT_PARAMETER = "patient";
  public static final String ANY_PARAMETER = "any";

  private final JdbcClient client;
  private final RowMapper<DocumentRequiringReview> mapper;

  CaseReportRequiringReviewFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new CaseReportRequiringReviewRowMapper();
  }

  List<DocumentRequiringReview> find(final DocumentsRequiringReviewCriteria criteria) {
    return this.client.sql(QUERY)
        .param(PATIENT_PARAMETER, criteria.patient())
        .param(ANY_PARAMETER, criteria.documentScope().any())
        .query(this.mapper)
        .list();
  }
}
