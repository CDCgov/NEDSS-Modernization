package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ScenarioScope
class ActiveUserMother {

  private final AuthenticationSupportSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final EntityManager entityManager;
  private final ActiveUserCleaner cleaner;
  private final Available<ActiveUser> users;

  ActiveUserMother(
      final AuthenticationSupportSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final ActiveUserCleaner cleaner,
      final Available<ActiveUser> users
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.users = users;
  }

  @PreDestroy
  public void reset() {
    this.users.all().forEach(this.cleaner::clean);
  }

  ActiveUser create(final String name) {
    return create(name, "test", "user");
  }

  ActiveUser create(
      final String username,
      final String first,
      final String last
  ) {
    AuthUser user = new AuthUser();
    user.setUserId(username);
    user.setUserType("internalUser");
    user.setUserFirstNm(first);
    user.setUserLastNm(last);
    user.setMasterSecAdminInd('F');
    user.setProgAreaAdminInd('F');

    return including(user);
  }

  private ActiveUser including(final AuthUser user) {
    long identifier = idGenerator.next();
    user.setNedssEntryId(identifier);

    AuthAudit audit = new AuthAudit(this.settings.createdBy(), this.settings.createdOn());

    user.setAudit(audit);

    entityManager.persist(user);

    ActiveUser activeUser = new ActiveUser(user.getId(), user.getUserId(), user.getNedssEntryId());
    users.available(activeUser);

    return activeUser;
  }

  ActiveUser create() {
    return create(UUID.randomUUID().toString());
  }
}
