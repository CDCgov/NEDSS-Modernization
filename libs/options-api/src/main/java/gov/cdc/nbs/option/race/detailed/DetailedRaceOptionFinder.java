package gov.cdc.nbs.option.race.detailed;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.option.jdbc.OptionRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
class DetailedRaceOptionFinder {

  private static final String CATEGORY = "category";

  private static final String QUERY = """
      select
          code                                as [value],
          code_short_desc_txt                 as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Race_code
      where parent_is_cd = :category
      order by
        code
      """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<Option> mapper;


  DetailedRaceOptionFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.mapper = new OptionRowMapper();
  }

  Collection<Option> find(final String category) {

    Map<String, Object> parameters = Map.of(CATEGORY, category);

    return this.template.query(
        QUERY,
        new MapSqlParameterSource(parameters),
        this.mapper
    );
  }
}
