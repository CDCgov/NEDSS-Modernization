package gov.cdc.nbs.option.general;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.option.concept.ConceptOption;
import gov.cdc.nbs.option.jdbc.OptionRowMapper;
import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;

import java.util.Collection;
import java.util.function.UnaryOperator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GeneralCodedFinder {

  private static final int VALUE_SET_PARAMETER = 1;
  private static final String QUERY = """
      select
          code                                as [value],
          code_short_desc_txt                 as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm = ?
      order by
        code
      """;

  private final JdbcTemplate template;
  private final RowMapper<Option> mapper;

  GeneralCodedFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new OptionRowMapper();
  }

  public Collection<Option> find(final String valueSet) {
    return this.template.query(
        QUERY,
        statement -> statement.setString(VALUE_SET_PARAMETER, valueSet),
        this.mapper);
  }

}
