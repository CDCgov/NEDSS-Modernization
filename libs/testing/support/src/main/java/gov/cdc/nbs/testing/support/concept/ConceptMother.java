package gov.cdc.nbs.testing.support.concept;

import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class ConceptMother {

  private static final String CREATE =
      """
      insert into NBS_SRTE.dbo.Code_value_general (
        code_set_nm,
        code,
        code_short_desc_txt
      ) values (
        :set,
        :code,
        :name
      );
      """;

  private static final String DELETE =
      """
      delete from NBS_SRTE.dbo.Code_value_general
      where code in (:identifiers)
      """;

  private final NamedParameterJdbcTemplate template;
  private final List<String> created;

  ConceptMother(final NamedParameterJdbcTemplate template) {
    this.template = template;
    this.created = new ArrayList<>();
  }

  @PostConstruct
  void reset() {
    if (!created.isEmpty()) {

      template.execute(DELETE, Map.of("identifiers", created), PreparedStatement::executeUpdate);
    }
  }

  public void create(final String set, final String name) {

    String code = "TEST_" + this.created.size();

    SqlParameterSource parameters =
        new MapSqlParameterSource(
            Map.of(
                "set", set,
                "code", code,
                "name", name));

    template.execute(CREATE, parameters, PreparedStatement::executeUpdate);

    this.created.add(code);
  }
}
