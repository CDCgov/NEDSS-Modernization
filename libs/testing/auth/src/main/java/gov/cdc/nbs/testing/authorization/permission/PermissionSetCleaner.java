package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthPermSet;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
class PermissionSetCleaner {



  private final EntityManager entityManager;

  PermissionSetCleaner(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  void clean(final long identifier) {

    AuthPermSet found = this.entityManager.find(AuthPermSet.class, identifier);

    if (found != null) {
      this.entityManager.remove(found);
    }


  }

}
