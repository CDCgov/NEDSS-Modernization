package gov.cdc.nbs.option.language.primary;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
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
@ScenarioScope
class PrimaryLanguageMother {

  private static final String DELETE_IN =
      """
      delete
      from NBS_SRTE.[dbo].language_code
      where code in (:codes)
      """;

  private static final String CREATE =
      """
      insert into NBS_SRTE.[dbo].language_code(
        code,
        code_desc_txt,
        indent_level_nbr,
        status_cd
      )
      values (:identifier, :name, :order, 'A')
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> availalbe;

  PrimaryLanguageMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.availalbe = new Available<>();
  }

  @PreDestroy
  void reset() {

    List<String> codes = this.availalbe.all().map(Option::value).toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("codes", codes);

      template.execute(
          DELETE_IN, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

      this.availalbe.reset();
    }
  }

  void create(final String name) {

    String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

    int order =
        this.availalbe.all().map(Option::order).max(Comparator.naturalOrder()).orElse(1) + 1;

    Map<String, ? extends Serializable> parameters =
        Map.of(
            "identifier", code,
            "name", name,
            "order", order);

    template.execute(
        CREATE, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

    this.availalbe.available(new Option(code, name, name, order));
  }
}
