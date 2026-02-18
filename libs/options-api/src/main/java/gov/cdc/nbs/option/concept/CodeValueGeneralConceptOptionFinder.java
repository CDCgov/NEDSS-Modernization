package gov.cdc.nbs.option.concept;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

class CodeValueGeneralConceptOptionFinder implements ConceptOptionFinder {

  private static final String ALL =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm = :name
      order by
          indent_level_nbr,
          code_short_desc_txt
      """;

  private static final String EXCLUDING =
      """
      select
          code                as [value],
          code_short_desc_txt as [name],
          indent_level_nbr    as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm = :name
          and code not in (:excluding)
      order by
          indent_level_nbr,
          code_short_desc_txt
      """;

  private final JdbcClient client;
  private final RowMapper<ConceptOption> mapper;

  CodeValueGeneralConceptOptionFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new ConceptOptionRowMapper();
  }

  @Override
  public Collection<ConceptOption> find(final String valueSet, final String... excluding) {
    return excluding.length == 0 ? all(valueSet) : excluding(valueSet, excluding);
  }

  private Collection<ConceptOption> all(final String valueSet) {
    return this.client.sql(ALL).param("name", valueSet).query(this.mapper).list();
  }

  private Collection<ConceptOption> excluding(final String valueSet, final String... excluding) {
    return this.client
        .sql(EXCLUDING)
        .param("name", valueSet)
        .param("excluding", Arrays.asList(excluding))
        .query(this.mapper)
        .list();
  }
}
