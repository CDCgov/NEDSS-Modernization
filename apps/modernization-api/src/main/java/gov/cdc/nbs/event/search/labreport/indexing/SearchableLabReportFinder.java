package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class SearchableLabReportFinder {

  private static final String QUERY =
      """
      select
          observation_uid,
          act.class_cd,
          act.mood_cd,
          [lab_report].prog_area_cd,
          [lab_report].jurisdiction_cd,
          [lab_report].program_jurisdiction_oid,
          [lab_report].pregnant_ind_cd,
          [lab_report].local_id,
          [lab_report].activity_to_time,
          [lab_report].effective_from_time,
          [lab_report].[rpt_to_state_time],
          [lab_report].add_user_id,
          [lab_report].add_time,
          [lab_report].last_chg_user_id,
          [lab_report].last_chg_time,
          [lab_report].version_ctrl_nbr,
          [lab_report].record_status_cd,
          [lab_report].electronic_ind
      from Observation [lab_report]

          join act [act] on
                  [act].act_uid = [lab_report].observation_uid

      where   [lab_report].ctrl_cd_display_form = 'LabReport'
          and [lab_report].obs_domain_cd_st_1 = 'Order'
          and [lab_report].observation_uid = ?
      """;
  private static final int IDENTIFIER_COLUMN = 1;
  private static final int CLASS_CODE_COLUMN = 2;
  private static final int MOOD_COLUMN = 3;
  private static final int PROGRAM_AREA_COLUMN = 4;
  private static final int JURISDICTION_COLUMN = 5;
  private static final int OID_COLUMN = 6;
  private static final int PREGNANCY_STATUS_COLUMN = 7;
  private static final int LOCAL_COLUMN = 8;
  private static final int REPORTED_ON_COLUMN = 9;
  private static final int COLLECTED_ON_COLUMN = 10;
  private static final int RECEIVED_ON_COLUMN = 11;
  private static final int CREATED_BY_COLUMN = 12;
  private static final int CREATED_ON_COLUMN = 13;
  private static final int UPDATED_BY_COLUMN = 14;
  private static final int UPDATED_ON_COLUMN = 15;
  private static final int VERSION_COLUMN = 16;
  private static final int STATUS_COLUMN = 17;
  private static final int ELECTRONIC_ENTRY_COLUMN = 18;
  private static final int IDENTIFIER_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchableLabReportRowMapper mapper;

  SearchableLabReportFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new SearchableLabReportRowMapper(
            new SearchableLabReportRowMapper.Column(
                IDENTIFIER_COLUMN,
                CLASS_CODE_COLUMN,
                MOOD_COLUMN,
                PROGRAM_AREA_COLUMN,
                JURISDICTION_COLUMN,
                OID_COLUMN,
                PREGNANCY_STATUS_COLUMN,
                LOCAL_COLUMN,
                REPORTED_ON_COLUMN,
                COLLECTED_ON_COLUMN,
                RECEIVED_ON_COLUMN,
                CREATED_BY_COLUMN,
                CREATED_ON_COLUMN,
                UPDATED_BY_COLUMN,
                UPDATED_ON_COLUMN,
                VERSION_COLUMN,
                STATUS_COLUMN,
                ELECTRONIC_ENTRY_COLUMN));
  }

  Optional<SearchableLabReport> find(final long identifier) {
    return this.template
        .query(QUERY, statement -> statement.setLong(IDENTIFIER_PARAMETER, identifier), this.mapper)
        .stream()
        .findFirst();
  }
}
