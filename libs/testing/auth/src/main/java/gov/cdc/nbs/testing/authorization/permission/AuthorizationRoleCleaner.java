package gov.cdc.nbs.testing.authorization.permission;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.QAuthUserRole;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class AuthorizationRoleCleaner {

    private static final QAuthUserRole AUTHORIZATION_ROLE = QAuthUserRole.authUserRole;

    private final EntityManager entityManager;

    private final JPAQueryFactory factory;

    AuthorizationRoleCleaner(
        final EntityManager entityManager,
        final JPAQueryFactory factory
    ) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    void clean(final long identifier) {
        this.factory.select(AUTHORIZATION_ROLE)
            .from(AUTHORIZATION_ROLE)
            .where(AUTHORIZATION_ROLE.id.eq(identifier))
            .fetch()
            .forEach(this.entityManager::remove);
    }


}
