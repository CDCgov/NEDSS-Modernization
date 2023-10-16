package gov.cdc.nbs.testing.authorization;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.UUID;

@Component
public class ActiveUserMother {

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

  public void reset() {
    this.users.all().forEach(this.cleaner::clean);
    this.users.reset();
  }

  public ActiveUser create(final String name) {
    long identifier = idGenerator.next();

    AuthUser user = new AuthUser();
    user.setUserId(name);
    user.setUserType("internalUser");
    user.setUserFirstNm("test");
    user.setUserLastNm("user");
    user.setMasterSecAdminInd('F');
    user.setProgAreaAdminInd('F');
    user.setNedssEntryId(identifier);

    AuthAudit audit = new AuthAudit(this.settings.createdBy(), this.settings.createdOn());

    user.setAudit(audit);

    entityManager.persist(user);

    ActiveUser activeUser = new ActiveUser(user.getId(), user.getUserId(), user.getNedssEntryId());
    users.available(activeUser);

    return activeUser;
  }

  public ActiveUser create() {
    return create(UUID.randomUUID().toString());
  }
}
