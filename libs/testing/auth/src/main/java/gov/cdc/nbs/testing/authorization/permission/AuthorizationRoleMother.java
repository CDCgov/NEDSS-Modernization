package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.authorization.AuthenticationSupportSettings;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationRoleMother {

  private final AuthenticationSupportSettings settings;
  private final JdbcClient client;
  private final EntityManager entityManager;


  AuthorizationRoleMother(
      final AuthenticationSupportSettings settings,
      final JdbcClient client,
      final EntityManager entityManager
  ) {
    this.settings = settings;
    this.client = client;
    this.entityManager = entityManager;

  }

  public void allowAny(final ActiveUser user, final long set) {
    allow(user, set, 'F', "STD", "all");
  }

  public void allowAny(final ActiveUser user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'F', programArea, jurisdiction);
  }

  public void allowShared(final ActiveUser user, final long set) {
    allow(user, set, 'T', "STD", "all");
  }

  public void allowShared(final ActiveUser user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'T', programArea.toUpperCase(), jurisdiction.toUpperCase());
  }

  public void systemAdmin(final ActiveUser user) {
    this.client.sql("update dbo.Auth_user set master_sec_admin_ind = 'T' where auth_user_uid = ?")
        .param(user.id())
        .update();
  }

  public void securityAdmin(final ActiveUser user) {
    this.client.sql("update dbo.Auth_user set prog_area_admin_ind = 'T' where auth_user_uid = ?")
        .param(user.id())
        .update();
  }

  private void allow(
      final ActiveUser user,
      final long set,
      final char guest,
      final String programArea,
      final String jurisdiction
  ) {
    AuthUser authUser = this.entityManager.find(AuthUser.class, user.id());
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
