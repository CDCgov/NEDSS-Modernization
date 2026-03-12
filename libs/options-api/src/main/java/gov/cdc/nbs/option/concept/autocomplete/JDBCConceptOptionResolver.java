package gov.cdc.nbs.option.concept.autocomplete;

import gov.cdc.nbs.option.concept.ConceptOption;
import gov.cdc.nbs.option.concept.ConceptOptionRowMapper;
import java.util.Collection;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JDBCConceptOptionResolver implements ConceptOptionResolver {

  private static final String QUERY =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from [NBS_SRTE].[dbo].Code_value_general
      where code_set_nm = :valueSet
        and code_short_desc_txt like :criteria
      order by
          indent_level_nbr
      offset 0 rows
      fetch next :limit rows only
      """;
  private static final String VALUE_SET_PARAMETER = "valueSet";
  private static final String CRITERIA_PARAMETER = "criteria";
  private static final String LIMIT_PARAMETER = "limit";
  private static final String SQL_WILDCARD = "%";

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<ConceptOption> mapper;

  public JDBCConceptOptionResolver(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new ConceptOptionRowMapper();
  }

  @Override
  public Collection<ConceptOption> resolve(
      final String valueSet, final String query, final int limit) {
    Map<String, Object> parameters =
        Map.of(
            VALUE_SET_PARAMETER, valueSet,
            CRITERIA_PARAMETER, withWildcard(query),
            LIMIT_PARAMETER, limit);
    return this.template.query(QUERY, new MapSqlParameterSource(parameters), this.mapper);
  }

  private String withWildcard(final String value) {
    return value + SQL_WILDCARD;
  }
}
