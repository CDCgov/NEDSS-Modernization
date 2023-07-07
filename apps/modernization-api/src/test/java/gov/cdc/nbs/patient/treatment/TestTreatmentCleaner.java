package gov.cdc.nbs.patient.treatment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.odse.Treatment;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
class TestTreatmentCleaner {

    private static final QTreatment TREATMENT = QTreatment.treatment;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    TestTreatmentCleaner(final EntityManager entityManager, final JPAQueryFactory factory) {
        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Transactional
    void clean(final long starting) {
        this.factory.select(TREATMENT)
                .from(TREATMENT)
                .where(criteria(starting))
                .fetch()
                .forEach(this::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = TREATMENT.id.goe(starting);
        return starting < 0
                ? threshold.and(TREATMENT.id.lt(0))
                : threshold;
    }

    private void remove(final Treatment treatment) {
        removeParticipation(treatment);

        this.entityManager.remove(treatment);
    }

    private void removeParticipation(final Treatment treatment) {
        //  The all treatment participation instances are associated with the Act entity.  Removing the Act will remove any
        //  associations.
        Act existing = this.entityManager.find(Act.class, treatment.getId());

        if (existing != null) {
            this.entityManager.remove(existing);
        }
    }
}
