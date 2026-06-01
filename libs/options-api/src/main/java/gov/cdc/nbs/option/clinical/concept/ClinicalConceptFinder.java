package gov.cdc.nbs.option.clinical.concept;

import gov.cdc.nbs.option.concept.ConceptOption;
import gov.cdc.nbs.option.concept.ConceptOptionRowMapper;
import java.util.Collection;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class ClinicalConceptFinder {

  private static final String QUERY =
      """
      select
        code                as [value],
        code_short_desc_txt as [name],
        order_number        as [order]
      from NBS_SRTE..Code_value_clinical
      where code_set_nm = :name
      order by
          order_number
      """;

  private final JdbcClient client;
  private final RowMapper<ConceptOption> mapper;

  ClinicalConceptFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new ConceptOptionRowMapper();
  }

  Collection<ConceptOption> find(final String valueSet) {
    return this.client.sql(QUERY).param("name", valueSet).query(this.mapper).list();
  }
}
