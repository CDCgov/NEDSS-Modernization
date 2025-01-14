package gov.cdc.nbs.option.program.area;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@ScenarioScope
class ProgramAreaMother {

  private static final String DELETE_IN = """
      delete
      from NBS_SRTE.[dbo].Program_area_code
      where prog_area_cd in (:codes)
      """;

  private static final String CREATE = """
      insert into NBS_SRTE.[dbo].Program_area_code(
        nbs_uid,
        prog_area_cd,
        prog_area_desc_txt,
        status_cd
      )
      values (:identifier, :code, :name, 'A')
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;

  ProgramAreaMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
  }

  @PreDestroy
  void reset() {

    List<String> codes = this.available.all()
        .map(Option::value)
        .toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("codes", codes);

      template.execute(
          DELETE_IN,
          new MapSqlParameterSource(parameters),
          PreparedStatement::executeUpdate
      );

      this.available.reset();
    }
  }

  void create(final String name) {

    String code = UUID.randomUUID().toString()
        .replace("-", "")
        .substring(0, 20);

    int identifier = this.available.all()
        .map(Option::order)
        .max(Comparator.naturalOrder())
        .orElse(787) + 1;

    Map<String, ? extends Serializable> parameters = Map.of(
        "identifier", identifier,
        "code", code,
        "name", name
    );

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate
    );

    this.available.available(new Option(code, name, name, identifier));

  }

}
