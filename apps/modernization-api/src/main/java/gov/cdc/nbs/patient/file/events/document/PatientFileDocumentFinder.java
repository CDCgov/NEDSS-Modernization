package gov.cdc.nbs.patient.file.events.document;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientFileDocumentFinder {

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
          [revisions].mpr_id                      as [patient],
          [document].nbs_document_uid             as [id],
          [document].local_id                     as [local],
          [document].add_time                     as [date_received],
          [document].sending_facility_nm          as [sending_facility],
          [document].add_time                     as [date_reported],
          [condition].condition_short_nm          as [condition],
          case
              when external_version_ctrl_nbr > 1
                  then 1
              else 0
          end                                     as [updated]
      from revisions
      
          join participation [subject_of_document] with (nolock) on
                  [subject_of_document].subject_entity_uid = [revisions].person_uid
              AND [subject_of_document].type_cd='SubjOfDoc'
              AND [subject_of_document].act_class_cd = 'DOC'
              AND [subject_of_document].subject_class_cd = 'PSN'
              AND [subject_of_document].record_status_cd = 'ACTIVE'
      
          join nbs_document [document] with (nolock) on
                  [document].nbs_document_uid = [subject_of_document].act_uid
              and [document].record_status_cd != 'LOG_DEL'
              and [document].program_jurisdiction_oid in (:any)
      
          left join nbs_srte..Condition_code [condition] with (nolock) on
                   [condition].condition_cd = [document].cd
      order by
          [document].local_id desc
      """;

  private final JdbcClient client;
  private final RowMapper<PatientFileDocument> mapper;

  PatientFileDocumentFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientFileDocumentRowMapper();
  }

  List<PatientFileDocument> find(final long patient, final PermissionScope scope) {
    return this.client.sql(QUERY)
        .param("patient", patient)
        .param("any", scope.any())
        .query(this.mapper)
        .list();
  }
}
