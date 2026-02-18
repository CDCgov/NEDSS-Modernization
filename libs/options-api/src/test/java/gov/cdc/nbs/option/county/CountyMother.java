package gov.cdc.nbs.option.county;

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
class CountyMother {

  private static final String DELETE_IN =
      """
      delete
      from NBS_SRTE.[dbo].State_county_code_value
      where code in (:codes)
      """;

  private static final String CREATE =
      """
      insert into NBS_SRTE.[dbo].State_county_code_value(
        code,
        code_desc_txt,
        indent_level_nbr,
        parent_is_cd
      )
      values (:identifier, :name, :order, :stateCode)
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;

  CountyMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
  }

  @PreDestroy
  void reset() {

    List<String> codes = this.available.all().map(Option::value).toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("codes", codes);

      template.execute(
          DELETE_IN, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

      this.available.reset();
    }
  }

  void create(final String name, final String stateCode) {

    String code = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

    int order =
        this.available.all().map(Option::order).max(Comparator.naturalOrder()).orElse(1) + 1;

    Map<String, ? extends Serializable> parameters =
        Map.of(
            "identifier", code,
            "name", name,
            "stateCode", stateCode,
            "order", order);

    template.execute(
        CREATE, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

    this.available.available(new Option(code, name, name, order));
  }
}
