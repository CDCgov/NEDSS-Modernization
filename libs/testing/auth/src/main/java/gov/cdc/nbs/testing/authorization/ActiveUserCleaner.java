package gov.cdc.nbs.testing.authorization;


import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class ActiveUserCleaner {

    private static final QAuthUser USER = QAuthUser.authUser;

    private final EntityManager entityManager;
    private final JPAQueryFactory factory;


    ActiveUserCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final ActiveUser user) {
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
