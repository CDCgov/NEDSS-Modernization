package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.patient.documentsrequiringreview.detail.LabTestSummary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        NBS_SRTE..Lab_test lt
        join observation o on lt.lab_test_cd = o.cd and o.obs_domain_cd_st_1 = 'Result'
        join Obs_value_coded ovc on ovc.observation_uid = o.observation_uid
        join NBS_SRTE..Lab_result lr on lr.lab_result_cd = ovc.code
        join Obs_value_numeric ovn on ovn.observation_uid = o.observation_uid
        left join NBS_SRTE..Code_value_general cvg on cvg.code = o.status_cd and cvg.code_set_nm = 'ACT_OBJ_ST'
      where
        lt.test_type_cd = 'R'
        and o.observation_uid = :observationUid
                                              """;

  private final NamedParameterJdbcTemplate namedTemplate;

  public LabTestSummaryFinder(
      final NamedParameterJdbcTemplate template) {
    this.namedTemplate = template;

  }

  /**
   * @param observationUid
   * @return
   */
  public List<LabTestSummary> find(long observationUid) {
    SqlParameterSource namedParameters = new MapSqlParameterSource(
        Map.of("observationUid", observationUid));

    return namedTemplate.query(QUERY, namedParameters, new RowMapper<LabTestSummary>() {

      @Override
      public LabTestSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
        // return new LabTestSummary("name", "status", "coded", new BigDecimal("1.0"), "high", "low", "unit");
        return new LabTestSummary(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4),
            rs.getString(5), rs.getString(6),
            rs.getString(7));
      }
    });
  }

}

