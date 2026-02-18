package gov.cdc.nbs.option.user.autocomplete;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.support.Available;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class UserOptionMother {

  private static final String DELETE_IN =
      """
      delete
      from Auth_user
      where auth_user_uid in (:identifiers)
      """;

  private static final String CREATE =
      """
      insert into Auth_user(
        user_id,
        user_first_nm,
        user_last_nm,
        nedss_entry_id,
        record_status_cd,
        record_status_time,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time
      )
      values (:username, :first, :last, :identifier, 'A', getDate(), -9999, getDate(), -9999, getDate());
      """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;
  private final AtomicLong identifier;

  UserOptionMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
    this.identifier = new AtomicLong(Long.MIN_VALUE);
  }

  void reset() {

    List<String> codes = this.available.all().map(Option::value).toList();

    if (!codes.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("identifiers", codes);

      template.execute(
          DELETE_IN, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);
      this.available.reset();
    }
  }

  private long nextIdentifier() {
    return identifier.getAndIncrement();
  }

  void create(final String first, final String last) {

    String username = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

    int order = this.available.all().map(Option::order).max(Comparator.naturalOrder()).orElse(1);

    long next = nextIdentifier();

    Map<String, ? extends Serializable> parameters =
        Map.of(
            "username", username,
            "first", first,
            "last", last,
            "identifier", next);

    template.execute(
        CREATE, new MapSqlParameterSource(parameters), PreparedStatement::executeUpdate);

    String name = "%s %s".formatted(first, last);

    this.available.available(new Option(String.valueOf(next), name, name, order));
  }
}
