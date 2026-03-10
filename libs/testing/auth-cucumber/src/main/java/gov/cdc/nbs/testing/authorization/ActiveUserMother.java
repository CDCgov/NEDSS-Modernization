package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class ActiveUserMother {

  private static final String CREATE =
      """
      insert into Auth_user (
        nedss_entry_id,
        user_id,
        user_first_nm,
        user_last_nm,
        prog_area_admin_ind,
        master_sec_admin_ind,
        user_type,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      ) values (
        :nedss,
        :username,
        :firstName,
        :lastName,
        'F',
        'F',
        'internalUser',
        :addedBy,
        :addedOn,
        :addedBy,
        :addedOn,
        'ACTIVE',
        :addedOn
      );

      select @@Identity
      """;

  private static final String DELETE =
      """
      delete from Auth_user_role where auth_user_uid in (:identifiers);
      delete from Auth_user where auth_user_uid in (:identifiers);
      """;

  private final AuthenticationSupportSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  private final Available<ActiveUser> users;

  ActiveUserMother(
      final AuthenticationSupportSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<ActiveUser> users) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.users = users;
    this.cleaner = new TestingDataCleaner<>(client, DELETE, "identifiers");
  }

  @PreDestroy
  public void reset() {
    this.cleaner.clean();
  }

  ActiveUser create(final String name) {
    return create(name, "test", "user");
  }

  ActiveUser create(final String username, final String first, final String last) {

    long nedss = idGenerator.next();

    long id =
        this.client
            .sql(CREATE)
            .param("nedss", nedss)
            .param("username", username)
            .param("firstName", first)
            .param("lastName", last)
            .param("addedOn", this.settings.createdOn())
            .param("addedBy", this.settings.createdBy())
            .query(Long.class)
            .single();

    ActiveUser activeUser = new ActiveUser(id, username, nedss);
    include(activeUser);
    return activeUser;
  }

  private void include(final ActiveUser activeUser) {
    users.available(activeUser);
    cleaner.include(activeUser.id());
  }

  ActiveUser create() {
    return create(UUID.randomUUID().toString());
  }
}
