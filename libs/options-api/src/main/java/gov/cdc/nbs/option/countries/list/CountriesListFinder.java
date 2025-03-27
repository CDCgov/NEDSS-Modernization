package gov.cdc.nbs.option.countries.list;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.Collection;
import gov.cdc.nbs.option.Option;

@Component
public class CountriesListFinder {

  private static final String QUERY = """
      select
          code                as [value],
          code_desc_txt       as [name]
      from NBS_SRTE..Country_code
      order by
          indent_level_nbr,
          code_short_desc_txt
      """;

  private final JdbcTemplate template;
  private final RowMapper<Option> mapper;

  CountriesListFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new CountriesListRowMapper();
  }

  public Collection<Option> find() {
    return this.template.query(
        QUERY,
        statement -> {
        },
        this.mapper);
  }
}
