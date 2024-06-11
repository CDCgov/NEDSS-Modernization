package gov.cdc.nbs.option.jdbc.autocomplete;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.option.jdbc.OptionRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SQLBasedOptionResolver {
  private static List<String> relatedClassCodes = Arrays.asList(
      "ABXBACT",
      "BC",
      "CELLMARK",
      "CHAL",
      "CHALSKIN",
      "CHEM",
      "COAG",
      "CYTO",
      "DRUG",
      "DRUG/TOX",
      "HEM",
      "HEM/BC",
      "MICRO",
      "MISC",
      "PANEL.ABXBACT",
      "PANEL.BC",
      "PANEL.CHEM",
      "PANEL.MICRO",
      "PANEL.OBS",
      "PANEL.SERO",
      "PANEL.TOX",
      "PANEL.UA",
      "SERO",
      "SPEC",
      "TOX",
      "UA",
      "VACCIN");

  private static final String CRITERIA_PARAMETER = "criteria";
  private static final String PREFIX_CRITERIA_PARAMETER = "prefixCriteria";
  private static final String LIMIT_PARAMETER = "limit";
  private static final String SQL_WILDCARD = "%";
  private static final String QUICK_CODE = "quickCode";
  private static final String RELATED_CLASS_CODES = "relatedClassCodes";

  private final String query;
  private final NamedParameterJdbcTemplate template;
  private final RowMapper<Option> mapper;

  public SQLBasedOptionResolver(final String query, final NamedParameterJdbcTemplate template) {
    this.query = query;
    this.template = template;
    this.mapper = new OptionRowMapper();
  }


  public Collection<Option> resolve(final String keyword, final int limit) {
    Map<String, Object> parameters = Map.of(
        CRITERIA_PARAMETER, withWildcard(keyword),
        PREFIX_CRITERIA_PARAMETER, withPrefixWildcard(keyword),
        QUICK_CODE, keyword,
        RELATED_CLASS_CODES, relatedClassCodes,
        LIMIT_PARAMETER, limit);
    return this.template.query(
        this.query,
        new MapSqlParameterSource(parameters),
        this.mapper);
  }

  private String withWildcard(final String value) {
    return value + SQL_WILDCARD;
  }

  private String withPrefixWildcard(final String value) {
    return SQL_WILDCARD + ' ' + value + SQL_WILDCARD;
  }
}
