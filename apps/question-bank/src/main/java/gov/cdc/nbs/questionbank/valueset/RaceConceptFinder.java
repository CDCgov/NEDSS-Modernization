package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.RaceConcept;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RaceConceptFinder {

  private String findRaceConceptQuery = """
      SELECT 
          NBS_SRTE.dbo.Race_code.code,
          NBS_SRTE.dbo.Race_code.code_set_nm,
          NBS_SRTE.dbo.Race_code.code_short_desc_txt,
          NBS_SRTE.dbo.Race_code.code_desc_txt,
          NBS_SRTE.dbo.Race_code.code_system_desc_txt,
          NBS_SRTE.dbo.Race_code.status_cd,
          NBS_SRTE.dbo.Race_code.effective_from_time,
          NBS_SRTE.dbo.Race_code.effective_to_time
          
      FROM NBS_SRTE.dbo.Race_code 
      WHERE NBS_SRTE.dbo.Race_code.code_set_nm =?
          ORDER BY code_short_desc_txt
          """;

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<RaceConcept> mapper;
  private static final int CODE_SET_NAME = 1;

  RaceConceptFinder(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = new RaceConceptMapper();
  }

  public List<RaceConcept> findRaceConceptCodes(String codeSetName) {
    return this.jdbcTemplate.query(
        findRaceConceptQuery,
        setter -> setter.setString(CODE_SET_NAME, codeSetName),
        mapper);
  }


}
