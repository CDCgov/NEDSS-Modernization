package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthBusObjType;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
class AuthorizationObjectTypeFinder {

  private final EntityManager entityManager;

  AuthorizationObjectTypeFinder(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  AuthBusObjType find(final String name) {
    return this.entityManager.createQuery(
            "select op from AuthBusObjType op where op.busObjNm = :name",
            AuthBusObjType.class
        ).setParameter("name", name)
        .getSingleResult();
  }

}
