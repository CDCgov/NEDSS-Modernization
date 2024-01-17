package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class CaseReportDetailFinder {

  private static final String QUERY = """
      select
          [document].nbs_document_uid             as [id],
          [document].add_time                     as [date_received],
          [document_type].code_short_desc_txt     as [document_type],
          [document].sending_facility_nm          as [sending_facility],
          [document].add_time                     as [date_reported],
          [condition].condition_short_nm          as [condition],
          [document].local_id                     as [event_id],
          case
              when external_version_ctrl_nbr > 1
                  then 1
              else 0
          end                                     as [updated],
          [investigation].public_health_case_uid  as [investigation_id],
          [investigation].local_id                as [investigation_local]
      from nbs_document [document] with (nolock)

          join NBS_SRTE..Code_value_general [document_type] on
                  [document_type].[code_set_nm] = 'PUBLIC_HEALTH_EVENT'
              and [document_type].code = [document].doc_type_cd

          join nbs_srte..Condition_code [condition] on
                          [condition].condition_cd = [document].cd

          left join Act_relationship [relationship] on
                  [relationship].target_class_cd = 'CASE'
              and [relationship].[source_class_cd] = 'DOC'
              and [relationship].[source_act_uid] = [document].[nbs_document_uid]
              and [relationship].[record_status_cd] = 'ACTIVE'

          left join Public_health_case [investigation] on
                  [investigation].public_health_case_uid = [relationship].target_act_uid
              and [investigation].record_status_cd <> 'LOG_DEL'
            
      where   [document].nbs_document_uid in (:identifiers)
      """;

  private final NamedParameterJdbcTemplate template;

  CaseReportDetailFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
  }

}
