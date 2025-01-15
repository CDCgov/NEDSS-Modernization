package gov.cdc.nbs.option.race.detailed;

import gov.cdc.nbs.option.concept.ConceptOption;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
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
@ScenarioScope
class DetailedRaceConceptMother {

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
  private final Available<ConceptOption> available;
  private static final String RACE_CONCEPT_CODE_SET = "P_RACE_CAT";

  DetailedRaceConceptMother(
      final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
  }

  @PostConstruct
  void reset() {

    template.execute(
        DELETE_IN,
        new MapSqlParameterSource("codeSet", RACE_CONCEPT_CODE_SET),
        PreparedStatement::executeUpdate);
    this.available.reset();

  }

  void create(final String concept) {
    String code = UUID.randomUUID().toString()
        .replace("-", "")
        .substring(0, 20);

    int order = this.available.all()
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

    this.available.available(new ConceptOption(code, concept, order));

  }

}
