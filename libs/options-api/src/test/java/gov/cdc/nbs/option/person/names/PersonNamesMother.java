package gov.cdc.nbs.option.person.names;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class PersonNamesMother {

  private static final String DELETE_IN =
      """
      delete from Person_name where person_uid in (:identifiers);
      delete from Person where person_uid in (:identifiers);
      delete from Entity_id where entity_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers);
      delete from Auth_user_role where auth_user_uid in (:identifiers);
      delete from Auth_user where auth_user_uid in (:identifiers);
      delete from Auth_perm_set where auth_perm_set_uid in (:identifiers);
   """;

  private static final String CREATE_ENTITY =
      "insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');";
  private static final String CREATE_PERSON =
      "insert into Person(person_uid, version_ctrl_nbr) values (:identifier, 1);";
  private static final String CREATE_PERSON_NAME =
      "insert into Person_name(person_uid, person_name_seq, status_cd, status_time, first_nm, last_nm) "
          + "values (:identifier, 1, 'A', :now, :firstName, :lastName);";
  private static final String CREATE_ENTITY_ID =
      "insert into Entity_id(entity_uid, entity_id_seq, type_cd, root_extension_txt) "
          + "values (:identifier, 1, 'QEC', :identifier);";

  private static final String CREATE_PERM_SET =
      """
        set IDENTITY_INSERT Auth_perm_set on;
        insert into Auth_perm_set(auth_perm_set_uid, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time)
            values (:permSetIdentifier, :now, :identifier, :now, :identifier, 'A', :now);
         set IDENTITY_INSERT Auth_perm_set off;
    """;

  private static final String CREATE_AUTH_USER =
      """
        set IDENTITY_INSERT Auth_user on;
        insert into Auth_user(
          auth_user_uid,
          user_id,
          nedss_entry_id,
          add_time,
          add_user_id,
          last_chg_time,
          last_chg_user_id,
          record_status_cd,
          record_status_time,
          provider_uid
        ) values (
          :identifier,
          :userId,
          :identifier,
          :now,
          :identifier,
          :now,
          :identifier,
          'A',
          :now,
          :identifier
        );
        set IDENTITY_INSERT Auth_user off;
    """;

  private static final String CREATE_AUTH_USER_ROLE =
      """
        set IDENTITY_INSERT Auth_user_role on;
        insert into Auth_user_role(auth_user_role_uid, auth_user_uid, auth_perm_set_uid, prog_area_cd, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time)
            values (:roleIdentifier, :identifier, :permSetIdentifier, 'STD', :now, :identifier, :now, :identifier, 'A', :now);
        set IDENTITY_INSERT Auth_user_role off;
    """;

  private final NamedParameterJdbcTemplate template;
  private final Available<Option> available;
  private final AtomicLong identifier;

  PersonNamesMother(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = new Available<>();
    long randomStart = ThreadLocalRandom.current().nextLong() & Long.MAX_VALUE;
    this.identifier = new AtomicLong(randomStart);
  }

  @PreDestroy
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

  void create(final String firstName, final String lastName) {
    int order =
        this.available.all().map(Option::order).max(Comparator.naturalOrder()).orElse(0) + 1;

    long next = nextIdentifier();
    long roleNext = nextIdentifier();
    long permSetNext = nextIdentifier();
    String fullName = String.format("%s %s", firstName, lastName);

    MapSqlParameterSource parameters =
        new MapSqlParameterSource()
            .addValue("identifier", next)
            .addValue("roleIdentifier", roleNext)
            .addValue("permSetIdentifier", permSetNext)
            .addValue("firstName", firstName)
            .addValue("lastName", lastName)
            .addValue("userId", "USER_" + next)
            .addValue("now", LocalDateTime.now());

    template.update(CREATE_ENTITY, parameters);
    template.update(CREATE_PERSON, parameters);
    template.update(CREATE_PERSON_NAME, parameters);
    template.update(CREATE_ENTITY_ID, parameters);
    template.update(CREATE_PERM_SET, parameters);
    template.update(CREATE_AUTH_USER, parameters);
    template.update(CREATE_AUTH_USER_ROLE, parameters);

    this.available.available(new Option(String.valueOf(next), fullName, fullName, order));
    this.available.available(new Option(String.valueOf(roleNext), fullName, fullName, order));
    this.available.available(new Option(String.valueOf(permSetNext), fullName, fullName, order));
  }
}
