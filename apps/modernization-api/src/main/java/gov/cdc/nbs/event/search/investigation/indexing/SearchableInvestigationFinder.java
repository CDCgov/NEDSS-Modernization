package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class SearchableInvestigationFinder {

  private static final String QUERY =
      """
      select
          [investigation].public_health_case_uid,
          act.class_cd,
          act.mood_cd,
          [investigation].prog_area_cd,
          [investigation].jurisdiction_cd,
          [jurisdiction].code_desc_txt    as [jurisdiction_code_desc_txt],
          [investigation].program_jurisdiction_oid,
          coalesce([investigation].case_class_cd,'UNASSIGNED') as case_class_cd,
          [investigation].[case_type_cd],
          [investigation].outbreak_name,
          [condition].condition_short_nm as cd_desc_txt,
          [investigation].cd as condition,
          [investigation].pregnant_ind_cd,
          [investigation].local_id,
          [investigation].add_user_id,
          [investigation].[add_time],
          [investigation].last_chg_user_id,
          [investigation].last_chg_time,
          [investigation].[rpt_form_cmplt_time],
          [investigation].[activity_from_time],
          [investigation].[activity_to_time],
          coalesce([investigation].curr_process_state_cd,'UNASSIGNED') as processing_state,
          [investigation].investigation_status_cd,
          [notification].local_id as notification_local_id,
          [notification].add_time as notification_add_time,
          coalesce([notification].record_status_cd,'UNASSIGNED') as notification_record_status_cd,
          [investigator].last_nm as investigator_last_nm
      from Public_health_case [investigation]

          join act on
                  [act].act_uid = [investigation].public_health_case_uid

          join nbs_srte.dbo.Condition_code [condition] on
                          [condition].condition_cd = [investigation].cd

          join NBS_SRTE.dbo.Jurisdiction_code [jurisdiction] on
                  [jurisdiction].code = [investigation].jurisdiction_cd

          left join Act_relationship [notified] with (nolock) on
                  [notified].[target_act_uid] = [investigation].[public_health_case_uid]
              and [notified].type_cd = 'Notification'
              and [notified].[target_class_cd] = 'CASE'
              and [notified].[source_class_cd] = 'NOTF'
              and [notified].record_status_cd = 'ACTIVE'

          left join [Notification] [notification] with (nolock) on
                  [notification].[notification_uid] = [notified].[source_act_uid]

          LEFT JOIN (
              SELECT MAX(person.last_nm) last_nm, act_uid
              FROM participation p WITH (NOLOCK)
                  JOIN person WITH (NOLOCK) ON person.person_uid = (
                    select person.person_parent_uid
                    from person WITH (NOLOCK)
                    where person.person_uid = p.subject_entity_uid)
              WHERE p.type_cd='InvestgrOfPHC' group by act_uid
          ) investigator ON investigator.act_uid = [investigation].[public_health_case_uid]

      where [investigation].[public_health_case_uid] = ?
      """;
  private static final int IDENTIFIER_PARAMETER = 1;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int CLASS_CODE_COLUMN = 2;
  private static final int MOOD_COLUMN = 3;
  private static final int PROGRAM_AREA_COLUMN = 4;
  private static final int JURISDICTION_COLUMN = 5;
  private static final int JURISDICTION_NAME_COLUMN = 6;
  private static final int OID_COLUMN = 7;
  private static final int CASE_CLASS_COLUMN = 8;
  private static final int CASE_TYPE_COLUMN = 9;
  private static final int OUTBREAK_COLUMN = 10;
  private static final int CONDITION_NAME_COLUMN = 11;
  private static final int CONDITION_COLUMN = 12;
  private static final int PREGNANCY_STATUS_COLUMN = 13;
  private static final int LOCAL_COLUMN = 14;
  private static final int CREATED_BY_COLUMN = 15;
  private static final int CREATED_ON_COLUMN = 16;
  private static final int UPDATED_BY_COLUMN = 17;
  private static final int UPDATED_ON_COLUMN = 18;
  private static final int REPORTED_ON_COLUMN = 19;
  private static final int STARTED_ON_COLUMN = 20;
  private static final int CLOSED_ON_COLUMN = 21;
  private static final int PROCESSING_COLUMN = 22;
  private static final int STATUS_COLUMN = 23;
  private static final int NOTIFICATION_COLUMN = 24;
  private static final int NOTIFIED_ON_COLUMN = 25;
  private static final int NOTIFICATION_STATUS_COLUMN = 26;
  private static final int INVESTIGATOR_LAST_NAME_COLUMN = 27;

  private final JdbcTemplate template;
  private final SearchableInvestigationRowMapper mapper;

  SearchableInvestigationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableInvestigationRowMapper(
            new SearchableInvestigationRowMapper.Column(
                IDENTIFIER_COLUMN,
                CLASS_CODE_COLUMN,
                MOOD_COLUMN,
                PROGRAM_AREA_COLUMN,
                JURISDICTION_COLUMN,
                JURISDICTION_NAME_COLUMN,
                OID_COLUMN,
                CASE_CLASS_COLUMN,
                CASE_TYPE_COLUMN,
                OUTBREAK_COLUMN,
                CONDITION_NAME_COLUMN,
                CONDITION_COLUMN,
                PREGNANCY_STATUS_COLUMN,
                LOCAL_COLUMN,
                CREATED_BY_COLUMN,
                CREATED_ON_COLUMN,
                UPDATED_BY_COLUMN,
                UPDATED_ON_COLUMN,
                REPORTED_ON_COLUMN,
                STARTED_ON_COLUMN,
                CLOSED_ON_COLUMN,
                PROCESSING_COLUMN,
                STATUS_COLUMN,
                NOTIFICATION_COLUMN,
                NOTIFIED_ON_COLUMN,
                NOTIFICATION_STATUS_COLUMN,
                INVESTIGATOR_LAST_NAME_COLUMN));
  }

  Optional<SearchableInvestigation> find(final long identifier) {
    return this.template
        .query(QUERY, statement -> statement.setLong(IDENTIFIER_PARAMETER, identifier), this.mapper)
        .stream()
        .findFirst();
  }
}
