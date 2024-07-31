package gov.cdc.nbs.option.facility.autocomplete;

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
import java.util.concurrent.atomic.AtomicLong;

@Component
@ScenarioScope
class FacilityOptionMother {

  private static final String DELETE_IN = """
      delete
      from Organization
      where organization_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers);
      """;

  private static final String CREATE = """
      insert into Entity(entity_uid, class_cd) values (:identifier, 'ORG');
      insert into Organization(organization_uid, display_nm, version_ctrl_nbr, electronic_ind)
      values (:identifier, :name, 1, :electronic);
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;
  private final AtomicLong identifier;

  FacilityOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
    this.identifier = new AtomicLong(Long.MIN_VALUE);
  }

  @PreDestroy
  void reset() {

    List<String> codes = this.available.all()
        .map(Option::value)
        .toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("identifiers", codes);

      template.execute(
          DELETE_IN,
          new MapSqlParameterSource(parameters),
          PreparedStatement::executeUpdate);
      this.available.reset();
    }
  }

  private long nextIdentifier() {
    return identifier.getAndIncrement();
  }

  void create(final String name) {
    create(name, false);
  }

  void electronic(final String name) {
    create(name, true);
  }

  private void create(final String name, final boolean isElectronic) {
    int order = this.available.all()
        .map(Option::order)
        .max(Comparator.naturalOrder())
        .orElse(1);

    long next = nextIdentifier();

    String electronic = isElectronic ? "Y" : "N";

    Map<String, ? extends Serializable> parameters = Map.of(
        "identifier", next,
        "name", name,
        "electronic", electronic
    );

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

    this.available.available(new Option(String.valueOf(next), name, name, order));
  }
}
