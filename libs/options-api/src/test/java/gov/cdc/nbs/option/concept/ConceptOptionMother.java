package gov.cdc.nbs.option.concept;

import gov.cdc.nbs.testing.support.Available;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class ConceptOptionMother {

  private static final String DELETE_IN =
      """
      delete
      from NBS_SRTE.[dbo].Code_value_general
      where code in (:codes)
      """;

  private static final String CREATE =
      """
      insert into NBS_SRTE.[dbo].Code_value_general(
        code_set_nm,
        code,
        code_short_desc_txt,
        indent_level_nbr
      )
      values (:set, :code, :concept, :order)
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<ConceptOption> availalbe;

  ConceptOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.availalbe = new Available<>();
  }

  void reset() {

    List<String> codes = this.availalbe.all().map(ConceptOption::value).toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("codes", codes);

      template.execute(
          DELETE_IN, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);
      this.availalbe.reset();
    }
  }

  void create(final String concept, final String set) {

    String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

    int order =
        this.availalbe.all().map(ConceptOption::order).max(Comparator.naturalOrder()).orElse(1);

    Map<String, ? extends Serializable> parameters =
        Map.of(
            "set", set,
            "code", code,
            "concept", concept,
            "order", order);

    template.execute(
        CREATE, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

    this.availalbe.available(new ConceptOption(code, concept, order));
  }
}
