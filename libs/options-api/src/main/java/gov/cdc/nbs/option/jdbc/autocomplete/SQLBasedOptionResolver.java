package gov.cdc.nbs.option.jdbc.autocomplete;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.option.jdbc.OptionRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.util.Collection;
import java.util.Map;

public class SQLBasedOptionResolver {
  private static final String CRITERIA_PARAMETER = "criteria";
  private static final String PREFIX_CRITERIA_PARAMETER = "prefixCriteria";
  private static final String LIMIT_PARAMETER = "limit";
  private static final String SQL_WILDCARD = "%";
  private static final String QUICK_CODE = "quickCode";
  private static final String ROOT = "root";

  private final String query;
  private final NamedParameterJdbcTemplate template;
  private final RowMapper<Option> mapper;

  public SQLBasedOptionResolver(final String query, final NamedParameterJdbcTemplate template) {
    this.query = query;
    this.template = template;
    this.mapper = new OptionRowMapper();
  }

  public Collection<Option> resolve(final String keyword, final String root, final int limit) {
    Map<String, Object> parameters = Map.of(
        CRITERIA_PARAMETER, withWildcard(keyword),
        PREFIX_CRITERIA_PARAMETER, withPrefixWildcard(keyword),
        QUICK_CODE, keyword,
        ROOT, root,
        LIMIT_PARAMETER, limit);
    return this.template.query(
        this.query,
        new MapSqlParameterSource(parameters),
        this.mapper);
  }

  public Collection<Option> resolve(final String keyword, final int limit) {
    return resolve(keyword, null, limit);
  }

  private String withWildcard(final String value) {
    return value + SQL_WILDCARD;
  }

  private String withPrefixWildcard(final String value) {
    return SQL_WILDCARD + ' ' + value + SQL_WILDCARD;
  }
}
