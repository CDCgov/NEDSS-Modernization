package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.testing.support.Available;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

@Component
class RaceConceptMother {

  private static final String DELETE_IN = """
      delete
      from NBS_SRTE.[dbo].Race_code
      where code_set_nm = :codeSet
      """;

  private static final String CREATE = """
      insert into NBS_SRTE.[dbo].Race_code(
        code_set_nm,
        code,
        code_short_desc_txt,
        indent_level_nbr
      )
      values (:set, :code, :concept, :order)
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<ConceptOption> availalbe;
  private static final String RACE_CONCEPT_CODE_SET = "P_RACE_CAT";

  RaceConceptMother(
      final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.availalbe = new Available<>();
  }

  void reset() {

    template.execute(
        DELETE_IN,
        new MapSqlParameterSource("codeSet", RACE_CONCEPT_CODE_SET),
        PreparedStatement::executeUpdate);
    this.availalbe.reset();

  }

  void create(final String concept) {
    String code = UUID.randomUUID().toString()
        .replace("-", "")
        .substring(0, 20);

    int order = this.availalbe.all()
        .map(ConceptOption::order)
        .max(Comparator.naturalOrder())
        .orElse(1);

    Map<String, ? extends Serializable> parameters = Map.of(
        "set", RACE_CONCEPT_CODE_SET,
        "code", code,
        "concept", concept,
        "order", order);

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

    this.availalbe.available(new ConceptOption(code, concept, order));

  }

}
