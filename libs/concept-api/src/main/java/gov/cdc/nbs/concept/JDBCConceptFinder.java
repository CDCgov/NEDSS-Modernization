package gov.cdc.nbs.concept;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;


class JDBCConceptFinder implements ConceptFinder {

  private static final int VALUE_SET_PARAMETER = 1;
  private static final String QUERY = """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm = ?
      order by
          indent_level_nbr
      """;

  private final JdbcTemplate template;
  private final RowMapper<Concept> mapper;

  JDBCConceptFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new ConceptRowMapper();
  }

  @Override
  public Collection<Concept> find(final String valueSet) {
    return this.template.query(
        QUERY,
        statement -> statement.setString(VALUE_SET_PARAMETER, valueSet),
        this.mapper
    );
  }
}
