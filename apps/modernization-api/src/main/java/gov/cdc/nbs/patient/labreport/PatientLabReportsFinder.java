package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.patient.labreport.PatientLabReport.AssociatedInvestigation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
class PatientLabReportsFinder {
  private String query(boolean isOpen, Collection<Long> oids) {
    return """
        select
            [lab].[observation_uid]                             as [id],
            [lab].[add_time]                                    as [Date Received],
            [reporting_facility].display_nm                     as [reporting_facility],
            [provider].[nm_prefix]                              as [provider_prefix],
            [provider].[first_nm]                               as [provider_first_name],
            [provider].[last_nm]                                as [provider_last_name],
            [provider].[nm_suffix]                              as [provider_suffix],
            [lab].[effective_from_time]                         as [Date Collected],
            [investigation].public_health_case_uid              as [Associated With Id],
            [investigation].local_id                            as [Associated With local],
            [investigation_condition].condition_short_nm        as [Associated With Condition],
            [lab].prog_area_cd                                  as [Program Area],
            [jurisdiction].code_short_desc_txt                  as [Jurisduction],
            [lab].[local_id]                                    as [Event Id],
            [specimen_site].[code_desc_txt]                     as [Specimen Site],
            [specimen_source].[code_desc_txt]                   as [Speciman Source],
          [lab_test].lab_test_desc_txt                        as [Lab Test],
          [lab_result_status].[code_short_desc_txt]           as [Lab Result Status],
          [coded_result].lab_result_desc_txt                  as [coded_result],
          [numeric].numeric_value_1                           as [numeric_result],
          [numeric].high_range                                as [high_range],
          [numeric].low_range                                 as [low_range],
          [numeric].numeric_unit_cd                           as [unit]

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
                    """ + oids.stream().map(String::valueOf).collect(Collectors.joining(",")) +
        """
                      )
            left join Participation [report_provider] with (nolock) on
                    [report_provider].act_uid = [lab].observation_uid
                and [report_provider].type_cd = 'ORD'
                and [report_provider].subject_class_cd = 'PSN'
            left join person_name [provider] with (nolock) on
                                [provider].person_uid = [report_provider].[subject_entity_uid]
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


            left join Act_relationship [lab_result_components] on
                    [lab_result_components].target_act_uid = [lab].observation_uid
                and [lab_result_components].type_cd = 'COMP'
                and [lab_result_components].source_class_cd = 'OBS'
                and [lab_result_components].target_class_cd = 'OBS'

            left join observation [lab_result] on
                    [lab_result].observation_uid = [lab_result_components].[source_act_uid]
                and [lab_result].obs_domain_cd_st_1 = 'Result'

            left join NBS_SRTE..Code_value_general [lab_result_status] on
                    [lab_result_status].[code_set_nm] = 'ACT_OBJ_ST'
                and [lab_result_status].code =  [lab_result].[status_cd]

            left join NBS_SRTE..Lab_test [lab_test] on
                    [lab_test].lab_test_cd = [lab_result].cd

            left join [Obs_value_coded] [coded] on
                    [coded].[observation_uid] = [lab_result].[observation_uid]

            left join NBS_SRTE..Lab_result [coded_result] on
                    [coded_result].[lab_result_cd] = [coded].code

            left join [Obs_value_numeric] [numeric] on
                    [numeric].[observation_uid] = [lab_result].[observation_uid]

              where   [lab].[ctrl_cd_display_form] = 'LabReport'
              and [subject].person_parent_uid = ?
                            """;
  }

  private static final int PERSON_UID_PARAMETER = 1;

  private static final int INVESTIGATION_ID_COLUMN = 1;
  private static final int RECEIVE_DATE_COLUMN = 2;
  private static final int FACILITY_NAME_COLUMN = 3;
  private static final int PROVIDER_PREFIX_COLUMN = 4;
  private static final int PROVIDER_FIRST_NAME_COLUMN = 5;
  private static final int PROVIDER_LAST_NAME_COLUMN = 6;
  private static final int PROVIDER_SUFFIX_COLUMN = 7;
  private static final int DATE_COLLECTED_COLUMN = 8;
  private static final int ASSOCIATED_WITH_ID_COLUMN = 9;
  private static final int ASSOCIATED_WITH_LOCAL_COLUMN = 10;
  private static final int ASSOCIATED_WITH_CONDITION_COLUMN = 11;
  private static final int PROGRAM_AREA_COLUMN = 12;
  private static final int JURISDICTION_COLUMN = 13;
  private static final int EVENT_ID_COLUMN = 14;
  private static final int SPECIMEN_SITE_COLUMN = 15;
  private static final int SPECIMEN_SOURCE_COLUMN = 16;
  private static final int LAB_TEST_COLUMN = 17;
  private static final int LAB_RESULT_STATUS_COLUMN = 18;
  private static final int CODED_RESULT_COLUMN = 19;
  private static final int NUMERIC_RESULT_COLUMN = 20;
  private static final int HIGH_RANGE_COLUMN = 21;
  private static final int LOW_RANGE_COLUMN = 22;

  private final JdbcTemplate template;
  private final PatientLabReportsRowMapper mapper;

  PatientLabReportsFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientLabReportsRowMapper(
        new PatientLabReportsRowMapper.Column(
            INVESTIGATION_ID_COLUMN,
            RECEIVE_DATE_COLUMN,
            FACILITY_NAME_COLUMN,
            PROVIDER_PREFIX_COLUMN,
            PROVIDER_FIRST_NAME_COLUMN,
            PROVIDER_LAST_NAME_COLUMN,
            PROVIDER_SUFFIX_COLUMN,
            DATE_COLLECTED_COLUMN,
            ASSOCIATED_WITH_ID_COLUMN,
            ASSOCIATED_WITH_LOCAL_COLUMN,
            ASSOCIATED_WITH_CONDITION_COLUMN,
            PROGRAM_AREA_COLUMN,
            JURISDICTION_COLUMN,
            EVENT_ID_COLUMN,
            SPECIMEN_SITE_COLUMN,
            SPECIMEN_SOURCE_COLUMN,
            LAB_TEST_COLUMN,
            LAB_RESULT_STATUS_COLUMN,
            CODED_RESULT_COLUMN,
            NUMERIC_RESULT_COLUMN,
            HIGH_RANGE_COLUMN,
            LOW_RANGE_COLUMN));
  }

  List<PatientLabReport> find(final long personUid, Collection<Long> oids) {
    System.out.println("XXX oids:" + oids.toString());
    if (oids.isEmpty()) {
      return new ArrayList<PatientLabReport>();
    }
    return this.template.query(
        query(false, oids),
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }
}
