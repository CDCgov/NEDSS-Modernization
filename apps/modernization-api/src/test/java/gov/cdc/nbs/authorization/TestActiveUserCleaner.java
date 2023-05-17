package gov.cdc.nbs.authorization;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
class TestActiveUserCleaner {

    private static final QAuthUser USER = QAuthUser.authUser;

    private final EntityManager entityManager;
    private final JPAQueryFactory factory;


    TestActiveUserCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final long starting) {
        this.factory.select(USER)
            .from(USER)
            .where(criteria(starting))
            .fetch()
            .forEach(this::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = USER.nedssEntryId.goe(starting);
        return starting < 0
            ? threshold.and(USER.id.lt(0))
            : threshold;
    }

    private void remove(final AuthUser user) {


        this.entityManager.remove(user);
    }
}
