package gov.cdc.nbs.patient.file.events.investigation;

import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientInvestigationsFinder {

  private static final String QUERY = """
      select
          [patient].person_parent_uid                 as [patient], 
          [investigation].[public_health_case_uid]    as [identifier],
          [investigation].[local_id]                  as [local],
          [investigation].[activity_from_time]        as [start_date],
          [condition].condition_short_nm              as [condition],
          [status].[code_short_desc_txt]              as [status],
          [case_status].[code_short_desc_txt]         as [case_status],
          [jurisdiction].[code_short_desc_txt]        as [jurisdiction],
          [investigation].[coinfection_id]            as [co-infection],
          [notification_status].[code_short_desc_txt] as [notification],
          [investigator].first_nm                     as [investigator_first_name],
          [investigator].last_nm                      as [investigator_last_name],
          [condition].investigation_form_cd           as [investigation_form]
      from person [patient]
          
          join participation [investigated] on
                  [investigated].record_status_cd = 'ACTIVE'
              and [investigated].[type_cd] = 'SubjOfPHC'
              and [investigated].[subject_class_cd] = 'PSN'
              and [investigated].[subject_entity_uid] = [patient].person_uid
              and [investigated].[act_class_cd] = 'CASE'
          
          join Public_health_case [investigation] on
                  [investigation].public_health_case_uid = [investigated].act_uid
              and [investigation].record_status_cd <> 'LOG_DEL'
              and [investigation].program_jurisdiction_oid in (:any)
              and [investigation].investigation_status_cd = coalesce(:status, [investigation].investigation_status_cd)
      
          left join NBS_SRTE..Code_value_general [status] on
                    [status].[code_set_nm]  = 'PHC_IN_STS'
                and [status].[code]         = [investigation].[investigation_status_cd]
          join nbs_srte..Condition_code [condition] on
                            [condition].condition_cd = [investigation].cd
          join nbs_srte..Jurisdiction_code [jurisdiction] on
                        [jurisdiction].code = [investigation].[jurisdiction_cd]
          left join nbs_srte..Code_value_general [case_status] on
                        [case_status].[code_set_nm] = 'PHC_CLASS'
                    and [case_status].[code] = [investigation].[case_class_cd]
      
          left join Act_relationship [notified] on
                    [notified].[target_act_uid] = [investigation].[public_health_case_uid]
                and [notified].type_cd = 'Notification'
                and [notified].[target_class_cd] = 'CASE'
                and [notified].[source_class_cd] = 'NOTF'
                and [notified].record_status_cd = 'ACTIVE'
      
          left join [Notification] [notification] on
                    [notification].[notification_uid] = [notified].[source_act_uid]
      
          left join NBS_SRTE..Code_value_general [notification_status] on
                    [notification_status].[code_set_nm] = 'REC_STAT'
                and [notification_status].[code]        = [notification].[record_status_cd]
          
          left join Participation [investigated_by] on
                    [investigated_by].[type_cd] = 'InvestgrOfPHC'
                and [investigated_by].[act_uid] = [investigation].[public_health_case_uid]
                and [investigated_by].[act_class_cd] = 'CASE'
                and [investigated_by].[subject_class_cd] = 'PSN'
      
          left join [Person_name] [investigator] on
                    [investigator].[person_uid] = [investigated_by].subject_entity_uid
      
      where  [patient].person_parent_uid = :patient
      order by
          [investigation].[local_id] desc
      """;

  private static final String OPEN_STATUS = "O";

  private static final int PATIENT_COLUMN = 1;
  private static final int IDENTIFIER_COLUMN = 2;
  private static final int INVESTIGATION_ID_COLUMN = 3;
  private static final int START_DATE_COLUMN = 4;
  private static final int STATUS_COLUMN = 5;
  private static final int CONDITION_COLUMN = 6;
  private static final int CASE_STATUS_COLUMN = 7;
  private static final int NOTIFICATION_COLUMN = 8;
  private static final int JURISDICTION_COLUMN = 9;
  private static final int COINFECTION_COLUMN = 10;
  private static final int INVESTIGATOR_FIRST_NAME_COLUMN = 11;
  private static final int INVESTIGATOR_LAST_NAME_COLUMN = 12;
  private static final int INVESTIGATION_FORM_CODE_COLUMN = 13;

  private final JdbcClient client;
  private final PatientInvestigationsRowMapper mapper;

  PatientInvestigationsFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientInvestigationsRowMapper(
        new PatientInvestigationsRowMapper.Column(
            IDENTIFIER_COLUMN,
            INVESTIGATION_ID_COLUMN,
            START_DATE_COLUMN,
            STATUS_COLUMN,
            CONDITION_COLUMN,
            CASE_STATUS_COLUMN,
            NOTIFICATION_COLUMN,
            JURISDICTION_COLUMN,
            COINFECTION_COLUMN,
            new DisplayableSimpleNameRowMapper.Columns(
                INVESTIGATOR_FIRST_NAME_COLUMN,
                INVESTIGATOR_LAST_NAME_COLUMN
            ),
            INVESTIGATION_FORM_CODE_COLUMN
        )
    );
  }

  List<PatientInvestigation> findAll(final long patient, final PermissionScope scope) {
    return this.client.sql(QUERY)
        .param("patient", patient)
        .param("any", scope.any())
        .param("status", null)
        .query(this.mapper)
        .list();
  }

  List<PatientInvestigation> findOpen(final long patient, final PermissionScope scope) {
    return this.client.sql(QUERY)
        .param("patient", patient)
        .param("any", scope.any())
        .param("status", OPEN_STATUS)
        .query(this.mapper)
        .list();
  }
}

