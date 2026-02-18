package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.County;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class CountyFinder {
  private static final String FIND_BY_STATE_CODE =
      """
      select
           [StateCounty].code                        as [code],
           [StateCounty].code_short_desc_txt         as [shortDescription],
           [StateCounty].code_desc_txt               as [longDescription],
           [StateCounty].code_set_nm                 as [codeSetName]
       from NBS_SRTE..State_county_code_value [StateCounty]
       where  [StateCounty].parent_is_cd =:stateCode
       """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<County> mapper;

  CountyFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new CountyMapper();
  }

  List<County> findByStateCode(final String stateCode) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("stateCode", stateCode);
    return this.template.query(FIND_BY_STATE_CODE, parameters, mapper);
  }
}
