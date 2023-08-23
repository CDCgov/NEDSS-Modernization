package gov.cdc.nbs.event.investigation;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class TestInvestigationCleaner {

    public static final QPublicHealthCase INVESTIGATION = QPublicHealthCase.publicHealthCase;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    TestInvestigationCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    void clean(final long starting) {
        this.factory.select(INVESTIGATION)
                .from(INVESTIGATION)
                .where(criteria(starting))
                .fetch()
                .forEach(entityManager::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = INVESTIGATION.id.goe(starting);
        return starting < 0
                ? threshold.and(INVESTIGATION.id.lt(0))
                : threshold;
    }

}
