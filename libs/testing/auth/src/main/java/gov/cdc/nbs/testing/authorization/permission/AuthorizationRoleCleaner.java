package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthUserRole;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
class AuthorizationRoleCleaner {

  private final EntityManager entityManager;

  AuthorizationRoleCleaner(final EntityManager entityManager) {
    this.entityManager = entityManager;

  }

  void clean(final long identifier) {

    AuthUserRole found = this.entityManager.find(AuthUserRole.class, identifier);

    if (found != null) {
      this.entityManager.remove(found);
    }

  }

}
