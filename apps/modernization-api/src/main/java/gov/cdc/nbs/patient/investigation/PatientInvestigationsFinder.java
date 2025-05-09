package gov.cdc.nbs.patient.investigation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
class PatientInvestigationsFinder {
  private String query(boolean isOpen, Collection<Long> oids) {
    return """
        select distinct
            [investigation].[local_id]                  as [investigation_id],
            [investigation].[public_health_case_uid]    as [identifier],
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
                    and [investigation].program_jurisdiction_oid in (
                    """ +
        oids.stream().map(String::valueOf).collect(Collectors.joining(","))
        + " ) " +
        (isOpen ? " and [investigation].investigation_status_cd='O'" : "")
        + """

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
                left join [Person] [investigator] on
                        [investigator].[person_uid] = [investigated_by].subject_entity_uid
            where  [patient].person_parent_uid = ?
            ORDER BY investigation_id DESC
              """;
  }

  private static final int PERSON_UID_PARAMETER = 1;

  private static final int INVESTIGATION_ID_COLUMN = 1;
  private static final int IDENTIFIER_COLUMN = 2;
  private static final int START_DATE_COLUMN = 3;
  private static final int STATUS_COLUMN = 4;
  private static final int CONDITION_COLUMN = 5;
  private static final int CASE_STATUS_COLUMN = 6;
  private static final int NOTIFICATION_COLUMN = 7;
  private static final int JURISDICTION_COLUMN = 8;
  private static final int COINFECTION_COLUMN = 9;
  private static final int INVESTIGATOR_FIRST_NAME_COLUMN = 10;
  private static final int INVESTIGATOR_LAST_NAME_COLUMN = 11;
  private static final int INVESTIGATION_FORM_CODE_COLUMN = 12;

  private final JdbcTemplate template;
  private final PatientInvestigationsRowMapper mapper;

  PatientInvestigationsFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientInvestigationsRowMapper(
        new PatientInvestigationsRowMapper.Column(
            INVESTIGATION_ID_COLUMN,
            IDENTIFIER_COLUMN,
            START_DATE_COLUMN,
            STATUS_COLUMN,
            CONDITION_COLUMN,
            CASE_STATUS_COLUMN,
            NOTIFICATION_COLUMN,
            JURISDICTION_COLUMN,
            COINFECTION_COLUMN,
            INVESTIGATOR_FIRST_NAME_COLUMN,
            INVESTIGATOR_LAST_NAME_COLUMN,
            INVESTIGATION_FORM_CODE_COLUMN));
  }

  List<PatientInvestigation> findAll(final long personUid, Collection<Long> oids) {
    return this.template.query(
        query(false, oids),
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }

  List<PatientInvestigation> findOpen(final long personUid, Collection<Long> oids) {
    return this.template.query(
        query(true, oids),
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }
}

