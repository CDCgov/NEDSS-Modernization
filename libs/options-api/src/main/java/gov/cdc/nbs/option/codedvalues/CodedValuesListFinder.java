package gov.cdc.nbs.option.codedvalues;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.Collection;
import gov.cdc.nbs.option.Option;

@Component
public class CodedValuesListFinder {

  private static final int VALUE_SET_PARAMETER = 1;
  private static final String QUERY = """
      select
          code                as [value],
          code_short_desc_txt as [name]
      from NBS_SRTE..Code_value_general
      where code_set_nm = ?
      order by
          indent_level_nbr,
          code_short_desc_txt
      """;

  private final JdbcTemplate template;
  private final RowMapper<Option> mapper;

  CodedValuesListFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new CodedValuesListRowMapper();
  }

  public Collection<Option> find(final String valueSet) {
    return this.template.query(
        QUERY,
        statement -> statement.setString(VALUE_SET_PARAMETER, valueSet),
        this.mapper);
  }
}
