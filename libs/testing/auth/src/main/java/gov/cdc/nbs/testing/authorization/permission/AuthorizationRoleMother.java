package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import gov.cdc.nbs.testing.authorization.AuthenticationSupportSettings;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
public class AuthorizationRoleMother {

  private final AuthenticationSupportSettings settings;
  private final EntityManager entityManager;
  private final AuthorizationRoleCleaner cleaner;
  private final Available<Long> available;

  AuthorizationRoleMother(
      final AuthenticationSupportSettings settings,
      final EntityManager entityManager,
      final AuthorizationRoleCleaner cleaner) {
    this.settings = settings;
    this.entityManager = entityManager;
    this.cleaner = cleaner;
    this.available = new Available<>();
  }

  public void reset() {
    this.available.all().forEach(this.cleaner::clean);
    this.available.reset();
  }

  public void allowAny(final long user, final long set) {
    allow(user, set, 'F', "STD", "all");
  }

  public void allowAny(final long user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'F', programArea, jurisdiction);
  }

  public void allowShared(final long user, final long set) {
    allow(user, set, 'T', "STD", "all");
  }

  public void allowShared(final long user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'T', programArea.toUpperCase(), jurisdiction.toUpperCase());
  }

  private void allow(
      final long user,
      final long set,
      final char guest,
      final String programArea,
      final String jurisdiction
  ) {
    AuthUser authUser = this.entityManager.find(AuthUser.class, user);
    AuthPermSet authPermSet = this.entityManager.find(AuthPermSet.class, set);

    AuthUserRole role = new AuthUserRole(authUser, authPermSet)
        .guest(guest)
        .programArea(programArea)
        .jurisdiction(jurisdiction)
        .audit(resolveAudit());

    this.entityManager.persist(role);
  }

  private AuthAudit resolveAudit() {
    return new AuthAudit(settings.createdBy(), settings.createdOn());
  }
}
