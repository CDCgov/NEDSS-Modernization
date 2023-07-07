package gov.cdc.nbs.patient.profile.vaccination;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.odse.Intervention;
import gov.cdc.nbs.entity.odse.QIntervention;
import gov.cdc.nbs.patient.ParticipationCleaner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
class TestVaccinationCleaner {

    private static final QIntervention TABLE = QIntervention.intervention;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    private final ParticipationCleaner participationCleaner;

    TestVaccinationCleaner(
            final EntityManager entityManager,
            final JPAQueryFactory factory,
            final ParticipationCleaner participationCleaner) {
        this.entityManager = entityManager;
        this.factory = factory;
        this.participationCleaner = participationCleaner;
    }

    void clean(final long starting) {
        this.factory.select(TABLE)
                .from(TABLE)
                .where(criteria(starting))
                .fetch()
                .forEach(this::remove);
    }

    private BooleanExpression criteria(final long starting) {
        BooleanExpression threshold = TABLE.id.goe(starting);
        return starting < 0
                ? threshold.and(TABLE.id.lt(0))
                : threshold;
    }

    private void remove(final Intervention intervention) {
        participationCleaner.clean(intervention.getId(), "INTV");

        this.entityManager.remove(intervention);
    }



}
