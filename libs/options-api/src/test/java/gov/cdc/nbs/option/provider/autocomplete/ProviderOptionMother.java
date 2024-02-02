package gov.cdc.nbs.option.provider.autocomplete;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.support.Available;
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
import java.util.concurrent.atomic.AtomicLong;

@Component
class ProviderOptionMother {

  private static final String DELETE_IN = """
      delete from Participation where subject_class_cd = 'PSN' and subject_entity_uid in (:identifiers)
      delete from Person_name where person_uid in (:identifiers);
      delete from Person where person_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers) and class_cd = 'PSN';
      """;

  private static final String CREATE =
      """
          insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
          insert into Person(person_uid, version_ctrl_nbr, cd, first_nm, last_nm) values (:identifier, 1, 'PRV', :first, :last);

          insert into Person_name(
            person_uid,
            person_name_seq,
            first_nm,
            last_nm,
            status_cd,
            status_time
          ) values (
            :identifier,
            1,
            :first,
            :last,
            'A',
            GETDATE()
          )
          """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;
  private final AtomicLong identifier;

  ProviderOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
    this.identifier = new AtomicLong(Long.MIN_VALUE);
  }

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

  void create(final String first, final String last) {

    String username = UUID.randomUUID().toString()
        .replace("-", "")
        .substring(0, 20);

    int order = this.available.all()
        .map(Option::order)
        .max(Comparator.naturalOrder())
        .orElse(1);

    long next = nextIdentifier();

    Map<String, ? extends Serializable> parameters = Map.of(
        "username", username,
        "first", first,
        "last", last,
        "identifier", next);

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

    String name = String.format("%s %s", first, last);

    this.available.available(new Option(String.valueOf(next), name, name, order));

  }

}
