package gov.cdc.nbs.testing.authorization.permission;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.QAuthPermSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
class PermissionSetCleaner {

  private static final QAuthPermSet PERMISSION_SET = QAuthPermSet.authPermSet;

  private final EntityManager entityManager;

  private final JPAQueryFactory factory;

  PermissionSetCleaner(
      final EntityManager entityManager,
      final JPAQueryFactory factory
  ) {
    this.entityManager = entityManager;
    this.factory = factory;
  }

  @Transactional
  void clean(final long identifier) {
    this.factory.select(PERMISSION_SET)
        .from(PERMISSION_SET)
        .where(PERMISSION_SET.id.eq(identifier))
        .fetch()
        .forEach(this.entityManager::remove);
  }


}
