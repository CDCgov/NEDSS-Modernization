package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthBusOpType;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
class AuthorizationOperationTypeFinder {


  private final EntityManager entityManager;

  AuthorizationOperationTypeFinder(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  AuthBusOpType find(final String name) {
    return this.entityManager.createQuery(
            "select op from AuthBusOpType op where op.busOpNm = :name",
            AuthBusOpType.class
        ).setParameter("name", name)
        .getSingleResult();
  }

}
