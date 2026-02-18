package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import java.util.Collection;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class PageNameAutocompleteOptionFinder {

  private static final String CRITERIA_PARAMETER = "criteria";
  private static final String LIMIT_PARAMETER = "limit";
  private static final String SQL_WILDCARD = "%";

  private static final String QUERY =
      """
      select
          template_nm
      from WA_Template [page]
      where template_type in ('Draft', 'Published')
      and template_nm like :criteria
      order by
          template_nm
      offset 0 rows
      fetch next :limit rows only
      """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<PageBuilderOption> mapper;

  PageNameAutocompleteOptionFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new PageNameRowMapper();
  }

  Collection<PageBuilderOption> resolve(final String term, final int limit) {
    Map<String, Object> parameters =
        Map.of(CRITERIA_PARAMETER, withWildcard(term), LIMIT_PARAMETER, limit);

    return this.template.query(QUERY, new MapSqlParameterSource(parameters), this.mapper);
  }

  private String withWildcard(final String value) {
    return value + SQL_WILDCARD;
  }
}
