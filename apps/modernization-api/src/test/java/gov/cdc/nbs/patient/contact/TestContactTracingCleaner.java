package gov.cdc.nbs.patient.contact;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.QCtContact;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
class TestContactTracingCleaner {

    private static final QCtContact TRACING = QCtContact.ctContact;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    TestContactTracingCleaner(final EntityManager entityManager, JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final long starting) {
        this.factory.select(TRACING)
                .from(TRACING)
                .where(criteria(starting))
                .fetch()
                .forEach(entityManager::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = TRACING.id.goe(starting);
        return starting < 0
                ? threshold.and(TRACING.id.lt(0))
                : threshold;
    }

}
