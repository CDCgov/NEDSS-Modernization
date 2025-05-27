package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PatientTestResultsFinder {
  private static final String QUERY = """
      select
          o1.observation_uid                  as observation_uid,
          o2.observation_uid                  as event_id,
          lt.lab_test_desc_txt                as lab_test,
          lr.lab_result_desc_txt              as coded_result,
          ovn.numeric_value_1                 as numeric_result,
          ovn.high_range                      as high_range,
          ovn.low_range                       as low_range,
          ovn.numeric_unit_cd                 as unit,
          cvg.code_short_desc_txt             as lab_result_status
      from Observation o1 with (nolock)
          join Participation par with (nolock) on
                  par.type_cd='PATSBJ'
              and par.act_class_cd = 'OBS'
              and par.subject_class_cd = 'PSN'
              and par.record_status_cd = 'ACTIVE'
              and par.act_uid = o1.observation_uid
          join person with (nolock) on
              par.subject_entity_uid=person.person_uid
              and person.person_parent_uid=?
          join Act_relationship ar with (nolock) on
             ar.target_act_uid = o1.observation_uid
             and ar.type_cd = 'COMP'
             and ar.source_class_cd = 'OBS'
             and ar.target_class_cd = 'OBS'
          join observation o2 with (nolock) on
              o2.observation_uid = ar.source_act_uid
              and o2.obs_domain_cd_st_1 = 'Result'
          left join NBS_SRTE..Code_value_general cvg on
              cvg.code_set_nm = 'ACT_OBJ_ST'
              and cvg.code =  o2.status_cd
          left join NBS_SRTE..Lab_test lt on
              lt.lab_test_cd = o2.cd
          left join Obs_value_coded ovc on
              ovc.observation_uid = o2.observation_uid
          left join NBS_SRTE..Lab_result lr on
              lr.lab_result_cd = ovc.code
          left join Obs_value_numeric ovn on
              ovn.observation_uid = o2.observation_uid
            """;

  private static final int PERSON_UID_PARAMETER = 1;

  private static final int OBSERVATION_ID_COLUMN = 1;
  private static final int EVENT_ID_COLUMN = 2;
  private static final int LAB_TEST_COLUMN = 3;
  private static final int CODED_RESULT_COLUMN = 4;
  private static final int NUMERIC_RESULT_COLUMN = 5;
  private static final int HIGH_RANGE_COLUMN = 6;
  private static final int LOW_RANGE_COLUMN = 7;
  private static final int UNIT_COLUMN = 8;
  private static final int STATUS_COLUMN = 9;

  private final JdbcTemplate template;
  private final PatientTestResultsRowMapper mapper;

  PatientTestResultsFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientTestResultsRowMapper(
        new PatientTestResultsRowMapper.Column(
            OBSERVATION_ID_COLUMN,
            EVENT_ID_COLUMN,
            LAB_TEST_COLUMN,
            CODED_RESULT_COLUMN,
            NUMERIC_RESULT_COLUMN,
            HIGH_RANGE_COLUMN,
            LOW_RANGE_COLUMN,
            UNIT_COLUMN,
            STATUS_COLUMN));
  }

  List<PatientLabReport.TestResult> find(long personUid) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }
}
