package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
class PatientLabReportsFinder {
  private String query(Collection<Long> oids) {
    return """
               select
                   [lab].[observation_uid]                             as [id],
                   [lab].[rpt_to_state_time]                           as [Date Received],
                   [reporting_facility].display_nm                     as [reporting_facility],
                   [ordering_facility].display_nm                      as [ordering_facility],
                   [prefix].[code_short_desc_txt]                      as [provider_prefix],
                   [provider].[first_nm]                               as [provider_first_name],
                   [provider].[last_nm]                                as [provider_last_name],
                   [lab].[effective_from_time]                         as [Date Collected],
                   [investigation].public_health_case_uid              as [Associated With Id],
                   [investigation].local_id                            as [Associated With local],
                   [investigation_condition].condition_short_nm        as [Associated With Condition],
                   [follow_up_status].code_desc_txt                    as [Asssociated With Status],
                   [lab].prog_area_cd                                  as [Program Area],
                   [jurisdiction].code_short_desc_txt                  as [Jurisduction],
                   [lab].[local_id]                                    as [Event Id],
                   [specimen_site].[code_desc_txt]                     as [Specimen Site],
                   [specimen_source].[code_desc_txt]                   as [Speciman Source]
               from [Observation] [lab]
                   left join NBS_SRTE..Jurisdiction_code [jurisdiction] on
                           [jurisdiction].[code]  = [lab].[jurisdiction_cd]
                   join [Participation] [subject_of_report] on
                           [subject_of_report].type_cd='PATSBJ'
                       and [subject_of_report].act_class_cd = 'OBS'
                       and [subject_of_report].subject_class_cd = 'PSN'
                       and [subject_of_report].record_status_cd = 'ACTIVE'
                       and [subject_of_report].act_uid = [lab].observation_uid
                   join person [subject] with (nolock) on
                           [subject_of_report].subject_entity_uid=[subject].person_uid
                   join Participation [reporting_facility_participation] on
                         [reporting_facility_participation].act_uid = [lab].observation_uid
                     and [reporting_facility_participation].type_cd = 'AUT'
                     and [reporting_facility_participation].subject_class_cd = 'ORG'
                   join Organization [reporting_facility] on
                         [reporting_facility].organization_uid = [reporting_facility_participation].[subject_entity_uid]
                   left join Participation [ordering_facility_participation] with (nolock) on
                         [ordering_facility_participation].act_uid = [lab].observation_uid
                       and [ordering_facility_participation].type_cd = 'ORD'
                       and [ordering_facility_participation].subject_class_cd = 'ORG'
                   left join Organization [ordering_facility] with (nolock) on
                         [ordering_facility].organization_uid = [ordering_facility_participation].[subject_entity_uid]
                   left join act_relationship [case_of_report] with (nolock) on
                           [case_of_report].type_cd='LabReport'
                       and [case_of_report].source_act_uid = [lab].[observation_uid]
                       and [case_of_report].source_class_cd = 'OBS'
                       and [case_of_report].target_class_cd = 'CASE'
                   left join Public_health_case [investigation] with (nolock) on
                           [investigation].Public_health_case_uid = [case_of_report].target_act_uid
                       and [investigation].investigation_status_cd in ( 'O','C')
                       and [investigation].record_status_cd <> 'LOG_DEL'
                       and [investigation].program_jurisdiction_oid in (
               """
           + oids.stream().map(String::valueOf).collect(Collectors.joining(",")) +
           """
                         )
                 left join case_management [case_management] with (nolock) on
                       [case_management].public_health_case_uid = [investigation].public_health_case_uid
                 left join NBS_SRTE..Code_value_general [follow_up_status] with (nolock) on
                       [follow_up_status].code = [case_management].init_foll_up
                   and [follow_up_status].code_set_nm = 'STD_NBS_PROCESSING_DECISION_ALL'
               left join Participation [report_provider] with (nolock) on
                       [report_provider].act_uid = [lab].observation_uid
                   and [report_provider].type_cd = 'ORD'
                   and [report_provider].subject_class_cd = 'PSN'
               left join person_name [provider] with (nolock) on
                                   [provider].person_uid = [report_provider].[subject_entity_uid]
               left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                             [prefix].[code_set_nm] = 'P_NM_PFX'
                         and [prefix].code = [provider].nm_prefix
               left join nbs_srte..Condition_code [investigation_condition] with (nolock) on
                               [Investigation_condition].condition_cd = [investigation].cd
               left join NBS_SRTE..Code_value_general [specimen_site] with (nolock) on
                       [specimen_site].code_set_nm = 'ANATOMIC_SITE'
                   and [specimen_site].code = [lab].target_site_cd
               left join Participation [specimen_source_participation] with (nolock) on
                       [specimen_source_participation].act_uid = [lab].[observation_uid]
                   and [specimen_source_participation].act_class_cd = 'OBS'
                   and [specimen_source_participation].type_cd = 'SPC'
                   and [specimen_source_participation].[record_status_cd] = 'ACTIVE'
               left join Material [specimen_source_material] with (nolock) on
                       [specimen_source_material].material_uid = [specimen_source_participation].[subject_entity_uid]
               left join NBS_SRTE..Code_value_general [specimen_source] with (nolock) on
                       [specimen_source].code = [specimen_source_material].cd
                   and [specimen_source].code_set_nm = 'SPECMN_SRC'
               
                 where   [lab].[ctrl_cd_display_form] = 'LabReport'
                 and [subject].person_parent_uid = ?
               """;
  }

  private static final int PERSON_UID_PARAMETER = 1;

  private static final int INVESTIGATION_ID_COLUMN = 1;
  private static final int RECEIVE_DATE_COLUMN = 2;
  private static final int FACILITY_NAME_COLUMN = 3;
  private static final int ORDERING_NAME_COLUMN = 4;
  private static final int PROVIDER_PREFIX_COLUMN = 5;
  private static final int PROVIDER_FIRST_NAME_COLUMN = 6;
  private static final int PROVIDER_LAST_NAME_COLUMN = 7;
  private static final int DATE_COLLECTED_COLUMN = 8;
  private static final int ASSOCIATED_WITH_ID_COLUMN = 9;
  private static final int ASSOCIATED_WITH_LOCAL_COLUMN = 10;
  private static final int ASSOCIATED_WITH_CONDITION_COLUMN = 11;
  private static final int ASSOCIATED_WITH_STATUS = 12;
  private static final int PROGRAM_AREA_COLUMN = 13;
  private static final int JURISDICTION_COLUMN = 14;
  private static final int EVENT_ID_COLUMN = 15;
  private static final int SPECIMEN_SITE_COLUMN = 16;
  private static final int SPECIMEN_SOURCE_COLUMN = 17;

  private final JdbcTemplate template;
  private final PatientLabReportsRowMapper mapper;

  PatientLabReportsFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientLabReportsRowMapper(
        new PatientLabReportsRowMapper.Column(
            INVESTIGATION_ID_COLUMN,
            RECEIVE_DATE_COLUMN,
            FACILITY_NAME_COLUMN,
            ORDERING_NAME_COLUMN,
            new DisplayableSimpleNameRowMapper.Columns(PROVIDER_PREFIX_COLUMN, PROVIDER_FIRST_NAME_COLUMN,
                PROVIDER_LAST_NAME_COLUMN),
            DATE_COLLECTED_COLUMN,
            ASSOCIATED_WITH_ID_COLUMN,
            ASSOCIATED_WITH_LOCAL_COLUMN,
            ASSOCIATED_WITH_CONDITION_COLUMN,
            ASSOCIATED_WITH_STATUS,
            PROGRAM_AREA_COLUMN,
            JURISDICTION_COLUMN,
            EVENT_ID_COLUMN,
            SPECIMEN_SITE_COLUMN,
            SPECIMEN_SOURCE_COLUMN));
  }

  List<PatientLabReport> find(final long personUid, Collection<Long> oids) {
    if (oids.isEmpty()) {
      return new ArrayList<>();
    }
    return this.template.query(
        query(oids),
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }
}
