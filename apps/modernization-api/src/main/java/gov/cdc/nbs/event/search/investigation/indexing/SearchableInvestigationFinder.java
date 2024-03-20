package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class SearchableInvestigationFinder {

  private static final String QUERY = """
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
          [investigation].cd_desc_txt,
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
          [investigation].curr_process_state_cd,
          [investigation].investigation_status_cd,
          [notification].local_id as notification_local_id,
          [notification].add_time as notification_add_time,
          coalesce([notification].record_status_cd,'UNASSIGNED') as notification_record_status_cd
      from Public_health_case [investigation]
      
          join act [act] on
                  [act].act_uid = [investigation].public_health_case_uid
      
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
      
      where [investigation].[public_health_case_uid] = ?
      """;
  private static final int IDENTIFIER_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableInvestigationRowMapper mapper;

  SearchableInvestigationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchableInvestigationRowMapper(
        new SearchableInvestigationRowMapper.Column(
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22,
            23,
            24,
            25,
            26
        )
    );
  }

  Optional<SearchableInvestigation> find(final long identifier) {
    return this.template.query(
            QUERY,
            statement -> statement.setLong(IDENTIFIER_PARAMETER, identifier),
            this.mapper
        ).stream()
        .findFirst();
  }
}
