package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PatientTestResultsFinder {
  private static final String QUERY = """
         select
         [lab_result].observation_uid,
                   [lab_test].lab_test_desc_txt                        as [Lab Test],
          [lab_result_status].[code_short_desc_txt]           as [Lab Result Status],
          [coded_result].lab_result_desc_txt                  as [coded_result],
          [numeric].numeric_value_1                           as [numeric_result],
          [numeric].high_range                                as [high_range],
          [numeric].low_range                                 as [low_range],
          [numeric].numeric_unit_cd                           as [unit]
      from  Act_relationship [lab_result_components]
          join observation [lab_result] on
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
      where  [lab_result_components].type_cd = 'COMP'
              and [lab_result_components].source_class_cd = 'OBS'
              and [lab_result_components].target_class_cd = 'OBS'
            """;

  private static final int OBSERVATION_ID_COLUMN = 1;
  private static final int STATUS_COLUMN = 2;
  private static final int CODED_RESULT_COLUMN = 3;
  private static final int DECISION_COLUMN = 4;
  private static final int CONDITION_COLUMN = 5;
  private static final int CASE_STATUS_COLUMN = 6;

  private final JdbcTemplate template;
  private final PatientTestResultsRowMapper mapper;

  PatientTestResultsFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientTestResultsRowMapper(
        new PatientTestResultsRowMapper.Column(
            OBSERVATION_ID_COLUMN,
            STATUS_COLUMN,
            CODED_RESULT_COLUMN,
            DECISION_COLUMN,
            CONDITION_COLUMN,
            CASE_STATUS_COLUMN));
  }

  List<PatientLabReport.TestResult> find() {
    return this.template.query(
        QUERY,
        this.mapper);
  }
}
