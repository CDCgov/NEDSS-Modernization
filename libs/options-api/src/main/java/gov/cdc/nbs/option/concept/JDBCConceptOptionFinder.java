package gov.cdc.nbs.option.concept;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collection;


class JDBCConceptOptionFinder implements ConceptOptionFinder {

  private static final int VALUE_SET_PARAMETER = 1;
  private static final String QUERY = """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm = ?
      order by
          indent_level_nbr,
          code_short_desc_txt
      """;

  private static final String FIND_RACE_CONCEPT_QUERY = """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Race_code
      where code_set_nm = ?
      order by
          indent_level_nbr,
          code
      """;

  private final JdbcTemplate template;
  private final RowMapper<ConceptOption> mapper;
  private static final String RACE_CONCEPT_CODE_SET = "P_RACE_CAT";

  JDBCConceptOptionFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new ConceptOptionRowMapper();
  }

  @Override
  public Collection<ConceptOption> find(final String valueSet) {
    return this.template.query(
        getQuery(valueSet),
        statement -> statement.setString(VALUE_SET_PARAMETER, valueSet),
        this.mapper);
  }

  private String getQuery(String valueSet) {
    return (valueSet.equals(RACE_CONCEPT_CODE_SET)) ? FIND_RACE_CONCEPT_QUERY : QUERY;
  }

}
