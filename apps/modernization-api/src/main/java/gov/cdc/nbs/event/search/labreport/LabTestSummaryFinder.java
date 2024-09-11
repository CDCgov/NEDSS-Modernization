package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.patient.documentsrequiringreview.detail.LabTestSummary;
import gov.cdc.nbs.patient.documentsrequiringreview.detail.LabTestSummaryRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class LabTestSummaryFinder {
  private static final String QUERY = """
      select
        lt.lab_test_desc_txt    as name,
        cvg.code_short_desc_txt as status,
        lr.lab_result_desc_txt  as coded_result,
        ovn.numeric_value_1     as numeric_result,
        ovn.high_range          as high,
        ovn.low_range           as low,
        ovn.numeric_unit_cd     as unit
      from
        Observation o1
        join Act_relationship ar on
          ar.target_act_uid = o1.observation_uid
          and ar.type_cd = 'COMP'
          and ar.source_class_cd = 'OBS'
          and ar.target_class_cd = 'OBS'
        join observation o2 on o2.observation_uid = ar.source_act_uid and o2.obs_domain_cd_st_1 = 'Result'
        join NBS_SRTE..Lab_test lt on lt.lab_test_cd = o2.cd
        left join Obs_value_coded ovc on ovc.observation_uid = o2.observation_uid
        left join NBS_SRTE..Lab_result lr on lr.lab_result_cd = ovc.code
        left join Obs_value_numeric ovn on ovn.observation_uid = o2.observation_uid
        left join NBS_SRTE..Code_value_general cvg on cvg.code = o2.status_cd and cvg.code_set_nm = 'ACT_OBJ_ST'
      where
        lt.test_type_cd = 'R'
        and o1.observation_uid = :observationUid
                                              """;

  private final NamedParameterJdbcTemplate namedTemplate;
  private final RowMapper<LabTestSummary> mapper;

  public LabTestSummaryFinder(
      final NamedParameterJdbcTemplate template) {
    this.mapper = new LabTestSummaryRowMapper(new LabTestSummaryRowMapper.Column(
        1,
        2,
        3,
        4,
        5,
        6,
        7));
    this.namedTemplate = template;
  }

  /**
   * @param observationUid
   * @return
   */
  public List<LabTestSummary> find(long observationUid) {
    SqlParameterSource namedParameters = new MapSqlParameterSource(
        Map.of("observationUid", observationUid));

    return namedTemplate.query(QUERY, namedParameters, mapper);
  }

}

