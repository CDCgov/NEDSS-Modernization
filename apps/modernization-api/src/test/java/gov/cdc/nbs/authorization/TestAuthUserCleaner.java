package gov.cdc.nbs.authorization;


import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class TestAuthUserCleaner {

    private static final QAuthUser USER = QAuthUser.authUser;

    private final EntityManager entityManager;
    private final JPAQueryFactory factory;


    TestAuthUserCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final TestAuthorizedUser user) {
        this.factory.select(USER)
            .from(USER)
            .where(USER.id.eq(user.id()))
            .fetch()
            .forEach(this::remove);
    }

    private void remove(final AuthUser user) {


        this.entityManager.remove(user);
    }
}
