package gov.cdc.nbs.option.condition;

import gov.cdc.nbs.option.Option;
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
class ConditionMother {

  private static final String DELETE_IN =
      """
      delete
      from NBS_SRTE.[dbo].Condition_code
      where condition_cd in (:codes)
      """;

  private static final String CREATE =
      """
      insert into NBS_SRTE.[dbo].Condition_code(
        condition_cd,
        condition_short_nm,
        indent_level_nbr,
        status_cd,
        nnd_ind,
        reportable_morbidity_ind,
        reportable_summary_ind
      )
      values (:identifier, :name, :order, 'A', 'N','N','N')
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> availalbe;

  ConditionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.availalbe = new Available<>();
  }

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

    int order = this.availalbe.all().map(Option::order).max(Comparator.naturalOrder()).orElse(1);

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
